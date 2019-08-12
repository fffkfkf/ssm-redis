package com.dell.anli02_web;

import com.dell.anli01filter.MyQX;
import com.dell.anli03_service.OrderService;
import com.dell.anli07_domain.*;
import com.dell.anli08_util.*;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *订单模块的servlet
 */
public class OrderServlet extends MyBaseServlet {
	//1:创建订单的方法;
	@MyQX
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,从session中获取购物车对象;
			2:将购物车对象转成订单对象,将每一个购物项对象转成订单项对象;
			3:调用业务层,将订单和订单项分别保存到数据库中;
			4:存,将订单对象和订单项对象保存到request域中;
			5:清空购物车
			6:转发到订单详情页面,展示订单信息;
		 */
		//1:参,从session中获取购物车对象;
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//2:判断购物车是否有效
		if(cart==null||cart.getTotalMoney()==0){
			request.setAttribute("msg","购物车已失效,请重新添加购物车!!!");
			return "/jsp/info.jsp";
		}
		//3:将购物车对象转成订单对象,将每一个购物项对象转成订单项对象;
		Orders o = new Orders();
		o.setOid(UUIDUtils.getId());
		//4:设置购物车相关信息
		o.setOrdertime(new Date());
		o.setTotal(cart.getTotalMoney());
		User u = (User) request.getSession().getAttribute("user");
		o.setUid(u.getUid());
		Collection<CartItem> list = cart.getCartItemList();
		List<OrderItem> oli = new ArrayList<OrderItem>();
		for (CartItem cartItem : list) {
			//将每一个cartItem都转成一个对应的orderItem
			OrderItem oi = new OrderItem();
			oi.setItemid(UUIDUtils.getId());
			oi.setCount(cartItem.getCount());
			oi.setOid(o.getOid());
			oi.setPid(cartItem.getProduct().getPid());
			oi.setProduct(cartItem.getProduct());
			oi.setSubtotal(cartItem.getSubMoney());
			oli.add(oi);
		}
		o.setItems(oli);
		//3:调用业务层,将订单和订单项分别保存到数据库中;
		//OrderService os = new OrderServiceImpl();
		OrderService os = (OrderService) MyBeanFactory.getBean("OrderService");
		os.createOrder(o);
		//4:存,将订单对象和订单项对象保存到request域中;
		request.setAttribute("o",o);
		//5:清空购物车
		request.getSession().removeAttribute("cart");
		//6:转发到订单详情页面,展示订单信息;
		return "/WEB-INF/order_info.jsp";
	}
	//2:订单列表查询的方法;
	@MyQX
	public String myOrderListByPagenumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 		1:参,获取当前页码值从session对象中获取当前用户对象;
				2:调,调用业务层,传递当前页和用户对象得到一个分页对象
				3:存,将分页对象保存到request域中;
				4:转,转发到order_list.jsp页面
		 */
		//1:参,获取当前页码值从session对象中获取当前用户对象;
		String pn = request.getParameter("pageNumber");
		User u = (User)request.getSession().getAttribute("user");
		//2:调,调用业务层,传递当前页和用户对象得到一个分页对象
		OrderService os = (OrderService) MyBeanFactory.getBean("OrderService");
		PageBean<Orders> pb=os.findMyOrderList(u,pn);
		//3:存,将分页对象保存到request域中;
		request.setAttribute("pb",pb);
		//4:转,转发到order_list.jsp页面
		return "/WEB-INF/order_list.jsp";
	}
	//3:订单详情查询的方法;
	@MyQX
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 *1:参,获取oid
		 *2:调,根据oid获取订单对象;(包含订单项集合)
		 *3:存,存到request域中
		 *4:转,转发到order_info.jsp页面
		 */
		//1:参,获取oid
		String oid = request.getParameter("oid");
		OrderService os = (OrderService) MyBeanFactory.getBean("OrderService");
		//2:调,根据oid获取订单对象;(包含订单项集合)
		Orders o = os.findOrderByOid(oid);
		//3:存,存到request域中
		request.setAttribute("o", o);
		//4:转,转发到order_info.jsp页面
		//                order_info.jsp
		return "/WEB-INF/order_info.jsp";
	}
	//4:确认订单的方法;  更新订单的收货人信息即可
	@MyQX
	public void  confirmOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:参,获取收货人相关信息;
		2:调,调用业务层更新订单的收货人信息
		3:存,不存;
		4:转,需要重定向到用户想去的银行页面
		 */
		Map<String, String[]> map = request.getParameterMap();
		Orders o = new Orders();
		BeanUtils.populate(o, map);
		OrderService os = (OrderService) MyBeanFactory.getBean("OrderService");
		os.updateOrderInfo(o);
		//4:转,需要重定向到用户想去的银行页面
		//重定向的时候,需要按照易宝的要求,携带相应的参数即可
		// 组织发送支付公司需要哪些数据
				String pd_FrpId = request.getParameter("pd_FrpId");
				String p0_Cmd = "Buy";
				String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order = o.getOid();
				String p3_Amt = "0.01";
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
				// 第三方支付可以访问网址
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// 加密hmac 需要密钥
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
						p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
						pd_FrpId, pr_NeedResponse, keyValue);
			
				
				//发送给第三方
				StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
				sb.append("p0_Cmd=").append(p0_Cmd).append("&");
				sb.append("p1_MerId=").append(p1_MerId).append("&");
				sb.append("p2_Order=").append(p2_Order).append("&");
				sb.append("p3_Amt=").append(p3_Amt).append("&");
				sb.append("p4_Cur=").append(p4_Cur).append("&");
				sb.append("p5_Pid=").append(p5_Pid).append("&");
				sb.append("p6_Pcat=").append(p6_Pcat).append("&");
				sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
				sb.append("p8_Url=").append(p8_Url).append("&");
				sb.append("p9_SAF=").append(p9_SAF).append("&");
				sb.append("pa_MP=").append(pa_MP).append("&");
				sb.append("pd_FrpId=").append(pd_FrpId).append("&");
				sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
				sb.append("hmac=").append(hmac);
				
				response.sendRedirect(sb.toString());
	}
	//5:专门用于等待用户支付完成之后,回调的方法
	@MyQX
	public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 1:专门写一个等待
			用户支付完成后回调的方法;
			2:获取用户支付状态及支付的订单号
			3:根据订单号修改数据库中订单的支付状态;
			4:转发,转发到信息提示页面,提示支付结果
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效,判断支付结果是否成功了
			if (r1_Code.equals("1")) {
				//成功了
				//修改订单状态
				OrderService s=(OrderService) MyBeanFactory.getBean("OrderService");
				s.updateOrderState(r6_Order,1);
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
			} else {
				//响应数据有效,但支付结果是失败的
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"支付失败了,请重试~~");
			}
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
			request.setAttribute("msg", "支付无效!!!数据被篡改！");
		}
		return "/jsp/info.jsp";
	}
}
