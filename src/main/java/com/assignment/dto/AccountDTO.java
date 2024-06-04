package com.assignment.dto;

import com.assignment.utils.RandomUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
public class AccountDTO {

    private long accountId;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được trống")
    @Size(max = 100, message = "Email có độ dài tối đa là 100 ký tự")
    private String email;

    @Size(max = 100, message = "Mật khẩu có độ dài tối đa là 100 ký tự")
    private String passwords = RandomUtils.RandomToken(8);

    @NotBlank(message = "Họ và tên không được trống")
    private String fullname;

    @NotBlank(message = "Số điện thoại không được trống")
    @Length(min = 10, max = 10, message = "Số điện thoại phải có 10 chữ số")
    private String phoneNumber = "";

    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    private Boolean isAcctive = true;

    private String role = "USER";
}
