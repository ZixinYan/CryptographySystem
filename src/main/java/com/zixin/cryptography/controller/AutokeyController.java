package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Autokey;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.AutokeyService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autokey")
public class AutokeyController {
    @Autowired
    private AutokeyService autokeyService;

    @PostMapping("/encrypt")
    public Result<Autokey> encrypt(Autokey autokey) {
        return Result.success(autokeyService.encrypt(autokey));
    }

    @PostMapping("/decrypt")
    public Result<Autokey> decrypt(Autokey autokey) {
        return Result.success(autokeyService.decrypt(autokey));
    }

    @PostMapping("/encryptFile")
    public Result<Autokey> encryptFile(Autokey autokey) {
        return Result.success(autokeyService.encryptFile(autokey));
    }

    @PostMapping("/decryptFile")
    public Result<Autokey> decryptFile(Autokey autokey) {
        return Result.success(autokeyService.decryptFile(autokey));
    }

}
