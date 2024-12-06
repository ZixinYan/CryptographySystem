package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Vigenere;

public interface VigenereService {
    Vigenere encrypt(Vigenere vigenere);

    Vigenere decrypt(Vigenere vigenere);

    Vigenere encryptFile(Vigenere vigenere);

    Vigenere decryptFile(Vigenere vigenere);
}
