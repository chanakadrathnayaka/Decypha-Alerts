package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Danuran on 3/11/2015.
 */
@Entity
@Table(name = "SMS_DETAILS")
public class SMSMessage implements Serializable {
    @Id
    @Column(name = "SMS_ID")
    @SequenceGenerator(name = "SMS_DETAILS", sequenceName = "SEQ_SMS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SMS_DETAILS")
    private Integer smsId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name= "ACCOUNT_TYPE")
    private Integer accountType;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "SMS_TEXT", length = 158)
    private String smsText;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp lastUpdatedTime;

    @Column(name = "SMS_SENT_TIME")
    private Timestamp smsSentTime;

    public int getIsPromoCamp() {
        return isPromoCamp;
    }

    public void setIsPromoCamp(int isPromoCamp) {
        this.isPromoCamp = isPromoCamp;
    }

    @Column(name = "IS_PROMO_CAMP")
    private int isPromoCamp;

    @Column(name = "NOTIFY_STATUS")
    private Integer notifyStatus;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "CREATED_DATE", updatable = false)
    private Timestamp createdDate;

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getSmsId() {
        return smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Timestamp getSmsSentTime() {
        return smsSentTime;
    }

    public void setSmsSentTime(Timestamp smsSentTime) {
        this.smsSentTime = smsSentTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}

