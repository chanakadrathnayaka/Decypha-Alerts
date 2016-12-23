package com.dfn.alerts.beans.dcms;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 11/28/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocRegionsDTO {

    private Integer fileId;
    private Integer regionId;
    private Integer status;
    private Timestamp lastUpdatedTime;
    private Timestamp lastSyncTime;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
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
