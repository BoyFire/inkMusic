package com.ruoyi.music.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MmsSongTag implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 歌曲id*/
    private Long songId;

    /** 标签id*/
    private Long tagId;

    private MmsTag mmsTag;
    private MmsSong mmsSong;

}
