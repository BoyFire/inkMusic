package com.ruoyi.music.vo.front;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Ink
 * 2022/10/24
 */
@Data
@ToString
public class SimpleUserVo implements Serializable {
    public final static Long serialVersionUID = 1L;
    private Long userId;
    private String nickname;
}
