package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.ECC;

public interface ECCService {
    ECC generateKey(ECC ecc);

    ECC encrypt(ECC ecc);

    ECC decrypt(ECC ecc);

    ECC encryptFile(ECC ecc);

    ECC decryptFile(ECC ecc);
}
