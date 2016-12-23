package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 6/26/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USR_ACCOUNT_DETAILS", uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME", "EMAIL"})})
public class UserDetails implements Serializable {

    private Long userID;

    private String username;

    private String password;

    private String email;

    private String userRoles;

    private Integer accountType;

    private Integer accountStatus;

    private String title;

    private String firstName;

    private String lastName;

    private String phone;

    private String workTel;

    private String country;

    private String company;

    private Date accountCreationDate;

    private Date accountExpiryDate;

    private Date passwordChangedDate;

    private Date passwordExpiryDate;

    private Date lastLoginDate;

    private String lastLoginIp;

    private String lastLoginCountry;

    private Integer noOfFailedAttempts;

    private String lastWebSession;

    private Integer loginStatus;

    private UserPremiumAccount userPremiumAccount;

    private int userVersion;

    private String designation;

    private String city;

    private String address1;

    private String address2;

    private Integer lastUpdatedBy;

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "CREATE_USER", sequenceName = "HIBERNATE_CREATE_USER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREATE_USER")
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

//    @Id
    @Column(name = "USERNAME", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    @Column(name = "PASSWORD", length = 512, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "EMAIL", length = 256, nullable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "USER_ROLES", length = 128, nullable = true)
    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    @Column(name = "ACCOUNT_TYPE")
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }


    @Column(name = "ACCOUNT_STATUS")
    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "WORK_TEL")
    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "COMPANY")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "ACCOUNT_CREATION_DATE")
    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    @Column(name = "ACCOUNT_EXPIRE_DATE")
    public Date getAccountExpiryDate() {
        return accountExpiryDate;
    }

    public void setAccountExpiryDate(Date accountExpiryDate) {
        this.accountExpiryDate = accountExpiryDate;
    }

    @Column(name = "PASSWORD_CHANGED_DATE")
    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }

    @Column(name = "PASSWORD_EXPIRE_DATE")
    public Date getPasswordExpiryDate() {
        return passwordExpiryDate;
    }

    public void setPasswordExpiryDate(Date passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }

    @Column(name = "LAST_LOGIN_DATE", nullable = false)
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Column(name = "LAST_LOGIN_IP", length = 32, nullable = false)
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Column(name = "NO_OF_FAILED_ATTEMPTS")
    public Integer getNoOfFailedAttempts() {
        return noOfFailedAttempts;
    }

    public void setNoOfFailedAttempts(Integer noOfFailedAttempts) {
        this.noOfFailedAttempts = noOfFailedAttempts;
    }

    @Column(name = "LAST_LOGIN_COUNTRY", length = 32, nullable = false)
    public String getLastLoginCountry() {
        return lastLoginCountry;
    }

    public void setLastLoginCountry(String lastLoginCountry) {
        this.lastLoginCountry = lastLoginCountry;
    }

    @Column(name = "LAST_WEB_SESSION_ID", length = 64, nullable = false)
    public String getLastWebSession() {
        return lastWebSession;
    }

    public void setLastWebSession(String lastWebSession) {
        this.lastWebSession = lastWebSession;
    }

    @Column(name = "LOGIN_STATUS")
    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = UserPremiumAccount.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
//    @NotFound(action=NotFoundAction.IGNORE)
    public UserPremiumAccount getUserPremiumAccount() {
        return userPremiumAccount;
    }

    public void setUserPremiumAccount(UserPremiumAccount userPremiumAccount) {
        this.userPremiumAccount = userPremiumAccount;
    }

    @Column(name = "USER_VERSION")
    public int getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(int userVersion) {
        this.userVersion = userVersion;
    }

    @Column(name = "DESIGNATION")
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "ADDRESS_1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "ADDRESS_2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "LAST_UPDATED_BY", insertable = false)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
