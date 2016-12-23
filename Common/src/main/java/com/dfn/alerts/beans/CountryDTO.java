package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/19/13
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountryDTO implements Serializable {
    private String countryCode ;

    private String desc ;

    private String regionIds ;

    private Boolean  isMacroData;

    private Boolean  isPriceData;

    private Boolean  isIPOData;

    private Boolean  isOtherData;

    private Boolean companyData;

    private Boolean isEditionControlCountry;

    private Boolean  isFIData;

    private Boolean  isMFData;

    private String  isoCode;

    private Map<String, String> description;

    private Map<String,CountryLangDTO> countryLangDTOMap;

    private Integer status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryDTO)) return false;

        CountryDTO that = (CountryDTO) o;

        if (!countryCode.equals(that.countryCode)) return false;
        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryCode.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    public CountryDTO(){
        countryLangDTOMap = new HashMap<String, CountryLangDTO>();
        description = new HashMap<String, String>();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String regionIds) {
        this.regionIds = regionIds;
    }

    public Boolean getMacroData() {
        return isMacroData;
    }
    public Boolean isMacroData() {
        return isMacroData;
    }

    public void setMacroData(Boolean macroData) {
        isMacroData = macroData;
    }

    public Boolean getPriceData() {
        return isPriceData;
    }

    public void setPriceData(Boolean priceData) {
        isPriceData = priceData;
    }

    public Boolean getIPOData() {
        return isIPOData;
    }

    public void setIPOData(Boolean IPOData) {
        isIPOData = IPOData;
    }

    public Boolean getOtherData() {
        return isOtherData;
    }

    public void setOtherData(Boolean otherData) {
        isOtherData = otherData;
    }

    public Boolean getCompanyData() {
        return companyData;
    }
    public Boolean isCompanyData() {
        return companyData;
    }

    public void setCompanyData(Boolean companyData) {
        this.companyData = companyData;
    }

    public Boolean getEditionControlCountry() {
        return isEditionControlCountry;
    }

    public void setEditionControlCountry(Boolean editionControlCountry) {
        isEditionControlCountry = editionControlCountry;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public Boolean getIsMacroData() {
        return isMacroData;
    }

    public void setIsMacroData(Boolean isMacroData) {
        this.isMacroData = isMacroData;
    }

    public Boolean getIsPriceData() {
        return isPriceData;
    }

    public Boolean isPriceData() {
        return isPriceData;
    }

    public void setIsPriceData(Boolean isPriceData) {
        this.isPriceData = isPriceData;
    }

    public Boolean getIsIPOData() {
        return isIPOData;
    }

    public Boolean isIPOData() {
        return isIPOData;
    }

    public void setIsIPOData(Boolean isIPOData) {
        this.isIPOData = isIPOData;
    }


    public Boolean getIsOtherData() {
        return isOtherData;
    }
    public Boolean isOtherData() {
        return isOtherData;
    }

    public void setIsOtherData(Boolean isOtherData) {
        this.isOtherData = isOtherData;
    }

    public Boolean getIsEditionControlCountry() {
        return isEditionControlCountry;
    }

    public void setIsEditionControlCountry(Boolean isEditionControlCountry) {
        this.isEditionControlCountry = isEditionControlCountry;
    }

    public Boolean getIsFIData() {
        return isFIData;
    }
    public Boolean isFIData() {
        return isFIData;
    }

    public void setIsFIData(Boolean isFIData) {
        this.isFIData = isFIData;
    }

    public Boolean getIsMFData() {
        return isMFData;
    }

    public Boolean isMFData() {
        return isMFData;
    }

    public void setIsMFData(Boolean isMFData) {
        this.isMFData = isMFData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Map<String, CountryLangDTO> getCountryLangDTOMap() {
        return countryLangDTOMap;
    }

    public void setCountryLangDTOMap(Map<String, CountryLangDTO> countryLangDTOMap) {
        this.countryLangDTOMap = countryLangDTOMap;
    }
}
