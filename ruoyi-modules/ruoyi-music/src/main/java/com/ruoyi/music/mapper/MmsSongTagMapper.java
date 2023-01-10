package com.ruoyi.music.mapper;

import com.ruoyi.music.vo.front.SimpleTagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歌曲标签Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-20
 */
public interface MmsSongTagMapper {

    /**
     * 查询标签id列表
     * @param songId 歌曲id
     * @return 结果
     */
    Long[] selectTagIdsBySongId(@Param("songId") Long songId);

    /**
     * 查询歌曲id列表
     * @param  tagId 标签id
     * @return 结果
     */
    Long[] selectSongIdsByTagId(@Param("tagId") Long tagId);


    /**
     *  根据歌曲id 查询标签
     * @param songId 歌曲id
     * @return  结果
     */
    List<SimpleTagVo> selectSimpleTagsBySongId(@Param("songId") Long songId);


    /**
     * 根据 歌曲id 获取标签名
     * @param songId
     * @return
     */
    List<String> selectTagNameBySongId(@Param("songId") Long songId);


    /**
     * 根据标签id 删除歌曲与标签的连接
     * @param tagId 标签id
     * @return 结果
     */
    int deleteSongTagByTagId(@Param("tagId") Long tagId);


    /**
     * 添加songTag
     * @param tagIds 标签id列表
     * @param songId 歌曲id
     * @return 结果
     */
    int insertSongTag(@Param("tagIds") List<Long> tagIds, @Param("songId") Long songId);
}
