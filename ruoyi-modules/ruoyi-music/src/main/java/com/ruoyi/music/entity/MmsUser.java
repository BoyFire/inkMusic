package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象 mms_user
 *
 * @author ruoyi
 * @date 2022-10-19
 */
@Data
public class MmsUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String userNickname;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String userImgUrl;

    /** 用户性别;1-男, 2-女, 0-未知 */
    @Excel(name = "用户性别;1-男, 2-女, 0-未知")
    private Integer userGender;

    /** 用户状态;1-启用,0-停用 */
    @Excel(name = "用户状态;1-启用,0-停用")
    private Integer userStatus;

    /** 更新者 */
    @Excel(name = "更新者")
    private String editBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date editTime;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Integer revision;

    /** 是否删除;0-未删除,1-删除 */
    @Excel(name = "是否删除;0-未删除,1-删除")
    private Integer isDel;
}
