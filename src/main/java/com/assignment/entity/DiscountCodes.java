package com.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "discount_codes", schema = "solarbachthinhdb", catalog = "")
public class DiscountCodes {

    @Id
    @Column(name = "discount_code", nullable = false, length = 10)
    private String discountCode;

    @Basic
    @Column(name = "discount_value", nullable = false)
    private int discountValue;

    @Basic
    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Basic
    @Column(name = "is_active", nullable = true)
    private String isActive;

    @OneToMany(mappedBy = "discountCodesByDiscountCode")
    private Collection<AccountDiscountCodes> accountDiscountCodesByDiscountCode;

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountCodes that = (DiscountCodes) o;

        if (discountValue != that.discountValue) return false;
        if (discountCode != null ? !discountCode.equals(that.discountCode) : that.discountCode != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = discountCode != null ? discountCode.hashCode() : 0;
        result = 31 * result + discountValue;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }

    public Collection<AccountDiscountCodes> getAccountDiscountCodesByDiscountCode() {
        return accountDiscountCodesByDiscountCode;
    }

    public void setAccountDiscountCodesByDiscountCode(Collection<AccountDiscountCodes> accountDiscountCodesByDiscountCode) {
        this.accountDiscountCodesByDiscountCode = accountDiscountCodesByDiscountCode;
    }
}
