package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.beans.CountryDTO;
import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 6/5/13
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "GLOBAL_EDITION_SETTINGS", uniqueConstraints = @UniqueConstraint(columnNames ={ "REGION" }))
public class GlobalEditionSettings implements Serializable {
    private Integer regionId;

    private String region ;

    private String regionDescriptions;

    private Map<String,String> regionDescriptionsList;

    private String countries ;

    private List<CountryDTO> countriesList ;
    
    private Map<String,String> countryExchangeMap;

    @Id
    @Column(name = "REGION_ID",  nullable = false)
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Column(name = "COUNTRIES", length = 90, nullable = false)
    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    @Column(name = "REGION", length = 32, nullable = false)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Transient
    public List<CountryDTO> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<CountryDTO> countriesList) {
        this.countriesList = countriesList;
    }

    @Column(name = "SHORT_DESCRIPTION_LAN", length = 1000, nullable = false)
    public String getRegionDescriptions() {
        return regionDescriptions;
    }

    public void setRegionDescriptions(String shortDescriptionLan) {
        this.regionDescriptions = shortDescriptionLan;
        this.regionDescriptionsList = DataFormatter.GetLanguageSpecificDescriptionMap(shortDescriptionLan);
    }

    @Transient
    public Map<String,String> getRegionDescriptionsList(){
        return this.regionDescriptionsList;
    }

    @Transient
    public Map<String, String> getCountryExchangeMap() {
        return countryExchangeMap;
    }

    public void setCountryExchangeMap(Map<String, String> countryExchangeMap) {
        this.countryExchangeMap = countryExchangeMap;
    }
}