package com.dfn.alerts.dataaccess.orm.impl;


import com.dfn.alerts.dataaccess.utils.ColumnLengths;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 5/31/13
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USR_PREFERENCES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID"})})
public class UserPreference implements Serializable {

    private Long id;
    private Long userID;

    //Default settings
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
    private Boolean saveNewsStreamingSettings;


    //File settings
    private String preferredFilesLanguage;

    // Top Main Panel Settings
    private String marketsInTimeLine;
    private String marketsInActivePanel;
    private String commodities;
    private String currencies;

    //page settings
    private String screenerSettings;
    private String watchListSettings;
    private String newsStreamSettings;

    /* Release note show to user. This is a general setting user can change latest release note status using showLatestReleaseNote*/
    private Integer showReleaseNotes;
    private Integer emailReleaseNotes;
    private Integer showLatestReleaseNote;
    private Integer isEmailSent;

    /**
     * financial settings
     */
    private String periodBasis;
    private String defaultFinancialTab;
    private String defaultFinancialView;
    private Integer breakdown;
    private Integer futureEstimates;

    /**
     * stock chart settings
     */
    private String stockChartType;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "USER_ID", nullable = false)
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "DEFAULT_TIME_ZONE", length = ColumnLengths.L32, nullable = false)
    public String getDefaultTimeZone() {
        return defaultTimeZone;
    }

    public void setDefaultTimeZone(String defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    @Column(name = "DEFAULT_CURRENCY", length = ColumnLengths.L32, nullable = false)
    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Column(name = "DEFAULT_MARKET", length = ColumnLengths.L32, nullable = false)
    public String getDefaultMarket() {
        return defaultMarket;
    }

    public void setDefaultMarket(String defaultMarket) {
        this.defaultMarket = defaultMarket;
    }

    @Column(name = "DEFAULT_COMPANY", length = ColumnLengths.L128, nullable = false)
    public String getDefaultCompany() {
        return defaultCompany;
    }

    public void setDefaultCompany(String defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    @Column(name = "DEFAULT_COUNTRY", length = ColumnLengths.L32, nullable = false)
    public String getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    @Column(name = "PREFERRED_NEWS_READING_LAN", length = ColumnLengths.L32, nullable = false)
    public String getPreferredNewsReadingLanguages() {
        return preferredNewsReadingLanguages;
    }

    public void setPreferredNewsReadingLanguages(String preferredNewsReadingLanguages) {
        this.preferredNewsReadingLanguages = preferredNewsReadingLanguages;
    }

    @Column(name = "NEWS_READING_LAN_PREFERENCE", length = ColumnLengths.L32, nullable = false)
    public String getNewsReadingLanguagePreference() {
        return newsReadingLanguagePreference;
    }

    public void setNewsReadingLanguagePreference(String newsReadingLanguagePreference) {
        this.newsReadingLanguagePreference = newsReadingLanguagePreference;
    }

    @Column(name = "PREFERRED_NEWS_HEADINGS_LAN", length = ColumnLengths.L32, nullable = false)
    public String getPreferredNewsHeadLanguages() {
        return preferredNewsHeadLanguages;
    }

    public void setPreferredNewsHeadLanguages(String preferredNewsHeadLanguages) {
        this.preferredNewsHeadLanguages = preferredNewsHeadLanguages;
    }

    @Column(name = "SAVE_NEWS_STREAMING_SETTINGS", nullable = false)
    public Boolean isSaveNewsStreamingSettings() {
        return saveNewsStreamingSettings;
    }

    public void setSaveNewsStreamingSettings(Boolean saveNewsStreamingSettings) {
        this.saveNewsStreamingSettings = saveNewsStreamingSettings;
    }

    @Column(name = "PREFERRED_FILES_LANGUAGE", length = ColumnLengths.L32, nullable = false)
    public String getPreferredFilesLanguage() {
        return preferredFilesLanguage;
    }

    public void setPreferredFilesLanguage(String preferredFilesLanguage) {
        this.preferredFilesLanguage = preferredFilesLanguage;
    }

    @Column(name = "MARKETS_IN_TIME_LINE", length = ColumnLengths.L256, nullable = false)
    public String getMarketsInTimeLine() {
        return marketsInTimeLine;
    }


    public void setMarketsInTimeLine(String marketsInTimeLine) {
        this.marketsInTimeLine = marketsInTimeLine;
    }

    @Column(name = "MARKETS_IN_ACTIVE_PANEL", length = ColumnLengths.L256, nullable = false)
    public String getMarketsInActivePanel() {
        return marketsInActivePanel;
    }

    public void setMarketsInActivePanel(String marketsInActivePanel) {
        this.marketsInActivePanel = marketsInActivePanel;
    }

    @Column(name = "COMMODITIES", length = ColumnLengths.L256, nullable = false)
    public String getCommodities() {
        return commodities;
    }

    public void setCommodities(String commodities) {
        this.commodities = commodities;
    }

    @Column(name = "CURRENCIES", length = ColumnLengths.L256, nullable = false)
    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    @Column(name = "SCREENER_SETTINGS", length = ColumnLengths.L1000, nullable = false)
    public String getScreenerSettings() {
        return screenerSettings;
    }

    public void setScreenerSettings(String screenerSettings) {
        this.screenerSettings = screenerSettings;
    }

    @Column(name = "DEFAULT_REGION", length = ColumnLengths.L32, nullable = false)
    public String getDefaultRegion() {
        return defaultRegion;
    }

    public void setDefaultRegion(String defaultRegion) {
        this.defaultRegion = defaultRegion;
    }

    @Column(name = "EDITION_CONTROL_TYPE", length = ColumnLengths.L5, nullable = false)
    public String getEditionControlType() {
        return editionControlType;
    }

    public void setEditionControlType(String editionControlType) {
        this.editionControlType = editionControlType;
    }

    @Column(name = "WATCH_LIST_SETTINGS", length = ColumnLengths.L2000, nullable = false)
    public String getWatchListSettings() {
        return watchListSettings;
    }

    public void setWatchListSettings(String watchListSettings) {
        this.watchListSettings = watchListSettings;
    }

    @Column(name = "NEWS_STREAM_SETTINGS", length = ColumnLengths.L2000, nullable = false)
    public String getNewsStreamSettings() {
        return newsStreamSettings;
    }

    public void setNewsStreamSettings(String newsStreamSettings) {
        this.newsStreamSettings = newsStreamSettings;
    }

    @Column(name = "SHOW_RELEASE_NOTES")
    public Integer getShowReleaseNotes() {
        return showReleaseNotes;
    }

    public void setShowReleaseNotes(Integer showReleaseNotes) {
        this.showReleaseNotes = showReleaseNotes;
    }

    @Column(name = "EMAIL_RELEASE_NOTES")
    public Integer getEmailReleaseNotes() {
        return emailReleaseNotes;
    }

    public void setEmailReleaseNotes(Integer emailReleaseNotes) {
        this.emailReleaseNotes = emailReleaseNotes;
    }

    @Column(name = "SHOW_LATEST_REL_NOTE")
    public Integer getShowLatestReleaseNote() {
        return showLatestReleaseNote;
    }

    public void setShowLatestReleaseNote(Integer showLatestReleaseNote) {
        this.showLatestReleaseNote = showLatestReleaseNote;
    }

    @Column(name = "IS_EMAIL_SENT")
    public Integer getIsEmailSent() {
        return isEmailSent;
    }

    public void setIsEmailSent(Integer isEmailSent) {
        this.isEmailSent = isEmailSent;
    }

    @Column(name = "BASIC_PERIOD")
    public String getPeriodBasis() {
        return periodBasis;
    }

    public void setPeriodBasis(String periodBasis) {
        this.periodBasis = periodBasis;
    }

    @Column(name = "DEFAULT_FINANCIAL_TAB")
    public String getDefaultFinancialTab() {
        return defaultFinancialTab;
    }

    public void setDefaultFinancialTab(String defaultFinancialTab) {
        this.defaultFinancialTab = defaultFinancialTab;
    }

    @Column(name = "DEFAULT_FINANCIAL_VIEW")
    public String getDefaultFinancialView() {
        return defaultFinancialView;
    }

    public void setDefaultFinancialView(String defaultFinancialView) {
        this.defaultFinancialView = defaultFinancialView;
    }

    @Column(name = "IS_COLLAPSE")
    public Integer getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Integer breakdown) {
        this.breakdown = breakdown;
    }

    @Column(name = "SHOW_FUTURE_ESTIMATES")
    public Integer getFutureEstimates() {
        return futureEstimates;
    }

    public void setFutureEstimates(Integer futureEstimates) {
        this.futureEstimates = futureEstimates;
    }

    @Column(name = "STOCK_CHART_TYPE")
    public String getStockChartType() {
        return stockChartType;
    }

    public void setStockChartType(String stockChartType) {
        this.stockChartType = stockChartType;
    }

}
