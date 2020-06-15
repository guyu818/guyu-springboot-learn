package com.guyu.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    // ShiroFilterFactoryBean :第三步
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
        anon:无需认证就可以访问
        authc：必须认证才可以访问
        user： 必须拥有 记住我 这个功能才能用
        perms：拥有对某个资源的访问权限才可以访问
        role：拥有某个角色的权限才可以访问
         */
        //拦截
        Map<String, String> filterChainDefinitionMap=new HashMap<>();
        //****************授权的这段代码一定要在上面，可以把这个整理到文件上作为面试的一个考点**************
        //授权,正常的情况下，没有授权会跳转到未授权的页面
        filterChainDefinitionMap.put("/user/add","perms[user:add]");
        filterChainDefinitionMap.put("/user/update","perms[user:update]");
//        filterChainDefinitionMap.put("/user/add","authc");
//        filterChainDefinitionMap.put("/user/update","authc");
        //这一句代替上面的，可以使用通配符
        filterChainDefinitionMap.put("/user/*","authc");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置登录请求
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");

        return shiroFilterFactoryBean;
    }
    //DefaultWebSecurityManager :第二步
//    @Qualifier("userRealm") UserRealm userRealm 这里以spring的方式传进去，这里需要注意
//    也可以绑定自己命名的名字
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //关联UserRealm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }


    //创建realm对象 需要自定义类： 第一步
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    //整合shirodialect：用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
