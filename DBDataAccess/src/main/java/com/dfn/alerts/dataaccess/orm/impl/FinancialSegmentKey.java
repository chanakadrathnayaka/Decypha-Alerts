package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Priyabashitha L.P.
 * Date: 4/22/2015
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class FinancialSegmentKey implements Serializable {

    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Column(name = "FIN_YEAR")
    private Integer year;

    @Column(name = "PERIOD_ID")
    private Integer period;

    @Column(name = "INFO_TYPE")
    private String infoType;

    @Column(name = "FIELD_ID")
    private Integer fieldId;

    @Column(name = "SEGMENT_TYPE_ID")
    private Integer segmentTypeId;

    @Column(name = "SEGMENT_ID")
    private Integer segmentId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getSegmentTypeId() {
        return segmentTypeId;
    }

    public void setSegmentTypeId(Integer segmentTypeId) {
        this.segmentTypeId = segmentTypeId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialSegmentKey)) return false;

        FinancialSegmentKey that = (FinancialSegmentKey) o;

        if (!companyId.equals(that.companyId)) return false;
        if (!fieldId.equals(that.fieldId)) return false;
        if (!infoType.equals(that.infoType)) return false;
        if (!period.equals(that.period)) return false;
        if (!segmentId.equals(that.segmentId)) return false;
        if (!segmentTypeId.equals(that.segmentTypeId)) return false;
        if (!year.equals(that.year)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + period.hashCode();
        result = 31 * result + infoType.hashCode();
        result = 31 * result + fieldId.hashCode();
        result = 31 * result + segmentTypeId.hashCode();
        result = 31 * result + segmentId.hashCode();
        return result;
    }
}
