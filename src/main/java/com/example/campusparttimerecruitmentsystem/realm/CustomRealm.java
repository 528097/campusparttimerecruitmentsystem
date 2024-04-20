package com.example.campusparttimerecruitmentsystem.realm;

import com.example.campusparttimerecruitmentsystem.Util.JwtToken;
import com.example.campusparttimerecruitmentsystem.Util.JwtUtils;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.service.IUsersService;
import com.example.campusparttimerecruitmentsystem.service.impl.UsersServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UsersServiceImpl usersServiceimpl; // 假设您有一个服务类来处理员工数据

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) getAvailablePrincipal(principals);
        String email = JwtUtils.getClaimByToken(token).getSubject();
        Users users = usersServiceimpl.findByEmail(email);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 根据员工职位添加角色
        info.addRole(users.getRole());
        // 根据职位添加权限
        if ("admin".equals(users.getRole())) {
            info.addStringPermission("attendance:get");
            info.addStringPermission("attendance:create");
            info.addStringPermission("attendance:update");
            info.addStringPermission("attendance:delete");
            info.addStringPermission("attendance:end");
            info.addStringPermission("leave:apply");
            info.addStringPermission("leave:approve");
            info.addStringPermission("leave:list");
            info.addStringPermission("leave:delete");
            info.addStringPermission("attendance:userinfo");
            info.addStringPermission("attendance:get/want");
        } else {
            info.addStringPermission("attendance:get");
            info.addStringPermission("attendance:create");
            info.addStringPermission("attendance:end");
            info.addStringPermission("attendance:userinfo");
            info.addStringPermission("leave:apply");
            info.addStringPermission("leave:list");

        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 将原来的UsernamePasswordToken改成我们的JwtToken
        JwtToken jwtToken = (JwtToken) token;
        String jwt = (String) jwtToken.getPrincipal();
        String email = JwtUtils.getClaimByToken(jwt).getSubject();
        Users users = usersServiceimpl.findByEmail(email);
        if (users != null) {
            // 这里将credentials改为jwt
            return new SimpleAuthenticationInfo(jwt, jwt, getName());
        } else {
            throw new UnknownAccountException("用户不存在！");
        }
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 使得realm够处理jwtToken
        return token instanceof JwtToken;
    }

}
