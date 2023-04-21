package com.ruoyi.music.mapper;


import com.ruoyi.music.vo.front.SimpleSinger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MmsSongSingerMapper {
    /**
     * 查询歌手信息 通过歌曲id
     * @param songId 歌曲id
     * @return 结果
     */
    List<SimpleSinger> selectSimpleSingerBySongId(@Param("songId") Long songId);

    /**
     * 增加歌曲歌手联系
     *
     * @param songId 歌曲id
     * @param singerIds 歌手ids
     * @return 结果
     */
    int insertSongSinger(@Param("songId") Long songId, @Param("singerIds") List<Long> singerIds);

    /**
     * 根据歌曲id进行删除
     *
     * @param songId 歌曲id
     * @return 结果
     */
    int deleteSongSingerBySongId(@Param("songId") Long songId);

    /**
     * 毗连增加歌曲歌手联系
     *
     * @param songId  歌曲id
     * @param singers SimpleSinger类 列表
     * @return 结果
     */
    int insertSongSingers(@Param("songId") Long songId, @Param("singers") List<SimpleSinger> singers);
}
