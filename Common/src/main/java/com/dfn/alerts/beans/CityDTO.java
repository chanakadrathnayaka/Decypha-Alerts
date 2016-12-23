package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 1/30/14
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CityDTO implements Serializable {
    private Integer cityId;

    private String countryCode;

    private Map<String, String> shortDescription;

    private Map<String, String> longDescription;


    public CityDTO(Integer cityId, String countryCode, Map<String, String> shortDescription, Map<String, String> longDescription) {
        this.cityId = cityId;
        this.countryCode = countryCode;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Map<String, String> shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Map<String, String> getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(Map<String, String> longDescription) {
        this.longDescription = longDescription;
    }
}
