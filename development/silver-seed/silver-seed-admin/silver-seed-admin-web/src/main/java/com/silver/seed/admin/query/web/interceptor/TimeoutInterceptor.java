package com.silver.seed.admin.query.web.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Liaojian on 2015/12/2.
 */
public class TimeoutInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("silver_user");
        System.out.println("->silver_user:" + user);
        if(user == null) {
            request.getSession().setAttribute("silver_user", "admin");
            //request.getRequestDispatcher("/query/display-with-jsp.jsp").forward(request, response);
            return false;
        }
        return true;
    }

}
