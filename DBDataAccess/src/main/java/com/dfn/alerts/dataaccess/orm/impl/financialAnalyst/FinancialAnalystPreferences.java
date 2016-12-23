package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by hasarindat on 4/3/2015.
 */
@Entity
@Table(name = "FINANCIAL_ANALYST_PREFERENCES")
public class FinancialAnalystPreferences {

    @Id
    @Column(name = "ANALYST_ID")
    private int individualId;

    @Column(name = "LANGUAGE_ID", nullable = false)
    private String language;

    @Column(name = "NOTIFY", nullable = false)
    private boolean notify;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private long createdBy;

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    private long updatedBy;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "key.analystId", cascade = CascadeType.ALL, targetEntity = FinancialAnalystCompanyPreferences.class, fetch = FetchType.EAGER)
    private Set<FinancialAnalystCompanyPreferences> companyPreferences;

    public int getIndividualId() {
        return individualId;
    }

    public void setIndividualId(int individualId) {
        this.individualId = individualId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<FinancialAnalystCompanyPreferences> getCompanyPreferences() {
        return companyPreferences;
    }

    public void setCompanyPreferences(Set<FinancialAnalystCompanyPreferences> companyPreferences) {
        this.companyPreferences = companyPreferences;
    }
}
