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
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "STOCK_METADATA")
public class StockMetaData implements Serializable{

    private String sourceId;
    private boolean overview;
    private boolean realTimePrices;
    private boolean chart;
    private boolean announcements;
    private boolean news;
    private boolean financials;
    private boolean estimates;
    private boolean targetPrice;
    private boolean reports;
    private boolean disclosures;
    private boolean events;
    private boolean corporateActions;
    private boolean companyProfile;
    private boolean owners;
    private boolean ownersAndSubsidiaries;
    private boolean fundPositions;
    private boolean investments;
    private boolean relatedInstruments;
    private boolean management;
    private boolean insiderTransactions;
    private boolean peerCompanies;
    private boolean operationalKPIs;

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

    @Column(name = "REAL_TIME_PRICES")
    public boolean isRealTimePrices() {
        return realTimePrices;
    }

    public void setRealTimePrices(boolean realTimePrices) {
        this.realTimePrices = realTimePrices;
    }

    @Column(name = "CHART")
    public boolean isChart() {
        return chart;
    }

    public void setChart(boolean chart) {
        this.chart = chart;
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

    @Column(name = "FINANCIALS")
    public boolean isFinancials() {
        return financials;
    }

    public void setFinancials(boolean financials) {
        this.financials = financials;
    }

    @Column(name = "ESTIMATES")
    public boolean isEstimates() {
        return estimates;
    }

    public void setEstimates(boolean estimates) {
        this.estimates = estimates;
    }

    @Column(name = "TARGET_PRICE")
    public boolean isTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(boolean targetPrice) {
        this.targetPrice = targetPrice;
    }

    @Column(name = "REPORTS")
    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }

    @Column(name = "DISCLOSURES")
    public boolean isDisclosures() {
        return disclosures;
    }

    public void setDisclosures(boolean disclosures) {
        this.disclosures = disclosures;
    }

    @Column(name = "EVENTS")
    public boolean isEvents() {
        return events;
    }

    public void setEvents(boolean events) {
        this.events = events;
    }

    @Column(name = "CORPORATE_ACTIONS")
    public boolean isCorporateActions() {
        return corporateActions;
    }

    public void setCorporateActions(boolean corporateActions) {
        this.corporateActions = corporateActions;
    }

    @Column(name = "COMPANY_PROFILE")
    public boolean isCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(boolean companyProfile) {
        this.companyProfile = companyProfile;
    }

    @Column(name = "OWNERS")
    public boolean isOwners() {
        return owners;
    }

    public void setOwners(boolean owners) {
        this.owners = owners;
    }

    @Column(name = "OWNERS_AND_SUBSIDIARIES")
    public boolean isOwnersAndSubsidiaries() {
        return ownersAndSubsidiaries;
    }

    public void setOwnersAndSubsidiaries(boolean ownersAndSubsidiaries) {
        this.ownersAndSubsidiaries = ownersAndSubsidiaries;
    }

    @Column(name = "FUND_POSITIONS")
    public boolean isFundPositions() {
        return fundPositions;
    }

    public void setFundPositions(boolean fundPositions) {
        this.fundPositions = fundPositions;
    }

    @Column(name = "INVESTMENTS")
    public boolean isInvestments() {
        return investments;
    }

    public void setInvestments(boolean investments) {
        this.investments = investments;
    }

    @Column(name = "RELATED_INSTRUMENTS")
    public boolean isRelatedInstruments() {
        return relatedInstruments;
    }

    public void setRelatedInstruments(boolean relatedInstruments) {
        this.relatedInstruments = relatedInstruments;
    }

    @Column(name = "MANAGEMENT")
    public boolean isManagement() {
        return management;
    }

    public void setManagement(boolean management) {
        this.management = management;
    }

    @Column(name = "INSIDER_TRANSACTIONS")
    public boolean isInsiderTransactions() {
        return insiderTransactions;
    }

    public void setInsiderTransactions(boolean insiderTransactions) {
        this.insiderTransactions = insiderTransactions;
    }

    @Column(name = "PEER_COMPANIES")
    public boolean isPeerCompanies() {
        return peerCompanies;
    }

    public void setPeerCompanies(boolean peerCompanies) {
        this.peerCompanies = peerCompanies;
    }

    @Column(name = "OPERATIONAL_KPIS")
    public boolean isOperationalKPIs() {
        return operationalKPIs;
    }

    public void setOperationalKPIs(boolean operationalKPIs) {
        this.operationalKPIs = operationalKPIs;
    }
}
