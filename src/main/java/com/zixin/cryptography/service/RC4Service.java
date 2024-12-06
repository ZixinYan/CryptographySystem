package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.RC4;

public interface RC4Service {
    RC4 encrypt(RC4 rc4);

    RC4 decrypt(RC4 rc4);

    RC4 encryptFile(RC4 rc4);

    RC4 decryptFile(RC4 rc4);
}
