package com.assignment.controller.admin.brand;

import com.assignment.entity.Accounts;
import com.assignment.entity.Brands;
import com.assignment.exception.BrandsNotFoundException;
import com.assignment.service.BrandsService;
import com.assignment.service.HistoryService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping("/admin/danh-sach-thuong-hieu")
public class BrandControllerAD {

    @Autowired
    BrandsService brandsService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String showBrands(Model model) {
        model.addAttribute("brandsList", brandsService.findAll());
        return "views/admin/page/views/brands";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBrands(@PathVariable("id") String id) throws BrandsNotFoundException {
        Optional<Brands> brandsOptional = brandsService.findById(id);
        if (brandsOptional.isPresent()) {
            Brands brands = brandsOptional.get();
            brands.setIsStatusDelete("Ngưng hợp tác");
            brandsService.insert(brands);
            session.setAttribute("toastSuccess", "Ngưng hợp tác thành công !");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Ngưng hợp tác");
        }
        return "redirect:/admin/danh-sach-thuong-hieu";
    }
}
