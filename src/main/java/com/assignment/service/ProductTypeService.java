package com.assignment.service;

import com.assignment.dto.ProductTypeDTO;
import com.assignment.entity.ProductTypes;

import java.util.List;

public interface ProductTypeService {
    List<ProductTypes> findAll();

    ProductTypes findById(Long id);

    void insert(ProductTypeDTO productTypeDTO, Long categoryId);

    ProductTypes update(ProductTypeDTO productTypeDTO, Long categoryId);

    void delete(Long id);

    List<ProductTypes> findByCategoryId(Long categoryId);

}
