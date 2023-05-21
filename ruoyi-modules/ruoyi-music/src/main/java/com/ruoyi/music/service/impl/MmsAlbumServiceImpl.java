package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.mapper.MmsAlbumMapper;
import com.ruoyi.music.mapper.MmsSingerAlbumMapper;
import com.ruoyi.music.mapper.TempAlbumMapper;
import com.ruoyi.music.mapper.TempSingerMapper;
import com.ruoyi.music.service.IMmsAlbumService;
import com.ruoyi.music.service.IMmsSingerService;
import com.ruoyi.music.utils.HttpUtils;
import com.ruoyi.music.vo.front.AlbumParamsVo;
import com.ruoyi.music.vo.front.SimpleAlbum;
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
 * 专辑Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@Service
public class MmsAlbumServiceImpl implements IMmsAlbumService {
    @Autowired
    private MmsAlbumMapper mmsAlbumMapper;

    @Autowired
    private MmsSingerAlbumMapper mmsSingerAlbumMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private IMmsSingerService mmsSingerService;
    @Resource
    private TempAlbumMapper tempAlbumMapper;
    @Resource
    private TempSingerMapper tempSingerMapper;

    /**
     * 根据专辑名 返回专辑列表
     *
     * @param albumName 专辑名
     * @return 结果
     */
    @Override
    public List<SimpleAlbum> selectSimpleAlbumListByAlbumName(String albumName) {
        return mmsAlbumMapper.selectSimpleAlbumsByAlbumName(albumName);
    }

    @Override
    public List<SimpleAlbum> addAlbumFromSongAl(Map<String, Object> al) {
        Map<Object, Object> map = HttpUtils.httpGet("/album", "id", al.get("id").toString());
        Map<String, Object> map1 = (Map<String, Object>) map.get("album");
        MmsAlbum album = new MmsAlbum();
        album.setAlbumName((String) al.get("name"));
        album.setAlbumImgUrl((String) al.get("picUrl"));
        album.setAlbumType(String.valueOf(map1.get("type")).equals("专辑") ? "Album" : "Single");
        //3. 发行时间
        album.setCreateTime(DateUtils.parseDate(map1.get("publishTime")));
        //5 介绍
        album.setAlbumDesc(String.valueOf(map1.get("description")));
        //6. 专辑歌曲数
        album.setAlbumSize(Integer.valueOf(String.valueOf(map1.get("size"))));

        //8 获取作者
        ArrayList<Map<String, Object>> artists = (ArrayList<Map<String, Object>>) map1.get("artists");
        List<Long> singerIds = new ArrayList<>();
        if (artists.size() > 0) { // artists 长度为零
            for (Map<String, Object> artist : artists) {
                Long singerId = tempSingerMapper.selectSingerIdByApiId(Long.valueOf(String.valueOf(artist.get("id"))));
                if (singerId == null || singerId == 0) {
                    Long insertId = mmsSingerService.insertApiSinger(Long.valueOf(String.valueOf(artist.get("id")))).getSingerId();
                    if (insertId != -1L) {
                        singerIds.add(insertId);
                    }
                } else {
                    singerIds.add(singerId);
                }

            }
        } else {
            Map<String, Object> artist = (Map<String, Object>) map1.get("artist");
            singerIds.add(tempSingerMapper.selectSingerIdByApiId(Long.valueOf(String.valueOf(artist.get("id")))));
        }
        if (!CollectionUtils.isEmpty(singerIds) ) {
            //8.5. 做个存档备份
            insertMmsAlbum(album);
            //9. 专辑的作者
            mmsSingerAlbumMapper.insertSingerAlbum(album.getAlbumId(), singerIds);

            tempAlbumMapper.insertAlbumId(Long.valueOf(String.valueOf(al.get("id"))), album.getAlbumId()
            );
        }
        ArrayList<SimpleAlbum> result = new ArrayList<>();
        SimpleAlbum simpleAlbum = new SimpleAlbum();
        simpleAlbum.setAlbumId(album.getAlbumId());
        simpleAlbum.setAlbumName(album.getAlbumName());
        result.add(simpleAlbum);
        return result;
    }


    /**
     * 查询专辑
     *
     * @param id 专辑主键
     * @return 专辑
     */
    @Override
    public MmsAlbum selectMmsAlbumById(Long id) {
        return mmsAlbumMapper.selectMmsAlbumById(id);
    }

    /**
     * 查询专辑列表
     *
     * @param albumParamsVo 专辑
     * @return 专辑
     */
    @Override
    public List<MmsAlbum> selectMmsAlbumList(AlbumParamsVo albumParamsVo) {
        return mmsSingerAlbumMapper.selectMmsAlbumList(albumParamsVo);
    }

    /**
     * 新增专辑
     *
     * @param mmsAlbum 专辑
     * @return 结果
     */
    @Override
    public int insertMmsAlbum(MmsAlbum mmsAlbum) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("mms_dict:ids:mms_album_id", redisTemplate.getConnectionFactory());
        long albumId = redisAtomicLong.addAndGet(1);
        mmsAlbum.setAlbumId(albumId);
        if (mmsAlbum.getIsDel() == null) {
            mmsAlbum.setIsDel(0);
        }
        if (mmsAlbum.getAlbumStatus() == null) {
            mmsAlbum.setAlbumStatus(1);
        }
        mmsAlbum.setIsDel(0);
        mmsAlbum.setRevision(0);
        if (mmsAlbum.getCreateBy() == null) {
            mmsAlbum.setCreateBy(SecurityUtils.getUsername());
            mmsAlbum.setCreateTime(DateUtils.getNowDate());
        }
        return mmsAlbumMapper.insertMmsAlbum(mmsAlbum);
    }

    /**
     * 修改专辑
     *
     * @param mmsAlbum 专辑
     * @return 结果
     */
    @Override
    public int updateMmsAlbum(MmsAlbum mmsAlbum) {
        mmsAlbum.setEditBy(SecurityUtils.getUsername());
        mmsAlbum.setEditTime(DateUtils.getNowDate());
        return mmsAlbumMapper.updateMmsAlbum(mmsAlbum);
    }

    /**
     * 批量删除专辑
     *
     * @param ids 需要删除的专辑主键
     * @return 结果
     */
    @Override
    public int deleteMmsAlbumByIds(Long[] ids) {
        mmsSingerAlbumMapper.deleteMmsAlbumByAlbumId(ids);
        return mmsAlbumMapper.deleteMmsAlbumByIds(ids);
    }

    /**
     * 删除专辑信息
     *
     * @param id 专辑主键
     * @return 结果
     */
    @Override
    public int deleteMmsAlbumById(Long id) {
        return mmsAlbumMapper.deleteMmsAlbumById(id);
    }

    /**
     * 根据歌手id 获取 专辑名和id值
     *
     * @param singerId
     * @return
     */
    @Override
    public List<SimpleAlbum> selectSimpleAlbumsBySingerId(Long singerId) {
        return mmsAlbumMapper.selectSimpleAlbumsBySingerId(singerId);
    }

    @Override
    public List<SimpleAlbum> listSimpleAlbums() {
        return mmsAlbumMapper.listSimpleAlbum();
    }

    @Override
    public List<SimpleAlbum> selectSimpleAlbumsBySingers(List<SimpleSinger> singers) {
        return mmsSingerAlbumMapper.selectSimpleAlbumsBySingers(singers);
    }
}
