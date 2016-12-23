package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 2/21/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class CorporateActionTypeItem implements Serializable {
    private String value;
    private Map<String, String> displayText;

    public CorporateActionTypeItem(String value, Map<String, String> displayText) {
        this.value = value;
        this.displayText = displayText;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getDisplayText() {
        return displayText;
    }

    public void setDisplayText(Map<String, String> displayText) {
        this.displayText = displayText;
    }
}
