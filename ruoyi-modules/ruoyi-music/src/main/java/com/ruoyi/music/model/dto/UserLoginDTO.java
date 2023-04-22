package com.ruoyi.music.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Ink
 * 2022/10/17
 */
@Data
public class UserLoginDTO {
    private String userAuthType;
    @NotBlank(message = "登陆账号不能为空")
    private String username;
    @Pattern(regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).{6,20}$",
            message = "由字母、数字、特殊字符，任意2种组成，6-20位")
    private String password;
}

