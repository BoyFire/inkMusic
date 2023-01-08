package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标签对象 mms_tag
 * 
 * @author ruoyi
 * @date 2022-10-25
 */
@Data
@ToString
public class MmsTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 标签id */
    @Excel(name = "标签id")
    private Long tagId;

    /** 标签名 */
    @Excel(name = "标签名")
    private String tagName;

    /** 标签上级id;0表示根目录 */
    @Excel(name = "标签上级id;0表示根目录")
    private Long tagParentsId;

    /** 标签状态, 0-停用,1-启用 */
    @Excel(name = "标签状态, 0-停用,1-启用")
    private Integer tagStatus;

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

    // 子标签
    private List<MmsTag> children = new ArrayList<>();
}
