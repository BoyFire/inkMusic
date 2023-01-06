package com.ruoyi.music.service;

import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.vo.front.SimpleSinger;

import java.util.List;

/**
 * 歌手Service接口
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
public interface IMmsSingerService 
{
    /**
     * 查询歌手
     * 
     * @param id 歌手主键
     * @return 歌手
     */
    public MmsSinger selectMmsSingerById(Long id);

    /**
     * 查询歌手列表
     * 
     * @param mmsSinger 歌手
     * @return 歌手集合
     */
    public List<MmsSinger> selectMmsSingerList(MmsSinger mmsSinger);

    /**
     * 新增歌手
     * 
     * @param mmsSinger 歌手
     * @return 结果
     */
    public int insertMmsSinger(MmsSinger mmsSinger);

    /**
     * 修改歌手
     * 
     * @param mmsSinger 歌手
     * @return 结果
     */
    public int updateMmsSinger(MmsSinger mmsSinger);

    /**
     * 批量删除歌手
     * 
     * @param ids 需要删除的歌手主键集合
     * @return 结果
     */
    public int deleteMmsSingerByIds(Long[] ids);

    /**
     * 删除歌手信息
     * 
     * @param id 歌手主键
     * @return 结果
     */
    public int deleteMmsSingerById(Long id);

    /**
     * 获取歌手id及姓名列表
     * @return  结果
     */
    List<SimpleSinger> selectSimpleSingerList();
}
