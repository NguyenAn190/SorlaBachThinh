package com.assignment.service.Impl;

import com.assignment.entity.Orders;
import com.assignment.repository.OrdersRepository;
import com.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersRepository repo;

    @Override
    public void save(Orders orders) {
        repo.save(orders);
    }

    @Override
    public Integer sumOrderPrice(Long accountId) {
        return repo.sumOrdersPriceByAccountId(accountId);
    }

    @Override
    public Integer countOrdersByAccountId(Long accountId) {
        return repo.countOrdersByAccountId(accountId);
    }

    @Override
    public Orders findByOrderId(long orderId) {
        return repo.findByOrderId(orderId);
    }

    @Override
    public Page<Orders> findByAccountId(Long accountId, Pageable pageable) {
        return repo.findByAccountId(accountId, pageable);
    }

    @Override
    public List<Orders> findAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Page<Orders> findLimit5OrdersList(Pageable pageable) {
        return repo.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public List<Orders> findOrderByStatus(String statusPayment) {
        return repo.findByStatusPaymentOrderByCreatedAtDesc(statusPayment);
    }

    @Override
    public List<Orders> findOrderByStatusOrder(String statusOrder) {
        return repo.findByStatusOrderOrderByCreatedAtDesc(statusOrder);
    }

    @Override
    public List<Orders> findByMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return repo.findByCreatedAtBetween(startOfMonth, endOfMonth);
    }

    @Override
    public List<Orders> findOrderByMonth(int month) {
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();

        LocalDateTime startOfMonth = LocalDateTime.of(currentYear, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(currentYear, month, Year.of(currentYear).isLeap() ? 29 : 28, 23, 59, 59);

        return repo.findByCreatedAtBetween(startOfMonth, endOfMonth);
    }

    @Override
    public List<Orders> getOrdersInMonth(int year, int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59);

        return repo.findByCreatedAtBetween(startOfMonth, endOfMonth);
    }
}
