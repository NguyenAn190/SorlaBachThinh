package com.assignment.service;


import com.assignment.entity.DiscountCodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountService {
    List<DiscountCodes> findAll();

    Page<DiscountCodes> findAll(Pageable pageable);

    DiscountCodes findById(String discountID);

    DiscountCodes insert(DiscountCodes discountCodes);

    DiscountCodes update(DiscountCodes discountCodes);

    DiscountCodes delete(String discountCode);
}
