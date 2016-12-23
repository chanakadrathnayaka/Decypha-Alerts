package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by dushani on 8/26/14.
 */
public class CountryLangDTO implements Serializable {
    private String shortName;
    private String fullName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
