package com.dell.anli01filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 专门控制购物车和订单权限的过滤器
 */
public class QuanXian implements Filter {
@Override
	public void destroy() {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest)request;
		Object u = r.getSession().getAttribute("user");
		if(u==null){
			r.setAttribute("msg","亲,请选登录,后操作!!!");
			r.getRequestDispatcher("/jsp/info.jsp").forward(r, response);
			return;
		}
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
