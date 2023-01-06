package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.vo.front.SimpleUserVo;

import java.util.List;

/**
 * 用户Service接口
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
public interface IMmsUserService 
{
    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    public MmsUser selectMmsUserById(Long id);

    /**
     * 查询用户列表
     * 
     * @param mmsUser 用户
     * @return 用户集合
     */
    public List<MmsUser> selectMmsUserList(MmsUser mmsUser);

    /**
     * 新增用户
     * 
     * @param mmsUser 用户
     * @return 结果
     */
    public int insertMmsUser(MmsUser mmsUser);

    /**
     * 修改用户
     * 
     * @param mmsUser 用户
     * @return 结果
     */
    public int updateMmsUser(MmsUser mmsUser);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteMmsUserByIds(Long[] ids);

    /**
     * 删除用户信息
     * 
     * @param id 用户主键
     * @return 结果
     */
    public int deleteMmsUserById(Long id);

    /**
     * 修改用户状态
     *
     * @param mmsUser 用户
     * @return 结果
     */
    int updateUserStatus(MmsUser mmsUser);

    /**
     * 查询用户姓名及id
     * @return 结果
     */
    List<SimpleUserVo> selectSimpleUsers();
}
