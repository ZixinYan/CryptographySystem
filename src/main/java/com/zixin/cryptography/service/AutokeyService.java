package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Autokey;

public interface AutokeyService {
    Autokey encrypt(Autokey autokey);

    Autokey decrypt(Autokey autokey);

    Autokey encryptFile(Autokey autokey);

    Autokey decryptFile(Autokey autokey);
}
