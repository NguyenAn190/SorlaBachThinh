package com.assignment.controller.admin;

import com.assignment.dto.CategoryDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Categorys;
import com.assignment.service.CategoryService;
import com.assignment.service.HistoryService;
import com.assignment.service.OrderService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("admin")
public class CategoryControllerAD {

    @Autowired
    CategoryService categoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession session;

    @GetMapping("lich-su")
    public String history_update_product(Model model) {
        model.addAttribute("histories", historyService.findAll());
        return "views/admin/page/views/historys-list";
    }

    @GetMapping("danh-muc")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "views/admin/page/views/categorys-list";
    }

    @GetMapping("them-danh-muc")
    public String categorys_add(CategoryDTO categoryDTO, Model model) {
        model.addAttribute("categoryDTO", categoryDTO);
        return "views/admin/page/crud/category/categorys-add";
    }

    @GetMapping("sua-danh-muc/{id}")
    public String categorys_edit(@PathVariable Long id, Model model) {
        Categorys categorys = categoryService.findById(id);
        CategoryDTO categoryDTO = new CategoryDTO(categorys);

        model.addAttribute("images", categorys.getCategoryImage());
        model.addAttribute("categoryDTO", categoryDTO);
        return "views/admin/page/crud/category/categorys-edit";
    }

    @GetMapping("xoa-danh-muc/{id}")
    public String delete_categorys(@PathVariable Long id) {
        Categorys categorys = categoryService.findById(id);
        categorys.setActive(Boolean.FALSE);
        session.setAttribute("toastSuccess", "Xoá danh mục thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Xoá danh mục");
        return "redirect:/admin/danh-muc";
    }

    @PostMapping("them-danh-muc")
    public String addFormCategory(CategoryDTO categoryDTO, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Path path = Paths.get("src/main/resources/static/upload/");

            if (!path.toFile().exists()) {
                path.toFile().mkdirs();
            }
            try {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        categoryService.insert(categoryDTO, file.getOriginalFilename());
        session.setAttribute("toastSuccess", "Thêm danh mục thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm danh mục");
        return "redirect:/admin/danh-muc";
    }

    @PostMapping("sua-danh-muc")
    public String updateFormCategory(CategoryDTO categoryDTO, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            Path path = Paths.get("src/main/resources/static/upload/");

            if (!path.toFile().exists()) {
                path.toFile().mkdirs();
            }
            try {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        categoryService.update(categoryDTO, file.getOriginalFilename());
        session.setAttribute("toastSuccess", "Cập nhật thành công !");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Cập nhật danh mục");
        return "redirect:/admin/danh-muc";
    }
}
