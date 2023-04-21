package com.ruoyi.music.mapper;

import com.ruoyi.music.Temp.ApiSinger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TempSingerMapper {

    /**
     * 添加 api歌手id 与 歌手id 对应表
     * @param apiId apiId
     * @param singerId 歌手id
     */
    void insertSingerId(@Param("apiId") Long apiId, @Param("singerId") Long singerId);

    List<ApiSinger> selectApiSinger(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据apiId 搜索歌手id
     * @param apiId  apiId
     * @return 结果
     */
    Long selectSingerIdByApiId(@Param("apiId") Long apiId);

    /**
     * 批量查询 歌手id
     * @param apiIds  api歌手id
     * @return 结果
     */
    List<Long> selectSingerIdByApiIds(@Param("apiIds") List<Long> apiIds);


}
