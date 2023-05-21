import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.service.IMmsSongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestMmsSongService {
    @Resource
    private IMmsSongService mmsSongService;

    @Test
    public void selectMmsSong(){
        MmsSong mmsSong = mmsSongService.selectMmsSongBySongId(1L);
        System.out.println(mmsSong);
    }

    @Test
    public void selectSimpleSongsBySongName() {
    }

    @Test
    public void selectMmsSongsBySongListId() {
    }

    @Test
    public void selectMmsSongById() {
    }

    @Test
    public void selectMmsSongList() {
    }

    @Test
    public void insertMmsSong() {
    }

    @Test
    public void updateMmsSong() {
    }

    @Test
    public void deleteMmsSongByIds() {
    }

    @Test
    public void deleteMmsSongById() {
    }

    @Test
    public void listSimpleSong() {
    }

    @Test
    public void selectMmsSongBySongId() {
        List<MmsSong> mmsSongs = mmsSongService.selectMmsSongsBySongListId(2L);
        System.out.println(mmsSongs);
    }
}
