package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.mapper.MmsSongMapper;
import com.ruoyi.music.service.IMmsSongService;
import com.ruoyi.music.vo.front.SimpleSongVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌曲Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
@Service
public class MmsSongServiceImpl implements IMmsSongService 
{
    @Autowired
    private MmsSongMapper mmsSongMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询歌曲
     * 
     * @param id 歌曲主键
     * @return 歌曲
     */
    @Override
    public MmsSong selectMmsSongById(Long id)
    {
        return mmsSongMapper.selectMmsSongById(id);
    }

    /**
     * 查询歌曲列表
     * 
     * @param mmsSong 歌曲
     * @return 歌曲
     */
    @Override
    public List<MmsSong> selectMmsSongList(MmsSong mmsSong)
    {
        return mmsSongMapper.selectMmsSongList(mmsSong);
    }

    /**
     * 新增歌曲
     * 
     * @param mmsSong 歌曲
     * @return 结果
     */
    @Override
    public int insertMmsSong(MmsSong mmsSong)
    {
        RedisAtomicLong atomicLong = new RedisAtomicLong("mms_dict:ids:mms_song_id", redisTemplate.getConnectionFactory());
        long songId = atomicLong.addAndGet(1);
        mmsSong.setSongId(songId);
        mmsSong.setIsDel(0);
        if (mmsSong.getSongStatus() == null){
            mmsSong.setSongStatus(1);
        }
        mmsSong.setCreateBy(SecurityUtils.getUsername());
        mmsSong.setCreateTime(DateUtils.getNowDate());
        mmsSongMapper.insertSongAlbum(mmsSong.getSongId(), mmsSong.getAlbumId());
        return mmsSongMapper.insertMmsSong(mmsSong);
    }

    /**
     * 修改歌曲
     * 
     * @param mmsSong 歌曲
     * @return 结果
     */
    @Override
    public int updateMmsSong(MmsSong mmsSong)
    {
        mmsSong.setEditBy(SecurityUtils.getUsername());
        mmsSong.setEditTime(DateUtils.getNowDate());
        mmsSongMapper.updateSongAlbum(mmsSong.getSongId(), mmsSong.getAlbumId());
        return mmsSongMapper.updateMmsSong(mmsSong);
    }

    /**
     * 批量删除歌曲
     * 
     * @param ids 需要删除的歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongByIds(Long[] ids)
    {
        return mmsSongMapper.deleteMmsSongByIds(ids);
    }

    /**
     * 删除歌曲信息
     * 
     * @param id 歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongById(Long id)
    {
        return mmsSongMapper.deleteMmsSongById(id);
    }

    @Override
    public List<SimpleSongVo> listSimpleSong() {
        return mmsSongMapper.listSimpleSong();
    }

    @Override
    public MmsSong selectMmsSongBySongId(Long songId) {
        return mmsSongMapper.selectSongBySongId(songId);
    }
}
