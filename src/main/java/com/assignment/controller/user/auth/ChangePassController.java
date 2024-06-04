package com.assignment.controller.user.auth;

import com.assignment.dto.ChangePassDTO;
import com.assignment.entity.Accounts;
import com.assignment.service.AccountService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("doi-mat-khau")
public class ChangePassController {

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
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpSession session;

    @GetMapping
    public String showChange(ChangePassDTO changePassDTO, Model model) {
        model.addAttribute("changePassDTO", changePassDTO);
        return "views/user/page/auth/change-password";
    }

    @PostMapping
    public String submitChanegPass(@Valid ChangePassDTO changePassDTO, BindingResult result) {
        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);

        if (result.hasErrors()) {
            return "views/user/page/auth/change-password";
        }

        if (accounts == null) {
            session.setAttribute("toastFailed", "Đổi mật khẩu thất bại!");
            return "redirect:/doi-mat-khau";

        } else if (!encoder.matches(changePassDTO.getPassword0(), accounts.getPasswords())) {
            session.setAttribute("toastFailed", "Mật khẩu cũ không khớp !");
            return "redirect:/doi-mat-khau";

        } else if (changePassDTO.getPassword0().equals(changePassDTO.getPasswords())) {
            session.setAttribute("centerFailed", "Mật khẩu mới không được trùng với mật khẩu cũ !");
            return "redirect:/doi-mat-khau";

        } else {
            String passwordEncore = encoder.encode(changePassDTO.getPasswords());
            accountService.updatePass(accounts.getEmail(), passwordEncore);

            session.setAttribute("centerSuccess", "Đổi mật khẩu thành công, bạn vui lòng đăng nhập lại !");
            session.removeAttribute(SessionAtrr.CURRENT_USER);
            return "redirect:/dang-nhap";
        }
    }
}
