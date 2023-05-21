package com.ruoyi.music.service.impl;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.mapper.MmsUserMapper;
import com.ruoyi.music.model.MmsLoginUser;
import com.ruoyi.music.model.dto.MmsUserDTO;
import com.ruoyi.music.model.dto.UserLoginDTO;
import com.ruoyi.music.model.dto.UserRegisterDTO;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.service.IMmsUserAuthService;
import com.ruoyi.music.service.IMmsUserService;
import com.ruoyi.music.vo.front.SimpleUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-16
 */
@Service
public class MmsUserServiceImpl implements IMmsUserService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MmsUserMapper mmsUserMapper;

    @Autowired
    private IMmsUserAuthService mmsUserAuthService;


    @Autowired
    private IMmsSongListService mmsSongListService;


    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public MmsUser selectMmsUserById(Long id) {
        return mmsUserMapper.selectMmsUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param mmsUser 用户
     * @return 用户
     */
    @Override
    public List<MmsUser> selectMmsUserList(MmsUser mmsUser) {
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
    public int insertMmsUser(MmsUser mmsUser) {
        generatedUserMate(mmsUser);
        if (SecurityUtils.getUsername() != null && "".equals(SecurityUtils.getUsername())) {
            mmsUser.setCreateBy(SecurityUtils.getUsername());
        } else {
            mmsUser.setCreateBy("admin");
        }
        int insertFlag = mmsUserMapper.insertMmsUser(mmsUser);
        mmsSongListService.insertNewUserLover(mmsUser);
        return insertFlag;
    }


    /**
     * 修改用户
     *
     * @param mmsUser 用户
     * @return 结果
     */
    @Override
    public int updateMmsUser(MmsUser mmsUser) {
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
    public int deleteMmsUserByIds(Long[] ids) {
        return mmsUserMapper.deleteMmsUserByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deleteMmsUserById(Long id) {
        return mmsUserMapper.deleteMmsUserById(id);
    }

    /**
     * 修改用户状态
     *
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

    /**
     * 获取简单用户列表 通过姓名模糊查询
     *
     * @param userName 姓名
     * @return 结果
     */
    @Override
    public List<SimpleUserVo> selectSimpleUserListByName(String userName) {

        return mmsUserMapper.selectSimpleUsersByUserName(userName);
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO VO
     * @return 结果
     */
    @Override
    public MmsLoginUser login(UserLoginDTO userLoginDTO) throws ServiceException {
        return mmsUserAuthService.matches(userLoginDTO);
    }


    /**
     * 客户端用户注册
     *
     * @param userRegisterDTO 表单信息
     * @return 结果
     */
    @Override
    public int insertMmsUser(UserRegisterDTO userRegisterDTO) throws ServiceException {
        int rows =0;
        MmsUser mmsUser = new MmsUser();
        mmsUser.setUserNickname(userRegisterDTO.getUserNickname());
        mmsUser.setCreateBy("user");
        generatedUserMate(mmsUser);

        // 创建用户信息
        rows += mmsUserMapper.insertMmsUser(mmsUser);
        // 登记账号密码
        rows += mmsUserAuthService.insertMmsUserAuth(mmsUser,userRegisterDTO);
        //创建 喜欢的歌单
        rows += mmsSongListService.insertNewUserLover(mmsUser);
        return rows;
    }

    /**
     * 获取客户端用户信息
     */
    @Override
    public MmsUserDTO selectMmsUserByUserId(Long userId) {
        return mmsUserMapper.selectUserDTOByUserId(userId);
    }

    @Override
    public MmsUser selectMmsUserBySongListId(Long songListId) {
        return mmsUserMapper.selectMmsUserBySongListId(songListId);
    }

    /**
     * 生成用户通用元素
     * 主要涉及 userId, 未指定gender, is_del, revision, userStatus
     */
    private void generatedUserMate(MmsUser mmsUser) {
        RedisAtomicLong counter = new RedisAtomicLong("mms_dict:ids:mms_user_id", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        long userId = counter.addAndGet(1);
        mmsUser.setUserId(userId);
        mmsUser.setRevision(0);
        mmsUser.setCreateTime(DateUtils.getNowDate());
        if (mmsUser.getUserStatus() == null) {
            mmsUser.setUserStatus(1);
        }
        if (mmsUser.getUserGender() == null) {
            mmsUser.setUserGender(0);
        }
        if (mmsUser.getIsDel() == null) {
            mmsUser.setIsDel(0);
        }
    }
}
