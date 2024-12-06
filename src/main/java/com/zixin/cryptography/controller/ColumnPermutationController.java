package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.ColumnPermutation;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.ColumnPermutationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/columnPermutation")
public class ColumnPermutationController {
    @Autowired
    private ColumnPermutationService columnPermutationService;

    @PostMapping("/encrypt")
    public Result<ColumnPermutation> encrypt(ColumnPermutation columnPermutation) {
        return Result.success(columnPermutationService.encrypt(columnPermutation));
    }

    @PostMapping("/decrypt")
    public Result<ColumnPermutation> decrypt(ColumnPermutation columnPermutation) {
        return Result.success(columnPermutationService.decrypt(columnPermutation));
    }

    @PostMapping("/encryptFile")
    public Result<ColumnPermutation> encryptFile(ColumnPermutation columnPermutation) {
        return Result.success(columnPermutationService.encryptFile(columnPermutation));
    }

    @PostMapping("/decryptFile")
    public Result<ColumnPermutation> decryptFile(ColumnPermutation columnPermutation) {
        return Result.success(columnPermutationService.decryptFile(columnPermutation));
    }
}
