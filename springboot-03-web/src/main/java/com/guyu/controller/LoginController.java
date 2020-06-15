package com.guyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String name,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        //判断用户名不为空，并且密码是123456就可以进来
        if (!StringUtils.isEmpty(name) && "123456".equals(password) ){
            //跳转到主页面
            session.setAttribute("loginUser",name);
            return "redirect:/main.html";
        }else {
            //告诉用户，你登录失败了
            model.addAttribute("msg","你登陆失败了");
            return "index";
        }
    }

    //注销
    @RequestMapping("/user/logout")
    public String logout(HttpSession session){

        //remove或者invalidate使session注销
        session.invalidate();
        return "redirect:/index.html";
    }



}
