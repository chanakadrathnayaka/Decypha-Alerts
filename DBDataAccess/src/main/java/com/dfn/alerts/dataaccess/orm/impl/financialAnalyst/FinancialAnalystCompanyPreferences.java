package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by hasarindat on 4/3/2015.
 */
@Entity
@Table(name = "FINANCIAL_ANALYST_COM_PREF")
public class FinancialAnalystCompanyPreferences {

    @EmbeddedId
    private FinancialAnalystComPrefPrimaryKey key;

    @Column(name = "INTERESTED", nullable = false)
    private boolean interested;

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    private long updatedBy;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp updatedTime;

    public FinancialAnalystComPrefPrimaryKey getKey() {
        return key;
    }

    public void setKey(FinancialAnalystComPrefPrimaryKey key) {
        this.key = key;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

}
