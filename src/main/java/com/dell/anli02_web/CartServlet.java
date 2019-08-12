package com.dell.anli02_web;

import com.dell.anli01filter.MyQX;
import com.dell.anli03_service.ProductService;
import com.dell.anli04_serviceimple.ProductServiceImpl;
import com.dell.anli07_domain.Cart;
import com.dell.anli07_domain.Product;
import com.dell.anli08_util.MyBaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 购物车模块的servlet
 */
public class CartServlet extends MyBaseServlet {
	//1:添加商品到购物车中
	@MyQX
	public String addProductToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1.1:参:获取商品编号和商品数量;
			1.2:根据商品编号查询商品对象;
			2.1:调,从session中获取一个购物车对象;
			如果购物车对象为空,则需要创建一个新的购物车对象,否则直接使用已经存在的购物车对象;
			2.2:面向购物车对象,将商品对象和商品数量传递给购物车对象的添加商品到购物车中的方法;
			3:将购物车对象保存到session中;
			4:转发到购物车详情页面;
		 */
		//1.1:参:获取商品编号和商品数量;
		String pid = request.getParameter("pid");
		String count = request.getParameter("count");
		//1.2:根据商品编号查询商品对象;
		ProductService ps = (ProductService) new ProductServiceImpl();
		Product p = ps.findProductByPid(pid);
		//2.1:调,从session中获取一个购物车对象;
		HttpSession se = request.getSession();
		Cart cart = (Cart) se.getAttribute("cart");
		if(cart==null){
			//仅仅是为了保证让购物车对象一定存在!!!
			cart=new Cart();
		}
		//2.2:面向购物车对象,将商品对象和商品数量传递给购物车对象的添加商品到购物车中的方法;
		cart.addProductToCart(p, Integer.parseInt(count));
		//3:将购物车对象保存到session中;
		se.setAttribute("cart",cart);
		//4:转发到购物车详情页面;
		return "/jsp/cart.jsp";
	}
	//2:从购物车中移除一个购物项的方法
	@MyQX
	public String delProductFromCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1:获取pid
		String pid = request.getParameter("pid");
		//2:获取购物车对象,并判断是否为空
		HttpSession se = request.getSession();
		Cart cart = (Cart) se.getAttribute("cart");
		if(cart==null){
			//说明session中的购物车已经超时了!!!
			request.setAttribute("msg","亲,购物车已失效,请刷新购物车重试!");
			return "/jsp/info.jsp";
		}
		//3:面向购物车对象,移除一个购物项
		cart.removeProductFromCart(pid);
		//4:更新session中的购物车对象
		se.setAttribute("cart",cart);
		//5"转发到购物车详情页面
		return "/jsp/cart.jsp";
	}
	//2:从购物车中移除一个购物项的方法
	@MyQX
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1:直接从session中移除购物车对象
		request.getSession().removeAttribute("cart");
		//2:转发到购物车详情页面
		return "/jsp/cart.jsp";
	}

}
