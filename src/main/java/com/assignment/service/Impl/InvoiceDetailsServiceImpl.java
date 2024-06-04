package com.assignment.service.Impl;

import com.assignment.entity.InvoiceDetailts;
import com.assignment.repository.InvoiceDetailtsRepository;
import com.assignment.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {

    @Autowired
    InvoiceDetailtsRepository repo;

    @Override
    public void save(InvoiceDetailts invoiceDetailts) {
        repo.save(invoiceDetailts);
    }
}
