package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 歌单对象 mms_song_list
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@Data
@ToString

public class MmsSongList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 歌单id */
    @Excel(name = "歌单id")
    private Long songListId;

    /** 歌单名 */
    @Excel(name = "歌单名")
    private String songListTitle;

    /** 歌单图像URL */
    @Excel(name = "歌单图像URL")
    private String songListImgUrl;

    /** 歌单创建者id */
    @Excel(name = "歌单创建者id")
    private Long songListCreatorId;

    /** 歌单状态, 1-启用, 0-停用 */
    @Excel(name = "歌单状态, 1-启用, 0-停用")
    private Integer songListStatus;

    /** 是否为精品, 0-否,1-是 */
    @Excel(name = "是否为精品, 0-否,1-是")
    private Integer isFine;

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

    // 歌单创建者姓名
    private String songListCreatorName;

}
