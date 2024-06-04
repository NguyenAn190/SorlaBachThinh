package com.assignment.controller.admin;

import com.assignment.entity.Accounts;
import com.assignment.entity.Orders;
import com.assignment.service.AccountService;
import com.assignment.service.OrderService;
import com.assignment.service.RevenueService;
import com.assignment.utils.ParamService;
import com.assignment.utils.SessionAtrr;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class DashboardControllerAD {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    RevenueService revenueService;

    @Autowired
    ParamService paramService;

    @Autowired
    HttpSession session;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("dashboard")
    public String showDashBoard(@RequestParam(name = "year", defaultValue = "2023") int year, Model model) {
        Pageable pageable = PageRequest.of(0, 5);

        model.addAttribute("orders", orderService.findLimit5OrdersList(pageable));

        BigDecimal revenue = revenueService.calculateRevenueForYear(year);
        model.addAttribute("year", year);
        model.addAttribute("revenue", revenue);

        List<Orders> ordersInYear = revenueService.getOrdersByCreatedAt_Year(year); // year là năm bạn muốn lấy dữ liệu

        // Bước 2: Tính lợi nhuận của từng tháng trong năm
        Map<Integer, BigDecimal> monthlyProfitMap = new HashMap<>();
        for (Orders order : ordersInYear) {
            LocalDateTime createdAt = order.getCreatedAt().toLocalDateTime();
            int month = createdAt.getMonthValue();
            BigDecimal totalAmount = BigDecimal.valueOf(order.getTotalAmount());

            if (monthlyProfitMap.containsKey(month)) {
                monthlyProfitMap.put(month, monthlyProfitMap.get(month).add(totalAmount));
            } else {
                monthlyProfitMap.put(month, totalAmount);
            }
        }

        // Bước 3: Tạo mảng lợi nhuận của từng tháng
        List<BigDecimal> profitData = new ArrayList<>();
        for (int month = 1; month <= 12; month++) { // Duyệt từ tháng 1 đến tháng 12
            BigDecimal profit = monthlyProfitMap.getOrDefault(month, BigDecimal.ZERO);
            profitData.add(profit);
        }

        // Bước 4: Đưa mảng lợi nhuận vào mã JavaScript
        Gson gson = new Gson();
        String profitDataJson = gson.toJson(profitData);
        model.addAttribute("profitData", profitDataJson);

        //doanh thu trung bình trong các năm
        model.addAttribute("AverageRevenueByYear", revenueService.calculateAverageRevenue());
        return "views/admin/page/views/dashboard";
    }

    @GetMapping("load-bieu-do")
    public ResponseEntity<Map<String, Object>> getRevenueByYear(@RequestParam("year") int year, Model model) {
        Map<String, Object> response = new HashMap<>();
        List<Orders> ordersInYear = revenueService.getOrdersByCreatedAt_Year(year);

        Map<Integer, BigDecimal> monthlyProfitMap = new HashMap<>();
        for (Orders order : ordersInYear) {
            LocalDateTime createdAt = order.getCreatedAt().toLocalDateTime();
            int month = createdAt.getMonthValue();
            BigDecimal totalAmount = BigDecimal.valueOf(order.getTotalAmount());

            if (monthlyProfitMap.containsKey(month)) {
                monthlyProfitMap.put(month, monthlyProfitMap.get(month).add(totalAmount));
            } else {
                monthlyProfitMap.put(month, totalAmount);
            }
        }

        // Bước 3: Tạo mảng lợi nhuận của từng tháng
        List<BigDecimal> profitData = new ArrayList<>();
        for (int month = 1; month <= 12; month++) { // Duyệt từ tháng 1 đến tháng 12
            BigDecimal profit = monthlyProfitMap.getOrDefault(month, BigDecimal.ZERO);
            profitData.add(profit);
        }

        response.put("profitData", profitData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("doi-mat-khau-admin/{accountId}")
    public ResponseEntity<Map<String, Object>> changePassword(@PathVariable long accountId) {
        Map<String, Object> responsesMap = new HashMap<>();
        Accounts accounts = accountService.findById(accountId);

        responsesMap.put("accounts", accounts);
        return ResponseEntity.ok(responsesMap);
    }

    @PostMapping("doi-mat-khau-admin")
    public String submitChangePass() {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN);

        String pass0 = paramService.getString("password0", "");
        String pass1 = paramService.getString("password1", "");

        if (accounts == null) {
            session.setAttribute("toastFailed", "Đổi mật khẩu thất bại!");

        } else if (!encoder.matches(pass0, accounts.getPasswords())) {
            session.setAttribute("toastFailed", "Mật khẩu cũ không khớp !");

        } else if (pass0.equals(pass1)) {
            session.setAttribute("toastFailed", "Mật khẩu mới không được trùng !");

        } else {
            String passwordEncore = encoder.encode(pass1);
            accountService.updatePass(accounts.getEmail(), passwordEncore);

            session.setAttribute("toastSuccess", "Đổi mật khẩu thành công !");
            session.removeAttribute(SessionAtrr.CURRENT_USER);
            return "redirect:/admin";
        }
        return "redirect:/admin/dashboard";
    }

    @ModelAttribute("selectYear")
    public List<Integer> filterOrdersByUniqueYear() {
        return revenueService.findDistinctOrdersByYear();
    }
}
