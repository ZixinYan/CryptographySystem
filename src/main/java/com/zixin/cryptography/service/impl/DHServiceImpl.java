package com.zixin.cryptography.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zixin.cryptography.mapper.DHMapper;
import com.zixin.cryptography.pojo.DH;
import com.zixin.cryptography.pojo.Note;
import com.zixin.cryptography.pojo.PageBean;
import com.zixin.cryptography.utils.Md5Util;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class DHServiceImpl implements com.zixin.cryptography.service.DHService {

    String genPath = "E:\\Biancheng\\cryptography\\tool\\Prime_gen.exe";
    String genPrivate = "E:\\Biancheng\\cryptography\\tool\\private_Keygen.exe";
    String cal = "E:\\Biancheng\\cryptography\\tool\\Key_calculate.exe";

    String P;
    @Autowired
    private DHMapper dhMapper;

    @Override
    public void delete(Integer id) {
        dhMapper.delete(id);
    }



    @Override
    public DH generateAKey(DH dh) {
        String calKey = cal + " " + dh.getB() + " " + dh.getPrivateKey() + " " + dh.getP();
        System.out.println(calKey);
        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", calKey);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();
            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                dh.setKey(output.toString().trim());
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dh;
    }

    @Override
    public DH generateBKey(DH dh) {
        String calKey = cal + " " + dh.getA() + " " + dh.getPrivateKey() + " " + dh.getP();
        System.out.println(calKey);
        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", calKey);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                dh.setKey(output.toString().trim());
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dh;
    }

    @Override
    public void add(DH dh) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        dh.setCreateUser(id);
        if(dh.getCreateUser() == dh.getTargetUser()){
            throw new RuntimeException("不能给自己发送密钥");
        }

        String genP = genPath + " " + "1024";

        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", genP);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                P = output.toString().trim();
                dh.setP(P);
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String g = selectRandomG();
        dh.setG(g);

        String publicKey = cal + " " + g +  " " + dh.getPrivateKey()  + " " + P;

        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", publicKey);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                dh.setA(output.toString().trim());
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dhMapper.add(dh);
    }


    @Override
    public PageBean<DH> list(Integer pageNum, Integer pageSize, Integer createUser, Integer targetUser) {
        PageBean<DH> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        System.out.println(createUser);
        List<DH> list = dhMapper.list(id,createUser, targetUser);
        // 强转, 由于PageBean继承自ArrayList, 所以可以强转, 可以获取PageHelper的分页信息
        Page<DH> p = (Page<DH>) list;
        //填充到PageBean
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void update(DH dh) {
        String publicKey = cal + " " + dh.getG() + " " + dh.getPrivateKey() + " " + dh.getP();
        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", publicKey);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                dh.setB(output.toString().trim());
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dhMapper.update(dh);
    }

    @Override
    public DH generatePrivateKey(DH dh) {

        String genPrivateKey;
        if(dh.getP()!=null){
             genPrivateKey = genPrivate + " " + dh.getP();
        }else{
             genPrivateKey = genPrivate + " " + "fffffffffffffffffffffffffffffffffffffffffffff";
        }

        try {
            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", genPrivateKey);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流中
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 打印输出结果
                System.out.println("Program Output: " + output.toString().trim());
                dh.setPrivateKey(output.toString().trim());
            } else {
                System.err.println("Error occurred, exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dh;
    }






    public static String selectRandomG() {
        // 候选生成元数组
        int[] candidates = {2, 5};

        // 创建随机数生成器
        Random random = new Random();

        // 从数组中随机选择一个索引
        int randomIndex = random.nextInt(candidates.length);

        // 返回随机选择的生成元
        return String.valueOf(candidates[randomIndex]);
    }


    public static String generateGenerator(String pStr) {
        // 将字符串 p 转换为 BigInteger
        BigInteger p = new BigInteger(pStr,16);
        // 检查 p 是否为素数
        if (!p.isProbablePrime(100)) {
            throw new IllegalArgumentException("Input p must be a prime number.");
        }
        // φ(p) = p - 1
        BigInteger phi = p.subtract(BigInteger.ONE);

        // SecureRandom 随机生成器
        SecureRandom random = new SecureRandom();
        BigInteger g;
        do {
            // 随机生成 g ∈ [2, p-1]
            g = new BigInteger(p.bitLength(), random).mod(p.subtract(BigInteger.ONE)).add(BigInteger.TWO);
        } while (!isGenerator(g, p, phi));
        // 返回生成元 g 的字符串形式
        return g.toString(16);
    }

    /**
     * 检查 g 是否为 p 的生成元
     *
     * @param g   候选生成元
     * @param p   素数 p
     * @param phi φ(p) = p - 1
     * @return 如果 g 是生成元，则返回 true；否则返回 false
     */
    private static boolean isGenerator(BigInteger g, BigInteger p, BigInteger phi) {
        for (BigInteger factor : getPrimeFactors(phi)) {
            BigInteger exponent = phi.divide(factor);
            if (g.modPow(exponent, p).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取 n 的所有素因子
     *
     * @param n 输入数
     * @return 素因子列表
     */
    private static BigInteger[] getPrimeFactors(BigInteger n) {
        BigInteger i = BigInteger.TWO;
        BigInteger[] factors = new BigInteger[0];

        while (i.compareTo(n.divide(i)) <= 0) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                factors = addToArray(factors, i);
                while (n.mod(i).equals(BigInteger.ZERO)) {
                    n = n.divide(i);
                }
            }
            i = i.add(BigInteger.ONE);
        }

        if (n.compareTo(BigInteger.ONE) > 0) {
            factors = addToArray(factors, n);
        }

        return factors;
    }

    /**
     * 向数组中添加一个元素
     *
     * @param array 原数组
     * @param value 新值
     * @return 包含新值的新数组
     */
    private static BigInteger[] addToArray(BigInteger[] array, BigInteger value) {
        BigInteger[] newArray = new BigInteger[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }



}
