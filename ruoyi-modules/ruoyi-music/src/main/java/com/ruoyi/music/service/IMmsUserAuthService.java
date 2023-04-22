package com.ruoyi.music.service;


import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.entity.MmsUserAuth;
import com.ruoyi.music.model.MmsLoginUser;
import com.ruoyi.music.model.dto.UserLoginDTO;
import com.ruoyi.music.model.dto.UserRegisterDTO;

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
    public int insertMmsUserAuth(MmsUserAuth mmsUserAuth) throws ServiceException;

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

    /**
     * 验证 用户登录
     */
    MmsLoginUser matches(UserLoginDTO userLoginDTO) throws ServiceException;

    /**
     * 客户端用户注册
     *
     * @param mmsUser 注册生成的用户类
     * @param userRegisterDTO 前端 注册信息
     * @return 结果
     */
    Integer insertMmsUserAuth(MmsUser mmsUser, UserRegisterDTO userRegisterDTO) throws ServiceException;
}
