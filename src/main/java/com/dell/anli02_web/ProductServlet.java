package com.dell.anli02_web;

import com.dell.anli03_service.ProductService;
import com.dell.anli04_serviceimple.ProductServiceImpl;
import com.dell.anli07_domain.Product;
import com.dell.anli08_util.MyBaseServlet;
import com.dell.anli08_util.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *商品模块相关的servlet
 */
public class ProductServlet extends MyBaseServlet {
	//1:查询最新商品和热门商品的方法
	public String findHotAndNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//转,转发到index.jsp,.展示最新商品和热门商品
		/*
		 * 1:调用业务层,获取9个热门的和最新的商品集合
		 * 2:分别将2个集合保存到request域中
		 * 3:转发到index.jsp展示商品信息
		 */
		ProductService ps = (ProductService) new ProductServiceImpl();
		List<Product> hots = ps.findHots9();
		List<Product> news = ps.findNews9();
		request.setAttribute("hots",hots);
		request.setAttribute("news",news);
		System.out.println("hots:"+hots);
		System.out.println("news:"+news);
		return "/WEB-INF/index.jsp";
	}
	//2:查询商品详情的方法
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,获取编号
		2:调用业务层,根据编号,查询商品对象;
		3:将对象保存到request域中
		4:转发到商品详情页面
		 */
		String pid = request.getParameter("pid");
		ProductService ps = (ProductService) new ProductServiceImpl();
		Product p=ps.findProductByPid(pid);
		request.setAttribute("p",p);
		return "/WEB-INF/product_info.jsp";
	}
	//3:分页查询商品列表的方法
	public String findProductListByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,获取分类id,当前页;
			2:调,调用业务层,获取一个分页对象;
			3:将分页对象保存到request域中
			4:转,转发到商品列表页面;
		 */
		String pn = request.getParameter("pageNumber");
		String cid = request.getParameter("cid");
		ProductService ps = (ProductService) new ProductServiceImpl();
		PageBean<Product> pb=ps.findProductListByPage(pn,cid);
		request.setAttribute("pb",pb);
		return "/WEB-INF/product_list.jsp";
	}

}
