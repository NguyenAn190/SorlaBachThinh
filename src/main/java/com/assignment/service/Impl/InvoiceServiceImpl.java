package com.assignment.service.Impl;

import com.assignment.entity.Invoice;
import com.assignment.repository.InvoiceRepository;
import com.assignment.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository repo;


    @Override
    public void save(Invoice invoice) {
        repo.save(invoice);
    }
}
