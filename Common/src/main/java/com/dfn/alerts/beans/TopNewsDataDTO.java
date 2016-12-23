package com.dfn.alerts.beans;

/**
 * Created by aravindal on 10/09/14.
 */
public class TopNewsDataDTO {
    //news edition and section as comma separated value pairs (e1,s1),(e2,s2)
    private String newsEditionSection;
    //news id
    private int newsId;
    private int isTopStory;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsEditionSection() {
        return newsEditionSection;
    }

    public int getIsTopStory() {
        return isTopStory;
    }

    public void setIsTopStory(int isTopStory) {
        this.isTopStory = isTopStory;
    }

    public void setNewsEditionSection(String newsEditionSection) {
        this.newsEditionSection = newsEditionSection;
    }
}
