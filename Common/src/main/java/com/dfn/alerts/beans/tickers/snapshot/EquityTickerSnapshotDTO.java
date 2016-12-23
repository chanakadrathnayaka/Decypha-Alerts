package com.dfn.alerts.beans.tickers.snapshot;

import com.dfn.alerts.beans.tickers.EquityTickerDTO;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 4/11/13
 * Time: 8:29 AM
 */
@SuppressWarnings("unused")
public class EquityTickerSnapshotDTO extends EquityTickerDTO {

    private String tradeTime            = null;
    private String tradeDate            = null;
    private String previousTradeTime            = null;
    private String previousTradeDate            = null;
    private double tradePrice           = -1;
    private double marketCap            = -1;
    private double marketCapUSD         = -1;
    private double highPriceOf52Weeks   = -1;
    private double lowPriceOf52Weeks    = -1;
    private Double netChange            = null;
    private Double percentChange        = null;
    private Float yearChange            = null;
    private Float yearPecentageChange   = null;
    private Float YTDPriceChang         = null;
    private Float YTDPricePercentageChange         = null;
    private float yearTurnoverRatio     = -1;
    private int noOfTrades=-1;
    private double turnover=-1;
    private long volume=-1;
    private double averageVolume30D = -1;
    private double high = -1;
    private double low = -1;
    private double averageTurnover30D = 0;
    private double averageTransactionValue30D = 0;
    private double vwap = -1;  // average trade price
    private double beta = -1;
    private double bestAskPrice = -1;
    private double bestBidPrice = -1;
    private Date bestAskPriceUpdatedDate;
    private Date bestBidPriceUpdatedDate;

    private double previousClosed = -1;
    private double todaysClose = -1;
    private double open = -1;

    private String ajaxPriceSnapshot = null;
    private String cmSubscriptionStatus;

    private double lastTradedPrice = -1;
    private double correctLastTradedPrice = -1;
    private int adjustedDecimalPlaces = -1;
    //used in js - json
    private double yearlyHighPrice = -1;
    private double yearlyLowPrice = -1;

    private int subUnitLevel = 3;
    private double divideFactor = 1;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public double getLastTradedPrice() {
        return lastTradedPrice;
    }

    /**
     * set last traded price - if traded today then trade price else previous closed
     */
    public void setLastTradedPrice() {
        lastTradedPrice = (tradePrice == 0 && noOfTrades == 0 && previousClosed > 0) ? previousClosed : tradePrice;
        setYearlyHighPrice();
        setYearlyLowPrice();
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
        setLastTradedPrice();
    }

    public double getCorrectedLastTradedPrice(){
        return this.correctLastTradedPrice;
    }

    public void correctLastTradedPrice() {
        this.correctLastTradedPrice = lastTradedPrice;
        if(this.getUseSubunit() != null && this.getUseSubunit() == 1){
            correctLastTradedPrice = lastTradedPrice/divideFactor;
        }
    }

    public int getAdjustedDecimalPlaces(){
       return  this.adjustedDecimalPlaces;
    }

    public void adjustDecimalPlaces() {
        int dp = super.getDecimalPlaces();

            if (dp >= 0) {
                this.adjustedDecimalPlaces = dp;
            } else {
                this.adjustedDecimalPlaces = dp * -1;
            }

            if (this.getUseSubunit() != null && this.getUseSubunit() == 1) {
                this.adjustedDecimalPlaces = this.adjustedDecimalPlaces + subUnitLevel;
            }
    }

    public void setDivideFactor() {
        if(this.getUseSubunit() != null && this.getUseSubunit() == 1){
            this.divideFactor = Math.pow(10,subUnitLevel);
        }
    }

    public double getDivideFactor() {
        return divideFactor;
    }

    public void setAdjustedPrices(){
        setDivideFactor();
        correctLastTradedPrice();
        adjustDecimalPlaces();
    }

    public void setSubUnitLevel(int subUnitLevel) {
        this.subUnitLevel = subUnitLevel;
    }

