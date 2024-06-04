package com.assignment.service.Impl;

import com.assignment.entity.ShoppingCart;
import com.assignment.repository.ShoppingCartsRepository;
import com.assignment.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartsRepository repo;

    @Override
    public List<ShoppingCart> findAll() {
        return repo.findAll();
    }

    @Override
    public List<ShoppingCart> findListCartByAccountId(Long accountId) {
        return repo.findListCartByAccountId(accountId);
    }

    @Override
    public List<ShoppingCart> findListByCartId(Long cartId) {
        return repo.findByCartId(cartId);
    }

    @Override
    public ShoppingCart findCartByCardId(Long cartId) {
        return repo.getReferenceById(cartId);
    }

    @Override
    public ShoppingCart findCartByAccountId(Long accountId) {
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
        ShoppingCart cart = repo.findById(cartId).orElse(null);
        if (cart != null) {
            System.out.println(cart.getPrice());
            return cart.getPrice();
        }
        return 0;
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        repo.save(shoppingCart);
    }

    @Override
    public ShoppingCart updateAmount(Long cardId, Integer amount) {
        ShoppingCart cart = repo.findById(cardId).orElse(null);
        if (cart != null) {
            cart.setAmount(amount);
            repo.save(cart);
        }
        return cart;
    }

    @Override
    public void delete(Long cardId) {
        ShoppingCart cart = repo.getReferenceById(cardId);
        repo.delete(cart);
    }

    @Override
    public void deleteAll(List<ShoppingCart> shoppingCarts) {
        repo.deleteAll(shoppingCarts);
    }

    @Override
    public ShoppingCart findProductExits(Long accountId, String productId) {
        return repo.findByAccountIdAndProductId(accountId, productId);
    }
}
