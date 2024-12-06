package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.FindPasswordInfo;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.VerificationCodeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private VerificationCodeService codeService;

    @ApiOperation("请求权限码接口")
    @PostMapping("/getCode")
    public Result getRequestPermissionCode(@RequestBody FindPasswordInfo info) {
        return codeService.getRequestPermissionCode(info);
    }
    @ApiOperation("发送邮箱验证码接口")
    @PostMapping("/sendEmailCode")
    public Result sendEmailCode(@RequestBody FindPasswordInfo info) {
        return codeService.sendEmailCode(info);
    }
}