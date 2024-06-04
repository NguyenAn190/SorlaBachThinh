package com.assignment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_detailts", schema = "solarbachthinhdb", catalog = "")
public class InvoiceDetailts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoice_detailts_id", nullable = false)
    private int invoiceDetailtsId;

    @Basic
    @Column(name = "invoice_id", nullable = true)
    private Integer invoiceId;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", nullable = false, insertable = false, updatable = false)
    private Invoice invoiceByInvoiceId;

    public int getInvoiceDetailtsId() {
        return invoiceDetailtsId;
    }

    public void setInvoiceDetailtsId(int invoiceDetailtsId) {
        this.invoiceDetailtsId = invoiceDetailtsId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceDetailts that = (InvoiceDetailts) o;

        if (invoiceDetailtsId != that.invoiceDetailtsId) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceDetailtsId;
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        return result;
    }

    public Invoice getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(Invoice invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }
}
