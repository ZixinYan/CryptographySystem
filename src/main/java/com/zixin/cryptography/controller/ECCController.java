package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.ECC;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.ECCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecc")
public class ECCController {
    @Autowired
    private ECCService eccService;

    @PostMapping("/generateKey")
    public Result<ECC> generateKey(ECC ecc) {
        return Result.success(eccService.generateKey(ecc));
    }

    @PostMapping("/encrypt")
    public Result<ECC> encrypt(ECC ecc) {
        return Result.success(eccService.encrypt(ecc));
    }

    @PostMapping("/decrypt")
    public Result<ECC> decrypt(ECC ecc) {
        return Result.success(eccService.decrypt(ecc));
    }

    @PostMapping("/encryptFile")
    public Result<ECC> encryptFile(ECC ecc) {
        return Result.success(eccService.encryptFile(ecc));
    }

    @PostMapping("/decryptFile")
    public Result<ECC> decryptFile(ECC ecc) {
        return Result.success(eccService.decryptFile(ecc));
    }


}
