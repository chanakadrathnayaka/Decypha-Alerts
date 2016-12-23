package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by udaras on 4/1/2014.
 */
@Entity
@Table(name = "COMPANY_TYPES")
public class CompanyTypes implements Serializable{
    private String companyTypeId;
    private String companyTypeDescEN;
    private String companyTypeDescAR;

    private Map<String, String> companyTypeDesc = new HashMap<String, String>(2);

    @Id
    @Column(name = "COMPANY_TYPE_ID")
    public String getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(String companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    @Column(name = "COMPANY_TYPE_DESC_EN")
    public String getCompanyTypeDescEN() {
        return companyTypeDescEN;
    }

    public void setCompanyTypeDescEN(String companyTypeDescEN) {
        this.companyTypeDescEN = companyTypeDescEN;
        this.companyTypeDesc.put("EN", companyTypeDescEN);
    }

    @Column(name = "COMPANY_TYPE_DESC_AR")
    public String getCompanyTypeDescAR() {
        return companyTypeDescAR;
    }

    public void setCompanyTypeDescAR(String companyTypeDescAR) {
        this.companyTypeDescAR = companyTypeDescAR;
        this.companyTypeDesc.put("AR", companyTypeDescAR);
    }

    @Transient
    public Map<String, String> getCompanyTypeDesc() {
        return companyTypeDesc;
    }
}
