package com.assignment.service;

import com.assignment.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    void save(Orders orders);

    Integer sumOrderPrice(Long accountId);

    Integer countOrdersByAccountId(Long accountId);

    Orders findByOrderId(long orderId);

    Page<Orders> findByAccountId(Long accountId, Pageable pageable);

    List<Orders> findAll();

    Page<Orders> findLimit5OrdersList(Pageable pageable);

    List<Orders> findOrderByStatus(String statusPayment);

    List<Orders> findOrderByStatusOrder(String statusOrder);

    List<Orders> findByMonth();

    List<Orders> findOrderByMonth(int month);

    List<Orders> getOrdersInMonth(int year, int month);
}
