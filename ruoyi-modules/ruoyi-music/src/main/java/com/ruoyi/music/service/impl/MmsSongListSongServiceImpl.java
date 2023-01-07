package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.entity.MmsSongListSong;
import com.ruoyi.music.mapper.MmsSongListSongMapper;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.service.IMmsSongListSongService;
import com.ruoyi.music.service.IMmsSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
