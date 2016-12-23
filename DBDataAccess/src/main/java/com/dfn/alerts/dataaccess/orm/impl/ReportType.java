package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 10/21/13
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "REPORT_TYPE_DESC")
public class ReportType implements Serializable {
    private String reportTypeId;
    private String desc;
    private Map<String, String> description;

    @Id
    @Column(name = "REPORT_TYPE_ID")
    public String getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(String reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    @Column(name = "DESCRIPTION" )
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
