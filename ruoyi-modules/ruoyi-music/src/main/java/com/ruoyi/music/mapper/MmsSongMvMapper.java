package com.ruoyi.music.mapper;

import org.apache.ibatis.annotations.Param;

public interface MmsSongMvMapper {
    /**
     * 添加歌曲 mv 联系
     *
     * @param songId  歌曲id
     * @param movieId mvid
     * @return 结果
     */
    int insertSongMv(@Param("songId") Long songId, @Param("movieId") Long movieId);
}
