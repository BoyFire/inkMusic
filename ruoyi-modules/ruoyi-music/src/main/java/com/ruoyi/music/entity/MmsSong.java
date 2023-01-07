package com.ruoyi.music.entity;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 歌曲对象 mms_song
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
@Data
@ToString
@EqualsAndHashCode
public class MmsSong extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 歌曲id */
    @Excel(name = "歌曲id")
    private Long songId;

    /** 歌手id */
    @Excel(name = "歌手id")
    private Long singerId;

    /** 歌曲名 */
    @Excel(name = "歌曲名")
    private String songName;

    /** 歌曲URL */
    @Excel(name = "歌曲URL")
    private String songUrl;

    /** 歌曲图像URL */
    @Excel(name = "歌曲图像URL")
    private String songImgUrl;

    /** 歌词url地址 */
    @Excel(name = "歌词url")
    private String songLyric;

    /** 歌曲状态, 1-启用,0-停用 */
    @Excel(name = "歌曲状态, 1-启用,0-停用")
    private Integer songStatus;

    /** 更新者 */
    private String editBy;

    /** 更新时间 */
    private Date editTime;

    /** 乐观锁 */
    private Integer revision;

    /** 是否删除;0-未删除,1-删除 */
    private Integer isDel;

    // 歌手名
    private String singerName;

    //专辑名
    private String albumName;

    //专辑id
    private Long albumId;

    //TODO 给音乐增加标签
    private List<MmsTag> tags;

}
