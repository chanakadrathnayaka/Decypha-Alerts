package com.dfn.alerts.beans.tickers;

import com.dfn.alerts.beans.CompanyDTO;
import com.dfn.alerts.utils.CommonUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: duminda
 * Date: 4/9/13
 * Time: 7:44 PM
 */
public class TickerDTO extends CompanyDTO {

    private Long tickerSerial;
    private String  tickerId;
    private String  displayTicker;
    private String  tickerShortDes;
    private String  tickerLongDesLn;
    private String  sourceId;
    private Integer instrumentTypeId;
    private String  currencyId;
    private Integer decimalPlaces;
    private String  parentSourceId;
    private Date lastUpdatedOn;
    private Integer    status;
    private Timestamp lastUpdatedTime = null;
    private Timestamp lastSyncTime = null;
    private String tickerSource;
    private Double per = null;
    private Integer cityId;
    private String countryDesLn;
    private Integer eligibility ;

    private Integer tickerClassLevel1;
    private Integer tickerClassLevel2;
    private Integer tickerClassLevel3;

    //Ticker country code    "TICKER_COUNTRY_DESC_EN" "TICKER_COUNTRY_DESC_AR" etc
    private String tickerCountryCode;
    //language country description map based on tickerCountryCode
    private Map<String,TickerLangDTO> tickerLangDTOMap;

    private Map<String,String> tickerShortDescriptions = null;
    private Map<String,String> tickerLongDescriptions  = null;

    //ticker country "COUNTRY_DESC" column- this is company country des
    // based on CompanyDTO.countryCode
    private Map<String,String> countryDescriptions  = null;

    public TickerDTO(){
        setTickerLangDTOMap(new HashMap<String, TickerLangDTO>());
    }

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

    public String getDisplayTicker() {
        return displayTicker;
    }

    public void setDisplayTicker(String displayTicker) {
        this.displayTicker = displayTicker;
    }

    public String getTickerShortDes() {
        return tickerShortDes;
    }

    public void setTickerShortDes(String tickerShortDes) {
        this.tickerShortDescriptions = CommonUtils.getLanguageDescriptionMap(tickerShortDes);
        //TODO need to change this code
        if(this.tickerShortDescriptions == null){
            this.tickerShortDescriptions = new HashMap<String, String>();
        }

        if(this.tickerShortDescriptions.get("EN") == null){
            this.tickerShortDescriptions.put("EN", this.getTickerId());
        }

        this.tickerShortDes = tickerShortDes;
    }

    public String getTickerLongDesLn() {
        return tickerLongDesLn;
    }

    public void setTickerLongDesLn(String tickerLongDesLn) {
        this.tickerLongDescriptions = CommonUtils.getLanguageDescriptionMap(tickerLongDesLn);
        //TODO need to change this code
        if(this.tickerLongDescriptions == null){
            this.tickerLongDescriptions = new HashMap<String, String>();
        }

        if(this.tickerLongDescriptions.get("EN") == null){
            this.tickerLongDescriptions.put("EN", this.tickerId);
        }

        this.tickerLongDesLn = tickerLongDesLn;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Deprecated
    public Integer getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(Integer instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
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


    public String getParentSourceId() {
        return parentSourceId;
    }

    public void setParentSourceId(String parentSourceId) {
        this.parentSourceId = parentSourceId;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Map<String, String> getTickerShortDescriptions() {
        return tickerShortDescriptions;
    }

    public Map<String, String> getTickerLongDescriptions() {
        return tickerLongDescriptions;
    }

    public String getTickerSource() {
        return tickerSource;
    }

    public void setTickerSource(String tickerSource) {
        this.tickerSource = tickerSource;
    }

    public Double getPer() {
        return per;
    }

    public void setPer(Double per) {
        this.per = per;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCountryDesLn() {
        return countryDesLn;
    }

    public void setCountryDesLn(String countryDesLn) {
        this.countryDescriptions = CommonUtils.getLanguageDescriptionMap(countryDesLn);
        //TODO need to change this code
        if(this.countryDescriptions == null){
            this.countryDescriptions = new HashMap<String, String>();
        }

        if(this.countryDescriptions.get("EN") == null){
            this.countryDescriptions.put("EN", this.getCountryCode());
        }

        this.countryDesLn = countryDesLn;
    }

    public Map<String, String> getCountryDescriptions() {
        return countryDescriptions;
    }

    public Map<String, TickerLangDTO> getTickerLangDTOMap() {
        return tickerLangDTOMap;
    }

    public void setTickerLangDTOMap(Map<String, TickerLangDTO> tickerLangDTOMap) {
        this.tickerLangDTOMap = tickerLangDTOMap;
    }

    public String getTickerCountryCode() {
        return tickerCountryCode;
    }

    public void setTickerCountryCode(String tickerCountryCode) {
        this.tickerCountryCode = tickerCountryCode;
    }

    public Integer getEligibility() {
        return eligibility;
    }

    public void setEligibility(Integer eligibility) {
        this.eligibility = eligibility;
    }

    public Integer getTickerClassLevel1() {
        return tickerClassLevel1;
    }

    public void setTickerClassLevel1(Integer tickerClassLevel1) {
        this.tickerClassLevel1 = tickerClassLevel1;
    }

    public Integer getTickerClassLevel2() {
        return tickerClassLevel2;
    }

    public void setTickerClassLevel2(Integer tickerClassLevel2) {
        this.tickerClassLevel2 = tickerClassLevel2;
    }

    public Integer getTickerClassLevel3() {
        return tickerClassLevel3;
    }

    public void setTickerClassLevel3(Integer tickerClassLevel3) {
        this.tickerClassLevel3 = tickerClassLevel3;
    }

}
