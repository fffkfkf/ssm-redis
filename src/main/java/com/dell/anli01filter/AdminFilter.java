package com.dell.anli01filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *	管理员专用的权限过滤器
 */
public class AdminFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//1:从session中获取admin,如果能找到,则放行,否则不放行
		HttpServletRequest req = (HttpServletRequest)request;
		String p = req.getParameter("m");
		if("login".equals(p)){
			chain.doFilter(request, response);
			return;
		}
		Object o = req.getSession().getAttribute("admin");
		if(o==null){
			//说明管理员没有登录,不能放行
			req.setAttribute("msg","亲,您不是管理员,不能乱来!!!");
			req.getRequestDispatcher("/jsp/info.jsp").forward(req, response);
			return;
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
