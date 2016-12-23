package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** DTO for Individual snapshot. Used by Individual Page
 * Created by IntelliJ IDEA.
 * User: Duminda
 * Date: 12/31/12
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndividualSnapshotDTO implements Serializable{

    private List<Map<String,String>> individualNews = new ArrayList<Map<String, String>>();
    private List<Map<String,String>> insiderTransactions = new ArrayList<Map<String, String>>();
    private List<Map<String,String>> ownershipUpdates = new ArrayList<Map<String, String>>();
    private List<Map<String,String>> designationUpdates = new ArrayList<Map<String, String>>();
    private List<Map<String,String>> lastUpdatedProfiles = new ArrayList<Map<String, String>>();

    public List<Map<String, String>> getInsiderTransactions() {
        return insiderTransactions;
    }

    public void setInsiderTransactions(List<Map<String,String>> insiderTransactions) {
        this.insiderTransactions = insiderTransactions;
    }

    public List<Map<String, String>> getOwnershipUpdates() {
        return ownershipUpdates;
    }

    public void setOwnershipUpdates(List<Map<String,String>> ownershipUpdates) {
        this.ownershipUpdates = ownershipUpdates;
    }

    public List<Map<String, String>> getDesignationUpdates() {
        return designationUpdates;
    }

    public void setDesignationUpdates(List<Map<String,String>> designationUpdates) {
        this.designationUpdates = designationUpdates;
    }

    public List<Map<String, String>> getIndividualNews() {
        return individualNews;
    }

    public void setIndividualNews(List<Map<String,String>> individualNews) {
        this.individualNews = individualNews;
    }

    public List<Map<String, String>> getLastUpdatedProfiles() {
        return lastUpdatedProfiles;
    }

    public void setLastUpdatedProfiles(List<Map<String,String>> lastUpdatedProfiles) {
        this.lastUpdatedProfiles = lastUpdatedProfiles;
    }

}
