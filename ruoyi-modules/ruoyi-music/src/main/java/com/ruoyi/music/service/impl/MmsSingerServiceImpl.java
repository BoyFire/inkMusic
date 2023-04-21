package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.mapper.MmsSingerMapper;
import com.ruoyi.music.service.IMmsSingerService;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌手Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-16
 */
@Service
public class MmsSingerServiceImpl implements IMmsSingerService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MmsSingerMapper mmsSingerMapper;

    /**
     * 查询歌手
     *
     * @param id 歌手主键
     * @return 歌手
     */
    @Override
    public MmsSinger selectMmsSingerById(Long id) {
        return mmsSingerMapper.selectMmsSingerById(id);
    }

    /**
     * 查询歌手列表
     *
     * @param mmsSinger 歌手
     * @return 歌手
     */
    @Override
    public List<MmsSinger> selectMmsSingerList(MmsSinger mmsSinger) {
        return mmsSingerMapper.selectMmsSingerList(mmsSinger);
    }

    /**
     * 新增歌手
     *
     * @param mmsSinger 歌手
     * @return 结果
     */
    @Override
    public int insertMmsSinger(MmsSinger mmsSinger) {
        RedisAtomicLong counter = new RedisAtomicLong("mms_dict:ids:mms_singer_id", redisTemplate.getConnectionFactory());
        long singerId = counter.addAndGet(1);
        mmsSinger.setSingerId(singerId);
        if (mmsSinger.getSingerType() == null) {
            mmsSinger.setSingerType(0);
        }
        if (mmsSinger.getSingerCountry() == null) {
            mmsSinger.setSingerCountry(0);
        }
        if (mmsSinger.getSingerStatus() == null) {
            mmsSinger.setSingerStatus(1);
        }
        mmsSinger.setCreateBy(SecurityUtils.getUsername());
        mmsSinger.setCreateTime(DateUtils.getNowDate());
        return mmsSingerMapper.insertMmsSinger(mmsSinger);
    }

    /**
     * 修改歌手
     *
     * @param mmsSinger 歌手
     * @return 结果
     */
    @Override
    public int updateMmsSinger(MmsSinger mmsSinger) {
        mmsSinger.setEditBy(SecurityUtils.getUsername());
        mmsSinger.setEditTime(DateUtils.getNowDate());
        return mmsSingerMapper.updateMmsSinger(mmsSinger);
    }

    /**
     * 批量删除歌手
     *
     * @param ids 需要删除的歌手主键
     * @return 结果
     */
    @Override
    public int deleteMmsSingerByIds(Long[] ids) {
        return mmsSingerMapper.deleteMmsSingerByIds(ids);
    }

    /**
     * 删除歌手信息
     *
     * @param id 歌手主键
     * @return 结果
     */
    @Override
    public int deleteMmsSingerById(Long id) {
        return mmsSingerMapper.deleteMmsSingerById(id);
    }

    @Override
    public List<SimpleSinger> selectSimpleSingerList() {
        return mmsSingerMapper.selectSimpleSinger();
    }

    @Override
    public List<SimpleSinger> selectSimpleSingerListBySingerName(String singerName) {
        return mmsSingerMapper.selectSimpleSingerBySingerName( singerName);
    }
}
