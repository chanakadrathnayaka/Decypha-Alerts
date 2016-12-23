package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.dataaccess.utils.ColumnLengths;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 3/20/13
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "NEWS",uniqueConstraints = @UniqueConstraint(columnNames = {"SEQ_ID","LANGUAGE_ID"}))
public class News implements Serializable {
    private Integer seqId;
    private Integer newsId;
    private Integer extId;
    private String newsProvider;
    private String assetClass;
    private Date newsDate;
    private String country;
    private Integer hotNewsIndicator;
    private String tickerId;
    private String sourceId;
    private Date lastUpdatedTime;
    private String status;
    private String languageId;
    private String newsHeadlineLn;
    private String exchange;
    private String symbol;
    private String editorialCode;
    private String geoRegionCode;
    private String governmentCode;
    private String individualCode;
    private String industryCode;
    private String marketSectorCode;
    private String productServicesCode;
    private String internalClassType;
    private Integer approvalStatus;
    private String url;
    private Integer uniqueSequenceId;
    private String companyId;

    //news source
    private String newsSourceId;
    private String newsSourceDesc;
    private Integer isTopStory;
    private String topNewsEditionSection;
    private int node1Status;
    private int node2Status;


    private Map<String,String> newsHeadLine = new HashMap<String, String>(28);

