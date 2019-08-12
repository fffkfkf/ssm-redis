package com.dell.anli02_web;

import com.dell.anli03_service.UserService;
import com.dell.anli04_serviceimple.UserServiceImpl;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyBaseServlet;
import com.dell.anli08_util.MyBeanUtilsPlus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 用户模块的servlet,与用户相关的所有操作都使用这个servlet完成
 */
public class UserServlet extends MyBaseServlet {
	//1:用户注册的方法
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 参 调 存 转
			1:参,获取所有参数封装成user对象
			2:调用业务层,传递user对象;
			3:将添加的结果保存到request中
			4:转发到信息提示页面
		 */
		//1:参,获取所有参数封装成user对象
		Map<String, String[]> map = request.getParameterMap();
		User u = MyBeanUtilsPlus.populate(User.class, map);
		//2:调用业务层,传递user对象;
		UserService us = (UserService) new UserServiceImpl();
		String msg=us.regist(u);
		//3:将添加的结果保存到request中
		request.setAttribute("msg",msg);
		//4:转发到信息提示页面
		return "/jsp/info.jsp";
	}
	//2:用户激活的方法
	public String activeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,获取激活码
			2:调,根据激活码
			调用业务层修改数
			据库中用户状态
			3:将激活结果保存
			到request
			4:转发到信息提示
			页面
		 */
		String code = request.getParameter("code");
		UserService us = (UserService) new UserServiceImpl();
		String msg=us.activeUser(code);
		request.setAttribute("msg",msg);
		return "/jsp/info.jsp";
	}
	//3:用户登录的方法
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,获取用户名,密码,验证码;
			2:检验验证码;
			3:调用业务层,根据用户名获取一个user对象;
			4:判断用户是否存在,如果不存在,说明用户名错误,
			5:继续判断状态,如果状态为0,说明没有激活
			6:判断密码,如果密码错误,则提示密码错误
			7:将用户对象保存到session中
			8:转发到信息提示页面
		 */
		//1:参,获取用户名,密码,验证码;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usercode = request.getParameter("code");
		//2:检验验证码;
		HttpSession se = request.getSession();
		String sessionCode = (String) se.getAttribute("code");
		if(sessionCode==null||!sessionCode.equalsIgnoreCase(usercode)){
			request.setAttribute("msg","亲,验证码错了!");
			return "/jsp/login.jsp";
		}
		//3:调用业务层,根据用户名获取一个user对象;
		UserService us = (UserService) new UserServiceImpl();
		User u=us.findUserByUsername(username);
		//4:判断用户是否存在,如果不存在,说明用户名错误,
		if(u==null){
			request.setAttribute("msg","亲,用户名不存在呀,先去注册吧!");
			return "/jsp/info.jsp";
		}
		//5:继续判断状态,如果状态为0,说明没有激活
		if(u.getState()==0){
			request.setAttribute("msg","亲,您还没有激活呢,请先登录邮箱激活吧!");
			return "/jsp/info.jsp";
		}
		//6:判断密码,如果密码错误,则提示密码错误
		if(!u.getPassword().equals(password)){
			request.setAttribute("msg","亲,密码错了!");
			return "/jsp/login.jsp";
		}
		//7:将用户对象保存到session中
		se.setAttribute("user",u);
		//-------------------------记住用户名的代码--------开始----------------------------
		//1:创建cookie,设置路径
		Cookie c = new Cookie("remember",URLEncoder.encode(username,"utf-8"));
		c.setPath("/");
		//2:判断用户是否勾选了记住用户名
		String ok = request.getParameter("remember");
		if("ok".equals(ok)){
			c.setMaxAge(60*60);
		}else{
			c.setMaxAge(0);
		}
		//3:响应cookie
		response.addCookie(c);
		//-------------------------记住用户名的代码---------结束---------------------------
		//重定向到Productservlet中
		//response.sendRedirect(request.getContextPath()+"/ProductServlet?m=findHotAndNew");
		return "/index.html";
	}
	//3:用户退出登录的方法
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1:直接销毁session对象,并转发到登录页面
		request.getSession().invalidate();
		//2:转发
		return "/jsp/login.jsp";
	}
}
