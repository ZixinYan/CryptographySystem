package com.zixin.cryptography.controller;

import com.google.j2objc.annotations.AutoreleasePool;
import com.zixin.cryptography.pojo.DH;
import com.zixin.cryptography.pojo.Note;
import com.zixin.cryptography.pojo.PageBean;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.DHService;
import com.zixin.cryptography.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dh")
public class DHController {
    @Autowired
    private DHService dhService;

    @PostMapping("/generatePrivateKey")
    public Result<DH> generatePrivateKey(DH dh) {
        return Result.success(dhService.generatePrivateKey(dh));
    }

    @PostMapping("/generateAKey")
    public Result<DH> generateAKey(@RequestBody @Validated DH dh) {
        return Result.success(dhService.generateAKey(dh));
    }

    @PostMapping("/generateBKey")
    public Result<DH> generateBKey(@RequestBody @Validated DH dh) {
        return Result.success(dhService.generateBKey(dh));
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Validated DH dh){
        dhService.add(dh);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageBean<DH>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer createUser,
            @RequestParam(required = false) Integer targetUser
    ){
        PageBean<DH> pageBean = dhService.list(pageNum, pageSize, createUser, targetUser);
        return Result.success(pageBean);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        dhService.delete(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated DH dh){
        dhService.update(dh);
        return Result.success();
    }
}
