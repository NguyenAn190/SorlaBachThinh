package com.assignment.controller.admin.product;

import com.assignment.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class ProductControllerAD {

    @Autowired
    ProductsService productsService;

    @GetMapping("san-pham")
    public String product(Model model) {
        model.addAttribute("products", productsService.findAll());
        return "views/admin/page/views/products-list";
    }
}
