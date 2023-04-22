package com.ruoyi.music.service;


import com.ruoyi.music.model.MmsLoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ITokenService {
    /**
     * 创建令牌
     */
    Map<String, Object> createToken(MmsLoginUser mmsLoginUser);

    /**
     * 获取用户身份信息
     */
    MmsLoginUser getLoginUser(HttpServletRequest request);

    /**
     * 获取用户身份信息
     */
    MmsLoginUser getLoginUser(String token);

    /**
     * 设置用户身份信息
     */
    void setLoginUser(MmsLoginUser mmsLoginUser);

    /**
     * 删除用户身份信息
     */
    void delLoginUser(String token);

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     */
    void verifyToken(MmsLoginUser mmsLoginUser);

    /**
     * 刷新令牌有效期
     */
    void refreshToken(MmsLoginUser mmsLoginUser);

    String getTokenKey(String token);
    
}
