package com.assignment.controller.user.auth;

import com.assignment.dto.RegisterDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Customers;
import com.assignment.service.AccountService;
import com.assignment.service.CustomerService;
import com.assignment.service.EmailService;
import com.assignment.utils.RandomUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dang-ky")
public class SignupController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    EmailService emailService;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    CustomerService customerService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @GetMapping("xac-thuc-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        Accounts accounts = accountService.findByToken(token);
        if (accounts != null) {
            String email = accounts.getEmail();
            model.addAttribute("email", email);
            accounts.setAcctive(Boolean.TRUE);
            accountService.save(accounts);
        }
        return "views/user/page/verify-success";
    }

    @GetMapping
    public String showSigupPage(RegisterDTO registerDTO, Model model) {
        model.addAttribute("token", RandomUtils.RandomToken(20));
        model.addAttribute("registerDTO", registerDTO);
        return "views/user/page/auth/sign-up";
    }

    @PostMapping
    public String submitForm(@Valid RegisterDTO registerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "views/user/page/auth/sign-up";
        }

        if (accountService.findByEmail(registerDTO.getEmail()) != null) {
            session.setAttribute("toastFailed", "Email đã tồn tại !");
            return "redirect:/dang-ky";
        }

        if (accountService.findByPhone(registerDTO.getPhoneNumber()) != null) {
            session.setAttribute("toastFailed", "Số điện thoại đã tồn tại !");
            return "redirect:/dang-ky";
        }

        // mã hoá password
        String encodedPassword = encoder.encode(registerDTO.getPasswords());
        registerDTO.setPasswords(encodedPassword);

        Accounts account = accountService.register(registerDTO);
        if (account != null) {
            // Tạo đối tượng Customer
            Customers customer = new Customers();
            customer.setAccountId(account.getAccountId()); // Đặt accountId từ tài khoản
            customer.setCustomerId(customerIdValue());

            // Lưu trữ đối tượng Customer vào cơ sở dữ liệu (nếu cần)
            customerService.save(customer);

            emailService.queueEmail(registerDTO);
            session.setAttribute("centerSuccess", "Đăng ký tài khoản thành công, vui lòng xác thực email !");
            return "redirect:/dang-nhap";
        }
        return "redirect:/dang-ky";
    }

    @ModelAttribute("customerIdValue")
    public String customerIdValue() {
        List<Customers> customersList = customerService.findAll();
        String customertId;
        if (customersList.isEmpty()) {
            customertId = "KH0001";
        } else {
            String input = customersList.get(customersList.size() - 1).getCustomerId();
            String prefix = input.substring(0, 2);
            int number = Integer.parseInt(input.substring(2));
            String newNumber = String.format("%04d", number + 1); // Thay đổi 1 thành number + 1 và "%06d" thành "%04d"

            customertId = prefix + newNumber;
        }
        return customertId;
    }
}
