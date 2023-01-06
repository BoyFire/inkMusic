package com.ruoyi.music.config;

import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "qi")
public class QiNiuFileConfig {
    /** */
    private String accessKey;
    /** */
    private String secretKey;
    /** */
    private String bucketName;
    //七牛云域名
    private String domain;

    private  com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.autoRegion());

    public com.qiniu.storage.Configuration getCfg() {
        return cfg;
    }

    public void setCfg(com.qiniu.storage.Configuration cfg) {
        this.cfg = cfg;
    }

    public String getUpToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody", "{\"fileUrl\":\"" + domain + "$(key)\"}");
        return auth.uploadToken(bucketName, null, 3600, stringMap);
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketname) {
        this.bucketName = bucketname;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
