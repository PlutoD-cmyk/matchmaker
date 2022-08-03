package org.plutodjava.matchmaker.db.manager;

import lombok.extern.slf4j.Slf4j;
import org.plutodjava.matchmaker.db.dao.TbUserIntentionMapper;
import org.plutodjava.matchmaker.db.domain.TbUserIntention;
import org.plutodjava.matchmaker.db.domain.TbUserIntentionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserIntentionManager {
    @Resource
    private TbUserIntentionMapper userIntentionMapper;

    public TbUserIntention findById(Integer id) {
        return userIntentionMapper.selectByPrimaryKey(id);
    }

    public int add(TbUserIntention userIntention) {
        userIntention.setAddTime(LocalDateTime.now());
        userIntention.setUpdateTime(LocalDateTime.now());
        return userIntentionMapper.insertSelective(userIntention);
    }

    public List<TbUserIntention> queryByUserId(Integer userId) {
        TbUserIntentionExample example = new TbUserIntentionExample();
        example.or().andUserIdEqualTo(userId);
        return userIntentionMapper.selectByExample(example);
    }

    public List<TbUserIntention> queryByUserIdList(List<Integer> userIdList) {
        TbUserIntentionExample example = new TbUserIntentionExample();
        if (CollectionUtils.isEmpty(userIdList)) {
            return new ArrayList<>();
        }
        example.or().andUserIdIn(userIdList);
        return userIntentionMapper.selectByExample(example);
    }

    public int updateUserIntention(TbUserIntention user) {
        return userIntentionMapper.updateByPrimaryKey(user);
    }

    public int updateById(TbUserIntention userIntention) {
        userIntention.setUpdateTime(LocalDateTime.now());
        return userIntentionMapper.updateByPrimaryKeySelective(userIntention);
    }
}
