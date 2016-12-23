package com.dfn.alerts.beans.tickers;

import java.util.Date;
import java.util.Map;

/** Data Transfer Object for Fixed Income Ticker
 * Created by IntelliJ IDEA.
 * User: Duminda
 * Date: 07/04/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.*/
public class FixedIncomeTickerDTO extends TickerDTO{

    private Date couponDateFirst;
    private Date announceDate;
    private Date auctionDate;
    private Date settleDateFirst;
    private Date interestAcrDate;
    private Date maturityDate;
    private Date dateOfIssue;
    private Date nextCouponDate;
    private String wkn;
    private String issuedPeriod;
    private String countryOfIssue;
    private String maturityPeriod;
    private double amountIssued;
    private double amountIssuedUSD;
    private double amountOutstanding;
    private double minimumPiece;
    private double minimumIncrement;
    private double issuePrice;
    private double couponRate;
    private double minPrice;
    private double yield;//not sure
    private double auctionAmount;
    private double issueMinRate;
    private double issueMaxRate;
    private double issueWgtAvgRate;
    private double floatingCouponA;
    private double floatingCouponB;
    private double floatingCcouponC;
    private double floatingCouponD;
    private double floatingCouponE;
    private double floatingCouponIbor1;
    private double floatingCouponIbor2;
    private double floatingCouponTbill1;
    private double floatingCouponTbill2;
    private double auctionMinRate;
    private double auctionMaxRate;
    private double auctionWgtAvgRate;
    private Integer sukukType;
    private Integer leadManager;
    private Integer marketType;
    private Integer bondType;
    private Integer couponFrequency;
    private Integer couponType;
    private Integer couponDayCount;
    private Long auctionBids;
    private Long issueBids;
    private Integer remainingCouponCount;
    private Date lastCouponDate;
    private Date lastTradedDate;
    private Double requiredAmount;
    private double parValue;
    private double faceValue;
    private Double amountIssuedInIssuerCurrency;
    private String couponDayCountDesc;
    private String embeddedOption;
    private int tradingClearanceDays;
    private double ytm;
    private double amountIssuedInLatestUsd;
    private Integer isPricePercentage;
    private String typeDesc;
    private String countryDes;

    public double getParValue() {
        return parValue;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }
    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    public void setCouponDateFirst(Date couponDateFirst) {
        this.couponDateFirst = couponDateFirst;
    }

    public Date getCouponDateFirst() {
        return this.couponDateFirst;
    }

    public void setAnnounceDate(Date announceDate) {
        this.announceDate = announceDate;
    }

    public Date getAnnounceDate() {
        return this.announceDate;
    }

    public void setAuctionDate(Date auctionDate) {
        this.auctionDate = auctionDate;
    }

    public Date getAuctionDate() {
        return this.auctionDate;
    }

    public void setSettleDateFirst(Date settleDateFirst) {
        this.settleDateFirst = settleDateFirst;
    }

    public Date getSettleDateFirst() {
        return this.settleDateFirst;
    }

    public void setInterestAcrDate(Date interestAcrDate) {
        this.interestAcrDate = interestAcrDate;
    }

