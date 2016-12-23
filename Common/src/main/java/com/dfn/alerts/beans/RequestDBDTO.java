package com.dfn.alerts.beans;

import com.dfn.alerts.constants.DBConstants;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Duminda
 * Date: 6/30/13
 * Time: 3:14 PM
 * To pass the database parameter data
 */
public  class RequestDBDTO {
    private String procedureName;
    private Timestamp lastRunTime;
    private String downloadURL;
    private String tableName;
    private String gicsCode;
    private String sourceId;
    private DBConstants.DBDataSyncTypes syncTypes;
    private String id;
    private int type;
    private String query;
    private boolean lastRunTimeRequired;
    private String params;
    private List<String> supportedLang;
    private String preferredLang;
    private Object data;
    private Map<String,Object> customParams;
    private String countryCode;
    private String schedulerTypes;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getGicsCode() {
        return gicsCode;
    }

    public void setGicsCode(String gicsCode) {
        this.gicsCode = gicsCode;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Timestamp getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Timestamp lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public DBConstants.DBDataSyncTypes getSyncTypes() {
        return syncTypes;
    }

    public void setSyncTypes(DBConstants.DBDataSyncTypes syncTypes) {
        this.syncTypes = syncTypes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isLastRunTimeRequired() {
        return lastRunTimeRequired;
    }

    public void setLastRunTimeRequired(boolean lastRunTimeRequired) {
        this.lastRunTimeRequired = lastRunTimeRequired;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<String> getSupportedLang() {
        return supportedLang;
    }

    public void setSupportedLang(List<String> supportedLang) {
        this.supportedLang = supportedLang;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPreferredLang() {
        return preferredLang;
    }

    public void setPreferredLang(String preferredLang) {
        this.preferredLang = preferredLang;
    }

    public Map<String, Object> getCustomParams() {
        return customParams;
    }

    public void setCustomParams(Map<String, Object> customParams) {
        this.customParams = customParams;
    }


    public String getSchedulerTypes() {
        return schedulerTypes;
    }

    public void setSchedulerTypes(String schedulerTypes) {
        this.schedulerTypes = schedulerTypes;
    }
}
