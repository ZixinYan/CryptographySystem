package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Caesar;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.CaesarService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caesar")
public class CaesarController {
    @Autowired
    private CaesarService caesarService;

    @PostMapping("/encrypt")
    public Result<Caesar> encrypt(Caesar caesar) {
        return Result.success(caesarService.encrypt(caesar));
    }

    @PostMapping("/decrypt")
    public Result<Caesar> decrypt(Caesar caesar) {
        return Result.success(caesarService.decrypt(caesar));
    }

    @PostMapping("/encryptFile")
    public Result<Caesar> encryptFile(Caesar caesar) {
        return Result.success(caesarService.encryptFile(caesar));
    }

    @PostMapping("/decryptFile")
    public Result<Caesar> decryptFile(Caesar caesar) {
        return Result.success(caesarService.decryptFile(caesar));
    }
}
