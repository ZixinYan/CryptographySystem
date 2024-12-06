package com.zixin.cryptography.pojo;

import lombok.Data;

@Data
public class DH {
    private Integer id;
    private String p;
    private String g;
    private String privateKey;
    private String a;
    private String b;
    private String key;
    private Integer createUser;
    private Integer targetUser;

}
