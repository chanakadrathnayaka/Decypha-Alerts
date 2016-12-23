package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 11/15/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "EXCHANGE_METADATA")
public class ExchangeMetaData implements Serializable {

    private String sourceId;
    private boolean overview;
    private boolean watchList;
    private boolean indices;
    private boolean announcements;
    private boolean news;
    private boolean sectors;
    private boolean marketEvents;
    private boolean corporateActions;
    private boolean disclosures;
    private boolean reports;
    private boolean investorTypes;

    @Column(name = "SOURCE_ID")
    @Id
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "OVERVIEW")
    public boolean isOverview() {
        return overview;
    }

    public void setOverview(boolean overview) {
        this.overview = overview;
    }

    @Column(name = "WATCHLIST")
    public boolean isWatchList() {
        return watchList;
    }

    public void setWatchList(boolean watchList) {
        this.watchList = watchList;
    }

    @Column(name = "INDICES")
    public boolean isIndices() {
        return indices;
    }

    public void setIndices(boolean indices) {
        this.indices = indices;
    }

    @Column(name = "ANNOUNCEMENTS")
    public boolean isAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(boolean announcements) {
        this.announcements = announcements;
    }

    @Column(name = "NEWS")
    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    @Column(name = "SECTORS")
    public boolean isSectors() {
        return sectors;
    }

    public void setSectors(boolean sectors) {
        this.sectors = sectors;
    }

    @Column(name = "MARKET_EVENTS")
    public boolean isMarketEvents() {
        return marketEvents;
    }

    public void setMarketEvents(boolean marketEvents) {
        this.marketEvents = marketEvents;
    }

    @Column(name = "CORPORATE_ACTIONS")
    public boolean isCorporateActions() {
        return corporateActions;
    }

    public void setCorporateActions(boolean corporateActions) {
        this.corporateActions = corporateActions;
    }

    @Column(name = "DISCLOSURES")
    public boolean isDisclosures() {
        return disclosures;
    }

    public void setDisclosures(boolean disclosures) {
        this.disclosures = disclosures;
    }

    @Column(name = "REPORTS")
    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }

    @Column(name = "INVESTOR_TYPES")
    public boolean isInvestorTypes() {
        return investorTypes;
    }

    public void setInvestorTypes(boolean investorTypes) {
        this.investorTypes = investorTypes;
    }

}
