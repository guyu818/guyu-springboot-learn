package com.guyu.controller;

import com.guyu.mapper.StuMapper;
import com.guyu.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StuController {

    @Autowired
    StuMapper stuMapper;

    @GetMapping("/list")
    public List<Stu> list(Model model){
        List<Stu> stus = stuMapper.queryStuList();

        return stus;
    }
}
