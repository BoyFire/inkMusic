package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.mapper.MmsUserMapper;
import com.ruoyi.music.service.IMmsUserService;
import com.ruoyi.music.vo.front.SimpleUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
@Service
public class MmsUserServiceImpl implements IMmsUserService 
{
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MmsUserMapper mmsUserMapper;

    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public MmsUser selectMmsUserById(Long id)
    {
        return mmsUserMapper.selectMmsUserById(id);
    }

    /**
     * 查询用户列表
     * 
     * @param mmsUser 用户
     * @return 用户
     */
    @Override
    public List<MmsUser> selectMmsUserList(MmsUser mmsUser)
    {
        List<MmsUser> mmsUsers = mmsUserMapper.selectMmsUserList(mmsUser);
        return mmsUsers;
    }

    /**
     * 新增用户
     * 
     * @param mmsUser 用户
     * @return 结果
     */
    @Override
    public int insertMmsUser(MmsUser mmsUser)
    {
        RedisAtomicLong counter = new RedisAtomicLong("mms_dict:ids:mms_user_id", redisTemplate.getConnectionFactory());
        long userId = counter.addAndGet(1);
        mmsUser.setUserId(userId);
        mmsUser.setCreateBy(SecurityUtils.getUsername());
        mmsUser.setCreateTime(DateUtils.getNowDate());
        mmsUser.setEditTime(DateUtils.getNowDate());
        if (mmsUser.getUserStatus() ==null) {
            mmsUser.setUserStatus(0);
        }
        if (mmsUser.getUserGender() ==null){
            mmsUser.setUserGender(0);
        }
        if (mmsUser.getIsDel() == null){
            mmsUser.setIsDel(0);
        }
        return mmsUserMapper.insertMmsUser(mmsUser);
    }

    /**
     * 修改用户
     * 
     * @param mmsUser 用户
     * @return 结果
     */
    @Override
    public int updateMmsUser(MmsUser mmsUser)
    {
        mmsUser.setEditTime(DateUtils.getNowDate());
        mmsUser.setEditBy(SecurityUtils.getUsername());
        return mmsUserMapper.updateMmsUser(mmsUser);
    }

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteMmsUserByIds(Long[] ids)
    {
        return mmsUserMapper.deleteMmsUserByIds(ids);
    }

    /**
     * 删除用户信息
     * 
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deleteMmsUserById(Long id)
    {
        return mmsUserMapper.deleteMmsUserById(id);
    }

    /**
     *
     * 修改用户状态
     * @param mmsUser 用户
     * @return
     */
    @Override
    public int updateUserStatus(MmsUser mmsUser) {
        return mmsUserMapper.updateMmsUser(mmsUser);
    }

    @Override
    public List<SimpleUserVo> selectSimpleUsers() {
        return mmsUserMapper.selectSimpleUsers();
    }
}
