package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by hasarindat on 4/7/2015.
 */
@Embeddable
public class FinancialAnalystComPrefPrimaryKey implements Serializable {

    @Column(name = "ANALYST_ID")
    Integer analystId;

    @Column(name = "COMPANY_ID")
    Integer companyId;

    public Integer getAnalystId() {
        return analystId;
    }

    public void setAnalystId(Integer analystId) {
        this.analystId = analystId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinancialAnalystComPrefPrimaryKey that = (FinancialAnalystComPrefPrimaryKey) o;

        if (analystId != null ? !analystId.equals(that.analystId) : that.analystId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = analystId != null ? analystId.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
