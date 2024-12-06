package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.Playfair;
import com.zixin.cryptography.service.PlayfairService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class PlayfairServiceImpl implements PlayfairService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\PlayfairEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\PlayfairDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\PlayfairEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\PlayfairDecryptFile.exe";
    @Override
    public Playfair encrypt(Playfair playfair) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        playfair.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + playfair.getKey() + " " + StringUtil.addUnderLine(playfair.getPlaintext());
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
                playfair.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return playfair;
    }

    @Override
    public Playfair decrypt(Playfair playfair) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        playfair.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + playfair.getKey() + " " + StringUtil.addUnderLine(playfair.getCiphertext());
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
                playfair.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return playfair;
    }

    @Override
    public Playfair encryptFile(Playfair playfair) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        playfair.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + playfair.getKey() + " " + playfair.getPlainFile();

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
                    String downloadUrl = "http://9tf51st50240.vicp.fun/download?fileName=" + encryptedFile.getName();
                    playfair.setCipherFile(downloadUrl);
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
        return playfair;
    }

    @Override
    public Playfair decryptFile(Playfair playfair) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        playfair.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + playfair.getKey() + " " + playfair.getCipherFile();
            System.out.println(cmd);
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
                    String downloadUrl = "http://9tf51st50240.vicp.fun/download?fileName=" + decryptedFile.getName();
                    playfair.setPlainFile(downloadUrl);
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

        return playfair;
    }
}
