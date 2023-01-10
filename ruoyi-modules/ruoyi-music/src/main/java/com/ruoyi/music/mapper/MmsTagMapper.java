package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsTag;
import com.ruoyi.music.vo.front.SimpleTagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-25
 */
public interface MmsTagMapper 
{
    /**
     * 查询标签
     * 
     * @param id 标签主键
     * @return 标签
     */
    public MmsTag selectMmsTagById(Long id);

    /**
     * 查询标签列表
     * 
     * @param mmsTag 标签
     * @return 标签集合
     */
    public List<MmsTag> selectMmsTagList(MmsTag mmsTag);

    /**
     * 新增标签
     * 
     * @param mmsTag 标签
     * @return 结果
     */
    public int insertMmsTag(MmsTag mmsTag);

    /**
     * 修改标签
     * 
     * @param mmsTag 标签
     * @return 结果
     */
    public int updateMmsTag(MmsTag mmsTag);

    /**
     * 删除标签
     * 
     * @param id 标签主键
     * @return 结果
     */
    public int deleteMmsTagById(Long id);

    /**
     * 批量删除标签
     * 
     * @param ids 需要删除的数据id集合
     * @return 结果
     */
    public int deleteMmsTagByTagIds(Long[] ids);

    /**
     * 查询是否含有子标签
     * @param tagId  标签id
     * @return  结果
     */
    int hasChildByTagId(@Param("tagId") Long tagId);

    /**
     * 根据 tagId 查询 MmsTag
     * @param tagId tagId
     * @return 结果
     */
    MmsTag selectMmsTagByTagId(@Param("tagId") Long tagId);

    /**
     * 根据 标签ids 获取 标签列表
     * @param tagIds  标签ids
     * @return  结果
     */
    List<MmsTag> selectMmsTagsByTagIds(Long[] tagIds);


    /**
     * 根据 父类id 查询标签
     * @param parentsId 父类id
     * @return 结果
     */
    List<SimpleTagVo> selectSimpleTagsByParentsId(@Param("parentsId") Long parentsId);
}
