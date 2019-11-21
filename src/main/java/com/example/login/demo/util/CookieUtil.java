package com.example.login.demo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: zhouFaQuan
 * Date: 2019/10/30 14:40
 */
public class CookieUtil {
    //设置Cookie
    //参数根据需求添加
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int expiry){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    //获取所有Cookie
    public static Map<String, String> getCookies(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Cookie cookies[] = request.getCookies();
        if (cookies != null){
            for(int i = 0; i < cookies.length; i++){
                if(!"token".equals(cookies[i].getName())) {
                    map.put(cookies[i].getName(), cookies[i].getValue());
                }
            }
        }
        return map;
    }

    //清空所有的Cookie
    public void clear(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map = getCookies(request);
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> me = iter.next();
            Cookie cookie = new Cookie(me.getKey(), "");//假如要删除名称为me.getKey()的Cookie
            cookie.setMaxAge(0); //立即删除型
            response.addCookie(cookie); //重新写入，将覆盖之前的
        }
    }
}
