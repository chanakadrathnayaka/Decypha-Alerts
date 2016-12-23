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
@Table(name = "IPO_SUBS_TYPE")
public class IpoSubscriptionType implements Serializable {
    private Integer subscriptionType;
    private String desc;
    private Map<String,String> description;

    @Id
    @Column( name = "IPO_SUBSCRIPTION_TYPE", length=20)
    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Column( name="DESCRIPTION", length = 200)
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
