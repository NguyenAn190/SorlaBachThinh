package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "orders", schema = "solarbachthinhdb", catalog = "")
public class Orders {

    @Id
    @Column(name = "order_id", nullable = false)
    private long orderId;

    @Basic
    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Basic
    @Column(name = "full_name", nullable = true, length = 100)
    private String fullName;

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Basic
    @Column(name = "phone_number", nullable = true, length = 20)
    private String phoneNumber;

    @Basic
    @Column(name = "province", nullable = true, length = 50)
    private String province;

    @Basic
    @Column(name = "district", nullable = true, length = 50)
    private String district;

    @Basic
    @Column(name = "village", nullable = true, length = 50)
    private String village;

    @Basic
    @Column(name = "address", nullable = true, length = -1)
    private String address;

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Basic
    @Column(name = "category-payment", nullable = true, length = 150)
    private String categoryPayment;

    @Basic
    @Column(name = "status-payment", nullable = true, length = 150)
    private String statusPayment;

    @Basic
    @Column(name = "status-order", nullable = true, length = 150)
    private String statusOrder;

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description;

    @Basic
    @Column(name = "cart_item_id", nullable = true)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_item_id", referencedColumnName = "cart_item_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private CartItem cartItem;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategoryPayment() {
        return categoryPayment;
    }

    public void setCategoryPayment(String categoryPayment) {
        this.categoryPayment = categoryPayment;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders that = (Orders) o;

        if (orderId != that.orderId) return false;
        if (totalAmount != that.totalAmount) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (village != null ? !village.equals(that.village) : that.village != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + totalAmount;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (village != null ? village.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
