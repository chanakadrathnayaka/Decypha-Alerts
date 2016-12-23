package com.dfn.alerts.beans;

import com.dfn.alerts.utils.CommonUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 5/27/13
 * Time: 6:51 PM
 */
public class CountryTickerDTO {

    private Long tickerSerial;
    private String  tickerId;
    private String  countryCode;
    private String  currencyId;
    private Integer decimalPlaces;
    private String  displayTicker;
    private Date lastUpdatedTime;
    private String  sourceId;
    private String  tickerShortDesLn;
    private String  tickerLongDesLn;
    private String  unit;
    private Integer instrumentTypeId;
    private String  status;
    private Map<String , String> tickerShortDes;
    private Map<String , String>  tickerLongDes;

    public Long getTickerSerial() {
        return tickerSerial;
    }

    public void setTickerSerial(Long tickerSerial) {
        this.tickerSerial = tickerSerial;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDisplayTicker() {
        return displayTicker;
    }

    public void setDisplayTicker(String displayTicker) {
        this.displayTicker = displayTicker;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTickerShortDesLn() {
        return tickerShortDesLn;
    }

    public void setTickerShortDesLn(String tickerShortDesLn) {
        this.tickerShortDes = CommonUtils.getLanguageDescriptionMap(tickerShortDesLn);
        this.tickerShortDesLn = tickerShortDesLn;
    }

    public String getTickerLongDesLn() {
        return tickerLongDesLn;
    }

    public void setTickerLongDesLn(String tickerLongDesLn) {
        this.tickerLongDes = CommonUtils.getLanguageDescriptionMap(tickerLongDesLn);
        this.tickerLongDesLn = tickerLongDesLn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(Integer instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getTickerShortDes() {
        return tickerShortDes;
    }

    public void setTickerShortDes(Map<String, String> tickerShortDes) {
        this.tickerShortDes = tickerShortDes;
    }

    public Map<String, String> getTickerLongDes() {
        return tickerLongDes;
    }

    public void setTickerLongDes(Map<String, String> tickerLongDes) {
        this.tickerLongDes = tickerLongDes;
    }
}
