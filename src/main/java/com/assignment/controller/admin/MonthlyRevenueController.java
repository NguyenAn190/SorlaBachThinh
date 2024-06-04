package com.assignment.controller.admin;

import com.assignment.dto.response.RevenueComparison;
import com.assignment.entity.Orders;
import com.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/doanhthu")
public class MonthlyRevenueController {

    @Autowired
    OrderService orderService;

    @GetMapping("theo-thang")
    public String showRevenue(Model model) {
        List<Integer> revenueData = chartRevenueForTheMonth();
        LocalDate now = LocalDate.now();

        if (now.getMonthValue() == 1) {
            model.addAttribute("monthNow", now.getMonthValue());
            model.addAttribute("monthLast", now.getMonthValue());
            model.addAttribute("locationSelectedMonth", generateMonthYearList().size());
            model.addAttribute("numberOfProductsInTheMonth", numberOfProductsInTheMonth(now.getMonthValue()));
        } else {
            model.addAttribute("monthNow", now.getMonthValue());
            model.addAttribute("monthLast", now.getMonthValue() - 1);
            model.addAttribute("locationSelectedMonth", generateMonthYearList().size() - 1);
        }

        model.addAttribute("numberOfProductsInTheMonth", numberOfProductsInTheMonth(now.getMonthValue()));
        model.addAttribute("revenueForTheMonth", revenueForTheMonth());
        model.addAttribute("chartRevenueForTheMonth", chartRevenueForTheMonth());
        model.addAttribute("percentageDecrease", compareRevenue(now.getMonthValue()));
        model.addAttribute("statisticsOfBestSellingProducts", statisticsOfBestSellingProducts(now.getMonthValue()));
        model.addAttribute("compareRevenueAmount", compareRevenueAmount(now.getMonthValue()));
        model.addAttribute("revenueData", revenueData);

        return "views/admin/page/views/doanh-thu-thang";
    }

    @GetMapping("theo-thang/{month}")
    public String showInRevenue(@PathVariable String month, Model model) {
        List<Integer> revenueData = chartRevenueForTheMonth();

        if (Integer.parseInt(month) == 1) {
            model.addAttribute("monthNow", month);
            model.addAttribute("monthLast", month);
            model.addAttribute("locationSelectedMonth", Integer.parseInt(month));
            model.addAttribute("numberOfProductsInTheMonth", numberOfProductsInTheMonth(Integer.parseInt(month)));
        } else {
            model.addAttribute("monthNow", month);
            model.addAttribute("monthLast", Integer.parseInt(month) - 1);
            model.addAttribute("locationSelectedMonth", Integer.parseInt(month) - 1);

        }
        model.addAttribute("numberOfProductsInTheMonth", numberOfProductsInTheMonth(Integer.parseInt(month)));
        model.addAttribute("revenueForTheMonth", revenueForTheMonthInput(Integer.parseInt(month)));
        model.addAttribute("chartRevenueForTheMonth", chartRevenueForTheMonth());
        model.addAttribute("percentageDecrease", compareRevenue(Integer.parseInt(month)));
        model.addAttribute("statisticsOfBestSellingProducts", statisticsOfBestSellingProducts(Integer.parseInt(month)));
        model.addAttribute("compareRevenueAmount", compareRevenueAmount(Integer.parseInt(month)));
        model.addAttribute("revenueData", revenueData);

        return "views/admin/page/views/doanh-thu-thang";
    }

    public int numberOfProductsInTheMonth(int month) {
        List<Orders> listOrder = orderService.findOrderByMonth(month);

        int numberOfProductsInTheMonth = 0;
        for (Orders order : listOrder) {
            if ("Thanh toán thành công".equals(order.getStatusPayment())) {
                numberOfProductsInTheMonth += order.getCartItem().getAmount();
            }
        }
        return numberOfProductsInTheMonth;
    }

    public List<Orders> statisticsOfBestSellingProducts(int month) {
        List<Orders> listOrder = orderService.findOrderByMonth(month);
        List<Orders> listProductBestSelling = new ArrayList<>();

        for (Orders order : listOrder) {
            if ("Thanh toán thành công".equals(order.getStatusPayment())) {
                listProductBestSelling.add(order);
            }
        }
        return listProductBestSelling;
    }

    public RevenueComparison compareRevenueAmount(int month) {
        RevenueComparison revenueComparison = new RevenueComparison();

        List<Orders> thisMonthOrder = orderService.findOrderByMonth(month);
        List<Orders> lastMonthOrder = orderService.findOrderByMonth(month - 1);

        double thisMonthOrderAmount = 0.0;
        double lastMonthOrderAmount = 0.0;

        if (!thisMonthOrder.isEmpty()) {
            for (Orders o1 : thisMonthOrder) {
                if ("Thanh toán thành công".equals(o1.getStatusPayment())) {
                    thisMonthOrderAmount += o1.getCartItem().getAmount();
                }
            }
        } else {
            thisMonthOrderAmount = 0.0;
        }

        if (!lastMonthOrder.isEmpty()) {
            for (Orders o2 : lastMonthOrder) {
                if ("Thanh toán thành công".equals(o2.getStatusPayment())) {
                    lastMonthOrderAmount += o2.getCartItem().getAmount();
                }
            }
        } else {
            lastMonthOrderAmount = 0.0;
        }


        if (thisMonthOrderAmount > lastMonthOrderAmount) {
            if (lastMonthOrderAmount == 0.0) {
                double percentageChange = ((thisMonthOrderAmount - lastMonthOrderAmount));
                revenueComparison.setStatus("Tăng");
                revenueComparison.setPercentage(percentageChange);
                return revenueComparison;
            } else {
                double percentageChange = ((thisMonthOrderAmount - lastMonthOrderAmount) / lastMonthOrderAmount) * 100;
                revenueComparison.setStatus("Tăng");
                revenueComparison.setPercentage(percentageChange);
                return revenueComparison;
            }

        } else if (thisMonthOrderAmount < lastMonthOrderAmount) {
            double percentageDecrease = ((lastMonthOrderAmount - thisMonthOrderAmount) / lastMonthOrderAmount) * 100;
            revenueComparison.setStatus("Giảm");
            revenueComparison.setPercentage(percentageDecrease);

            return revenueComparison;
        } else {
            revenueComparison.setStatus("Không thay đổi");
            revenueComparison.setPercentage(0.0);

            return revenueComparison;
        }
    }

