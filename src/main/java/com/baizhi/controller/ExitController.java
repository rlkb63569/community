package com.baizhi.controller;

import com.baizhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ExitController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        session.removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }
}
