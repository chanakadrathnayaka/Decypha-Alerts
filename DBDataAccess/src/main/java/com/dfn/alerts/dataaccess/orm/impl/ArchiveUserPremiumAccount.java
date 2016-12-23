package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 4/21/14
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USR_PRM_ACCOUNT_DET_ARCHIVE")
public class ArchiveUserPremiumAccount implements Serializable {
    private Long accountDetailsID;

    private Long userID;

    private Long id;

    private Long priceUserId;

    private Long priceProfileId;

    private String priceUsername;

    private Integer status;

    private String services;

    private Integer salesRepId;

    private Date expiryDate;

    private int createdBy;

    private int lastUpdatedBy;

    private int version;

    private Timestamp lastUpdatedOn;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ARCHIVE_PRE_USER", sequenceName = "HIBERNATE_ARCHIVE_PRE_USER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVE_PRE_USER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "ACCOUNT_DETAILS_ID")
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

    @Column(name = "SALES_REP_ID")
    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    @Column(name = "EXPIRY_DATE")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Column(name = "CREATED_BY", updatable = false)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "LAST_UPDATED_BY")
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
