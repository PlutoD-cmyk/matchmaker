package org.plutodjava.matchmaker.wx.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plutodjava.matchmaker.core.notify.NotifyService;
import org.plutodjava.matchmaker.core.notify.NotifyType;
import org.plutodjava.matchmaker.core.utils.*;
import org.plutodjava.matchmaker.core.utils.bcrypt.BCryptPasswordEncoder;
import org.plutodjava.matchmaker.core.vo.IntentionJsonVo;
import org.plutodjava.matchmaker.core.vo.UserIntentionVo;
import org.plutodjava.matchmaker.db.domain.TbUser;
import org.plutodjava.matchmaker.db.domain.TbUserIntention;
import org.plutodjava.matchmaker.db.manager.UserIntentionManager;
import org.plutodjava.matchmaker.db.manager.WxUserManager;
import org.plutodjava.matchmaker.wx.annotation.LoginUser;
import org.plutodjava.matchmaker.wx.dto.WxLoginInfo;
import org.plutodjava.matchmaker.wx.dto.WxUserInfo;
import org.plutodjava.matchmaker.wx.service.CaptchaCodeManager;
import org.plutodjava.matchmaker.wx.service.UserTokenManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.plutodjava.matchmaker.wx.utils.WxResponseCode.*;

/**
 * 相亲报名
 */
@RestController
@RequestMapping("/matchmaker/user/auth")
@Validated
@Slf4j
public class WxUserAuthController {
    private final Log logger = LogFactory.getLog(WxUserAuthController.class);

    @Autowired
    private WxUserManager userService;
    @Autowired
    private CaptchaCodeManager captchaCodeManager;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserIntentionManager userIntentionManager;
    
    /**
     * 请求注册验证码
     *
     * @param body 手机号码 { mobile }
     * @return
     */
    @PostMapping("regCaptcha")
    public Object registerCaptcha(@RequestBody String body) {
        String mobile = JacksonUtil.parseString(body, "mobile");
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileSimple(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        List<TbUser> tbUsers = userService.queryByMobile(mobile);
        if (CollectionUtils.isNotEmpty(tbUsers)) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        String code = CharUtil.getRandomNum(6);
        //String code = "123456";
        log.info("mobile:{},code:{}", mobile, code);
        boolean successful = captchaCodeManager.addToCache(mobile, code);
        if (!successful) {
            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码仍在有效期");
        }
        notifyService.notifySmsTemplate(mobile, NotifyType.CAPTCHA, new String[]{code});
        return ResponseUtil.ok();
    }


    @PostMapping("register")
    public Object register(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String mobile = JacksonUtil.parseString(body, "mobile");
        // 验证码
        String code = JacksonUtil.parseString(body, "code");
        String avatar = JacksonUtil.parseString(body, "avatar");
        String gender = JacksonUtil.parseString(body, "gender");
        //Integer age = JacksonUtil.parseInteger(body, "age");
        String certificate = JacksonUtil.parseString(body, "certificate");
        Integer age = IdCardNumberMethod.getAgeForIdcard(certificate);
        String marriage = JacksonUtil.parseString(body, "marriage");
        // 如果是小程序注册，则必须非空
        // 其他情况，可以为空
        String wxCode = JacksonUtil.parseString(body, "wxCode");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileSimple(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        List<TbUser> userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        //判断验证码是否正确
        String cacheCode = captchaCodeManager.getCachedCaptcha(mobile);
        // todo：收发短信服务完备后打开
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
        }
        TbUser user = new TbUser();
        // 继续验证openid
        if(!StringUtils.isEmpty(wxCode)) {
        }
        user.setUsername(username);
        user.setMobile(mobile);
        user.setAge(age);
        user.setCertificate(certificate);
        user.setMarriage(marriage);
        user.setGender(gender);
        user.setAvatar(avatar);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);
        return ResponseUtil.ok(user.getId());
    }

    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<TbUser> userList = userService.queryByMobile(username);
        TbUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(-1,"您的手机号存在多个账户，请联系管理员");
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
        } else {
            user = userList.get(0);
        }

        if (user.getStatus() != 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账户目前不可用，请等待审批通过");
       }
        UserIntentionVo userIntentionVo = new UserIntentionVo();
        BeanUtils.copyProperties(user,userIntentionVo);
        List<TbUserIntention> tbUserIntentions = userIntentionManager.queryByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(tbUserIntentions)) {
            TbUserIntention tbUserIntention = tbUserIntentions.get(0);
            IntentionJsonVo intentionJsonVo = new IntentionJsonVo();
            BeanUtils.copyProperties(tbUserIntention,intentionJsonVo);
            userIntentionVo.setIntention(intentionJsonVo);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateUser(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        // token
        String token = UserTokenManager.generateToken(user.getId().toString());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userIntentionVo);
        return ResponseUtil.ok(result);
    }

    /**
     * 账号个人信息查询
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @return 登录结果
     */
    @PostMapping("profile")
    public Object profile(@LoginUser String userId, @RequestBody(required = false) String body) {
        Integer id = 0;

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(body)) {
            Integer otherId = JacksonUtil.parseInteger(body, "otherId");
            if(otherId != null){
                id = otherId;
            } else {
                return ResponseUtil.unlogin();
            }
        }else {
            if (StringUtils.isEmpty(userId)) {
                return ResponseUtil.unlogin();
            }
            id = Integer.valueOf(userId);
        }
        TbUser user = userService.findById(id);
        UserIntentionVo userIntentionVo = new UserIntentionVo();
        BeanUtils.copyProperties(user,userIntentionVo);
        List<TbUserIntention> tbUserIntentions = userIntentionManager.queryByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(tbUserIntentions)) {
            TbUserIntention tbUserIntention = tbUserIntentions.get(0);
            IntentionJsonVo intentionJsonVo = new IntentionJsonVo();
            BeanUtils.copyProperties(tbUserIntention,intentionJsonVo);
            userIntentionVo.setIntention(intentionJsonVo);
        }
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("userInfo", userIntentionVo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     */
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        WxUserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }

        String openId = null;
        if(!StringUtils.isEmpty(code)) {
            try {

            } catch (Exception e) {
                logger.error("weixin code :" + code + " login fail, errorMsg:" + e.getMessage());
                // return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "openid 获取失败");
            }
        }

        if (openId == null) {
            return ResponseUtil.fail();
        }

        // token
        String token = UserTokenManager.generateToken(openId);

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        return ResponseUtil.ok(result);
    }

    /**
     * 账号密码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    public Object reset(@RequestBody String body, HttpServletRequest request) {
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = captchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<TbUser> userList = userService.queryByMobile(mobile);
        TbUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_MOBILE_UNREGISTERED, "手机号未注册");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号手机号码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetPhone")
    public Object resetPhone(@LoginUser String userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = captchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<TbUser> userList = userService.queryByMobile(mobile);
        TbUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        user = userService.findById(Integer.valueOf(userId));
        user.setMobile(mobile);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    @PostMapping("logout")
    public Object logout(@LoginUser String userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }




}