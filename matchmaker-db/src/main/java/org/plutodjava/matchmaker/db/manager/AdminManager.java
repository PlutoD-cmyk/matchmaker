package org.plutodjava.matchmaker.db.manager;

import org.plutodjava.matchmaker.db.dao.TbAdminMapper;
import org.plutodjava.matchmaker.db.domain.TbAdmin;
import org.plutodjava.matchmaker.db.domain.TbAdminExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminManager {
    private final TbAdmin.Column[] result = new TbAdmin.Column[]{TbAdmin.Column.id, TbAdmin.Column.username};
    @Resource
    private TbAdminMapper adminMapper;

    public TbAdmin findAdmin(String username) {
        TbAdminExample example = new TbAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectOneByExample(example);
    }
}
