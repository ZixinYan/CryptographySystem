package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.utils.AliOSSUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+name.substring(name.lastIndexOf("."));
        String url = AliOSSUtils.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }

    private static final String UPLOAD_DIR = "E:\\Biancheng\\cryptography\\fileStorage";
    @PostMapping("/uploadFile")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        // 定义上传目录
        final String UPLOAD_DIR = "E:/Biancheng/cryptography/fileStorage/";

        try {
            // 检查目录是否存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 自动创建目录
            }

            // 保存文件
            File uploadedFile = new File(Paths.get(UPLOAD_DIR, UUID.randomUUID().toString()+"--"+file.getOriginalFilename()).toString());
            file.transferTo(uploadedFile);

            // 返回保存的文件路径
            String path = uploadedFile.getAbsolutePath();
            System.out.println("File saved to: " + path); // 打印日志
            return Result.success(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("File upload failed: " + e.getMessage());
        }
    }
        private static final String FILE_STORAGE_PATH = "E:\\Biancheng\\cryptography\\result";

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/download")
    public Result<Map<String, Object>> downloadFile(@RequestParam String fileName) {
        try {
            // 解码文件名
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.toString());
            System.out.println("请求下载文件: " + fileName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            // 检查文件路径合法性，防止路径穿越攻击
            if (fileName.contains("..") || fileName.contains(File.separator)) {
                throw new SecurityException("非法文件路径");
            }

            // 构建文件路径
            Path filePath = Paths.get(FILE_STORAGE_PATH).resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("文件不存在: " + fileName);
            }

            // 创建资源
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("无法读取文件: " + fileName);
            }

            // 将文件内容转换为字节数组
            byte[] fileContent = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            contentType = (contentType != null) ? contentType : "application/octet-stream";

            // 将内容封装为可序列化的 Map
            Map<String, Object> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("contentType", contentType);
            response.put("content", Base64.getEncoder().encodeToString(fileContent)); // Base64 编码文件内容

            return Result.success(response);
        } catch (Exception e) {
            return Result.error("下载失败: " + e.getMessage());
        }
    }



}
