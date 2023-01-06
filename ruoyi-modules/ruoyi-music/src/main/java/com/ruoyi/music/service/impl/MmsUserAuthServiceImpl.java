package com.ruoyi.music.service.impl;


import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsUserAuth;
import com.ruoyi.music.mapper.MmsUserAuthMapper;
import com.ruoyi.music.mapper.MmsUserMapper;
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
    public int insertMmsUserAuth(MmsUserAuth mmsUserAuth) {
        MmsUserAuth userAuth = mmsUserAuthMapper.selectMmsUserByUserIdAndUserAuthType(mmsUserAuth.getUserId(), mmsUserAuth.getUserAuthType());
        if (userAuth!=null){
            return 0;
        }
        if (mmsUserAuth.getUserAuthStatus() == null){
            mmsUserAuth.setUserAuthStatus(1);
        }
        mmsUserAuth.setUserAuthPassword(SecurityUtils.encryptPassword(mmsUserAuth.getUserAuthPassword()));
        mmsUserAuth.setCreateBy(SecurityUtils.getUsername());
        mmsUserAuth.setCreateTime(DateUtils.getNowDate());
        return mmsUserAuthMapper.insertMmsUserAuth(mmsUserAuth);
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
}
