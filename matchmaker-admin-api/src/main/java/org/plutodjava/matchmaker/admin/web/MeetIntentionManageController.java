package org.plutodjava.matchmaker.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plutodjava.matchmaker.core.storage.StorageService;
import org.plutodjava.matchmaker.core.utils.ResponseUtil;
import org.plutodjava.matchmaker.core.utils.XuRemarkUtil;
import org.plutodjava.matchmaker.db.domain.TbFlippedMobileGroup;
import org.plutodjava.matchmaker.db.manager.FlippedMobileGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.plutodjava.matchmaker.core.utils.XuRemarkUtil.getContentType;

@RestController
@RequestMapping("/matchmaker/admin/meetIntention")
@Validated
public class MeetIntentionManageController {
    private final Log logger = LogFactory.getLog(MeetIntentionManageController.class);
    @Autowired
    private StorageService storageService;
    @Autowired
    private FlippedMobileGroupManager flippedMobileGroupManager;

    @GetMapping("/list")
    public Object list(String flippedMobile,
                       String byFlippedMobile,
                       String startTime,
                       String endTime,
                       Boolean apply ,
                       Boolean hand ,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<TbFlippedMobileGroup> flippedMobileGroupList = flippedMobileGroupManager.querySelective(flippedMobile, byFlippedMobile, startTime,
                endTime, apply, hand, page, limit, sort, order);
        return ResponseUtil.okList(flippedMobileGroupList);
    }

    @PostMapping("applyOrHand")
    public Object applyOrHand(Integer id,
                              Boolean apply ,
                              Boolean hand) {
        TbFlippedMobileGroup tbFlippedMobileGroup = flippedMobileGroupManager.findById(id);
        tbFlippedMobileGroup.setApply(apply);
        tbFlippedMobileGroup.setHand(hand);
        if(hand != null) {
            if (!hand) {
                tbFlippedMobileGroup.setTypicalUrl(null);
            }
        }
        if (flippedMobileGroupManager.updateById(tbFlippedMobileGroup) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(tbFlippedMobileGroup.getId());
    }


    @PostMapping("/uploadTypicalPic")
    public Object upload(@RequestParam(value = "file",required=false) MultipartFile file,
                         @RequestParam(value = "id") Integer id
    ) throws IOException {
        TbFlippedMobileGroup record = flippedMobileGroupManager.findById(id);
        if (!record.getHand()) {
            return ResponseUtil.fail(-1,"未牵手状态不可上传");
        }
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

        if (record.getHand()) {
            record.setTypicalUrl(url);
            flippedMobileGroupManager.updateById(record);
        }

        return ResponseUtil.ok(url);
    }

}
