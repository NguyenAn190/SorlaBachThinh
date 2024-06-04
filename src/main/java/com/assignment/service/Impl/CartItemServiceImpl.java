package com.assignment.service.Impl;

import com.assignment.entity.CartItem;
import com.assignment.repository.CartItemRepository;
import com.assignment.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository repo;

    @Override
    public List<CartItem> findAll() {
        return repo.findAll();
    }

    @Override
    public List<CartItem> findListCartByAccountId(Long accountId) {
        return repo.findListCartByAccountId(accountId);
    }

    @Override
    public List<CartItem> findByInvoiceCartItemId(long orderCartItemId) {
        return repo.findByInvoiceCartItemId(orderCartItemId);
    }

    @Override
    public List<CartItem> findListByCartId(Long cartId) {
        return repo.findByCartId(cartId);
    }

    @Override
    public CartItem findCartByCardId(Long cartId) {
        return repo.getReferenceById(cartId);
    }

    @Override
    public CartItem findCartByAccountId(Long accountId) {
        return repo.findByAccountId(accountId);
    }

    @Override
    public Integer findPriceByAcountId(Long accountId) {
        if (repo.findPriceByAccountId(accountId) != null) {
            return repo.findPriceByAccountId(accountId);
        } else {
            return 0;
        }
    }

    @Override
    public Integer countByAccountId(Long accountId) {
        return repo.countByAccountId(accountId);
    }

    @Override
    public Integer getUpdatedPrice(Long cartId) {
        CartItem cart = repo.findById(cartId).orElse(null);
        if (cart != null) {
            System.out.println(cart.getPrice());
            return cart.getPrice();
        }
        return 0;
    }

    @Override
    public void save(CartItem shoppingCart) {
        repo.save(shoppingCart);
    }

    @Override
    public void saveAll(List<CartItem> cartItem) {
        repo.saveAll(cartItem);
    }

    @Override
    public void updateAmount(Long cardId, Integer amount) {
        CartItem cart = repo.findById(cardId).orElse(null);
        if (cart != null) {
            cart.setAmount(amount);
            repo.save(cart);
        }
    }

    @Override
    public void delete(Long cardId) {
        CartItem cart = repo.getReferenceById(cardId);
        repo.delete(cart);
    }

    @Override
    public void deleteProductByAccountId(Long accountId) {
        List<CartItem> cart = findListCartByAccountId(accountId);
        for (CartItem shoppingCart : cart) {
        }
        repo.saveAll(cart);
    }

    @Override
    public CartItem findProductExits(Long accountId, String productId) {
        return repo.findByAccountIdAndProductId(accountId, productId);
    }
}
