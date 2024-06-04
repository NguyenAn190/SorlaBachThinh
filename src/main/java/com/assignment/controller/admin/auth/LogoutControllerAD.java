package com.assignment.controller.admin.auth;

import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logout")
public class LogoutControllerAD {

    @GetMapping
    public String logout(HttpSession session) {
        session.removeAttribute(SessionAtrr.CURRENT_ADMIN);
        return "redirect:/admin";
    }
}
