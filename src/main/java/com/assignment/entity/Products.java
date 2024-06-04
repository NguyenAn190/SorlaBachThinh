package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "products", schema = "solarbachthinhdb", catalog = "")
public class Products {
    @Id
    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;

    @Basic
    @Column(name = "product_type_id", nullable = true)
    private Long productTypeId;

    @Basic
    @Column(name = "brand_id", nullable = true, length = 20)
    private String brandId;

    @Basic
    @Column(name = "history_update_product_id", nullable = true)
    private Long historyUpdateProductId;

    @Basic
    @Column(name = "power", nullable = true)
    private String power;

    @Basic
    @Column(name = "product_name", nullable = true, length = 100)
    private String productName;

    @Basic
    @Column(name = "price", nullable = true)
    private Integer price;

    @Basic
    @Column(name = "amount", nullable = true)
    private Integer amount;

    @Basic
    @Column(name = "sale_off", nullable = true)
    private Integer saleOff;

    @Basic
    @Column(name = "descriptions", nullable = true, length = -1)
    private String descriptions;

    @Basic
    @Column(name = "template_description", nullable = true, length = -1)
    private String templateDescription;

    @Basic
    @Column(name = "date_created", nullable = true)
    private Timestamp dateCreated;

    @Basic
    @Column(name = "is_status_delete", nullable = true)
    private String isStatusDelete;

    @Basic
    @Column(name = "warranty", nullable = true, length = 20)
    private String warranty;

    @OneToMany(mappedBy = "productsByProductId")
    @JsonManagedReference
    private Collection<HistoryUpdateProducts> historyUpdateProductsByProductId;

    @OneToMany(mappedBy = "productsByProductId")
    @JsonManagedReference
    private Collection<ProductImages> productImagesByProductId;

    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id", insertable = false, updatable = false)
    @JsonBackReference
    private ProductTypes productTypesByProductTypeId;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false)
    @JsonBackReference
    private Brands brandsByBrandId;

    @OneToMany(mappedBy = "productsByProductId")
    @JsonManagedReference
    private Collection<ShoppingCart> shoppingCartsByProductId;

    @OneToMany(mappedBy = "productsByProductId")
    @JsonManagedReference
    private Collection<CartItem> cartItemsByProductId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Long getHistoryUpdateProductId() {
        return historyUpdateProductId;
    }

    public void setHistoryUpdateProductId(Long historyUpdateProductId) {
        this.historyUpdateProductId = historyUpdateProductId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(Integer saleOff) {
        this.saleOff = saleOff;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String getStatusDelete() {
        return isStatusDelete;
    }

    public void setStatusDelete(String statusDelete) {
        isStatusDelete = statusDelete;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Products that = (Products) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (productTypeId != null ? !productTypeId.equals(that.productTypeId) : that.productTypeId != null)
            return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (historyUpdateProductId != null ? !historyUpdateProductId.equals(that.historyUpdateProductId) : that.historyUpdateProductId != null)
            return false;
        if (power != null ? !power.equals(that.power) : that.power != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (saleOff != null ? !saleOff.equals(that.saleOff) : that.saleOff != null) return false;
        if (descriptions != null ? !descriptions.equals(that.descriptions) : that.descriptions != null) return false;
        if (templateDescription != null ? !templateDescription.equals(that.templateDescription) : that.templateDescription != null)
            return false;
        if (isStatusDelete != null ? !isStatusDelete.equals(that.isStatusDelete) : that.isStatusDelete != null)
            return false;
        if (warranty != null ? !warranty.equals(that.warranty) : that.warranty != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (productTypeId != null ? productTypeId.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (historyUpdateProductId != null ? historyUpdateProductId.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (saleOff != null ? saleOff.hashCode() : 0);
        result = 31 * result + (descriptions != null ? descriptions.hashCode() : 0);
        result = 31 * result + (templateDescription != null ? templateDescription.hashCode() : 0);
        result = 31 * result + (isStatusDelete != null ? isStatusDelete.hashCode() : 0);
        result = 31 * result + (warranty != null ? warranty.hashCode() : 0);
        return result;
    }

    public Collection<HistoryUpdateProducts> getHistoryUpdateProductsByProductId() {
        return historyUpdateProductsByProductId;
    }

    public void setHistoryUpdateProductsByProductId(Collection<HistoryUpdateProducts> historyUpdateProductsByProductId) {
        this.historyUpdateProductsByProductId = historyUpdateProductsByProductId;
    }

    public Collection<ProductImages> getProductImagesByProductId() {
        return productImagesByProductId;
    }

    public void setProductImagesByProductId(Collection<ProductImages> productImagesByProductId) {
        this.productImagesByProductId = productImagesByProductId;
    }

    public ProductTypes getProductTypesByProductTypeId() {
        return productTypesByProductTypeId;
    }

    public void setProductTypesByProductTypeId(ProductTypes productTypesByProductTypeId) {
        this.productTypesByProductTypeId = productTypesByProductTypeId;
    }

    public Brands getBrandsByBrandId() {
        return brandsByBrandId;
    }

    public void setBrandsByBrandId(Brands brandsByBrandId) {
        this.brandsByBrandId = brandsByBrandId;
    }

    public Collection<ShoppingCart> getShoppingCartsByProductId() {
        return shoppingCartsByProductId;
    }

    public void setShoppingCartsByProductId(Collection<ShoppingCart> shoppingCartsByProductId) {
        this.shoppingCartsByProductId = shoppingCartsByProductId;
    }

    public String getIsStatusDelete() {
        return isStatusDelete;
    }

    public void setIsStatusDelete(String isStatusDelete) {
        this.isStatusDelete = isStatusDelete;
    }

    public Collection<CartItem> getCartItemsByProductId() {
        return cartItemsByProductId;
    }

    public void setCartItemsByProductId(Collection<CartItem> cartItemsByProductId) {
        this.cartItemsByProductId = cartItemsByProductId;
    }
}
