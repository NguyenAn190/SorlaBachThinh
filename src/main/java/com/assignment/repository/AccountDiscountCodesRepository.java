package com.assignment.repository;

import com.assignment.entity.AccountDiscountCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDiscountCodesRepository extends JpaRepository<AccountDiscountCodes, Long> {
}