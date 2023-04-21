package com.ruoyi.music.entity;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
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

    /** 歌曲名 */
    @Excel(name = "歌曲名")
    private String songName;

    /** 歌曲时长 */
    @Excel(name = "歌曲时长")
    private Long songDuration;

    /** 歌曲URL */
    @Excel(name = "歌曲URL")
    private String songUrl;

    /** 歌曲图像URL */
    @Excel(name = "歌曲图像URL")
    private String songImgUrl;

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

    // 歌手信息
    private List<SimpleSinger> singers;

    //专辑信息
    private List<SimpleAlbum> albums;

    //歌曲别名
    private List<String> alias;

    // 标签列表
    private List<String> tagNames;


}
