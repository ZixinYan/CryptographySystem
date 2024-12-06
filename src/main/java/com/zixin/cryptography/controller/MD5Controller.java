package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.MD5;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.MD5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/md5")
public class MD5Controller {
    @Autowired
    private MD5Service md5Service;

    @RequestMapping("/encrypt")
    public Result<MD5> encrypt(String plaintext) {
        MD5 md5 = new MD5();
        md5.setPlaintext(plaintext);
        return Result.success(md5Service.encrypt(md5));
    }

    @RequestMapping("/encryptFile")
    public Result<MD5> encryptFile(String fileUrl) {
        MD5 md5 = new MD5();
        md5.setPlainFile(fileUrl);
        return Result.success(md5Service.encryptFile(md5));
    }


}
