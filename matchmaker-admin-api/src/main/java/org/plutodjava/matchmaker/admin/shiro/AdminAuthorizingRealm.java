package org.plutodjava.matchmaker.admin.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.plutodjava.matchmaker.db.domain.TbAdmin;
import org.plutodjava.matchmaker.db.manager.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class AdminAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private AdminManager adminManager;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AccountException("密码不能为空");
        }
        TbAdmin tbAdmin = adminManager.findAdmin(username);
        if (!tbAdmin.getPassword().equals(password)) {
            throw new UnknownAccountException("密码错误");
        }
        return new SimpleAuthenticationInfo(tbAdmin, password, getName());
    }

}
