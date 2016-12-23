package com.dfn.alerts.beans.tickers.snapshot;

import com.dfn.alerts.beans.tickers.IndexDTO;

/**
 * @author : Lasanthak
 * @version : 4/5/13 , 2:51 PM
 * @see
 */
public class IndexSnapshotDTO extends IndexDTO {

    private String description           = null;
    private double indexValue            = -1;
    private double indexOpen             = -1;
    private double adjPrevClosed         = -1;
    private Double netChange             = null;
    private Double pcntChange            = null;
    private double indexHigh             = -1;
    private double indexLow              = -1;
    private long volume                  = -1;
    private double turnover              = -1;
    private String indexTime             = null;
    private double weightedIndexValue    = -1;
    private double indexClose            = -1;
    private int decimalCorrFactor        = 1;
    private int instrumentType           = 1;
    private char transactionType         = 'N';
    private double forcastedIndexValue   = -1;
    private Double forcastedNetChange    = null;
    private Double forcastedPctChange    = null;
    private int isForecasting            = -1;
    private float sessionPecentageChange = -1;
    private float weekPecentageChange    = -1;
    private float monthPecentageChange   = -1;
    private Float yearPecentageChange    = null;
    private float sessionChange          = -1;
    private float weekChange             = -1;
    private float monthChange            = -1;
    private Float yearChange             = null;
    //High Price Of 52Weeks	float	W
    private float high52Week             = -1;
    //Low Price Of 52Weeks
    private float low52Week              = -1;
    private String lastUpdatedDateTime   = null;   // Updating from CM
    private String cmSubscriptionStatus;
    private double marketCap                = -1;
    private Double changeMarketCap          = null;
    private double average30DTurnover       = -1;
    private double average30DTransaction    = -1;
    private long numberOfUps                 = -1;
    private long numberOfDowns               = -1;
    private long numberOfNoChanges           = -1;
    private long tradedSymbols               = -1;
    private long numberOfTrades               = -1;

    private Float priceChangeYTD         = null;
    private Float pricePercentageChangeYTD         = null;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(double indexValue) {
        this.indexValue = indexValue;
    }

    public double getIndexOpen() {
        return indexOpen;
    }

    public void setIndexOpen(double indexOpen) {
        this.indexOpen = indexOpen;
    }

    public double getAdjPrevClosed() {
        return adjPrevClosed;
    }

    public void setAdjPrevClosed(double adjPrevClosed) {
        this.adjPrevClosed = adjPrevClosed;
    }

    public double getIndexHigh() {
        return indexHigh;
    }

    public void setIndexHigh(double indexHigh) {
        this.indexHigh = indexHigh;
    }

    public double getIndexLow() {
        return indexLow;
    }

