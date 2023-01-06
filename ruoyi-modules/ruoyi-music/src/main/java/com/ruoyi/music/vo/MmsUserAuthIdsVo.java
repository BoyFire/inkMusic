package com.ruoyi.music.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Ink
 * 2022/10/19
 */
@Data
@ToString
@EqualsAndHashCode
public class MmsUserAuthIdsVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private List<Integer> userAuthType;
    private List<Boolean> userAuthTypeDisabled;
}
