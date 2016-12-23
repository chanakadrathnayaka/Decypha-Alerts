package com.dfn.alerts.beans.user;


import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/18/13
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserPreferenceDetails implements Serializable{

    private long id;
    private final long userID;

    private String defaultTimeZone;
    private String defaultCurrency;
    private String defaultMarket;
    private String defaultCompany;
    private String defaultCountry;
    private String defaultRegion;
    private String editionControlType;

    //News settings
    private String preferredNewsReadingLanguages;
    private String newsReadingLanguagePreference;
    private String preferredNewsHeadLanguages;
    private boolean saveNewsStreamingSettings;

    //File settings
    private String preferredFilesLanguage;

   // Top Main Panel Settings
    private String marketsInTimeLine;
    private String marketsInActivePanel;
    private String commodities;
    private String currencies;

    private final boolean dummy;

    //page settings
    private String screenerSettings;
    private String watchListSettings;
    private String newsStreamSettings;


    //
    private int showReleaseNotes;
    private int emailReleaseNotes;
    private int showLatestReleaseNote;

    //user financial preferences
    private String periodBasis;
    private String defaultFinancialTab;
    private String defaultFinancialView;
    private Integer breakdown;
    private Integer futureEstimates;

    /**
     * stock chart settings
     */
    private String stockChartType;



    public UserPreferenceDetails(long userID, boolean isDummy){
        this.userID  = userID;
        this.dummy   = isDummy;
    }

    public boolean isDummy() {
        return dummy;
    }

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public String getDefaultTimeZone() {
        return defaultTimeZone;
    }

    public void setDefaultTimeZone(String defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getDefaultMarket() {
        return defaultMarket;
    }

    public void setDefaultMarket(String defaultMarket) {
        this.defaultMarket = defaultMarket;
    }

    public String getDefaultCompany() {
        return defaultCompany;
    }

    public void setDefaultCompany(String defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    public String getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public String getDefaultRegion() {
        return defaultRegion;
    }

    public void setDefaultRegion(String defaultRegion) {
        this.defaultRegion = defaultRegion;
    }

    public String getEditionControlType() {
        return editionControlType;
    }

    public void setEditionControlType(String editionControlType) {
        this.editionControlType = editionControlType;
    }

    public String getPreferredNewsReadingLanguages() {
        return preferredNewsReadingLanguages;
    }

    public void setPreferredNewsReadingLanguages(String preferredNewsReadingLanguages) {
        this.preferredNewsReadingLanguages = preferredNewsReadingLanguages;
    }

    public String getNewsReadingLanguagePreference() {
        return newsReadingLanguagePreference;
    }

    public void setNewsReadingLanguagePreference(String newsReadingLanguagePreference) {
        this.newsReadingLanguagePreference = newsReadingLanguagePreference;
    }

    public String getPreferredNewsHeadLanguages() {
        return preferredNewsHeadLanguages;
    }

    public void setPreferredNewsHeadLanguages(String preferredNewsHeadLanguages) {
        this.preferredNewsHeadLanguages = preferredNewsHeadLanguages;
    }

    public boolean isSaveNewsStreamingSettings() {
        return saveNewsStreamingSettings;
    }

    public void setSaveNewsStreamingSettings(boolean saveNewsStreamingSettings) {
        this.saveNewsStreamingSettings = saveNewsStreamingSettings;
    }

    public String getPreferredFilesLanguage() {
        return preferredFilesLanguage;
    }

    public void setPreferredFilesLanguage(String preferredFilesLanguage) {
        this.preferredFilesLanguage = preferredFilesLanguage;
    }

    public String getMarketsInTimeLine() {
        return marketsInTimeLine;
    }

    public void setMarketsInTimeLine(String marketsInTimeLine) {
        this.marketsInTimeLine = marketsInTimeLine;
    }

    public String getMarketsInActivePanel() {
        return marketsInActivePanel;
    }

    public void setMarketsInActivePanel(String marketsInActivePanel) {
        this.marketsInActivePanel = marketsInActivePanel;
    }

    public String getCommodities() {
        return commodities;
    }

    public void setCommodities(String commodities) {
        this.commodities = commodities;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public String getScreenerSettings() {
        return screenerSettings;
    }

    public void setScreenerSettings(String screenerSettings) {
        this.screenerSettings = screenerSettings;
    }

    public String getWatchListSettings() {
        return watchListSettings;
    }

    public void setWatchListSettings(String watchListSettings) {
        this.watchListSettings = watchListSettings;
    }

    public String getNewsStreamSettings() {
        return newsStreamSettings;
    }

    public void setNewsStreamSettings(String newsStreamSettings) {
        this.newsStreamSettings = newsStreamSettings;
    }

    public int getShowReleaseNotes() {
        return showReleaseNotes;
    }

    public void setShowReleaseNotes(int showReleaseNotes) {
        this.showReleaseNotes = showReleaseNotes;
    }

    public int getEmailReleaseNotes() {
        return emailReleaseNotes;
    }

    public void setEmailReleaseNotes(int emailReleaseNotes) {
        this.emailReleaseNotes = emailReleaseNotes;
    }

    public int getShowLatestReleaseNote() {
        return showLatestReleaseNote;
    }

    public void setShowLatestReleaseNote(int showLatestReleaseNote) {
        this.showLatestReleaseNote = showLatestReleaseNote;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPeriodBasis() {
        return periodBasis;
    }

    public void setPeriodBasis(String periodBasis) {
        this.periodBasis = periodBasis;
    }

    public String getDefaultFinancialTab() {
        return defaultFinancialTab;
    }

    public void setDefaultFinancialTab(String defaultFinancialTab) {
        this.defaultFinancialTab = defaultFinancialTab;
    }

    public String getDefaultFinancialView() {
        return defaultFinancialView;
    }

    public void setDefaultFinancialView(String defaultFinancialView) {
        this.defaultFinancialView = defaultFinancialView;
    }

    public Integer getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Integer breakdown) {
        this.breakdown = breakdown;
    }

    public Integer getFutureEstimates() {
        return futureEstimates;
    }

    public void setFutureEstimates(Integer futureEstimates) {
        this.futureEstimates = futureEstimates;
    }

    public String getStockChartType() {
        return stockChartType;
    }

    public void setStockChartType(String stockChartType) {
        this.stockChartType = stockChartType;
    }
}