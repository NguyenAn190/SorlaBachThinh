package com.assignment.interceptor;

import com.assignment.entity.Accounts;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Accounts admin = (Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN);
        String requestURI = request.getRequestURI();

        if (admin == null && requestURI.contains("/admin/")) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
