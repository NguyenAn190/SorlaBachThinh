package com.assignment.service;

import com.assignment.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> findAll();

    List<ShoppingCart> findListCartByAccountId(Long accountId);

    List<ShoppingCart> findListByCartId(Long cartId);

    ShoppingCart findCartByCardId(Long cartId);

    ShoppingCart findCartByAccountId(Long accountId);

    Integer findPriceByAcountId(Long accountId);

    Integer countByAccountId(Long accountId);

    Integer getUpdatedPrice(Long cartId);

    void save(ShoppingCart shoppingCart);

    ShoppingCart updateAmount(Long cardId, Integer amount);

    void delete(Long cardId);

    void deleteAll(List<ShoppingCart> shoppingCarts);

    ShoppingCart findProductExits(Long accountId, String productId);
}
