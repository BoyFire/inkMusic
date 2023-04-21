package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.vo.front.SimpleSongVo;
import com.ruoyi.music.vo.front.SongParamsVo;

import java.util.List;

/**
 * 歌曲Service接口
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
public interface IMmsSongService 
{
    /**
     * 查询歌曲
     * 
     * @param id 歌曲主键
     * @return 歌曲
     */
    public MmsSong selectMmsSongById(Long id);

    /**
     * 查询歌曲列表
     *
     * @param songParams 歌曲
     * @return 歌曲集合
     */
    public List<MmsSong> selectMmsSongList(SongParamsVo songParams);

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
     * 批量删除歌曲
     * 
     * @param ids 需要删除的歌曲主键集合
     * @return 结果
     */
    public int deleteMmsSongByIds(Long[] ids);

    /**
     * 删除歌曲信息
     * 
     * @param id 歌曲主键
     * @return 结果
     */
    public int deleteMmsSongById(Long id);

    /**
     * 获取简单歌曲表
     * @return 结果
     */
    List<SimpleSongVo> listSimpleSong();

    /**
     * 根据songId 来获取 歌曲信息
     * @param songId song_id
     * @return  结果
     */
    MmsSong selectMmsSongBySongId( Long songId);

    /**
     * 根据歌曲名 查询简单歌曲
     *
     * @param songName
     * @return
     */
    List<SimpleSongVo> selectSimpleSongsBySongName(String songName);
}
