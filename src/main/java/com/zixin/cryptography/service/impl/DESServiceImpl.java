package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.DES;
import com.zixin.cryptography.service.DESService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class DESServiceImpl implements DESService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\DESEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\DESDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\DESEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\DESDecryptFile.exe";
    @Override
    public DES encrypt(DES des) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        des.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + des.getKey() + " " + StringUtil.addUnderLine(des.getPlaintext());
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
                des.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return des;
    }

    @Override
    public DES decrypt(DES des) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        des.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + des.getKey() + " " + StringUtil.addUnderLine(des.getCiphertext());
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
                des.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return des;
    }

    @Override
    public DES encryptFile(DES des) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        des.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + des.getKey() + " " + des.getPlainFile();
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
                    des.setCipherFile(downloadUrl);
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
        return des;
    }

    @Override
    public DES decryptFile(DES des) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        des.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + des.getKey() + " " + des.getCipherFile();
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
                File decryptedFile = new File(output.toString().trim());
                if(decryptedFile.exists()){
                    String downloadUrl = "https://9tf51st50240.vicp.fun/download?fileName=" + decryptedFile.getName();
                    des.setPlainFile(downloadUrl);
                }else{
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
        return des;
    }
}
