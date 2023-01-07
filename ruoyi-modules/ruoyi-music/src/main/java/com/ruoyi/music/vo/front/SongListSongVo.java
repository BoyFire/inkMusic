package com.ruoyi.music.vo.front;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongListSongVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 歌曲id */
    private Long songId;

    /** 歌单id */
    private Long songListId;

    /** 歌曲名 */
    private String songName;

    /** 歌曲URL */
    private String songUrl;

    /** 歌曲图像URL */
    private String songImgUrl;

    /** 是否删除;0-未删除,1-删除 */
    private Integer isDel;
}
