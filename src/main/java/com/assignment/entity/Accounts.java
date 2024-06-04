package com.assignment.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "accounts", schema = "solarbachthinhdb", catalog = "")
public class Accounts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Basic
    @Column(name = "passwords", nullable = false, length = 100)
    private String passwords;

    @Basic
    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @Basic
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Basic
    @Column(name = "date_created", nullable = true)
    private Timestamp dateCreated;

    @Basic
    @Column(name = "is_acctive", nullable = false)
    private Boolean isAcctive = true;

    @Basic
    @Column(name = "role", nullable = false, length = 50)
    private String role = "USER";

    @Basic
    @Column(name = "token", nullable = true, length = 50)
    private String token;

    public boolean isAcctive() {
        return isAcctive;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getAcctive() {
        return isAcctive;
    }

    public void setAcctive(boolean acctive) {
        isAcctive = acctive;
    }

    public void setAcctive(Boolean acctive) {
        isAcctive = acctive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accounts that = (Accounts) o;

        if (accountId != that.accountId) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (passwords != null ? !passwords.equals(that.passwords) : that.passwords != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (isAcctive != null ? !isAcctive.equals(that.isAcctive) : that.isAcctive != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (passwords != null ? passwords.hashCode() : 0);
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (isAcctive != null ? isAcctive.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
