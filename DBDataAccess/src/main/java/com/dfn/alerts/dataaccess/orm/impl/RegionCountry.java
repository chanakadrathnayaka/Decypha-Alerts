package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.beans.CountryDTO;
import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/7/13
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "REGION_COUNTRY", uniqueConstraints = @UniqueConstraint(columnNames ={ "REGION" }))
public class RegionCountry implements Serializable{

    private Integer regionId;

    private String region ;

    private String regionDescriptions;

    private Map<String,String> regionDescriptionsList;

    private String countries = null;

    private String defaultCountry = null;

    private List<CountryDTO> countriesList = new ArrayList<CountryDTO>();
    
    private String exchanges = null;

    private Boolean isEditionControlRegion;

    private Boolean isEditionControlRegionGroup;

    @Id
    @Column(name = "REGION_ID",  nullable = false)
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Transient
    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    @Column(name = "DEFAULT_COUNTRY")
    public String getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
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
    public String getExchanges() {
        return exchanges;
    }

    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }

    @Column(name = "IS_EC_REGION")
    public Boolean getEditionControlRegion() {
        return isEditionControlRegion;
    }

    public void setEditionControlRegion(Boolean editionControlRegion) {
        isEditionControlRegion = editionControlRegion;
    }

    @Column(name = "IS_EC_REGION_GROUP")
    public Boolean getEditionControlRegionGroup() {
        return isEditionControlRegionGroup;
    }

    public void setEditionControlRegionGroup(Boolean editionControlRegionGroup) {
        isEditionControlRegionGroup = editionControlRegionGroup;
    }
}
