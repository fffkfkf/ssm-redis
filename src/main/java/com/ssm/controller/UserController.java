package com.ssm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.pojo.User;
import com.ssm.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {
    @Autowired
    public UserService userService;

    @RequestMapping(value = "/user")
    public String getUserById(Model model, HttpServletRequest request) {
        User user = userService.selectByPrimaryKey(1);
        System.out.println(user + "----------------------------------------" + user.getPname());
        model.addAttribute("cc", user);
        request.getSession().setAttribute("u", user);
        return "main";
    }
}
