package com.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPassDTO {
    @NotBlank(message = "Vui lòng nhập địa chỉ email !")
    @Email(message = "Địa chỉ email không đúng định dạng !")
    private String email;

    private String verifyCode;

    private String full_name;
}
