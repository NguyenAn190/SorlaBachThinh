package com.assignment.controller.admin;

import com.assignment.dto.ProductTypeDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Categorys;
import com.assignment.entity.ProductTypes;
import com.assignment.service.CategoryService;
import com.assignment.service.HistoryService;
import com.assignment.service.ProductTypeService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class ProductTypeControllerAD {

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @GetMapping("the-loai")
    public String product_types(Model model) {
        model.addAttribute("product_types", productTypeService.findAll());
        return "views/admin/page/views/product-type-list";
    }

    @GetMapping("them-the-loai")
    public String show_product_types_add(ProductTypeDTO productTypeDTO, Model model) {
        List<Categorys> categories = categoryService.findAll();

        model.addAttribute("productTypeDTO", productTypeDTO);
        model.addAttribute("categories", categories);
        return "views/admin/page/crud/product_type/product-type-add";
    }

    @GetMapping("sua-the-loai/{id}")
    public String show_product_types_edit(@PathVariable Long id, Model model) {
        ProductTypes productTypes = productTypeService.findById(id);
        ProductTypeDTO productTypeDTO = new ProductTypeDTO(productTypes);
        List<Categorys> categories = categoryService.findAll();

        model.addAttribute("product_types", productTypeDTO);
        model.addAttribute("categories", categories);
        return "views/admin/page/crud/product_type/product-type-edit";
    }

    @GetMapping("xoa-the-loai/{id}")
    public String delete_product_type(@PathVariable Long id) {
        ProductTypes productTypes = productTypeService.findById(id);
        productTypes.setActive(Boolean.FALSE);
        session.setAttribute("toastSuccess", "Xoá thể loại thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Xoá thể loại");
        return "redirect:/admin/the-loai";
    }

    @PostMapping("them-the-loai")
    public String insert_product_types(ProductTypeDTO productTypeDTO, @RequestParam("category-name") Long categoryId) {
        productTypeService.insert(productTypeDTO, categoryId);
        session.setAttribute("toastSuccess", "Thêm thể loại thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm thể loại");
        return "redirect:/admin/the-loai";
    }

    @PostMapping("sua-the-loai")
    public String update_product_types(@RequestParam("category-name") Long categoryId, ProductTypeDTO productTypeDTO) {
        productTypeService.update(productTypeDTO, categoryId);
        session.setAttribute("toastSuccess", "Cập nhật thể loại thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Cập nhật thể loại");
        return "redirect:/admin/the-loai";
    }
}
