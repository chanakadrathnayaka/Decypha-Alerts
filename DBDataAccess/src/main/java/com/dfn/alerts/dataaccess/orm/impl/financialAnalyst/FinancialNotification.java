package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import com.dfn.alerts.constants.IConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by hasarindat on 4/3/2015.
 */
@Entity
@Table(name = "FINANCIAL_NOTIFICATION")
public class FinancialNotification {

    @Id
    @Column(name = "TRANSACTION_ID")
    private int transactionId;

    @Column(name = "COMPANY_ID", nullable = false, updatable = false)
    private int companyId;

    @Column(name = "FINANCIAL_YEAR", nullable = false, updatable = false)
    private int financialYear;

    @Column(name = "FINANCIAL_QUARTER", nullable = false, updatable = false)
    private int financialQuarter;

    @Column(name = "FINANCIAL_TYPE", nullable = false, updatable = false)
    private IConstants.FinancialTypes financialType;

    @Column(name = "STATUS", nullable = false)
    private int status;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp updatedTime;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(int financialYear) {
        this.financialYear = financialYear;
    }

    public int getFinancialQuarter() {
        return financialQuarter;
    }

    public void setFinancialQuarter(int financialQuarter) {
        this.financialQuarter = financialQuarter;
    }

    public IConstants.FinancialTypes getFinancialType() {
        return financialType;
    }

    public void setFinancialType(IConstants.FinancialTypes financialType) {
        this.financialType = financialType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
