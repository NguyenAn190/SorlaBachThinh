package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "history_update_products", schema = "solarbachthinhdb", catalog = "")
public class HistoryUpdateProducts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "history_update_product_id", nullable = false)
    private long historyUpdateProductId;

    @Basic
    @Column(name = "account_id", nullable = true, length = 20)
    private long accountId;

    @Basic
    @Column(name = "update_date", nullable = true)
    private Timestamp updateDate;

    @Basic
    @Column(name = "update_description", nullable = true, length = -1)
    private String updateDescription;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private Products productsByProductId;

    public long getHistoryUpdateProductId() {
        return historyUpdateProductId;
    }

    public void setHistoryUpdateProductId(long historyUpdateProductId) {
        this.historyUpdateProductId = historyUpdateProductId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }


    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryUpdateProducts that = (HistoryUpdateProducts) o;

        if (historyUpdateProductId != that.historyUpdateProductId) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (updateDescription != null ? !updateDescription.equals(that.updateDescription) : that.updateDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (historyUpdateProductId ^ (historyUpdateProductId >>> 32));
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (updateDescription != null ? updateDescription.hashCode() : 0);
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
