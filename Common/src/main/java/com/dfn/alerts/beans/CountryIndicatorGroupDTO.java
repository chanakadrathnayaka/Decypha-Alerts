package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by aravindal on 30/05/14.
 */
public class CountryIndicatorGroupDTO implements Serializable {

    private int indicatorGroupId;
    private Map<String,String> indicatorGroupLangDesc;
    private int providerId;

    public int getIndicatorGroupId() {
        return indicatorGroupId;
    }

    public void setIndicatorGroupId(int indicatorGroupId) {
        this.indicatorGroupId = indicatorGroupId;
    }

    public Map<String, String> getIndicatorGroupLangDesc() {
        return indicatorGroupLangDesc;
    }

    public void setIndicatorGroupLangDesc(Map<String, String> indicatorGroupLangDesc) {
        this.indicatorGroupLangDesc = indicatorGroupLangDesc;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int provider) {
        this.providerId = provider;
    }
}
