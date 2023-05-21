package com.ruoyi.music.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.mapper.*;
import com.ruoyi.music.service.IMmsAlbumService;
import com.ruoyi.music.service.IMmsSingerService;
import com.ruoyi.music.service.IMmsSongService;
import com.ruoyi.music.utils.HttpUtils;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
import com.ruoyi.music.vo.front.SimpleSongVo;
import com.ruoyi.music.vo.front.SongParamsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 歌曲Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@Service
public class MmsSongServiceImpl implements IMmsSongService {
    @Autowired
    private MmsSongMapper mmsSongMapper;

    @Autowired
    private MmsSongSingerMapper mmsSongSingerMapper;
    @Autowired
    private MmsSongAlbumMapper mmsSongAlbumMapper;
    @Autowired
    private MmsSongAliasMapper mmsSongAliasMapper;
    @Autowired
    private MmsAlbumMapper mmsAlbumMapper;
    @Autowired
    private MmsSingerMapper mmsSingerMapper;
    @Autowired
    private MmsSongTagMapper mmsSongTagMapper;
    @Resource
    private TempSongMapper tempSongMapper;
    @Resource
    private IMmsAlbumService mmsAlbumService;
    @Resource
    private IMmsSingerService mmsSingerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据歌曲名 查询简单歌曲
     *
     * @param songName 歌曲名
     * @return 结果
     */
    @Override
    public List<SimpleSongVo> selectSimpleSongsBySongName(String songName) {
        return mmsSongMapper.selectSimpleSongsBySongName(songName);
    }

    @Override
    public List<MmsSong> selectMmsSongsBySongListId(Long songListId) {
        return mmsSongMapper.selectMmsSongsBySongListId(songListId);
    }

    @Override
    public String getRedirectUrl(String url, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        // 移除末尾的分号和空格
        sb.delete(sb.length() - 2, sb.length());


        HttpResponse httpResponse = HttpRequest.get(url).header("cookie", sb.toString()).execute();
        return httpResponse.header("Location");
    }

    @Override
    public Long selectTempSongId(String apiSongId) {
        return tempSongMapper.selectSongIdByApi(Long.valueOf(apiSongId));
    }

    @Override
    public MmsSong addSong(String apiSongId) {
        Map<Object, Object> res = HttpUtils.httpGet("/song/detail", "ids", apiSongId);
        ArrayList<Map<String, Object>> songs = (ArrayList<Map<String, Object>>) res.get("songs");
        Map<String, Object> song = songs.get(0);
        List<Map<String,Object>> ars = JSON.parseArray(JSON.toJSONString(song.get("ar")), (Type) Map.class);
        Map<String, Object> al = JSON.parseObject(JSON.toJSONString(song.get("al")), Map.class);
        List<SimpleAlbum> albums =  mmsAlbumService.addAlbumFromSongAl( al);
        List<SimpleSinger>  singers =  mmsSingerService.addSingerFromSongAr(ars);


        MmsSong mmsSong = new MmsSong();
        mmsSong.setCreateBy("admin");
        //1. 名字
        mmsSong.setSongName(String.valueOf(song.get("name")));
        //2. 时长
        mmsSong.setSongDuration(Long.valueOf(String.valueOf(song.get("dt"))));
        //3. 歌曲URL
        mmsSong.setSongUrl("https://music.163.com/song/media/outer/url?id=" + apiSongId + ".mp3");
        //4. 图像 专辑图片
        mmsSong.setSongImgUrl(String.valueOf(al.get("picUrl")));
        mmsSong.setSingers(singers);
        mmsSong.setAlbums(albums);
        insertMmsSong(mmsSong);
        tempSongMapper.insertSongId(Long.valueOf(apiSongId), mmsSong.getSongId());
        return mmsSong;
    }

    /**
     * 查询歌曲
     *
     * @param id 歌曲主键
     * @return 歌曲
     */
    @Override
    public MmsSong selectMmsSongById(Long id) {
        return mmsSongMapper.selectMmsSongById(id);
    }

    /**
     * 查询歌曲列表
     *
     * @param songParams 歌曲
     * @return 歌曲
     */
    @Override
    public List<MmsSong> selectMmsSongList(SongParamsVo songParams) {
        return mmsSongMapper.selectMmsSongList(songParams);
    }

    /**
     * 新增歌曲
     *
     * @param mmsSong 歌曲
     * @return 结果
     */
    @Override
    public int insertMmsSong(MmsSong mmsSong) {
        RedisAtomicLong atomicLong = new RedisAtomicLong("mms_dict:ids:mms_song_id", redisTemplate.getConnectionFactory());
        long songId = atomicLong.addAndGet(1);
        mmsSong.setSongId(songId);
        mmsSong.setIsDel(0);
        if (mmsSong.getSongStatus() == null) {
            mmsSong.setSongStatus(1);
        }
        if (mmsSong.getCreateBy() == null) {
            mmsSong.setCreateBy(SecurityUtils.getUsername());
        }
        mmsSong.setCreateTime(DateUtils.getNowDate());
        mmsSongMapper.insertMmsSong(mmsSong);
        mmsSongAlbumMapper.insertSongAlbums(mmsSong.getSongId(), mmsSong.getAlbums());
        mmsAlbumMapper.updateAlbumsSize(mmsSong.getAlbums(), 1);
        mmsSongSingerMapper.insertSongSingers(mmsSong.getSongId(), mmsSong.getSingers());
        return mmsSingerMapper.updateSingerSongSize(mmsSong.getSongId(), 1);
    }

    /**
     * 修改歌曲
     *
     * @param mmsSong 歌曲
     * @return 结果
     */
    @Override
    public int updateMmsSong(MmsSong mmsSong) {
        mmsSong.setEditBy(SecurityUtils.getUsername());
        mmsSong.setEditTime(DateUtils.getNowDate());
        mmsSongAlbumMapper.deleteSongAlbumBySongId(mmsSong.getSongId());
        mmsSongAlbumMapper.insertSongAlbums(mmsSong.getSongId(), mmsSong.getAlbums());
        mmsSongSingerMapper.deleteSongSingerBySongId(mmsSong.getSongId());
        mmsSongSingerMapper.insertSongSingers(mmsSong.getSongId(), mmsSong.getSingers());
        return mmsSongMapper.updateMmsSong(mmsSong);
    }

    /**
     * 批量删除歌曲
     *
     * @param ids 需要删除的歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongByIds(Long[] ids) {
        return mmsSongMapper.deleteMmsSongByIds(ids);
    }

    /**
     * 删除歌曲信息
     *
     * @param id 歌曲主键
     * @return 结果
     */
    @Override
    public int deleteMmsSongById(Long id) {
        return mmsSongMapper.deleteMmsSongById(id);
    }

    @Override
    public List<SimpleSongVo> listSimpleSong() {
        return mmsSongMapper.listSimpleSong();
    }

    @Override
    public MmsSong selectMmsSongBySongId(Long songId) {
        return mmsSongMapper.selectSongBySongId(songId);
    }
}
