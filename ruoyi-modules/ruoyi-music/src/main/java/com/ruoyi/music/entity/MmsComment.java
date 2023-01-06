package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 评论对象 mms_comment
 *
 * @author ruoyi
 * @date 2022-10-24
 */
@Data
@ToString
public class MmsComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 评论id
     */
    @Excel(name = "评论id")
    private Long commentId;

    /**
     * 评论目标,1-评论,2-专辑,3-歌曲,4-歌单,5-视频
     */
    @Excel(name = "评论目标,1-评论,2-专辑,3-歌曲,4-歌单,5-视频")
    private Integer commentType;

    /**
     * 评论内容
     */
    @Excel(name = "评论内容")
    private String commentDetail;

    /**
     * 评论点赞数
     */
    @Excel(name = "评论点赞数")
    private Long commentLike;

    /**
     * 评论状态,1-启用, 0-停用
     */
    @Excel(name = "评论状态,1-启用, 0-停用")
    private Integer commentStatus;

    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String editBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date editTime;

    /**
     * 乐观锁
     */
    @Excel(name = "乐观锁")
    private Long revision;

    /**
     * 是否删除;0-未删除,1-删除
     */
    @Excel(name = "是否删除;0-未删除,1-删除")
    private Integer isDel;


    /**
     * 评论目标id
     */
    @Excel(name = "评论目标id")
    private Long commentTargetId;

    private String userName;
    private String albumName;
    private String songListName;
    private String songName;
    private String movieName;



}
