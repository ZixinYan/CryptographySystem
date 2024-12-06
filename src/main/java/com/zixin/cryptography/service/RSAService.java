package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.RSA;

public interface RSAService {
    RSA encrypt(RSA rsa);

    RSA decrypt(RSA rsa);

    RSA encryptFile(RSA rsa);

    RSA decryptFile(RSA rsa);

    RSA generateKey(RSA rsa);
}
