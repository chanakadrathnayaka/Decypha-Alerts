package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.utils.CommonUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 10/23/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "FILE_PUBLISHER")
public class FilePublisher implements Serializable {
    private Integer companyId;
    private String  companyNameLan;
    private Map<String,String> companyName = null;
    private boolean isResearchProvider;
    private Integer researchProvider;

    @Id
    @Column(name = "COMPANY_ID")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name = "COMPANY_NAME_LAN" )
    public String getCompanyNameLan() {
        return companyNameLan;
    }

    public void setCompanyNameLan(String companyNameLan) {
        this.companyNameLan = companyNameLan;
        this.companyName = CommonUtils.getLanguageDescriptionMap(companyNameLan);

        if(this.companyName == null){
            this.companyName = new HashMap<String, String>();
        }

        if(this.companyName.get("EN") == null){
            this.companyName.put("EN","");
        }
    }

    @Transient
    public Map<String, String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Map<String, String> companyName) {
        this.companyName = companyName;
    }

    @Transient
    public boolean isResearchProvider() {
        return isResearchProvider;
    }

    @Column(name = "IS_RESEARCH_PROVIDER" )
    public Integer getResearchProvider() {
        return researchProvider;
    }

    public void setResearchProvider(Integer researchProvider) {
        this.isResearchProvider = researchProvider != null && researchProvider == 1;
    }
}
