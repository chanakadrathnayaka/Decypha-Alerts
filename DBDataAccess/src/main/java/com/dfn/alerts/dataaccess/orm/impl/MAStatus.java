package com.dfn.alerts.dataaccess.orm.impl;


import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chanakar
 * Date: 10/9/13
 * Time: 4:11 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MA_STATUS")
public class MAStatus implements Serializable {
    private String statusId;
    private String desc;
    private Map<String, String> description;

    @Id
    @Column(name = "MA_STATUS_ID", length = 20)
    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
    @Column(name = "DESCRIPTION", length = 500)
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
