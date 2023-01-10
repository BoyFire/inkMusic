package com.ruoyi.music.vo.front;

import lombok.Data;

import java.util.List;

@Data
public class AddSongTagVo {
    private List<Long> tagIds;
    private Long tagParentsId;
}
