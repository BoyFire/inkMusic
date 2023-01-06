package com.ruoyi.music.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Ink
 * 2022/10/18
 */
public interface IMmsFileService {

    /**
     * 上传文件
     * @param file 文件
     * @param pre 前缀
     * @return 结果
     */
    String uploadFile(MultipartFile file , String pre) throws IOException;


}
