package com.ruoyi.music.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.music.vo.front.SimpleSinger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 专辑对象 mms_album
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@Data
@ToString
@EqualsAndHashCode
public class MmsAlbum extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 专辑id
     */
    @Excel(name = "专辑id")
    private Long albumId;


    /**
     * 专辑id
     */
    @Excel(name = "专辑歌曲数")
    private Integer albumSize;
    /**
     * 专辑名
     */
    @Excel(name = "专辑名")
    private String albumName;


    /**
     * 专辑类型
     */
    @Excel(name = "专辑类型:singer,album")
    private String albumType;

    /**
     * 专辑描述
     */
    @Excel(name = "专辑描述")
    private String albumDesc;

    /**
     * 专辑封面URL
     */
    @Excel(name = "专辑封面URL")
    private String albumImgUrl;

    /**
     * 专辑状态,1-启用,0-停用
     */
    @Excel(name = "专辑状态,1-启用,0-停用")
    private Integer albumStatus;

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
    private Integer revision;

    /**
     * 是否删除;0-未删除,1-删除
     */
    private Integer isDel;

    private List<SimpleSinger> singers;

}
