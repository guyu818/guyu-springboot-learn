package com.guyu.service;

import com.guyu.mapper.StuMapper;
import com.guyu.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    StuMapper stuMapper;
    @Override
    public Stu queryByName(String name) {
        return stuMapper.queryByName(name);
    }
}
