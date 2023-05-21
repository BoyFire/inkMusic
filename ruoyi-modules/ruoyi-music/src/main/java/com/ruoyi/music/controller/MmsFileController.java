package com.ruoyi.music.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.music.service.IMmsFileService;
import com.ruoyi.system.api.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Ink
 * 2022/10/18
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class MmsFileController {
    @Autowired
    private IMmsFileService mmsFileService;

    @PostMapping("/upload")
        public R<SysFile> upload(MultipartFile file, @RequestParam Map<String, String> map) {
        if (file.isEmpty()) {
            return R.fail("文件为空");
        }
        SysFile sysFile = new SysFile();
        try {
            String type = map.get("fileType");
            String url = mmsFileService.uploadFile(file, type );
            log.info(type + "地址为:" + url);
            sysFile.setName(file.getName());
            sysFile.setUrl(url);

        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
        return R.ok(sysFile);
    }


}
