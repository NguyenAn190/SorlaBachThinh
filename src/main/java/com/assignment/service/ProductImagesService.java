package com.assignment.service;

import com.assignment.entity.ProductImages;
import com.assignment.entity.Products;
import com.assignment.exception.ProductImageNotFoundException;

import java.util.List;

public interface ProductImagesService {
    void insert(ProductImages productImages);
    List<ProductImages> findAll() throws ProductImageNotFoundException;
    List<ProductImages> findImgByProductId(String id) throws ProductImageNotFoundException;
}
