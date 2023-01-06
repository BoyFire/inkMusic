package com.ruoyi.music.entity;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 歌单歌曲对象 mms_song_list_song
 * 
 * @author ruoyi
 * @date 2023-01-05
 */
public class MmsSongListSong extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 歌曲id */
    @Excel(name = "歌曲id")
    private Long songId;

    /** 歌单id */
    @Excel(name = "歌单id")
    private Long songListId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSongId(Long songId) 
    {
        this.songId = songId;
    }

    public Long getSongId() 
    {
        return songId;
    }
    public void setSongListId(Long songListId) 
    {
        this.songListId = songListId;
    }

    public Long getSongListId() 
    {
        return songListId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("songId", getSongId())
            .append("songListId", getSongListId())
            .toString();
    }
}
