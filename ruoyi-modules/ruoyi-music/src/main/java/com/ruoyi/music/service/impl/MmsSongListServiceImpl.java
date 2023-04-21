package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.mapper.MmsSongListMapper;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.vo.front.SimpleSongListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
     * 根据 songName 进行模糊查询
     *
     * @param songListName 歌单名
     * @return 结果
     */
    @Override
    public List<SimpleSongListVo> selectSimpleSongsBySongName(String songListName) {
        return mmsSongListMapper.selectSimpleSongsBySongName(songListName);
    }

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
    public int insertMmsSongList(MmsSongList mmsSongList) throws NullPointerException
    {

        if (SecurityUtils.getUsername() == null || "".equals(SecurityUtils.getUsername()) ){
            mmsSongList.setCreateBy("admin");
        }else{
            mmsSongList.setCreateBy(SecurityUtils.getUsername());
        }
        generateSongListMeta(mmsSongList);
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


    /**
     * 给新增的用户添加 最爱的歌单
     *
     * @param user 用户id
     * @return 结果
     */
    @Override
    public int insertNewUserLover(MmsUser user) throws NullPointerException {
        MmsSongList mmsSongList = new MmsSongList();
        mmsSongList.setSongListCreatorId(user.getUserId());
        mmsSongList.setSongListTitle("我喜欢的音乐");
        mmsSongList.setCreateBy(user.getUserNickname());
        generateSongListMeta(mmsSongList);
        return mmsSongListMapper.insertMmsSongList(mmsSongList);
    }


    /**
     * 修改 mmsSongList d 基础属性
     *
     * @param mmsSongList 要修改的对象
     */
    private void generateSongListMeta(MmsSongList mmsSongList) throws NullPointerException {
        RedisAtomicLong atomicLong  = new RedisAtomicLong("mms_dict:ids:mms_song_list_id", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        mmsSongList.setSongListId(atomicLong.addAndGet(1));
        if (mmsSongList.getSongListStatus() == null){
            mmsSongList.setSongListStatus(1);
        }
        mmsSongList.setIsDel(0);
        mmsSongList.setIsFine(0);
        mmsSongList.setCreateTime(DateUtils.getNowDate());
    }
}
