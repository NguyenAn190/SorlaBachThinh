package com.assignment.service.Impl;

import com.assignment.entity.ProductImages;
import com.assignment.repository.ProductImageRepository;
import com.assignment.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImagesImpl implements ProductImagesService {

    @Autowired
    ProductImageRepository productImageRepository;
    @Override
    public void insert(ProductImages productImages) {
       productImageRepository.save(productImages);
    }

    @Override
    public List<ProductImages> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public List<ProductImages> findImgByProductId(String id) {
        return productImageRepository.findByProductId(id);
    }
}
