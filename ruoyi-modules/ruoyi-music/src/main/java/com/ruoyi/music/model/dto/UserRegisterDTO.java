package com.ruoyi.music.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Length(max = 20,min = 4,message = "昵称最长为20位,最短为4位")
    private String userNickname;

    @NotBlank(message = "登陆账号不能为空")
    private String userLogin;

    @Pattern(regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).{6,20}$",message = "由字母、数字、特殊字符，任意2种组成，6-20位")
    private String pwd;

    private String checkPwd;
}
