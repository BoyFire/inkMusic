package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsTag;
import com.ruoyi.music.vo.front.SimpleTagVo;

import java.util.List;

/**
 * 标签Service接口
 * 
 * @author ruoyi
 * @date 2022-10-25
 */
public interface IMmsTagService 
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
     * 批量删除标签
     * 
     * @param tagIds 需要删除的标签id集合
     * @return 结果
     */
    public int deleteMmsTagByTagIds(Long[] tagIds);

    /**
     * 删除标签信息
     * 
     * @param id 标签主键
     * @return 结果
     */
    public int deleteMmsTagById(Long id);

    /**
     * 构建树结构
     *
     * @param mmsTags 标签列表
     * @return 树结构列表
     */
    List<MmsTag> buildMmsTagTree(List<MmsTag> mmsTags);

    /**
     * 是否含有子标签
     * @param tagId  标签id
     * @return 结果
     */
    boolean hasChildByTagId(Long tagId);

    /**
     * 查询标签
     * @param songId 歌曲id
     * @return 结果
     */
    List<MmsTag> selectMmsTagBySongId(Long songId);

    /**
     * 通过歌曲id 查询标签
     * @param songId 歌曲id
     * @return 结果
     */
    List<SimpleTagVo> selectSimpleTagsBySongId(Long songId);

    /**
     * 根据 parentsId 查询标签
     * @param parentsId 父类标签
     * @return 结果
     */
    List<SimpleTagVo> selectSimpleTagsByParentsId(Long parentsId);
}
