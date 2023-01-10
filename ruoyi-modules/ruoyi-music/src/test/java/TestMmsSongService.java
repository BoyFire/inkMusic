import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.service.IMmsSongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
}
