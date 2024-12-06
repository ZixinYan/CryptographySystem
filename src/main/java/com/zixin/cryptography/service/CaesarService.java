package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Caesar;

public interface CaesarService {
    Caesar encrypt(Caesar caesar);

    Caesar decrypt(Caesar caesar);

    Caesar encryptFile(Caesar caesar);

    Caesar decryptFile(Caesar caesar);
}
