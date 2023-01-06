package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.vo.front.SimpleSongVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歌曲Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
public interface MmsSongMapper 
{
    /**
     * 查询歌曲
     * 
     * @param id 歌曲主键
     * @return 歌曲
     */
    public MmsSong selectMmsSongById(Long id);

    /**
     * 查询歌曲名
     *
     * @param songId 歌曲id
     * @return 歌曲
     */
    public String selecSongNameBySongId(@Param("songId") Long songId);

    /**
     * 查询歌曲列表
     * 
     * @param mmsSong 歌曲
     * @return 歌曲集合
     */
    public List<MmsSong> selectMmsSongList(MmsSong mmsSong);

    /**
     * 新增歌曲
     * 
     * @param mmsSong 歌曲
     * @return 结果
     */
    public int insertMmsSong(MmsSong mmsSong);

    /**
     * 修改歌曲
     * 
     * @param mmsSong 歌曲
     * @return 结果
     */
    public int updateMmsSong(MmsSong mmsSong);

    /**
     * 删除歌曲
     * 
     * @param id 歌曲主键
     * @return 结果
     */
    public int deleteMmsSongById(Long id);

    /**
     * 批量删除歌曲
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsSongByIds(Long[] ids);

    /**
     * 获取简单歌曲列表
     * @return 结果
     */
    List<SimpleSongVo> listSimpleSong();

    /**
     *
     * 添加 歌曲与专辑关系
     * @param songId 歌曲id
     * @param albumId   专辑id
     * @return 结果
     */
    int insertSongAlbum(@Param("songId") Long songId, @Param("albumId") Long albumId);

    /**
     * 修改歌曲与专辑关系
     * @param songId
     * @param albumId
     * @return
     */
    int updateSongAlbum(@Param("songId") Long songId, @Param("albumId") Long albumId);

    /**
     * 根据 song_id 来获取歌曲信息
     * @param songId song_id
     * @return  结果
     */
    MmsSong selecSongBySongId(@Param("songId") Long songId);
}
