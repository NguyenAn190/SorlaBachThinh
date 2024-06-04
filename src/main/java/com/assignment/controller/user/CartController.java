package com.assignment.controller.user;

import com.assignment.dto.AccountDiscountDTO;
import com.assignment.dto.CustomerDTO;
import com.assignment.dto.PaymentDTO;
import com.assignment.entity.*;
import com.assignment.service.*;
import com.assignment.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("gio-hang")
public class CartController {

    @Autowired
    ProductsService productsService;

    @Autowired
    ShoppingCartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    AccountDiscountService accountDiscountService;

    @Autowired
    DiscountService discountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceDetailsService invoiceDetailsService;

    @Autowired
    EmailService emailService;

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpSession session;


    @GetMapping
    public String showCart(Model model) {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        List<ShoppingCart> cart = cartService.findListCartByAccountId(accounts.getAccountId());
        int totalPricePay = cartService.findPriceByAcountId(accounts.getAccountId());

        model.addAttribute("carts", cart);
        model.addAttribute("totalPricePay", totalPricePay);
        session.setAttribute("cartAmount", cartService.countByAccountId(accounts.getAccountId()));
        return "views/user/page/cart";
    }

    @PostMapping("kiem-tra-so-luong")
    public ResponseEntity<Map<String, Object>> kiemTraSoLuongTang(@RequestParam("ma-gio-hang") Long cartId, @RequestParam("so-luong") Integer amount) {
        Map<String, Object> responseData = new HashMap<>();
        ShoppingCart cart = cartService.findCartByCardId(cartId);

        if (cart != null && cart.getProductsByProductId().getAmount() >= amount) {
            responseData.put("valid", true);
            responseData.put("price", cart.getPrice());
        } else {
            responseData.put("message", "Số lượng đã vượt quá giới hạn !");
        }
        return ResponseEntity.ok(responseData);
    }

//    @GetMapping("reload-back")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> reloadBack() {
//        Map<String, Object> responseData = new HashMap<>();
//
//        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
//        List<ShoppingCart> cart = cartService.findListCartByAccountId(accounts.getAccountId());
//        int totalPricePay = cartService.findPriceByAcountId(accounts.getAccountId());
//
//        responseData.put("carts", cart);
//        responseData.put("totalPricePay", totalPricePay);
//        session.setAttribute("cartAmount", cartService.countByAccountId(accounts.getAccountId()));
//
//        return ResponseEntity.ok(responseData);
//    }

