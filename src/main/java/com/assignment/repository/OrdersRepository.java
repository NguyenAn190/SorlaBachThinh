package com.assignment.repository;

import com.assignment.entity.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Orders o JOIN o.cartItem ct JOIN ct.accountsByAccountId a WHERE a.accountId = :accountId")
    Integer sumOrdersPriceByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT COUNT(o) FROM Orders o JOIN o.cartItem ct JOIN ct.accountsByAccountId a WHERE a.accountId = :accountId")
    Integer countOrdersByAccountId(@Param("accountId") Long accountId);

    @Query(value = "SELECT SUM(total_amount) as revenue " + "FROM orders " + "WHERE EXTRACT(YEAR FROM created_at) = :year", nativeQuery = true)
    BigDecimal calculateRevenueForYear(@Param("year") int year);

    @Query("SELECT o from Orders o WHERE YEAR(o.createdAt) = :year")
    List<Orders> getOrdersByCreatedAt_Year(@Param("year") int year);

    @Query("SELECT DISTINCT YEAR(o.createdAt) FROM Orders o")
    List<Integer> findDistinctOrdersByYear();

    @Query("SELECT AVG(totalAmount) FROM Orders")
    List<Integer> calculateAverageRevenue();

    @Query(nativeQuery = true, value = "SELECT p.product_id, b.brand_name, p.product_name, SUM(c.amount) AS totalSold, SUM(o.total_amount) AS totalRevenue " + "FROM orders o " + "JOIN cart_item c ON o.cart_item_id = c.cart_item_id " + "JOIN products p ON c.product_id = p.product_id " + "JOIN brands b ON p.brand_id = b.brand_id " + "WHERE YEAR(o.created_at) = :year " + // Thêm điều kiện lọc theo năm
            "GROUP BY p.product_id " + "ORDER BY totalSold DESC " + "LIMIT 5")
    List<Object[]> findTopSellingProducts(@Param("year") int year);

    List<Orders> findAllByOrderByCreatedAtDesc();

    Page<Orders> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Orders> findByStatusPaymentOrderByCreatedAtDesc(String statusPayment);

    List<Orders> findByStatusOrderOrderByCreatedAtDesc(String statusOrder);

    Orders findByOrderId(long orderId);

    List<Orders> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT o FROM Orders o JOIN o.cartItem ct join ct.accountsByAccountId a WHERE a.accountId = :accountId ORDER BY o.createdAt DESC")
    Page<Orders> findByAccountId(@Param("accountId") Long accountId, Pageable pageable);
}