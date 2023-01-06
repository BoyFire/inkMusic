package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.vo.front.SimpleSongListVo;

import java.util.List;

/**
 * 歌单Service接口
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
public interface IMmsSongListService 
{
    /**
     * 查询歌单
     * 
     * @param id 歌单主键
     * @return 歌单
     */
    public MmsSongList selectMmsSongListById(Long id);

    /**
     * 查询歌单列表
     * 
     * @param mmsSongList 歌单
     * @return 歌单集合
     */
    public List<MmsSongList> selectMmsSongListList(MmsSongList mmsSongList);

    /**
     * 新增歌单
     * 
     * @param mmsSongList 歌单
     * @return 结果
     */
    public int insertMmsSongList(MmsSongList mmsSongList);

    /**
     * 修改歌单
     * 
     * @param mmsSongList 歌单
     * @return 结果
     */
    public int updateMmsSongList(MmsSongList mmsSongList);

    /**
     * 批量删除歌单
     * 
     * @param ids 需要删除的歌单主键集合
     * @return 结果
     */
    public int deleteMmsSongListByIds(Long[] ids);

    /**
     * 删除歌单信息
     * 
     * @param id 歌单主键
     * @return 结果
     */
    public int deleteMmsSongListById(Long id);

    /**
     * 获取简单歌单列表
     * @return 结果
     */
    List<SimpleSongListVo> listSimpleSongList();

    /**
     * 根据 songListId 来获取歌单信息
     * @param songListId    歌单id
     * @return  结果
     */
    MmsSongList selectMmsSongListBySongListId(Long songListId);
}
