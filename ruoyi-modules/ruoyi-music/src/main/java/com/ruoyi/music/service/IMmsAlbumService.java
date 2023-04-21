package com.ruoyi.music.service;


import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.vo.front.AlbumParamsVo;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;

import java.util.List;

/**
 * 专辑Service接口
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
public interface IMmsAlbumService 
{
    /**
     * 查询专辑
     * 
     * @param id 专辑主键
     * @return 专辑
     */
    public MmsAlbum selectMmsAlbumById(Long id);

    /**
     * 查询专辑列表
     * 
     * @param albumParamsVo 专辑
     * @return 专辑集合
     */
    public List<MmsAlbum> selectMmsAlbumList(AlbumParamsVo albumParamsVo);

    /**
     * 新增专辑
     * 
     * @param mmsAlbum 专辑
     * @return 结果
     */
    public int insertMmsAlbum(MmsAlbum mmsAlbum);

    /**
     * 修改专辑
     * 
     * @param mmsAlbum 专辑
     * @return 结果
     */
    public int updateMmsAlbum(MmsAlbum mmsAlbum);

    /**
     * 批量删除专辑
     * 
     * @param ids 需要删除的专辑主键集合
     * @return 结果
     */
    public int deleteMmsAlbumByIds(Long[] ids);

    /**
     * 删除专辑信息
     * 
     * @param id 专辑主键
     * @return 结果
     */
    public int deleteMmsAlbumById(Long id);

    /**
     * 查询专辑id 和 名字
     * @param singerId 歌手id
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumsBySingerId(Long singerId);

    /**
     * 查询简单专辑列表
     *
     * @return 结果
     */
    List<SimpleAlbum> listSimpleAlbums();

    /**
     * 根据简单歌手列表 查询专辑列表s
     * @param singers SimpleSinger类
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumsBySingers(List<SimpleSinger> singers);

    /**
     * 根据专辑名 返回专辑列表
     *
     * @param albumName  专辑名
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumListByAlbumName(String albumName);
}
