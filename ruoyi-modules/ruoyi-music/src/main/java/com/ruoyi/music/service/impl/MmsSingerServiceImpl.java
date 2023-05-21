package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.mapper.MmsSingerMapper;
import com.ruoyi.music.mapper.TempSingerMapper;
import com.ruoyi.music.service.IMmsSingerService;
import com.ruoyi.music.utils.HttpUtils;
import com.ruoyi.music.vo.front.SimpleSinger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 歌手Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-16
 */
@Service
public class MmsSingerServiceImpl implements IMmsSingerService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MmsSingerMapper mmsSingerMapper;
    @Resource
    private TempSingerMapper tempSingerMapper;

    /**
     * 查询歌手
     *
     * @param id 歌手主键
     * @return 歌手
     */
    @Override
    public MmsSinger selectMmsSingerById(Long id) {
        return mmsSingerMapper.selectMmsSingerById(id);
    }

    /**
     * 查询歌手列表
     *
     * @param mmsSinger 歌手
     * @return 歌手
     */
    @Override
    public List<MmsSinger> selectMmsSingerList(MmsSinger mmsSinger) {
        return mmsSingerMapper.selectMmsSingerList(mmsSinger);
    }

    /**
     * 新增歌手
     *
     * @param mmsSinger 歌手
     * @return 结果
     */
    @Override
    public int insertMmsSinger(MmsSinger mmsSinger) {
        RedisAtomicLong counter = new RedisAtomicLong("mms_dict:ids:mms_singer_id", redisTemplate.getConnectionFactory());
        long singerId = counter.addAndGet(1);
        mmsSinger.setSingerId(singerId);
        if (mmsSinger.getSingerType() == null) {
            mmsSinger.setSingerType(0);
        }
        if (mmsSinger.getSingerCountry() == null) {
            mmsSinger.setSingerCountry(0);
        }
        if (mmsSinger.getSingerStatus() == null) {
            mmsSinger.setSingerStatus(1);
        }
        mmsSinger.setCreateBy(SecurityUtils.getUsername());
        mmsSinger.setCreateTime(DateUtils.getNowDate());
        return mmsSingerMapper.insertMmsSinger(mmsSinger);
    }

    /**
     * 修改歌手
     *
     * @param mmsSinger 歌手
     * @return 结果
     */
    @Override
    public int updateMmsSinger(MmsSinger mmsSinger) {
        mmsSinger.setEditBy(SecurityUtils.getUsername());
        mmsSinger.setEditTime(DateUtils.getNowDate());
        return mmsSingerMapper.updateMmsSinger(mmsSinger);
    }

    /**
     * 批量删除歌手
     *
     * @param ids 需要删除的歌手主键
     * @return 结果
     */
    @Override
    public int deleteMmsSingerByIds(Long[] ids) {
        return mmsSingerMapper.deleteMmsSingerByIds(ids);
    }

    /**
     * 删除歌手信息
     *
     * @param id 歌手主键
     * @return 结果
     */
    @Override
    public int deleteMmsSingerById(Long id) {
        return mmsSingerMapper.deleteMmsSingerById(id);
    }

    @Override
    public List<SimpleSinger> selectSimpleSingerList() {
        return mmsSingerMapper.selectSimpleSinger();
    }

    @Override
    public List<SimpleSinger> selectSimpleSingerListBySingerName(String singerName) {
        return mmsSingerMapper.selectSimpleSingerBySingerName(singerName);
    }

    @Override
    public List<SimpleSinger> addSingerFromSongAr(List<Map<String, Object>> ars) {
        ArrayList<Long> singerIds = new ArrayList<>();
        ArrayList<SimpleSinger> result = new ArrayList<>();
        for (Map<String, Object> ar : ars) {
            Long apiId = Long.valueOf(String.valueOf(ar.get("id")));
            if (apiId == 0) continue;
            Long singerId = tempSingerMapper.selectSingerIdByApiId(apiId);
            if (singerId != null) {
                singerIds.add(singerId);
            } else {
                result.add(insertApiSinger(apiId));
            }
        }
        result.addAll(selectSimpleSingersBySingerIds(singerIds));
        return result;
    }

    @Override
    public List<SimpleSinger> selectSimpleSingersBySingerIds(List<Long> singerIds) {
        if (CollectionUtils.isEmpty(singerIds)) return null;
        return mmsSingerMapper.selectSimpleSingerBySingerIds(singerIds);
    }

    public SimpleSinger insertApiSinger(Long apiId) {
        if (apiId == 0L) return null;
        Map<Object, Object> detailMap = HttpUtils.httpGet("/artist/detail", "id", apiId.toString());
        if (String.valueOf(detailMap.get("code")).equals("404")) return null;

        Map<Object, Object> dataDetailMap = (Map<Object, Object>) detailMap.get("data");
        Map<Object, Object> artistDetail = (Map<Object, Object>) dataDetailMap.get("artist");

        String apiSingerId = String.valueOf(apiId);
        MmsSinger mmsSinger = new MmsSinger();
        //歌手名
        mmsSinger.setSingerName((String) artistDetail.get("name"));
        //歌手别名
        ArrayList<String> alias = (ArrayList<String>) artistDetail.get("alias");
        //歌手头像
        mmsSinger.setSingerImgUrl((String) artistDetail.get("cover"));
        //歌手专辑数
        mmsSinger.setSingerAlbumSize((Integer) artistDetail.get("albumSize"));
        //歌手歌曲数
        mmsSinger.setSingerMusicSize((Integer) artistDetail.get("musicSize"));
        //歌手fans
        mmsSinger.setSingerFanCount(0);
        //Creator
        mmsSinger.setCreateBy("admin");
        //添加歌手详情
        mmsSinger.setSingerDetail((String) artistDetail.get("briefDesc"));
        //歌手类型 歌手类型概率存在为空
        mmsSinger.setSingerType(0);
        // 歌手所属国家;1-华语, 2-欧美,3-日本, 4-韩国, 0-其他

        mmsSinger.setSingerCountry(0);
        if (mmsSinger.getSingerName().length() >= 60) return null;

        insertMmsSinger(mmsSinger);
        tempSingerMapper.insertSingerId(Long.valueOf(String.valueOf(apiSingerId)), mmsSinger.getSingerId());
        //歌手别名
        if (alias.size() > 0) {
            mmsSingerMapper.insertSingerAlias(mmsSinger.getSingerId(), alias);
        }
        SimpleSinger simpleSinger = new SimpleSinger();
        simpleSinger.setSingerId(mmsSinger.getSingerId());
        simpleSinger.setSingerName(mmsSinger.getSingerName());
        return simpleSinger;
    }

}
