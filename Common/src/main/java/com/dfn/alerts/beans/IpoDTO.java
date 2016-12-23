package com.dfn.alerts.beans;

import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.utils.CommonUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 7/30/13
 * Time: 10:35 AM
 */
@SuppressWarnings("unused")
public class IpoDTO{

    private Integer id;                          // IPO_ID
    private String  sourceId;
    private String  currencyId;
    private double issuePrice;                   // ISSUE_PRICE
    private Integer announcementId;              // ANNOUNCEMENT_ID
    private Date announceDate;                   // ANNOUNCE_DATE
    private Integer companyId;                   // COMPANY_ID
    private Integer newsId;                      // IPO_NEWS_ID
    private double coverageVolume;               // COVERAGE_VOLUME
    private double ipoVolume;                    // IPO_VOLUME
    private Integer subscriptionType;            // IPO_SUBSCRIPTION_TYPE
    private double coveragePercentage;           // COVERAGE_PERCENTAGE
    private Date endDate;                        // IPO_END_DATE
    private Date custodySettlementDate;          // CUSTODY_SETTLEMENT_DATE
    private Date refundDate;                     // IPO_REFUND_DATE
    private Date startDate;                      // IPO_START_DATE
    private Integer ipoStatus;                   // IPO_STATUS
    private String documentURL;                  // DOCUMENT_URL
    private String documentId;                   // DOCUMENT_ID
    private Integer newsFileId;                  // NEWS_FILE_ID        //TODO :: check
    private Integer upcomingNewsId;              // UPCOMING_NEWS_ID
    private Integer detailsReleasedNewsId;       // DETAILS_RELEASED_NEWS_ID
    private Integer activeNewsId;                // ACTIVE_NEWS_ID
    private Integer toBeTradedNewsId;            // TO_BE_TRADED_NEWS_ID
    private Integer tradedNewsId;                // TRADED_NEWS_ID
    private Integer withdrawnNewsId;             // WITHDRAWN_NEWS_ID
    private String managerIds;                   // MANAGER_ID_LIST
    private long tickerSerial;                   // TICKER_SERIAL
    private double priceOnTradingDate;           // ISSUE_PRICE
    private Date lastUpdatedTime;                // LAST_UPDATED_TIME
    private Date tradingDate;                    // TRADING_DATE
    private Date cancellationDate;               // CANCELLATION_DATE
    private Date detailsDisclosingDate;          // DETAILS_DISCLOSING_DATE
    private Date latestDate;                     // Latest date from ANNOUNCEMENT_DATE,REFUND_DATE, IPO_START_DATE, IPO_END_DATE, CUSTODY_SETTLEMENT_DATE, TRADING_DATE, CANCELLATION_DATE, DETAILS_DISCLOSING_DATE
    private Integer    status;
    private Timestamp lastSyncTime = null;

    private Map<String,String> documentURLMap = null;

    private String  countryCode;
    private String  companyNameLan;
    private String  gicsL2Lan;
    private String  gicsL3Lan;
    private String  gicsL4Lan;
    private String  gicsL4Code;
    private String  gicsL3Code;
    private String  gicsL2Code;

    private Map<String,String> gicsL2s                 = null;
    private Map<String,String> gicsL3s                 = null;
    private Map<String,String> gicsL4s                 = null;
    private Map<String,String> companyName             = null;

    private Map<String,String> langSpecificDescs       = new HashMap<String, String>(2);

    private List<Date> dates = new ArrayList<Date>(7);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public double getIssuePrice() {
        return issuePrice;
    }

