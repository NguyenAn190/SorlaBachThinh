package com.assignment.service;

import com.assignment.entity.Products;
import com.assignment.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Products> findAll();

    List<Products> findByProductTypeId(long productTypeId);

    List<Products> findByCategoryId(Long categoryId);

    List<Products> findByInvoiceCartItemId(long invoiceCartItemId);

    void save(Products products);

    Products findByIdAddToCart(String id);

    Optional<Products> findById(String id) throws ProductNotFoundException;

    List<Products> findByProductNameContaining(String search);

    List<Products> findProductsByCategoryAndTypeName(Long categoryId, Long productTypeId);
}
