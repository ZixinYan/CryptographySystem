package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.ECC;
import com.zixin.cryptography.service.ECCService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class ECCServiceImpl implements ECCService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\ECC_signature_string.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\ECC_verify_string.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\ECC_signature_file.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\ECC_verify_file.exe";
    String generateKeyPath = "E:\\Biancheng\\cryptography\\tool\\KeyPair_gen.exe";


    @Override
    public ECC generateKey(ECC ecc) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        ecc.setCreateUser(id);
        String line;
        try {
            String cmd = generateKeyPath;
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 使用 BufferedReader 读取命令行输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            int lineCount = 0;

            // 读取每一行输出
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");

                // 将第一行赋值给 ecc.publicKey
                if (lineCount == 0) {
                    ecc.setPrivateKey(line);
                }
                // 将第二行赋值给 ecc.privateKey
                if (lineCount == 1) {
                    ecc.setPublicKey(line);
                }
                lineCount++;
            }
            // 等待进程完成
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ecc;
    }

    @Override
    public ECC encrypt(ECC ecc) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        ecc.setCreateUser(id);
        String line;
        try {
            String cmd = enPath  + " " + StringUtil.addUnderLine(ecc.getPlaintext()) + " " + ecc.getPublicKey();
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
                ecc.setCiphertext(output.toString().trim());
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ecc;
    }

    @Override
    public ECC decrypt(ECC ecc) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        ecc.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + StringUtil.addUnderLine(ecc.getPlaintext())+ " " + ecc.getCiphertext() +" " + ecc.getPrivateKey();
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
                ecc.setSignature(output.toString().trim());
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ecc;
    }

    @Override
    public ECC encryptFile(ECC ecc) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        ecc.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + ecc.getPlainFile() + " " + ecc.getPublicKey();
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
                ecc.setCiphertext(output.toString().trim());
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ecc;
    }

    @Override
    public ECC decryptFile(ECC ecc) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        ecc.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + ecc.getPlainFile() + " " + ecc.getCiphertext() + " " + ecc.getPrivateKey();
            System.out.println(cmd);
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
                ecc.setSignature(output.toString().trim());
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ecc;
    }
}
