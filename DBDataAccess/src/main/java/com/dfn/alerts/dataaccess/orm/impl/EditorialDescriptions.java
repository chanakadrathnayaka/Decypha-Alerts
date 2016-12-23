package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by udaras on 6/4/2015.
 */
@Entity
@Table(name = "NEWS_EDITORIAL_CODE_DESC")
public class EditorialDescriptions implements Serializable{
    private String editorialCode;
    private String desc;
    private Map<String, String> description;

    @Id
    @Column(name = "EDITORIAL_CODE")
    public String getEditorialCode() {
        return editorialCode;
    }

    public void setEditorialCode(String editorialCode) {
        this.editorialCode = editorialCode;
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
