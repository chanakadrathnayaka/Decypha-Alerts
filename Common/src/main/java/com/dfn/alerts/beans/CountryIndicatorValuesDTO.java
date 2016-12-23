package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by aravindal on 29/05/14.
 */
public class CountryIndicatorValuesDTO implements Serializable {
    private String indicatorCode;
    private int indicatorGroupId;
    private Object[] indicatorValue;
    private Date indicatorValueDate;
    private int providerId;

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public int getIndicatorGroupId() {
        return indicatorGroupId;
    }

    public void setIndicatorGroupId(int indicatorGroupId) {
        this.indicatorGroupId = indicatorGroupId;
    }

    public Object[] getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(Object[] indicatorValue) {

        Object[] dataCopy = new Object[indicatorValue.length];
        System.arraycopy(indicatorValue, 0, dataCopy, 0, indicatorValue.length);

        this.indicatorValue = dataCopy;
    }

    public Date getIndicatorValueDate() {
        return indicatorValueDate;
    }

    public void setIndicatorValueDate(Date indicatorValueDate) {
        this.indicatorValueDate = indicatorValueDate;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
