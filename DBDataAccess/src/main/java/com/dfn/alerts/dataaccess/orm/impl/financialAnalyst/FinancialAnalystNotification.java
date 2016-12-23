package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hasarindat on 4/3/2015.
 */
@Entity
@Table(name = "ANALYST_NOTIFICATION_RECORD", uniqueConstraints = {@UniqueConstraint(columnNames = "EMAIL_ID")})
public class FinancialAnalystNotification {

    @Id
    @Column(name = "EMAIL_ID", nullable = false)
    private int emailId;

    @Column(name = "ANALYST_ID", nullable = false)
    private int individualId;

    @Column(name = "COMPANY_ID", nullable = false)
    private int companyId;

    @Column(name = "FINANCIAL_YEAR", nullable = false)
    private int financialYear;

    @Column(name = "FINANCIAL_QUARTER", nullable = false)
    private int financialPeriod;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private Timestamp createdTime;

    public int getIndividualId() {
        return individualId;
    }

    public void setIndividualId(int individualId) {
        this.individualId = individualId;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
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

    public int getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(int financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
