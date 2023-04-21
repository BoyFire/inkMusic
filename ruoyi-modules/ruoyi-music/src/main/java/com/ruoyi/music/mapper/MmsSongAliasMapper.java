package com.ruoyi.music.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MmsSongAliasMapper {
    /**
     * 根据歌曲id 查询歌曲别名
     *
     * @param songId 歌曲id
     * @return 结果
     */
    List<String> selectSongAliasBySongId(@Param("songId") Long songId);

    /**
     * 添加歌曲别名
     *
     * @param songId 歌曲id
     * @param alias 别名列表
     * @return 结果
     */
    int insertSongAlias(@Param("songId") Long songId, @Param("alias") List<String> alias);

    /**
     * 删除别名列表
     *
     * @param songId 歌曲id
     * @return 结果
     */
    int deleteSongAlias(@Param("songId") Long songId);
}
