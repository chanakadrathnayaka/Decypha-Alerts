package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by udaras on 9/12/2014.
 */
@Entity
@Table(name = "PERIOD_DESCRIPTIONS")
public class PeriodDescriptions implements Serializable {
    private String periodId;
    private String desc;
    private Map<String, String> description;

    @Id
    @Column(name = "PERIOD_ID")
    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
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
