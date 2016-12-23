package com.dfn.alerts.beans;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 8/6/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommodityDTO implements Serializable {
    private String tickerId = null;
    private String exchange = null;
    private Integer instrumentTypeId;
    private String tradeTime;
    private String tradeDate = null;
    private String previousTradeTime            = null;
    private String previousTradeDate            = null;
    private double change;
    private double percentageChange;
    private double tradePrice;
    private String cmSubscriptionStatus;
    private String shortDescriptionLn;
    private String longDescriptionLn;
    private String currencyId;
    private double high = -1;
    private double low = -1;
    private double open = -1;
    private Map<String,String> shortDescription;
    private Map<String,String> longDescription;

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
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

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getCmSubscriptionStatus() {
        return cmSubscriptionStatus;
    }

    public void setCmSubscriptionStatus(String cmSubscriptionStatus) {
        this.cmSubscriptionStatus = cmSubscriptionStatus;
    }

    public String getShortDescriptionLn() {
        return shortDescriptionLn;
    }

    public void setShortDescriptionLn(String shortDescriptionLn) {
        this.shortDescription = CommonUtils.getLanguageDescriptionMap(shortDescriptionLn);
        this.shortDescriptionLn = shortDescriptionLn;
    }

    public String getLongDescriptionLn() {
        return longDescriptionLn;
    }

    public void setLongDescriptionLn(String longDescriptionLn) {
        this.longDescription = CommonUtils.getLanguageDescriptionMap(longDescriptionLn);
        this.longDescriptionLn = longDescriptionLn;
    }

    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    public Map<String, String> getLongDescription() {
        return longDescription;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
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

    public Integer getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(Integer instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public void setPreviousTradeDate(String previousTradeDate) {
        this.previousTradeDate = previousTradeDate;

    }


}
