package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.pojo.Vigenere;
import com.zixin.cryptography.service.VigenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vigenere")
public class VigenereController {
    @Autowired
    private VigenereService vigenereService;

    @PostMapping("/encrypt")
    public Result<Vigenere> encrypt(Vigenere vigenere) {
        return Result.success(vigenereService.encrypt(vigenere));
    }

    @PostMapping("/decrypt")
    public Result<Vigenere> decrypt(Vigenere vigenere) {
        return Result.success(vigenereService.decrypt(vigenere));
    }

    @PostMapping("/encryptFile")
    public Result<Vigenere> encryptFile(Vigenere vigenere) {
        return Result.success(vigenereService.encryptFile(vigenere));
    }

    @PostMapping("/decryptFile")
    public Result<Vigenere> decryptFile(Vigenere vigenere) {
        return Result.success(vigenereService.decryptFile(vigenere));
    }
}
