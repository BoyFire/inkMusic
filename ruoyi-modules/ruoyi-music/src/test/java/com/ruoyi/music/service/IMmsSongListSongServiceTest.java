package com.ruoyi.music.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class IMmsSongListSongServiceTest {

    @Resource
    private IMmsSongListSongService mmsSongListSongService;
    @Test
    void selectMmsSongListSongBySongListId() {
        mmsSongListSongService.selectMmsSongListSongBySongListId(5L);
    }
}