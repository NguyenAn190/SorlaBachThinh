package com.assignment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "brands", schema = "solarbachthinhdb", catalog = "")
public class Brands {

    @Id
    @Column(name = "brand_id", nullable = false, length = 20)
    private String brandId;

    @Basic
    @Column(name = "brand_name", nullable = false, length = 255)
    private String brandName;

    @Basic
    @Column(name = "brand_phone_number", nullable = true, length = 13)
    private String phoneNumber;

    @Basic
    @Column(name = "country_of_origin", nullable = true, length = 100)
    private String countryOfOrigin;

    @Basic
    @Column(name = "brand_description", nullable = true, length = -1)
    private String brandDescription;

    @Basic
    @Column(name = "website_url", nullable = true, length = 255)
    private String websiteUrl;

    @Basic
    @Column(name = "is_status_delete", nullable = true, length = 255)
    private String isStatusDelete;

    @OneToMany(mappedBy = "brandsByBrandId")
    @JsonManagedReference
    private Collection<Products> productsByBrandId;

    public String getIsStatusDelete() {
        return isStatusDelete;
    }

    public void setIsStatusDelete(String isStatusDelete) {
        this.isStatusDelete = isStatusDelete;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brands that = (Brands) o;

        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null) return false;
        if (countryOfOrigin != null ? !countryOfOrigin.equals(that.countryOfOrigin) : that.countryOfOrigin != null)
            return false;
        if (brandDescription != null ? !brandDescription.equals(that.brandDescription) : that.brandDescription != null)
            return false;
        if (websiteUrl != null ? !websiteUrl.equals(that.websiteUrl) : that.websiteUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brandId != null ? brandId.hashCode() : 0;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + (countryOfOrigin != null ? countryOfOrigin.hashCode() : 0);
        result = 31 * result + (brandDescription != null ? brandDescription.hashCode() : 0);
        result = 31 * result + (websiteUrl != null ? websiteUrl.hashCode() : 0);
        return result;
    }

    public Collection<Products> getProductsByBrandId() {
        return productsByBrandId;
    }

    public void setProductsByBrandId(Collection<Products> productsByBrandId) {
        this.productsByBrandId = productsByBrandId;
    }
}
