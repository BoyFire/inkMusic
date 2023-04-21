package com.ruoyi.music.mapper;

import org.apache.ibatis.annotations.Param;

public interface MmsSingerMvMapper {
    /**
     * 新增歌手,mv关联
     *
     * @param singerId  歌手id
     * @param mvId mvId
     * @return 结果
     */
    int insertSingerMv(@Param("singerId") Long singerId, @Param("mvId") Long mvId);
}
