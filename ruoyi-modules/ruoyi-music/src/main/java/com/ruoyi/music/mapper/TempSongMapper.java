package com.ruoyi.music.mapper;

import com.ruoyi.music.temp.ApiSong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TempSongMapper {
    /**
     * 添加 api歌曲id 与 歌曲id 对应表
     * @param apiId apiId
     * @param songId 专辑id
     */
    void insertSongId(@Param("apiId") Long apiId, @Param("songId") Long songId);

    List<ApiSong> selectApiSong(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询是否已有当前歌曲信息
     *
     * @param apiId apiId
     * @return 结果
     */
    int selectSongIdCountsByApi(@Param("apiId") Long apiId);

    Long selectSongIdByApi(@Param("apiId") Long apiId);
}
