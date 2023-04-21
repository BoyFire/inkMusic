package com.ruoyi.music.mapper;

import com.ruoyi.music.vo.front.SimpleAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MmsSongAlbumMapper {
    /**
     * 根据歌曲id 查询所属专辑
     * @param songId  歌曲id
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumBySongId(@Param("songId") Long songId);


    /**
     * 添加歌曲专辑联系
     *
     * @param songIds  歌曲id列表
     * @param albumId 专辑id
     * @return 结果
     */
    int insertSongsAlbum(@Param("songIds") List<Long> songIds, @Param("albumId") Long albumId);


    /**
     * 删除专辑中所有歌曲
     *
     * @param albumId 专辑id
     * @return 结果
     */
    int deleteSongAlbumByAlbumId(@Param("albumId") Long albumId);


    /**
     * 删除专辑中的某个歌曲
     *
     * @param songId 歌曲id
     * @return 结果
     */
    int deleteSongAlbumBySongId(@Param("songId") Long songId);

    /**
     * 新增歌曲专辑
     *
     * @param songId 歌曲id
     * @param albums SimpleAlbum类 列表
     * @return
     */
    int insertSongAlbums(@Param("songId") Long songId, @Param("albums") List<SimpleAlbum> albums);
}
