package com.ruoyi.music.service;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.model.MmsLoginUser;
import com.ruoyi.music.model.dto.MmsUserDTO;
import com.ruoyi.music.model.dto.UserLoginDTO;
import com.ruoyi.music.model.dto.UserRegisterDTO;
import com.ruoyi.music.vo.front.SimpleUserVo;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author ruoyi
 * @date 2022-10-16
 */
public interface IMmsUserService {
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
     *
     * @return 结果
     */
    List<SimpleUserVo> selectSimpleUsers();

    /**
     * 获取简单用户列表 通过姓名模糊查询
     *
     * @param userName 姓名
     * @return 结果
     */
    List<SimpleUserVo> selectSimpleUserListByName(String userName);


    /**
     * 用户登录
     *
     * @param userLoginDTO VO
     * @return 结果
     */
    MmsLoginUser login(UserLoginDTO userLoginDTO) throws ServiceException;

    /**
     * 客户端用户注册
     *
     * @param userRegisterDTO 表单信息
     * @return 结果
     */
    int insertMmsUser(UserRegisterDTO userRegisterDTO) throws ServiceException;

    /**
     * 获取客户端用户信息
     *
     */
    MmsUserDTO selectMmsUserByUserId(Long userId);
}
