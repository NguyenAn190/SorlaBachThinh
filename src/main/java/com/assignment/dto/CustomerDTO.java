package com.assignment.dto;

import com.assignment.entity.Accounts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Data
public class CustomerDTO {

    private String customerId;

    @NotNull(message = "Vui lòng chọn mã tài khoản")
    private long accountId;

    @NotBlank(message = "Vui lòng chọn tỉnh thành")
    private String province;

    @NotBlank(message = "Vui lòng chọn Quận/Huyện")
    private String district;

    @NotBlank(message = "Vui lòng chọn Phường/Xã")
    private String village;

    @NotBlank(message = "Địa chỉ không được trống")
    private String address;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Boolean gender;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @NotNull(message = "Vui lòng chọn ngày sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    private Accounts accountsByAccountId;
}
