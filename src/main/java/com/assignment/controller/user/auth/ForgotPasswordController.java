package com.assignment.controller.user.auth;

import com.assignment.dto.ForgotPassDTO;
import com.assignment.dto.NewPassDTO;
import com.assignment.entity.Accounts;
import com.assignment.service.AccountService;
import com.assignment.service.EmailService;
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

@Controller
@RequestMapping("quen-mat-khau")
public class ForgotPasswordController {

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

    @Autowired
    EmailService emailService;

    @GetMapping
    public String showForgot(ForgotPassDTO forgotDTO, Model model) {
        model.addAttribute("forgotDTO", forgotDTO);
        return "views/user/page/auth/forgot-password";
    }

    @GetMapping("xac-nhan-otp")
    public String showVerify() {
        return "views/user/page/auth/verify";
    }

    @GetMapping("mat-khau-moi")
    public String showNewPass(NewPassDTO newpassDTO, Model model) {
        model.addAttribute("newPassDTO", newpassDTO);
        return "views/user/page/auth/new-password";
    }

    @PostMapping
    public String submitEmail(@Valid ForgotPassDTO forgotDTO, BindingResult result) {
        Accounts account = accountService.forgotpass(forgotDTO.getEmail());
        String verifyCode = String.valueOf((int) (Math.floor(Math.random() * 899999) + 100000));

        if (result.hasErrors()) {
            return "views/user/page/auth/forgot-password";
        }

        if (account == null) {
            session.setAttribute("toastFailed", "Email không tồn tại !");
            return "redirect:/quen-mat-khau";

        } else if (!account.getAcctive()) {
            session.setAttribute("toastFailed", "Tài khoản bạn đã bị khoá !");
            return "redirect:/quen-mat-khau";

        } else if (!account.getRole().equals("USER")) {
            session.setAttribute("toastWarning", "Không đủ quyền cần thiết !");
            return "redirect:/quen-mat-khau";

        } else {
            long startTime = System.currentTimeMillis();
            session.setAttribute("startTime", startTime);
            session.setAttribute("verifyCode", verifyCode);
            session.setAttribute("full_name", account.getFullname());
            forgotDTO.setFull_name(account.getFullname());
            forgotDTO.setVerifyCode(verifyCode);
            // Lưu email vào phiên làm việc
            session.setAttribute("forgotEmail", forgotDTO.getEmail());

            session.setAttribute("centerSuccess", "Mã code đã được gửi đến email của bạn !");
            emailService.queueForgotEmail(forgotDTO);
            return "redirect:/quen-mat-khau/xac-nhan-otp";
        }
    }

    @PostMapping("xac-nhan-otp")
    public String submitOTP(@RequestParam("otp") String otp) {
        // Lấy thời điểm bắt đầu từ phiên làm việc
        long startTime = (long) session.getAttribute("startTime");

        // Kiểm tra thời gian hiệu lực
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime > 2 * 60 * 1000) { // 2 phút (2 * 60 * 1000 ms)
            // Thời gian đã hết hiệu lực, xử lý tùy theo trường hợp cụ thể
            // Ví dụ: hiển thị thông báo cho người dùng
            session.setAttribute("toastFailed", "Thời gian đã hết hiệu lực.");
            return "redirect:/quen-mat-khau";
        }

        // Lấy verifyCode từ phiên làm việc
        String storedVerifyCode = (String) session.getAttribute("verifyCode");

        // Kiểm tra OTP
        if (otp.equals(storedVerifyCode)) {
            // OTP đúng, chuyển hướng đến trang chính (hoặc trang khác tùy theo yêu cầu)
            return "redirect:/quen-mat-khau/mat-khau-moi";
        } else {
            // OTP không đúng, xử lý tùy theo trường hợp cụ thể
            // Ví dụ: hiển thị thông báo cho người dùng
            session.setAttribute("toastFailed", "Mã OTP không đúng.");
            return "redirect:/quen-mat-khau/xac-nhan-otp";
        }
    }

    @PostMapping("mat-khau-moi")
    public String submitNewPass(@Valid NewPassDTO newpassDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "views/user/page/auth/new-password";
        }

        // Lấy email từ phiên làm việc
        String email = (String) session.getAttribute("forgotEmail");

        // Tiếp tục xử lý với email
        String passwordEncore = encoder.encode(newpassDTO.getPasswords());
        Accounts account = accountService.updatePass(email, passwordEncore);

        if (account != null) {
            session.setAttribute("centerSuccess", "Lấy lại mật khẩu thành công, xin mời bạn đăng nhập !");
            return "redirect:/dang-nhap";
        } else {
            return "redirect:/quen-mat-khau/mat-khau-moi";
        }
    }
}
