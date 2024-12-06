package com.zixin.cryptography.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ECC {
    private Integer id;

    private String publicKey;
    private String privateKey;
    private String plaintext;
    private String plainFile;
    private String ciphertext;
    private String signature;
    private Integer createUser;
}
