package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.Autokey;
import com.zixin.cryptography.service.AutokeyService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class AutokeyServiceImpl implements AutokeyService {

    String enPath = "E:\\Biancheng\\cryptography\\tool\\AutokeyEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\AutokeyDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\AutokeyEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\AutokeyDecryptFile.exe";
    @Override
    public Autokey encrypt(Autokey autokey) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        autokey.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + autokey.getKey() + " " + StringUtil.addUnderLine(autokey.getPlaintext());
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
                autokey.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return autokey;
    }

    @Override
    public Autokey decrypt(Autokey autokey) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        autokey.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + autokey.getKey() + " " + StringUtil.addUnderLine(autokey.getCiphertext());
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
                autokey.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return autokey;
    }

    @Override
    public Autokey encryptFile(Autokey autokey) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        autokey.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + autokey.getKey() + " " + autokey.getPlainFile();
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
                    autokey.setCipherFile(downloadUrl);
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

        return autokey;
    }

    @Override
    public Autokey decryptFile(Autokey autokey) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        autokey.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + autokey.getKey() + " " + autokey.getCipherFile();
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
                    autokey.setPlainFile(downloadUrl);
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

        return autokey;
    }
}
