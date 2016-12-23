package com.dfn.alerts.beans;

import com.dfn.alerts.beans.tickers.TickerDTO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: asankah
 * Date: 8/23/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 *
 * Data about Fund Managers
 */

/**
 * Stores data about a fund manager. In addition to the company data of the fund manager
 * this stores the statistics data about the funds which are managed by the fund manager.
 */
public class FundManagersDTO extends CompanyDTO implements Serializable {
    //values regarding all the funds managed by this fund manager
    private int numberOfManagedFunds;
    private Double totalTNA;
    private boolean isValidTotalTNA = true;

    //values regarding the Fund which hold a maximum change percentage and managed by this fund manager
    private Double maxChangePercentage;
    private TickerDTO maxChangedFundCompany;

    //values regarding the Fund which hold a maximum change percentage and managed by this fund manager.
    private Double minChangePercentage;
    private TickerDTO minChangedFundCompany;



    public FundManagersDTO(int companyId, Double tnaValue, Double changePercentage, TickerDTO fund) {
        setCompanyId(companyId);
        initializeTotalTNA(tnaValue);
        this.numberOfManagedFunds = 1;


        this.maxChangePercentage = changePercentage;
        maxChangedFundCompany = fund;

        this.minChangePercentage = changePercentage;
        minChangedFundCompany = fund;
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

    public int getNumberOfManagedFunds() {
        return numberOfManagedFunds;
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
        this.numberOfManagedFunds++;
    }

    public boolean isValidTotalTNA() {
        return isValidTotalTNA;
    }

}
