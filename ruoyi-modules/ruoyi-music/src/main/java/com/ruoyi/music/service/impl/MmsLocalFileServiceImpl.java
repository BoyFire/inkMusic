package com.ruoyi.music.service.impl;

import com.ruoyi.music.service.IMmsFileService;
import com.ruoyi.music.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Primary
@Service
@Slf4j
public class MmsLocalFileServiceImpl implements IMmsFileService {

    @Value("${file.prefix}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    @Override
    public String uploadFile(MultipartFile file, String prefix) {
        String fileDownloadUrl = "";
        String fileName = FileUploadUtils.extractFilename(file, prefix);
        File dest = new File(localFilePath + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            fileDownloadUrl = domain + localFilePrefix + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileDownloadUrl;
    }
}
