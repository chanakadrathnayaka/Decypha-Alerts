package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object for price data updated from Price History
 * with non zero values. Price Values can have different last updated times.
 *
 * Created by aravindal on 01/04/14.
 */
public class PriceSnapshotAdjustedDTO implements Serializable {

    private long tickerSerial;
    private double tradePrice;
    private Date tradePriceUpdatedDate;
    private double bestAskPrice;
    private Date bestAskPriceUpdatedDate;
    private double bestBidPrice;
    private Date bestBidPriceUpdatedDate;

    public long getTickerSerial() {
        return tickerSerial;
    }

    public void setTickerSerial(long tickerSerial) {
        this.tickerSerial = tickerSerial;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public Date getTradePriceUpdatedDate() {
        return tradePriceUpdatedDate;
    }

    public void setTradePriceUpdatedDate(Date tradePriceUpdatedDate) {
        this.tradePriceUpdatedDate = tradePriceUpdatedDate;
    }

    public double getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(double bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public Date getBestAskPriceUpdatedDate() {
        return bestAskPriceUpdatedDate;
    }

    public void setBestAskPriceUpdatedDate(Date bestAskPriceUpdatedDate) {
        this.bestAskPriceUpdatedDate = bestAskPriceUpdatedDate;
    }

    public double getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(double bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public Date getBestBidPriceUpdatedDate() {
        return bestBidPriceUpdatedDate;
    }

    public void setBestBidPriceUpdatedDate(Date bestBidPriceUpdatedDate) {
        this.bestBidPriceUpdatedDate = bestBidPriceUpdatedDate;
    }
}
