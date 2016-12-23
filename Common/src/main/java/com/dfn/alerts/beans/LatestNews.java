package com.dfn.alerts.beans;

import java.util.List;
import java.util.Map;

/**
 * Created by Lasanthak on 9/22/2014.
 */
public class LatestNews {

    private List<Integer> newsIds ;

    private List<Integer> newsIdsToDelete ;

    private String stringNewsIds ;

    private List<String> stringNewsIdsToDelete ;

    private List<Map<String , String>> newNews;

    public List<Integer> getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(List<Integer> newsIds) {
        this.newsIds = newsIds;
    }

    public List<Integer> getNewsIdsToDelete() {
        return newsIdsToDelete;
    }

    public void setNewsIdsToDelete(List<Integer> newsIdsToDelete) {
        this.newsIdsToDelete = newsIdsToDelete;
    }

    public List<Map<String, String>> getNewNews() {
        return newNews;
    }

    public void setNewNews(List<Map<String, String>> newNews) {
        this.newNews = newNews;
    }


    public List<String> getStringNewsIdsToDelete() {
        return stringNewsIdsToDelete;
    }

    public void setStringNewsIdsToDelete(List<String> stringNewsIdsToDelete) {
        this.stringNewsIdsToDelete = stringNewsIdsToDelete;
    }

    public String getStringNewsIds() {
        return stringNewsIds;
    }

    public void setStringNewsIds(String stringNewsIds) {
        this.stringNewsIds = stringNewsIds;
    }
}
