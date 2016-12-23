package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 4/11/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "FUND_CLASS")
public class FundClass implements Serializable {
    private Integer fundClass;
    private String shortDesc;
    private Map<String,String> shortDescription;

    @Id
    @Column( name = "FUND_CLASS", length=20)
    public Integer getFundClass() {
        return fundClass;
    }

    public void setFundClass(Integer fundClass) {
        this.fundClass = fundClass;
    }

    @Column( name="SHRT_DSC", length = 200)
    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
        this.shortDescription = DataFormatter.GetLanguageSpecificDescriptionMap(shortDesc);
    }

    @Transient
    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Map<String, String> shortDescription) {
        this.shortDescription = shortDescription;
    }
}
