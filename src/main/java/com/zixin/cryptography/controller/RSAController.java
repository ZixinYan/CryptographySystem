package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.ECC;
import com.zixin.cryptography.pojo.RSA;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.ECCService;
import com.zixin.cryptography.service.RSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rsa")
public class RSAController {

    @Autowired
    private RSAService rsaService;

    @PostMapping("/generateKey")
    public Result<RSA> generateKey(RSA rsa) {
        return Result.success(rsaService.generateKey(rsa));
    }

    @PostMapping("/encrypt")
    public Result<RSA> encrypt(RSA rsa) {
        return Result.success(rsaService.encrypt(rsa));
    }

    @PostMapping("/decrypt")
    public Result<RSA> decrypt(RSA rsa) {
        return Result.success(rsaService.decrypt(rsa));
    }

    @PostMapping("/encryptFile")
    public Result<RSA> encryptFile(RSA rsa) {
        return Result.success(rsaService.encryptFile(rsa));
    }

    @PostMapping("/decryptFile")
    public Result<RSA> decryptFile(RSA rsa) {
        return Result.success(rsaService.decryptFile(rsa));
    }

}
