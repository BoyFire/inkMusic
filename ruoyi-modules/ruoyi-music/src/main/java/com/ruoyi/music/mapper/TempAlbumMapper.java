package com.ruoyi.music.mapper;

import com.ruoyi.music.Temp.ApiAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TempAlbumMapper {

    /**
     * 添加 api专辑id 与 专辑id 对应表
     * @param apiId apiId
     * @param albumId 专辑id
     */
    int insertAlbumId(@Param("apiId") Long apiId, @Param("albumId") Long albumId);

    List<ApiAlbum> selectApiAlbum(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
