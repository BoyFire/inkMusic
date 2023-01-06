package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsMovie;
import com.ruoyi.music.mapper.MmsMovieMapper;
import com.ruoyi.music.service.IMmsMovieService;
import com.ruoyi.music.vo.front.SimpleMovieVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 视频Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-16
 */
@Service
public class MmsMovieServiceImpl implements IMmsMovieService {
    @Autowired
    private MmsMovieMapper mmsMovieMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询视频
     *
     * @param id 视频主键
     * @return 视频
     */
    @Override
    public MmsMovie selectMmsMovieById(Long id) {
        return mmsMovieMapper.selectMmsMovieById(id);
    }

    /**
     * 查询视频列表
     *
     * @param mmsMovie 视频
     * @return 视频
     */
    @Override
    public List<MmsMovie> selectMmsMovieList(MmsMovie mmsMovie) {
        return mmsMovieMapper.selectMmsMovieList(mmsMovie);
    }

    /**
     * 新增视频
     *
     * @param mmsMovie 视频
     * @return 结果
     */
    @Override
    public int insertMmsMovie(MmsMovie mmsMovie) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("mms_dict:ids:mms_movie_id", redisTemplate.getConnectionFactory());
        long movieId = redisAtomicLong.addAndGet(1);
        mmsMovie.setMovieId(movieId);
        mmsMovie.setIsDel(0);
        mmsMovie.setCreateBy(SecurityUtils.getUsername());
        if (mmsMovie.getMovieStatus() == null) {
            mmsMovie.setMovieStatus(1);
        }
        mmsMovie.setCreateTime(DateUtils.getNowDate());
        return mmsMovieMapper.insertMmsMovie(mmsMovie);
    }

    /**
     * 修改视频
     *
     * @param mmsMovie 视频
     * @return 结果
     */
    @Override
    public int updateMmsMovie(MmsMovie mmsMovie) {
        mmsMovie.setEditBy(SecurityUtils.getUsername());
        mmsMovie.setEditTime(DateUtils.getNowDate());
        return mmsMovieMapper.updateMmsMovie(mmsMovie);
    }

    /**
     * 批量删除视频
     *
     * @param ids 需要删除的视频主键
     * @return 结果
     */
    @Override
    public int deleteMmsMovieByIds(Long[] ids) {
        return mmsMovieMapper.deleteMmsMovieByIds(ids);
    }

    /**
     * 删除视频信息
     *
     * @param id 视频主键
     * @return 结果
     */
    @Override
    public int deleteMmsMovieById(Long id) {
        return mmsMovieMapper.deleteMmsMovieById(id);
    }

    @Override
    public List<SimpleMovieVo> listSimpleMovie() {
        return mmsMovieMapper.listSimpleMovie();
    }


}
