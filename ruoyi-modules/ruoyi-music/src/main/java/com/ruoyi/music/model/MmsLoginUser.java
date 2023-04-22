package com.ruoyi.music.model;

import com.ruoyi.music.entity.MmsUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class MmsLoginUser implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;
    /**
     * 用户登录类型
     */
    private String userAuthType;

    private MmsUser mmsUser;
}
