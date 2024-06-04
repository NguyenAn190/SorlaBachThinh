package com.assignment.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "account_discount_codes", schema = "solarbachthinhdb", catalog = "")
public class AccountDiscountCodes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic

    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Basic
    @Column(name = "discount_code", nullable = false, length = 10)
    private String discountCode;

    @Basic
    @Column(name = "used_at", nullable = true)
    private Timestamp usedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    @ManyToOne
    @JoinColumn(name = "discount_code", referencedColumnName = "discount_code", nullable = false, insertable = false, updatable = false)
    private DiscountCodes discountCodesByDiscountCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDiscountCodes that = (AccountDiscountCodes) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        if (discountCode != null ? !discountCode.equals(that.discountCode) : that.discountCode != null) return false;
        if (usedAt != null ? !usedAt.equals(that.usedAt) : that.usedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (discountCode != null ? discountCode.hashCode() : 0);
        result = 31 * result + (usedAt != null ? usedAt.hashCode() : 0);
        return result;
    }

    public Accounts getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(Accounts accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }

    public DiscountCodes getDiscountCodesByDiscountCode() {
        return discountCodesByDiscountCode;
    }

    public void setDiscountCodesByDiscountCode(DiscountCodes discountCodesByDiscountCode) {
        this.discountCodesByDiscountCode = discountCodesByDiscountCode;
    }
}
