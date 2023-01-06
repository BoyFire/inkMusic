package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.mapper.MmsSongListMapper;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.vo.front.SimpleSongListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@Service
public class MmsSongListServiceImpl implements IMmsSongListService 
{
    @Autowired
    private MmsSongListMapper mmsSongListMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询歌单
     * 
     * @param id 歌单主键
     * @return 歌单
     */
    @Override
    public MmsSongList selectMmsSongListById(Long id)
    {
        return mmsSongListMapper.selectMmsSongListById(id);
    }

    /**
     * 查询歌单列表
     * 
     * @param mmsSongList 歌单
     * @return 歌单
     */
    @Override
    public List<MmsSongList> selectMmsSongListList(MmsSongList mmsSongList)
    {
        return mmsSongListMapper.selectMmsSongListList(mmsSongList);
    }

    /**
     * 新增歌单
     * 
     * @param mmsSongList 歌单
     * @return 结果
     */
    @Override
    public int insertMmsSongList(MmsSongList mmsSongList)
    {
        RedisAtomicLong atomicLong = new RedisAtomicLong("mms_dict:ids:mms_song_list_id", redisTemplate.getConnectionFactory());
        long songListId = atomicLong.addAndGet(1);
        mmsSongList.setSongListId(songListId);
        if (mmsSongList.getSongListStatus() == null){
            mmsSongList.setSongListStatus(1);
        }
        mmsSongList.setIsDel(0);
        mmsSongList.setIsFine(0);
        mmsSongList.setCreateBy(SecurityUtils.getUsername());
        mmsSongList.setCreateTime(DateUtils.getNowDate());
        return mmsSongListMapper.insertMmsSongList(mmsSongList);
    }

    /**
     * 修改歌单
     * 
     * @param mmsSongList 歌单
     * @return 结果
     */
    @Override
    public int updateMmsSongList(MmsSongList mmsSongList)
    {
        mmsSongList.setEditBy(SecurityUtils.getUsername());
        mmsSongList.setEditTime(DateUtils.getNowDate());
        return mmsSongListMapper.updateMmsSongList(mmsSongList);
    }

    /**
     * 批量删除歌单
     * 
     * @param ids 需要删除的歌单主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongListByIds(Long[] ids)
    {
        return mmsSongListMapper.deleteMmsSongListByIds(ids);
    }

    /**
     * 删除歌单信息
     * 
     * @param id 歌单主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongListById(Long id)
    {
        return mmsSongListMapper.deleteMmsSongListById(id);
    }

    @Override
    public List<SimpleSongListVo> listSimpleSongList() {
        return mmsSongListMapper.listSimpleSongList();
    }

    @Override
    public MmsSongList selectMmsSongListBySongListId(Long songListId) {

        return mmsSongListMapper.selectMmsSongListBySongListId(songListId);
    }
}
