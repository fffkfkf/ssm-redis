package com.dell.anli08_util;


import com.dell.anli01filter.MyQX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/*
 * 这就是抽取的BaseServlet,以后,自定义的其他servlet,直接继承该servlet即可
 */
@SuppressWarnings("all")
public class MyBaseServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//1:要反射获取正在执行的servlet对象;  this代表的就是正在执行的servlet,(该MyBaseServlet的其中一个孩子)
		Class c1 = this.getClass();
		//2:从浏览器获取想要执行的方法
		String m = req.getParameter("m");
		//3:根据方法名,反射方法
		try {
			Method method = c1.getMethod(m,HttpServletRequest.class,HttpServletResponse.class );
			//---------------------判断method这个方法是否需要权限-------------------------------------
			boolean b = method.isAnnotationPresent(MyQX.class);
			if(b){
				MyQX qx = method.getAnnotation(MyQX.class);
				String val = qx.value();
				//说明要权限
				Object u = req.getSession().getAttribute(val);
				if(u==null){
					//说明权限不足
					req.setAttribute("msg","亲,先登录,后操作哈!");
					req.getRequestDispatcher("/jsp/info.jsp").forward(req, res);
					return;
				}
			}
			//---------------------判断method这个方法是否需要权限-------------------------------------
			
			//4:让method执行
			Object invoke = method.invoke(this,req,res);
			//5:判断invoke的值是否为null,如果不为null,就转发,
			if(invoke!=null){
				req.getRequestDispatcher((String)invoke).forward(req, res);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}
}
