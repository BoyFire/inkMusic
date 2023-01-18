package com.ruoyi.music.utils;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.common.core.utils.uuid.Seq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;


/**
 * Ink
 * 2022/10/18
 */
@Slf4j
public class FileUploadUtils {
    private static final int MAX_FILENAME_SIZE = 20;

    /**
     * 文件转 base64
     *
     * @param file 文件
     * @return 结果
     */
    public String fileToBase64(File file) {
        return Base64.getEncoder().encodeToString(fileToByte(file));
    }

    /**
     * 文件File类型转byte[]
     *
     * @param file
     * @return
     */
    public byte[] fileToByte(File file) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBytes;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file,String prefix)
    {
        String fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
        if (fileName.length() > MAX_FILENAME_SIZE){
            fileName = String.valueOf(file.hashCode());
        }
        return StringUtils.format("{}/{}/{}_{}.{}", prefix,DateUtils.datePath(),
                fileName, Seq.getId(Seq.uploadSeqType), FileTypeUtils.getExtension(file));
    }
}
