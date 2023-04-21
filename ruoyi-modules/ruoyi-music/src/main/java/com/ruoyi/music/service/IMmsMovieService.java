package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsMovie;
import com.ruoyi.music.vo.front.SimpleMovieVo;

import java.util.List;

/**
 * 视频Service接口
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
public interface IMmsMovieService 
{
    /**
     * 查询视频
     * 
     * @param id 视频主键
     * @return 视频
     */
    public MmsMovie selectMmsMovieById(Long id);

    /**
     * 查询视频列表
     * 
     * @param mmsMovie 视频
     * @return 视频集合
     */
    public List<MmsMovie> selectMmsMovieList(MmsMovie mmsMovie);

    /**
     * 新增视频
     * 
     * @param mmsMovie 视频
     * @return 结果
     */
    public int insertMmsMovie(MmsMovie mmsMovie);

    /**
     * 修改视频
     * 
     * @param mmsMovie 视频
     * @return 结果
     */
    public int updateMmsMovie(MmsMovie mmsMovie);

    /**
     * 批量删除视频
     * 
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int deleteMmsMovieByIds(Long[] ids);

    /**
     * 删除视频信息
     * 
     * @param id 视频主键
     * @return 结果
     */
    public int deleteMmsMovieById(Long id);


    /**
     * 获取简单视频列表
     *
     * @return 结果
     */
    List<SimpleMovieVo>  listSimpleMovie();

    /**
     * 根据Mv名进行模糊查询
     *
     * @param mvName  mv名
     * @return 结果
     */
    List<SimpleMovieVo> getSimpleMvsByName(String mvName);
}
