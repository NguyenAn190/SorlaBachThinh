package com.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDTO {
    @NotBlank(message = "Vui lòng nhập địa chỉ email !")
    @Email(message = "Địa chỉ email không đúng định dạng !")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu !")
    @Length(min = 6, message = "Mật khẩu có ít nhất là 6 kí tự !")
    private String passwords;

    private boolean rememberMe;
}
