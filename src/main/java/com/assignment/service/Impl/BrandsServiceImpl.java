package com.assignment.service.Impl;

import com.assignment.entity.Brands;
import com.assignment.exception.BrandsNotFoundException;
import com.assignment.repository.BrandsRepository;
import com.assignment.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandsServiceImpl implements BrandsService {

    @Autowired
    BrandsRepository brandsRepository;

    @Override
    public List<Brands> findAll() {
        return brandsRepository.findAllByOrderByBrandIdDesc();
    }

    @Override
    public void insert(Brands brands) {
        brandsRepository.save(brands);
    }

    @Override
    public Optional<Brands> findById(String id) throws BrandsNotFoundException {
        Optional<Brands> result = brandsRepository.findById(id);
        if (result.isPresent()) {
            return Optional.of(brandsRepository.findById(id).get());
        } else {
            throw new BrandsNotFoundException("Mã thương hiệu không tồn tại vui lòng kiểm tra lại!");
        }
    }

}