    public void setIndexLow(double indexLow) {
        this.indexLow = indexLow;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public String getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(String indexTime) {
        this.indexTime = indexTime;
    }

    public double getWeightedIndexValue() {
        return weightedIndexValue;
    }

    public void setWeightedIndexValue(double weightedIndexValue) {
        this.weightedIndexValue = weightedIndexValue;
    }

    public double getIndexClose() {
        return indexClose;
    }

    public void setIndexClose(double indexClose) {
        this.indexClose = indexClose;
    }

    public int getDecimalCorrFactor() {
        return decimalCorrFactor;
    }

    public void setDecimalCorrFactor(int decimalCorrFactor) {
        this.decimalCorrFactor = decimalCorrFactor;
    }

    public int getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(int instrumentType) {
        this.instrumentType = instrumentType;
    }

    public char getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(char transactionType) {
        this.transactionType = transactionType;
    }

    public double getForcastedIndexValue() {
        return forcastedIndexValue;
    }

    public void setForcastedIndexValue(double forcastedIndexValue) {
        this.forcastedIndexValue = forcastedIndexValue;
    }

    public Double getNetChange() {
        return netChange;
    }

    public void setNetChange(Double netChange) {
        this.netChange = netChange;
    }

    public Double getPcntChange() {
        return pcntChange;
    }

    public void setPcntChange(Double pcntChange) {
        this.pcntChange = pcntChange;
    }

    public Double getForcastedNetChange() {
        return forcastedNetChange;
    }

    public void setForcastedNetChange(Double forcastedNetChange) {
        this.forcastedNetChange = forcastedNetChange;
    }

    public Double getForcastedPctChange() {
        return forcastedPctChange;
    }

    public void setForcastedPctChange(Double forcastedPctChange) {
        this.forcastedPctChange = forcastedPctChange;
    }

    public int getForecasting() {
        return isForecasting;
    }

    public void setForecasting(int forecasting) {
        isForecasting = forecasting;
    }

    public float getSessionPecentageChange() {
        return sessionPecentageChange;
    }

    public void setSessionPecentageChange(float sessionPecentageChange) {
        this.sessionPecentageChange = sessionPecentageChange;
    }

    public float getWeekPecentageChange() {
        return weekPecentageChange;
    }

    public void setWeekPecentageChange(float weekPecentageChange) {
        this.weekPecentageChange = weekPecentageChange;
    }

    public float getMonthPecentageChange() {
        return monthPecentageChange;
    }

    public void setMonthPecentageChange(float monthPecentageChange) {
        this.monthPecentageChange = monthPecentageChange;
    }

    public Float getYearPecentageChange() {
        return yearPecentageChange;
    }

    public void setYearPecentageChange(Float yearPecentageChange) {
        this.yearPecentageChange = yearPecentageChange;
    }

    public float getSessionChange() {
        return sessionChange;
    }

    public void setSessionChange(float sessionChange) {
        this.sessionChange = sessionChange;
    }

    public float getWeekChange() {
        return weekChange;
    }

    public void setWeekChange(float weekChange) {
        this.weekChange = weekChange;
    }

    public float getMonthChange() {
        return monthChange;
    }

    public void setMonthChange(float monthChange) {
        this.monthChange = monthChange;
    }

    public Float getYearChange() {
        return yearChange;
    }

    public void setYearChange(Float yearChange) {
        this.yearChange = yearChange;
    }

    public float getHigh52Week() {
        return high52Week;
    }

    public void setHigh52Week(float high52Week) {
        this.high52Week = high52Week;
    }

    public float getLow52Week() {
        return low52Week;
    }

    public void setLow52Week(float low52Week) {
        this.low52Week = low52Week;
    }


    public String getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String getCmSubscriptionStatus() {
        return cmSubscriptionStatus;
    }

    public void setCmSubscriptionStatus(String cmSubscriptionStatus) {
        this.cmSubscriptionStatus = cmSubscriptionStatus;
    }


    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getChangeMarketCap() {
        return changeMarketCap;
    }

    public void setChangeMarketCap(double changeMarketCap) {
        this.changeMarketCap = changeMarketCap;
    }

    public double getAverage30DTransaction() {
        return average30DTransaction;
    }

    public void setAverage30DTransaction(double average30DTransaction) {
        this.average30DTransaction = average30DTransaction;
    }

    public double getAverage30DTurnover() {
        return average30DTurnover;
    }

    public void setAverage30DTurnover(double average30DTurnover) {
        this.average30DTurnover = average30DTurnover;
    }

    public long getNumberOfUps() {
        return numberOfUps;
    }

    public void setNumberOfUps(long numberOfUps) {
        this.numberOfUps = numberOfUps;
    }

    public long getNumberOfDowns() {
        return numberOfDowns;
    }

    public void setNumberOfDowns(long numberOfDowns) {
        this.numberOfDowns = numberOfDowns;
    }

    public long getNumberOfNoChanges() {
        return numberOfNoChanges;
    }

    public void setNumberOfNoChanges(long numberOfNoChanges) {
        this.numberOfNoChanges = numberOfNoChanges;
    }

    public long getTradedSymbols() {
        return tradedSymbols;
    }

    public void setTradedSymbols(long tradedSymbols) {
        this.tradedSymbols = tradedSymbols;
    }

    public long getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setNumberOfTrades(long numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public Float getPriceChangeYTD() {
        return priceChangeYTD;
    }

    public void setPriceChangeYTD(Float priceChangeYTD) {
        this.priceChangeYTD = priceChangeYTD;
    }

    public Float getPricePercentageChangeYTD() {
        return pricePercentageChangeYTD;
    }

    public void setPricePercentageChangeYTD(Float pricePercentageChangeYTD) {
        this.pricePercentageChangeYTD = pricePercentageChangeYTD;
    }
}
