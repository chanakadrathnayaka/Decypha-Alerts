package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/19/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditionControlSettingsDTO implements Serializable {
    private Integer regionId;

    private String region ;

    private String regionDescriptions;

    private Map<String,String> regionDescriptionsMap;

    private String countries ;

    private List<CountryDTO> countriesList ;

    private Map<String, String> countryExchangeMap;

    private String exchanges = null;

    public EditionControlSettingsDTO(Integer regionId, String regionDescriptions, Map<String, String> regionDescriptionsMap, String countries, List<CountryDTO> countriesList, Map<String, String> countryExchangeMap, String exchanges){
        this.regionId = regionId;
        this.regionDescriptions = regionDescriptions;
        this.regionDescriptionsMap = regionDescriptionsMap;
        this.countries = countries;
        this.countriesList = countriesList;
        this.countryExchangeMap = countryExchangeMap;
        this.exchanges = exchanges;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionDescriptions() {
        return regionDescriptions;
    }

    public void setRegionDescriptions(String regionDescriptions) {
        this.regionDescriptions = regionDescriptions;
    }

    public Map<String, String> getRegionDescriptionsMap() {
        return regionDescriptionsMap;
    }

    public void setRegionDescriptionsMap(Map<String, String> regionDescriptionsMap) {
        this.regionDescriptionsMap = regionDescriptionsMap;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public List<CountryDTO> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<CountryDTO> countriesList) {
        this.countriesList = countriesList;
    }

    public Map<String, String> getCountryExchangeMap() {
        return countryExchangeMap;
    }

    public void setCountryExchangeMap(Map<String, String> countryExchangeMap) {
        this.countryExchangeMap = countryExchangeMap;
    }

    public String getExchanges() {
        return exchanges;
    }

    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }
}
