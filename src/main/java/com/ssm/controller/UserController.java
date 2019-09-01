package com.ssm.controller;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.pojo.User;
import com.ssm.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {
    @Autowired
    public UserService userService;
    //http://localhost:8081/user.action
    @RequestMapping(value = "/user")
    public String getUserById(Model model, HttpServletRequest request) {
        User user = userService.selectByPrimaryKey(1);
        System.out.println(user + "----------------------------------------" + user.getPname());
        model.addAttribute("cc", user);
        request.getSession().setAttribute("u", user);
        return "main.jsp";
    }

}
