package com.ruoyi.music.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户对象 mms_user
 *
 * @author ruoyi
 * @date 2022-10-19
 */
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

    /** 用户状态;0-启用,1-停用 */
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
    public void setUserNickname(String userNickname)
    {
        this.userNickname = userNickname;
    }

    public String getUserNickname()
    {
        return userNickname;
    }
    public void setUserImgUrl(String userImgUrl)
    {
        this.userImgUrl = userImgUrl;
    }

    public String getUserImgUrl()
    {
        return userImgUrl;
    }
    public void setUserGender(Integer userGender)
    {
        this.userGender = userGender;
    }

    public Integer getUserGender()
    {
        return userGender;
    }
    public void setUserStatus(Integer userStatus)
    {
        this.userStatus = userStatus;
    }

    public Integer getUserStatus()
    {
        return userStatus;
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
                .append("userNickname", getUserNickname())
                .append("userImgUrl", getUserImgUrl())
                .append("userGender", getUserGender())
                .append("userStatus", getUserStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("editBy", getEditBy())
                .append("editTime", getEditTime())
                .append("revision", getRevision())
                .append("isDel", getIsDel())
                .toString();
    }
}
