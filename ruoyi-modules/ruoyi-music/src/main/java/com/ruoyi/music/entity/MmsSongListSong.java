package com.ruoyi.music.entity;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 歌单歌曲对象 mms_song_list_song
 *
 * @author ruoyi
 * @date 2023-01-05
 */
@Data
public class MmsSongListSong extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 歌曲id
     */
    @Excel(name = "歌曲id")
    private Long songId;

    /**
     * 歌单id
     */
    @Excel(name = "歌单id")
    private Long songListId;

    /**
     * 歌单名
     */
    private String songListName;

    private MmsSong mmsSong;
    private MmsSongList mmsSongList;


}
