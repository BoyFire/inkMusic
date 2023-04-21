package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.vo.front.AlbumParamsVo;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MmsSingerAlbumMapper {

    /**
     * 添加歌手歌单联系
     * @param albumId 歌单id
     * @param singerIds 歌手ids
     * @return 结果
     */
    int insertSingerAlbum(@Param("albumId") Long albumId, @Param("list") List<Long> singerIds);

    /**
     * 根据专辑id 查询歌手姓名
     * @param albumId  专辑id
     * @return 结果
     */
    List<String> selectSingerNamesByAlbumId(@Param("albumId") Long albumId);

    /**
     * 根骨歌手id 查询专辑
     * @param singerId  歌手id
     * @return 结果
     */
    List<MmsAlbum> selectAlbumsBySingerId(@Param("singerId") Long singerId);

    /**
     * 根据专辑id 查询歌手
     * @param albumId  专辑id
     * @return 结果
     */
    List<SimpleSinger> selectSimpleSingerByAlbumId(@Param("albumId") Long albumId);


    /**
     * 查询专辑
     *
     * @param albumParamsVo 专辑
     * @return 结果
     */
    List<MmsAlbum> selectMmsAlbumList(AlbumParamsVo albumParamsVo);

    /**
     * 根据专辑id 删除歌手专辑
     *
     * @param AlbumId 专辑id
     * @return 结果
     */
    int deleteMmsAlbumByAlbumId( Long[] AlbumId);

    /**
     * 根据专辑id, 歌手id 删除联系表
     *
     * @param singerId 歌手id
     * @param AlbumId 专辑id;
     * @return 结果
     */
    int deleteMmsAlbumByAlbumIdSingerId(@Param("singerId") Long singerId, @Param("AlbumId") Long AlbumId);

    /**
     * 根据 简单歌手列表查询 专辑s
     *
     * @param singers SimpleSinger类
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumsBySingers(@Param("singers") List<SimpleSinger> singers);
}