    @Id
    @Column(name = "SEQ_ID", length = ColumnLengths.L20, nullable = false)
    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.SEQ_ID,String.valueOf(this.seqId));
    }

    @Column(name = "NEWS_ID", length = ColumnLengths.L20, nullable = false)
    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_ID,String.valueOf(this.newsId));
    }

    @Column(name = "NEWS_PROVIDER", length = ColumnLengths.L20, nullable = true)
    public String getNewsProvider() {
        return newsProvider;
    }

    public void setNewsProvider(String newsProvider) {
        this.newsProvider = newsProvider;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_PROVIDER,this.newsProvider);
    }

    @Column(name = "NEWS_DATE", nullable = true)
    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_DATE,this.newsDate.toString());
    }

    @Column(name = "COUNTRY", length = ColumnLengths.L200, nullable = true)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.COUNTRY,this.country);
    }

    @Column(name = "TICKER_ID", length = ColumnLengths.L2000, nullable = true)
    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.TICKER_ID,this.tickerId);
    }

    @Column(name = "SOURCE_ID", length = ColumnLengths.L200, nullable = true)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.SOURCE_ID,this.sourceId);

    }

    @Column(name = "LAST_UPDATED_TIME", nullable = true)
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.LAST_UPDATED_TIME,this.lastUpdatedTime.toString());
    }

    @Column(name = "STATUS", length = ColumnLengths.L1, nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.STATUS,this.status);
    }

    @Id
    @Column(name = "LANGUAGE_ID", length = ColumnLengths.L5, nullable = true)
    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.LANGUAGE_ID,this.languageId);
    }

    @Column(name = "NEWS_HEADLINE_LN", length = ColumnLengths.L4000, nullable = true)
    public String getNewsHeadlineLn() {
        return newsHeadlineLn;
    }

    public void setNewsHeadlineLn(String newsHeadlineLn) {
        this.newsHeadlineLn = newsHeadlineLn;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_HEADLINE_LN,this.newsHeadlineLn);
    }

    @Column(name = "EXCHANGE", length = ColumnLengths.L200, nullable = true)
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.EXCHANGE,this.exchange);
    }

    @Column(name = "SYMBOL", length = ColumnLengths.L200, nullable = true)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.SYMBOL,this.symbol);
    }

    @Column(name = "EDITORIAL_CODE", length = ColumnLengths.L200, nullable = true)
    public String getEditorialCode() {
        return editorialCode;
    }

    public void setEditorialCode(String editorialCode) {
        this.editorialCode = editorialCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.EDITORIAL_CODE,this.editorialCode);
    }

    @Column(name = "GEO_REGION_CODE", length = ColumnLengths.L200, nullable = true)
    public String getGeoRegionCode() {
        return geoRegionCode;
    }

    public void setGeoRegionCode(String geoRegionCode) {
        this.geoRegionCode = geoRegionCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.GEO_REGION_CODE,this.geoRegionCode);
    }

    @Column(name = "GOVERNMENT_CODE", length = ColumnLengths.L200, nullable = true)
    public String getGovernmentCode() {
        return governmentCode;
    }

    public void setGovernmentCode(String governmentCode) {
        this.governmentCode = governmentCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.GOVERNMENT_CODE,this.governmentCode);
    }

    @Column(name = "INDIVIDUAL_CODE", length = ColumnLengths.L200, nullable = true)
    public String getIndividualCode() {
        return individualCode;
    }

    public void setIndividualCode(String individualCode) {
        this.individualCode = individualCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.INDIVIDUAL_CODE,this.individualCode);
    }

    @Column(name = "INDUSTRY_CODE", length = ColumnLengths.L20, nullable = true)
    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.INDUSTRY_CODE,this.industryCode);
    }

    @Column(name = "MARKET_SECTOR_CODE", length = ColumnLengths.L200, nullable = true)
    public String getMarketSectorCode() {
        return marketSectorCode;
    }

    public void setMarketSectorCode(String marketSectorCode) {
        this.marketSectorCode = marketSectorCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.MARKET_SECTOR_CODE,this.marketSectorCode);
    }

    @Column(name = "PRODUCT_SERVICES_CODE", length = ColumnLengths.L40, nullable = true)
    public String getProductServicesCode() {
        return productServicesCode;
    }

    public void setProductServicesCode(String productServicesCode) {
        this.productServicesCode = productServicesCode;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.PRODUCT_SERVICES_CODE,this.productServicesCode);
    }

    @Column(name = "INTERNAL_CLASS_TYPE", length = ColumnLengths.L40, nullable = true)
    public String getInternalClassType() {
        return internalClassType;
    }

    public void setInternalClassType(String internalClassType) {
        this.internalClassType = internalClassType;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.INTERNAL_CLASS_TYPE,this.internalClassType);
    }

    @Column(name = "APPROVAL_STATUS", length = ColumnLengths.L10, nullable = true)
    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.APPROVAL_STATUS,String.valueOf(this.approvalStatus));
    }

    @Column(name = "URL", length = ColumnLengths.L200, nullable = true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.URL,this.url);
    }

    @Column(name = "ASSET_CLASS", length = ColumnLengths.L200, nullable = true)
    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.ASSET_CLASS,this.assetClass);
    }

    @Column(name = "COMPANY_ID", length = ColumnLengths.L200, nullable = true)
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.COMPANY_ID,this.companyId);
    }

    @Column(name = "HOT_NEWS_INDICATOR", length = ColumnLengths.L10, nullable = true)
    public Integer getHotNewsIndicator() {
        return hotNewsIndicator;
    }

    public void setHotNewsIndicator(Integer hotNewsIndicator) {
        this.hotNewsIndicator = hotNewsIndicator;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.HOT_NEWS_INDICATOR,String.valueOf(this.hotNewsIndicator));
    }

    @Column(name = "UNIQUE_SEQUENCE_ID", length = ColumnLengths.L19, nullable = true)
    public Integer getUniqueSequenceId() {
        return uniqueSequenceId;
    }

    public void setUniqueSequenceId(Integer uniqueSequenceId) {
        this.uniqueSequenceId = uniqueSequenceId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.UNIQUE_SEQUENCE_ID,String.valueOf(this.uniqueSequenceId));
    }

    @Column(name = "EXT_ID", length = ColumnLengths.L19, nullable = true)
    public Integer getExtId() {
        return extId;
    }

    public void setExtId(Integer extId) {
        this.extId = extId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.EXT_ID,String.valueOf(this.extId));
    }

    @Column(name = "NEWS_SOURCE_ID", nullable = true)
    public String getNewsSourceId() {
        return newsSourceId;
    }

    public void setNewsSourceId(String newsSourceId) {
        this.newsSourceId = newsSourceId;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID, this.newsSourceId);
    }

    @Column(name = "NEWS_SOURCE_DESC", nullable = true)
    public String getNewsSourceDesc() {
        return newsSourceDesc;
    }

    public void setNewsSourceDesc(String newsSourceDesc) {
        this.newsSourceDesc = newsSourceDesc;
        newsHeadLine.put(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_DESC, this.newsSourceDesc);
    }

    public Map<String,String> accessNewsHeadLine(){
        return newsHeadLine;
    }

    @Column(name = "IS_TOP_STORY")
    public Integer getIsTopStory() {
        return isTopStory;
    }

    public void setIsTopStory(Integer isTopStory) {
        this.isTopStory = isTopStory;
    }

    @Column(name = "TOP_NEWS_EDITION_SECTION", nullable = true)
    public String getTopNewsEditionSection() {
        return topNewsEditionSection;
    }

    public void setTopNewsEditionSection(String topNewsEditionSection) {
        this.topNewsEditionSection = topNewsEditionSection;
    }

    @Column(name = "NODE_1_STATUS")
    public int getNode1Status() {
        return node1Status;
    }

    public void setNode1Status(int node1Status) {
        this.node1Status = node1Status;
    }

    @Column(name = "NODE_2_STATUS")
    public int getNode2Status() {
        return node2Status;
    }

    public void setNode2Status(int node2Status) {
        this.node2Status = node2Status;
    }
}
