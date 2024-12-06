package com.zixin.cryptography.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Permutation {
    private Integer id;
    private String key;
    private String plaintext;
    private String plainFile;
    private String cipherFile;
    private String ciphertext;
    private Integer createUser;
    @NotEmpty(message = "源IP不能为空")
    private String sourceIP;
    @NotEmpty(message = "目标IP不能为空")
    private String targetIP;
    @Pattern(regexp = "^[\\s\\S]{1,5}$",message = "端口长度必须在1-5之间")
    private String port;
}
