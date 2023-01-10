import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.service.IMmsTagService;
import com.ruoyi.music.vo.front.SimpleTagVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestMmsTagService {

    @Resource
    private IMmsTagService mmsTagService;

    @Test
    public void getMmsTagsBySongId(){
        List<SimpleTagVo> tagName = mmsTagService.selectSimpleTagsBySongId(1L);
        System.out.println(tagName);
    }

    @Test
    public void getMmsTagsByParentsId(){
        List<SimpleTagVo> tagName = mmsTagService.selectSimpleTagsByParentsId(1L);
        System.out.println(tagName);
    }
}
