package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.DES;

public interface DESService {
    DES encrypt(DES des);

    DES decrypt(DES des);

    DES encryptFile(DES des);

    DES decryptFile(DES des);
}
