package com.assignment.repository;

import com.assignment.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartsRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByAccountIdAndProductId(Long accountId, String productId);

    ShoppingCart findByAccountId(Long accountId);

    @Query("SELECT sc FROM ShoppingCart sc JOIN sc.accountsByAccountId a WHERE a.accountId = :accountId")
    List<ShoppingCart> findListCartByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT COUNT(sc.productId) FROM ShoppingCart sc JOIN sc.accountsByAccountId a WHERE a.accountId = :accountId")
    Integer countByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT SUM(sc.price * sc.amount) FROM ShoppingCart sc JOIN sc.accountsByAccountId a WHERE sc.accountId = :accountId")
    Integer findPriceByAccountId(@Param("accountId") Long accountId);

    List<ShoppingCart> findByCartId(Long cartId);
}
