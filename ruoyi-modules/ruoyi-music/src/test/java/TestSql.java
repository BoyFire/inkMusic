import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.entity.MmsSongListSong;
import com.ruoyi.music.mapper.*;
import com.ruoyi.music.service.IMmsSingerService;
import com.ruoyi.music.vo.front.SimpleAlbum;
import com.ruoyi.music.vo.front.SimpleSinger;
import com.ruoyi.music.vo.front.SongParamsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Ink
 * 2022/10/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestSql {
    @Autowired
    private MmsAlbumMapper mmsAlbumMapper;
    @Autowired
    MmsSongMapper mmsSongMapper;

    @Autowired
    IMmsSingerService mmsSingerService;
    @Autowired
    MmsSingerAlbumMapper mmsSingerAlbumMapper;
@Autowired
    MmsSongAlbumMapper mmsSongAlbumMapper;
    @Resource
    MmsSongListSongMapper mmsSongListSongMapper;
    @Test
    public void testUpload() {
        MmsSongListSong mmsSongListSong = new MmsSongListSong();
        List<MmsSongListSong> mmsSongListSongs = mmsSongListSongMapper.selectMmsSongListSongList(mmsSongListSong);
        System.out.println(mmsSongListSongs);
    }

    @Test
    public void testSelectMmsSong(){
        SongParamsVo mmsSong = new SongParamsVo();
        mmsSong.setSongId(1L);

        List<MmsSong> mmsSongs = mmsSongMapper.selectMmsSongList(mmsSong);
        
        for (MmsSong song : mmsSongs) {
            System.out.println("song = " + song);
        }
    }

    @Test
    public  void testSelectSimpleSingerBySingerName(){
        String name = "陈";
        List<SimpleSinger> simpleSingers = mmsSingerService.selectSimpleSingerListBySingerName(name);
        for (SimpleSinger simpleSinger : simpleSingers) {
            System.out.println(simpleSinger);
        }
    }

    @Test
    public void testSelectMmsAlbumById(){
        Long id = 2L;
        MmsAlbum mmsAlbum = mmsAlbumMapper.selectMmsAlbumById(id);
        System.out.println(mmsAlbum);
    }

    @Test
    public void testSelectSimpleSingerByAlbumId(){
        Long id = 1L;
        List<SimpleSinger> singers = mmsSingerAlbumMapper.selectSimpleSingerByAlbumId(id);
        for (SimpleSinger singer : singers) {
            System.out.println(singer);
        }
    }

    @Test
    public void testSelectMmsSongList(){
        long now = System.currentTimeMillis();
        SongParamsVo songParamsVo = new SongParamsVo();
        songParamsVo.setSingerName("周");
        List<MmsSong> mmsSongs = mmsSongMapper.selectMmsSongList(songParamsVo);
        System.out.println(mmsSongs.size());
        System.out.println("time:"+ (System.currentTimeMillis() - now)/1000);
    }

    @Test
    public void testSelectSimpleAlbumBySongId(){
        List<SimpleAlbum> simpleAlbums = mmsSongAlbumMapper.selectSimpleAlbumBySongId(1L);
        System.out.println(simpleAlbums.size());
    }

    @Test
    public void testSelectSimpleAlbumsBySingers(){
        List<SimpleSinger> singers = new ArrayList<>(10);
        SimpleSinger singer1 = new SimpleSinger();
        singer1.setSingerId(1L);
        SimpleSinger singer2 = new SimpleSinger();
        singer1.setSingerId(4L);
        SimpleSinger singer3 = new SimpleSinger();
        singer1.setSingerId(5L);
        singers.add(singer1);
        singers.add(singer2);
        singers.add(singer3);
        List<SimpleAlbum> simpleAlbums = mmsSingerAlbumMapper.selectSimpleAlbumsBySingers(singers);
        System.out.println(simpleAlbums.size());

    }
}
