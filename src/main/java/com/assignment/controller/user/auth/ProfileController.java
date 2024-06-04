package com.assignment.controller.user.auth;

import com.assignment.dto.CustomerDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Customers;
import com.assignment.service.AccountService;
import com.assignment.service.CustomerService;
import com.assignment.service.EmailService;
import com.assignment.service.OrderService;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("thong-tin")
public class ProfileController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    AccountService accountService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CustomerService customerService;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpSession session;

    @Autowired
    EmailService emailService;

    @GetMapping
    public String showProfile(Model model) {
        Accounts accounts = (Accounts) session.getAttribute("account");
        Customers cus = customerService.findByAccountId(accounts.getAccountId());
        Optional<Customers> customers = customerService.findById(cus.getCustomerId());

        if (customers.isPresent()) {
            model.addAttribute("sumOrderPrice", orderService.sumOrderPrice(accounts.getAccountId()));
            model.addAttribute("sumOrder", orderService.countOrdersByAccountId(accounts.getAccountId()));
            model.addAttribute("customerDTO", EntityDtoUtils.convertToDto(customers.get(), CustomerDTO.class));
            model.addAttribute("customers", customers.get());
            return "views/user/page/auth/user-profile";
        } else {
            return "redirect:/dang-nhap";
        }
    }

    @GetMapping("sua-thong-tin/{id}")
    public String showEditProfile(@PathVariable("id") long id, Model model) {
        Accounts accounts = accountService.findById(id);

        Customers cus = customerService.findByAccountId(accounts.getAccountId());
        Optional<Customers> customers = customerService.findById(cus.getCustomerId());

        if (customers.isPresent()) {
            model.addAttribute("sumOrderPrice", orderService.sumOrderPrice(accounts.getAccountId()));
            model.addAttribute("sumOrder", orderService.countOrdersByAccountId(accounts.getAccountId()));
            model.addAttribute("customerDTO", EntityDtoUtils.convertToDto(customers.get(), CustomerDTO.class));
            model.addAttribute("customers", customers.get());
            model.addAttribute("accounts", accounts);
            return "views/user/page/auth/user-editprofile";
        }
        return "redirect:/thong-tin";
    }

    @PostMapping("sua-thong-tin/{id}")
    public String account_update(@PathVariable("id") String id, @ModelAttribute("customerDTO") CustomerDTO customerDTO) {
        String email = customerDTO.getAccountsByAccountId().getEmail();
        String full_name = customerDTO.getAccountsByAccountId().getFullname();
        String phone = customerDTO.getAccountsByAccountId().getPhoneNumber();

        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        accountService.userupdateAccounts(email, full_name, phone);

        customerDTO.setCustomerId(id);
        customerDTO.setAccountsByAccountId(accounts);
        Customers customers = EntityDtoUtils.convertToEntity(customerDTO, Customers.class);
        customerService.save(customers);

        session.setAttribute("centerSuccess", "Cập nhật thông tin cá nhân thành công !");

        return "redirect:/thong-tin";
    }
}
