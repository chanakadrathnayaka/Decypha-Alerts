package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by udaras on 4/1/2014.
 */
public class CompanyTypeDTO implements Serializable {

    private String companyTypeId;
    private Map<String, String> companyTypeDesc = new HashMap<String, String>(2);

    public CompanyTypeDTO(String companyTypeId, Map<String, String> companyTypeDesc) {
        this.companyTypeId = companyTypeId;
        this.companyTypeDesc = companyTypeDesc;
    }

    public String getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(String companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public Map<String, String> getCompanyTypeDesc() {
        return companyTypeDesc;
    }

    public void setCompanyTypeDesc(Map<String, String> companyTypeDesc) {
        this.companyTypeDesc = companyTypeDesc;
    }
}
