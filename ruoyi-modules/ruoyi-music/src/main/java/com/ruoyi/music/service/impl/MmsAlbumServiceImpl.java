package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.mapper.MmsAlbumMapper;
import com.ruoyi.music.mapper.MmsSingerAlbumMapper;
import com.ruoyi.music.service.IMmsAlbumService;
import com.ruoyi.music.vo.front.AlbumParamsVo;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专辑Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@Service
public class MmsAlbumServiceImpl implements IMmsAlbumService {
    @Autowired
    private MmsAlbumMapper mmsAlbumMapper;

    @Autowired
    private MmsSingerAlbumMapper mmsSingerAlbumMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据专辑名 返回专辑列表
     *
     * @param albumName 专辑名
     * @return 结果
     */
    @Override
    public List<SimpleAlbum> selectSimpleAlbumListByAlbumName(String albumName) {
        return mmsAlbumMapper.selectSimpleAlbumsByAlbumName(albumName);
    }

    /**
     * 查询专辑
     *
     * @param id 专辑主键
     * @return 专辑
     */
    @Override
    public MmsAlbum selectMmsAlbumById(Long id) {
        return mmsAlbumMapper.selectMmsAlbumById(id);
    }

    /**
     * 查询专辑列表
     *
     * @param albumParamsVo 专辑
     * @return 专辑
     */
    @Override
    public List<MmsAlbum> selectMmsAlbumList(AlbumParamsVo albumParamsVo) {
        return mmsSingerAlbumMapper.selectMmsAlbumList(albumParamsVo);
    }

    /**
     * 新增专辑
     *
     * @param mmsAlbum 专辑
     * @return 结果
     */
    @Override
    public int insertMmsAlbum(MmsAlbum mmsAlbum) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("mms_dict:ids:mms_album_id", redisTemplate.getConnectionFactory());
        long albumId = redisAtomicLong.addAndGet(1);
        mmsAlbum.setAlbumId(albumId);
        if (mmsAlbum.getIsDel() == null) {
            mmsAlbum.setIsDel(0);
        }
        if (mmsAlbum.getAlbumStatus() ==null){
            mmsAlbum.setAlbumStatus(1);
        }
        mmsAlbum.setIsDel(0);
        mmsAlbum.setRevision(0);
        if (mmsAlbum.getCreateBy()== null) {
            mmsAlbum.setCreateBy(SecurityUtils.getUsername());
            mmsAlbum.setCreateTime(DateUtils.getNowDate());
        }
        return mmsAlbumMapper.insertMmsAlbum(mmsAlbum);
    }

    /**
     * 修改专辑
     *
     * @param mmsAlbum 专辑
     * @return 结果
     */
    @Override
    public int updateMmsAlbum(MmsAlbum mmsAlbum) {
        mmsAlbum.setEditBy(SecurityUtils.getUsername());
        mmsAlbum.setEditTime(DateUtils.getNowDate());
        return mmsAlbumMapper.updateMmsAlbum(mmsAlbum);
    }

    /**
     * 批量删除专辑
     *
     * @param ids 需要删除的专辑主键
     * @return 结果
     */
    @Override
    public int deleteMmsAlbumByIds(Long[] ids) {
        mmsSingerAlbumMapper.deleteMmsAlbumByAlbumId(ids);
        return mmsAlbumMapper.deleteMmsAlbumByIds(ids);
    }

    /**
     * 删除专辑信息
     *
     * @param id 专辑主键
     * @return 结果
     */
    @Override
    public int deleteMmsAlbumById(Long id) {
        return mmsAlbumMapper.deleteMmsAlbumById(id);
    }

    /**
     * 根据歌手id 获取 专辑名和id值
     *
     * @param singerId
     * @return
     */
    @Override
    public List<SimpleAlbum> selectSimpleAlbumsBySingerId(Long singerId) {
        return mmsAlbumMapper.selectSimpleAlbumsBySingerId(singerId);
    }

    @Override
    public List<SimpleAlbum> listSimpleAlbums() {
        return mmsAlbumMapper.listSimpleAlbum();
    }

    @Override
    public List<SimpleAlbum> selectSimpleAlbumsBySingers(List<SimpleSinger> singers) {
        return mmsSingerAlbumMapper.selectSimpleAlbumsBySingers(singers);
    }
}
