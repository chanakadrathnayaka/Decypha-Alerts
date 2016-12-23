package com.dfn.alerts.beans.tickers;

import com.dfn.alerts.utils.FormatUtils;
import org.apache.commons.lang3.CharUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dfn.alerts.constants.IConstants.Delimiter.COMMA;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/28/14
 * Time: 2:44 PM
 */
public class IndexDTO extends TickerDTO {

    private String relatedExchanges;
    private String relatedCountries;
    private String relatedRegions;

    private List<String> exchanges;
    private List<String> countries;
    private List<String> regions;

    private String sectorId;
    private int provider;
    private Date launchDate;

    private int noOfConstituents;
    private String constituents;
    private List<String> constituentList;

    private String updateFrequency;

    /**
     * SNAPSHOT COLUMNS - TODO : MOVE TO SEPARATE CLASS
     */
    private Double lastTradePrice;
    private Timestamp lastTradeDate;

    private Double annualizedReturnQtd = null;
    private Double annualizedReturnYtd = null;
    private Double annualizedReturn1Y = null;
    private Double annualizedReturn2Y = null;
    private Double annualizedReturn3Y = null;
    private Double annualizedReturn5Y = null;
    private Double annualizedReturnLifetime = null;

    private Double monthlyHigh = null;
    private Date monthlyHighDate;
    private Double monthlyLow = null;
    private Date monthlyLowDate;

    private Double quarterlyHigh = null;
    private Date quarterlyHighDate;
    private Double quarterlyLow = null;
    private Date quarterlyLowDate;

    private Double ytdHigh = null;
    private Date ytdHighDate;
    private Double ytdLow = null;
    private Date ytdLowDate;

    private Double highPriceOf52Weeks = null;
    private Date highPriceDateOf52Weeks;
    private Double lowPriceOf52Weeks = null;
    private Date lowPriceDateOf52Weeks;

    private Double twoYearHigh = null;
    private Date twoYearHighDate;
    private Double twoYearLow = null;
    private Date twoYearLowDate;

    private Double threeYearHigh = null;
    private Date threeYearHighDate;
    private Double threeYearLow = null;
    private Date threeYearLowDate;

    private Double lifetimeHigh = null;
    private Date lifetimeHighDate;
    private Double lifetimeLow = null;
    private Date lifetimeLowDate;

    private Date closeStartDate;
    private Integer lotSize;
    private String unit;

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRelatedExchanges(String relatedExchanges) {
        if (relatedExchanges != null && !relatedExchanges.isEmpty()) {
            exchanges = Arrays.asList(relatedExchanges.split(CharUtils.toString(COMMA)));
        } else{
            exchanges = Collections.emptyList();
        }
        this.relatedExchanges = relatedExchanges;
    }

    public List<String> getExchanges() {
        return exchanges;
    }

