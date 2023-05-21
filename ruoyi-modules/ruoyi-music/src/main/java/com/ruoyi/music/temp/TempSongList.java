package com.ruoyi.music.temp;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * api歌单信息对象 temp_song_list
 *
 * @author ruoyi
 * @date 2023-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TempSongList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * api歌单id
     */
    @Excel(name = "api歌单id")
    private String apiSongListId;

    /**
     * 封面
     */
    @Excel(name = "封面")
    private String coverImgUrl;

    /**
     * 歌单名
     */
    @Excel(name = "歌单名")
    private String name;

    /**
     * 歌单简介
     */
    @Excel(name = "歌单简介")
    private String description;

    /**
     * 收藏数
     */
    @Excel(name = "收藏数")
    private Long subscribedCount;

    /**
     * 评论数
     */
    @Excel(name = "评论数")
    private Long commentCount;

    /**
     * 歌曲总数
     */
    @Excel(name = "歌曲总数")
    private Long total;

    @Excel(name="播放次数")
    private Long playCount;
}
