package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.Vigenere;
import com.zixin.cryptography.service.VigenereService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class VigenereServiceImpl implements VigenereService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\VigenereEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\VigenereDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\VigenereEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\VigenereDecryptFile.exe";
    @Override
    public Vigenere encrypt(Vigenere vigenere) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        vigenere.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + vigenere.getKey() + " " + StringUtil.addUnderLine(vigenere.getPlaintext());
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
                vigenere.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return vigenere;
    }

    @Override
    public Vigenere decrypt(Vigenere vigenere) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        vigenere.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + vigenere.getKey() + " " + StringUtil.addUnderLine(vigenere.getCiphertext());
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
                vigenere.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return vigenere;
    }

    @Override
    public Vigenere encryptFile(Vigenere vigenere) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        vigenere.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + vigenere.getKey() + " " + vigenere.getPlainFile();
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
                System.out.println("加密结果: " + output.toString().trim());
                File encryptedFile = new File(output.toString().trim());
                if(encryptedFile.exists()){
                    String downloadUrl = "http://9tf51st50240.vicp.fun/download?fileName=" + encryptedFile.getName();
                    vigenere.setCipherFile(downloadUrl);
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
        return vigenere;
    }

    @Override
    public Vigenere decryptFile(Vigenere vigenere) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        vigenere.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + vigenere.getKey() + " " + vigenere.getCipherFile();
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
                    vigenere.setPlainFile(downloadUrl);
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
        return vigenere;
    }
}
