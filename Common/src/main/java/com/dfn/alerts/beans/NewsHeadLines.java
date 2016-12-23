package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 12/6/12
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewsHeadLines implements Serializable {
    private int currentCount = 0;
    private List<Map<String, String>> news = null;
    private boolean hasNext = false;
    private List<Integer> seqIdList = null;
    private List<Integer> newsIdList = null;
    private boolean imdbNewsLimitReached = false;
    private int imdbPageLimit = -1;
    private List<?> dataList=null;
    private int OracleDboffsetValue = 0;
    private int totalNewsCount = 0;
    private int minSeqId;

    public int getTotalCount() {
        return totalNewsCount;
    }

    public void setTotalCount(int totalNewsCount) {
        this.totalNewsCount = totalNewsCount;
    }

    public int getMinSeqId() {
        return minSeqId;
    }

    public void setMinSeqId(int minSeqId) {
        this.minSeqId = minSeqId;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public int getOracleDboffsetValue() {
        return OracleDboffsetValue;
    }

    public void setOracleDboffsetValue(int oracleDboffSetValue) {
        this.OracleDboffsetValue = oracleDboffSetValue;
    }

    public int getImdbPageLimit() {
        return imdbPageLimit;
    }

    public void setImdbPageLimit(int imdbPageLimit) {
        this.imdbPageLimit = imdbPageLimit;
    }

    public List<Integer> getSeqIdList() {
        return seqIdList;
    }

    public void setSeqIdList(List<Integer> seqIdList) {
        this.seqIdList = seqIdList;
    }

    public boolean isImdbNewsLimitReached() {
        return imdbNewsLimitReached;
    }

    public void setImdbNewsLimitReached(boolean imdbNewsLimitReached) {
        this.imdbNewsLimitReached = imdbNewsLimitReached;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public List<Map<String, String>> getNews() {
        return news;
    }

    public void setNews(List<Map<String, String>> news) {
        this.news = news;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setNewsIdList(List<Integer> newsIdList) {
        this.newsIdList = newsIdList;
    }

    public List<Integer> getNewsIdList() {
        return newsIdList;
    }
}
