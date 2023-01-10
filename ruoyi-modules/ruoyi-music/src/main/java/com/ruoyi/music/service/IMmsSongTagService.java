package com.ruoyi.music.service;

import com.ruoyi.music.vo.front.AddSongTagVo;

public interface IMmsSongTagService {

    /**
     * 根据标签id 删除歌曲标签连接
     * @param tagId  标签id
     * @return 结果
     */
    int deleteSongTagByTagId(Long tagId);


    /**
     * 添加songTag
     *
     * @param addSongTagVo addSongTagVo
     * @param songId
     * @return 结果
     */
    int insertSongTags(AddSongTagVo addSongTagVo, Long songId);
}
