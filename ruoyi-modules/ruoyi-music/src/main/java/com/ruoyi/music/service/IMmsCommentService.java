package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsComment;

import java.util.List;

/**
 * 评论Service接口
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
public interface IMmsCommentService 
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
     * 批量删除评论
     * 
     * @param ids 需要删除的评论主键集合
     * @return 结果
     */
    public int deleteMmsCommentByIds(Long[] ids);

    /**
     * 删除评论信息
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteMmsCommentById(Long id);

    List<MmsComment> selectMmsCommentListByTypeAndId(Integer type, Long id);
}
