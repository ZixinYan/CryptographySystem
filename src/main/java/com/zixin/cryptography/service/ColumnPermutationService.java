package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.ColumnPermutation;

public interface ColumnPermutationService {
    ColumnPermutation encrypt(ColumnPermutation columnPermutation);

    ColumnPermutation decrypt(ColumnPermutation columnPermutation);

    ColumnPermutation encryptFile(ColumnPermutation columnPermutation);

    ColumnPermutation decryptFile(ColumnPermutation columnPermutation);
}
