package com.assignment.service.Impl;

import com.assignment.entity.Orders;
import com.assignment.repository.OrdersRepository;
import com.assignment.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private OrdersRepository ordersRepository;

    public BigDecimal calculateRevenueForYear(int year) {
        return ordersRepository.calculateRevenueForYear(year);
    }

    @Override
    public List<Orders> getOrdersByCreatedAt_Year(int year) {
        return ordersRepository.getOrdersByCreatedAt_Year(year);
    }

    @Override
    public List<Integer> findDistinctOrdersByYear() {
        return ordersRepository.findDistinctOrdersByYear();
    }

    @Override
    public List<Integer> calculateAverageRevenue() {
        return ordersRepository.calculateAverageRevenue();
    }

    @Override
    public List<Object[]> findTopSellingProducts(int year) {
        return ordersRepository.findTopSellingProducts(year);
    }
}
