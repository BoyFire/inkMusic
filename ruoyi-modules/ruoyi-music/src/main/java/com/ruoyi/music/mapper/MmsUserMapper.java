package com.ruoyi.music.mapper;


import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.model.dto.MmsUserDTO;
import com.ruoyi.music.vo.front.SimpleUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-19
 */
public interface MmsUserMapper
{
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    public MmsUser selectMmsUserById(Long id);
    /**
     * 查询用户姓名
     *
     * @param userId 用户id
     * @return 用户
     */
    public String selectUserNameByUserId(@Param("userId") Long userId);

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
     * 删除用户
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deleteMmsUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsUserByIds(Long[] ids);


    /**
     * 查询用户id列表
     * @return
     */
    List<Long> selectMmsUserIdList();

    List<SimpleUserVo> selectSimpleUsers();

    /**
     * 获取简单用户列表 通过模糊用户名
     *
     * @param userName 用户名
     * @return 结果
     */
    List<SimpleUserVo> selectSimpleUsersByUserName(@Param("userName") String userName);


    /**
     * 获取用户信息
     */
    MmsUserDTO selectUserDTOByUserId(@Param("userId") Long userId);

    MmsUser selectMmsUserBySongListId(@Param("songListId") Long songListId);
}
