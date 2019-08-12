package com.dell.anli02_web;

import com.dell.anli03_service.CategoryService;
import com.dell.anli07_domain.Category;
import com.dell.anli08_util.MyBaseServlet;
import com.dell.anli08_util.MyBeanFactory;
import com.dell.anli08_util.MyJedisUtils;
import com.dell.anli08_util.MyJsonUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分类相关操作的servlet
 */
public class CategoryServlet extends MyBaseServlet {
	//1:同步查询的方法
	public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,无参数
			2:调,调用业务层查询数据库中所有的商品分类
			3:将集合保存到request域中;
			4:转发到index.jsp页面
		 */
		//CategoryService cs = new CategoryServiceImpl();
		CategoryService cs = (CategoryService) MyBeanFactory.getBean("CategoryService");
		List<Category> li = cs.findAllCategory();
		request.setAttribute("clist",li);
		return "/jsp/index.jsp";
	}
	//2:异步查询的方法
	public void findAllCategoryByAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 *  1:参,无参数
			2:调,调用业务层查询数据库中所有的商品分类
			3:将集合转成json字符串,
			4:将json字符串响应给浏览器
		 */
		//CategoryService cs = new CategoryServiceImpl();
		CategoryService cs = (CategoryService) MyBeanFactory.getBean("CategoryService");
		//添加一个缓存技术,
		/*
		 * 思路:
		 * 	在这里,先从redis中查询,如果找不到,再从mysql数据库中查询
		 */
		//1:使用jedis工具类,获取一个jedis对象
		Jedis j = MyJedisUtils.getJedis();
		//2:尝试从redis数据库中获取数据
		String v = j.get("clist");
		if(v==null){
			List<Category> li = cs.findAllCategory();
			 v = MyJsonUtil.list2json(li);
			//为了保证下一次能从redis中找到数据,这里需要向redis存一份
			j.set("clist",v);
			System.out.println("从mysql中查询了一次.................");
		}
		response.getWriter().print(v);
		j.close();
	}
}
