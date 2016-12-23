package com.dfn.alerts.beans.news;

import com.dfn.alerts.constants.IConstants;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasarindat on 7/3/2015.
 */
public class NewsDTO {

    private long newsId;
    private long seqId;
    private Timestamp newsDate;
    private IConstants.DCPStatus status;
    private String provider;
    private int newsSource;
    private int hotNewsIndicator;
    private String assetClasses;
    private String companies;
    private String symbolTickerSerials;
    private String symbols;
    private String marketSectorCodes;
    private String gicsL3Codes;
    private String exchanges;
    private String countries;
    private String individuals;
    private String editorialCodes;
    private int isTopStory;

    private Timestamp lastUpdatedTime;
    private Timestamp lastSyncTime;

    private Map<String, NewsLangDTO> langDTOMap;
    private String availableLanguages;

    private NewsDTO() {
    }

    public NewsDTO(long newsId) {
        this.newsId = newsId;
        langDTOMap = new HashMap<String, NewsLangDTO>(2);
    }

    public long getNewsId() {
        return newsId;
    }

    public long getSeqId() {
        return seqId;
    }

    public void setSeqId(long seqId) {
        this.seqId = seqId;
    }

    public Timestamp getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Timestamp newsDate) {
        this.newsDate = newsDate;
    }

    public IConstants.DCPStatus getStatus() {
        return status;
    }

    public void setStatus(IConstants.DCPStatus status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(int newsSource) {
        this.newsSource = newsSource;
    }

    public int getHotNewsIndicator() {
        return hotNewsIndicator;
    }

    public void setHotNewsIndicator(int hotNewsIndicator) {
        this.hotNewsIndicator = hotNewsIndicator;
    }

    public String getAssetClasses() {
        return assetClasses;
    }

    public void setAssetClasses(String assetClasses) {
        this.assetClasses = assetClasses;
    }

    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public String getSymbolTickerSerials() {
        return symbolTickerSerials;
    }

    public void setSymbolTickerSerials(String symbolTickerSerials) {
        this.symbolTickerSerials = symbolTickerSerials;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public String getMarketSectorCodes() {
        return marketSectorCodes;
    }

    public void setMarketSectorCodes(String marketSectorCodes) {
        this.marketSectorCodes = marketSectorCodes;
    }

    public String getGicsL3Codes() {
        return gicsL3Codes;
    }

    public void setGicsL3Codes(String gicsL3Codes) {
        this.gicsL3Codes = gicsL3Codes;
    }

    public String getExchanges() {
        return exchanges;
    }

    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getIndividuals() {
        return individuals;
    }

    public void setIndividuals(String individuals) {
        this.individuals = individuals;
    }

    public String getEditorialCodes() {
        return editorialCodes;
    }

    public void setEditorialCodes(String editorialCodes) {
        this.editorialCodes = editorialCodes;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Map<String, NewsLangDTO> getLangDTOMap() {
        return langDTOMap;
    }

    public void addLangDTO(String language, NewsLangDTO langDTO) {
        langDTOMap.put(language.toUpperCase(), langDTO);
    }

    public boolean isNew(){
        return status == IConstants.DCPStatus.NEW;
    }

    public boolean isModification(){
        return status == IConstants.DCPStatus.MODIFIED;
    }

    public boolean isDelete(){
        return status == IConstants.DCPStatus.DELETED;
    }

    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public int getIsTopStory() {
        return isTopStory;
    }

    public void setIsTopStory(int isTopStory) {
        this.isTopStory = isTopStory;
    }
}
