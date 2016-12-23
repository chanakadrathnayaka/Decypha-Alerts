package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.constants.DBConstants;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 3/27/13
 * Time: 2:30 PM
 */

public class Announcements implements Serializable{
    private Integer announcementId;
    private Timestamp date;
    private String sourceId;
    private String tickerId;
    private String annHeadLine;
    private String sectorCode;
    private Long tickerSerial;
    private Integer annSourceSerial;
    private Integer priorityId;
    private String status;
    private Integer seqId;
    private String url;
    private String gicsL2Code;
    private String gicsL3Code;
    private String dcmsId;

    private Timestamp lastUpdatedTime;

    private Map<String,String> announcement = new HashMap<String, String>();

    private Map<String,String> langSpecificData = new HashMap<String, String>(2);

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_ID,String.valueOf(announcementId));
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_DATE, date.toString());
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SOURCE_ID,sourceId);
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.TICKER_ID,tickerId);
    }

    public String getAnnHeadLine() {
        return annHeadLine;
    }

    public void setAnnHeadLine(String annHeadLine) {
        this.annHeadLine = annHeadLine;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.ANN_HEADLINE,annHeadLine);
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sector) {
        this.sectorCode = sector;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SECTOR_CODE,sector);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.URL,url);
    }

    public Long getTickerSerial() {
        return tickerSerial;
    }

    public void setTickerSerial(Long tickerSerial) {
        this.tickerSerial = tickerSerial;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.TICKER_SERIAL,String.valueOf(tickerSerial));
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priority) {
        this.priorityId = priority;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.PRIORITY_ID,String.valueOf(priority));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.STATUS,status);
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SEQ_ID,String.valueOf(seqId));
    }

    public String getGicsL2Code() {
        return gicsL2Code;
    }

    public void setGicsL2Code(String gicsL2Code) {
        this.gicsL2Code = gicsL2Code;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L2_CODE,gicsL2Code);
    }

    public String getGicsL3Code() {
        return gicsL3Code;
    }

    public void setGicsL3Code(String gicsL3Code) {
        this.gicsL3Code = gicsL3Code;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L3_CODE,gicsL3Code);
    }

    public Integer getAnnSourceSerial() {
        return annSourceSerial;
    }

    public void setAnnSourceSerial(Integer annSourceSerial) {
        this.annSourceSerial = annSourceSerial;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_SOURCE_SERIAL,String.valueOf(annSourceSerial));
    }

    public String getDcmsId() {
       return dcmsId;
    }

    public void setDcmsId(String dcmsId) {
        this.dcmsId = dcmsId;
        announcement.put(DBConstants.AnnouncementDatabaseColumns.DCMS_IDS,dcmsId);
    }

    public Map<String,String> getAnnouncement(String languageId) {
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.ANN_HEADLINE, getAnnouncementHeadline(languageId));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.URL, getAnnouncementUrl(languageId));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.DCMS_IDS, dcmsId);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_SOURCE_SERIAL,String.valueOf(annSourceSerial));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L3_CODE,gicsL3Code);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L2_CODE,gicsL2Code);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SEQ_ID,String.valueOf(seqId));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.STATUS,status);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.PRIORITY_ID,String.valueOf(priorityId));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.TICKER_SERIAL,String.valueOf(tickerSerial));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SECTOR_CODE,sectorCode);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.TICKER_ID,tickerId);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.SOURCE_ID,sourceId);
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_DATE,date.toString());
        announcement.put(DBConstants.AnnouncementDatabaseColumns.ANN_ID, String.valueOf(announcementId));
        announcement.put(DBConstants.AnnouncementDatabaseColumns.LAST_UPDATED_TIME, String.valueOf(lastUpdatedTime));

        return announcement;
    }

    public void addLangSpecificDescs(String language, String columnPrefix, String desc){
        langSpecificData.put((columnPrefix + language).toUpperCase(), desc);
    }

    public String getAnnouncementHeadline(String language){
        return langSpecificData.get(DBConstants.LangSpecificDatabaseColumns.ANN_HEADLINE + language);
    }

    public String getAnnouncementUrl(String language){
        return langSpecificData.get(DBConstants.LangSpecificDatabaseColumns.ANN_URL + language);
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
