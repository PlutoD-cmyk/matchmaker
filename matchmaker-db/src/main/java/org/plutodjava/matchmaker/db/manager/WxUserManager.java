package org.plutodjava.matchmaker.db.manager;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.plutodjava.matchmaker.db.dao.TbUserMapper;
import org.plutodjava.matchmaker.db.domain.TbUser;
import org.plutodjava.matchmaker.db.domain.TbUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class WxUserManager {
    @Resource
    private TbUserMapper userMapper;

    public TbUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public int add(TbUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insertSelective(user);
    }

    public List<TbUser> querySelective(String username, String mobile, String startTime, String endTime,
                                       Integer status, Integer page, Integer size, String sort, String order) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            log.info("startTime:{},endTime:{}",LocalDateTime.now().plusDays(-7),LocalDateTime.now());
            criteria.andAddTimeBetween(LocalDateTime.now().plusDays(-7),LocalDateTime.now());
        }else {
            criteria.andAddTimeBetween(LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        if (page ==null && size == null) {
            return userMapper.selectByExample(example);
        }
        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public List<TbUser> queryByMobile(String mobile) {
        TbUserExample example = new TbUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public int updateUser(TbUser user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public int updateById(TbUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<TbUser> queryOppositeSexUser(List<String> industryList, Integer ageStart,
                                             Integer ageEnd, List<String> educationList,
                                             Integer heightStart, Integer heightEnd,
                                             Integer weightStart, Integer weightEnd,
                                             Integer incomeStart, Integer incomeEnd,
                                             String place, String marriage, String gender,
                                             Integer page, Integer size, String sort, String order) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();

        if (!CollectionUtils.isEmpty(industryList)) {
            criteria.andIndustryIn(industryList);
        }
        if (incomeStart != null) {
            criteria.andIncomeGreaterThan(incomeStart);
        }
        if (incomeEnd != null) {
            criteria.andIncomeLessThan(incomeEnd);
        }
        if (ageStart != null) {
            criteria.andAgeGreaterThan(ageStart);
        }
        if (ageEnd != null) {
            criteria.andAgeLessThan(ageEnd);
        }
        if (heightStart != null) {
            criteria.andHeightGreaterThan(heightStart);
        }
        if (heightEnd != null) {
            criteria.andHeightLessThan(heightEnd);
        }
        if (weightStart != null) {
            criteria.andWeightGreaterThan(weightStart);
        }
        if (weightEnd != null) {
            criteria.andWeightLessThan(weightEnd);
        }
        if (!StringUtils.isEmpty(place)) {
            criteria.andPlaceLike("%" + place + "%");
        }
        if (!StringUtils.isEmpty(marriage)) {
            criteria.andMarriageEqualTo(marriage);
        }
        if (!CollectionUtils.isEmpty(educationList)) {
            criteria.andEducationIn(educationList);
        }
        criteria.andStatusEqualTo(0);
        criteria.andGenderEqualTo(gender);
        criteria.andMeetIntentionEqualTo(true);
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

}
