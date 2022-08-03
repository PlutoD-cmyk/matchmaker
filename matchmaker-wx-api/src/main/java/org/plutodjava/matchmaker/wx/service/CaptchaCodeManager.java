package org.plutodjava.matchmaker.wx.service;

import org.plutodjava.matchmaker.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 缓存系统中的验证码
 */
@Service
public class CaptchaCodeManager {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 添加到缓存
     *
     * @param phoneNumber 电话号码
     * @param code        验证码
     */
    public boolean addToCache(String phoneNumber, String code) {
        //已经发过验证码且验证码还未过期
        if (redisUtil.get(phoneNumber) != null) {
            return false;
        }
        redisUtil.setEx(phoneNumber, code, 1, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 获取缓存的验证码
     *
     * @param phoneNumber 关联的电话号码
     * @return 验证码
     */
    public String getCachedCaptcha(String phoneNumber) {
        //没有这个电话记录
        if (redisUtil.get(phoneNumber) == null) {
            return null;
        }
        return redisUtil.get(phoneNumber).toString();
    }
}
