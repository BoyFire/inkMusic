package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsComment;
import com.ruoyi.music.mapper.MmsCommentMapper;
import com.ruoyi.music.mapper.MmsUserMapper;
import com.ruoyi.music.service.IMmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@Service
public class MmsCommentServiceImpl implements IMmsCommentService 
{
    @Autowired
    private MmsCommentMapper mmsCommentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MmsUserMapper mmsUserMapper;

    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public MmsComment selectMmsCommentById(Long id)
    {
        return mmsCommentMapper.selectMmsCommentById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param mmsComment 评论
     * @return 评论
     */
    @Override
    public List<MmsComment> selectMmsCommentList(MmsComment mmsComment)
    {
        return mmsCommentMapper.selectMmsCommentList(mmsComment);
    }

    /**
     * 新增评论
     * 
     * @param mmsComment 评论
     * @return 结果
     */
    @Override
    public int insertMmsComment(MmsComment mmsComment)
    {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("mms_dict:ids:mms_comment_id", stringRedisTemplate.getConnectionFactory());
        long commentId = redisAtomicLong.addAndGet(1);
        mmsComment.setCommentId(commentId);
        if (mmsComment.getIsDel() ==null){
            mmsComment.setIsDel(0);
        }
        mmsComment.setCommentLike(0L);
        if (mmsComment.getCommentStatus() == null){
            mmsComment.setCommentStatus(1);
        }
        if (mmsComment.getUserId() ==null){
            mmsComment.setUserId(0L);
        }
        // 如果这条评论1是评论 评论2的评论3 则修改他的内容为 @评论3的用户名:原内容, 评论目标id 变为 评论3的目标id
        if (mmsComment.getCommentType() == 1){
            MmsComment commentParent = mmsCommentMapper.selectMmsCommentByCommentTypeAndTargetId(mmsComment.getCommentType() , mmsComment.getCommentTargetId());
            if (commentParent !=null) {
                if (commentParent.getCommentType() == 1) {
                    mmsComment.setCommentDetail("@" + mmsUserMapper.selectUserNameByUserId(commentParent.getUserId()) + ": " + mmsComment.getCommentDetail());
                    mmsComment.setCommentTargetId(commentParent.getCommentTargetId());
                }
            }
        }
        mmsComment.setCreateBy(SecurityUtils.getUsername());
        mmsComment.setCreateTime(DateUtils.getNowDate());
        return mmsCommentMapper.insertMmsComment(mmsComment);
    }

    /**
     * 修改评论
     * 
     * @param mmsComment 评论
     * @return 结果
     */
    @Override
    public int updateMmsComment(MmsComment mmsComment)    {
        mmsComment.setEditBy(SecurityUtils.getUsername());
        mmsComment.setEditTime(DateUtils.getNowDate());
        return mmsCommentMapper.updateMmsComment(mmsComment);
    }

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteMmsCommentByIds(Long[] ids)
    {
        return mmsCommentMapper.deleteMmsCommentByIds(ids);
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteMmsCommentById(Long id)
    {
        return mmsCommentMapper.deleteMmsCommentById(id);
    }

    @Override
    public List<MmsComment> selectMmsCommentListByTypeAndId(Integer type, Long id) {
        MmsComment mmsComment = new MmsComment();
        mmsComment.setCommentType(type);
        mmsComment.setCommentTargetId(id);
        return selectMmsCommentList(mmsComment);
    }
}
