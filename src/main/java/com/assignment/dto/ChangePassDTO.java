package com.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePassDTO {
    @NotBlank(message = "Vui lòng nhập mật khẩu cũ!")
    @Length(min = 6, message = "Mật khẩu có ít nhất là 6 kí tự !")
    private String password0;

    @NotBlank(message = "Vui lòng nhập mật khẩu mới!")
    @Length(min = 6, message = "Mật khẩu có ít nhất là 6 kí tự !")
    private String passwords;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu !")
    private String rpasswords;
}
