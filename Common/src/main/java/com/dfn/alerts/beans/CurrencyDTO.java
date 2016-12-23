package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 7/29/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CurrencyDTO implements Serializable {
    private String currencyPair = null;
    private String exchange = null;
    private String tradeTime;
    private String tradeDate = null;
    private String previousTradeTime            = null;
    private String previousTradeDate            = null;
    private double change;
    private double percentageChange;
    private double currencyRate;
    private double high = -1;
    private double low = -1;
    private double open = -1;
    private String cmSubscriptionStatus;

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getCmSubscriptionStatus() {
        return cmSubscriptionStatus;
    }

    public void setCmSubscriptionStatus(String cmSubscriptionStatus) {
        this.cmSubscriptionStatus = cmSubscriptionStatus;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {

        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
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
}
