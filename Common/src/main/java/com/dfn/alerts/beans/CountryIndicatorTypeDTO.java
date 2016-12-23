package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by aravindal on 28/05/14.
 */
public class CountryIndicatorTypeDTO implements Serializable {

    private String indicatorCode;
    private int indicatorGroupId;
    private int providerId;
    private Map<String,String> indicatorLangDesc;

    public int getIndicatorGroupId() {
        return indicatorGroupId;
    }

    public void setIndicatorGroupId(int indicatorGroupId) {
        this.indicatorGroupId = indicatorGroupId;
    }

    public Map<String, String> getIndicatorLangDesc() {
        return indicatorLangDesc;
    }

    public void setIndicatorLangDesc(Map<String, String> indicatorLangDesc) {
        this.indicatorLangDesc = indicatorLangDesc;
    }

    public String getIndicatorCode() {

        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
