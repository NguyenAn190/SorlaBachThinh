package com.assignment.controller.admin.brand;

import com.assignment.dto.BrandsDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Brands;
import com.assignment.exception.BrandsNotFoundException;
import com.assignment.service.BrandsService;
import com.assignment.service.HistoryService;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/chinh-sua-thuong-hieu")
public class EditBrandController {

    @Autowired
    HttpSession session;

    @Autowired
    BrandsService brandsService;

    @Autowired
    HistoryService historyService;

    @RequestMapping("{id}")
    public String showFromAddBrands(@ModelAttribute("brandsDTO") BrandsDTO brandsDTO, Model model, @PathVariable String id) throws BrandsNotFoundException {
        Optional<Brands> brands = Optional.of(brandsService.findById(id).get());
        model.addAttribute("brand", brands);

        return "views/admin/page/crud/brand/brand-edit";
    }

    @PostMapping("edit/{id}")
    public String saveBrands(@Valid @ModelAttribute BrandsDTO brandsDTO, BindingResult bindingResult, @PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model) throws BrandsNotFoundException {
        Optional<Brands> brands = Optional.of(brandsService.findById(id).get());
        if (bindingResult.hasErrors()) {
            model.addAttribute("brand", brands);
            return "views/admin/page/crud/brand/brand-edit";
        } else {
            Brands save = EntityDtoUtils.convertToEntity(brandsDTO, Brands.class);

            brandsService.insert(save);
            model.addAttribute("brand", brands);
            session.setAttribute("toastSuccess", "Cập nhật thành công");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Cập nhật nhà cung cấp");
        }
        return "redirect:/admin/danh-sach-thuong-hieu";
    }

    @PostMapping("delete/{id}")
    public String deleteBrands(@PathVariable("id") String id) throws BrandsNotFoundException {
        Optional<Brands> brands = Optional.of(brandsService.findById(id).get());

        BrandsDTO brandsDTO = EntityDtoUtils.convertToDto(brands, BrandsDTO.class);
        brandsDTO.setIsStatusDelete("Ngưng hợp tác");

        Brands save = EntityDtoUtils.convertToEntity(brandsDTO, Brands.class);
        brandsService.insert(save);
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm nhà cung cấp");
        return "redirect:/admin/danh-sach-thuong-hieu";
    }

    @ModelAttribute("statusOptions")
    public List<String> statusOptions() {
        List<String> options = new ArrayList<>();
        options.add("Đang hợp tác");
        options.add("Ngưng hợp tác");
        return options;
    }
}
