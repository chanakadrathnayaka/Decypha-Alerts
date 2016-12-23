package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 2/19/14
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewsSourceDTO implements Serializable {

    private String sourceId;


    private Map<String, String> description ;

    public NewsSourceDTO(String sourceId, Map<String, String> description ){
        this.sourceId = sourceId;
        this.description = description;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }
}
