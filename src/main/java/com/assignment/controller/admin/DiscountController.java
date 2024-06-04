package com.assignment.controller.admin;

import com.assignment.dto.DiscountCodesDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.DiscountCodes;
import com.assignment.service.AccountDiscountCodeService;
import com.assignment.service.DiscountService;
import com.assignment.service.HistoryService;
import com.assignment.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @Autowired
    AccountDiscountCodeService accountDiscountCodeService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @RequestMapping("giam-gia")
    public String showDiscount(Model model) {
        model.addAttribute("discountCodes", discountService.findAll());
        return "views/admin/page/views/discouts-list";
    }

    @GetMapping("ap-dung-giam-gia")
    public String showAccountDiscountCode(Model model) {
        model.addAttribute("accounts", discountService.findAll());
        model.addAttribute("accountDiscountCodeList", accountDiscountCodeService.findAll());
        return "views/admin/page/views/apply-discouts-list";
    }

    @RequestMapping("them-ma")
    public String showAddDiscout(@ModelAttribute DiscountCodesDTO discountCodesDTO, Model model) {
        model.addAttribute("discountCodesDTO", discountCodesDTO);
        return "views/admin/page/crud/discout/discout-add";
    }

    @RequestMapping("them-ma/random-code")
    public ModelAndView randomeCodeAddDiscout(@ModelAttribute DiscountCodesDTO discountCodesDTO, ModelAndView modelAndView, RedirectAttributes redirectAttributes, Model model) {
        modelAndView.addObject("discountCodeValue", DiscountCodeGeneratoUtils.generateDiscountCode());
        modelAndView.setViewName("views/admin/page/crud/discout/discout-add");

        return modelAndView;
    }

    @RequestMapping("them-ma/save")
    public String saveDiscountCode(@Validated @ModelAttribute DiscountCodesDTO discountCodesDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        String price = request.getParameter("price");
        if (discountCodesDTO.getExpirationDate().isBefore(discountCodesDTO.getCreatedAt())) {
            redirectAttributes.addFlashAttribute("errorDate", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            return "views/admin/page/crud/discout/discout-add";
        }

        if (!bindingResult.hasErrors()) {
            discountCodesDTO.setIsActive("Đang hoạt động");
            discountCodesDTO.setDiscountPrice(ReplaceUtils.replacePrice(price));
            DiscountCodes save = EntityDtoUtils.convertToEntity(discountCodesDTO, DiscountCodes.class);
            discountService.insert(save);

            SessionUtils.setAttribute("centerSuccess", "Thêm mã giảm giá thành công!");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm mã giảm giá");
            return "redirect:/admin/giam-gia";
        } else {
            model.addAttribute("discountCodeValue", DiscountCodeGeneratoUtils.generateDiscountCode());
            return "views/admin/page/crud/discout/discout-add";
        }
    }

    @RequestMapping("sua-ma/{id}")
    public String showAddDiscout(@ModelAttribute DiscountCodesDTO discountCodesDTO, @PathVariable("id") String id, Model model) {
        DiscountCodes discountCodes = discountService.findById(id);

        model.addAttribute("discountCodesDTO", discountCodesDTO);
        model.addAttribute("discountValue", discountCodes);
        model.addAttribute("discountPriceEdit", ReplaceUtils.formatPrice(discountCodes.getDiscountPrice()));
        return "views/admin/page/crud/discout/discout-edit";
    }

    @PostMapping("sua-ma/save/{id}")
    public String showEditDiscout(@Validated @ModelAttribute DiscountCodesDTO discountCodesDTO, BindingResult bindingResult, @PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model) {
        String price = request.getParameter("discountPriceEdit");

        if (!bindingResult.hasErrors()) {
            if (discountCodesDTO.getExpirationDate().isBefore(discountCodesDTO.getCreatedAt())) {
                redirectAttributes.addFlashAttribute("errorDate", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
                return "views/admin/page/crud/discout/discout-edit";
            } else {
                discountCodesDTO.setDiscountCode(id);
                if (discountCodesDTO.getDiscountCode() != null) {
                    discountCodesDTO.setDiscountPrice(ReplaceUtils.replacePrice(price));
                    DiscountCodes save = EntityDtoUtils.convertToEntity(discountCodesDTO, DiscountCodes.class);
                    discountService.update(save);
                }

                SessionUtils.setAttribute("centerSuccess", "Sửa mã giảm giá thành công!");
                historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Sửa mã giảm giá");
                return "redirect:/admin/giam-gia";
            }

        } else {
            model.addAttribute("discountCodeValue", DiscountCodeGeneratoUtils.generateDiscountCode());
            return "views/admin/page/crud/discout/discout-edit";

        }
    }

    @GetMapping("xoa-ma/{id}")
    public String deleteDiscout(@PathVariable("id") String id) {
        discountService.delete(id);
        SessionUtils.setAttribute("centerSuccess", "Xóa mã giảm giá thành công!");
        historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "xóa mã giảm giá");
        return "redirect:/admin/giam-gia";
    }

    @ModelAttribute("statusOptions")
    public List<String> statusOptions() {
        List<String> options = new ArrayList<>();
        options.add("Đang hoạt động");
        options.add("Ngưng hoạt động");
        return options;
    }
}
