package com.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
public class RegisterDTO {

    @NotBlank(message = "Vui lòng nhập địa chỉ email !")
    @Email(message = "Địa chỉ email không đúng định dạng !")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu !")
    @Length(min = 6, message = "Mật khẩu có ít nhất là 6 kí tự !")
    private String passwords;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu !")
    private String rpasswords;

    @NotBlank(message = "Vui lòng nhập họ và tên !")
    private String fullname;

    @NotBlank(message = "Vui lòng nhập số điện thoại !")
    @Length(min = 10, max = 10, message = "Số điện thoại phải có 10 chữ số !")
    private String phoneNumber;

    private Timestamp dateCreated;

    private Boolean isAcctive;

    private String role;

    private String token;
}