    public RevenueComparison compareRevenue(int month) {
        RevenueComparison revenueComparison = new RevenueComparison();

        List<Orders> thisMonthOrder = orderService.findOrderByMonth(month);
        List<Orders> lastMonthOrder = orderService.findOrderByMonth(month - 1);

        double thisMonthOrderAmount = 0.0;
        double lastMonthOrderAmount = 0.0;

        if (!thisMonthOrder.isEmpty()) {
            for (Orders o1 : thisMonthOrder) {
                if ("Thanh toán thành công".equals(o1.getStatusPayment())) {
                    thisMonthOrderAmount += o1.getTotalAmount();
                }
            }
        } else {
            thisMonthOrderAmount = 0.0;
        }

        if (!lastMonthOrder.isEmpty()) {
            for (Orders o2 : lastMonthOrder) {
                if ("Thanh toán thành công".equals(o2.getStatusPayment())) {
                    lastMonthOrderAmount += o2.getTotalAmount();
                }
            }
        } else {
            lastMonthOrderAmount = 0.0;
        }


        if (thisMonthOrderAmount > lastMonthOrderAmount) {
            if (lastMonthOrderAmount == 0.0) {
                double percentageChange = ((thisMonthOrderAmount - lastMonthOrderAmount));
                revenueComparison.setStatus("Tăng");
                revenueComparison.setPercentage(percentageChange);
                return revenueComparison;
            } else {
                double percentageChange = ((thisMonthOrderAmount - lastMonthOrderAmount) / lastMonthOrderAmount) * 100;
                revenueComparison.setStatus("Tăng");
                revenueComparison.setPercentage(percentageChange);
                return revenueComparison;
            }

        } else if (thisMonthOrderAmount < lastMonthOrderAmount) {
            double percentageDecrease = ((lastMonthOrderAmount - thisMonthOrderAmount) / lastMonthOrderAmount) * 100;
            revenueComparison.setStatus("Giảm");
            revenueComparison.setPercentage(percentageDecrease);

            return revenueComparison;
        } else {
            revenueComparison.setStatus("Không thay đổi");
            revenueComparison.setPercentage(0.0);

            return revenueComparison;
        }
    }

    public static int getPreviousMonth() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int previousMonth = (currentMonth - 2 + 12) % 12 + 1;
        return previousMonth;
    }

    List<Integer> chartRevenueForTheMonth() {
        List<Integer> listAmountByMonth = new ArrayList<>();
        List<Integer> monthNumbers = getMonthIntegers();

        for (Integer monthNumber : monthNumbers) {
            List<Orders> orders = orderService.findOrderByMonth(monthNumber);
            if (orders == null || orders.isEmpty()) {
                listAmountByMonth.add(0);
            } else {
                int totalAmount = 0;
                for (Orders o : orders) {
                    if ("Thanh toán thành công".equals(o.getStatusPayment())) {
                        totalAmount += o.getTotalAmount();
                    }
                }
                listAmountByMonth.add(totalAmount);
            }
        }
        return listAmountByMonth;
    }

    public List<Integer> getMonthIntegers() {
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        return months;
    }

    public static List<Integer> getMonthsOfCurrentYear() {
        List<Integer> monthList = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        for (int i = 1; i <= 12; i++) {
            LocalDate date = LocalDate.of(currentYear, i, 1);
            if (date.getYear() == currentYear) {
                monthList.add(i);
            }
        }

        return monthList;
    }

    public List<Integer> getMonthNumbers(List<String> monthList) {
        List<Integer> monthNumbers = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Tháng' M - yyyy");

        for (String monthString : monthList) {
            LocalDate localDate = LocalDate.parse(monthString, formatter);
            int monthNumber = localDate.getMonthValue();
            monthNumbers.add(monthNumber);
        }

        return monthNumbers;
    }

    Double revenueForTheMonthInput(int month) {
        double totalAmount = 0.0;

        for (Orders order : orderService.findOrderByMonth(month))
            if ("Thanh toán thành công".equals(order.getStatusPayment())) {
                totalAmount += order.getTotalAmount();
            }
        return totalAmount;
    }

    Double revenueForTheMonth() {
        double totalAmount = 0.0;
        for (Orders order : orderService.findByMonth()) {
            if ("Thanh toán thành công".equals(order.getStatusPayment())) {
                totalAmount += order.getTotalAmount();
            }
        }
        return totalAmount;
    }

    public LocalDateTime getNow() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    @ModelAttribute("listMonthValue")
    public List<String> generateMonthYearList() {
        List<String> monthList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Tháng' M - yyyy");

        for (int i = 1; i <= currentDate.getMonthValue(); i++) {
            String monthString = currentDate.withMonth(i).format(formatter);
            monthList.add(monthString);
        }

        return monthList;
    }

}
