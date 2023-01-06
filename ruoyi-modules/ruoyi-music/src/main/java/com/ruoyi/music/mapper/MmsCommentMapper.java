package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
public interface MmsCommentMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    public MmsComment selectMmsCommentById(Long id);

    /**
     * 查询评论列表
     * 
     * @param mmsComment 评论
     * @return 评论集合
     */
    public List<MmsComment> selectMmsCommentList(MmsComment mmsComment);

    /**
     * 新增评论
     * 
     * @param mmsComment 评论
     * @return 结果
     */
    public int insertMmsComment(MmsComment mmsComment);

    /**
     * 修改评论
     * 
     * @param mmsComment 评论
     * @return 结果
     */
    public int updateMmsComment(MmsComment mmsComment);

    /**
     * 删除评论
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteMmsCommentById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsCommentByIds(Long[] ids);

    /**
     * 通过评论类型和目标id 查询评论

     *
     * @param commentType 评论类型
     * @param commentTargetId 目标id
     * @return 结果
     *
     * 注意: 仅适用于 评论目标为评论的
     */
    MmsComment selectMmsCommentByCommentTypeAndTargetId(@Param("commentType") Integer commentType, @Param("commentTargetId") Long commentTargetId);
}
