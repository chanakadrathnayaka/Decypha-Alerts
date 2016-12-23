package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 2/20/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CORP_ACTIONS_TYPE_MST")
public class CorporateActionType {
    @Transient
    private static String DELIMITER_PIPE = "|";

    @Transient
    private static String DELIMITER_COLON = ":";

    private Integer corpActionType;

    private Integer corpActTypeCategory;

    private String actionNames;

    private Map<String, String> description;

    @Id
    @Column(name = "CORP_ACT_TYPE", length = 11, nullable = false)
    public Integer getCorpActionType() {
        return corpActionType;
    }

    public void setCorpActionType(Integer corpActionType) {
        this.corpActionType = corpActionType;
    }

    @Column(name = "CORP_ACT_TYPE_CATEGORY_ID", length = 6)
    public Integer getCorpActTypeCategory() {
        return corpActTypeCategory;
    }

    public void setCorpActTypeCategory(Integer corpActTypeCategory) {
        this.corpActTypeCategory = corpActTypeCategory;
    }

    @Column(name = "SHORT_DESCRIPTION", length = 100)
    public String getActionNames() {
        return actionNames;
    }

    public void setActionNames(String actionNames) {
        this.actionNames = actionNames;
        StringTokenizer values;
        StringTokenizer descriptions;
        if (actionNames != null) {
            values = new StringTokenizer(actionNames, DELIMITER_PIPE);
            this.description = new HashMap<String, String>(values.countTokens());
            while (values.hasMoreElements()) {
                descriptions = new StringTokenizer(values.nextElement().toString(), DELIMITER_COLON);
                description.put(descriptions.nextElement().toString(), descriptions.nextElement().toString());
            }
        }
    }

    @Transient
    public Map<String, String> getDescription() {
        return description;
    }
}
