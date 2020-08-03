package com.wzx.realm;

import com.wzx.po.Permission;
import com.wzx.po.Role;
import com.wzx.po.User;
import com.wzx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class NewsRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    public void setName(String name){
        setName("NewsRealm");
    }

    //完成授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();
        Set<Role> roles=user.getRoles();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        for(Role role:roles){
            info.addRole(role.getName());
            for(Permission p:role.getPermissions()){
                info.addStringPermission(p.getCode());
            }
        }
        return info;
    }
    //完成认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

       UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User user = userService.CheckUser(username, password);
        if(user!=null){
            return new SimpleAuthenticationInfo(user,password,this.getName());
        }
        return null;
    }
}
