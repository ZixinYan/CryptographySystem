package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.ColumnPermutation;
import com.zixin.cryptography.service.ColumnPermutationService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class ColumnPermutationServiceImpl implements ColumnPermutationService{

    String enPath = "E:\\Biancheng\\cryptography\\tool\\ColumnPermutationEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\ColumnPermutationDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\ColumnPermutationEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\ColumnPermutationDecryptFile.exe";

    @Override
    public ColumnPermutation encrypt(ColumnPermutation columnPermutation) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        columnPermutation.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + columnPermutation.getKey() + " " + StringUtil.addUnderLine(columnPermutation.getPlaintext());
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("加密结果: " + output.toString().trim());
                columnPermutation.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return columnPermutation;
    }

    @Override
    public ColumnPermutation decrypt(ColumnPermutation columnPermutation) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        columnPermutation.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + columnPermutation.getKey() + " " + StringUtil.addUnderLine(columnPermutation.getCiphertext());
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("解密结果: " + output.toString().trim());
                columnPermutation.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return columnPermutation;
    }

    @Override
    public ColumnPermutation encryptFile(ColumnPermutation columnPermutation) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        columnPermutation.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + columnPermutation.getKey() + " " + columnPermutation.getPlainFile();
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("加密结果: " + output.toString().trim());
                File encryptedFile = new File(output.toString().trim());
                if(encryptedFile.exists()){
                    String downloadUrl = "https://9tf51st50240.vicp.fun/download?fileName=" + encryptedFile.getName();
                    columnPermutation.setCipherFile(downloadUrl);
                }else{
                    System.err.println("加密失败");
                }

            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return columnPermutation;
    }

    @Override
    public ColumnPermutation decryptFile(ColumnPermutation columnPermutation) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        columnPermutation.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + columnPermutation.getKey() + " " + columnPermutation.getCipherFile();
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("解密结果: " + output.toString().trim());
                File decryptedFile = new File(output.toString().trim());
                if (decryptedFile.exists()) {
                    String downloadUrl = "https://9tf51st50240.vicp.fun/download?fileName=" + decryptedFile.getName();
                    columnPermutation.setPlainFile(downloadUrl);
                } else {
                    System.err.println("解密失败");
                }
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return columnPermutation;
    }
}
