package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 8/28/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationItemDTO implements Serializable {

    private String informationType;
    private Double operatorType;
    private Double transactionId;
    private List<Map<String,String>> contributionList;

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    public Double getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Double operatorType) {
        this.operatorType = operatorType;
    }

    public Double getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Double transactionId) {
        this.transactionId = transactionId;
    }

    public List<Map<String,String>> getContributionList() {
        return contributionList;
    }

    public void setContributionList(List<Map<String,String>> contributionList) {
        this.contributionList = contributionList;
    }
}
