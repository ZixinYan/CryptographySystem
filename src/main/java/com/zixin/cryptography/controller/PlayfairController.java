package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Playfair;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.PlayfairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playfair")
public class PlayfairController {
    @Autowired
    private PlayfairService playfairService;

    @PostMapping("/encrypt")
    public Result<Playfair> encrypt(Playfair playfair) {
        return Result.success(playfairService.encrypt(playfair));
    }

    @PostMapping("/decrypt")
    public Result<Playfair> decrypt(Playfair playfair) {
        return Result.success(playfairService.decrypt(playfair));
    }

    @PostMapping("/encryptFile")
    public Result<Playfair> encryptFile(Playfair playfair) {
        return Result.success(playfairService.encryptFile(playfair));
    }

    @PostMapping("/decryptFile")
    public Result<Playfair> decryptFile(Playfair playfair) {
        return Result.success(playfairService.decryptFile(playfair));
    }
}
