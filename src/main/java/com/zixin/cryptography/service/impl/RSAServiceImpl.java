package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.RSA;
import com.zixin.cryptography.service.RSAService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class RSAServiceImpl implements RSAService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\RSA_encrypt_string.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\RSA_decrypt_string.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\RSA_encrypt_file.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\RSA_decrypt_file.exe";
    String generateKeyPath = "E:\\Biancheng\\cryptography\\tool\\RSA_KeyPair_gen.exe";
    @Override
    public RSA encrypt(RSA rsa) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        rsa.setCreateUser(id);
        String line;
        try {
            String cmd = enPath  + " " + StringUtil.addUnderLine(rsa.getPlaintext()) + " " + rsa.getE() + " " + rsa.getN();
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
                System.out.println("加密结果: " + output.toString().trim());
                rsa.setCiphertext(output.toString().trim());
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsa;
    }

    @Override
    public RSA decrypt(RSA rsa) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        rsa.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + rsa.getCiphertext() + " " + rsa.getD() + " " + rsa.getN();
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
                rsa.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsa;
    }

    @Override
    public RSA encryptFile(RSA rsa) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        rsa.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + rsa.getPlainFile() + " " + rsa.getE() + " " + rsa.getN();
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
                System.out.println("加密结果: " + output.toString().trim());
                File encryptedFile = new File(output.toString().trim());
                if (encryptedFile.exists()) {
                    String downloadUrl = "http://9tf51st50240.vicp.fun/download?fileName=" + encryptedFile.getName();
                    rsa.setCipherFile(downloadUrl);
                } else {
                    System.err.println("加密失败");
                }
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsa;
    }

    @Override
    public RSA decryptFile(RSA rsa) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        rsa.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + rsa.getPlainFile() + " " + rsa.getD() + " " + rsa.getN();
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
                    String downloadUrl = "http://9tf51st50240.vicp.fun/download?fileName=" + decryptedFile.getName();
                    rsa.setPlainFile(downloadUrl);
                } else {
                    System.err.println("解密失败");
                }
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsa;
    }

    @Override
    public RSA generateKey(RSA rsa) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        rsa.setCreateUser(id);
        String line;
        try {
            String cmd = generateKeyPath + " " + rsa.getLength();
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    if (lineCount == 0) {
                        rsa.setE(parts[0]);
                        rsa.setN(parts[1]);
                    } else if (lineCount == 1) {
                        rsa.setD(parts[0]);
                        rsa.setN(parts[1]);
                    }
                }
                lineCount++;
            }
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsa;
    }
}
