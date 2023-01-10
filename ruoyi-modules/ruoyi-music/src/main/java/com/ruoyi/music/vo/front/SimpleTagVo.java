package com.ruoyi.music.vo.front;

import lombok.Data;

@Data
public class SimpleTagVo {
    private Long tagId;
    private String tagName;
    private Long parentId;
}
