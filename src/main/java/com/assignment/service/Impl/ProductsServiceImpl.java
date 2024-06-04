package com.assignment.service.Impl;

import com.assignment.entity.Products;
import com.assignment.exception.ProductNotFoundException;
import com.assignment.repository.ProductsRepository;
import com.assignment.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public List<Products> findAll() {
        return productsRepository.findAllByOrderByDateCreatedDesc();
    }

    @Override
    public List<Products> findByProductTypeId(long productTypeId) {
        return productsRepository.findByProductTypeId(productTypeId);
    }

    @Override
    public List<Products> findByCategoryId(Long categoryId) {
        return productsRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Products> findByInvoiceCartItemId(long invoiceCartItemId) {
        return productsRepository.findByInvoiceCartItemId(invoiceCartItemId);
    }

    @Override
    public void save(Products products) {
        productsRepository.save(products);
    }

    @Override
    public Products findByIdAddToCart(String id) {
        return productsRepository.getReferenceById(id);
    }

    @Override
    public Optional<Products> findById(String id) throws ProductNotFoundException {
        Optional<Products> result = productsRepository.findById(id);
        if (result.isPresent()) {
            return Optional.of(productsRepository.findById(id).get());
        } else {
            throw new ProductNotFoundException("Mã sản phẩm không tồn tại vui lòng kiểm tra lại!");
        }
    }

    @Override
    public List<Products> findByProductNameContaining(String search) {
        return productsRepository.findByProductNameContaining(search);
    }

    @Override
    public List<Products> findProductsByCategoryAndTypeName(Long categoryId, Long productTypeId) {
        return productsRepository.findProductsByCategoryAndTypeName(categoryId, productTypeId);
    }

}
