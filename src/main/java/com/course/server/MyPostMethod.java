package com.course.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyPostMethod {
    @RequestMapping(value = "/login",method=RequestMethod.POST)
    public String Login(@RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "password",required = true) String password,
                        HttpServletResponse response){
        if(userName.equals("zhangsan") && password.equals("123456")){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "登录成功";
        } else{
            return "用户名和密码不正确";
        }
    }
}
