package com.assignment.service.Impl;

import com.assignment.entity.Accounts;
import com.assignment.entity.HistoryUpdateProducts;
import com.assignment.entity.Products;
import com.assignment.repository.HistoryRepository;
import com.assignment.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository repo;

    @Override
    public List<HistoryUpdateProducts> findAll() {
        return repo.findAllByOrderByUpdateDateDesc();
    }

    @Override
    public Page<HistoryUpdateProducts> findAll(Pageable pageable) {
        return repo.findAllByOrderByUpdateDateDesc(pageable);
    }

    public void addHistoryUpdateProducts(Accounts accounts, String active) {
        try {
            HistoryUpdateProducts historyUpdateProducts = new HistoryUpdateProducts();
            historyUpdateProducts.setAccountId(accounts.getAccountId());
            historyUpdateProducts.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            historyUpdateProducts.setUpdateDescription(active);
            repo.save(historyUpdateProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
