package com.guyu.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //请求授权的规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限默认会到登录页面，需要开启登录页面
        //login
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLogin")
        .loginProcessingUrl("/login");
        //如果注销404了，就是因为它默认防止csrf跨站请求伪造，因为会产生安全问题，我们可以将请求改为post表单提交，
        //或者在spring security中关闭csrf功能；我们试试：在 配置中增加 `http.csrf().disable();`
        http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求

        //开启注销功能
            //注销成功跳转到首页
        http.logout().logoutSuccessUrl("/");

        //登录时出现记住我，勾选上可以在14天之内免登录
        http.rememberMe().rememberMeParameter("remember");
    }

    //认证，spring boot2.1.x 可以直接使用
    //密码编码 passwordEncoder
    //在spring security 5.0+ 增加了很多的加密方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码这些数码正常从数据库中读
        //这种机密的方式是他推荐的一种BCryptPasswordEncoder
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("guyu").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}
