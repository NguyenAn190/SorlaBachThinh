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
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Accounts user = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        String uri = request.getRequestURI();

        if (user == null && checkURIUser(uri)) {
            response.sendRedirect("/dang-nhap");
            return false;
        }
        if (user != null && checkURIExits(uri)) {
            response.sendRedirect("/trang-chu");
            return false;
        }
        return true;
    }

    private boolean checkURIExits(String uri) {
        return uri.contains("/dang-nhap") || uri.contains("/dang-ky") || uri.contains("/quen-mat-khau");
    }


    private boolean checkURIUser(String uri) {
        return uri.contains("gio-hang") ||
                uri.contains("thong-tin") ||
                uri.contains("thong-tin/**") ||
                uri.contains("doi-mat-khau") ||
                uri.contains("dang-xuat") ||
                uri.contains("dang-ky/xac-thuc-email") ||
                uri.contains("lich-su-mua-hang") ||
                uri.contains("thanh-toan") ||
                uri.contains("san-pham/them-vao-gio-hang");
    }
}
