package com.ruoyi.music.mapper;

import com.ruoyi.music.temp.TempSongList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * api歌单信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-20
 */
public interface TempSongListMapper 
{
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
     * 删除api歌单信息
     * 
     * @param id api歌单信息主键
     * @return 结果
     */
    public int deleteTempSongListById(Long id);

    /**
     * 批量删除api歌单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTempSongListByIds(Long[] ids);

    List<TempSongList> selectTempSongListByUserId(@Param("userId") Long userId);

    TempSongList selectTempSongListBySongListId(@Param("apiSongListId") String apiSongListId);

    TempSongList selectTempSongListBySongListIdAndUserId(@Param("userId") Long userId, @Param("apiSongListId") String apiSongListId);

    int insertCollect(@Param("apiSongListId") String apiSongListId, @Param("userId") Long userId);

    int deleteTempSongListCollectByUserIdAndApiSongListId(@Param("userId") Long userId, @Param("apiSongListId") String apiSongListId);
}
