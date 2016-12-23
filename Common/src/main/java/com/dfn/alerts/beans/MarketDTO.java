package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 4/11/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarketDTO implements Serializable {

    private String exchangeCode;
    //DES
    private String description;
    //
    private String mainIndex;
    //TZ
    private String timeZone;
    //CC
    private String countryCode;
    //DEF
    private String decimalCorrectionFactor;
    //CUR
    private String currency;
    //DEP
    private String decimalPlaces;
    //VID
    private String versionID;
    //DE
    private String displayExchange;
    //TZ
    private String timeZoneOffset;
    //IES
    private String isExpandSubMarkets;
    //PMF
    private String priceModificationFactor;
    //IS_VIR_EX
    private String isVirtualExchange;
    // list under SUBM
    private List<SubMarketData> subMarkets;

    //meta data
    private String mainIndexSource;


    //Map to keep descriptions
    private Map<String, String> shortDescriptions;

    private Map<String, String> longDescriptions;

    private Integer listedStockCount = 0;

    private Integer sectorCount;

    private Integer isDefaultMkt;

    private String screenerCode;

    private String windowTypes;

    private Double marketCap;

    private Integer realTimeFeed;

    private Date marketOpen;

    private Date marketClose;

    private List<String> offDays;

    private String marketOpenInLocalTime;
    private String marketCloseInLocalTime;

    public List<String> getOffDays() {
        return offDays;
    }

    public void setOffDays(List<String> offDays) {
        this.offDays = offDays;
    }

    public Date getMarketClose() {
        return marketClose;
    }

    public void setMarketClose(Date marketClose) {
        this.marketClose = marketClose;
    }

    public Date getMarketOpen() {
        return marketOpen;
    }

    public void setMarketOpen(Date marketOpen) {
        this.marketOpen = marketOpen;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(String mainIndex) {
        this.mainIndex = mainIndex;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDecimalCorrectionFactor() {
        return decimalCorrectionFactor;
    }

    public void setDecimalCorrectionFactor(String decimalCorrectionFactor) {
        this.decimalCorrectionFactor = decimalCorrectionFactor;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(String decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getVersionID() {
        return versionID;
    }

    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    public String getDisplayExchange() {
        return displayExchange;
    }

    public void setDisplayExchange(String displayExchange) {
        this.displayExchange = displayExchange;
    }

    public String getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(String timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public String getExpandSubMarkets() {
        return isExpandSubMarkets;
    }

    public void setExpandSubMarkets(String expandSubMarkets) {
        isExpandSubMarkets = expandSubMarkets;
    }

    public String getPriceModificationFactor() {
        return priceModificationFactor;
    }

    public void setPriceModificationFactor(String priceModificationFactor) {
        this.priceModificationFactor = priceModificationFactor;
    }

    public String getIsVirtualExchange() {
        return isVirtualExchange;
    }

    public void setVirtualExchange(String virtualExchange) {
        isVirtualExchange = virtualExchange;
    }

    public List<SubMarketData> getSubMarkets() {
        return subMarkets;
    }

    public void setSubMarkets(List<SubMarketData> subMarkets) {
        this.subMarkets = subMarkets;
    }

    public String getMainIndexSource() {
        return mainIndexSource;
    }

    public void setMainIndexSource(String mainIndexSource) {
        this.mainIndexSource = mainIndexSource;
    }

    public Map<String, String> getShortDescriptions() {
        return shortDescriptions;
    }

    public void setShortDescriptions(Map<String, String> shortDescriptions) {
        this.shortDescriptions = shortDescriptions;
    }

    public Map<String, String> getLongDescriptions() {
        return longDescriptions;
    }

    public void setLongDescriptions(Map<String, String> longDescriptions) {
        this.longDescriptions = longDescriptions;
    }

    public Integer getListedStockCount() {
        return listedStockCount;
    }

    public void setListedStockCount(Integer listedStockCount) {
        this.listedStockCount = listedStockCount;
    }

    public Integer getSectorCount() {
        return sectorCount;
    }

    public void setSectorCount(Integer sectorCount) {
        this.sectorCount = sectorCount;
    }

    public Integer getIsDefaultMkt() {
        return isDefaultMkt;
    }

    public void setIsDefaultMkt(Integer isDefaultMkt) {
        this.isDefaultMkt = isDefaultMkt;
    }

    public String getScreenerCode() {
        return screenerCode;
    }

    public void setScreenerCode(String screenerCode) {
        this.screenerCode = screenerCode;
    }

    public String getWindowTypes() {
        return windowTypes;
    }

    public void setWindowTypes(String windowTypes) {
        this.windowTypes = windowTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketDTO marketDTO = (MarketDTO) o;

        if (exchangeCode != null ? !exchangeCode.equals(marketDTO.exchangeCode) : marketDTO.exchangeCode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return exchangeCode != null ? exchangeCode.hashCode() : 0;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Integer getRealTimeFeed() {
        return realTimeFeed;
    }

    public void setRealTimeFeed(Integer realTimeFeed) {
        this.realTimeFeed = realTimeFeed;
    }

    public String getMarketOpenInLocalTime() {
        return marketOpenInLocalTime;
    }

    public void setMarketOpenInLocalTime(String marketOpenInLocalTime) {
        this.marketOpenInLocalTime = marketOpenInLocalTime;
    }

    public String getMarketCloseInLocalTime() {
        return marketCloseInLocalTime;
    }

    public void setMarketCloseInLocalTime(String marketCloseInLocalTime) {
        this.marketCloseInLocalTime = marketCloseInLocalTime;
    }

}
