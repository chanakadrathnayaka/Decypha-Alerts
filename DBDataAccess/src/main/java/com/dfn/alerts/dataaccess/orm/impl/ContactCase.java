package com.dfn.alerts.dataaccess.orm.impl;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by udaras on 8/23/2016.
 * feedback contact case hibernate mapping
 */
@Entity
@Table(name = "USR_CONTACT_CASE")
public class ContactCase {

    //-----ZOHO fields ----------
    //CASECF1 caseNum
    //Description
    //CONTACTCF4 type
    //CONTACTCF9 CurrentURL
    //Subject
    //CONTACTCF8 PreferredContactMethod
    //---------------------------

    private String userId;
    private String caseNum;
    private String subject;
    private String description;
    private String currentURL;
    private String preferredContactMethod;

    @Transient
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Id
    @GenericGenerator(name = "CUSTOM_SEQ_USR_CONTACT_CASE", strategy = "com.dfn.alerts.dataaccess.utils.ContactCaseSequenceGenerator")
    @GeneratedValue(generator = "CUSTOM_SEQ_USR_CONTACT_CASE")
    @Column(name = "CASE_NUM")
    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    @Column(name = "SUBJECT")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CURRENT_URL")
    public String getCurrentURL() {
        return currentURL;
    }

    public void setCurrentURL(String currentURL) {
        this.currentURL = currentURL;
    }

    @Column(name = "PREFERRED_CONTACT_METHOD")
    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }

    public void setPreferredContactMethod(String preferredContactMethod) {
        this.preferredContactMethod = preferredContactMethod;
    }
}
