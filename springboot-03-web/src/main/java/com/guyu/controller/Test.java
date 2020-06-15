package com.guyu.controller;

import com.guyu.pojo.Dto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Test {


    @GetMapping("test")
    @ResponseBody
    public Dto getRes(HttpServletResponse response){
        Dto dto = new Dto();
        dto.setX(1);
        dto.setX(2);
        response.setCharacterEncoding("utf-8");
        return dto;
    }


}
