package org.plutodjava.matchmaker.wx.web;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plutodjava.matchmaker.core.enums.EducationEnum;
import org.plutodjava.matchmaker.core.storage.StorageService;
import org.plutodjava.matchmaker.core.utils.*;
import org.plutodjava.matchmaker.core.vo.IntentionJsonVo;
import org.plutodjava.matchmaker.db.domain.TbFlippedMobileGroup;
import org.plutodjava.matchmaker.db.domain.TbUser;
import org.plutodjava.matchmaker.db.domain.TbUserIntention;
import org.plutodjava.matchmaker.db.manager.FlippedMobileGroupManager;
import org.plutodjava.matchmaker.db.manager.UserIntentionManager;
import org.plutodjava.matchmaker.db.manager.WxUserManager;
import org.plutodjava.matchmaker.core.vo.CommonConstant;
import org.plutodjava.matchmaker.wx.annotation.LoginUser;
import org.plutodjava.matchmaker.wx.dto.OppositeSexUserVo;
import org.plutodjava.matchmaker.wx.utils.SplitUtil;
import org.plutodjava.matchmaker.core.utils.XuRemarkUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.plutodjava.matchmaker.core.utils.XuRemarkUtil.getContentType;

/**
 * 相亲报名
 */
@RestController
@RequestMapping("/matchmaker/user")
@Validated
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private WxUserManager userService;

    @Autowired
    private FlippedMobileGroupManager flippedMobileGroupManager;

    @Autowired
    private UserIntentionManager userIntentionManager;


    @PostMapping("/uploadPic")
    public Object upload(@RequestParam(value = "file",required=false) MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        File img = XuRemarkUtil.transferToFile(file);
        List<String> markList=new ArrayList<String>();
        markList.add("仅供万州公益红娘使用");
        img = XuRemarkUtil.pressText(markList,
                img,
                "宋体",
                Font.TYPE1_FONT, Color.red, 0.9f);
        if (img == null) {
            return ResponseUtil.fail(-1,"上传失败,联系管理员");
        }
        FileInputStream fileInputStream = new FileInputStream(img);
        String url = storageService.store(fileInputStream, img.length(), getContentType(img.getPath()), originalFilename);
        return ResponseUtil.ok(url);
    }

    /**
     * 账号信息更新
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
    @PostMapping("updateUserInfo")
    public Object updateUserInfo(@LoginUser String userId, @RequestBody String body, HttpServletRequest request) {
        if(StringUtils.isEmpty(userId)){
            return ResponseUtil.unlogin();
        }
        String place = JacksonUtil.parseString(body, "place");
        IntentionJsonVo intentionJsonVo = null;
        try {
            intentionJsonVo = JacksonUtil.parseObject(body, "intention", IntentionJsonVo.class);
        } catch (Exception e) {
            logger.error("intention is null");
        }
        String education = JacksonUtil.parseString(body, "education");
        String industry = JacksonUtil.parseString(body, "industry");
        Integer income = JacksonUtil.parseInteger(body, "income");
        Integer height = JacksonUtil.parseInteger(body, "height");
        Integer weight = JacksonUtil.parseInteger(body, "weight");
        String household = JacksonUtil.parseString(body, "household");
        String marriage = JacksonUtil.parseString(body, "marriage");

        TbUser user = userService.findById(Integer.parseInt(userId));
        if(!StringUtils.isEmpty(place)){
            user.setPlace(place);
        }
        if(!StringUtils.isEmpty(education)){
            user.setEducation(education);
        }
        if(!StringUtils.isEmpty(marriage)){
            user.setMarriage(marriage);
        }
        if(!StringUtils.isEmpty(industry)){
            user.setIndustry(industry);
        }
        if(!StringUtils.isEmpty(household)){
            user.setHousehold(household);
        }
        if (income != null) {
            user.setIncome(income);
        }
        if (weight != null) {
            user.setWeight(weight);
        }
        if (height != null) {
            user.setHeight(height);
        }
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        if (intentionJsonVo != null) {
            List<TbUserIntention> tbUserIntentions = userIntentionManager.queryByUserId(user.getId());
            if (CollectionUtils.isEmpty(tbUserIntentions)) {
                TbUserIntention tbUserIntention = new TbUserIntention();
                tbUserIntention.setAgeRange(intentionJsonVo.getAgeRange());
                tbUserIntention.setHeightRange(intentionJsonVo.getHeightRange());
                tbUserIntention.setMinimumEducation(intentionJsonVo.getMinimumEducation());
                tbUserIntention.setWeightRange(intentionJsonVo.getWeightRange());
                tbUserIntention.setIndustry(intentionJsonVo.getIndustry());
                tbUserIntention.setIncomeRange(intentionJsonVo.getIncomeRange());
                tbUserIntention.setPlace(intentionJsonVo.getPlace());
                tbUserIntention.setMarriage(intentionJsonVo.getMarriage());
                tbUserIntention.setUserId(user.getId());
                userIntentionManager.add(tbUserIntention);
            } else {
                TbUserIntention tbUserIntention = tbUserIntentions.get(0);
                tbUserIntention.setAgeRange(intentionJsonVo.getAgeRange());
                tbUserIntention.setHeightRange(intentionJsonVo.getHeightRange());
                tbUserIntention.setMinimumEducation(intentionJsonVo.getMinimumEducation());
                tbUserIntention.setWeightRange(intentionJsonVo.getWeightRange());
                tbUserIntention.setIndustry(intentionJsonVo.getIndustry());
                tbUserIntention.setUserId(user.getId());
                tbUserIntention.setIncomeRange(intentionJsonVo.getIncomeRange());
                tbUserIntention.setPlace(intentionJsonVo.getPlace());
                tbUserIntention.setMarriage(intentionJsonVo.getMarriage());
                userIntentionManager.updateById(tbUserIntention);
            }
        }
        return ResponseUtil.ok(user.getId());
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("deleteUser")
    public Object deleteUser(@LoginUser String userId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        TbUser user = userService.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseUtil.fail(-1,"用户不存在");
        }
        user.setDeleted(true);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(user.getId());
    }

    @GetMapping("/queryTypicalPicList")
    public Object queryOppositeSexUser() {
        List<String> data = flippedMobileGroupManager.findAllTypicalList();
        return ResponseUtil.ok(data);
    }

    @PostMapping("/queryOppositeSexUser")
    public Object queryOppositeSexUser(@LoginUser String userId,
                                       @RequestBody String body) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Integer limit = JacksonUtil.parseInteger(body, "limit");
        if (limit == null) {
            limit = 10;
        }
        List<OppositeSexUserVo> users = Lists.newArrayList();
        Integer page = JacksonUtil.parseInteger(body, "page");
        if (page == null) {
            page = 1;
        }
        String sort = JacksonUtil.parseString(body, "add_time");
        if (StringUtils.isEmpty(sort)) {
            sort = "add_time";
        }
        String order = JacksonUtil.parseString(body, "order");
        if (StringUtils.isEmpty(order)) {
            order = "desc";
        }
        TbUser user = userService.findById(Integer.valueOf(userId));
        List<TbUserIntention> tbUserIntentions = userIntentionManager.queryByUserId(Integer.valueOf(userId));
        List<String> industryList = Lists.newArrayList();
        List<String> educationList = Lists.newArrayList();
        String place = null;
        String marriage = null;
        Integer ageStart = null;
        Integer ageEnd = null;
        Integer heightStart = null;
        Integer heightEnd = null;
        Integer weightStart = null;
        Integer weightEnd = null;
        Integer incomeStart = null;
        Integer incomeEnd = null;
        if (CollectionUtils.isEmpty(tbUserIntentions)) {
        }else {
            //择偶意向-职业列表
            TbUserIntention tbUserIntention = tbUserIntentions.get(0);
            place = tbUserIntention.getPlace();
            marriage = tbUserIntention.getMarriage();
            if (StringUtils.isEmpty(tbUserIntention.getIndustry())) {
            }else {
                industryList = SplitUtil.stringSplit(tbUserIntention.getIndustry());
            }
            //择偶意向-年龄
            if (StringUtils.isEmpty(tbUserIntention.getAgeRange())) {
            }else{
                String[] split = tbUserIntention.getAgeRange().split("-");
                ageStart=Integer.parseInt(split[0]);
                ageEnd=Integer.parseInt(split[1]);
            }
            //择偶意向-最低学历
            if (StringUtils.isEmpty(tbUserIntention.getMinimumEducation())) {
            }else{
                if (EducationEnum.juniorCollege.getDesc().equals(tbUserIntention.getMinimumEducation())){
                    educationList.add(EducationEnum.graduate.getDesc());
                    educationList.add(EducationEnum.doctor.getDesc());
                    educationList.add(EducationEnum.juniorCollege.getDesc());
                    educationList.add(EducationEnum.undergraduate.getDesc());
                } else if(EducationEnum.undergraduate.getDesc().equals(tbUserIntention.getMinimumEducation())){
                    educationList.add(EducationEnum.graduate.getDesc());
                    educationList.add(EducationEnum.doctor.getDesc());
                    educationList.add(EducationEnum.undergraduate.getDesc());
                }else if(EducationEnum.graduate.getDesc().equals(tbUserIntention.getMinimumEducation())){
                    educationList.add(EducationEnum.graduate.getDesc());
                    educationList.add(EducationEnum.doctor.getDesc());
                } else {
                    educationList.add(EducationEnum.doctor.getDesc());
                }
            }
            //择偶意向-身高
            if (StringUtils.isEmpty(tbUserIntention.getHeightRange())) {
            }else{
                if (tbUserIntention.getHeightRange().endsWith("以上")){
                    heightStart = Integer.parseInt(tbUserIntention.getHeightRange()
                            .substring(0, tbUserIntention.getHeightRange().indexOf("以")));
                }else {
                    String[] split = tbUserIntention.getHeightRange().split("-");
                    heightStart = Integer.parseInt(split[0]);
                    heightEnd = Integer.parseInt(split[1]);
                }
            }
            //择偶意向-体重
            if (StringUtils.isEmpty(tbUserIntention.getWeightRange())) {
            }else{
                if (tbUserIntention.getWeightRange().endsWith("以上")){
                    weightStart = Integer.parseInt(tbUserIntention.getWeightRange()
                            .substring(0, tbUserIntention.getWeightRange().indexOf("以")));
                }else {
                    String[] split = tbUserIntention.getWeightRange().split("-");
                    weightStart = Integer.parseInt(split[0]);
                    weightEnd = Integer.parseInt(split[1]);
                }
            }
            //择偶意向-年龄
            if (StringUtils.isEmpty(tbUserIntention.getIncomeRange())) {
            }else{
                if (tbUserIntention.getIncomeRange().endsWith("以上")){
                    incomeStart = Integer.parseInt(tbUserIntention.getIncomeRange()
                            .substring(0, tbUserIntention.getIncomeRange().indexOf("以")));
                }else if (tbUserIntention.getIncomeRange().endsWith("以下")){
                    incomeEnd = Integer.parseInt(tbUserIntention.getIncomeRange()
                            .substring(0, tbUserIntention.getIncomeRange().indexOf("以")));
                }else{
                    String[] split = tbUserIntention.getIncomeRange().split("-");
                    incomeStart = Integer.parseInt(split[0]);
                    incomeEnd = Integer.parseInt(split[1]);
                }
            }
        }
        List<TbUser> userList = userService.queryOppositeSexUser(
                industryList,ageStart,ageEnd,educationList,heightStart,heightEnd,weightStart,weightEnd,
                incomeStart,incomeEnd,place,marriage,
                user.getGender().equals(CommonConstant.female)?CommonConstant.male:CommonConstant.female,
                page, limit, sort, order);
        // 校验是否在报名表中
        if (!CollectionUtils.isEmpty(userList)) {
            users = Lists.transform(userList, (entity) -> {
                List<TbFlippedMobileGroup> flippedMobileGroupList = flippedMobileGroupManager.queryApplyByFlipMobile(entity.getMobile());
                List<TbFlippedMobileGroup> byFlippedMobileGroupList = flippedMobileGroupManager.queryApplyByByFlipMobile(entity.getMobile());
                List<TbFlippedMobileGroup> handByFlipMobile = flippedMobileGroupManager.queryHandByFlipMobile(entity.getMobile());
                List<TbFlippedMobileGroup> handByByFlipMobile = flippedMobileGroupManager.queryHandByByFlipMobile(entity.getMobile());

                OppositeSexUserVo vo = new OppositeSexUserVo();
                BeanUtils.copyProperties(entity, vo);
                if (!CollectionUtils.isEmpty(flippedMobileGroupList)) {
                    vo.setIfApply(true);
                } else if (!CollectionUtils.isEmpty(byFlippedMobileGroupList)) {
                    vo.setIfApply(true);
                } else {
                    vo.setIfApply(false);
                }
                if (!CollectionUtils.isEmpty(handByFlipMobile)) {
                    vo.setIsHand(true);
                } else if (!CollectionUtils.isEmpty(handByByFlipMobile)) {
                    vo.setIsHand(true);
                } else {
                    vo.setIsHand(false);
                }
                List<TbUserIntention> intentions = userIntentionManager.queryByUserId(entity.getId());
                if (CollectionUtils.isEmpty(intentions)) {
                } else {
                    vo.setIntention(JsonUtils.toJson(intentions.get(0)));
                }
                return vo;
            });
        }
        return ResponseUtil.okList(users);
    }

    /**
     * 账号信息更新
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
    @PostMapping("establishMeetIntention")
    public Object establishMeetIntention(@LoginUser String userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        String byFlippedMobile = JacksonUtil.parseString(body, "byFlippedMobile");
        boolean apply = flippedMobileGroupManager.queryApply(byFlippedMobile);
        if (apply) {
            return ResponseUtil.fail(-1, "对方已参与线下报名，无法建立意向");
        }
        TbUser user = userService.findById(Integer.valueOf(userId));

        String flippedMobile = user.getMobile();
        List<TbFlippedMobileGroup> flippedMobileGroupList = flippedMobileGroupManager.checkOnly(flippedMobile, byFlippedMobile);
        if (!CollectionUtils.isEmpty(flippedMobileGroupList)) {
            return ResponseUtil.fail(-1, "已建立意向，请等平台联系");
        }
        TbFlippedMobileGroup flippedMobileGroup = new TbFlippedMobileGroup();
        flippedMobileGroup.setFlippedMobile(flippedMobile);
        flippedMobileGroup.setByFlippedMobile(byFlippedMobile);
        flippedMobileGroupManager.add(flippedMobileGroup);
        return ResponseUtil.ok(flippedMobileGroup.getId());
    }
}