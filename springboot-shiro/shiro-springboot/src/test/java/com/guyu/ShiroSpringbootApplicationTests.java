package com.guyu;

import com.guyu.mapper.StuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    StuMapper stuMapper;
    @Test
    void contextLoads() {
        System.out.println(stuMapper.queryByName("谷雨"));
    }

}
