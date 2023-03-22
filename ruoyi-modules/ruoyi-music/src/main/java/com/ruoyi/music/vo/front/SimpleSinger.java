package com.ruoyi.music.vo.front;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Ink
 * 2022/10/20
 */
@Data
@NoArgsConstructor
public class SimpleSinger implements Serializable {
    public final static Long serialVersionUID = 1L;
    private Long singerId;
    private String singerName;
}
