package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chathurangad
 * Date: 4/24/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndividualProfileDTO implements Serializable {
    private List<Map<String, String>> individualDetails = null;
    private List<Map<String, String>> relatedNews = null;
    private List<Map<String, Object>> ownershipUpdates = null;
    private List<Map<String, Object>> designations = null;
    private List<Map<String, Object>> insiderTrades = null;
    private List<Map<String, Object>> relatedIndividuals = null;
    private List<Map<String, Object>> relatedOwners = null;
    private List<Map<String, Object>> relatedInsiders = null;
    private List<Map<String, Object>> relatedManagers = null;
    private List<Map<String, Object>> relatedCompanies = null;
    private Map<String,IndividualLangDTO> individualsLangDetails=null;

    public List<Map<String, String>> getIndividualDetails() {
        return individualDetails;
    }

    public void setIndividualDetails(List<Map<String, String>> individualDetails) {
        this.individualDetails = individualDetails;
    }

    public List<Map<String, String>> getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(List<Map<String, String>> relatedNews) {
        this.relatedNews = relatedNews;
    }

    public List<Map<String, Object>> getOwnershipUpdates() {
        return ownershipUpdates;
    }

    public void setOwnershipUpdates(List<Map<String, Object>> ownershipUpdates) {
        this.ownershipUpdates = ownershipUpdates;
    }

    public List<Map<String, Object>> getDesignations() {
        return designations;
    }

    public void setDesignations(List<Map<String, Object>> designations) {
        this.designations = designations;
    }

    public List<Map<String, Object>> getInsiderTrades() {
        return insiderTrades;
    }

    public void setInsiderTrades(List<Map<String, Object>> insiderTrades) {
        this.insiderTrades = insiderTrades;
    }

    public List<Map<String, Object>> getRelatedIndividuals() {
        return relatedIndividuals;
    }

    public void setRelatedIndividuals(List<Map<String, Object>> relatedIndividuals) {
        this.relatedIndividuals = relatedIndividuals;
    }

    public List<Map<String, Object>> getRelatedCompanies() {
        return relatedCompanies;
    }

    public void setRelatedCompanies(List<Map<String, Object>> relatedCompanies) {
        this.relatedCompanies = relatedCompanies;
    }

    public List<Map<String, Object>> getRelatedOwners() {
        return relatedOwners;
    }

    public void setRelatedOwners(List<Map<String, Object>> relatedOwners) {
        this.relatedOwners = relatedOwners;
    }

    public List<Map<String, Object>> getRelatedInsiders() {
        return relatedInsiders;
    }

    public void setRelatedInsiders(List<Map<String, Object>> relatedInsiders) {
        this.relatedInsiders = relatedInsiders;
    }

    public List<Map<String, Object>> getRelatedManagers() {
        return relatedManagers;
    }

    public void setRelatedManagers(List<Map<String, Object>> relatedManagers) {
        this.relatedManagers = relatedManagers;
    }

    public void setIndividualsLangDetails(Map<String, IndividualLangDTO> individualsLangDetails) {
        this.individualsLangDetails = individualsLangDetails;
    }

    public Map<String,IndividualLangDTO> getIndividualsLangDetails(){
        return individualsLangDetails;
    }
}
