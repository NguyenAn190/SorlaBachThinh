package com.assignment.controller.admin;

import com.assignment.entity.CartItem;
import com.assignment.entity.Orders;
import com.assignment.entity.Products;
import com.assignment.service.CartItemService;
import com.assignment.service.OrderService;
import com.assignment.service.ProductsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class ComfimOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductsService productsService;

    @Autowired
    HttpSession session;

    @GetMapping("xac-nhan-don-hang")
    public String confirm_order(Model model) {
        List<Orders> orders = orderService.findOrderByStatus("Chờ xác nhận");
        model.addAttribute("orders", orders);
        return "views/admin/page/views/confirm-order-list";
    }

    @GetMapping("don-da-van-chuyen")
    public String showOrderShip(Model model) {
        List<Orders> orders = orderService.findOrderByStatusOrder("Đang vận chuyển");
        model.addAttribute("orders", orders);
        return "views/admin/page/views/order-ship-list";
    }

    @GetMapping("don-da-huy")
    public String showOrderClose(Model model) {
        List<Orders> orders = orderService.findOrderByStatus("Đã huỷ đơn");
        model.addAttribute("orders", orders);
        return "views/admin/page/views/order-close-list";
    }

    @GetMapping("huy-don-hang/{orderId}")
    public String closeOrder(@PathVariable long orderId) {
        Orders orders = orderService.findByOrderId(orderId);
        orders.setStatusPayment("Đã huỷ đơn");
        orders.setStatusOrder("Ngưng vận chuyển");
        orderService.save(orders);

        session.setAttribute("toastSuccess", "Huỷ đơn hàng thành công !");
        return "redirect:/admin/xac-nhan-don-hang";
    }

    @PostMapping("xac-nhan")
    public String changeStatusOrder(@RequestParam("orderId") long orderId) {
        Orders orders = orderService.findByOrderId(orderId);
        orders.setStatusPayment("Đã xác nhận");
        orders.setStatusOrder("Đang vận chuyển");
        orderService.save(orders);

        session.setAttribute("toastSuccess", "Xác nhận đơn thành công !");
        return "redirect:/admin/xac-nhan-don-hang";
    }

    @GetMapping("xac-nhan-thong-tin/{orderId}")
    @ResponseBody
    public ResponseEntity<?> getOrderInfo(@PathVariable long orderId) {
        Orders orders = orderService.findByOrderId(orderId);
        List<Products> products = productsService.findByInvoiceCartItemId(orders.getOrderId());
        List<CartItem> cartItems = cartItemService.findByInvoiceCartItemId(orders.getOrderId());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("orders", orders);
        responseData.put("products", products);
        responseData.put("cartItems", cartItems);

        return ResponseEntity.ok(responseData);
    }
}
