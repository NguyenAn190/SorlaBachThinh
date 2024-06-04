package com.assignment.service;

import com.assignment.entity.Accounts;
import com.assignment.entity.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> findAll();

    List<CartItem> findListCartByAccountId(Long accountId);

    List<CartItem> findListByCartId(Long cartId);

    List<CartItem> findByInvoiceCartItemId(long orderCartItemId);

    CartItem findCartByCardId(Long cartId);

    CartItem findCartByAccountId(Long accountId);

    Integer findPriceByAcountId(Long accountId);

    Integer countByAccountId(Long accountId);

    Integer getUpdatedPrice(Long cartId);

    void save(CartItem cartItem);

    void saveAll(List<CartItem> cartItem);

    void updateAmount(Long cardId, Integer amount);

    void delete(Long cardId);

    void deleteProductByAccountId(Long accountId);

    CartItem findProductExits(Long accountId, String productId);
}
