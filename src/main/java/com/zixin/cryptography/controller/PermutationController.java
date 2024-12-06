package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Permutation;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.PermutationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permutation")
public class PermutationController {
    @Autowired
    private PermutationService permutationService;

    @RequestMapping("/encrypt")
    public Result<Permutation> encrypt(Permutation permutation) {
        return Result.success(permutationService.encrypt(permutation));
    }

    @RequestMapping("/decrypt")
    public Result<Permutation> decrypt(Permutation permutation) {
        return Result.success(permutationService.decrypt(permutation));
    }

    @RequestMapping("/encryptFile")
    public Result<Permutation> encryptFile(Permutation permutation) {
        return Result.success(permutationService.encryptFile(permutation));
    }

    @RequestMapping("/decryptFile")
    public Result<Permutation> decryptFile(Permutation permutation) {
        return Result.success(permutationService.decryptFile(permutation));
    }
}
