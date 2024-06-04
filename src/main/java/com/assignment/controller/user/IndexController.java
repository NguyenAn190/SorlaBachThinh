package com.assignment.controller.user;

import com.assignment.entity.Accounts;
import com.assignment.entity.Products;
import com.assignment.service.CategoryService;
import com.assignment.service.ProductsService;
import com.assignment.service.ShoppingCartService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"trang-chu", "/", ""})
public class IndexController {

    @Autowired
    ProductsService productsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    HttpSession session;

    @GetMapping
    public String index(Model model) {
        Pageable pageable = PageRequest.of(0, 4);
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        if (accounts != null) {
            session.setAttribute("cartAmount", shoppingCartService.countByAccountId(accounts.getAccountId()));
        }

        model.addAttribute("products", productsService.findByProductTypeId(1));
        model.addAttribute("categories", categoryService.findAllTop4(pageable));
        return "views/user/index";
    }

    @GetMapping("load-slide")
    public ResponseEntity<Map<String, Object>> loadProductOnPage(@RequestParam(value = "ma-san-pham", required = false) Long categoryId) {
        Map<String, Object> responseData = new HashMap<>();

        List<Products> products = productsService.findByCategoryId(categoryId);

        String brandName = "";

        for (Products pd : products) {
            brandName = pd.getBrandsByBrandId().getBrandName();
        }

        if (categoryId == null && products.isEmpty()) {
            responseData.put("product_default", productsService.findByProductTypeId(1));

        } else {
            if (!products.isEmpty()) {
                responseData.put("product_load_slide", products);
                responseData.put("brandName", brandName);
            }
        }
        return ResponseEntity.ok(responseData);
    }
}
