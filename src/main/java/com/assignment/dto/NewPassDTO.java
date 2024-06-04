package com.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewPassDTO {
    @NotBlank(message = "Vui lòng nhập mật khẩu !")
    @Length(min = 6, message = "Mật khẩu có ít nhất là 6 kí tự !")
    private String passwords;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu !")
    private String rpasswords;
}
