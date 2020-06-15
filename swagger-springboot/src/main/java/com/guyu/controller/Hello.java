package com.guyu.controller;

import com.guyu.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "hellocontroller的模块")
@RestController
public class Hello {

    @RequestMapping("/hello")
    public String hello(){
        return "hello,guyu";
    }

    @ApiOperation("getUser接口说明")
    @RequestMapping("getUser")
    public User getUser(@ApiParam("这是接口的参数")String name){
        return new User();
    }
    @ApiOperation("摸拟报错接口")
    @RequestMapping("getUser1")
    public User getUser1(@ApiParam("这是接口的参数")String name){
        int i=5/0;
        return new User();
    }
}
