package com.assignment.controller.user.auth;

import com.assignment.dto.LoginDTO;
import com.assignment.dto.response.GoogleResponse;
import com.assignment.entity.Accounts;
import com.assignment.entity.Customers;
import com.assignment.security.GoogleUtils;
import com.assignment.service.AccountService;
import com.assignment.service.CustomerService;
import com.assignment.utils.RandomUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("dang-nhap")
public class LoginController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpSession session;

    @GetMapping
    public String showLoginPage(LoginDTO loginDTO, Model model) throws IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "views/user/page/auth/login";
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GoogleResponse googlePojo = GoogleUtils.getUserInfo(accessToken);

            String email = googlePojo.getEmail();
            String pass = RandomUtils.RandomToken(6);
            String phone = RandomUtils.RandomPhoneNumber();
            String full_name = RandomUtils.RandomFullname();

            Accounts getAccountFormGoogle = accountService.findByEmail(email);

            if (getAccountFormGoogle == null) {
                String encodedPassword = encoder.encode(pass);

                Accounts accounts = accountService.registerGoogle(email, encodedPassword, phone, full_name);

                if (accounts != null) {
                    Customers customer = new Customers();
                    customer.setAccountId(accounts.getAccountId());
                    customer.setCustomerId(customerIdValue());
                    customerService.save(customer);

                    session.setAttribute("toastSuccess", "Đăng nhập thành công !");
                    session.setAttribute(SessionAtrr.CURRENT_USER, accounts);
                    return "redirect:/trang-chu";
                }
            } else {
                if (getAccountFormGoogle.getAcctive() && getAccountFormGoogle.getRole().equals("USER")) {
                    session.setAttribute("toastSuccess", "Đăng nhập thành công !");
                    session.setAttribute(SessionAtrr.CURRENT_USER, getAccountFormGoogle);
                    return "redirect:/trang-chu";
                } else if (getAccountFormGoogle.getAcctive() && getAccountFormGoogle.getRole().equals("ADMIN")) {
                    session.setAttribute("toastWarning", "Bạn không đủ quyền cần thiết !");
                    return "redirect:/dang-nhap";
                } else if (!getAccountFormGoogle.getAcctive()) {
                    session.setAttribute("toastError", "Tài khoản bạn đã bị khoá !");
                    return "redirect:/dang-nhap";
                }
            }
        }
        model.addAttribute("loginDTO", loginDTO);
        return "views/user/page/auth/login";
    }

    @PostMapping
    public String login(@Valid LoginDTO loginDTO, BindingResult result) {
        Accounts account = accountService.login(loginDTO.getEmail());

        if (result.hasErrors()) {
            return "views/user/page/auth/login";
        }

        if (account == null) {
            session.setAttribute("toastFailed", "Tài khoản không tồn tại !");

        } else if (!account.getAcctive()) {
            session.setAttribute("toastWarning", "Tài khoản đang bị khoá !");

        } else if (!account.getRole().equals("USER")) {
            session.setAttribute("toastWarning", "Bạn không đủ quyền cần thiết !");

        } else {
            String passwordsInDB = account.getPasswords();
            String passwordInput = loginDTO.getPasswords();

            if (encoder.matches(passwordInput, passwordsInDB)) {
                session.setAttribute("account", account);
                session.setAttribute("toastSuccess", "Đăng nhập thành công !");

                return "redirect:/trang-chu";
            }

            session.setAttribute("toastFailed", "Sai thông tin đăng nhập !");
        }
        return "redirect:/dang-nhap";
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
