package com.dfn.alerts.beans;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dushani on 4/3/14.
 */
public class InvestorTypeValueDTO implements Serializable {

    private Integer id;                 // INVESTOR_TYPE_VALUE_ID
    private Integer nationalityId;      // NATIONALITY_ID
    private Integer investorTypeId;     // INVESTOR_TYPE_ID
    private Date investmentDate;        // INVESTMENT_DATE
    private String investmentDateStr;        // INVESTMENT_DATE
    private Integer referenceDocId;     // REF_DOC_ID
    private Double buyValue;            // BUY_VALUE
    private Double sellValue;           // SELL_VALUE
    private Double buyPercentage;       // BUY_PERCENTAGE
    private Double sellPercentage;      // SELL_PERCENTAGE
    private Date lastUpdatedTime;       // LAST_UPDATED_TIME
    private Integer status;               // STATUS
    private String sourceId;             // SOURCE_ID
    private Timestamp lastSyncTime = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Integer getInvestorTypeId() {
        return investorTypeId;
    }

    public void setInvestorTypeId(Integer investorTypeId) {
        this.investorTypeId = investorTypeId;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
        if(investmentDate != null)   {
            this.investmentDateStr = CommonUtils.convertDateToString(investmentDate);
        }
    }

    public Integer getReferenceDocId() {
        return referenceDocId;
    }

    public void setReferenceDocId(Integer referenceDocId) {
        this.referenceDocId = referenceDocId;
    }

    public Double getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(Double buyValue) {
        this.buyValue = buyValue;
    }

    public Double getSellValue() {
        return sellValue;
    }

    public void setSellValue(Double sellValue) {
        this.sellValue = sellValue;
    }

    public Double getBuyPercentage() {
        return buyPercentage;
    }

    public void setBuyPercentage(Double buyPercentage) {
        this.buyPercentage = buyPercentage;
    }

    public Double getSellPercentage() {
        return sellPercentage;
    }

    public void setSellPercentage(Double sellPercentage) {
        this.sellPercentage = sellPercentage;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}
