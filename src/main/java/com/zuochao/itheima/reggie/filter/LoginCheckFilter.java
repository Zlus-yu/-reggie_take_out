package com.zuochao.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.zuochao.itheima.reggie.common.BaseContext;
import com.zuochao.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zuo Chao
 * @date 2022/9/3 20:34
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取请求url
        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}",requestURI);


        //定义不需要拦截的URL
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2.判断本次请求是否需要处理
        boolean check = checkURL(urls, requestURI);

        //3.若不需要处理，直接放行
        if(check){
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4-1.判断登录状态
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("employee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }

        //4-2.判断登录状态
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }


        log.info("用户未登录");
        //5.如果未登录，通过输出流的方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }


    /**
     * 匹配路径
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean checkURL(String[] urls, String requestURI){
        for (String url:urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }

}
