package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 5/15/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SUKUK_TYPES")
public class SukukTypes implements Serializable{
    private Integer typeId;
    private String shortDescLan;
    private Map<String,String> shortDescription;

    @Id
    @Column( name = "SUKUK_TYPE_ID", length=5)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Id
    @Column( name = "SHORT_DESCRIPTION", length=300)
    public String getShortDescLan() {
        return shortDescLan;
    }

    public void setShortDescLan(String shortDescLan) {
        this.shortDescLan = shortDescLan;
        this.shortDescription = DataFormatter.GetLanguageSpecificDescriptionMap(shortDescLan);
    }

    @Transient
    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Map<String, String> shortDescription) {
        this.shortDescription = shortDescription;
    }
}
