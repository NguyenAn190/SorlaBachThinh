package com.assignment.repository;

import com.assignment.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByAccountIdAndProductId(Long accountId, String productId);

    CartItem findByAccountId(Long accountId);

    List<CartItem> findByInvoiceCartItemId(long orderCartItemId);

    @Query("SELECT sc FROM CartItem sc JOIN sc.accountsByAccountId a WHERE a.accountId = :accountId")
    List<CartItem> findListCartByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT COUNT(sc.productId) FROM CartItem sc JOIN sc.accountsByAccountId a WHERE a.accountId = :accountId")
    Integer countByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT SUM(sc.price * sc.amount) FROM CartItem sc JOIN sc.accountsByAccountId a WHERE sc.accountId = :accountId")
    Integer findPriceByAccountId(@Param("accountId") Long accountId);

    List<CartItem> findByCartId(Long cartId);
}