    @GetMapping("tang-so-luong")
    public ResponseEntity<Map<String, Object>> loadProductOnPage(@RequestParam("ma-gio-hang") Long cartId, @RequestParam("so-luong") Integer amount) {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        int totalPrice = cartService.findPriceByAcountId(accounts.getAccountId());

        ShoppingCart cart = cartService.updateAmount(cartId, amount);

        int updatedPrice = cartService.getUpdatedPrice(cartId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("cart", cart);
        responseData.put("price", updatedPrice);
        responseData.put("total", totalPrice);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("su-dung-giam-gia")
    public ResponseEntity<Map<String, Object>> addDiscountAccount(@RequestParam("tong-gia-tien") String tongGiaTien, @RequestParam("ma-giam-gia") String discountCode) {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);

        AccountDiscountCodes existCode = accountDiscountService.findByDiscountCode(discountCode, accounts.getAccountId());
        List<DiscountCodes> existDiscountCodes = discountService.findAll();
        DiscountCodes discount = discountService.findById(discountCode);

        boolean codeExists = false;

        Map<String, Object> responseData = new HashMap<>();

        for (DiscountCodes codes : existDiscountCodes) {
            if (Objects.equals(codes.getDiscountCode(), discountCode) && Objects.equals(codes.getIsActive(), "Đang hoạt động") && codes.getDiscountValue() != 0) {
                codeExists = true;
                break;
            }
        }

        if (existCode != null) {
            responseData.put("message", "warning:Mỗi tài khoản chỉ được sử dụng 1 mã code trong 1 lần !");

        } else if (!codeExists) {
            responseData.put("message", "warning:Mã code không tồn tại hoặc đã hết thời hạn sử dụng !");

        } else if (discount.getDiscountPrice() >= Integer.parseInt(tongGiaTien)) {
            responseData.put("message", "warning:Không thể sử dụng mã giảm giá !");

        } else {
            int priceApplyCode = Integer.parseInt(tongGiaTien) - discount.getDiscountPrice();

            responseData.put("price", discount.getDiscountPrice());
            responseData.put("priceApplyCode", priceApplyCode);
            responseData.put("message", "success:Chúc mừng bạn sử dụng thành công mã code " + discountCode + " và được giảm " + ReplaceUtils.formatPrice(discount.getDiscountPrice()) + " !");
        }
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("xac-nhan-thong-tin")
    public String showCheckDetails(Model model, @RequestParam(name = "tong-gia-tien", defaultValue = "") int tongGiaTien, @RequestParam(name = "ma-giam-gia", defaultValue = "") String discountCode) {
        if (!Objects.equals(discountCode, "")) {
            DiscountCodes discount = discountService.findById(discountCode);
            int priceDiscountCode = discount.getDiscountPrice();
            model.addAttribute("priceDiscountCode", ReplaceUtils.formatPrice(priceDiscountCode) + " ₫");
        } else {
            model.addAttribute("priceDiscountCode", "0 ₫");
        }
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        Customers customers = customerService.findByAccountId(accounts.getAccountId());
        List<ShoppingCart> shoppingCarts = cartService.findListCartByAccountId(accounts.getAccountId());
        List<ShoppingCart> cart = cartService.findListCartByAccountId(accounts.getAccountId());
        int priceTotalPay = cartService.findPriceByAcountId(accounts.getAccountId());

        model.addAttribute("carts", cart);
        String province = customers.getProvince();
        String district = customers.getDistrict();
        String village = customers.getVillage();

        if (province != null && district != null && village != null && !shoppingCarts.isEmpty()) {
            model.addAttribute("customer", customers);
            model.addAttribute("shoppingCarts", shoppingCarts);
            model.addAttribute("totalPricePay", priceTotalPay);
            model.addAttribute("discountCode", discountCode);
            session.setAttribute("priceTotalPay", priceTotalPay);
            session.setAttribute("tongGiaTien", tongGiaTien);

//            if (tongGiaTien != priceTotalPay && discountCode != null) {
//                model.addAttribute("totalPriceAfter", tongGiaTien);
//                session.setAttribute("totalPriceAfter", tongGiaTien);
//            }

            return "views/user/page/check-detailts";
        } else {
            session.setAttribute("centerWarning", "Nhấn vào <a href='/thong-tin'>đây</a> để cập nhật thông tin cá nhân của bạn !");
            return "redirect:/gio-hang";
        }
    }

    @PostMapping("xac-nhan-thong-tin")
    public String submitOrderDetails(@CookieValue(value = "tongGiaTien", defaultValue = "") String tongGiaTien, CustomerDTO customerDTO, Model model) {
        // Tạo mới cartItem để thêm từ shopping sang cartItems
        List<CartItem> cartItemNews = new ArrayList<>();

        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);

        List<ShoppingCart> cart = cartService.findListCartByAccountId(accounts.getAccountId());

//        String priceSession = (String) session.getAttribute("totalPriceAfter");
//        int totalPriceDiscountCode = Integer.parseInt(priceSession);

        String discountCode = request.getParameter("discountCode");
        int orderId = RandomUtils.RandomOtpValue(8);
        int priceTotalPay;
        if (!tongGiaTien.equals("")) {
            priceTotalPay = Integer.parseInt(tongGiaTien);
        } else {
            priceTotalPay = cartService.findPriceByAcountId(accounts.getAccountId());
        }
        String categoryPayment = request.getParameter("categoryPayment");
        String description = request.getParameter("description");

        if (categoryPayment.equals("COD")) {
//            minusAmountDiscountCode(accounts, discountCode);
            deleteShoppingCart(cart, cartItemNews, orderId);
            insertOrder(customerDTO, accounts, orderId, priceTotalPay, description);
            insertInvoiceAndInvoiceDetails(RandomUtils.RandomOtpValue(8), String.valueOf(orderId));

            session.setAttribute("centerSuccess", "Đơn hàng của đã được đặt thành công, vui lòng kiểm tra hoá đơn tại email !");
            return "redirect:/trang-chu";
        }

        if (categoryPayment.equals("TRANSFER")) {
            session.setAttribute("customerDTO", customerDTO);
            session.setAttribute("description", description);

            model.addAttribute("full_name", accounts.getFullname());
            model.addAttribute("price", priceTotalPay);
            model.addAttribute("orderId", "Thanh toan don hang " + orderId);
            return "views/user/vnpay/payment-vnpay";
        }
        return "redirect:/trang-chu";
    }

    @GetMapping("xoa-gio-hang/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.delete(id);
        session.setAttribute("toastSuccess", "Xoá sản phẩm thành công !");
        return "redirect:/gio-hang";
    }

    private void insertOrder(CustomerDTO customerDTO, Accounts accounts, int orderId, int priceTotalPay, String description) {
        List<CartItem> cartItems = cartItemService.findListCartByAccountId(accounts.getAccountId());
        Long cartItemId = null;
        for (CartItem cartItem : cartItems) {
            cartItemId = cartItem.getCartId();
        }
        Orders orders = EntityDtoUtils.convertToEntity(customerDTO, Orders.class);
        orders.setCartItemId(cartItemId);
        orders.setOrderId(orderId);
        orders.setFullName(accounts.getFullname());
        orders.setEmail(accounts.getEmail());
        orders.setPhoneNumber(accounts.getPhoneNumber());
        orders.setTotalAmount(priceTotalPay);
        orders.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orders.setCategoryPayment("Thanh toán khi nhận hàng");
        orders.setStatusPayment("Chờ xác nhận");
        orders.setStatusOrder("Chờ vận chuyển");
        orders.setDescription(description);
        orderService.save(orders);

        minusAmountProduct(orders.getOrderId());
        sendMailPayment(accounts, orders.getOrderId());
    }

    public void insertInvoiceAndInvoiceDetails(int invoiceId, String transactionId) {
        Invoice invoice = new Invoice();
        InvoiceDetailts invoiceDetailts = new InvoiceDetailts();

        invoice.setInvoiceId(invoiceId);
        invoice.setPayDate(new Timestamp(System.currentTimeMillis()));
        invoice.setOrderId(Long.parseLong(transactionId));

        invoiceDetailts.setInvoiceId(invoiceId);

        invoiceService.save(invoice);
        invoiceDetailsService.save(invoiceDetailts);
    }

    public void minusAmountProduct(long orderId) {
        List<Products> products = productsService.findByInvoiceCartItemId(orderId);
        List<CartItem> cartItems = cartItemService.findByInvoiceCartItemId(orderId);

        for (int i = 0; i < products.size(); i++) {
            Products product = products.get(i);
            CartItem cartItem = cartItems.get(i);

            int orderQuantity = cartItem.getAmount(); // Lấy số lượng từ đơn hàng
            int currentQuantity = product.getAmount(); // Lấy số lượng sản phẩm hiện có

            if (currentQuantity >= orderQuantity) {
                product.setAmount(currentQuantity - orderQuantity); // Giảm số lượng tương ứng
            }
            if (currentQuantity == 0) {
                product.setIsStatusDelete("Ngừng kinh doanh");
            }

            productsService.save(product);
        }
    }

    private void minusAmountDiscountCode(Accounts accounts, String discountCode) {
        DiscountCodes discount = discountService.findById(discountCode);
        AccountDiscountDTO discountDTO = new AccountDiscountDTO();

        discountDTO.setAccountId(accounts.getAccountId());
        discountDTO.setDiscountCode(discountCode);
        discountDTO.setUsedAt(new Timestamp(System.currentTimeMillis()));
        AccountDiscountCodes accountDiscountCodes = EntityDtoUtils.convertToEntity(discountDTO, AccountDiscountCodes.class);
        accountDiscountService.insert(accountDiscountCodes);

        // Giảm số lượng mã giảm giá xuống 1 và cập nhật trạng thái nếu giá trị giảm giá bằng 0
        if (discount != null) {
            int newAmount = discount.getDiscountValue() - 1;
            if (newAmount == 0) {
                discount.setIsActive("Ngưng hoạt động");
            }
            discount.setDiscountValue(newAmount);
            discountService.update(discount);
        }
    }

    public void deleteShoppingCart(List<ShoppingCart> cart, List<CartItem> cartItemNews, int orderId) {
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
        cartItemService.saveAll(cartItemNews);

        cartService.deleteAll(cart);
    }

    public void sendMailPayment(Accounts accounts, long orderId) {
        Orders orders = orderService.findByOrderId(orderId);
        List<Products> products = productsService.findByInvoiceCartItemId(orders.getOrderId());
        List<CartItem> cartItems = cartItemService.findByInvoiceCartItemId(orders.getOrderId());
        // Tạo mới paymentDTO để set vào queue để gửi mail
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setEmail(accounts.getEmail());
        paymentDTO.setFull_name(accounts.getFullname());
        paymentDTO.setPhone(accounts.getPhoneNumber());
        paymentDTO.setOrders(orders);
        paymentDTO.setProducts(products);
        paymentDTO.setCartItems(cartItems);

        emailService.queueMailPayment(paymentDTO);
    }
}