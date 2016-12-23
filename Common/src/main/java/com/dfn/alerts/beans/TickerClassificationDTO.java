package com.dfn.alerts.beans;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by aravindal on 29/10/14.
 */
public class TickerClassificationDTO implements Serializable {
    private int typeId;
    private String description;
    private Map<String,String> langDescMap;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.langDescMap = CommonUtils.getLanguageDescriptionMap(description);
    }

    public Map<String, String> getLangDescMap() {
        return langDescMap;
    }

}
