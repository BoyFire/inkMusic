package com.ruoyi.music.mapper;

import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
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


    /**
     * 添加歌手别名
     * @param singerId 歌手id
     * @param alias 别名列表
     * @return 结果
     */
    int insertSingerAlias(@Param("singerId") Long singerId, @Param("alias") ArrayList<String> alias);

    /**
     * 查询歌手别名
     * @param singerId 歌手id
     * @return 结果
     */
    List<String> selectSingerAliasBySingerId(@Param("singerId") Long singerId);

    /**
     * 更新歌手歌曲数
     *
     * @param singerId 歌手id
     * @param songSize 歌曲数
     * @return 结果
     */
    int updateSingerSongSize(@Param("singerId") Long singerId, @Param("songSize") int songSize);

    /**
     * 更新歌手歌曲数
     *
     * @param singerId 歌手id
     * @param albumSize 歌曲数
     * @return 结果
     */
    int updateSingerAlbumSize(@Param("singerId") Long singerId, @Param("albumSize") int albumSize);

    /**
     * 更新歌手歌曲数
     *
     * @param singerId 歌手id
     * @param fanSize 粉丝数
     * @return 结果
     */
    int updateSingeFanSize(@Param("singerId") Long singerId, @Param("fanSize") int fanSize);


    /**
     * 根据歌手姓名 模糊查询
     * @param singerName 歌手姓名
     * @return 结果
     */
    List<SimpleSinger> selectSimpleSingerBySingerName(@Param("singerName") String singerName);

    List<SimpleSinger> selectSimpleSingerBySingerIds(@Param("singerIds") List<Long> singerIds);
}
