package com.assignment.repository;

import com.assignment.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, String> {

    List<Customers> findAllByOrderByCustomerIdDesc();

    Customers findByAccountId(Long accountId);
}
