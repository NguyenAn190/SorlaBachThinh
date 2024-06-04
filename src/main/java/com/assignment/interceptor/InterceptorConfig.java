package com.assignment.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Autowired
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/*")
                .addPathPatterns("/admin/sua-danh-muc/*", "/admin/xoa-danh-muc/*")
                .addPathPatterns("/admin/sua-the-loai/*", "/admin/xoa-the-loai/*")
                .addPathPatterns("/admin/chinh-sua-thuong-hieu/**")
                .addPathPatterns("/admin/them-hinh-anh/**")
                .addPathPatterns("/admin/sua-san-pham/**", "/admin/them-san-pham/*")
                .addPathPatterns("/admin/sua-ma/*", "/admin/them-ma/*")
                .addPathPatterns("/admin/sua-tai-khoan/*", "/admin/xoa-tai-khoan/*")
                .addPathPatterns("/admin/sua-khach-hang/*", "/admin/xoa-khach-hang/*")
                .addPathPatterns("/admin/doanhthu/*")
                .excludePathPatterns("/static/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/*")
                .addPathPatterns("/thong-tin/**")
                .addPathPatterns("/gio-hang/**")
                .addPathPatterns("/thanh-toan/**")
                .excludePathPatterns("/static/**");
    }
}
