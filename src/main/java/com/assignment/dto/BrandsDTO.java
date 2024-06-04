package com.assignment.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Collection;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class BrandsDTO {
    private String brandId;

    @NotBlank(message = "Vui lòng nhập tên thương hiệu!")
    private String brandName;

    @Pattern(regexp = "\\b\\d{9,11}\\b", message = "Số điện thoại không hợp lệ!")
    private String phoneNumber;

    @NotBlank(message = "Vui lòng nhập địa chỉ!")
    private String countryOfOrigin;

    private String brandDescription;

    private String websiteUrl;

    private String isStatusDelete;

}
