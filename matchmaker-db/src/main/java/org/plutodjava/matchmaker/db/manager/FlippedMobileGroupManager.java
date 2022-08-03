package org.plutodjava.matchmaker.db.manager;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.plutodjava.matchmaker.db.dao.TbFlippedMobileGroupMapper;
import org.plutodjava.matchmaker.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlippedMobileGroupManager {

    @Resource
    private TbFlippedMobileGroupMapper flippedMobileGroupMapper;

    public int add(TbFlippedMobileGroup flippedMobileGroup) {
        flippedMobileGroup.setAddTime(LocalDateTime.now());
        flippedMobileGroup.setUpdateTime(LocalDateTime.now());
        return flippedMobileGroupMapper.insertSelective(flippedMobileGroup);
    }

    public List<TbFlippedMobileGroup> checkOnly(String flipMobile, String byFlipMobile) {
        TbFlippedMobileGroupExample example = new TbFlippedMobileGroupExample();
        example.or().andFlippedMobileEqualTo(flipMobile).andDeletedEqualTo(false).andByFlippedMobileEqualTo(byFlipMobile);
        return flippedMobileGroupMapper.selectByExample(example);
    }

    public List<TbFlippedMobileGroup> queryApplyByFlipMobile(List<String> mobileList) {
        TbFlippedMobileGroupExample example = new TbFlippedMobileGroupExample();
        example.or().andFlippedMobileIn(mobileList).andDeletedEqualTo(false).andApplyEqualTo(true);
        return flippedMobileGroupMapper.selectByExample(example);
    }

    public List<TbFlippedMobileGroup> queryHandByFlipMobile(List<String> mobileList) {
        TbFlippedMobileGroupExample example = new TbFlippedMobileGroupExample();
        example.or().andFlippedMobileIn(mobileList).andDeletedEqualTo(false).andApplyEqualTo(true);
        return flippedMobileGroupMapper.selectByExample(example);
    }

    public List<TbFlippedMobileGroup> querySelective(String flippedMobile, String byFlippedMobile,
                                       String startTime, String endTime, Boolean apply,
                                       Boolean hand, Integer page, Integer size, String sort, String order) {
        TbFlippedMobileGroupExample example = new TbFlippedMobileGroupExample();
        TbFlippedMobileGroupExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            log.info("startTime:{},endTime:{}",LocalDateTime.now().plusDays(-7),LocalDateTime.now());
            criteria.andAddTimeBetween(LocalDateTime.now().plusDays(-7),LocalDateTime.now());
        }else {
            criteria.andAddTimeBetween(LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (!StringUtils.isEmpty(byFlippedMobile)) {
            criteria.andByFlippedMobileEqualTo(byFlippedMobile);
        }
        if (apply != null) {
            criteria.andApplyEqualTo(apply);
        }
        if (hand != null) {
            criteria.andHandEqualTo(hand);
        }
        if (!StringUtils.isEmpty(flippedMobile)) {
            criteria.andFlippedMobileEqualTo(flippedMobile);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return flippedMobileGroupMapper.selectByExample(example);
    }

    public TbFlippedMobileGroup findById(Integer userId) {
        return flippedMobileGroupMapper.selectByPrimaryKey(userId);
    }

    public int updateById(TbFlippedMobileGroup tbFlippedMobileGroup) {
        tbFlippedMobileGroup.setUpdateTime(LocalDateTime.now());
        return flippedMobileGroupMapper.updateByPrimaryKeySelective(tbFlippedMobileGroup);
    }

    public List<String> findAllTypicalList() {
        TbFlippedMobileGroupExample example = new TbFlippedMobileGroupExample();
        example.or().andTypicalUrlIsNotNull().andDeletedEqualTo(false).andHandEqualTo(true);
        List<TbFlippedMobileGroup> flippedMobileGroupList = flippedMobileGroupMapper.selectByExample(example);
        List<String> collect = new ArrayList<>();
        if (!CollectionUtils.isEmpty(flippedMobileGroupList)) {
            collect = flippedMobileGroupList.stream().map(TbFlippedMobileGroup::getTypicalUrl).collect(Collectors.toList());
        }
        return collect;
    }
}
