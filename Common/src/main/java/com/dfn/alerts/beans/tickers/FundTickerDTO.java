package com.dfn.alerts.beans.tickers;

import java.util.Date;
import java.util.Map;

/** Data Transfer Object for Fund Ticker
 * Created by IntelliJ IDEA.
 * User: Duminda
 * Date: 07/04/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.*/
public class FundTickerDTO extends TickerDTO {

    private Integer fundClass;
    private double change3M;
    private Double changeYTD;
    private Double change1Y;
    private double changePer3M;
    private Double changePerYTD;
    private Double changePer1Y;
    private double nav;
    private String navDate;
    private String managedCompanies;
    private Double tNav;
    private Double tNavUSD;
    private Date  tNavDate;
    private double prvTNav;
    private Double prvTNavUSD;
    private Date  prvTNavDate;
    private String fundIssuers;

    private Date estbDate;
    private double adminFee;
    private double mgtFee;
    private double custodianFee;
    private double performanceFee;
    private double redemptionFee;
    private double subsequentSubs;
    private double minSubscription;
    private double subscriptionFee;
    private double serviceFee;
    private double otherExp;
    private Integer navFrequency;
    private double changePer1M;
    private double changePer3Y;
    private double changePer5Y;
    private double low52Week;
    private double high52Week;
    private double changePer10Y;
    private double changePerLifeTime;
    private String focusedCountries;
    private String focusedRegions;
    private Integer duration;
    private Integer fundFinancialYearStartDay;
    private Integer fundFinancialYearStartMonth;
    private Integer fundFinancialYearEndDay;
    private Integer fundFinancialYearEndMonth;
    private Integer fundStatus;

    private String managedCountries;
    private String issuedCountries;

    private double monthlyReturn3Y;         //THREE_YEAR_MONTHLY_RETURN
    private double monthlyReturn5Y;         //FIVE_YEAR_MONTHLY_RETURN
    private double annualReturn3Y;          //THREE_YEAR_ANNUAL_RETURN
    private double annualReturn5Y;          //FIVE_YEAR_ANNUAL_RETURN
    private double stdDevAnnualReturn3Y;    //THREE_YR_ANNUAL_RETURN_STD_DEV
    private double stdDevAnnualReturn5Y;    //FIVE_YR_ANNUAL_RETURN_STD_DEV

    private String fundClassDesc;
    private String managedCompanyDesc;

    public double getChangePer10Y() {
        return changePer10Y;
    }

    public void setChangePer10Y(double changePer10Y) {
        this.changePer10Y = changePer10Y;
    }

    public double getChangePerLifeTime() {
        return changePerLifeTime;
    }

    public void setChangePerLifeTime(double changePerLifeTime) {
        this.changePerLifeTime = changePerLifeTime;
    }

    public Integer getNavFrequency() {
        return navFrequency;
    }

    public void setNavFrequency(Integer navFrequency) {
        this.navFrequency = navFrequency;
    }

    public String getFundIssuers() {
        return fundIssuers;
    }

    public void setFundIssuers(String fundIssuers) {
        this.fundIssuers = fundIssuers;
    }

    public void setFundClass(Integer fundClass) {
        this.fundClass = fundClass;
    }

    public Integer getFundClass() {
        return fundClass;
    }

    public Double getChangeYTD() {
        return changeYTD;
    }

    public void setChangeYTD(Double changeYTD) {
        this.changeYTD = changeYTD;
    }

    public Double getChange3M() {
        return change3M;
    }

    public void setChange3M(Double change3M) {
        this.change3M = change3M;
    }

    public Double getChange1Y() {
        return change1Y;
    }

    public void setChange1Y(Double change1Y) {
        this.change1Y = change1Y;
    }

    public Double getChangePerYTD() {
        return this.changePerYTD;
    }

    public void setChangePerYTD(Double changePerYTD) {
        this.changePerYTD = changePerYTD;
    }

    public Double getChangePer3M() {
        return changePer3M;
    }

    public void setChangePer3M(Double changePer3M) {
        this.changePer3M = changePer3M;
    }

