package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "product_types", schema = "solarbachthinhdb", catalog = "")
public class ProductTypes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_type_id", nullable = false)
    private long productTypeId;

    @Basic
    @Column(name = "category_id", nullable = true)
    private Long categoryId;

    @Basic
    @Column(name = "product_type_name", nullable = true, length = 50)
    private String productTypeName;

    @Basic
    @Column(name = "is_active", nullable = true, length = 50)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Categorys categorysByCategoryId;

    @OneToMany(mappedBy = "productTypesByProductTypeId")
    private Collection<Products> productsByProductTypeId;

    public long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTypes that = (ProductTypes) o;

        if (productTypeId != that.productTypeId) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (productTypeName != null ? !productTypeName.equals(that.productTypeName) : that.productTypeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (productTypeId ^ (productTypeId >>> 32));
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (productTypeName != null ? productTypeName.hashCode() : 0);
        return result;
    }

    public Categorys getCategorysByCategoryId() {
        return categorysByCategoryId;
    }

    public void setCategorysByCategoryId(Categorys categorysByCategoryId) {
        this.categorysByCategoryId = categorysByCategoryId;
    }

    public Collection<Products> getProductsByProductTypeId() {
        return productsByProductTypeId;
    }

    public void setProductsByProductTypeId(Collection<Products> productsByProductTypeId) {
        this.productsByProductTypeId = productsByProductTypeId;
    }
}
