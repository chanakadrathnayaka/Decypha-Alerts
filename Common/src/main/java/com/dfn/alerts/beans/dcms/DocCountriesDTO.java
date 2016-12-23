package com.dfn.alerts.beans.dcms;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 11/28/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocCountriesDTO {

    private Integer fileId;
    private String countryCode;
    private Integer status;
    private Timestamp lastUpdatedTime;
    private Timestamp lastSyncTime;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}
