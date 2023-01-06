package com.ruoyi.music.vo.front;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Ink
 * 2022/10/26
 */
@Data
@ToString
public class SimpleSongVo implements Serializable {
    public final static Long serialVersionUID = 1L;
    private String songName;
    private Long songId;
}