    public Date getInterestAcrDate() {
        return this.interestAcrDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Date getMaturityDate() {
        return this.maturityDate;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDateOfIssue() {
        return this.dateOfIssue;
    }


    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getWkn() {
        return this.wkn;
    }

    public void setIssuedPeriod(String issuedPeriod) {
        this.issuedPeriod = issuedPeriod;
    }

    public String getIssuedPeriod() {
        return this.issuedPeriod;
    }

    public void setCountryOfIssue(String countryOfIssue) {
        this.countryOfIssue = countryOfIssue;
    }

    public String getCountryOfIssue() {
        return this.countryOfIssue;
    }

    public String getMaturityPeriod() {
        return maturityPeriod;
    }

    public void setMaturityPeriod(String maturityPeriod) {
        this.maturityPeriod = maturityPeriod;
    }

    public double getAmountIssued() {
        return amountIssued;
    }

    public void setAmountIssued(double amountIssued) {
        this.amountIssued = amountIssued;
    }


    public double getAmountIssuedUSD() {
        return amountIssuedUSD;
    }

    public void setAmountIssuedUSD(double amountIssuedUSD) {
        this.amountIssuedUSD = amountIssuedUSD;
    }

    public double getAmountOutstanding() {
        return amountOutstanding;
    }

    public void setAmountOutstanding(double amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    public double getMinimumIncrement() {
        return minimumIncrement;
    }

    public void setMinimumIncrement(double minimumIncrement) {
        this.minimumIncrement = minimumIncrement;
    }

    public double getIssuePrice() {
        return issuePrice;
    }

    public void setIssuePrice(double issuePrice) {
        this.issuePrice = issuePrice;
    }

    public double getMinimumPiece() {
        return minimumPiece;
    }

    public void setMinimumPiece(double minimumPiece) {
        this.minimumPiece = minimumPiece;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(double couponRate) {
        this.couponRate = couponRate;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(double auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    public double getFloatingCouponA() {
        return floatingCouponA;
    }

    public void setFloatingCouponA(double floatingCouponA) {
        this.floatingCouponA = floatingCouponA;
    }

    public double getFloatingCouponD() {
        return floatingCouponD;
    }

    public void setFloatingCouponD(double floatingCouponD) {
        this.floatingCouponD = floatingCouponD;
    }

    public double getFloatingCouponIbor2() {
        return floatingCouponIbor2;
    }

    public void setFloatingCouponIbor2(double floatingCouponIbor2) {
        this.floatingCouponIbor2 = floatingCouponIbor2;
    }

    public Long getIssueBids() {
        return issueBids;
    }

    public void setIssueBids(Long issueBids) {
        this.issueBids = issueBids;
    }

    public Long getAuctionBids() {
        return auctionBids;
    }

    public void setAuctionBids(Long auctionBids) {
        this.auctionBids = auctionBids;
    }

    public Integer getCouponDayCount() {
        return couponDayCount;
    }

    public void setCouponDayCount(Integer couponDayCount) {
        this.couponDayCount = couponDayCount;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getCouponFrequency() {
        return couponFrequency;
    }

    public void setCouponFrequency(Integer couponFrequency) {
        this.couponFrequency = couponFrequency;
    }

    public Integer getBondType() {
        return bondType;
    }

    public void setBondType(Integer bondType) {
        this.bondType = bondType;
    }

    public Integer getMarketType() {
        return marketType;
    }

    public void setMarketType(Integer marketType) {
        this.marketType = marketType;
    }

    public Integer getLeadManager() {
        return leadManager;
    }

    public void setLeadManager(Integer leadManager) {
        this.leadManager = leadManager;
    }

    public Integer getSukukType() {
        return sukukType;
    }

    public void setSukukType(Integer sukukType) {
        this.sukukType = sukukType;
    }

    public double getAuctionWgtAvgRate() {
        return auctionWgtAvgRate;
    }

    public void setAuctionWgtAvgRate(double auctionWgtAvgRate) {
        this.auctionWgtAvgRate = auctionWgtAvgRate;
    }

    public double getAuctionMaxRate() {
        return auctionMaxRate;
    }

    public void setAuctionMaxRate(double auctionMaxRate) {
        this.auctionMaxRate = auctionMaxRate;
    }

    public double getAuctionMinRate() {
        return auctionMinRate;
    }

    public void setAuctionMinRate(double auctionMinRate) {
        this.auctionMinRate = auctionMinRate;
    }

    public double getFloatingCouponTbill2() {
        return floatingCouponTbill2;
    }

    public void setFloatingCouponTbill2(double floatingCouponTbill2) {
        this.floatingCouponTbill2 = floatingCouponTbill2;
    }

    public double getFloatingCouponTbill1() {
        return floatingCouponTbill1;
    }

    public void setFloatingCouponTbill1(double floatingCouponTbill1) {
        this.floatingCouponTbill1 = floatingCouponTbill1;
    }

    public double getFloatingCouponIbor1() {
        return floatingCouponIbor1;
    }

    public void setFloatingCouponIbor1(double floatingCouponIbor1) {
        this.floatingCouponIbor1 = floatingCouponIbor1;
    }

    public double getFloatingCouponE() {
        return floatingCouponE;
    }

    public void setFloatingCouponE(double floatingCouponE) {
        this.floatingCouponE = floatingCouponE;
    }

    public double getFloatingCouponB() {
        return floatingCouponB;
    }

    public void setFloatingCouponB(double floatingCouponB) {
        this.floatingCouponB = floatingCouponB;
    }

    public double getFloatingCcouponC() {
        return floatingCcouponC;
    }

    public void setFloatingCcouponC(double floatingCcouponC) {
        this.floatingCcouponC = floatingCcouponC;
    }

    public double getIssueWgtAvgRate() {
        return issueWgtAvgRate;
    }

    public void setIssueWgtAvgRate(double issueWgtAvgRate) {
        this.issueWgtAvgRate = issueWgtAvgRate;
    }

    public double getIssueMaxRate() {
        return issueMaxRate;
    }

    public void setIssueMaxRate(double issueMaxRate) {
        this.issueMaxRate = issueMaxRate;
    }

    public double getIssueMinRate() {
        return issueMinRate;
    }

    public void setIssueMinRate(double issueMinRate) {
        this.issueMinRate = issueMinRate;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public Date getNextCouponDate() {
        return nextCouponDate;
    }

    public void setNextCouponDate(Date nextCouponDate) {
        this.nextCouponDate = nextCouponDate;
    }

    public Integer getRemainingCouponCount() {
        return remainingCouponCount;
    }

    public void setRemainingCouponCount(Integer remainingCouponCount) {
        this.remainingCouponCount = remainingCouponCount;
    }

    public Date getLastCouponDate() {
        return lastCouponDate;
    }

    public void setLastCouponDate(Date lastCouponDate) {
        this.lastCouponDate = lastCouponDate;
    }

    public Date getLastTradedDate() {
        return lastTradedDate;
    }

    public void setLastTradedDate(Date lastTradedDate) {
        this.lastTradedDate = lastTradedDate;
    }

    public Double getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(Double requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public Double getAmountIssuedInIssuerCurrency() {
        return amountIssuedInIssuerCurrency;
    }

    public void setAmountIssuedInIssuerCurrency(Double amountIssuedInIssuerCurrency) {
        this.amountIssuedInIssuerCurrency = amountIssuedInIssuerCurrency;
    }

    public String getCouponDayCountDesc() {
        return couponDayCountDesc;
    }

    public void setCouponDayCountDesc(String couponDayCountDesc) {
        this.couponDayCountDesc = couponDayCountDesc;
    }

    public String getEmbeddedOption() {
        return embeddedOption;
    }

    public void setEmbeddedOption(String embeddedOption) {
        this.embeddedOption = embeddedOption;
    }

    public Integer getTradingClearanceDays() {
        return tradingClearanceDays;
    }

    public void setTradingClearanceDays(int tradingClearanceDays) {
        this.tradingClearanceDays = tradingClearanceDays;
    }

    public double getYtm() {
        return ytm;
    }

    public void setYtm(double ytm) {
        this.ytm = ytm;
    }

    public double getAmountIssuedInLatestUsd() {
        return amountIssuedInLatestUsd;
    }

    public void setAmountIssuedInLatestUsd(double amountIssuedInLatestUsd) {
        this.amountIssuedInLatestUsd = amountIssuedInLatestUsd;
    }

    public Integer getIsPricePercentage() {
        return isPricePercentage;
    }

    public void setIsPricePercentage(Integer isPricePercentage) {
        this.isPricePercentage = isPricePercentage;
    }

    public void setTypeDesc(Map<String, Map<String, String>> bondTypeDesc,Map<String, Map<String, String>> susukTypeDesc,String language) {
        Map<String, String> descriptions;
        if (bondType != null && bondTypeDesc != null) {
            descriptions = bondTypeDesc.get(Integer.toString(bondType));
            if (descriptions != null && descriptions.get(language) != null) {
                typeDesc = descriptions.get(language);
            }
        } else if (sukukType != null && susukTypeDesc != null) {
            descriptions = susukTypeDesc.get(Integer.toString(sukukType));
            if (descriptions != null && descriptions.get(language) != null) {
                typeDesc = descriptions.get(language);
            }
        }
    }

    public String getTypeDesc(){
        return typeDesc;
    }

    public void setCountryDes(Map<String, Map<String, String>> countryDes,String language) {
        Map<String, String> descriptions = countryDes.get(countryOfIssue);
        if (descriptions != null && language != "") {
            this.countryDes = descriptions.get(language);
        }
    }

    public String getCountryDes() {
        return countryDes;
    }
}
