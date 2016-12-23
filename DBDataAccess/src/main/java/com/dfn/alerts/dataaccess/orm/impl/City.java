package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 1/29/14
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CITY")
public class City implements Serializable {
    private Integer cityId;

    private String countryCode;

    private Boolean capital;

    private String shortDesEN;

    private String shortDesAR;

    private String longDesEN;

    private String longDesAR;

    @Transient
    private Map<String, String> shortDescription = new HashMap<String, String>(2);

    @Transient
    private Map<String, String> longDescription = new HashMap<String, String>(2);

    @Id
    @Column(name = "CITY_ID",  nullable = false)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "COUNTRY_CODE")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "CAPITAL")
    public Boolean isCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    @Column(name = "SHORT_DESCRIPTION_EN")
    public String getShortDesEN() {
        return shortDesEN;
    }

    public void setShortDesEN(String shortDesEN) {
        this.shortDesEN = shortDesEN;
        this.shortDescription.put("EN", shortDesEN);
    }

    @Column(name = "SHORT_DESCRIPTION_AR")
    public String getShortDesAR() {
        return shortDesAR;
    }

    public void setShortDesAR(String shortDesAR) {
        this.shortDesAR = shortDesAR;
        this.shortDescription.put("AR", shortDesAR);
    }

    @Column(name = "LONG_DESCRIPTION_EN")
    public String getLongDesEN() {
        return longDesEN;
    }

    public void setLongDesEN(String longDesEN) {
        this.longDesEN = longDesEN;
        this.longDescription.put("EN", longDesEN);
    }

    @Column(name = "LONG_DESCRIPTION_AR")
    public String getLongDesAR() {
        return longDesAR;
    }

    public void setLongDesAR(String longDesAR) {
        this.longDesAR = longDesAR;
        this.longDescription.put("AR", longDesAR);
    }

    @Transient
    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    @Transient
    public Map<String, String> getLongDescription() {
        return longDescription;
    }
}
