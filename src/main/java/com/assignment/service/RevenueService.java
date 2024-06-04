package com.assignment.service;

import com.assignment.entity.Orders;

import java.math.BigDecimal;
import java.util.List;

public interface RevenueService {
    BigDecimal calculateRevenueForYear(int year);

    List<Orders> getOrdersByCreatedAt_Year(int year);

    List<Integer> findDistinctOrdersByYear();

    List<Integer> calculateAverageRevenue();

    List<Object[]> findTopSellingProducts(int year);

}
