package com.assignment.service;

import com.assignment.entity.Brands;
import com.assignment.exception.BrandsNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BrandsService {

    List<Brands> findAll();

    void insert(Brands brands);

    Optional<Brands> findById(String id) throws BrandsNotFoundException;

}
