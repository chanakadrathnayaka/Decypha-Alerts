package com.dfn.alerts.beans.news;

/**
 * Created by Danuran on 10/9/2015.
 */
public class NewsIndividualDTO extends NewsDTO {
    private long individualId;
    private String name;

    public NewsIndividualDTO(long newsId) {
        super(newsId);
    }

    public long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(long individualId) {
        this.individualId = individualId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
