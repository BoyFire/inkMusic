package com.ruoyi.music.mapper;

import org.apache.ibatis.annotations.Param;

public interface MmsSongLyricMapper {
    /**
     * 添加歌曲歌词
     *
     * @param songId  歌曲id
     * @param lyric 歌词
     * @return 结果
     */
    int insertSongLyric(@Param("songId") Long songId, @Param("lyric") String lyric);

    /**
     * 更新歌曲歌词
     *
     * @param songId  歌曲id
     * @param lyric 歌词
     * @return 结果
     */
    int updateSongLyric(@Param("songId") Long songId, @Param("lyric") String lyric);


    /**
     * 搜索歌曲歌词
     *
     * @param songId  歌曲id
     * @return 结果
     */
    String selectLyricBySongId(@Param("songId") Long songId);

}
