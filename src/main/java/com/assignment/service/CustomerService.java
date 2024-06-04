package com.assignment.service;

import com.assignment.entity.Customers;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customers> findAll();

    Optional<Customers> findById(String id);

    Customers findByAccountId(Long accountId);

    void save(Customers customers);

    void update(Customers customers);

    void delete(String id);
}
