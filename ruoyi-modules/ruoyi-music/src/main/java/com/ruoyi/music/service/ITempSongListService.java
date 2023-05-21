package com.ruoyi.music.service;

import com.ruoyi.music.temp.TempSongList;

import java.util.HashMap;
import java.util.List;

/**
 * api歌单信息Service接口
 *
 * @author ruoyi
 * @date 2023-05-20
 */
public interface ITempSongListService {
    /**
     * 查询api歌单信息
     *
     * @param id api歌单信息主键
     * @return api歌单信息
     */
    public TempSongList selectTempSongListById(Long id);

    /**
     * 查询api歌单信息列表
     *
     * @param tempSongList api歌单信息
     * @return api歌单信息集合
     */
    public List<TempSongList> selectTempSongListList(TempSongList tempSongList);

    /**
     * 新增api歌单信息
     *
     * @param tempSongList api歌单信息
     * @return 结果
     */
    public int insertTempSongList(TempSongList tempSongList);

    /**
     * 修改api歌单信息
     *
     * @param tempSongList api歌单信息
     * @return 结果
     */
    public int updateTempSongList(TempSongList tempSongList);

    /**
     * 批量删除api歌单信息
     *
     * @param ids 需要删除的api歌单信息主键集合
     * @return 结果
     */
    public int deleteTempSongListByIds(Long[] ids);

    /**
     * 删除api歌单信息信息
     *
     * @param id api歌单信息主键
     * @return 结果
     */
    public int deleteTempSongListById(Long id);


    TempSongList selectTempSongListBySongListId(String apiSongListId);

    TempSongList addSongList(String apiSongListId);

    TempSongList selectTempSongListBySongListIdAndUserId(Long userId, String apiSongListId);

    int insertCollect(String apiSongListId, Long userId);

    int deleteTempSongListCollectByUserIdAndApiSongListId(Long userId, String apiSongListId);

    TempSongList addSongList(HashMap<String, Object> item);
}
