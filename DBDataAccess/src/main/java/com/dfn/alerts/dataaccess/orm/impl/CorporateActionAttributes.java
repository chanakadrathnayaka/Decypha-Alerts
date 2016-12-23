package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chanakar
 * Date: 2015-05-27
 * Time: 18:01
 */
@Entity
@Table(name = "CORP_ACTION_ATTRIBUTES")
public class CorporateActionAttributes implements Serializable {

    private String actionAttribute;

    private String corpActionTypeList;

    private String attributeDesc;

    private IConstants.CorporateActionAttributeFormatTypes type;

    private Map<String, String> attributeDescriptions;

    @Id
    @Column(name = "ACTION_ATTRIBUTE")
    public String getActionAttribute() {
        return actionAttribute;
    }

    public void setActionAttribute(String actionAttribute) {
        this.actionAttribute = actionAttribute;
    }

    @Column(name = "CORP_ACT_TYPE_LIST")
    public String getCorpActionTypeList() {
        return corpActionTypeList;
    }

    public void setCorpActionTypeList(String corpActionTypeList) {
        this.corpActionTypeList = corpActionTypeList;
    }

    @Column(name = "ATTRIBUTE_DESCRIPTIONS")
    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
        this.attributeDescriptions = DataFormatter.GetLanguageSpecificDescriptionMap(attributeDesc);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    public IConstants.CorporateActionAttributeFormatTypes getType() {
        return type;
    }

    public void setType(IConstants.CorporateActionAttributeFormatTypes type) {
        this.type = type;
    }

    @Transient
    public Map<String, String> getAttributeDescriptions() {
        return attributeDescriptions;
    }

    public void setAttributeDescriptions(Map<String, String> attributeDescriptions) {
        this.attributeDescriptions = attributeDescriptions;
    }
}
