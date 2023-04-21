package com.ruoyi.music.vo.front;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class AlbumParamsVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 专辑id
     */
    private Long albumId;

    /**
     * 专辑名
     */
    private String albumName;

    /**
     * 专辑封面URL
     */
    private String albumImgUrl;

    /**
     * 专辑状态,1-启用,0-停用
     */
    private Integer albumStatus;

    /**
     * 更新者
     */
    private String editBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date editTime;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 是否删除;0-未删除,1-删除
     */
    private Integer isDel;

    private Long singerId;
    private String singerName;

}