package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "categorys", schema = "solarbachthinhdb", catalog = "")
public class Categorys {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Basic
    @Column(name = "category_name", nullable = true, length = 50)
    private String categoryName;

    @Lob
    @Column(name = "category_image", columnDefinition = "blob")
    private String categoryImage;

    @Basic
    @Column(name = "is_active", nullable = true, length = 50)
    private boolean isActive;

    @OneToMany(mappedBy = "categorysByCategoryId")
    @JsonManagedReference
    private Collection<ProductTypes> productTypesByCategoryId;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

        Categorys that = (Categorys) o;

        if (categoryId != that.categoryId) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (categoryId ^ (categoryId >>> 32));
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        return result;
    }

    public Collection<ProductTypes> getProductTypesByCategoryId() {
        return productTypesByCategoryId;
    }

    public void setProductTypesByCategoryId(Collection<ProductTypes> productTypesByCategoryId) {
        this.productTypesByCategoryId = productTypesByCategoryId;
    }
}
