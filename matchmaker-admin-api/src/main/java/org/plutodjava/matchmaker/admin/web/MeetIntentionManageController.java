package org.plutodjava.matchmaker.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plutodjava.matchmaker.core.utils.ResponseUtil;
import org.plutodjava.matchmaker.db.domain.TbFlippedMobileGroup;
import org.plutodjava.matchmaker.db.manager.FlippedMobileGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matchmaker/admin/meetIntention")
@Validated
public class MeetIntentionManageController {
    private final Log logger = LogFactory.getLog(MeetIntentionManageController.class);

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
        if (flippedMobileGroupManager.updateById(tbFlippedMobileGroup) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(tbFlippedMobileGroup.getId());
    }
}
