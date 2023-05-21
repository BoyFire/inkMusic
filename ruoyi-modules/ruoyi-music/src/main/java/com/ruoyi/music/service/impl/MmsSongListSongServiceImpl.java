package com.ruoyi.music.service.impl;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.entity.MmsSongListSong;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.mapper.MmsSongListSongMapper;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.service.IMmsSongListSongService;
import com.ruoyi.music.service.IMmsSongService;
import com.ruoyi.music.service.IMmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 歌单歌曲Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-05
 */
@Service
public class MmsSongListSongServiceImpl implements IMmsSongListSongService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MmsSongListSongMapper mmsSongListSongMapper;
    @Resource
    private IMmsSongListService songListService;
    @Resource
    private IMmsSongService songService;
    @Resource
    private IMmsUserService userService;

    /**
     * 查询歌单歌曲
     *
     * @param id 歌单歌曲主键
     * @return 歌单歌曲
     */
    @Override
    public MmsSongListSong selectMmsSongListSongById(Long id) {
        return mmsSongListSongMapper.selectMmsSongListSongById(id);
    }

    /**
     * 查询歌单歌曲列表
     *
     * @param mmsSongListSong 歌单歌曲
     * @return 歌单歌曲
     */
    @Override
    public List<MmsSongListSong> selectMmsSongListSongList(MmsSongListSong mmsSongListSong) {
        return mmsSongListSongMapper.selectMmsSongListSongList(mmsSongListSong);
    }

    /**
     * 新增歌单歌曲
     *
     * @param mmsSongListSong 歌单歌曲
     * @return 结果
     */
    @Override
    public AjaxResult insertMmsSongListSong(MmsSongListSong mmsSongListSong) {
        MmsSong mmsSong = songService.selectMmsSongBySongId(mmsSongListSong.getSongId());
        MmsSongList mmsSongList = songListService.selectMmsSongListBySongListId(mmsSongListSong.getSongListId());
        if (mmsSong == null || mmsSongList == null) {
            return AjaxResult.error("当前所选的歌曲或者歌单不存在!请重新选择");
        }
        return AjaxResult.success(mmsSongListSongMapper.insertMmsSongListSong(mmsSongListSong));
    }

    /**
     * 修改歌单歌曲
     *
     * @param mmsSongListSong 歌单歌曲
     * @return 结果
     */
    @Override
    public int updateMmsSongListSong(MmsSongListSong mmsSongListSong) {
        return mmsSongListSongMapper.updateMmsSongListSong(mmsSongListSong);
    }

    /**
     * 批量删除歌单歌曲
     *
     * @param ids 需要删除的歌单歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongListSongByIds(Long[] ids) {
        return mmsSongListSongMapper.deleteMmsSongListSongByIds(ids);
    }

    /**
     * 删除歌单歌曲信息
     *
     * @param id 歌单歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongListSongById(Long id) {
        return mmsSongListSongMapper.deleteMmsSongListSongById(id);
    }

    @Override
    public Map<String, Object> selectMmsSongListSongBySongListId(Long songListId) {
        MmsSongList songList = songListService.selectMmsSongListBySongListId(songListId);
        List<MmsSong> songs = songService.selectMmsSongsBySongListId(songListId);
        MmsUser user = userService.selectMmsUserBySongListId(songListId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("songList", songList);
        result.put("songs", songs);
        result.put("creator", user);
        result.put("subscribed", true);
        return result;
    }

    @Override
    public boolean collectSong(Map<String, Object> params) throws ServiceException {
        Long songListId = Long.valueOf(String.valueOf(params.get("songListId")));
        String apiSongId = String.valueOf(params.get("apiSongId"));
        Long songId = songService.selectTempSongId(apiSongId);
        MmsSong mmsSong = new MmsSong();
        if (songId == null) {
            mmsSong = songService.addSong(apiSongId);
        }
        MmsSongListSong mmsSongListSong = new MmsSongListSong();
        mmsSongListSong.setSongListId(songListId);
        mmsSongListSong.setSongId(songId);

        List<MmsSongListSong> songs = mmsSongListSongMapper.selectMmsSongListSongList(mmsSongListSong);
        int i;
        if (CollectionUtils.isEmpty(songs)) {
            MmsSongList mmsSongList = songListService.selectMmsSongListBySongListId(songListId);
            mmsSongList.setSongListImgUrl(mmsSong.getSongImgUrl());
            songListService.updateMmsSongList(mmsSongList);
            i = mmsSongListSongMapper.insertMmsSongListSong(mmsSongListSong);
        } else {
            throw new ServiceException("歌单存在这首歌");
        }
        return i > 0;
    }

    @Override
    public int disCollectSong(Map<String, Object> params) {
        Long songListId = Long.valueOf(String.valueOf(params.get("songListId")));
        String songId = String.valueOf(params.get("songId"));
        return mmsSongListSongMapper.deleteMmsSongListSongByTwo(songListId, songId);
    }
}
