package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by kokulank on 12/14/2015.
 */
public class ProductClassificationDTO implements Serializable {

    private String productClassificationCode;
    private int productClassificationLevel;
    private String parentClassificationCode;
    private String description;
    private Map<String, String> langSpecificDescription;

    public ProductClassificationDTO(String productClassificationCode, int productClassificationLevel, String parentClassificationCode, String description, Map<String, String> langSpecificDescription) {
        this.productClassificationCode = productClassificationCode;
        this.productClassificationLevel = productClassificationLevel;
        this.parentClassificationCode = parentClassificationCode;
        this.description = description;
        this.langSpecificDescription = langSpecificDescription;
    }

    public String getProductClassificationCode() {
        return productClassificationCode;
    }

    public void setProductClassificationCode(String productClassificationCode) {
        this.productClassificationCode = productClassificationCode;
    }

    public int getProductClassificationLevel() {
        return productClassificationLevel;
    }

    public void setProductClassificationLevel(int productClassificationLevel) {
        this.productClassificationLevel = productClassificationLevel;
    }

    public String getParentClassificationCode() {
        return parentClassificationCode;
    }

    public void setParentClassificationCode(String parentClassificationCode) {
        this.parentClassificationCode = parentClassificationCode;
    }

    public Map<String, String> getlangSpecificDescription() {
        return langSpecificDescription;
    }

    public void setlangSpecificDescription(Map<String, String> langSpecificDescription) {
        this.langSpecificDescription = langSpecificDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
