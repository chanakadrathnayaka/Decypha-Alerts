package com.dfn.alerts.dataaccess;

import com.dfn.alerts.dataaccess.orm.impl.SalesRepresentative;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/17/13
 * Time: 10:25 AM
 */
@Entity
@Table(name = "USR_PREMIUM_ACCOUNT_DETAILS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PRICE_USERNAME", "PRICE_USER_ID", "USER_ID"})})
public class UserPremiumAccount implements Serializable {

    private Long accountDetailsID;

    private Long priceUserId;

    private Long priceProfileId;

    private String priceUsername;

    private Integer status;

    private String services;

    private SalesRepresentative salesRep;

    private Date expiryDate;

    private UserDetails userDetails;

    private int createdBy;

    private int lastUpdatedBy;

    private int version;

    private Timestamp lastUpdatedOn;

    @Id
    @Column(name = "ACCOUNT_DETAILS_ID")
    @SequenceGenerator(name = "CREATE_PRE_USER", sequenceName = "HIBERNATE_CREATE_PRE_USER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREATE_PRE_USER")
    public Long getAccountDetailsID() {
        return accountDetailsID;
    }

    public void setAccountDetailsID(Long accountDetailsID) {
        this.accountDetailsID = accountDetailsID;
    }

    @Column(name = "PRICE_USER_ID", nullable = false)
    public Long getPriceUserId() {
        return priceUserId;
    }

    public void setPriceUserId(Long priceUserId) {
        this.priceUserId = priceUserId;
    }

    @Column(name = "PRICE_PROFILE_ID", nullable = false)
    public Long getPriceProfileId() {
        return priceProfileId;
    }

    public void setPriceProfileId(Long priceProfileId) {
        this.priceProfileId = priceProfileId;
    }

    @Column(name = "PRICE_USERNAME")
    public String getPriceUsername() {
        return priceUsername;
    }

    public void setPriceUsername(String priceUsername) {
        this.priceUsername = priceUsername;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SERVICES")
    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = SalesRepresentative.class)
    @JoinColumn(name = "SALES_REP_ID", referencedColumnName = "REP_ID")
    public SalesRepresentative getSalesRep() {
        if (salesRep == null) {
            salesRep = new SalesRepresentative();
        }
        return salesRep;
    }

    public void setSalesRep(SalesRepresentative salesRep) {
        this.salesRep = salesRep;
    }

    @Column(name = "EXPIRY_DATE")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = UserDetails.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public UserDetails getUserDetails() {
        if (userDetails == null) {
            userDetails = new UserDetails();
        }
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Column(name = "CREATED_BY", updatable = false)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "LAST_UPDATED_BY", insertable = false)
    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }



    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "LAST_UPDATED_ON")
    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
