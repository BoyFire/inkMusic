package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歌手Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
public interface MmsSingerMapper 
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
     * 删除歌手
     * 
     * @param id 歌手主键
     * @return 结果
     */
    public int deleteMmsSingerById(Long id);

    /**
     * 批量删除歌手
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMmsSingerByIds(Long[] ids);


    /**
     * 通过歌手id查询歌手名字
     *
     * @param singerId 歌手id
     * @return 结果
     */
    public String selectSingerNameBySingerId(@Param("singerId") Long singerId);

    /**
     * 获取所有歌手姓名及id
     * @return 结果
     */
    List<SimpleSinger> selectSimpleSinger();
}
