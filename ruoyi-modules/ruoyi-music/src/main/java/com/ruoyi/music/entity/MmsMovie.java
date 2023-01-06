package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 视频对象 mms_movie
 *
 * @author ruoyi
 * @date 2022-10-16
 */

@Data
@ToString
public class MmsMovie extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 视频id
     */
    @Excel(name = "视频id")
    private Long movieId;

    /**
     * 视频名称
     */
    @Excel(name = "视频名称")
    private String movieName;

    /**
     * 视频上传者类型;0-歌手, 1-用户
     */
    @Excel(name = "视频上传者类型;0-歌手, 1-用户")
    private Long movieUpType;

    /**
     * 视频上传者id
     */
    @Excel(name = "视频上传者id")
    private Long movieUpId;

    /**
     * 视频封面url
     */
    @Excel(name = "视频封面url")
    private String movieImageUrl;
    /**
     * 视频url
     */
    @Excel(name = "视频url")
    private String movieUrl;

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
     * 视频状态,0-停用,1-启用
     */
    @Excel(name = "视频状态,0-停用,1-启用")
    private Integer movieStatus;
    // 用户名
    private String userName;
    // 歌手名
    private String singerName;
}
