package org.plutodjava.matchmaker.admin.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plutodjava.matchmaker.core.enums.UserStatusEnum;
import org.plutodjava.matchmaker.core.notify.NotifyService;
import org.plutodjava.matchmaker.core.notify.NotifyType;
import org.plutodjava.matchmaker.core.utils.*;
import org.plutodjava.matchmaker.core.utils.bcrypt.BCryptPasswordEncoder;
import org.plutodjava.matchmaker.core.vo.AdminUserIntentionVo;
import org.plutodjava.matchmaker.core.vo.ExcelUser;
import org.plutodjava.matchmaker.db.domain.TbUser;
import org.plutodjava.matchmaker.db.domain.TbUserIntention;
import org.plutodjava.matchmaker.db.manager.UserIntentionManager;
import org.plutodjava.matchmaker.db.manager.WxUserManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;

@RestController
@RequestMapping("/matchmaker/admin/user")
@Validated
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private WxUserManager wxUserManager;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserIntentionManager userIntentionManager;

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }



    @GetMapping("/list")
    public Object list(String username,
                       String mobile,
                       String startTime,
                       String endTime,
                       Integer status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<AdminUserIntentionVo> result = Lists.newArrayList();
        List<TbUser> userList = wxUserManager.querySelective(username, mobile, startTime,
                endTime, status, page, limit, sort, order);
        if (CollectionUtils.isNotEmpty(userList)) {
            List<Integer> userIdList = userList.stream().map(TbUser::getId).collect(Collectors.toList());
            List<TbUserIntention> tbUserIntentions = userIntentionManager.queryByUserIdList(userIdList);
            if (CollectionUtils.isNotEmpty(tbUserIntentions)) {
                Map<Integer, TbUserIntention> userIntentionMap = tbUserIntentions.stream()
                        .collect(Collectors.toMap(TbUserIntention::getUserId, Function.identity()));
                for (TbUser user : userList) {
                    AdminUserIntentionVo adminUserIntentionVo = new AdminUserIntentionVo();
                    BeanUtils.copyProperties(user, adminUserIntentionVo);
                    TbUserIntention tbUserIntention = userIntentionMap.get(user.getId());
                    if(tbUserIntention ==null) {
                        adminUserIntentionVo.setIntention("??????");
                    }else {
                        StringBuilder sb = new StringBuilder();
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getAgeRange())){
                            sb.append("????????????:").append(tbUserIntention.getAgeRange()).append("???").append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getMinimumEducation())){
                            sb.append("????????????:").append(tbUserIntention.getMinimumEducation()).append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getHeightRange())){
                            sb.append("????????????:").append(tbUserIntention.getHeightRange()).append("??????").append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getWeightRange())){
                            sb.append("????????????:").append(tbUserIntention.getWeightRange()).append("??????").append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getIndustry())){
                            sb.append("??????:").append(tbUserIntention.getIndustry()).append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getIncomeRange())){
                            sb.append("????????????:").append(tbUserIntention.getIncomeRange()).append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getPlace())){
                            sb.append("?????????:").append(tbUserIntention.getPlace()).append(",");
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUserIntention.getMarriage())){
                            sb.append("????????????:").append(tbUserIntention.getMarriage());
                        }
                        adminUserIntentionVo.setIntention(sb.toString());
                    }
                    result.add(adminUserIntentionVo);
                }
            }else {
                return ResponseUtil.okList(userList);
            }
        }
        return ResponseUtil.okList(result);
    }

    @GetMapping("/getById")
    public Object userDetail(@NotNull Integer id) {
        TbUser user= wxUserManager.findById(id);
        return ResponseUtil.ok(user);
    }

    @GetMapping("/deleteUser")
    public Object deleteUser(@NotNull String ids) {
        String[] split = ids.split(",");
        List<String> idList = Arrays.asList(split);
        if (CollectionUtils.isNotEmpty(idList)) {
            for(String idStr :idList) {
                TbUser byId = wxUserManager.findById(Integer.parseInt(idStr));
                wxUserManager.deleteUser(byId);
            }
        }
        return ResponseUtil.ok(ids);
    }

    @PostMapping("/allowedRegister")
    public Object allowedRegister(@RequestBody String body) {
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileSimple(phoneNumber)) {
        return ResponseUtil.badArgumentValue();
    }
    List<TbUser> tbUsers = wxUserManager.queryByMobile(phoneNumber);
    TbUser tbUser = null;
        if (!CollectionUtils.isEmpty(tbUsers)) {
        tbUser= tbUsers.get(0);
    } else {
        return ResponseUtil.fail(-1, "???????????????");
    }
        Boolean allowed = JacksonUtil.parseBoolean(body, "allowed");
        if (Boolean.TRUE.equals(allowed)) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(tbUser.getPassword())) {
                return ResponseUtil.fail(-1,"??????????????????");
            }
            String rawPassword = CharUtil.getRandomString(6);
            //String rawPassword ="123456";
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(rawPassword);
            tbUser.setPassword(encodedPassword);
            tbUser.setStatus(0);
            notifyService.notifyAllowSmsTemplate(phoneNumber, NotifyType.ALLOW, new String[]{phoneNumber,rawPassword});
        } else {
            if (tbUser.getStatus() == 2) {
                return ResponseUtil.fail(-1,"??????????????????");
            }
            tbUser.setStatus(2);
            notifyService.notifySmsTemplate(phoneNumber, NotifyType.REJECT, new String[]{});
        }
        wxUserManager.updateUser(tbUser);
        return ResponseUtil.ok();
    }

    /**
     * ??????Excel
     * @param response
     * @throws IOException
     */
    @RequestMapping("/export")
    public void exportExcel(String username,
                            String mobile,
                            String startTime,
                            String endTime,
                            Integer status,
                            @RequestParam(defaultValue = "add_time") String sort,
                            @RequestParam(defaultValue = "desc") String order,
                            HttpServletResponse response) throws IOException {
        //???????????????????????????
        List<TbUser> userList = wxUserManager.querySelective(username, mobile, startTime,
                endTime, status, null, null, sort, order);
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        List<ExcelUser> users  = Lists.transform(userList, (entity) -> {
            ExcelUser vo = new ExcelUser();
            BeanUtils.copyProperties(entity, vo);
            vo.setStatusDesc(UserStatusEnum.getDesc(entity.getStatus()));
            return vo;
        });
        //??????excel
        /**
         * sheet?????????excel?????? ??????????????????new  ExportParams??????
         */
        Workbook workbook= ExcelExportUtil.exportExcel(new ExportParams("????????????","????????????"),
                ExcelUser.class,users);
        response.setHeader("content-disposition",
                "attachment;fileName="+ URLEncoder.encode("????????????.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

}
