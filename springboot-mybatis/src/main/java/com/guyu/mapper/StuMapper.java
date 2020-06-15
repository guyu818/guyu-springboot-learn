package com.guyu.mapper;

import com.guyu.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示这是一个mybatis的mapper类：dao=
@Mapper
@Repository
public interface StuMapper {
    List<Stu> queryStuList();

    int addStu(Stu stu);
}
