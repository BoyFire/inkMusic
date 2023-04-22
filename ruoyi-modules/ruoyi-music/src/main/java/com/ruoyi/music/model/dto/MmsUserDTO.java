package com.ruoyi.music.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MmsUserDTO implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户头像
     */
    private String userImgUrl;

    /**
     * 用户性别;1-男, 2-女, 0-未知
     */
    private Integer userGender;
}
