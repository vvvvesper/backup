package com.course.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
public class MyGetMethod {

    //获取cookie
    @RequestMapping(value = "/getCookie",method=RequestMethod.GET)
    public String getCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "get cookie success!";
    }


    //要求客户端携带cookie访问
    @RequestMapping(value = "/getWithCookie",method=RequestMethod.GET)
    public String getWithCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies))
            return "请携带cookie来";
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true"))
                return "携带cookie访问成功";
        }
        return "请携带cookie来";
    }
}



