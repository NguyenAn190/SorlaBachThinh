package com.assignment.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("lien-he")
public class ContactController {

    @GetMapping
    public String doGet() {
        return "views/user/page/contact";
    }
}
