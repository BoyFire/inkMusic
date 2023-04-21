package com.ruoyi.music.service.impl;


import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.entity.MmsUserAuth;
import com.ruoyi.music.mapper.MmsUserAuthMapper;
import com.ruoyi.music.mapper.MmsUserMapper;
import com.ruoyi.music.model.dto.UserLoginDTO;
import com.ruoyi.music.model.dto.UserRegisterDTO;
import com.ruoyi.music.service.IMmsUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户认证Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-19
 */
@Service
public class MmsUserAuthServiceImpl implements IMmsUserAuthService {
    @Autowired
    private MmsUserAuthMapper mmsUserAuthMapper;

    @Autowired
    private MmsUserMapper mmsUserMapper;

    /**
     * 查询用户认证
     *
     * @param id 用户认证主键
     * @return 用户认证
     */
    @Override
    public MmsUserAuth selectMmsUserAuthById(Long id) {
        return mmsUserAuthMapper.selectMmsUserAuthById(id);
    }

    /**
     * 查询用户认证列表
     *
     * @param mmsUserAuth 用户认证
     * @return 用户认证
     */
    @Override
    public List<MmsUserAuth> selectMmsUserAuthList(MmsUserAuth mmsUserAuth) {
        return mmsUserAuthMapper.selectMmsUserAuthList(mmsUserAuth);
    }

    /**
     * 新增用户认证
     *
     * @param mmsUserAuth 用户认证
     * @return 结果
     */
    @Override
    public int insertMmsUserAuth(MmsUserAuth mmsUserAuth) throws ServiceException {
        // 检查登录方式的是否存在相同账号
        MmsUserAuth auth = new MmsUserAuth();
        auth.setUserAuthType(mmsUserAuth.getUserAuthType());
        auth.setUserAuthLogin(mmsUserAuth.getUserAuthLogin());
        List<MmsUserAuth> mmsUserAuths = mmsUserAuthMapper.selectMmsUserAuthList(auth);
        if (!mmsUserAuths.isEmpty()){
            throw new ServiceException("当前账号已存在,请重新输入");
        }

        //检测用户当前登录方式是否存在账号
        MmsUserAuth userAuth = mmsUserAuthMapper.selectMmsUserByUserIdAndUserAuthType(mmsUserAuth.getUserId(), mmsUserAuth.getUserAuthType());
        if (userAuth!=null){
            throw new ServiceException("当前账号登录方式已存在, 请重新选择");
        }

        mmsUserAuth.setCreateBy(SecurityUtils.getUsername()==null?"admin":SecurityUtils.getUsername());
        generateUserAuthMate(mmsUserAuth);
        return mmsUserAuthMapper.insertMmsUserAuth(mmsUserAuth);
    }

    /**
     * 生成用户认证的基础属性
     */
    private void generateUserAuthMate(MmsUserAuth mmsUserAuth) {
        if (mmsUserAuth.getUserAuthStatus() == null){
            mmsUserAuth.setUserAuthStatus(1);
        }
        mmsUserAuth.setUserAuthPassword(SecurityUtils.encryptPassword(mmsUserAuth.getUserAuthPassword()));
        mmsUserAuth.setCreateTime(DateUtils.getNowDate());
        mmsUserAuth.setRevision(0);
        mmsUserAuth.setIsDel(0);
    }

    /**
     * 修改用户认证
     *
     * @param mmsUserAuth 用户认证
     * @return 结果
     */
    @Override
    public int updateMmsUserAuth(MmsUserAuth mmsUserAuth) {
        MmsUserAuth userAuth = mmsUserAuthMapper.selectMmsUserByUserIdAndUserAuthType(mmsUserAuth.getUserId(), mmsUserAuth.getUserAuthType());
        //1. 修改密码
        if (!userAuth.getUserAuthPassword().equals(mmsUserAuth.getUserAuthPassword())) {
            mmsUserAuth.setUserAuthPassword(SecurityUtils.encryptPassword(mmsUserAuth.getUserAuthPassword()));
        }
        mmsUserAuth.setUpdateBy(SecurityUtils.getUsername());
        mmsUserAuth.setEditTime(DateUtils.getNowDate());
        return mmsUserAuthMapper.updateMmsUserAuth(mmsUserAuth);
    }

    /**
     * 批量删除用户认证
     *
     * @param ids 需要删除的用户认证主键
     * @return 结果
     */
    @Override
    public int deleteMmsUserAuthByIds(Long[] ids) {
        return mmsUserAuthMapper.deleteMmsUserAuthByIds(ids);
    }

    /**
     * 删除用户认证信息
     *
     * @param id 用户认证主键
     * @return 结果
     */
    @Override
    public int deleteMmsUserAuthById(Long id) {
        return mmsUserAuthMapper.deleteMmsUserAuthById(id);
    }

    /**
     * 获取用户id列表
     *
     * @return 结果
     */
    @Override
    public List<Long> selectMmsUserIds() {
        return mmsUserMapper.selectMmsUserIdList();
    }

    /**
     * 验证 用户登录
     *
     * @param userLoginDTO
     */
    @Override
    public MmsUser matches(UserLoginDTO userLoginDTO) throws ServiceException {
        if (StringUtils.isAnyBlank(userLoginDTO.getUsername(), userLoginDTO.getPassword())) {
            throw new ServiceException("用户名/密码必须填写");
        }
        MmsUser userInfo = mmsUserAuthMapper.getUserInfo(userLoginDTO);
        if (userInfo == null) {
            throw new ServiceException("账号不存在,请重新输入");
        }
        if (userInfo.getUserStatus() == 0) {
            throw new ServiceException("账户已停用");
        }

        String rawPassword = mmsUserAuthMapper.getUserPassword(userLoginDTO);
        if (!SecurityUtils.matchesPassword(userLoginDTO.getPassword(),rawPassword)){
            throw new ServiceException("账号或密码不正确");
        }
        return userInfo;
    }

    /**
     * 客户端用户注册
     *
     * @param mmsUser         注册生成的用户类
     * @param userRegisterDTO 前端 注册信息
     * @return 结果
     */
    @Override
    public Integer insertMmsUserAuth(MmsUser mmsUser, UserRegisterDTO userRegisterDTO) throws ServiceException {
        MmsUserAuth mmsUserAuth = new MmsUserAuth();
        //用户认证方式;0-账号,1-邮箱, 2-手机
        mmsUserAuth.setUserAuthType(0);
        mmsUserAuth.setUserAuthLogin(userRegisterDTO.getUserLogin());
        List<MmsUserAuth> mmsUserAuths = mmsUserAuthMapper.selectMmsUserAuthList(mmsUserAuth);
        if (!mmsUserAuths.isEmpty()){
            throw new ServiceException("当前账号已存在,请重新输入");
        }
        mmsUserAuth.setUserAuthPassword(userRegisterDTO.getPwd());
        mmsUserAuth.setUserId(mmsUser.getUserId());
        mmsUserAuth.setCreateBy("user");
        generateUserAuthMate(mmsUserAuth);
        return mmsUserAuthMapper.insertMmsUserAuth(mmsUserAuth);
    }
}
