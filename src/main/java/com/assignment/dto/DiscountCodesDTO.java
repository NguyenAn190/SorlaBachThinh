package com.assignment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DiscountCodesDTO {

    @NotEmpty(message = "Vui lòng thêm mã giảm giá!")
    private String discountCode;

    @NotNull(message = "Vui lòng thêm giá giảm!")
    private int discountPrice;

    @NotNull(message = "Vui lòng thêm số lượng mã giảm giá!")
    private int discountValue;

    @NotNull(message = "Vui lòng thêm thời gian tạo mã!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @NotNull(message = "Vui lòng thêm số thời gian hết hạn")
    @Future(message = "Vui lòng nhập ngày trong tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expirationDate;

    private String isActive;
}
