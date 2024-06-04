package com.assignment.repository;

import com.assignment.entity.AccountDiscountCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDiscountRepository extends JpaRepository<AccountDiscountCodes, Long> {

    AccountDiscountCodes findByDiscountCodeAndAccountId(String discountCode, long accountId);
}
