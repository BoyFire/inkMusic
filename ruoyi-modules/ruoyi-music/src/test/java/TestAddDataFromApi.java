import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.Temp.ApiAlbum;
import com.ruoyi.music.Temp.ApiSinger;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.entity.MmsMovie;
import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.mapper.*;
import com.ruoyi.music.service.IMmsAlbumService;
import com.ruoyi.music.service.IMmsMovieService;
import com.ruoyi.music.service.IMmsSongService;
import com.ruoyi.music.service.impl.MmsSingerServiceImpl;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestAddDataFromApi {
    @Autowired
    private MmsSingerMapper mmsSingerMapper;
    @Autowired
    private TempSingerMapper tempSingerMapper;

    @Autowired
    private MmsSingerServiceImpl mmsSingerService;

    @Autowired
    private IMmsAlbumService mmsAlbumService;
    @Autowired
    private IMmsSongService mmsSongService;

    @Autowired
    private MmsSongMvMapper mmsSongMvMapper;
    @Autowired
    private MmsSongAliasMapper mmsSongAliasMapper;

    @Autowired
    private TempAlbumMapper tempAlbumMapper;
    @Autowired
    private TempSongMapper tempSongMapper;

    @Autowired
    private MmsSingerAlbumMapper mmsSingerAlbumMapper;

    @Autowired
    private IMmsMovieService mmsMovieService;

    @Autowired
    private MmsSongLyricMapper mmsSongLyricMapper;

    @Test
    public void addSinger() {
        Map<Object, Object> map = null;
        try {
            /* type 1:男歌手 2:女歌手 3:乐队  ,2,3 */
            int[] types = new int[]{3};
            /* 7华语 96欧美 8:日本 16韩国 0:其他 */
            int[] areas = new int[]{0};
            Integer sum = 0;
            Integer limit = 50;
            Integer offset = 329;
            for (int type : types) {
                for (int area : areas) {
                    do {
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("type", String.valueOf(type)));
                        params.add(new BasicNameValuePair("area", String.valueOf(area)));
                        params.add(new BasicNameValuePair("initial", "-1"));
                        params.add(new BasicNameValuePair("limit", limit.toString()));
                        params.add(new BasicNameValuePair("offset", offset.toString()));
                        URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                .setPath("/artist/list").setParameters(params).build();
                        map = doGet(uri.toString());

                        ArrayList<Map<String, Object>> artists = (ArrayList<Map<String, Object>>) map.get("artists");
                        for (Map<String, Object> artist : artists) {

                            //获取歌手详细信息
                            List<NameValuePair> detailParams = new ArrayList<>();
                            detailParams.add(new BasicNameValuePair("id", String.valueOf(artist.get("id"))));
                            URI uriDetail = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                    .setPath("/artist/detail").setParameters(detailParams).build();
                            Map<Object, Object> detailMap = doGet(uriDetail.toString());
                            Map<Object, Object> dataDetailMap = (Map<Object, Object>) detailMap.get("data");
                            Map<Object, Object> artistDetail = (Map<Object, Object>) dataDetailMap.get("artist");
                            String apiSingerId = String.valueOf(artist.get("id"));
                            MmsSinger mmsSinger = new MmsSinger();
                            //歌手名
                            mmsSinger.setSingerName((String) artist.get("name"));
                            //歌手别名
                            ArrayList<String> alias = (ArrayList<String>) artist.get("alias");
                            //歌手头像
                            mmsSinger.setSingerImgUrl((String) artist.get("picUrl"));
                            //歌手专辑数
                            mmsSinger.setSingerAlbumSize((Integer) artist.get("albumSize"));
                            //歌手歌曲数
                            mmsSinger.setSingerMusicSize((Integer) artist.get("musicSize"));
                            //歌手fans
                            mmsSinger.setSingerFanCount((Integer) artist.get("fansCount"));
                            //Creator
                            mmsSinger.setCreateBy("admin");
                            //添加歌手详情
                            mmsSinger.setSingerDetail((String) artistDetail.get("briefDesc"));
                            //歌手类型 歌手类型概率存在为空
                            mmsSinger.setSingerType(type);
                            // 歌手所属国家;1-华语, 2-欧美,3-日本, 4-韩国, 0-其他
                            switch (area) {
                                case 7:
                                    mmsSinger.setSingerCountry(1);
                                    break;
                                case 96:
                                    mmsSinger.setSingerCountry(2);
                                    break;
                                case 8:
                                    mmsSinger.setSingerCountry(3);
                                    break;
                                case 16:
                                    mmsSinger.setSingerCountry(4);
                                    break;
                                default:
                                    mmsSinger.setSingerCountry(0);
                            }

                            mmsSingerService.insertMmsSinger(mmsSinger);
                            tempSingerMapper.insertSingerId(Long.valueOf(String.valueOf(apiSingerId)), mmsSinger.getSingerId());
                            //歌手别名
                            if (alias.size() > 0) {
                                mmsSingerMapper.insertSingerAlias(mmsSinger.getSingerId(), alias);
                            }
                        }
                        if (String.valueOf(map.get("more")).equals("true")) {
                            offset += limit;
                            sum += limit;
                            if (sum > 500) {
                                sum = 0;
                                Thread.sleep(1000);
                            }
                        } else {
                            break;
                        }
                    } while (true);
                    offset = 0;
                }
                offset = 0;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Long insertApiSinger(Long apiId) throws URISyntaxException {
        System.out.println("不存在的歌手id==>" + apiId);
        if (apiId == 0) {
            return -1L;
        }
        //获取歌手详细信息
        List<NameValuePair> detailParams = new ArrayList<>();
        detailParams.add(new BasicNameValuePair("id", String.valueOf(apiId)));
        URI uriDetail = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                .setPath("/artist/detail").setParameters(detailParams).build();
        Map<Object, Object> detailMap = doGet(uriDetail.toString());
        if (String.valueOf(detailMap.get("code")).equals("404")) {
            return -1L;
        }
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
        if (mmsSinger.getSingerName().length() >= 60) {
            return -1L;
        }


        mmsSingerService.insertMmsSinger(mmsSinger);
        tempSingerMapper.insertSingerId(Long.valueOf(String.valueOf(apiSingerId)), mmsSinger.getSingerId());
        //歌手别名
        if (alias.size() > 0) {
            mmsSingerMapper.insertSingerAlias(mmsSinger.getSingerId(), alias);
        }
        return mmsSinger.getSingerId();
    }

    @Test
    public void addAlbum() {
        Integer offset = 59; // 歌手的第offset个专辑
        Integer offset1 = 18560; //歌手
        Integer limit = 50;

        Map<Object, Object> map;
        List<ApiSinger> ApiSingers;
        do {
            ApiSingers = tempSingerMapper.selectApiSinger(offset1, limit);
            for (ApiSinger apiSinger : ApiSingers) {
                do {
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("id", String.valueOf(apiSinger.getApiId())));
                    params.add(new BasicNameValuePair("limit", limit.toString()));
                    params.add(new BasicNameValuePair("offset", offset.toString()));
                    URI uri;
                    try {
                        uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                .setPath("/artist/album").setParameters(params).build();
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    map = doGet(uri.toString());
                    ArrayList<Map<String, Object>> hotAlbums = (ArrayList<Map<String, Object>>) map.get("hotAlbums");
                    if (hotAlbums == null || hotAlbums.isEmpty()) continue;
                    for (Map<String, Object> hotAlbum : hotAlbums) {
                        log.info("歌手 " + offset1 + "===>" + apiSinger.getSingerId() + "的===>第 " + offset + " 的专辑id:" + hotAlbum.get("id"));
                        offset += 1;

                        MmsAlbum mmsAlbum = new MmsAlbum();
                        //1. 专辑图片
                        mmsAlbum.setAlbumImgUrl(String.valueOf(hotAlbum.get("picUrl")));
                        //2.type
                        mmsAlbum.setAlbumType(String.valueOf(hotAlbum.get("type")).equals("专辑") ? "Album" : "Single");
                        //3. 发行时间
                        mmsAlbum.setCreateTime(DateUtils.parseDate(hotAlbum.get("publishTime")));
                        //5 介绍
                        mmsAlbum.setAlbumDesc(String.valueOf(hotAlbum.get("description")));
                        //6. 专辑歌曲数
                        mmsAlbum.setAlbumSize(Integer.valueOf(String.valueOf(hotAlbum.get("size"))));
                        //7. 专辑名
                        mmsAlbum.setAlbumName(String.valueOf(hotAlbum.get("name")));
                        if (mmsAlbum.getAlbumName().length() >= 100) {
                            continue;
                        }
                        //8 获取作者
                        ArrayList<Map<String, Object>> artists = (ArrayList<Map<String, Object>>) hotAlbum.get("artists");
                        List<Long> singerIds = new ArrayList<>();
                        if (artists.size() > 0) { // artists 长度为零
                            for (Map<String, Object> artist : artists) {
                                Long singerId = tempSingerMapper.selectSingerIdByApiId(Long.valueOf(String.valueOf(artist.get("id"))));
                                if (singerId == null || singerId == 0) {
                                    try {
                                        Long insertId = insertApiSinger(Long.valueOf(String.valueOf(artist.get("id"))));
                                        if (insertId != -1L) {
                                            singerIds.add(insertId);
                                        }
                                    } catch (URISyntaxException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    singerIds.add(singerId);
                                }

                            }
                        } else {
                            Map<String, Object> artist = (Map<String, Object>) hotAlbum.get("artist");
                            singerIds.add(tempSingerMapper.selectSingerIdByApiId(Long.valueOf(String.valueOf(artist.get("id")))));
                        }
                        if (!singerIds.isEmpty() || singerIds.size() != 0) {
                            //8.5. 做个存档备份
                            mmsAlbumService.insertMmsAlbum(mmsAlbum);

                            //9. 专辑的作者
                            mmsSingerAlbumMapper.insertSingerAlbum(mmsAlbum.getAlbumId(), singerIds);

                            tempAlbumMapper.insertAlbumId(Long.valueOf(String.valueOf(hotAlbum.get("id"))), mmsAlbum.getAlbumId()
                            );

                        }
                    }

                } while (String.valueOf(map.get("more")).equals("true"));
                offset = 0;
                offset1 += 1;
            }

        } while (offset1 <= 60872);


    }

    @Test
    public void addSong() throws URISyntaxException {
        Integer offset = 61944; //专辑偏移量
        Integer offset1 = 0; //单个专辑内 歌曲偏移量
        Integer limit = 100;
        Integer number;// 用来获取已保存几首歌
        Integer lastAlbum = offset + 3000;

        URI uri;
        Map<Object, Object> map;
        List<ApiAlbum> apiAlbums;
        //专辑列表
        do {
            apiAlbums = tempAlbumMapper.selectApiAlbum(offset, limit);
            //单个专辑
            for (ApiAlbum apiAlbum : apiAlbums) {
                number = 0;
                do {
                    // 获取专辑信息
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("id", String.valueOf(apiAlbum.getApiId())));
                    uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000).setPath("/album").setParameters(params).build();
                    map = doGet(uri.toString());
                    if ("404".equals(String.valueOf(map.get("code")))) {
                        log.info("404, offset: " + offset);
                        offset++;
                        continue;
                    }
                    Map<String, Object> album = (Map<String, Object>) map.get("album");
                    log.info("albumApiId: " + apiAlbum.getApiId() + ",offset: " + offset);
                    Long albumApiSingerId = Long.valueOf(String.valueOf(((Map<String, Object>) album.get("artist")).get("id")));
                    Long albumSingerId = tempSingerMapper.selectSingerIdByApiId(albumApiSingerId);

                    List<Map<String, Object>> songs = (List<Map<String, Object>>) map.get("songs");

                    //专辑内部歌曲
                    for (Map<String, Object> song : songs) {
                        //api 歌曲id
                        Long apiId = Long.valueOf(String.valueOf(song.get("id")));
                        if (tempSongMapper.selectSongIdByApi(apiId) > 0 || number < offset1) {
                            log.info("歌曲已存在,apiId为: " + apiId + ",number: " + number);
                            number++;
                            continue;
                        }
                        log.info("第" + offset1 + "首,songApiId:" + apiId);
                        number++;
                        offset1++;

                        // 歌词 api
                        List<NameValuePair> lyricParams = new ArrayList<>();
                        lyricParams.add(new BasicNameValuePair("id", String.valueOf(apiId)));
                        uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                .setPath("/lyric").setParameters(lyricParams).build();
                        Map<String, Object> lrc = (Map<String, Object>) doGet(uri.toString()).get("lrc");
                        // mv api
                        Long apiMvId = Long.valueOf(String.valueOf(song.get("mv")));
                        Map<Object, Object> mvMap = null;
                        Map<String, Object> mvDetailMap = null;
                        if (apiMvId != null && apiMvId != 0) {
                            List<NameValuePair> mvParams = new ArrayList<>();
                            mvParams.add(new BasicNameValuePair("mvid", String.valueOf(apiMvId)));
                            uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                    .setPath("/mv/detail").setParameters(mvParams).build();
                            mvMap = doGet(String.valueOf(uri));

                            if (mvMap != null && "200".equals(String.valueOf(mvMap.get("code")))) {
                                List<NameValuePair> mvDetailParams = new ArrayList<>();
                                mvDetailParams.add(new BasicNameValuePair("id", String.valueOf(apiMvId)));
                                uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000)
                                        .setPath("/mv/url").setParameters(mvDetailParams).build();
                                mvDetailMap = (Map<String, Object>) doGet(String.valueOf(uri)).get("data");
                            }
                        }


                        MmsSong mmsSong = new MmsSong();
                        mmsSong.setCreateBy("admin");
                        //1. 名字
                        mmsSong.setSongName(String.valueOf(song.get("name")));
                        //2. 时长
                        mmsSong.setSongDuration(Long.valueOf(String.valueOf(song.get("dt"))));
                        //3. 歌曲URL
                        mmsSong.setSongUrl("https://music.163.com/song/media/outer/url?id=" + apiId + ".mp3");
                        //4. 图像 既专辑图片
                        mmsSong.setSongImgUrl(String.valueOf(album.get("picUrl")));
                        //7. 专辑信息
                        ArrayList<SimpleAlbum> simpleAlbums = new ArrayList<>();
                        SimpleAlbum simpleAlbum = new SimpleAlbum();
                        simpleAlbum.setAlbumId(apiAlbum.getAlbumId());
                        simpleAlbums.add(simpleAlbum);
                        mmsSong.setAlbums(simpleAlbums);

                        //8. 歌手信息
                        List<Map<String, Object>> ars = (List<Map<String, Object>>) song.get("ar");
                        List<SimpleSinger> singers = new ArrayList<>();

                        for (Map<String, Object> ar : ars) {
                            Long apiSingerId = Long.valueOf(String.valueOf(ar.get("id")));
                            Long singerId = tempSingerMapper.selectSingerIdByApiId(apiSingerId);
                            if (singerId == null || singerId == 0) {
                                singerId = insertApiSinger(apiSingerId);
                                if (singerId == -1L) {
                                    continue;
                                }
                            }
                            SimpleSinger simpleSinger = new SimpleSinger();
                            simpleSinger.setSingerId(singerId);
                            singers.add(simpleSinger);
                        }
                        if (singers.size() < 1) {
                            SimpleSinger simpleSinger = new SimpleSinger();
                            simpleSinger.setSingerId(albumSingerId);
                            singers.add(simpleSinger);
                        }
                        mmsSong.setSingers(singers);

                        mmsSongService.insertMmsSong(mmsSong);
                        log.info("歌曲添加完成");
                        //9. 在tempSong中保存一份
                        tempSongMapper.insertSongId(apiId, mmsSong.getSongId());

                        //10. 歌曲别名
                        List<String> alias = (List<String>) song.get("alia");
                        if (alias != null && alias.size() > 0) {
                            mmsSongAliasMapper.insertSongAlias(mmsSong.getSongId(), alias);
                        }
                        //11 歌词
                        if (lrc != null && lrc.get("lyric") != null) {
                            String lyric = String.valueOf(lrc.get("lyric"));
                            if (lyric != null && lyric.length() > 0) {
                                mmsSongLyricMapper.insertSongLyric(mmsSong.getSongId(), lyric);
                            }
                        }
                        log.info("歌词备份完成");

                        //12. mv
                        if (mvMap != null && "200".equals(String.valueOf(mvMap.get("code")))) {
                            Map<String, Object> data = (Map<String, Object>) mvMap.get("data");
                            MmsMovie mmsMovie = new MmsMovie();
                            //1. 名字
                            mmsMovie.setMovieName(String.valueOf(data.get("name")));
                            //2. 上传类型
                            mmsMovie.setMovieUpType(0);
                            //3  歌手id
                            mmsMovie.setMovieUpId(albumSingerId);
                            //4. 封面
                            mmsMovie.setMovieImageUrl(String.valueOf(data.get("cover")));
                            mmsMovie.setMovieUrl(String.valueOf(mvDetailMap.get("url")));
                            //6.视频简介
                            mmsMovie.setMovieDesc(String.valueOf(data.get("desc")));
                            // 7 创建者
                            mmsMovie.setCreateBy("admin");
                            mmsMovieService.insertMmsMovie(mmsMovie);
                            mmsSongMvMapper.insertSongMv(mmsSong.getSongId(), mmsMovie.getMovieId());
                        }

                        log.info("MV备份完成");
                    }
                    offset1 = 0;

                } while (String.valueOf(map.get("more")).equals("true"));
                offset1 = 0;
                offset++;
            }
        } while (offset < lastAlbum);

        System.out.println("finish=====>" + offset);

    }

    public Map<Object, Object> doGet(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 响应模型
        CloseableHttpResponse response = null;
        String responseContent = null;
        try (
                // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        ) {
            // 创建Get请求
            HttpGet httpGet = new HttpGet(url);


            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return objectMapper.readValue(responseContent, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}

