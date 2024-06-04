package com.assignment.controller.admin.auth;

import com.assignment.dto.LoginDTO;
import com.assignment.entity.Accounts;
import com.assignment.service.AccountService;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.Cookie;
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

@Controller
@RequestMapping("admin")
public class LoginControllerAD {

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
    HttpServletResponse response;

    @Autowired
    HttpSession session;

    @GetMapping
    public String showFormLogin(LoginDTO loginDTO, Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "password", defaultValue = "") String password) {
        if (email != null && password != null) {
            loginDTO.setEmail(email);
            loginDTO.setPasswords(password);
            loginDTO.setRememberMe(true);
        }

        model.addAttribute("loginDTO", loginDTO);
        return "views/admin/login";
    }

    @PostMapping
    public String submitForm(@Valid LoginDTO loginDTO, BindingResult result) {
        Accounts account = accountService.login(loginDTO.getEmail());

        if (result.hasErrors()) {
            return "views/admin/login";
        }

        if (account == null) {
            session.setAttribute("toastFailed", "Tài khoản không tồn tại !");

        } else if (!account.getAcctive()) {
            session.setAttribute("toastFailed", "Tài khoản không hoạt động !");

        } else if (!account.getRole().equals("ADMIN")) {
            session.setAttribute("toastWarning", "Không đủ quyền cần thiết !");

        } else {
            String passwordsInDB = account.getPasswords();
            String passwordInput = loginDTO.getPasswords();

            if (encoder.matches(passwordInput, passwordsInDB)) {
                if (loginDTO.isRememberMe()) {
                    Cookie emailCookie = new Cookie("email", loginDTO.getEmail());
                    Cookie passwordCookie = new Cookie("password", loginDTO.getPasswords());

                    // Set thời gian sống của cookie (ví dụ: 7 ngày)
                    int maxAge = 7 * 24 * 60 * 60;
                    emailCookie.setMaxAge(maxAge);
                    passwordCookie.setMaxAge(maxAge);

                    emailCookie.setPath("/");
                    passwordCookie.setPath("/");

                    response.addCookie(emailCookie);
                    response.addCookie(passwordCookie);
                }

                session.setAttribute(SessionAtrr.CURRENT_ADMIN, account);
                session.setAttribute("toastSuccess", "Đăng nhập thành công !");
                return "redirect:/admin/dashboard";
            }
            session.setAttribute("toastFailed", "Sai thông tin đăng nhập !");
        }
        return "redirect:/admin";
    }
}
