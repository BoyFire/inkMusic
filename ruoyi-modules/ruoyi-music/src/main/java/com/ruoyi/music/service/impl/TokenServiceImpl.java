package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.core.utils.uuid.IdUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.model.MmsLoginUser;
import com.ruoyi.music.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    private RedisService redisService;
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     *
     * @param mmsLoginUser
     */
    @Override
    public Map<String, Object> createToken(MmsLoginUser mmsLoginUser) {
        String token = IdUtils.fastUUID();
        Long userId = mmsLoginUser.getMmsUser().getUserId();
        String username = mmsLoginUser.getUsername();
        String userAuthType = mmsLoginUser.getUserAuthType();

        mmsLoginUser.setToken(token);
        mmsLoginUser.setUserid(userId);
        mmsLoginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(mmsLoginUser);

        //JWT 存储信息
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, username);
        claimsMap.put(SecurityConstants.LOGIN_USER_TYPE, userAuthType);

        HashMap<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @param request
     */
    @Override
    public MmsLoginUser getLoginUser(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @param token
     */
    @Override
    public MmsLoginUser getLoginUser(String token) {
        MmsLoginUser loginUser = null;
        if (StringUtils.isNotEmpty(token)) {
            String userKey = JwtUtils.getUserKey(token);
            loginUser = redisService.getCacheObject(userKey);
        }
        return loginUser;
    }

    /**
     * 设置用户身份信息
     *
     * @param mmsLoginUser
     */
    @Override
    public void setLoginUser(MmsLoginUser mmsLoginUser) {
        if (StringUtils.isNotNull(mmsLoginUser) && StringUtils.isNotEmpty(mmsLoginUser.getToken()))
            refreshToken(mmsLoginUser);
    }

    /**
     * 删除用户身份信息
     *
     * @param token
     */
    @Override
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param mmsLoginUser
     */
    @Override
    public void verifyToken(MmsLoginUser mmsLoginUser) {

    }

    /**
     * 刷新令牌有效期
     */
    @Override
    public void refreshToken(MmsLoginUser mmsLoginUser) {
        mmsLoginUser.setLoginTime(System.currentTimeMillis());
        mmsLoginUser.setExpireTime(mmsLoginUser.getLoginTime()+ expireTime * MILLIS_MINUTE);
        String tokenKey = getTokenKey(mmsLoginUser.getToken());
        redisService.setCacheObject(tokenKey,mmsLoginUser,expireTime, TimeUnit.MINUTES);
    }

    @Override
    public String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}
