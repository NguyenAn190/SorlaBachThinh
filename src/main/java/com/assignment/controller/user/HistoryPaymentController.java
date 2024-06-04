package com.assignment.controller.user;

import com.assignment.entity.Accounts;
import com.assignment.entity.Orders;
import com.assignment.service.OrderService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("lich-su-mua-hang")
public class HistoryPaymentController {

    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession session;

    @GetMapping
    public String showListPayment(Model model, @RequestParam("page") Optional<Integer> page) {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<Orders> orderPage = orderService.findByAccountId(accounts.getAccountId(), pageable);

        List<Orders> orders = orderPage.getContent();

        int totalPages = orderPage.getTotalPages();
        int currentPage = orderPage.getNumber();

        int maxPagesToShow = 3;

        int startPage = Math.max(0, currentPage - maxPagesToShow / 2);
        int endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

        model.addAttribute("account", accounts);
        model.addAttribute("orders", orders);
        model.addAttribute("page", orderPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "views/user/page/history-payment";
    }
}
