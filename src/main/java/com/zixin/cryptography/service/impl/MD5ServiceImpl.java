package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.pojo.MD5;
import com.zixin.cryptography.service.MD5Service;
import org.springframework.stereotype.Service;

@Service
public class MD5ServiceImpl implements MD5Service {
    @Override
    public MD5 encrypt(MD5 md5) {
        return md5;
    }

    @Override
    public MD5 encryptFile(MD5 md5) {
        return md5;
    }
}
