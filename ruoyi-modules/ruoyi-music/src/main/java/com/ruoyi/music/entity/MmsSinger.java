package com.ruoyi.music.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 歌手对象 mms_singer
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
@Data
@ToString
@EqualsAndHashCode
public class MmsSinger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 歌手id */
    @Excel(name = "歌手id")
    private Long singerId;

    /** 歌手姓名 */
    @Excel(name = "歌手姓名")
    private String singerName;

    /** 歌手头像URL */
    @Excel(name = "歌手头像URL")
    private String singerImgUrl;

    /** 歌手详情 */
    @Excel(name = "歌手详情")
    private String singerDetail;

    /** 歌手类型;1-男, 2-女, 3-乐团, 0-未知 */
    @Excel(name = "歌手类型;1-男, 2-女, 3-乐团, 0-未知")
    private Integer singerType;

    /** 歌手状态;0-停用, 1-启用 */
    @Excel(name = "歌手状态;0-停用, 1-启用")
    private Integer singerStatus;

    /** 歌手所属国家;1-华语, 2-欧美,3-日本, 4-韩国, 0-其他 */
    @Excel(name = "歌手所属国家;1-华语, 2-欧美,3-日本, 4-韩国, 0-其他")
    private Integer singerCountry;

    /** 更新者 */
    @Excel(name = "更新者")
    private String editBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date editTime;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Long revision;

    /** 是否删除;0-未删除,1-删除 */
    @Excel(name = "是否删除;0-未删除,1-删除")
    private Integer isDel;

}
