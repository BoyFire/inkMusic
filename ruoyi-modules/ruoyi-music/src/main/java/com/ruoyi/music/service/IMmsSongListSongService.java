package com.ruoyi.music.service;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.music.entity.MmsSongListSong;

import java.util.List;
import java.util.Map;

/**
 * 歌单歌曲Service接口
 * 
 * @author ruoyi
 * @date 2023-01-05
 */
public interface IMmsSongListSongService 
{
    /**
     * 查询歌单歌曲
     * 
     * @param id 歌单歌曲主键
     * @return 歌单歌曲
     */
    public MmsSongListSong selectMmsSongListSongById(Long id);

    /**
     * 查询歌单歌曲列表
     * 
     * @param mmsSongListSong 歌单歌曲
     * @return 歌单歌曲集合
     */
    public List<MmsSongListSong> selectMmsSongListSongList(MmsSongListSong mmsSongListSong);

    /**
     * 新增歌单歌曲
     * 
     * @param mmsSongListSong 歌单歌曲
     * @return 结果
     */
    public AjaxResult insertMmsSongListSong(MmsSongListSong mmsSongListSong);

    /**
     * 修改歌单歌曲
     * 
     * @param mmsSongListSong 歌单歌曲
     * @return 结果
     */
    public int updateMmsSongListSong(MmsSongListSong mmsSongListSong);

    /**
     * 批量删除歌单歌曲
     * 
     * @param ids 需要删除的歌单歌曲主键集合
     * @return 结果
     */
    public int deleteMmsSongListSongByIds(Long[] ids);

    /**
     * 删除歌单歌曲信息
     * 
     * @param id 歌单歌曲主键
     * @return 结果
     */
    public int deleteMmsSongListSongById(Long id);

    Map<String, Object>  selectMmsSongListSongBySongListId(Long songListId);


    /**
     * 收藏歌曲
     *
     * @param params 请求体
     * @return 结果
     */
    boolean collectSong(Map<String, Object> params) throws ServiceException;

    int disCollectSong(Map<String, Object> params);
}
