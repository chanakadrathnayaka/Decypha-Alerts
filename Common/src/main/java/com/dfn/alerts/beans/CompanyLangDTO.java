package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by udaras on 4/2/2014.
 */
public class CompanyLangDTO implements Serializable{
    private String companyName;
    private String tradingName;
    private String countryDesc;
    private String cityDesc;
    private String gicsL2Lan;
    private String gicsL3Lan;
    private String gicsL4Lan;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getCountryDesc() {
        return countryDesc;
    }

    public void setCountryDesc(String countryDesc) {
        this.countryDesc = countryDesc;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }

    public String getGicsL2Lan() {
        return gicsL2Lan;
    }

    public void setGicsL2Lan(String gicsL2Lan) {
        this.gicsL2Lan = gicsL2Lan;
    }

    public String getGicsL3Lan() {
        return gicsL3Lan;
    }

    public void setGicsL3Lan(String gicsL3Lan) {
        this.gicsL3Lan = gicsL3Lan;
    }

    public String getGicsL4Lan() {
        return gicsL4Lan;
    }

    public void setGicsL4Lan(String gicsL4Lan) {
        this.gicsL4Lan = gicsL4Lan;
    }

}
