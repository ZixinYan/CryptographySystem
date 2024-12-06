package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.RC4;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.RC4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rc4")
public class RC4Controller {
    @Autowired
    private RC4Service rc4Service;

    @PostMapping("/encrypt")
    public Result<RC4> encrypt(RC4 rc4) {
        return Result.success(rc4Service.encrypt(rc4));
    }

    @PostMapping("/decrypt")
    public Result<RC4> decrypt(RC4 rc4) {
        return Result.success(rc4Service.decrypt(rc4));
    }

    @PostMapping("/encryptFile")
    public Result<RC4> encryptFile(RC4 rc4) {
        return Result.success(rc4Service.encryptFile(rc4));
    }

    @PostMapping("/decryptFile")
    public Result<RC4> decryptFile(RC4 rc4) {
        return Result.success(rc4Service.decryptFile(rc4));
    }

}
