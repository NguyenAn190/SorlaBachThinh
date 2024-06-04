package com.assignment.controller.admin;

import com.assignment.entity.Orders;
import com.assignment.service.RevenueService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/doanhthu")
public class ProcessController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("theo-nam")
    public String doanhThuNam(@RequestParam(name = "year", defaultValue = "2023") int year, Model model) {
        BigDecimal revenue = revenueService.calculateRevenueForYear(year);
        model.addAttribute("year", year);
        model.addAttribute("revenue", revenue);

        // Bước 1: Truy xuất dữ liệu từ bảng Orders dựa trên năm
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
        model.addAttribute("topSellingProducts", revenueService.findTopSellingProducts(year));


        return "views/admin/page/views/doanh-thu-nam";
    }

    @GetMapping("load-bieu-do")
    public ResponseEntity<Map<String, Object>> getRevenueByYear(@RequestParam("year") int year, Model model) {
        Map<String, Object> response = new HashMap<>();
        BigDecimal revenue = revenueService.calculateRevenueForYear(year);
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

        Gson gson = new Gson();
        String profitDataJson = gson.toJson(profitData);
        model.addAttribute("profitData", profitDataJson);

        response.put("revenue", revenue);
        response.put("profitData", profitData);
        response.put("topSellingProducts", revenueService.findTopSellingProducts(year));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ModelAttribute("selectYear")
    public List<Integer> filterOrdersByUniqueYear() {
        return revenueService.findDistinctOrdersByYear();
    }

}
