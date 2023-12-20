package com.ddp.res.service.login;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ddp.res.common.constants.SecurityConstants;
import com.ddp.res.common.redis.CacheConstants;
import com.ddp.res.common.utils.CryptUtil;
import com.ddp.res.common.utils.JacksonUtil;
import com.ddp.res.common.utils.JwtUtils;
import com.ddp.res.pojo.user.dto.LoginUser;
import com.ddp.res.service.redis.RedisService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;


    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;


    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        String token = UUID.fastUUID().toString();
        loginUser.setToken(token);
//        loginUser.setIpAddr(IpUtils.getIpAddr());
        refreshToken(loginUser);
        return getStringObjectMap(loginUser);
    }

    /**
     * 更新令牌
     */
    public Map<String, Object> updateToken(LoginUser loginUser) {
        setLoginUser(loginUser);
        return getStringObjectMap(loginUser);
    }

    private Map<String, Object> getStringObjectMap(LoginUser loginUser) {
        Long unitId = loginUser.getUnitId();
        Long roleId = loginUser.getRoleId();
        // Jwt存储信息
        Map<String, Object> claimsMap = Maps.newHashMap();
        claimsMap.put(SecurityConstants.USER_KEY, loginUser.getToken());
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getUserId());
        claimsMap.put(SecurityConstants.DETAILS_LOGIN_NAME, loginUser.getLoginName());
        claimsMap.put(SecurityConstants.DETAILS_ROLE_ID, roleId);
        claimsMap.put(SecurityConstants.DETAILS_PSN_NAME, loginUser.getPsnName());
        claimsMap.put(SecurityConstants.DETAILS_UNIT_NAME, loginUser.getUnitName());
//        claimsMap.put(SecurityConstants.DETAILS_UNIT_TYPE, loginUser.getUnitType());
        claimsMap.put(SecurityConstants.DETAILS_UNIT_ID, unitId);
        // 接口返回信息
        Map<String, Object> rspMap = Maps.newHashMap();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", CacheConstants.LOGIN_TOKEN.getTimeout().toMinutes());
        Map<String, Object> loginMap = Maps.newHashMap();
        loginMap.put("unitId", unitId == null ? null : CryptUtil.encrypt(unitId.toString()));
        loginMap.put("roleId", roleId == null ? null : CryptUtil.encrypt(roleId.toString()));
        rspMap.put("loginUser", loginMap);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
//        return getLoginUser(ServletUtils.getRequest());
        return null;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
   /* public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }*/

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token) {
        try {
            if (StringUtils.isNotEmpty(token)) {
                String userKey = JwtUtils.getUserKey(token);
                return JacksonUtil.string2Obj(redisService.getCacheObject(getTokenKey(userKey)), LoginUser.class);
            }
        } catch (Exception ex) {
            log.error("getLoginUser Error", ex);
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userKey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser 登录用户
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + CacheConstants.LOGIN_TOKEN.getTimeout().toMillis());
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, JacksonUtil.obj2String(loginUser), CacheConstants.LOGIN_TOKEN.getTimeout());
    }

    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN.formatKey(token);
    }
}
