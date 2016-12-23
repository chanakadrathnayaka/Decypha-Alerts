package com.dfn.alerts.beans;


import com.dfn.alerts.constants.IConstants;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Business object for exchange data
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/29/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */

public class SourceDTO implements Serializable {

    private static final String DES_DELIMITER = "|";

    private String sourceId;
    private Integer status;
    private Integer sourceSerial;
    private Timestamp openTime;
    private Timestamp closeTime;
    private String shortDescriptionLan;
    private String longDescriptionLan;
    private Date lastUpdatedTime;
    private String defaultCurrency;
    private Integer timezoneId;
    private String displayCode;
    private Integer defaultDecimalPlaces;
    private Integer isVirtualExchange;
    private Integer isExpandSubmkts;
    private Integer weekStart;
    private Integer listedStocks;
    private Integer sectorCount;
    private String mainIndexSource;
    private String mainIndexTicker;
    private Long mainIndexTickerSerial;
    private String countryCode;

    private Map<String,String> shortDescriptions = null ;
    private Map<String,String> longDescriptions  = null;

    private Integer isDefaultMkt;
    private String screenerCode;
    private String windowTypes;
    private Timestamp lastSyncTime = null;

    private Double marketCap;

    private Integer realTimeFeed;

    private String offDays;

    private String marketOpenInLocalTime;
    private String marketCloseInLocalTime;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSourceSerial() {
        return sourceSerial;
    }

    public void setSourceSerial(Integer sourceSerial) {
        this.sourceSerial = sourceSerial;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
        setMarketOpenInLocalTime();
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
        setMarketCloseInLocalTime();
    }

    public String getShortDescriptionLan() {
        return shortDescriptionLan;
    }

    public void setShortDescriptionLan(String shortDescriptionLan) {
        this.shortDescriptionLan = shortDescriptionLan;
        if(shortDescriptionLan != null && shortDescriptionLan.contains(DES_DELIMITER)){
            this.shortDescriptions = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(shortDescriptionLan, DES_DELIMITER);

            while (strTkn.hasMoreTokens()){

                String [] keyVal = strTkn.nextToken().split(":");

                if(keyVal.length == 2)
                {
                    this.shortDescriptions.put(keyVal[0],keyVal[1]);
                }

            }
        }

    }

    public String getLongDescriptionLan() {
        return longDescriptionLan;
    }

    public void setLongDescriptionLan(String longDescriptionLan) {
        this.longDescriptionLan = longDescriptionLan;
        if(longDescriptionLan != null && longDescriptionLan.contains(DES_DELIMITER)){
            this.longDescriptions = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(longDescriptionLan, DES_DELIMITER);

            while (strTkn.hasMoreTokens()){

                String [] keyVal = strTkn.nextToken().split(":");

                if(keyVal.length == 2)
                {
                    this.longDescriptions.put(keyVal[0],keyVal[1]);
                }

            }
        }
    }


    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Integer getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(Integer timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getDisplayCode() {
        return displayCode;
    }

    public void setDisplayCode(String displayCode) {
        this.displayCode = displayCode;
    }

    public Integer getDefaultDecimalPlaces() {
        return defaultDecimalPlaces;
    }

    public void setDefaultDecimalPlaces(Integer defaultDecimalPlaces) {
        this.defaultDecimalPlaces = defaultDecimalPlaces;
    }

    public Integer getVirtualExchange() {
        return isVirtualExchange;
    }

    public void setVirtualExchange(Integer virtualExchange) {
        isVirtualExchange = virtualExchange;
    }

    public Integer getExpandSubmkts() {
        return isExpandSubmkts;
    }

    public void setExpandSubmkts(Integer expandSubmkts) {
        isExpandSubmkts = expandSubmkts;
    }

    public Integer getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public String getMainIndexSource() {
        return mainIndexSource;
    }

    public void setMainIndexSource(String mainIndexSource) {
        this.mainIndexSource = mainIndexSource;
    }

    public String getMainIndexTicker() {
        return mainIndexTicker;
    }

    public void setMainIndexTicker(String mainIndexTicker) {
        this.mainIndexTicker = mainIndexTicker;
    }

    public Long getMainIndexTickerSerial() {
        return mainIndexTickerSerial;
    }

    public void setMainIndexTickerSerial(Long mainIndexTickerSerial) {
        this.mainIndexTickerSerial = mainIndexTickerSerial;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Map<String, String> getShortDescriptions() {
        return shortDescriptions;
    }

    public Map<String, String> getLongDescriptions() {
        return longDescriptions;
    }

    public Integer getListedStocks() {
        return listedStocks;
    }

    public void setListedStocks(Integer listedStocks) {
        this.listedStocks = listedStocks;
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


    public String getWindowTypes() {
        return windowTypes;
    }

    public void setWindowTypes(String windowTypes) {
        this.windowTypes = windowTypes;
    }

    public String getScreenerCode() {
        return screenerCode;
    }

    public void setScreenerCode(String screenerCode) {
        this.screenerCode = screenerCode;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
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

    public String getOffDays() {
        return offDays;
    }

    public void setOffDays(String offDays) {
        this.offDays = offDays;
    }

    public String getMarketOpenInLocalTime() {
        return marketOpenInLocalTime;
    }

    public String getMarketCloseInLocalTime() {
        return marketCloseInLocalTime;
    }

    public void setMarketOpenInLocalTime() {
        if(getOpenTime() != null) {
            DateTime oTime = new DateTime(getOpenTime().getTime());
            marketOpenInLocalTime = (oTime.getHourOfDay() < 10 ? "0" : "") + oTime.getHourOfDay() +
                    IConstants.Delimiter.COLON + (oTime.getMinuteOfHour() < 10 ? "0" : "") + oTime.getMinuteOfHour();
        }
    }

    public void setMarketCloseInLocalTime() {
        if(getCloseTime() != null) {
            DateTime cTime = new DateTime(getCloseTime().getTime());
            marketCloseInLocalTime = (cTime.getHourOfDay() < 10 ? "0" : "") + cTime.getHourOfDay() +
                    IConstants.Delimiter.COLON + (cTime.getMinuteOfHour() < 10 ? "0" : "") + cTime.getMinuteOfHour();
        }
    }

}
