package com.example.login.demo.controller;

import com.example.login.demo.entity.User;
import com.example.login.demo.service.LoginService;
import com.example.login.demo.util.CookieUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zhouFaQuan
 * @Date: 2019/10/28 21:36
 * @Controller用于标注控制层组件
 */
@Controller

public class LoginController {
    @Autowired
    private LoginService loginService;
    private HttpServletResponse response;


    @GetMapping("/login")
    public String login(@Param("telephone") String telephone, @Param("password") String password, @Param("verifyCode") String verifyCode,Model model,
                        HttpServletRequest request, HttpServletResponse response) {

         User user1 = loginService.findUser(telephone);
        if (user1 != null) {
           if (!user1.getPassword().equals(password)) {
               model.addAttribute("msg","密码错误");
               return "login";
           } else {
                   String code = (String) request.getSession().getAttribute("verifyCode");
                   System.out.println("code:"+code);
                   // 获取页面提交的验证码
                   String inputCode = verifyCode;
                   System.out.println("inputCode:"+inputCode);
                   if (code.toLowerCase().equals(inputCode.toLowerCase())) {
                       HttpSession session = request.getSession();
                       session.setAttribute("telephone", telephone);
                       return "/index";

                   }else {
                       model.addAttribute("msg", "验证码错误");
                       return "/login";
                   }
           }
        } else if (telephone != null && password!=null) {
            // getAttribute的返回值类型是Object,需要向下转型，转成你的verifyCode类型的，简单说就是存什么，取出来还是什么。
            String code = (String) request.getSession().getAttribute("verifyCode");
            System.out.println("code:"+code);
            // 获取页面提交的验证码
            String inputCode = verifyCode;
            System.out.println("inputCode:"+inputCode);
            if (code.toLowerCase().equals(inputCode.toLowerCase())) {
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setTelephone(telephone);
                user.setPassword(password);
                user.setToken(token);
                loginService.insertUser(user);
                //表示7天
                int expire = 60 * 60 * 24 * 7;
                CookieUtil.setCookie(request, response, "Token", token, expire);
                HttpSession session = request.getSession();
                session.setAttribute("telephone",telephone);
                return "index";
            }else {
                model.addAttribute("msg", "验证码错误");
                return "/login";
            }


        }
        else {
            return "login";
        }
    }
    @RequestMapping("/main")
    public String mainPage(HttpServletRequest request){
        // 判断cookie是否存在，如存在则利用cookie登录，否则返回登录界面
        Map<String, String> map = CookieUtil.getCookies(request);
        String token = map.get("Token");
        if( token!= null){

                return "index";
        }
        return "login";
    }
    @GetMapping("/login2")
    public String login2(HttpSession session){

        return "/login";
    }
    @GetMapping("/logout")
    //退出登录清除cookies 和 session
    public String logout(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        session.invalidate();
        CookieUtil cookieUtil = new CookieUtil();
        cookieUtil.clear(request,response);
        return "/login";
    }
    @GetMapping("/list.action")
    public String fengye(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         Model model){
        //通过构造PageInfo对象获取分页信息
        try {
            PageInfo<User> pp =  loginService.selectAll(pageNum,5);
            if (pp != null) {
                model.addAttribute("pageInfo", pp);
                System.out.println(pp);
                return "list";
            } else {

                return  null;
            }
        } catch (Exception e) {

            return null;
        }
    }

}


