package com.dfn.alerts.beans;

/**
 * Business object for ticker data
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/16/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */

import com.dfn.alerts.constants.IConstants;

import java.util.HashMap;
import java.util.Map;

public class CompanyDTO extends CompanyProfileDTO {
    private String  countryCode;
    private String  isinCode;
    private Integer companyId;
    private String  companyNameLan;
    private String  gicsL2Lan;
    private String  gicsL3Lan;
    private String  gicsL4Lan;
    private String  gicsL4Code;
    private String  gicsL3Code;
    private String  gicsL2Code;
    private int  companyType;
    private int  listingType;
    private int financialYearStartMonth;
    private int financialStFreq;
    private int templateId;
    private String  defaultCurrencyId;
    private String cityName;
    private Double paidCapital;
    private String paidCapitalCurrency;
    private Double authCapital;
    private String authCapitalCurrency;
    private Integer slaLevel;
    private Double companyMarketCap;
    private String fullyOwnedSubsidiary;
    private String partiallyOwnedSubsidiary;

    private Map<String,CompanyLangDTO> companyLangDTOMap;

    private Map<String,String> gicsL2s                 = null;
    private Map<String,String> gicsL3s                 = null;
    private Map<String,String> gicsL4s                 = null;
    private Map<String,String> companyName             = null;

    public CompanyDTO() {
        companyLangDTOMap = new HashMap<String, CompanyLangDTO>(2);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyNameLan() {
        return companyNameLan;
    }

    public void setCompanyNameLan(String companyNameLan) {
        this.companyNameLan = companyNameLan;
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

    public Map<String, String> getGicsL3s() {
        return gicsL3s;
    }

    public String getGicsL4Lan() {
        return gicsL4Lan;
    }

    public void setGicsL4Lan(String gicsL4Lan) {
        this.gicsL4Lan = gicsL4Lan;
    }

    public String getGicsL4Code() {
        return gicsL4Code;
    }

    public void setGicsL4Code(String gicsL4Code) {
        this.gicsL4Code = gicsL4Code;
    }

    public String getGicsL3Code() {
        return gicsL3Code;
    }

    public void setGicsL3Code(String gicsL3Code) {
        this.gicsL3Code = gicsL3Code;
    }

    public String getGicsL2Code() {
        return gicsL2Code;
    }

    public void setGicsL2Code(String gicsL2Code) {
        this.gicsL2Code = gicsL2Code;
    }

    public Map<String, String> getGicsL2s() {
        return gicsL2s;
    }

    public Map<String, String> getGicsL4s() {
        return gicsL4s;
    }

    public int getFinancialYearStartMonth() {
        return financialYearStartMonth;
    }

    public void setFinancialYearStartMonth(int financialYearStartMonth) {
        this.financialYearStartMonth = financialYearStartMonth;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public int getListingType() {
        return listingType;
    }

    public void setListingType(int listingType) {
        this.listingType = listingType;
    }

    public Map<String, String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Map<String, String> companyName) {
        this.companyName = companyName;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getDefaultCurrencyId() {
        return defaultCurrencyId;
    }

    public void setDefaultCurrencyId(String defaultCurrencyId) {
        this.defaultCurrencyId = defaultCurrencyId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Map<String, CompanyLangDTO> getCompanyLangDTOMap() {
        return companyLangDTOMap;
    }

    public void setCompanyLangDTOMap(Map<String, CompanyLangDTO> companyLangDTOMap) {
        this.companyLangDTOMap = companyLangDTOMap;
        addToCompanyNameMap();
        addToSectorMap();
        addToIndustryMap();
        addToSubIndustryMap();
    }

    private void addToCompanyNameMap(){
        if(companyName == null){
            companyName = new HashMap<String, String>(companyLangDTOMap.size());
        }

        for(String lang : companyLangDTOMap.keySet()){
            companyName.put(lang, companyLangDTOMap.get(lang).getCompanyName());
        }

        if(companyName.get("EN") == null){
            companyName.put("EN", IConstants.EMPTY);
        }
    }

    private void addToSectorMap(){
        if(gicsL2s == null){
            gicsL2s = new HashMap<String, String>(companyLangDTOMap.size());
        }

        for(String lang : companyLangDTOMap.keySet()){
            gicsL2s.put(lang, companyLangDTOMap.get(lang).getGicsL2Lan());
        }

        if(gicsL2s.get("EN") == null){
            gicsL2s.put("EN", IConstants.EMPTY);
        }
    }

    private void addToIndustryMap(){
        if(gicsL3s == null){
            gicsL3s = new HashMap<String, String>(companyLangDTOMap.size());
        }

        for(String lang : companyLangDTOMap.keySet()){
            gicsL3s.put(lang, companyLangDTOMap.get(lang).getGicsL3Lan());
        }

        if(gicsL3s.get("EN") == null){
            gicsL3s.put("EN", IConstants.EMPTY);
        }
    }

    private void addToSubIndustryMap(){
        if(gicsL4s == null){
            gicsL4s = new HashMap<String, String>(companyLangDTOMap.size());
        }

        for(String lang : companyLangDTOMap.keySet()){
            gicsL4s.put(lang, companyLangDTOMap.get(lang).getGicsL4Lan());
        }

        if(gicsL4s.get("EN") == null){
            gicsL4s.put("EN", IConstants.EMPTY);
        }
    }

    public int getFinancialStFreq() {
        return financialStFreq;
    }

    public void setFinancialStFreq(int financialStFreq) {
        this.financialStFreq = financialStFreq;
    }

    public String getAuthCapitalCurrency() {
        return authCapitalCurrency;
    }

    public void setAuthCapitalCurrency(String authCapitalCurrency) {
        this.authCapitalCurrency = authCapitalCurrency;
    }

    public Double getPaidCapital() {
        return paidCapital;
    }

    public void setPaidCapital(Double paidCapital) {
        this.paidCapital = paidCapital;
    }

    public String getPaidCapitalCurrency() {
        return paidCapitalCurrency;
    }

    public void setPaidCapitalCurrency(String paidCapitalCurrency) {
        this.paidCapitalCurrency = paidCapitalCurrency;
    }

    public Double getAuthCapital() {
        return authCapital;
    }

    public void setAuthCapital(Double authCapital) {
        this.authCapital = authCapital;
    }

    public Integer getSlaLevel() {
        return slaLevel;
    }

    public void setSlaLevel(Integer slaLevel) {
        this.slaLevel = slaLevel;
    }

    public Double getCompanyMarketCap() {
        return companyMarketCap;
    }

    public void setCompanyMarketCap(Double companyMarketCap) {
        this.companyMarketCap = companyMarketCap;
    }

    public String getFullyOwnedSubsidiary() {
        return fullyOwnedSubsidiary;
    }

    public void setFullyOwnedSubsidiary(String fullyOwnedSubsidiary) {
        this.fullyOwnedSubsidiary = fullyOwnedSubsidiary;
    }

    public String getPartiallyOwnedSubsidiary() {
        return partiallyOwnedSubsidiary;
    }

    public void setPartiallyOwnedSubsidiary(String partiallyOwnedSubsidiary) {
        this.partiallyOwnedSubsidiary = partiallyOwnedSubsidiary;
    }
}
