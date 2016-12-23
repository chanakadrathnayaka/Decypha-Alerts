package com.dfn.alerts.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 10/8/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeAcquireDTO implements Serializable {
    private Integer maId;
    private String targetCompanies;
    private String targetCompanyCountryCodes;
    private String targetCompanyGicsL4Codes;
    private Integer paymentMethodId;
    private String paidShares;
    private String paidCash;
    private String currency;
    private Double dealSize;
    private Double dealSizeUsd;
    private Date preOffAnnDate;
    private Date offAnnDate;
    private Date mktAuthAppDate;
    private Date completionDate;
    private Date latestDate;
    private Date dealDate;
    private Double ownedBeforePer;
    private Double targetPer;
    private Double offeredPer;
    private Double acceptedPer;
    private Integer newCompanyId;
    private Integer relatedNcCaId;
    private Integer relatedLoanId;
    private Integer maStatus;
    private Integer status;
    private Date lastUpdatedTime;
    private String acquiredCompanies;
    private String acquiredCompaniesCountryCodes;
    private String acquiredCompaniesGicsL4Codes;
    private String acquirerFinancialAdvisers;
    private String acquirerLegalAdvisers;
    private String targetFinancialAdvisers;
    private String targetLegalAdvisers;
    private String dealTypes;
    private String announcements;
    private String news;
    private String documents;
    private Timestamp lastSyncTime;
    private Integer maTypeId;
    private String relatedDebtInstruments;
    private Long stockSymbol;
    private String stockExchangeRatios;
    private Double ratioPE;
    private Double ratioEVSALES;
    private Double ratioEVEBIT;
    private Double ratioEVEBITDA;
    private Double ratioPB;
    private Map<String,MergeAcquireLangDTO> mergeAcquireLangDTOMap;

    private Map<String, String> statusDescription = null;
    private Map<String, String> dealClassification = null;
    private Map<String, String> dealTypeDescription = null;

    public MergeAcquireDTO() {
        mergeAcquireLangDTOMap = new HashMap<String, MergeAcquireLangDTO>(2);
    }

    public Map<String, MergeAcquireLangDTO> getMergeAcquireLangDTOMap() {
        return mergeAcquireLangDTOMap;
    }

    public void setMergeAcquireLangDTOMap(Map<String, MergeAcquireLangDTO> mergeAcquireLangDTOMap) {
        this.mergeAcquireLangDTOMap = mergeAcquireLangDTOMap;
        addStatusDescription();
        addDealClassification();
        addDealTypeDescription();
    }

    public void addStatusDescription(){
        if(statusDescription == null) {
            statusDescription = new HashMap<String, String>(mergeAcquireLangDTOMap.size());
        }

        for(String lang: mergeAcquireLangDTOMap.keySet()) {
            statusDescription.put(lang, mergeAcquireLangDTOMap.get(lang).getStatusDescription());
        }

        if(statusDescription.get("EN") == null) {
            statusDescription.put("EN", "");
        }
    }

    public void addDealClassification(){
        if(dealClassification == null) {
            dealClassification = new HashMap<String, String>(mergeAcquireLangDTOMap.size());
        }

        for(String lang: mergeAcquireLangDTOMap.keySet()) {
            dealClassification.put(lang, mergeAcquireLangDTOMap.get(lang).getDealClassification());
        }

        if(dealClassification.get("EN") == null) {
            dealClassification.put("EN", "");
        }
    }

    public void addDealTypeDescription(){
        if(dealTypeDescription == null) {
            dealTypeDescription = new HashMap<String, String>(mergeAcquireLangDTOMap.size());
        }

        for(String lang: mergeAcquireLangDTOMap.keySet()) {
            dealTypeDescription.put(lang, mergeAcquireLangDTOMap.get(lang).getDealTypeDescription());
        }

        if(dealTypeDescription.get("EN") == null) {
            dealTypeDescription.put("EN", "");
        }
    }

    public Map<String, String> getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(Map<String, String> statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Map<String, String> getDealClassification() {
        return dealClassification;
    }

    public void setDealClassification(Map<String, String> dealClassification) {
        this.dealClassification = dealClassification;
    }

    public Map<String, String> getDealTypeDescription() {
        return dealTypeDescription;
    }

    public void setDealTypeDescription(Map<String, String> dealTypeDescription) {
        this.dealTypeDescription = dealTypeDescription;
    }

    private List<Date> dates = new ArrayList<Date>(5);


    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Integer getMaId() {
        return maId;
    }

    public void setMaId(Integer maId) {
        this.maId = maId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getAcquirerFinancialAdvisers() {
        return acquirerFinancialAdvisers;
    }

    public void setAcquirerFinancialAdvisers(String acquirerFinancialAdvisers) {
        this.acquirerFinancialAdvisers = acquirerFinancialAdvisers;
    }

    public String getAcquirerLegalAdvisers() {
        return acquirerLegalAdvisers;
    }

    public void setAcquirerLegalAdvisers(String acquirerLegalAdvisers) {
        this.acquirerLegalAdvisers = acquirerLegalAdvisers;
    }

    public String getPaidShares() {
        return paidShares;
    }

    public void setPaidShares(String paidShares) {
        this.paidShares = paidShares;
    }

    public String getPaidCash() {
        return paidCash;
    }

    public void setPaidCash(String paidCash) {
        this.paidCash = paidCash;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDealSize() {
        return dealSize;
    }

    public void setDealSize(Double dealSize) {
        this.dealSize = dealSize;
    }

    public Double getDealSizeUsd() {
        return dealSizeUsd;
    }

    public void setDealSizeUsd(Double dealSizeUsd) {
        this.dealSizeUsd = dealSizeUsd;
    }

    public Date getPreOffAnnDate() {
        return preOffAnnDate;
    }

    public void setPreOffAnnDate(Date preOffAnnDate) {
        if(preOffAnnDate!= null){
            dates.add(preOffAnnDate);
        }
        this.preOffAnnDate = preOffAnnDate;
    }

    public Date getOffAnnDate() {
        return offAnnDate;
    }

    public void setOffAnnDate(Date offAnnDate) {
        if(offAnnDate!= null){
            dates.add(offAnnDate);
        }
        this.offAnnDate = offAnnDate;
    }

    public Date getMktAuthAppDate() {
        return mktAuthAppDate;
    }

    public void setMktAuthAppDate(Date mktAuthAppDate) {
        if(mktAuthAppDate!= null){
            dates.add(mktAuthAppDate);
        }
        this.mktAuthAppDate = mktAuthAppDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        if(completionDate!= null){
            dates.add(completionDate);
        }
        this.completionDate = completionDate;
    }

    public Date getLatestDate() {
        if(this.latestDate == null && dates.size()>0){
            this.latestDate = Collections.max(this.dates);
        }
        return latestDate;
    }

    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Double getOwnedBeforePer() {
        return ownedBeforePer;
    }

    public void setOwnedBeforePer(Double ownedBeforePer) {
        this.ownedBeforePer = ownedBeforePer;
    }

    public Double getTargetPer() {
        return targetPer;
    }

    public void setTargetPer(Double targetPer) {
        this.targetPer = targetPer;
    }

    public Double getOfferedPer() {
        return offeredPer;
    }

    public void setOfferedPer(Double offeredPer) {
        this.offeredPer = offeredPer;
    }

    public Double getAcceptedPer() {
        return acceptedPer;
    }

    public void setAcceptedPer(Double acceptedPer) {
        this.acceptedPer = acceptedPer;
    }

    public Integer getNewCompanyId() {
        return newCompanyId;
    }

    public void setNewCompanyId(Integer newCompanyId) {
        this.newCompanyId = newCompanyId;
    }

    public Integer getRelatedNcCaId() {
        return relatedNcCaId;
    }

    public void setRelatedNcCaId(Integer relatedNcCaId) {
        this.relatedNcCaId = relatedNcCaId;
    }

    public Integer getRelatedLoanId() {
        return relatedLoanId;
    }

    public void setRelatedLoanId(Integer relatedLoanId) {
        this.relatedLoanId = relatedLoanId;
    }

    public Integer getMaStatus() {
        return maStatus;
    }

    public void setMaStatus(Integer maStatus) {
        this.maStatus = maStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAcquiredCompanies() {
        return acquiredCompanies;
    }

    public void setAcquiredCompanies(String acquiredCompanies) {
        this.acquiredCompanies = acquiredCompanies;
    }

    public String getTargetFinancialAdvisers() {
        return targetFinancialAdvisers;
    }

    public void setTargetFinancialAdvisers(String targetFinancialAdvisers) {
        this.targetFinancialAdvisers = targetFinancialAdvisers;
    }

    public String getTargetLegalAdvisers() {
        return targetLegalAdvisers;
    }

    public void setTargetLegalAdvisers(String targetLegalAdvisers) {
        this.targetLegalAdvisers = targetLegalAdvisers;
    }

    public String getDealTypes() {
        return dealTypes;
    }

    public void setDealTypes(String dealTypes) {
        this.dealTypes = dealTypes;
    }

    public String getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        this.announcements = announcements;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTargetCompanyCountryCodes() {
        return targetCompanyCountryCodes;
    }

    public void setTargetCompanyCountryCodes(String targetCompanyCountryCodes) {
        this.targetCompanyCountryCodes = targetCompanyCountryCodes;
    }

    public String getTargetCompanyGicsL4Codes() {
        return targetCompanyGicsL4Codes;
    }

    public void setTargetCompanyGicsL4Codes(String targetCompanyGicsL4Codes) {
        this.targetCompanyGicsL4Codes = targetCompanyGicsL4Codes;
    }

    public String getAcquiredCompaniesCountryCodes() {
        return acquiredCompaniesCountryCodes;
    }

    public void setAcquiredCompaniesCountryCodes(String acquiredCompaniesCountryCodes) {
        this.acquiredCompaniesCountryCodes = acquiredCompaniesCountryCodes;
    }

    public String getAcquiredCompaniesGicsL4Codes() {
        return acquiredCompaniesGicsL4Codes;
    }

    public void setAcquiredCompaniesGicsL4Codes(String acquiredCompaniesGicsL4Codes) {
        this.acquiredCompaniesGicsL4Codes = acquiredCompaniesGicsL4Codes;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Integer getMaTypeId() {
        return maTypeId;
    }

    public void setMaTypeId(Integer maTypeId) {
        this.maTypeId = maTypeId;
    }

    public String getRelatedDebtInstruments() {
        return relatedDebtInstruments;
    }

    public void setRelatedDebtInstruments(String relatedDebtInstruments) {
        this.relatedDebtInstruments = relatedDebtInstruments;
    }

    public Long getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(Long stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockExchangeRatios() {
        return stockExchangeRatios;
    }

    public void setStockExchangeRatios(String stockExchangeRatios) {
        this.stockExchangeRatios = stockExchangeRatios;
    }

    public String getTargetCompanies() {
        return targetCompanies;
    }

    public void setTargetCompanies(String targetCompanies) {
        this.targetCompanies = targetCompanies;
    }

    public Double getRatioPB() {
        return ratioPB;
    }

    public void setRatioPB(Double ratioPB) {
        this.ratioPB = ratioPB;
    }

    public Double getRatioPE() {
        return ratioPE;
    }

    public void setRatioPE(Double ratioPE) {
        this.ratioPE = ratioPE;
    }

    public Double getRatioEVSALES() {
        return ratioEVSALES;
    }

    public void setRatioEVSALES(Double ratioEVSALES) {
        this.ratioEVSALES = ratioEVSALES;
    }

    public Double getRatioEVEBIT() {
        return ratioEVEBIT;
    }

    public void setRatioEVEBIT(Double ratioEVEBIT) {
        this.ratioEVEBIT = ratioEVEBIT;
    }

    public Double getRatioEVEBITDA() {
        return ratioEVEBITDA;
    }

    public void setRatioEVEBITDA(Double ratioEVEBITDA) {
        this.ratioEVEBITDA = ratioEVEBITDA;
    }
}
