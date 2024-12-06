package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.DES;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.DESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/des")
public class DESController {
    @Autowired
    private DESService desService;

    @PostMapping("/encrypt")
    public Result<DES> encrypt(DES des) {
        return Result.success(desService.encrypt(des));
    }

    @PostMapping("/decrypt")
    public Result<DES> decrypt(DES des) {
        return Result.success(desService.decrypt(des));
    }

    @PostMapping("/encryptFile")
    public Result<DES> encryptFile(DES des) {
        return Result.success(desService.encryptFile(des));
    }

    @PostMapping("/decryptFile")
    public Result<DES> decryptFile(DES des) {
        return Result.success(desService.decryptFile(des));
    }
}
