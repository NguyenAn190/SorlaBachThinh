package com.assignment.dto;

import com.assignment.entity.Categorys;
import com.assignment.entity.ProductTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeDTO {

    private long productTypeId;

    private Long categoryId;

    private String productTypeName;

    private boolean active;

    private Categorys categorysByCategoryId;

    public ProductTypeDTO(ProductTypes productTypes) {
        this.productTypeId = productTypes.getProductTypeId();
        this.categoryId = productTypes.getCategoryId();
        this.productTypeName = productTypes.getProductTypeName();
        this.categorysByCategoryId = productTypes.getCategorysByCategoryId();
    }
}
