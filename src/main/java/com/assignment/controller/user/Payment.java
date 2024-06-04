package com.assignment.controller.user;

import com.assignment.controller.vnpay.VNPayService;
import com.assignment.dto.CustomerDTO;
import com.assignment.dto.PaymentDTO;
import com.assignment.entity.*;
import com.assignment.service.*;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.RandomUtils;
import com.assignment.utils.ReplaceUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("thanh-toan")
public class Payment {

    @Autowired
    VNPayService vnPayService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductsService productsService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceDetailsService invoiceDetailsService;

    @Autowired
    EmailService emailService;

    @Autowired
    HttpSession session;

    @PostMapping("huy-thanh-toan")
    public String closePayment() {
        session.setAttribute("toastFailed", "Thanh toán thất bại !");
        return "redirect:/gio-hang";
    }

    @PostMapping("submit")
    public String submidOrder(@RequestParam("amount") String orderTotal, @RequestParam("orderInfo") String orderInfo, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(ReplaceUtils.replacePrice(orderTotal), orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("thanh-cong")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = ReplaceUtils.removeTwoZerosFromString(request.getParameter("vnp_Amount"));
        String bankCode = request.getParameter("vnp_BankCode");
        String invoiceId = request.getParameter("vnp_TxnRef");
        int transactionIdFailed = RandomUtils.RandomOtpValue(8);

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("paymentFailedId", transactionIdFailed);
        model.addAttribute("bankCode", bankCode);

        if (paymentStatus == 1) {
            insertOrder(Integer.parseInt(totalPrice), Integer.parseInt(transactionId), "Thanh toán thành công", "Đang vận chuyển");
            insertInvoiceAndInvoiceDetails(invoiceId, transactionId);
            emailService.sendMailPayment();

            session.removeAttribute("customerDTO");
            session.removeAttribute("description");
            return "views/user/vnpay/payment-success";
        } else {
            insertOrder(Integer.parseInt(totalPrice), transactionIdFailed, "Thanh toán thất bại", "Ngưng vận chuyển");
            insertInvoiceAndInvoiceDetails(invoiceId, String.valueOf(transactionIdFailed));

            session.removeAttribute("customerDTO");
            session.removeAttribute("description");
            return "views/user/vnpay/payment-failed";
        }
    }

    public void insertOrder(int priceTotalPay, int transactionId, String statusPayemnt, String statusOder) {
        List<CartItem> cartItemNews = new ArrayList<>();

        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customerDTO");

        List<ShoppingCart> cart = shoppingCartService.findListCartByAccountId(accounts.getAccountId());

        // hàm này để delete shopping cart và thêm vào cart item
        deleteShoppingCart(cart, cartItemNews, transactionId);

        String description = (String) session.getAttribute("description");

        Orders orders = EntityDtoUtils.convertToEntity(customerDTO, Orders.class);

        List<CartItem> cartItems = cartItemService.findListCartByAccountId(accounts.getAccountId());
        for (CartItem cartItem : cartItems) {
            orders.setCartItemId(cartItem.getCartId());
        }
        orders.setOrderId(transactionId);
        orders.setFullName(accounts.getFullname());
        orders.setEmail(accounts.getEmail());
        orders.setPhoneNumber(accounts.getPhoneNumber());
        orders.setTotalAmount(priceTotalPay);
        orders.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orders.setCategoryPayment("Thanh toán bằng hình thức chuyển khoản");
        orders.setStatusPayment(statusPayemnt);
        orders.setStatusOrder(statusOder);
        orders.setDescription(description);
        orderService.save(orders);

        sendMailPayment(accounts, orders.getOrderId());
    }

    public void insertInvoiceAndInvoiceDetails(String invoiceId, String transactionId) {
        Invoice invoice = new Invoice();
        InvoiceDetailts invoiceDetailts = new InvoiceDetailts();

        invoice.setInvoiceId(Integer.parseInt(invoiceId));
        invoice.setPayDate(new Timestamp(System.currentTimeMillis()));
        invoice.setOrderId(Long.parseLong(transactionId));

        invoiceDetailts.setInvoiceId(Integer.parseInt(invoiceId));

        invoiceService.save(invoice);
        invoiceDetailsService.save(invoiceDetailts);
    }

    public void sendMailPayment(Accounts accounts, long orderId) {
        Orders orders = orderService.findByOrderId(orderId);
        List<Products> products = productsService.findByInvoiceCartItemId(orders.getOrderId());
        List<CartItem> cartItems = cartItemService.findByInvoiceCartItemId(orders.getOrderId());
        // Tạo mới paymentDTO để set vào queue để gửi mail
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setEmail(accounts.getEmail());
        paymentDTO.setPhone(accounts.getPhoneNumber());
        paymentDTO.setOrders(orders);
        paymentDTO.setProducts(products);
        paymentDTO.setCartItems(cartItems);

        emailService.queueMailPayment(paymentDTO);
    }

    private void deleteShoppingCart(List<ShoppingCart> cart, List<CartItem> cartItemNews, int orderId) {
        for (ShoppingCart shoppingCart : cart) {
            CartItem cItem = new CartItem();
            cItem.setAccountId(shoppingCart.getAccountId());
            cItem.setAmount(shoppingCart.getAmount());
            cItem.setCreatedAt(shoppingCart.getCreatedAt());
            cItem.setPrice(shoppingCart.getPrice());
            cItem.setInvoiceCartItemId(orderId);
            cItem.setProductId(shoppingCart.getProductId());
            cartItemService.save(cItem);
        }
        // thêm dữ liệu từ shopping cart qua cartItem
        cartItemService.saveAll(cartItemNews);

        // xoá dữ liệu trong shopping cart theo accountId
        shoppingCartService.deleteAll(cart);
    }
}
