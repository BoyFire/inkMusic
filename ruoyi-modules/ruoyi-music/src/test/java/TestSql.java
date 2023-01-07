import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.entity.MmsSongListSong;
import com.ruoyi.music.mapper.MmsAlbumMapper;
import com.ruoyi.music.mapper.MmsSongListSongMapper;
import com.ruoyi.music.mapper.MmsSongMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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

    @Resource
    MmsSongListSongMapper mmsSongListSongMapper;
    @Test
    public void testUpload() {
        MmsSongListSong mmsSongListSong = new MmsSongListSong();
        List<MmsSongListSong> mmsSongListSongs = mmsSongListSongMapper.selectMmsSongListSongList(mmsSongListSong);
        System.out.println(mmsSongListSongs);
    }
}
