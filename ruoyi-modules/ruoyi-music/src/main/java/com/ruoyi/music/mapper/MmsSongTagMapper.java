package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.entity.MmsTag;
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
     * 根据歌曲id 查询标签
     * @param songId  歌曲id
     * @return 结果
     */
    List<MmsTag> selectMmsTagListBySongId(@Param("songId") Long songId);

    /**
     * 根据 tagId 查询歌曲
     * @param tagId 标签id
     * @return 结果
     */
List<MmsSong> selectMmsTagListByTagId(@Param("tagId") Long tagId);


}
