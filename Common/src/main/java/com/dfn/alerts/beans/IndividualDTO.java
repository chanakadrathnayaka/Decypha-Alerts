package com.dfn.alerts.beans;

/**
 * Business object for Individuals
 * Created by IntelliJ IDEA.
 * User: Duminda
 * Date: 1/16/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class IndividualDTO implements Serializable{
    private Integer individualId;
    private String  individualNameLan;
    private String  relatedIndividuals;
    private String  relatedCompanies;
    private String  relatedOwners;
    private String  relatedManagers;
    private String  relatedInsTraders;
    private String  countryCode;
    private boolean logo;

    private String email;
    private String phone;

    private Map<String,String> individualName  = null;
    private Map<String,IndividualLangDTO> individualLangDTOMap;

    public IndividualDTO(){
        setIndividualLangDTOMap(new HashMap<String, IndividualLangDTO>());
    }

    public String getRelatedCompanies() {
        return relatedCompanies;
    }

    public void setRelatedCompanies(String relatedCompanies) {
        this.relatedCompanies = relatedCompanies;
    }

    public Integer getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public String  getRelatedIndividuals() {
        return relatedIndividuals;
    }

    public void setRelatedIndividuals(String relatedIndividuals) {
        this.relatedIndividuals = relatedIndividuals;
    }

    public void setIndividualName(String individualNameLan) {
        this.individualName = CommonUtils.getLanguageDescriptionMap(individualNameLan);
        this.individualNameLan = individualNameLan;
    }

    public String getIndividualNameLan() {
        return individualNameLan;
    }

    public Map<String, String> getIndividualName() {
        return individualName;
    }

    public String getRelatedOwners() {
        return relatedOwners;
    }

    public void setRelatedOwners(String relatedOwners) {
        this.relatedOwners = relatedOwners;
    }

    public String getRelatedManagers() {
        return relatedManagers;
    }

    public void setRelatedManagers(String relatedManagers) {
        this.relatedManagers = relatedManagers;
    }

    public String getRelatedInsTraders() {
        return relatedInsTraders;
    }

    public void setRelatedInsTraders(String relatedInsTraders) {
        this.relatedInsTraders = relatedInsTraders;
    }

    public boolean getLogo() {
        return logo;
    }

    public void setLogo(boolean logo) {
        this.logo = logo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setIndividualLangDTOMap(Map<String, IndividualLangDTO> individualLangDTOMap) {
        this.individualLangDTOMap = individualLangDTOMap;
    }

    public Map<String, IndividualLangDTO> getIndividualLangDTOMap() {
        return individualLangDTOMap;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
