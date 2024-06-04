package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "product_images", schema = "solarbachthinhdb", catalog = "")
public class ProductImages {

    @Id
    @Column(name = "image_id", nullable = false)
    private String imageId;

    @Basic
    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;

    @Basic
    @Column(name = "image_path", nullable = false, length = -1)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Products productsByProductId;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImages that = (ProductImages) o;

        if (imageId != that.imageId) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null) return false;

        return true;
    }

    public Products getProductsByProductId() {
        return productsByProductId;
    }

    public void setProductsByProductId(Products productsByProductId) {
        this.productsByProductId = productsByProductId;
    }
}
