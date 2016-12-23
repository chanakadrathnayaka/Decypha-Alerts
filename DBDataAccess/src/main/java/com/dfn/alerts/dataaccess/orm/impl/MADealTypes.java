package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chanakar
 * Date: 10/9/13
 * Time: 4:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table( name = "MA_DEAL_TYPE")
public class MADealTypes implements Serializable {
    private String dealId;
    private String desc;
    private Map<String, String> description;

    @Id
    @Column(name ="DEAL_TYPE_ID")
    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    @Column(name = "DESCRIPTION")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        this.description = DataFormatter.GetLanguageSpecificDescriptionMap(desc);
    }

    @Transient
    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }
}
