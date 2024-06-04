package com.assignment.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "customers", schema = "solarbachthinhdb", catalog = "")
public class Customers {

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Basic
    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Basic
    @Column(name = "province", nullable = true, length = 50)
    private String province;

    @Basic
    @Column(name = "district", nullable = true, length = 50)
    private String district;

    @Basic
    @Column(name = "village", nullable = true, length = 50)
    private String village;

    @Basic
    @Column(name = "address", nullable = true, length = -1)
    private String address;

    @Basic
    @Column(name = "gender", nullable = true)
    private Boolean gender;

    @Basic
    @Column(name = "birth_day", nullable = true)
    private Date birthDay;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customers that = (Customers) o;

        if (customerId != that.customerId) return false;
        if (accountId != that.accountId) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (village != null ? !village.equals(that.village) : that.village != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (birthDay != null ? !birthDay.equals(that.birthDay) : that.birthDay != null) return false;
        return true;
    }


    public Accounts getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(Accounts accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }
}
