import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.service.IMmsSongTagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestMmsSongTagService {
    @Resource
    private IMmsSongTagService mmsSongTagService;

    @Test
    public void testDeleteSongTagByTagId(){
        int i = mmsSongTagService.deleteSongTagByTagId(29L);
        System.out.println(i);
    }

    @Test
    public void testInsertSongTags(){
    }
}
