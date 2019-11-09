package com.example.login.demo.service;

import com.example.login.demo.dao.OperationDao;
import com.example.login.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: zhouFaQuan
 * Date: 2019/11/7 11:45
 */
@Service
public class OpreationService {
 @Autowired
 private OperationDao operationDao;
    public int addUser(User user) {
        int flag = operationDao.addUser(user.getTelephone(),user.getPassword(),user.getToken());
        System.out.println("添加返回：" + flag);
        return flag;
    }

    public User getUserById(int id) {
        User user3 = operationDao.getUserById(id);
        return user3;
    }

    public int updateUser(User user) {

        int flag = operationDao.updateUser(user.getId(),user.getTelephone(),user.getPassword());
        System.out.println("更新返回：" + flag);
        return flag;
    }

    public int deleteUserById(int id) {
        int flag = operationDao.deleteUserById(id);
        System.out.println("删除返回：" + flag);
        return flag;
    }
}
