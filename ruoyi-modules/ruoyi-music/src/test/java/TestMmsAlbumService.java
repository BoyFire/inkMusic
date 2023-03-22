import com.ruoyi.music.RuoYiMusicApplication;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.service.IMmsAlbumService;
import com.ruoyi.music.vo.front.AlbumParamsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiMusicApplication.class})
public class TestMmsAlbumService {
    @Autowired
    private IMmsAlbumService mmsAlbumService;

    @Test
    public void testSelectMmsAlbumList(){
        AlbumParamsVo albumParamsVo = new AlbumParamsVo();
        albumParamsVo.setAlbumName("最");

        List<MmsAlbum> result = mmsAlbumService.selectMmsAlbumList(albumParamsVo);
        System.out.println(result);
    }

    @Test
    public void test
}
