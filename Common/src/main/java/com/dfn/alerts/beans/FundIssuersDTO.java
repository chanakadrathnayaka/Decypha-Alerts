package com.dfn.alerts.beans;

import com.dfn.alerts.beans.tickers.TickerDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 9/9/13
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class FundIssuersDTO extends CompanyDTO implements Serializable {
    //values regarding all the funds issued by this fund issuer
    private int numberOfIssuedFunds;
    private int numberOfIssuedTypes;
    private Double totalTNA;
    private boolean isValidTotalTNA = true;

    //values regarding the Fund which hold a maximum change percentage and issued by this fund issuer
    private Double maxChangePercentage;
    private TickerDTO maxChangedFundCompany;

    //values regarding the Fund which hold a maximum change percentage and issued by this fund issuer.
    private Double minChangePercentage;
    private TickerDTO minChangedFundCompany;

    private List<Integer> classificationTypes;

    public FundIssuersDTO(int companyId, Double tnaValue, Double changePercentage, TickerDTO fund, int classificationType) {
        setCompanyId(companyId);
        initializeTotalTNA(tnaValue);
        this.numberOfIssuedFunds = 1;

        this.maxChangePercentage = changePercentage;
        maxChangedFundCompany = fund;

        this.minChangePercentage = changePercentage;
        minChangedFundCompany = fund;

        classificationTypes = new ArrayList<Integer>();
        classificationTypes.add(classificationType);
        this.numberOfIssuedTypes = 1;
    }

    public List<Integer> addClassificationTypes(int type) {
        if(!classificationTypes.contains(type)){
            classificationTypes.add(type);
            numberOfIssuedTypes++;
        }
        return classificationTypes;
    }

    private void initializeTotalTNA(Double tnaValue) {
        if(tnaValue != null && tnaValue >= 0){
            this.totalTNA = tnaValue;
        }
        else{
            this.totalTNA = 0.0;
            isValidTotalTNA = false;
        }
    }

    public int getNumberOfIssuedFunds() {
        return numberOfIssuedFunds;
    }

    public Double getTotalTNA() {
        if(isValidTotalTNA){
            return totalTNA;
        }
        else {
            return -1.0;
        }
    }

    /**
     * validator:
     * if TNA value for a fund of a fund manager is not available (negative value or null) then
     * total TNA cant calculate.Therefor totalTNA of this manager become invalid.
     * @param TNAValue
     */
    public void setTotalTNA(Double TNAValue) {

        if(TNAValue != null && TNAValue >= 0){
            this.totalTNA = this.totalTNA + TNAValue;
        }
        else{
            isValidTotalTNA = false;
        }
    }

    public Double getMaxChangePercentage() {
        return maxChangePercentage;
    }

    public void setMaxChangePercentage(Double maxChangePercentage, TickerDTO fund) {
        if(this.maxChangePercentage < maxChangePercentage){
            this.maxChangePercentage = maxChangePercentage;
            maxChangedFundCompany = fund;
        }
    }

    public Double getMinChangePercentage() {
        return minChangePercentage;
    }

    public void setMinChangePercentage(Double minChangePercentage, TickerDTO fund) {
        if(this.minChangePercentage > minChangePercentage){
            this.minChangePercentage = minChangePercentage;
            minChangedFundCompany = fund;
        }
    }

    public void updateFundCount(){
        this.numberOfIssuedFunds++;
    }

    public boolean isValidTotalTNA() {
        return isValidTotalTNA;
    }

    public void updateFundTypeCount(){
        this.numberOfIssuedTypes++;
    }

    public int getNumberOfIssuedTypes() {
        return numberOfIssuedTypes;
    }
}
