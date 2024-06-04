package com.assignment.service.Impl;

import com.assignment.entity.Customers;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Override
    public List<Customers> findAll() {
        return repo.findAllByOrderByCustomerIdDesc();
    }

    @Override
    public Optional<Customers> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public Customers findByAccountId(Long accountId) {
        return repo.findByAccountId(accountId);
    }

    @Override
    public void save(Customers customers) {
        repo.save(customers);
    }

    @Override
    public void update(Customers customers) {
        repo.save(customers);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