    public void setIssuePrice(double issuePrice) {
        this.issuePrice = issuePrice;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Date getAnnounceDate() {
        return announceDate;
    }

    public void setAnnounceDate(Date announceDate) {
        if(announceDate!= null){
            dates.add(announceDate);
        }
        this.announceDate = announceDate;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public double getCoverageVolume() {
        return coverageVolume;
    }

    public void setCoverageVolume(double coverageVolume) {
        this.coverageVolume = coverageVolume;
    }

    public double getIpoVolume() {
        return ipoVolume;
    }

    public void setIpoVolume(double ipoVolume) {
        this.ipoVolume = ipoVolume;
    }

    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public double getCoveragePercentage() {
        return coveragePercentage;
    }

    public void setCoveragePercentage(double coveragePercentage) {
        this.coveragePercentage = coveragePercentage;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if(endDate!= null){
            dates.add(endDate);
        }
        this.endDate = endDate;
    }

    public Date getCustodySettlementDate() {
        return custodySettlementDate;
    }

    public void setCustodySettlementDate(Date custodySettlementDate) {
        if(custodySettlementDate!= null){
            dates.add(custodySettlementDate);
        }
        this.custodySettlementDate = custodySettlementDate;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        if(refundDate!= null){
            dates.add(refundDate);
        }
        this.refundDate = refundDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if(startDate!= null){
            dates.add(startDate);
        }
        this.startDate = startDate;
    }

    public Integer getIpoStatus() {
        return ipoStatus;
    }

    public void setIpoStatus(Integer ipoStatus) {
        this.ipoStatus = ipoStatus;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
        this.documentURLMap = CommonUtils.getLanguageDescriptionMap(documentURL);
    }

    public Integer getNewsFileId() {
        return newsFileId;
    }

    public void setNewsFileId(Integer newsFileId) {
        this.newsFileId = newsFileId;
    }

    public Integer getUpcomingNewsId() {
        return upcomingNewsId;
    }

    public void setUpcomingNewsId(Integer upcomingNewsId) {
        this.upcomingNewsId = upcomingNewsId;
    }

    public Integer getDetailsReleasedNewsId() {
        return detailsReleasedNewsId;
    }

    public void setDetailsReleasedNewsId(Integer detailsReleasedNewsId) {
        this.detailsReleasedNewsId = detailsReleasedNewsId;
    }

    public Integer getActiveNewsId() {
        return activeNewsId;
    }

    public void setActiveNewsId(Integer activeNewsId) {
        this.activeNewsId = activeNewsId;
    }

    public Integer getToBeTradedNewsId() {
        return toBeTradedNewsId;
    }

    public void setToBeTradedNewsId(Integer toBeTradedNewsId) {
        this.toBeTradedNewsId = toBeTradedNewsId;
    }

    public Integer getTradedNewsId() {
        return tradedNewsId;
    }

    public void setTradedNewsId(Integer tradedNewsId) {
        this.tradedNewsId = tradedNewsId;
    }

    public Integer getWithdrawnNewsId() {
        return withdrawnNewsId;
    }

    public void setWithdrawnNewsId(Integer withdrawnNewsId) {
        this.withdrawnNewsId = withdrawnNewsId;
    }

    public Map<String, String> getDocumentURLMap() {
        return documentURLMap;
    }

    public void setDocumentURLMap(Map<String, String> documentURLMap) {
        this.documentURLMap = documentURLMap;
    }

    public String getManagerIds() {
        return managerIds;
    }

    public void setManagerIds(String managerIds) {
        this.managerIds = managerIds;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public long getTickerSerial() {
        return tickerSerial;
    }

    public void setTickerSerial(long tickerSerial) {
        this.tickerSerial = tickerSerial;
    }

    public double getPriceOnTradingDate() {
        return priceOnTradingDate;
    }

    public void setPriceOnTradingDate(double priceOnTradingDate) {
        this.priceOnTradingDate = priceOnTradingDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(Date tradingDate) {
        if(tradingDate!= null){
            dates.add(tradingDate);
        }
        this.tradingDate = tradingDate;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        if(cancellationDate!= null){
            dates.add(cancellationDate);
        }
        this.cancellationDate = cancellationDate;
    }

    public Date getDetailsDisclosingDate() {
        return detailsDisclosingDate;
    }

    public void setDetailsDisclosingDate(Date detailsDisclosingDate) {
        if(detailsDisclosingDate!= null){
            dates.add(detailsDisclosingDate);
        }
        this.detailsDisclosingDate = detailsDisclosingDate;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCompanyNameLan() {
        return companyNameLan;
    }

    public void setCompanyNameLan(String companyNameLan) {
        this.companyNameLan = companyNameLan;
    }

    public String getGicsL2Lan() {
        return gicsL2Lan;
    }

    public void setGicsL2Lan(String gicsL2Lan) {
        this.gicsL2s = CommonUtils.getLanguageDescriptionMap(gicsL2Lan);
        this.gicsL2Lan = gicsL2Lan;
    }

    public String getGicsL3Lan() {
        return gicsL3Lan;
    }

    public void setGicsL3Lan(String gicsL3Lan) {
        this.gicsL3s = CommonUtils.getLanguageDescriptionMap(gicsL3Lan);
        this.gicsL3Lan = gicsL3Lan;
    }

    public Map<String, String> getGicsL3s() {
        return gicsL3s;
    }

    public String getGicsL4Lan() {
        return gicsL4Lan;
    }

    public void setGicsL4Lan(String gicsL4Lan) {
        this.gicsL4s = CommonUtils.getLanguageDescriptionMap(gicsL4Lan);
        this.gicsL4Lan = gicsL4Lan;
    }

    public String getGicsL4Code() {
        return gicsL4Code;
    }

    public void setGicsL4Code(String gicsL4Code) {
        this.gicsL4Code = gicsL4Code;
    }

    public String getGicsL3Code() {
        return gicsL3Code;
    }

    public void setGicsL3Code(String gicsL3Code) {
        this.gicsL3Code = gicsL3Code;
    }

    public String getGicsL2Code() {
        return gicsL2Code;
    }

    public void setGicsL2Code(String gicsL2Code) {
        this.gicsL2Code = gicsL2Code;
    }

    public Map<String, String> getGicsL2s() {
        return gicsL2s;
    }

    public Map<String, String> getGicsL4s() {
        return gicsL4s;
    }

    public Map<String, String> getCompanyName() {
        return companyName;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Map<String, String> getLangSpecificDescs() {
        return langSpecificDescs;
    }

    public void addLangSpecificDescs(String language, String columnPrefix, String desc){
        if(DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME.equals(columnPrefix)){
            if(this.companyName == null){
                this.companyName = new HashMap<String, String>();
            }
            companyName.put(language, desc);
        }
        langSpecificDescs.put((columnPrefix + language).toUpperCase(), desc);
    }

    public String getStatusDesc(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_STATUS_DESC + language);
    }

    public String getTypeDesc(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_TYPE_DESC + language);
    }

    public String getCountryName(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_COUNTRY_DESC + language);
    }

    public String getCompanyName(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME + language);
    }

    public String getGICSL2(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L2_DESC + language);
    }

    public String getGICSL3(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L3_DESC + language);
    }

    public String getGICSL4(String language){
        return langSpecificDescs.get(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L4_DESC + language);
    }

}
