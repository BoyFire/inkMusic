package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.music.mapper.TempSongListMapper;
import com.ruoyi.music.service.ITempSongListService;
import com.ruoyi.music.temp.TempSongList;
import com.ruoyi.music.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * api歌单信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-20
 */
@Service
public class TempSongListServiceImpl implements ITempSongListService {
    @Autowired
    private TempSongListMapper tempSongListMapper;

    /**
     * 查询api歌单信息
     *
     * @param id api歌单信息主键
     * @return api歌单信息
     */
    @Override
    public TempSongList selectTempSongListById(Long id) {
        return tempSongListMapper.selectTempSongListById(id);
    }

    /**
     * 查询api歌单信息列表
     *
     * @param tempSongList api歌单信息
     * @return api歌单信息
     */
    @Override
    public List<TempSongList> selectTempSongListList(TempSongList tempSongList) {
        return tempSongListMapper.selectTempSongListList(tempSongList);
    }

    /**
     * 新增api歌单信息
     *
     * @param tempSongList api歌单信息
     * @return 结果
     */
    @Override
    public int insertTempSongList(TempSongList tempSongList) {
        return tempSongListMapper.insertTempSongList(tempSongList);
    }

    /**
     * 修改api歌单信息
     *
     * @param tempSongList api歌单信息
     * @return 结果
     */
    @Override
    public int updateTempSongList(TempSongList tempSongList) {
        tempSongList.setUpdateTime(DateUtils.getNowDate());
        return tempSongListMapper.updateTempSongList(tempSongList);
    }

    /**
     * 批量删除api歌单信息
     *
     * @param ids 需要删除的api歌单信息主键
     * @return 结果
     */
    @Override
    public int deleteTempSongListByIds(Long[] ids) {
        return tempSongListMapper.deleteTempSongListByIds(ids);
    }

    /**
     * 删除api歌单信息信息
     *
     * @param id api歌单信息主键
     * @return 结果
     */
    @Override
    public int deleteTempSongListById(Long id) {
        return tempSongListMapper.deleteTempSongListById(id);
    }

    @Override
    public TempSongList selectTempSongListBySongListId(String apiSongListId) {
        return tempSongListMapper.selectTempSongListBySongListId(apiSongListId);
    }

    @Override
    public TempSongList addSongList(String apiSongListId) {
        Map<Object, Object> res = HttpUtils.httpGet("/playlist/detail", "id", apiSongListId);
        HashMap<String, Object> playlist = (HashMap<String, Object>) res.get("playlist");
        TempSongList tempSongList = new TempSongList();
        tempSongList.setApiSongListId(apiSongListId);
        tempSongList.setName(String.valueOf(playlist.get("name")));
        tempSongList.setCommentCount(Long.valueOf(String.valueOf(playlist.get("commentCount"))));
        tempSongList.setCoverImgUrl(String.valueOf(playlist.get("coverImgUrl")));
        tempSongList.setDescription(String.valueOf(playlist.get("description")));
        tempSongList.setSubscribedCount(Long.valueOf(String.valueOf(playlist.get("subscribedCount"))));
        tempSongList.setTotal(1L + Long.parseLong(String.valueOf(playlist.get("trackCount"))));
        tempSongList.setPlayCount(Long.parseLong(String.valueOf(playlist.get("playCount"))));
        insertTempSongList(tempSongList);
        return tempSongList;
    }

    @Override
    public TempSongList selectTempSongListBySongListIdAndUserId(Long userId, String apiSongListId) {
        return tempSongListMapper.selectTempSongListBySongListIdAndUserId(userId, apiSongListId);
    }

    @Override
    public int insertCollect(String apiSongListId, Long userId) {
        return tempSongListMapper.insertCollect(apiSongListId, userId);
    }

    @Override
    public int deleteTempSongListCollectByUserIdAndApiSongListId(Long userId, String apiSongListId) {
        return tempSongListMapper.deleteTempSongListCollectByUserIdAndApiSongListId(userId, apiSongListId);
    }

    @Override
    public TempSongList addSongList(HashMap<String, Object> item) {
        TempSongList tempSongList = new TempSongList();
        tempSongList.setApiSongListId(String.valueOf(item.get("id")));
        tempSongList.setName(String.valueOf(item.get("name")));
        tempSongList.setCommentCount(Long.valueOf(String.valueOf(item.get("commentCount"))));
        tempSongList.setCoverImgUrl(String.valueOf(item.get("coverImgUrl")));
        tempSongList.setDescription(String.valueOf(item.get("description")));
        tempSongList.setSubscribedCount(Long.valueOf(String.valueOf(item.get("subscribedCount"))));
        tempSongList.setTotal(1L + Long.parseLong(String.valueOf(item.get("trackCount"))));
        tempSongList.setPlayCount(Long.parseLong(String.valueOf(item.get("playCount"))));
        insertTempSongList(tempSongList);
        return tempSongList;
    }

}
