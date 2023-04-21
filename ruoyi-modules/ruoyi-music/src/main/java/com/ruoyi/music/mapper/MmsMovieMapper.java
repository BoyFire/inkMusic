package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsMovie;
import com.ruoyi.music.vo.front.SimpleMovieVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
public interface MmsMovieMapper 
{
    /**
     * 查询视频
     * 
     * @param id 视频主键
     * @return 视频
     */
    public MmsMovie selectMmsMovieById(Long id);
    /**
     * 查询视频名
     *
     * @param movieId 视频id
     * @return 视频
     */
    public String selectMovieNameByMovieId(@Param("movieId") Long movieId);

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
     * 删除视频
     * 
     * @param id 视频主键
     * @return 结果
     */
    public int deleteMmsMovieById(Long id);

    /**
     * 批量删除视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsMovieByIds(Long[] ids);

    /**
     * 获取简单视频列表
     * @return 结果
     */
    List<SimpleMovieVo> listSimpleMovie();

    /**
     * 根据Mv名进行模糊查询
     *
     * @param mvName mv名
     * @return 结果
     */
    List<SimpleMovieVo> selectSimpleMvsByName(@Param("mvName") String mvName);
}
