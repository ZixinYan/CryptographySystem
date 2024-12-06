package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Affine;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.AffineService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/affine")
public class AffineController {
    @Autowired
    private AffineService affineService;

    @PostMapping("/encrypt")
    public Result<Affine> encrypt(@ModelAttribute Affine affine) {
        return Result.success(affineService.encrypt(affine));
    }

    @PostMapping("/decrypt")
    public Result<Affine> decrypt(@ModelAttribute Affine affine) {
        return Result.success(affineService.decrypt(affine));
    }

    @PostMapping("/encryptFile")
    public Result<Affine> encryptFile(@ModelAttribute Affine affine) {
        return Result.success(affineService.encryptFile(affine));
    }

    @PostMapping("/decryptFile")
    public Result<Affine> decryptFile(@ModelAttribute Affine affine) {
        return Result.success(affineService.decryptFile(affine));
    }
}
