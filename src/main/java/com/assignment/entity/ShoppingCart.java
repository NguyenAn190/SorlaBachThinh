package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "shopping_cart", schema = "solarbachthinhdb", catalog = "")
public class ShoppingCart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cart_id", nullable = false)
    private long cartId;

    @Basic
    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Basic
    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;

    @Basic
    @Column(name = "amount", nullable = false)
    private int amount;

    @Basic
    @Column(name = "price", nullable = false)
    private int price;

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Products productsByProductId;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingCart that = (ShoppingCart) o;

        if (cartId != that.cartId) return false;
        if (accountId != that.accountId) return false;
        if (amount != that.amount) return false;
        if (price != that.price) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (cartId ^ (cartId >>> 32));
        result = 31 * result + (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + price;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    public Accounts getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(Accounts accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }

    public Products getProductsByProductId() {
        return productsByProductId;
    }

    public void setProductsByProductId(Products productsByProductId) {
        this.productsByProductId = productsByProductId;
    }
}
