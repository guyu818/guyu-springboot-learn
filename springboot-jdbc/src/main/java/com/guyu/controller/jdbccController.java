package com.guyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//jdbctemplate测试
@RestController
public class jdbccController {
    /**
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，用于简化 JDBC操作
     * 还能避免一些常见的错误,使用起来也不用再自己来关闭数据库连接
     */
    @Autowired
    JdbcTemplate jdbcTemplate;
    //查询所有
    @GetMapping("/list")
    public List<Map<String,Object>> userList(){
        String sql="select * from stu";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
    //增
    @GetMapping("/add")
    public String add(){
        String sql="insert into stu(studentid,name,score) values(1,'guyu',88)";
        jdbcTemplate.update(sql);
        return "addok";
    }
    //改
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id){
        //更改
        String sql="update stu set name=?,score=? where studentid="+id;
        //数据
        Object[] objects=new Object[2];
        objects[0]="谷雨";
        objects[1]=100;

        jdbcTemplate.update(sql,objects);
        return "updateok";
    }
    //删
    @GetMapping("/delete/{id}")
    public  String delete(@PathVariable("id") int id){
        String sql="delete from stu where studentid=?";
        jdbcTemplate.update(sql,id);
        return "deleteok";
    }


}
