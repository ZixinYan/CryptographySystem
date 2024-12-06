package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.Affine;
import com.zixin.cryptography.service.AffineService;
import com.zixin.cryptography.utils.StringUtil;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Service
public class AffineServiceImpl implements AffineService {
    String enPath = "E:\\Biancheng\\cryptography\\tool\\AffineEncrypt.exe";
    String dePath = "E:\\Biancheng\\cryptography\\tool\\AffineDecrypt.exe";
    String enFilePath = "E:\\Biancheng\\cryptography\\tool\\AffineEncryptFile.exe";
    String deFilePath = "E:\\Biancheng\\cryptography\\tool\\AffineDecryptFile.exe";

    @Override
    public Affine encrypt(Affine affine) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        affine.setCreateUser(id);
        String line;
        try {
            String cmd = enPath + " " + affine.getKeyA() + " " + affine.getKeyB() + " " + StringUtil.addUnderLine(affine.getPlaintext());
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
                affine.setCiphertext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return affine;
    }

    @Override
    public Affine decrypt(Affine affine) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        affine.setCreateUser(id);
        String line;
        try {
            String cmd = dePath + " " + affine.getKeyA() + " " + affine.getKeyB() + " " + StringUtil.addUnderLine(affine.getCiphertext());
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
                affine.setPlaintext(StringUtil.changeUnderLine(output.toString().trim()));
            } else {
                System.err.println("执行失败，错误代码: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return affine;
    }

    @Override
    public Affine encryptFile(Affine affine) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        affine.setCreateUser(id);
        String line;
        try {
            String cmd = enFilePath + " " + affine.getKeyA() + " " + affine.getKeyB() + " " + affine.getPlainFile();
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
                    affine.setCipherFile(downloadUrl);
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

        return affine;
    }

    @Override
    public Affine decryptFile(Affine affine) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        affine.setCreateUser(id);
        String line;
        try {
            String cmd = deFilePath + " " + affine.getKeyA() + " " + affine.getKeyB() + " " + affine.getCipherFile();
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
                    affine.setPlainFile(downloadUrl);
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
        return affine;
    }


}
