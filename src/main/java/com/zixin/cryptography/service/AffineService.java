package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Affine;

public interface AffineService {
    Affine encrypt(Affine affine);

    Affine decrypt(Affine affine);

    Affine encryptFile(Affine affine);

    Affine decryptFile(Affine affine);
}
