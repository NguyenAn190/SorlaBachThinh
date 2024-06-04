package com.assignment.service.Impl;

import com.assignment.entity.DiscountCodes;
import com.assignment.repository.DiscountRepository;
import com.assignment.service.DiscountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository repo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<DiscountCodes> findAll() {
        return repo.findAll();
    }

    @Override
    public Page<DiscountCodes> findAll(Pageable pageable) {
        return repo.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public DiscountCodes findById(String discoutID) {
        return repo.getReferenceById(discoutID);
    }

    @Override
    public DiscountCodes insert(DiscountCodes discountCodes) {
        return repo.save(discountCodes);
    }

    @Override
    public DiscountCodes update(DiscountCodes discountCodes) {
        return repo.save(discountCodes);
    }

    @Override
    public DiscountCodes delete(String discountCode) {
        DiscountCodes discountCodes = findById(discountCode);
        discountCodes.setIsActive("Ngưng hoạt động");
        return repo.save(discountCodes);
    }
}
