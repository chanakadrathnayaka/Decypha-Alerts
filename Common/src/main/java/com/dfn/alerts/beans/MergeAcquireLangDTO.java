package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by chathurag on 6/18/2015.
 *
 * contains language specific data for Mergers and Acquisitions
 */
public class MergeAcquireLangDTO implements Serializable{

    private String statusDescription;
    private String dealClassification;
    private String dealTypeDescription;

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getDealClassification() {
        return dealClassification;
    }

    public void setDealClassification(String dealClassification) {
        this.dealClassification = dealClassification;
    }

    public String getDealTypeDescription() {
        return dealTypeDescription;
    }

    public void setDealTypeDescription(String dealTypeDescription) {
        this.dealTypeDescription = dealTypeDescription;
    }
}
