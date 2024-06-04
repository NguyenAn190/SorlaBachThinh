package com.assignment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    private String productId;

    @NotNull(message = "Vui lòng chọn loại sản phẩm!")
    private Long productTypeId;

    @NotEmpty(message = "Vui lòng chọn thương hiệu!")
    private String brandId;

    private Long historyUpdateProductId;

    @NotEmpty(message = "Vui lòng nhập công suất!")
    private String power;

    @NotBlank(message = "Vui lòng nhập tên sản phẩm!")
    private String productName;

    private Integer price;

    @NotNull(message = "Vui lòng nhập số lượng!")
    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer amount;

    private Integer saleOff;

    private String descriptions;

    private Timestamp dateCreated;

    private String templateDescription;

    private String isStatusDelete;

    @NotEmpty(message = "Vui lòng nhập thời gian bảo hành")
    private String warranty;

}
