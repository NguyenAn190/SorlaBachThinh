package com.assignment.controller.user.auth;

import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dang-xuat")
public class LogoutController {

    @GetMapping
    public String logout(HttpSession session) {
        session.removeAttribute(SessionAtrr.CURRENT_USER);
        return "redirect:/trang-chu";
    }
}
