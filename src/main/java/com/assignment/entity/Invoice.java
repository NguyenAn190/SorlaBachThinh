package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Invoice {

    @Id
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Basic
    @Column(name = "order_id", nullable = true)
    private Long orderId;

    @Basic
    @Column(name = "pay-date", nullable = true)
    private Timestamp payDate;

    @OneToMany(mappedBy = "invoiceByInvoiceId")
    @JsonManagedReference
    private Collection<InvoiceDetailts> InvoiceDetailts;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Orders ordersByOrderId;

    public Collection<com.assignment.entity.InvoiceDetailts> getInvoiceDetailts() {
        return InvoiceDetailts;
    }

    public void setInvoiceDetailts(Collection<com.assignment.entity.InvoiceDetailts> invoiceDetailts) {
        InvoiceDetailts = invoiceDetailts;
    }

    public Orders getOrdersByOrderId() {
        return ordersByOrderId;
    }

    public void setOrdersByOrderId(Orders ordersByOrderId) {
        this.ordersByOrderId = ordersByOrderId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice invoice = (Invoice) o;

        if (invoiceId != invoice.invoiceId) return false;
        if (orderId != null ? !orderId.equals(invoice.orderId) : invoice.orderId != null) return false;
        if (payDate != null ? !payDate.equals(invoice.payDate) : invoice.payDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceId;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (payDate != null ? payDate.hashCode() : 0);
        return result;
    }
}
