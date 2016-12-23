package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 12/11/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopNewsEditionDTO implements Serializable {

    private String edition;
    private int editionType;
    private int newsEditionId;
    private Map<String,String> editionDescription;
    private String editionDescriptionLan;

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getEditionType() {
        return editionType;
    }

    public void setEditionType(int editionType) {
        this.editionType = editionType;
    }

    public int getNewsEditionId() {
        return newsEditionId;
    }

    public void setNewsEditionId(int newsEditionId) {
        this.newsEditionId = newsEditionId;
    }

    public Map<String, String> getEditionDescription() {
        return editionDescription;
    }

    public void setEditionDescription(Map<String, String> editionDescription) {
        this.editionDescription = editionDescription;
    }

    public String getEditionDescriptionLan() {
        return editionDescriptionLan;
    }

    public void setEditionDescriptionLan(String editionDescriptionLan) {
        this.editionDescriptionLan = editionDescriptionLan;
    }
}
