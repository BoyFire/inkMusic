package com.ruoyi.music.service;


import com.ruoyi.music.entity.MmsUserAuth;
import com.ruoyi.music.vo.MmsUserAuthIdsVo;

import java.util.List;

/**
 * 用户认证Service接口
 * 
 * @author ruoyi
 * @date 2022-10-19
 */
public interface IMmsUserAuthService 
{
    /**
     * 查询用户认证
     * 
     * @param id 用户认证主键
     * @return 用户认证
     */
    public MmsUserAuth selectMmsUserAuthById(Long id);

    /**
     * 查询用户认证列表
     * 
     * @param mmsUserAuth 用户认证
     * @return 用户认证集合
     */
    public List<MmsUserAuth> selectMmsUserAuthList(MmsUserAuth mmsUserAuth);

    /**
     * 新增用户认证
     * 
     * @param mmsUserAuth 用户认证
     * @return 结果
     */
    public int insertMmsUserAuth(MmsUserAuth mmsUserAuth);

    /**
     * 修改用户认证
     * 
     * @param mmsUserAuth 用户认证
     * @return 结果
     */
    public int updateMmsUserAuth(MmsUserAuth mmsUserAuth);

    /**
     * 批量删除用户认证
     * 
     * @param ids 需要删除的用户认证主键集合
     * @return 结果
     */
    public int deleteMmsUserAuthByIds(Long[] ids);

    /**
     * 删除用户认证信息
     * 
     * @param id 用户认证主键
     * @return 结果
     */
    public int deleteMmsUserAuthById(Long id);

    /**
     * 获取用户id列表信息
     * @return
     */
    List<Long> selectMmsUserIds();
}
