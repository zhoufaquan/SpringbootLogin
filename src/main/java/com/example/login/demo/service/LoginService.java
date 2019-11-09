package com.example.login.demo.service;

import com.example.login.demo.dao.LoginDao;
import com.example.login.demo.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: zhouFaQuan
 * Date: 2019/10/28 22:27
 */
@Service
//@Service服务层组件，用于标注业务层组件,表示定义一个bean，自动根据bean的类名实例化一个首写字母为小写的bean，
//   例如Chinese实例化为chinese，如果需要自己改名字则:@Service("你自己改的bean名")。
public class LoginService {
    @Autowired
   private LoginDao loginDao;

    public User findByIdToken(String token) {
        User user2 = new User();
        loginDao.findByIdToken(token);
        return user2;
    }

    public int insertUser(User user){
        User user1 = new User();
        user1.setToken(user.getToken());
        user1.setTelephone(user.getTelephone());
        user1.setPassword(user.getPassword());
        int flag =loginDao.insertUser(user1.getTelephone(),user1.getPassword(),user1.getToken());
        return flag;
    }
    public User findUser(String telephone){
        User user1 = loginDao.findUser(telephone);
        return user1;
    }

    public PageInfo<User> selectAll(int page, int size) {
        //1）静态分页
        PageHelper.startPage(page, size);
        //2）集合查询
        List<User> list = loginDao.selectAll();
        //3）返回PageInfo:包含数据结果集+分页信息
        return new PageInfo<User>(list);
    }
}
