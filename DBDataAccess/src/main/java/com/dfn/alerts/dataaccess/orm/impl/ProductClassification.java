package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by kokulank on 12/15/2015.
 */
@Entity
@Table(name = "PRODUCT_CLASSIFICATION")
public class ProductClassification implements Serializable {

    private String productClassificationCode;
    private int productClassificationLevel;
    private String parentClassificationCode;
    private String description;
    private Map<String, String> langSpecificDescription;

    @Id
    @Column(name = "PRODUCT_CLASSIFICATION_CODE", length = 10)
    public String getProductClassificationCode() {
        return productClassificationCode;
    }

    public void setProductClassificationCode(String productClassificationCode) {
        this.productClassificationCode = productClassificationCode;
    }

    @Column(name = "PRODUCT_CLASSIFICATION_LEVEL", length = 3)
    public int getProductClassificationLevel() {
        return productClassificationLevel;
    }

    public void setProductClassificationLevel(int productClassificationLevel) {
        this.productClassificationLevel = productClassificationLevel;
    }

    @Column(name = "PARENT_CLASSIFICATION_CODE", length = 10)
    public String getParentClassificationCode() {
        return parentClassificationCode;
    }

    public void setParentClassificationCode(String parentClassificationCode) {
        this.parentClassificationCode = parentClassificationCode;
    }

    @Column(name = "DESCRIPTION", length = 2100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.langSpecificDescription = DataFormatter.GetLanguageSpecificDescriptionMap(description);
    }

    @Transient
    public Map<String, String> getLangSpecificDescription() {
        return langSpecificDescription;
    }
}