    public Double getChangePer1Y() {
        return changePer1Y;
    }

    public void setChangePer1Y(Double changePer1Y) {
        this.changePer1Y = changePer1Y;
    }

    public double getNav() {
        return nav;
    }

    public void setNav(double nav) {
        this.nav = nav;
    }

    public String getNavDate() {
        return navDate;
    }

    public void setNavDate(String navDate) {
        this.navDate = navDate;
    }

    public String getManagedCompanies() {
        return managedCompanies;
    }

    public void setManagedCompanies(String managedCompanies) {
        this.managedCompanies = managedCompanies;
    }


    public Double getTNav() {
        return tNav;
    }

    public void setTNav(Double tNav) {
        this.tNav = tNav;
    }

    public Double getTNavUSD() {
        return tNavUSD;
    }

    public void setTNavUSD(Double tNavUSD) {
        this.tNavUSD = tNavUSD;
    }

    public Date getTNavDate() {
        return tNavDate;
    }

    public void setTNavDate(Date tNavDate) {
        this.tNavDate = tNavDate;
    }

    public double getPrvTNav() {
        return prvTNav;
    }

    public void setPrvTNav(double prvTNav) {
        this.prvTNav = prvTNav;
    }

    public Double getPrvTNavUSD() {
        return prvTNavUSD;
    }

    public void setPrvTNavUSD(Double prvTNavUSD) {
        this.prvTNavUSD = prvTNavUSD;
    }

    public Date getPrvTNavDate() {
        return prvTNavDate;
    }

    public void setPrvTNavDate(Date prvTNavDate) {
        this.prvTNavDate = prvTNavDate;
    }

    public double getOtherExp() {
        return otherExp;
    }

    public void setOtherExp(double otherExp) {
        this.otherExp = otherExp;
    }

    public Date getEstbDate() {
        return estbDate;
    }

    public void setEstbDate(Date estbDate) {
        this.estbDate = estbDate;
    }

    public double getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(double adminFee) {
        this.adminFee = adminFee;
    }

    public double getMgtFee() {
        return mgtFee;
    }

    public void setMgtFee(double mgtFee) {
        this.mgtFee = mgtFee;
    }

    public double getCustodianFee() {
        return custodianFee;
    }

    public void setCustodianFee(double custodianFee) {
        this.custodianFee = custodianFee;
    }

    public double getPerformanceFee() {
        return performanceFee;
    }

    public void setPerformanceFee(double performanceFee) {
        this.performanceFee = performanceFee;
    }

    public double getRedemptionFee() {
        return redemptionFee;
    }

    public void setRedemptionFee(double redemptionFee) {
        this.redemptionFee = redemptionFee;
    }

    public double getSubsequentSubs() {
        return subsequentSubs;
    }

    public void setSubsequentSubs(double subsequentSubs) {
        this.subsequentSubs = subsequentSubs;
    }

    public double getMinSubscription() {
        return minSubscription;
    }

    public void setMinSubscription(double minSubscription) {
        this.minSubscription = minSubscription;
    }

    public double getSubscriptionFee() {
        return subscriptionFee;
    }

