package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.MD5;

public interface MD5Service {
    MD5 encrypt(MD5 md5);

    MD5 encryptFile(MD5 md5);
}
