package com.ruoyi.music.vo.front;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Ink
 * 2022/10/20
 */
@Data
@ToString
@EqualsAndHashCode
public class SimpleAlbum implements Serializable {
    public final static Long serialVersionUID = 1L;
    private Long albumId;
    private String albumName;
}
