package com.example.login.demo.dao;

import com.example.login.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: zhouFaQuan
 * Date: 2019/11/1 10:25
 */
@Repository
public interface LoginDao {
    int findByIdToken(@Param("token") String token);

    int insertUser( @Param("telephone")String telephone,@Param("password") String password,@Param("token") String token);

    User findUser(@Param("telephone") String telephone);

    List<User> selectAll();
}
