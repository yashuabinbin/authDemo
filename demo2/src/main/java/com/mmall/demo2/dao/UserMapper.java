package com.mmall.demo2.dao;

import com.mmall.demo2.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectByUsername(@Param("username") String username);

}