    public void setRelatedCountries(String relatedCountries) {
        if (relatedCountries != null && !relatedCountries.isEmpty()) {
            countries = Arrays.asList(relatedCountries.split(CharUtils.toString(COMMA)));
        }else{
            countries = Collections.emptyList();
        }
        this.relatedCountries = relatedCountries;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setRelatedRegions(String relatedRegions) {
        if (relatedRegions != null && !relatedRegions.isEmpty()) {
            regions = Arrays.asList(relatedRegions.split(CharUtils.toString(COMMA)));
        }else{
            regions = Collections.emptyList();
        }
        this.relatedRegions = relatedRegions;
    }

    public List<String> getRegions() {
        return regions;
    }

    public String getRelatedExchanges() {
        return relatedExchanges;
    }

    public String getRelatedCountries() {
        return relatedCountries;
    }

    public String getRelatedRegions() {
        return relatedRegions;
    }

    public String getConstituents() {
        return constituents;
    }

    public void setConstituents(String constituents) {
        noOfConstituents = 0;
        if(constituents != null && !constituents.isEmpty()){
            constituents = FormatUtils.removeConsecutiveCommas(constituents);
            constituentList = Arrays.asList(constituents.split(CharUtils.toString(COMMA)));
            noOfConstituents = constituentList.size();
        }else{
            constituentList = Collections.emptyList();
        }
        this.constituents = constituents;
    }

    public int getNoOfConstituents() {
        return noOfConstituents;
    }

    public List<String> getConstituentList() {
        return constituentList;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Double getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(Double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public Timestamp getLastTradeDate() {
        return lastTradeDate;
    }

    public void setLastTradeDate(Timestamp lastTradeDate) {
        this.lastTradeDate = lastTradeDate;
    }

    public Double getAnnualizedReturnQtd() {
        return annualizedReturnQtd;
    }

    public void setAnnualizedReturnQtd(Double annualizedReturnQtd) {
        this.annualizedReturnQtd = annualizedReturnQtd;
    }

    public Double getAnnualizedReturnYtd() {
        return annualizedReturnYtd;
    }

    public void setAnnualizedReturnYtd(Double annualizedReturnYtd) {
        this.annualizedReturnYtd = annualizedReturnYtd;
    }

    public Double getAnnualizedReturn1Y() {
        return annualizedReturn1Y;
    }

    public void setAnnualizedReturn1Y(Double annualizedReturn1Y) {
        this.annualizedReturn1Y = annualizedReturn1Y;
    }

    public Double getAnnualizedReturn2Y() {
        return annualizedReturn2Y;
    }

    public void setAnnualizedReturn2Y(Double annualizedReturn2Y) {
        this.annualizedReturn2Y = annualizedReturn2Y;
    }

    public Double getAnnualizedReturn3Y() {
        return annualizedReturn3Y;
    }

    public void setAnnualizedReturn3Y(Double annualizedReturn3Y) {
        this.annualizedReturn3Y = annualizedReturn3Y;
    }

    public Double getAnnualizedReturn5Y() {
        return annualizedReturn5Y;
    }

    public void setAnnualizedReturn5Y(Double annualizedReturn5Y) {
        this.annualizedReturn5Y = annualizedReturn5Y;
    }

    public Double getAnnualizedReturnLifetime() {
        return annualizedReturnLifetime;
    }

    public void setAnnualizedReturnLifetime(Double annualizedReturnLifetime) {
        this.annualizedReturnLifetime = annualizedReturnLifetime;
    }

    public Double getMonthlyHigh() {
        return monthlyHigh;
    }

    public void setMonthlyHigh(Double monthlyHigh) {
        this.monthlyHigh = monthlyHigh;
    }

    public Date getMonthlyHighDate() {
        return monthlyHighDate;
    }

    public void setMonthlyHighDate(Date monthlyHighDate) {
        this.monthlyHighDate = monthlyHighDate;
    }

    public Double getMonthlyLow() {
        return monthlyLow;
    }

    public void setMonthlyLow(Double monthlyLow) {
        this.monthlyLow = monthlyLow;
    }

    public Date getMonthlyLowDate() {
        return monthlyLowDate;
    }

    public void setMonthlyLowDate(Date monthlyLowDate) {
        this.monthlyLowDate = monthlyLowDate;
    }

    public Double getQuarterlyHigh() {
        return quarterlyHigh;
    }

    public void setQuarterlyHigh(Double quarterlyHigh) {
        this.quarterlyHigh = quarterlyHigh;
    }

    public Date getQuarterlyHighDate() {
        return quarterlyHighDate;
    }

    public void setQuarterlyHighDate(Date quarterlyHighDate) {
        this.quarterlyHighDate = quarterlyHighDate;
    }

    public Double getQuarterlyLow() {
        return quarterlyLow;
    }

    public void setQuarterlyLow(Double quarterlyLow) {
        this.quarterlyLow = quarterlyLow;
    }

    public Date getQuarterlyLowDate() {
        return quarterlyLowDate;
    }

    public void setQuarterlyLowDate(Date quarterlyLowDate) {
        this.quarterlyLowDate = quarterlyLowDate;
    }

    public Double getYtdHigh() {
        return ytdHigh;
    }

    public void setYtdHigh(Double ytdHigh) {
        this.ytdHigh = ytdHigh;
    }

    public Date getYtdHighDate() {
        return ytdHighDate;
    }

    public void setYtdHighDate(Date ytdHighDate) {
        this.ytdHighDate = ytdHighDate;
    }

    public Double getYtdLow() {
        return ytdLow;
    }

    public void setYtdLow(Double ytdLow) {
        this.ytdLow = ytdLow;
    }

    public Date getYtdLowDate() {
        return ytdLowDate;
    }

    public void setYtdLowDate(Date ytdLowDate) {
        this.ytdLowDate = ytdLowDate;
    }

    public Double getTwoYearHigh() {
        return twoYearHigh;
    }

    public void setTwoYearHigh(Double twoYearHigh) {
        this.twoYearHigh = twoYearHigh;
    }

    public Date getTwoYearHighDate() {
        return twoYearHighDate;
    }

    public void setTwoYearHighDate(Date twoYearHighDate) {
        this.twoYearHighDate = twoYearHighDate;
    }

    public Double getTwoYearLow() {
        return twoYearLow;
    }

    public void setTwoYearLow(Double twoYearLow) {
        this.twoYearLow = twoYearLow;
    }

    public Date getTwoYearLowDate() {
        return twoYearLowDate;
    }

    public void setTwoYearLowDate(Date twoYearLowDate) {
        this.twoYearLowDate = twoYearLowDate;
    }

    public Double getThreeYearHigh() {
        return threeYearHigh;
    }

    public void setThreeYearHigh(Double threeYearHigh) {
        this.threeYearHigh = threeYearHigh;
    }

    public Date getThreeYearHighDate() {
        return threeYearHighDate;
    }

    public void setThreeYearHighDate(Date threeYearHighDate) {
        this.threeYearHighDate = threeYearHighDate;
    }

    public Double getThreeYearLow() {
        return threeYearLow;
    }

    public void setThreeYearLow(Double threeYearLow) {
        this.threeYearLow = threeYearLow;
    }

    public Date getThreeYearLowDate() {
        return threeYearLowDate;
    }

    public void setThreeYearLowDate(Date threeYearLowDate) {
        this.threeYearLowDate = threeYearLowDate;
    }

    public Double getLifetimeHigh() {
        return lifetimeHigh;
    }

    public void setLifetimeHigh(Double lifetimeHigh) {
        this.lifetimeHigh = lifetimeHigh;
    }

    public Date getLifetimeHighDate() {
        return lifetimeHighDate;
    }

    public void setLifetimeHighDate(Date lifetimeHighDate) {
        this.lifetimeHighDate = lifetimeHighDate;
    }

    public Double getLifetimeLow() {
        return lifetimeLow;
    }

    public void setLifetimeLow(Double lifetimeLow) {
        this.lifetimeLow = lifetimeLow;
    }

    public Date getLifetimeLowDate() {
        return lifetimeLowDate;
    }

    public void setLifetimeLowDate(Date lifetimeLowDate) {
        this.lifetimeLowDate = lifetimeLowDate;
    }

    public Double getHighPriceOf52Weeks() {
        return highPriceOf52Weeks;
    }

    public void setHighPriceOf52Weeks(Double highPriceOf52Weeks) {
        this.highPriceOf52Weeks = highPriceOf52Weeks;
    }

    public Date getHighPriceDateOf52Weeks() {
        return highPriceDateOf52Weeks;
    }

    public void setHighPriceDateOf52Weeks(Date highPriceDateOf52Weeks) {
        this.highPriceDateOf52Weeks = highPriceDateOf52Weeks;
    }

    public Double getLowPriceOf52Weeks() {
        return lowPriceOf52Weeks;
    }

    public void setLowPriceOf52Weeks(Double lowPriceOf52Weeks) {
        this.lowPriceOf52Weeks = lowPriceOf52Weeks;
    }

    public Date getLowPriceDateOf52Weeks() {
        return lowPriceDateOf52Weeks;
    }

    public void setLowPriceDateOf52Weeks(Date lowPriceDateOf52Weeks) {
        this.lowPriceDateOf52Weeks = lowPriceDateOf52Weeks;
    }

    public Date getCloseStartDate() {
        return closeStartDate;
    }

    public void setCloseStartDate(Date closeStartDate) {
        this.closeStartDate = closeStartDate;
    }


    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }
}