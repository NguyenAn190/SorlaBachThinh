package com.assignment.controller.admin.brand;

import com.assignment.dto.BrandsDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Brands;
import com.assignment.service.BrandsService;
import com.assignment.service.HistoryService;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/them-thuong-hieu")
public class InsertBrandController {

    List<Brands> brandsList = new ArrayList<>();

    @Autowired
    BrandsService brandsService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String showFromAddBrands(@ModelAttribute("brandsDTO") BrandsDTO brandsDTO, Model model) {
        model.addAttribute("isStatusDelete", "Đang hợp tác");
        return "views/admin/page/crud/brand/brand-add";
    }

    @RequestMapping("insert")
    public String saveBrands(@Validated @ModelAttribute BrandsDTO brandsDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isStatusDelete", "Đang hợp tác");
            return "views/admin/page/crud/brand/brand-add";
        } else {
            brandsDTO.setBrandId(brandId());
            brandsDTO.setIsStatusDelete("Đang hợp tác");
            Brands saveBrands = EntityDtoUtils.convertToEntity(brandsDTO, Brands.class);

            brandsService.insert(saveBrands);
            session.setAttribute("toastSuccess", "Thêm thành công !");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm nhà cung cấp");
            return "redirect:/admin/danh-sach-thuong-hieu";
        }
    }

    @ModelAttribute("brandsIdValue")
    public String brandId() {
        brandsList = brandsService.findAll();
        String brandId;
        if (brandsList.isEmpty()) {
            brandId = "BR001";
            return brandId;
        } else {
            String input = brandsList.get(brandsList.size() - 1).getBrandId();
            String prefix = input.substring(0, 2);
            int number = Integer.parseInt(input.substring(2));
            number++;
            String newNumber = String.format("%03d", number);

            brandId = prefix + newNumber;
            return brandId;
        }
    }
}
