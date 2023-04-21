package com.ruoyi.music.model.dto;

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
public class UserLoginDTO {
    private String userAuthType;
    private String username;
    private String password;
}

