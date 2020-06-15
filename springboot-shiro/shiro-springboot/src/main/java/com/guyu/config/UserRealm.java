package com.guyu.config;

import com.guyu.pojo.Stu;
import com.guyu.service.StuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义UserRealm  extends AuthorizingRealm {
public class UserRealm extends AuthorizingRealm {

    @Autowired
    StuService stuService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权doGetAuthorizationInfo");

        //摸拟给用户授权，实际的生产中会在数据库中取
        //SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        Stu currentuser = ((Stu) subject.getPrincipal());

        System.out.println(currentuser.getPerms());
        //设置当前用户的权限
        info.addStringPermission(currentuser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=》认证doGetAuthenticationInfo");
        //用户名 密码数据中取
//        String name="root";
//        String password="123456";


        //转换成我们封装的token的类型
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;

        //连接真实的数据库
        Stu stu = stuService.queryByName(usernamePasswordToken.getUsername());

        if(stu==null){//没有这个人
            return null;//这个比较职能的抛出 账户错误的异常
        }

        //添加用户的session，为了判断登录框是不是应该出现
        //获取用户
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",stu);

        //可以加密： MD5 MD5盐值加密
        //密码认证 shiro做~，自动帮你封装好了
        System.out.println(stu);
//        return new SimpleAuthenticationInfo("",  String.valueOf(stu.getStudentid()),"");
        //这里第一个参数将stu传入，这样在授权的那部分代码中就可以将stu这个用户信息拿过来了
        return new SimpleAuthenticationInfo(stu,  String.valueOf(stu.getStudentid()),"");

    }
}
