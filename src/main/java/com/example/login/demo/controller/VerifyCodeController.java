package com.example.login.demo.controller;

import com.example.login.demo.util.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: zhouFaQuan
 * @Date: 2019/11/20 18:25
 */
@Controller

public class VerifyCodeController {
    /* 获取验证码图片*/
    @RequestMapping("/getVerifyCode")

    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {

        try {

            int width=200;

            int height=69;

            BufferedImage verifyImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

//生成对应宽高的初始图片

            String randomText = VerifyCode.drawRandomText(width,height,verifyImg);

//单独的一个类方法，出于代码复用考虑，进行了封装。

//功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符

            request.getSession().setAttribute("verifyCode", randomText);
            //必须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            //获取文件输出流
            OutputStream os = response.getOutputStream();
            //输出图片流
            ImageIO.write(verifyImg,"png",os);

            os.flush();

            os.close();//关闭流

        } catch (IOException e) {

       /*this.logger.error(e.getMessage());*/

            e.printStackTrace();

        }

    }






//    @RequestMapping ("/verifyCode3")
//
//    public String verifyCode2(String verifyCode, HttpServletRequest request, Model model){
//        // 获取存放在session中的验证码
//        String code = (String) request.getSession().getAttribute("verifyCode");
//        System.out.println("code:"+code);
//        // 获取页面提交的验证码
//        String inputCode = verifyCode;
//        System.out.println("inputCode:"+inputCode);
//        if (code.toLowerCase().equals(inputCode.toLowerCase())) {
//            // 验证码不区分大小写
//            // 验证成功，跳转到成功页面
//            System.out.println("1111");
//            return "/index";
//        } else { // 验证失败
//            System.out.println("2222222222");
//            model.addAttribute("msg1","密码错误");
//            return "/login";
//        }
//    }

}
