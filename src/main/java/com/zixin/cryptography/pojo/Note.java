package com.zixin.cryptography.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
@Data
public class Note {
    private Integer id;//主键ID
    @NotEmpty(message = "文章标题不能为空")
    @Pattern(regexp = "^[\\s\\S]{1,10}$",message = "主题长度必须在1-10之间")
    private String title;//文章标题
    @NotEmpty(message = "文章内容不能为空")
    private String content;//文章内容
    @NotEmpty(message = "加密算法不能为空")
    private String algorithm;//加密算法
    private String signature;//签名
    @URL(message = "URL格式不正确")
    private String fileUrl;//文件地址
    private Integer createUser;//sender
    private Integer targetUser;//receiver
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
