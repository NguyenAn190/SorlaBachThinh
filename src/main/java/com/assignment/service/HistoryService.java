package com.assignment.service;

import com.assignment.entity.Accounts;
import com.assignment.entity.HistoryUpdateProducts;
import com.assignment.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {
    List<HistoryUpdateProducts> findAll();

    Page<HistoryUpdateProducts> findAll(Pageable pageable);

    void addHistoryUpdateProducts(Accounts accounts, String active);

}
