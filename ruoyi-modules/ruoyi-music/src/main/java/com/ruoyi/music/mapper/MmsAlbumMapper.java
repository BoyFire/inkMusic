package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.vo.front.SimpleAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专辑Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
public interface MmsAlbumMapper 
{
    /**
     * 查询专辑
     * 
     * @param id 专辑主键
     * @return 专辑
     */
    public MmsAlbum selectMmsAlbumById(@Param("id") Long id);

    /**
     * 查询专辑列表
     * 
     * @param mmsAlbum 专辑
     * @return 专辑集合
     */
    public List<MmsAlbum> selectMmsAlbumList(MmsAlbum mmsAlbum);

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
     * 删除专辑
     * 
     * @param id 专辑主键
     * @return 结果
     */
    public int deleteMmsAlbumById(Long id);

    /**
     * 批量删除专辑
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsAlbumByIds(Long[] ids);

    /**
     * 查询专辑名
     *
     * @param albumId 专辑id
     * @return 专辑
     */
    String selectMmsAlbumNameByAlbumId(@Param("albumId") Long albumId);

    /**
     * 查询专辑名和id
     * @param singerId 歌手id
     * @return 结果
     */
    List<SimpleAlbum> selectSimpleAlbumsBySingerId(@Param("singerId") Long singerId);

    /**
     * 查询简单专辑列表
     * @return  结果
     */
    List<SimpleAlbum> listSimpleAlbum();
}
