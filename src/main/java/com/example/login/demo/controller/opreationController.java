package com.example.login.demo.controller;

import com.example.login.demo.entity.User;
import com.example.login.demo.service.LoginService;
import com.example.login.demo.service.OpreationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Author: zhouFaQuan
 * Date: 2019/11/7 11:43
 */
@Controller
public class opreationController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private OpreationService opreationService;

    @RequestMapping(value = "/add.action")
    //加了@ResponseBody 就不会返回给页面thmeleaf，直接返回给请求
    @ResponseBody
    public String addUser(@Param("telephone") String telephone, @Param("password") String password, Model model){
        int flag = 0;
        User user1 = loginService.findUser(telephone);
        if (user1 != null) {
                 System.out.println(" 用户已存在！");
                 return "FAIL";
        } else if(password!="" && telephone!="") {

                     User user = new User();
                     String token = UUID.randomUUID().toString();
                     user.setTelephone(telephone);
                     user.setPassword(password);
                     user.setToken(token);
                     flag =  opreationService.addUser(user);
                        if (flag != 0){
                             return "OK";
                          } else {
                            return "FAIL";
                         }
        } else {
            return "FAIL";

        }

    }
    @RequestMapping("/delete.action")
    @ResponseBody
    public String deleteUserById(int id){
        int row = opreationService.deleteUserById(id);
        if (row>0){
            return "OK";
        } else {
           return "FAIL" ;
        }

    }
    @RequestMapping(value = "update.action")
    @ResponseBody
    public String updateUser(User user){
        int flag = 0;
        User user1 = loginService.findUser(user.getTelephone());
        if (user1 != null) {
            System.out.println(" 用户已存在！");
            return "FAIL";
        } else if(user.getPassword()!="" && user.getTelephone()!="") {

                    int rows = opreationService.updateUser(user);
                    if (rows==1){
                        return "OK";
                    }else{
                        return "FAIL";
                    }
        } else {
                    return "FAIL";
                }
            }

    }

