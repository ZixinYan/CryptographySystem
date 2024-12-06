package com.zixin.cryptography.pojo;

import lombok.Data;

@Data
public class Autokey {
    private Integer id;
    private String key;
    private String plaintext;
    private String plainFile;
    private String cipherFile;
    private String ciphertext;
    private Integer createUser;
    private String sourceIP;
    private String targetIP;
    private String port;
}
