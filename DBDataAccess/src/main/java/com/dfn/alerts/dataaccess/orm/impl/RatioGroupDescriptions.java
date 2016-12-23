package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by udaras on 11/13/2014.
 */
@Entity
@Table(name = "RATIO_GROUP_DESCRIPTIONS")
public class RatioGroupDescriptions implements Serializable {
    private String groupId;
    private String desc;
    private Map<String, String> description;


    @Id
    @Column(name = "RATIO_GROUP_ID")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column(name = "RATIO_GROUP_DESCRIPTION")
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
