package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/26/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Url implements Serializable{

    public Url(String url , String urlId,  String label , boolean  isActive){
        this.url = url;
        this.label = label;
        this.isActive = isActive;
        this.urlId = urlId;

    }
    private String url;
    private String urlId;
    private String label;
    private boolean isActive;

    public String getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getUrlId() {
        return urlId;
    }
}
