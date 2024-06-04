package com.assignment.service.Impl;

import com.assignment.entity.AccountDiscountCodes;
import com.assignment.repository.AccountDiscountRepository;
import com.assignment.service.AccountDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDiscountServiceImpl implements AccountDiscountService {

    @Autowired
    AccountDiscountRepository repo;

    @Override
    public List<AccountDiscountCodes> findAll() {
        return repo.findAll();
    }

    @Override
    public AccountDiscountCodes findByDiscountCode(String discountCode, long accountId) {
        return repo.findByDiscountCodeAndAccountId(discountCode, accountId);
    }

    @Override
    public void insert(AccountDiscountCodes discountCodes) {
        repo.save(discountCodes);
    }
}
