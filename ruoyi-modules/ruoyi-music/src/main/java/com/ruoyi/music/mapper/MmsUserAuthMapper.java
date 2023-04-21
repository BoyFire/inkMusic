package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.entity.MmsUserAuth;
import com.ruoyi.music.model.dto.UserLoginDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户认证Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-19
 */
public interface MmsUserAuthMapper {
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
     * 删除用户认证
     *
     * @param id 用户认证主键
     * @return 结果
     */
    public int deleteMmsUserAuthById(Long id);

    /**
     * 批量删除用户认证
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsUserAuthByIds(Long[] ids);

    /**
     * 根据用户id 和 用户认证类型查询 用户认证
     *
     * @param userId       用户id
     * @param userAuthType 用户认证类型
     * @return 结果
     */
    MmsUserAuth selectMmsUserByUserIdAndUserAuthType(@Param("userId") Long userId, @Param("userAuthType") Integer userAuthType);


    /**
     * 根据用户id 返回用户认证类型
     *
     * @param userId 用户id
     * @return 结果
     */
    List<Integer> selectMmsUserAuthTypesById(@Param("userId") Long userId);

    /**
     * 登录
     *
     * @param userLoginDTO VO
     * @return 结果
     */
    MmsUser getUserInfo(UserLoginDTO userLoginDTO);

    /**
     * 获取用户密码
     *
     * @return 结果
     */
    String getUserPassword(UserLoginDTO userLoginDTO);
}
