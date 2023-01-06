package com.ruoyi.music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * Ink
 * 2022/10/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 用户头像地址
     */
    private String userImgUrl;

    /**
     * 用户性别;1-男, 2-女, 0-未知
     */
    private Long userGender;

    /**
     * 用户认证方式
     */
    private Integer userAuthType;

    /**
     * 用户认证账号
     */
    private String userAuthLogin;

    /**
     * 用户认证密码
     */
    private String userAuthPassword;

    /**
     * 更新者
     */
    private String editBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date editTime;

    /**
     * 乐观锁
     */
    private Long revision;

    /**
     * 是否删除;0-未删除,1-删除
     */
    private Integer isDel;


}
