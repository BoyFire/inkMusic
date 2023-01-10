package com.ruoyi.music.service.impl;

import com.ruoyi.music.mapper.MmsSongTagMapper;
import com.ruoyi.music.service.IMmsSongTagService;
import com.ruoyi.music.vo.front.AddSongTagVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MmsSongTagServiceImpl implements IMmsSongTagService {
    @Resource
    private MmsSongTagMapper mmsSongTagMapper;

    @Override
    public int deleteSongTagByTagId(Long tagId) {
        return mmsSongTagMapper.deleteSongTagByTagId(tagId);
    }

    @Override
    public int insertSongTags(AddSongTagVo addSongTagVo, Long songId) {
        List<Long> tagIds = addSongTagVo.getTagIds();
        return mmsSongTagMapper.insertSongTag(tagIds, songId);
    }
}
