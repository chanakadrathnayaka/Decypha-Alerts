package com.dfn.alerts.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 8/30/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataSyncStatusDTO implements Serializable {
    private String id;
    private int type;
    private String lastId;
    private String description;
    private String params;
    private Timestamp lastUpdatedTime;

    public DataSyncStatusDTO(){

    }

    public DataSyncStatusDTO(String id, int type, String lastId, String description, String params, Timestamp lastUpdatedTime){
        this.id = id;
        this.type = type;
        this.lastId = lastId;
        this.description = description;
        this.params = params;
        this.lastUpdatedTime = lastUpdatedTime;
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

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return this.id + this.type + this.lastId+  this.description +  this.params + this.lastUpdatedTime;
    }
}
