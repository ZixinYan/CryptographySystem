package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Playfair;

public interface PlayfairService {
    Playfair encrypt(Playfair playfair);

    Playfair decrypt(Playfair playfair);

    Playfair encryptFile(Playfair playfair);

    Playfair decryptFile(Playfair playfair);
}
