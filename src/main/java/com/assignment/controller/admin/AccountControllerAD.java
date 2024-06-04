package com.assignment.controller.admin;

import com.assignment.dto.AccountDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Customers;
import com.assignment.service.AccountService;
import com.assignment.service.CustomerService;
import com.assignment.utils.EncodeUtils;
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
@RequestMapping("admin")
public class AccountControllerAD {

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
    HttpSession session;

    @GetMapping("tai-khoan")
    public String dataAccount(Model model) {
        List<Accounts> accounts = accountService.findAll();

        for (Accounts account : accounts) {
            String encodedPhone = EncodeUtils.encodePhoneNumber(account.getPhoneNumber());
            account.setPhoneNumber(encodedPhone);
        }

        model.addAttribute("accounts", accountService.findAll());
        return "views/admin/page/views/accounts-list";
    }

    @GetMapping("them-tai-khoan")
    public String account_add(Model model) {
        model.addAttribute("accountDTO", new AccountDTO());
        return "views/admin/page/crud/account/account-add";
    }

    @GetMapping("sua-tai-khoan/{id}")
    public String account_edit(@PathVariable("id") int id, Model model) {
        Accounts accounts = accountService.findById(id);

        String encodedPassword = encoder.encode(accounts.getPasswords());
        accounts.setPasswords(encodedPassword);

        String encodedPhone = EncodeUtils.encodePhoneNumber(accounts.getPhoneNumber());
        accounts.setPhoneNumber(encodedPhone);

        model.addAttribute("account", accounts);
        return "views/admin/page/crud/account/account-edit";
    }

    @GetMapping("xoa-tai-khoan/{id}")
    public String account_delete(@PathVariable("id") int id) {
        Accounts accounts = accountService.findById(id);
        accounts.setAcctive(Boolean.FALSE);
        accountService.save(accounts);
        session.setAttribute("toastSuccess", "Xoá tài khoản thành công !");
        return "redirect:/admin/tai-khoan";
    }

    @PostMapping("them-tai-khoan")
    public String accountPostAdd(@RequestParam("role") String role, @Valid AccountDTO accountDTO, BindingResult result) {
        String encodedPassword = encoder.encode(accountDTO.getPasswords());

        if (result.hasErrors()) {
            session.setAttribute("toastWarning", "Thêm mới thất bại !");
            return "views/admin/page/crud/account/account-add";
        }

        if (accountService.findByEmail(accountDTO.getEmail()) != null) {
            session.setAttribute("toastFailed", "Email đã tồn tại !");
            return "redirect:/admin/them-tai-khoan";
        }

        if (accountService.findByPhone(accountDTO.getPhoneNumber()) != null) {
            session.setAttribute("toastFailed", "Số điện thoại đã tồn tại !");
            return "redirect:/admin/them-tai-khoan";
        }

        accountDTO.setPasswords(encodedPassword);
        accountService.add(accountDTO, role);

        Accounts accounts = accountService.findByEmail(accountDTO.getEmail());
        Customers customer = new Customers();
        customer.setCustomerId(customerIdValue());
        customer.setAccountId(accounts.getAccountId());
        customer.setGender(accountDTO.getIsAcctive());

        customerService.save(customer);
        session.setAttribute("toastSuccess", "Thêm tài khoản thành công !");
        return "redirect:/admin/tai-khoan";
    }

    @PostMapping("sua-tai-khoan")
    public String account_update(@RequestParam("active-name") Boolean active, @Valid AccountDTO accountDTO) {
        accountService.update(accountDTO, active);
        session.setAttribute("toastSuccess", "Cập nhật tài khoản thành công !");
        return "redirect:/admin/tai-khoan";
    }

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