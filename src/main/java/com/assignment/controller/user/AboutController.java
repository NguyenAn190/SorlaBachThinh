package com.assignment.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

    @RequestMapping("gioi-thieu")
    public String showAbout() {
        return "views/user/page/about";
    }

    @RequestMapping("chinh-sach")
    public String showPolicy() {
        return "views/user/page/policy";
    }

    @RequestMapping("ban-tin")
    public String showNews() {
        return "views/user/page/news";
    }

    @RequestMapping("hoa-luoi-on-grid")
    public String showOnGrid() {
        return "views/user/page/on-grid";
    }

    @RequestMapping("hoa-luoi-hybrid")
    public String showHyBrid() {
        return "views/user/page/hybrid";
    }
}