    /**
     * market cap calculated with ltp
     * @return market cap
     */
    public double getLatestMarketCap() {
        double price = 0.0;
        if(getVwap()>0){
            price = getVwap();
        }
        else if( previousClosed >= 0){
            price = getPreviousClosed();
        }
        return price * getNoOfShares();
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getMarketCapUSD() {
        return marketCapUSD;
    }

    public void setMarketCapUSD(double marketCapUSD) {
        this.marketCapUSD = marketCapUSD;
    }


    public double getYearlyHighPrice() {
        return yearlyHighPrice;
    }

    /**
     * high of 52 weeks taking today's high into account
     */
    public void setYearlyHighPrice() {
        yearlyHighPrice = highPriceOf52Weeks > getLastTradedPrice() ? highPriceOf52Weeks : getLastTradedPrice();
    }

    public double getHighPriceOf52Weeks() {
        return highPriceOf52Weeks;
    }

    public void setHighPriceOf52Weeks(double highPriceOf52Weeks) {
        this.highPriceOf52Weeks = highPriceOf52Weeks;
        setYearlyHighPrice();
    }

    public double getYearlyLowPrice() {
        return yearlyLowPrice;
    }

    /**
     * low of 52 weeks taking today's low into account
     */
    public void setYearlyLowPrice() {
        yearlyLowPrice = lowPriceOf52Weeks < getLastTradedPrice() ? lowPriceOf52Weeks : getLastTradedPrice();
    }

    public double getLowPriceOf52Weeks() {
        return lowPriceOf52Weeks;
    }

    public void setLowPriceOf52Weeks(double lowPriceOf52Weeks) {
        this.lowPriceOf52Weeks = lowPriceOf52Weeks;
        setYearlyLowPrice();
    }

    public Double getNetChange() {
        return netChange;
    }

    public void setNetChange(Double netChange) {
        this.netChange = netChange;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public Float getYearChange() {
        return yearChange;
    }

    public void setYearChange(Float yearChange) {
        this.yearChange = yearChange;
    }

    public Float getYearPecentageChange() {
        return yearPecentageChange;
    }

    public void setYearPecentageChange(Float yearPecentageChange) {
        this.yearPecentageChange = yearPecentageChange;
    }

    public Float getYTDPriceChang() {
        return YTDPriceChang;
    }

    public void setYTDPriceChang(Float YTDPriceChang) {
        this.YTDPriceChang = YTDPriceChang;
    }

    public Float getYTDPricePercentageChange() {
        return YTDPricePercentageChange;
    }

    public void setYTDPricePercentageChange(Float YTDPricePercentageChange) {
        this.YTDPricePercentageChange = YTDPricePercentageChange;
    }

    public float getYearTurnoverRatio() {
        return yearTurnoverRatio;
    }

    public void setYearTurnoverRatio(float yearTurnoverRatio) {
        this.yearTurnoverRatio = yearTurnoverRatio;
    }

    public int getNoOfTrades() {
        return noOfTrades;
    }

    public void setNoOfTrades(int noOfTrades) {
        this.noOfTrades = noOfTrades;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getAverageVolume30D() {
        return averageVolume30D;
    }

    public void setAverageVolume30D(double averageVolume30D) {
        this.averageVolume30D = averageVolume30D;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getAverageTurnover30D() {
        return averageTurnover30D;
    }

    public void setAverageTurnover30D(double averageTurnover30D) {
        this.averageTurnover30D = averageTurnover30D;
    }

    public double getVwap() {
        return vwap;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getPreviousClosed() {
        return previousClosed;
    }

    public void setPreviousClosed(double previousClosed) {
        this.previousClosed = previousClosed;
        setLastTradedPrice();
    }

    public String getAjaxPriceSnapshot() {
        return ajaxPriceSnapshot;
    }

    public void setAjaxPriceSnapshot(String ajaxPriceSnapshot) {
        this.ajaxPriceSnapshot = ajaxPriceSnapshot;
    }

    public String getCmSubscriptionStatus() {
        return cmSubscriptionStatus;
    }

    public void setCmSubscriptionStatus(String cmSubscriptionStatus) {
        this.cmSubscriptionStatus = cmSubscriptionStatus;
    }

    public double getAverageTransactionValue30D() {
        return averageTransactionValue30D;
    }

    public void setAverageTransactionValue30D(double averageTransactionValue30D) {
        this.averageTransactionValue30D = averageTransactionValue30D;
    }

    public double getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(double bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public double getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(double bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public String getPreviousTradeTime() {
        return previousTradeTime;
    }

    public void setPreviousTradeTime(String previousTradeTime) {
        this.previousTradeTime = previousTradeTime;
    }

    public String getPreviousTradeDate() {
        return previousTradeDate;
    }

    public void setPreviousTradeDate(String previousTradeDate) {
        this.previousTradeDate = previousTradeDate;
    }

    public Date getBestAskPriceUpdatedDate() {
        return bestAskPriceUpdatedDate;
    }

    public void setBestAskPriceUpdatedDate(Date bestAskPriceUpdatedDate) {
        this.bestAskPriceUpdatedDate = bestAskPriceUpdatedDate;
    }

    public Date getBestBidPriceUpdatedDate() {
        return bestBidPriceUpdatedDate;
    }

    public void setBestBidPriceUpdatedDate(Date bestBidPriceUpdatedDate) {
        this.bestBidPriceUpdatedDate = bestBidPriceUpdatedDate;
    }

    public double getTodaysClose() {
        return todaysClose;
    }

    public void setTodaysClose(double todaysClose) {
        this.todaysClose = todaysClose;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        EquityTickerSnapshotDTO obj1 = (EquityTickerSnapshotDTO) obj;
        return getTickerSerial().equals(obj1.getTickerSerial());
    }

    @Override
    public int hashCode() {
         return getTickerSerial().intValue();
    }

}
