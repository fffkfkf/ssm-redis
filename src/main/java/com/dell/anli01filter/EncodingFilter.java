package com.dell.anli01filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编码过滤器
 */
public class EncodingFilter implements Filter{



    @Override
    public void destroy() {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //1.强转
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        //2:解决响应中文乱码问题
        response.setContentType("text/html;charset=utf-8");
        //3.放行,MyRequest类可以解决请求中文乱码问题
        chain.doFilter(new MyRequest(request), response);
    }
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }










}
