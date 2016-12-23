package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chathurangad
 * Date: 5/21/13
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataListDTO implements Serializable {
    private Map<String, Map<String,String>> dataMapList = null;
    private List<Map<String,String>> dataList = null;
    private Map<String, Map<String,String>> dataProviderMapList = null;
    int maxCount = 0;
    Map<String,String> maxCompanyCounts;

    public Map<String, String> getMaxCompanyCounts() {
        return maxCompanyCounts;
    }

    public void setMaxCompanyCounts(Map<String, String> maxCompanyCounts) {
        this.maxCompanyCounts = maxCompanyCounts;
    }

    public Map<String, Map<String,String>> getDataMapList() {
        return dataMapList;
    }


    public void setDataMapList(Map<String, Map<String,String>> dataMapList) {
        this.dataMapList = dataMapList;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public Map<String, Map<String, String>> getDataProviderMapList() {
        return dataProviderMapList;
    }

    public void setDataProviderMapList(Map<String, Map<String, String>> dataProviderMapList) {
        this.dataProviderMapList = dataProviderMapList;
    }
}
