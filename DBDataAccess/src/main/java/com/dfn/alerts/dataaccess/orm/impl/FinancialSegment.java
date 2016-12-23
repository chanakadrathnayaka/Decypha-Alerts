package com.dfn.alerts.dataaccess.orm.impl;

import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Priyabashitha L.P.
 * Date: 4/21/2015
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "DFN_FINANCIAL_SEGMENT_DATA")
public class FinancialSegment implements Serializable {

    @EmbeddedId
    private FinancialSegmentKey segmentKey;

    @Column(name = "SEGMENT_DESCRIPTION_EN")
    private String segmentDesc;

    @Column(name = "SEGMENT_CODE")
    private String segmentCode;

    @Column(name = "PARENT_SEGMENT_CODE")
    private String parentSegmentCode;

    @Column(name = "VALUE")
    private Double value;

    @Column(name = "CURRENCY_DATA")
    private String currencyData;

    @Column(name = "LAST_UPDATED_TIME")
    private Date lastUpdated;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "COMPANY_NAME_EN")
    private String companyNameEn;

    @Column(name = "FIELD_DISPLAY_NAME_EN")
    private String fieldNameEn;

    @Column(name = "SEGMENT_TYPE_DESCRIPTION")
    private String segmentTypeDesc;

    @Column(name = "CHILD_SEGMENT_CODE")
    private String childSegmentCode;

    @Column(name = "PCT")
    private Double percentage;

    @Column(name = "FIELD_TOOLTIP_EN")
    private String fieldTooltipEn;

    @Column(name = "STANDARD_SEGMENT_CODE")
    private String standardSegmentCode;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

    @Column(name = "ATTR_LEVEL")
    private Integer attrLevel;

    @Formula(value = "FIN_YEAR || PERIOD_ID")
    private String fullPeriod;

    public FinancialSegmentKey getSegmentKey() {
        return segmentKey;
    }

    public void setSegmentKey(FinancialSegmentKey segmentKey) {
        this.segmentKey = segmentKey;
    }

    public String getSegmentDesc() {
        return segmentDesc;
    }

    public void setSegmentDesc(String segmentDesc) {
        this.segmentDesc = segmentDesc;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getParentSegmentCode() {
        return parentSegmentCode;
    }

    public void setParentSegmentCode(String parentSegmentCode) {
        this.parentSegmentCode = parentSegmentCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrencyData() {
        return currencyData;
    }

    public void setCurrencyData(String currencyData) {
        this.currencyData = currencyData;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getFieldNameEn() {
        return fieldNameEn;
    }

    public void setFieldNameEn(String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    public String getSegmentTypeDesc() {
        return segmentTypeDesc;
    }

    public void setSegmentTypeDesc(String segmentTypeDesc) {
        this.segmentTypeDesc = segmentTypeDesc;
    }

    public String getChildSegmentCode() {
        return childSegmentCode;
    }

    public void setChildSegmentCode(String childSegmentCode) {
        this.childSegmentCode = childSegmentCode;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTooltipEn() {
        return fieldTooltipEn;
    }

    public void setFieldTooltipEn(String fieldTooltipEn) {
        this.fieldTooltipEn = fieldTooltipEn;
    }

    public String getStandardSegmentCode() {
        return standardSegmentCode;
    }

    public void setStandardSegmentCode(String standardSegmentCode) {
        this.standardSegmentCode = standardSegmentCode;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getAttrLevel() {
        return attrLevel;
    }

    public void setAttrLevel(Integer attrLevel) {
        this.attrLevel = attrLevel;
    }

    public String getFullPeriod() {
        return fullPeriod;
    }

    public void setFullPeriod(String fullPeriod) {
        this.fullPeriod = fullPeriod;
    }
}
