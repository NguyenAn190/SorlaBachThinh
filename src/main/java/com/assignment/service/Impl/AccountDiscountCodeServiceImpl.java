package com.assignment.service.Impl;

import com.assignment.entity.AccountDiscountCodes;
import com.assignment.repository.AccountDiscountCodesRepository;
import com.assignment.service.AccountDiscountCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDiscountCodeServiceImpl implements AccountDiscountCodeService {
    private final AccountDiscountCodesRepository repo;

    public AccountDiscountCodeServiceImpl(AccountDiscountCodesRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<AccountDiscountCodes> findAll() {
        return repo.findAll();
    }
}
