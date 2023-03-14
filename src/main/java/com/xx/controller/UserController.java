package com.xx.controller;

import com.alibaba.fastjson.JSONObject;
import com.xx.entity.User;
import com.xx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "back/login";
    }

    @RequestMapping("login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> hashMap = new HashMap<>();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            User login = userService.login(user);
            hashMap.put("success",true);
            hashMap.put("msg","登录成功！");
            request.getSession().setAttribute("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("success",false);
            hashMap.put("msg",e.getMessage());
        }
        String s = JSONObject.toJSONString(hashMap);
        writer.print(s);
        return null;
    }
}
