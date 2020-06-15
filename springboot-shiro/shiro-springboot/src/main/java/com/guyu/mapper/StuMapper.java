package com.guyu.mapper;

import com.guyu.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StuMapper {
    public Stu queryByName(String name);
}
