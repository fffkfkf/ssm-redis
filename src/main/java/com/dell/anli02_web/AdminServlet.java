package com.dell.anli02_web;


import com.dell.anli03_service.AdminService;
import com.dell.anli07_domain.Product2;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyBaseServlet;
import com.dell.anli08_util.MyBeanFactory;
import com.dell.anli08_util.Pager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员的servlet
 */
public class AdminServlet extends MyBaseServlet {
    //1:管理员登录的方法
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 参  调   存  转
         */
        String un = request.getParameter("username");
        String ps = request.getParameter("password");
        AdminService as = (AdminService) MyBeanFactory.getBean("AdminService");
        User u = as.login(un, ps);
        if (u == null) {
            request.setAttribute("msg", "管理员登录失败!");
            return "/adminLogin.jsp";
        }
        request.getSession().setAttribute("admin", u);
        return "/admin/myHome.jsp";
    }

    //2:删除一个分类信息的方法
    public void delCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 参  调  响应
         */
        String cid = request.getParameter("cid");
        AdminService as = (AdminService) MyBeanFactory.getBean("AdminService");
        String msg = as.delCategoryByCid(cid);
        response.getWriter().print(msg);
    }

    //2:添加一个分类信息的方法
    public void saveCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 参  调  响应
         */
        String cname = request.getParameter("cname");
        AdminService as = (AdminService) MyBeanFactory.getBean("AdminService");
        as.saveCategory(cname);
        response.getWriter().print("添加成功!");
    }

    //3:根据分类编号修改分类信息的方法
    public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 参  调  响应
         */
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        AdminService as = (AdminService) MyBeanFactory.getBean("AdminService");
        as.updateCategory(cid, cname);
        response.getWriter().print("修改成功!");
    }

    //4:根据分页,查询商品列表信息
    public void findProductByPageNumberAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 1:参   调   响应
         *
         * 1:获取当前页和每页显示的数量
         * 2:调用业务层,获取一个分页对象;
         * 3:将分页对象转成一个json字符串;
         * 4:将字符串响应给页面
         */
        //1:获取当前页和每页显示的数量
		/*Map<String, String[]> map = request.getParameterMap();
		Set<String> set = map.keySet();
		for (String k : set) {
			System.out.println(k+"====>"+Arrays.toString(map.get(k)));
		}*/
        String pageNumber = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        //2:调用业务层,获取一个分页对象;
        AdminService as = (AdminService) MyBeanFactory.getBean("AdminService");
        Pager<Product2> pager = as.findProductByPageNumberAjax(pageNumber, pageSize);
        //3:将分页对象转成一个json字符串;
        //注释了需要解开
        //String json = JSONObject.fromObject(pager).toString();//????????---------------
        String json="";

        //4:将字符串响应给页面
        response.getWriter().print(json);
    }
}
