package com.ruoyi.music.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.music.config.QiNiuFileConfig;
import com.ruoyi.music.service.IMmsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Ink
 * 2022/10/18
 */
@Service
public class MmsQiNiuFileServiceImpl implements IMmsFileService {

    @Autowired
    private QiNiuFileConfig qiNiuFileConfig;

    @Override
    public String uploadFile(MultipartFile file, String pre) throws IOException {
        byte[] bytes = file.getBytes();
        int hashCode = file.hashCode();
        String url = null;
        if (pre.equals("music") || pre.equals("video")) {
            url = bigFileUpload(bytes, pre + hashCode);
        } else {
            url = smallUpload(bytes, pre + hashCode);
        }
        if (url == null){
            throw  new RuntimeException("文件上传失败");
        }
        return url;
    }

    /**
     * 适合上传比较大的文件（例如数百 MB 或更大）。
     *
     * @param base64 上传文件流
     * @param fileName 文件名
     * @return 文件url
     */
    private String bigFileUpload(byte[] base64, String fileName){
        StringBuffer fileUrl = new StringBuffer(qiNiuFileConfig.getDomain());
        fileName = DateUtils.getDate() + "/" + fileName;
        try {
            String upToken = qiNiuFileConfig.getUpToken();
            if (StringUtils.isEmpty(upToken)){
                throw new RuntimeException("上传凭证为空");
            }
            Configuration cfg = new Configuration(Region.autoRegion());
            cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
            cfg.resumableUploadMaxConcurrentTaskCount = 2;
            UploadManager uploadManager = new UploadManager(cfg);
            uploadManager.put(base64, fileName, upToken);
            fileUrl.append(fileName);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return fileUrl.toString();
    }

    /**
     *
     * 数据流上传
     *
     * @param base64 上传文件流
     * @param fileName 文件名
     * @return 文件URL
     */
    //base64方式上传
    private String smallUpload(byte[] base64, String fileName) {
        StringBuffer fileUrl = new StringBuffer(qiNiuFileConfig.getDomain());
        fileName = DateUtils.getDate() + "/" + fileName;
        try {
            String upToken = qiNiuFileConfig.getUpToken();
            if (StringUtils.isEmpty(upToken)) {
                throw new RuntimeException("上传凭证为空");
            }
            UploadManager uploadManager = new UploadManager(qiNiuFileConfig.getCfg());
            uploadManager.put(base64, fileName, upToken);
            fileUrl.append(fileName);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
        return fileUrl.toString();
    }
}
