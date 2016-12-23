package com.dfn.alerts.beans.financial;

import java.io.Serializable;

/**
 * Created by Danuran on 4/24/2015.
 */
public class FinancialLineItem implements Serializable {
    private int fieldId;
    private String infoType;
    private String description;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
