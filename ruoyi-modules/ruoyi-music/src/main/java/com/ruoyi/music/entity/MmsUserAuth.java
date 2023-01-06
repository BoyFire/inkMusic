package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 用户认证对象 mms_user_auth
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
public class MmsUserAuth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户认证方式;0-账号,1-邮箱, 2-手机 */
    @Excel(name = "用户认证方式;0-账号,1-邮箱, 2-手机")
    private Integer userAuthType;

    /** 用户认证账号 */
    @Excel(name = "用户认证账号")
    private String userAuthLogin;

    /** 用户认证密码;存放用户的密码或者token, 建议修改非第三方登陆密码时, 同时同步所有密码 */
    @Excel(name = "用户认证密码;存放用户的密码或者token, 建议修改非第三方登陆密码时, 同时同步所有密码")
    private String userAuthPassword;

    /** 用户认证状态, 1-启用,0-停用 */
    @Excel(name = "用户认证状态, 1-启用,0-停用")
    private Integer userAuthStatus;

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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserAuthType(Integer userAuthType)
    {
        this.userAuthType = userAuthType;
    }

    public Integer getUserAuthType()
    {
        return userAuthType;
    }
    public void setUserAuthLogin(String userAuthLogin)
    {
        this.userAuthLogin = userAuthLogin;
    }

    public String getUserAuthLogin()
    {
        return userAuthLogin;
    }
    public void setUserAuthPassword(String userAuthPassword)
    {
        this.userAuthPassword = userAuthPassword;
    }

    public String getUserAuthPassword()
    {
        return userAuthPassword;
    }
    public void setUserAuthStatus(Integer userAuthStatus)
    {
        this.userAuthStatus = userAuthStatus;
    }

    public Integer getUserAuthStatus()
    {
        return userAuthStatus;
    }
    public void setEditBy(String editBy)
    {
        this.editBy = editBy;
    }

    public String getEditBy()
    {
        return editBy;
    }
    public void setEditTime(Date editTime)
    {
        this.editTime = editTime;
    }

    public Date getEditTime()
    {
        return editTime;
    }
    public void setRevision(Integer revision)
    {
        this.revision = revision;
    }

    public Integer getRevision()
    {
        return revision;
    }
    public void setIsDel(Integer isDel)
    {
        this.isDel = isDel;
    }

    public Integer getIsDel()
    {
        return isDel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("userAuthType", getUserAuthType())
                .append("userAuthLogin", getUserAuthLogin())
                .append("userAuthPassword", getUserAuthPassword())
                .append("userAuthStatus", getUserAuthStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("editBy", getEditBy())
                .append("editTime", getEditTime())
                .append("revision", getRevision())
                .append("isDel", getIsDel())
                .toString();
    }
}