    public void setSubscriptionFee(double subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getChangePer1M() {
        return changePer1M;
    }

    public void setChangePer1M(double changePer1M) {
        this.changePer1M = changePer1M;
    }

    public double getChangePer3Y() {
        return changePer3Y;
    }

    public void setChangePer3Y(double changePer3Y) {
        this.changePer3Y = changePer3Y;
    }

    public double getChangePer5Y() {
        return changePer5Y;
    }

    public void setChangePer5Y(double changePer5Y) {
        this.changePer5Y = changePer5Y;
    }

    public double getLow52Week() {
        return low52Week;
    }

    public void setLow52Week(double low52Week) {
        this.low52Week = low52Week;
    }

    public double getHigh52Week() {
        return high52Week;
    }

    public void setHigh52Week(double high52Week) {
        this.high52Week = high52Week;
    }

    public String getFocusedRegions() {
        return focusedRegions;
    }

    public void setFocusedRegions(String focusedRegions) {
        this.focusedRegions = focusedRegions;
    }

    public String getFocusedCountries() {
        return focusedCountries;
    }

    public void setFocusedCountries(String focusedCountries) {
        this.focusedCountries = focusedCountries;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFundFinancialYearStartDay() {
        return fundFinancialYearStartDay;
    }

    public void setFundFinancialYearStartDay(Integer fundFinancialYearStartDay) {
        this.fundFinancialYearStartDay = fundFinancialYearStartDay;
    }

    public Integer getFundFinancialYearStartMonth() {
        return fundFinancialYearStartMonth;
    }

    public void setFundFinancialYearStartMonth(Integer fundFinancialYearStartMonth) {
        this.fundFinancialYearStartMonth = fundFinancialYearStartMonth;
    }

    public Integer getFundFinancialYearEndDay() {
        return fundFinancialYearEndDay;
    }

    public void setFundFinancialYearEndDay(Integer fundFinancialYearEndDay) {
        this.fundFinancialYearEndDay = fundFinancialYearEndDay;
    }

    public Integer getFundFinancialYearEndMonth() {
        return fundFinancialYearEndMonth;
    }

    public void setFundFinancialYearEndMonth(Integer fundFinancialYearEndMonth) {
        this.fundFinancialYearEndMonth = fundFinancialYearEndMonth;
    }

    public Integer getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(Integer fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getManagedCountries() {
        return managedCountries;
    }

    public void setManagedCountries(String managedCountries) {
        this.managedCountries = managedCountries;
    }

    public String getIssuedCountries() {
        return issuedCountries;
    }

    public void setIssuedCountries(String issuedCountries) {
        this.issuedCountries = issuedCountries;
    }

    public double getMonthlyReturn3Y() {
        return monthlyReturn3Y;
    }

    public void setMonthlyReturn3Y(double monthlyReturn3Y) {
        this.monthlyReturn3Y = monthlyReturn3Y;
    }

    public double getMonthlyReturn5Y() {
        return monthlyReturn5Y;
    }

    public void setMonthlyReturn5Y(double monthlyReturn5Y) {
        this.monthlyReturn5Y = monthlyReturn5Y;
    }

    public double getAnnualReturn3Y() {
        return annualReturn3Y;
    }

    public void setAnnualReturn3Y(double annualReturn3Y) {
        this.annualReturn3Y = annualReturn3Y;
    }

    public double getAnnualReturn5Y() {
        return annualReturn5Y;
    }

    public void setAnnualReturn5Y(double annualReturn5Y) {
        this.annualReturn5Y = annualReturn5Y;
    }

    public double getStdDevAnnualReturn3Y() {
        return stdDevAnnualReturn3Y;
    }

    public void setStdDevAnnualReturn3Y(double stdDevAnnualReturn3Y) {
        this.stdDevAnnualReturn3Y = stdDevAnnualReturn3Y;
    }

    public double getStdDevAnnualReturn5Y() {
        return stdDevAnnualReturn5Y;
    }

    public void setStdDevAnnualReturn5Y(double stdDevAnnualReturn5Y) {
        this.stdDevAnnualReturn5Y = stdDevAnnualReturn5Y;
    }

    public String getFundClassDesc() {
        return fundClassDesc;
    }

    public void setFundClassDesc(Map<Integer, Map<String, String>> fundClassDesc, String language) {
        this.fundClassDesc = fundClassDesc.get(fundClass).get(language);
    }

    public void setChange3M(double change3M) {
        this.change3M = change3M;
    }

    public String getManagedCompanyDesc() {
        return managedCompanyDesc;
    }

    public void setManagedCompanyDesc(Map<Integer, Map<String, String>> managedCompanyDesc, String language) {
        if (managedCompanyDesc.get(Integer.parseInt(managedCompanies.replace(",", ""))) != null &&
                managedCompanyDesc.get(Integer.parseInt(managedCompanies.replace(",", ""))).get(language) != null) {
            this.managedCompanyDesc = managedCompanyDesc.get(Integer.parseInt(managedCompanies.replace(",", ""))).get(language);
        }
    }
}
