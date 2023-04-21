package com.ruoyi.music.vo.front;

import lombok.Data;

@Data
public class SongParamsVo {
    private Long songId;
    private String songName;
    private Long singerId;
    private String singerName;
    private Long albumId;
    private String albumName;
    private Integer songStatus;

}
