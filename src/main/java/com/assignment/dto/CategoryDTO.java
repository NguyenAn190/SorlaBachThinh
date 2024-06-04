package com.assignment.dto;

import com.assignment.entity.Categorys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private long categoryId;

    private String categoryName;

    private String categoryImage;

    private boolean active;

    public CategoryDTO(Categorys categorys) {
        this.categoryId = categorys.getCategoryId();
        this.categoryName = categorys.getCategoryName();
        this.categoryImage = categorys.getCategoryImage();
    }
}
