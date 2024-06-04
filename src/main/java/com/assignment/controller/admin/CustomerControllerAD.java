package com.assignment.controller.admin;

import com.assignment.dto.CustomerDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Customers;
import com.assignment.exception.ProductNotFoundException;
import com.assignment.service.AccountService;
import com.assignment.service.CustomerService;
import com.assignment.utils.EncodeUtils;
import com.assignment.utils.EntityDtoUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class CustomerControllerAD {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("khach-hang")
    public String dataCustomer(Model model) {
        List<Customers> customers = customerService.findAll();

        for (Customers customer : customers) {
            String encodedPhone = EncodeUtils.encodePhoneNumber(customer.getAccountsByAccountId().getPhoneNumber());
            customer.getAccountsByAccountId().setPhoneNumber(encodedPhone);
        }

        model.addAttribute("customers", customerService.findAll());
        return "views/admin/page/views/customers-list";
    }

    @GetMapping("them-khach-hang")
    public String customer_add(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "views/admin/page/crud/customer/customer-add";
    }

    @PostMapping("them-khach-hang")
    public String PostCustomer_add(@Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            session.setAttribute("toastWarning", "Thêm thất bại !");
            return "views/admin/page/crud/customer/customer-add";
        } else {
            customerDTO.setCustomerId(customerIdValue());
            Customers save = EntityDtoUtils.convertToEntity(customerDTO, Customers.class);

            customerService.save(save);
            session.setAttribute("toastSuccess", "Thêm thành công !");
            return "redirect:/admin/khach-hang";
        }
    }

    @GetMapping("sua-khach-hang/{id}")
    public String getCustomer_edit(@PathVariable("id") String id, Model model) {
        Optional<Customers> customers = customerService.findById(id);
        if (customers.isPresent()) {
            model.addAttribute("customerDTO", EntityDtoUtils.convertToDto(customers.get(), CustomerDTO.class));
            model.addAttribute("customers", customers.get());
            return "views/admin/page/crud/customer/customer-edit";
        } else {
            // Xử lý trường hợp không tìm thấy khách hàng
            return "redirect:/admin/khach-hang";
        }
    }

    @PostMapping("sua-khach-hang/{id}")
    public String customer_edit(@PathVariable("id") String id, @Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO, BindingResult bindingResult, Model model) throws ProductNotFoundException {
        if (bindingResult.hasErrors()) {
            session.setAttribute("toastWarning", "Cập hàng thất bại !");
            return "views/admin/page/crud/customer/customer-edit";
        } else {
            customerDTO.setCustomerId(id);
            Customers save = EntityDtoUtils.convertToEntity(customerDTO, Customers.class);

            customerService.save(save);
            session.setAttribute("toastSuccess", "Cập nhật thành công !");
            return "redirect:/admin/khach-hang";
        }
    }

    @GetMapping("xoa-khach-hang/{id}")
    public String deleteProducts(@PathVariable("id") String id) {
        Optional<Customers> optionalCustomers = Optional.of(customerService.findById(id).get());

        if (optionalCustomers.isPresent()) {
            Customers customers = optionalCustomers.get();
            customerService.delete(customers.getCustomerId());
            session.setAttribute("toastSuccess", "Xóa thành công !");
            return "redirect:/admin/khach-hang";
        } else {
            return "lỗi";
        }
    }

    @ModelAttribute("listAccount")
    public List<Accounts> listAcount() {
        List<Accounts> accountsList = accountService.findAll();
        return accountsList;
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
