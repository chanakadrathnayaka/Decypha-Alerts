package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 2/19/14
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "NEWS_SOURCES")
public class NewsSource implements Serializable {
    private String sourceId;

    private String descLan;

    @Transient
    private Map<String, String> description = new HashMap<String, String>(2);

    @Id
    @Column(name = "SOURCE_ID",  nullable = false)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "DESCRIPTION")
    public String getDescLan() {
        return descLan;
    }

    public void setDescLan(String descLan) {
        this.descLan = descLan;
        this.description = DataFormatter.GetLanguageSpecificDescriptionMap(descLan);
    }

    @Transient
    public Map<String, String> getDescription() {
        return description;
    }
}
