package com.assignment.service;

import com.assignment.entity.AccountDiscountCodes;

import java.util.List;

public interface AccountDiscountService {

    List<AccountDiscountCodes> findAll();

    AccountDiscountCodes findByDiscountCode(String discountCode, long accountId);

    void insert(AccountDiscountCodes discountCodes);
}
