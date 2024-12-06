package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Permutation;

public interface PermutationService {
    Permutation encrypt(Permutation permutation);

    Permutation decrypt(Permutation permutation);

    Permutation encryptFile(Permutation permutation);

    Permutation decryptFile(Permutation permutation);
}
