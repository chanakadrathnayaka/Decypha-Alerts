package com.dfn.alerts.beans.news;

import java.sql.Timestamp;

/**
 * Created by hasarindat on 7/3/2015.
 */
public class NewsLangDTO {

    private String newsHeadline;
    private String url;
    private int approvalStatus;
    private String newsSourceDescription;
    private String gicsL3Descriptions;
    private Timestamp newsLastUpdatedTime;
    private Timestamp newsContributedTime;

    public String getNewsHeadline() {
        return newsHeadline;
    }

    public void setNewsHeadline(String newsHeadline) {
        this.newsHeadline = newsHeadline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getNewsSourceDescription() {
        return newsSourceDescription;
    }

    public void setNewsSourceDescription(String newsSourceDescription) {
        this.newsSourceDescription = newsSourceDescription;
    }

    public boolean isApproved(){
        return approvalStatus == 1;
    }

    public String getGicsL3Descriptions() {
        return gicsL3Descriptions;
    }

    public void setGicsL3Descriptions(String gicsL3Descriptions) {
        this.gicsL3Descriptions = gicsL3Descriptions;
    }

    public Timestamp getNewsLastUpdatedTime() {
        return newsLastUpdatedTime;
    }

    public void setNewsLastUpdatedTime(Timestamp newsLastUpdatedTime) {
        this.newsLastUpdatedTime = newsLastUpdatedTime;
    }

    public Timestamp getNewsContributedTime() {
        return newsContributedTime;
    }

    public void setNewsContributedTime(Timestamp newsContributedTime) {
        this.newsContributedTime = newsContributedTime;
    }
}
