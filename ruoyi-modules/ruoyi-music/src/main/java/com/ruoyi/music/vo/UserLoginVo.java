package com.ruoyi.music.vo;

import lombok.*;

/**
 * Ink
 * 2022/10/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserLoginVo {
    private Integer userAuthType;
    private String username;
    private String password;
}

