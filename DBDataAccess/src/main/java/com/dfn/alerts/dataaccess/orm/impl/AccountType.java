package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Pubuduj
 * Date: 12/26/13
 * Time: 7:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USR_ACCOUNT_TYPE")
public class AccountType implements Serializable {


    private String accountTypeId;
    private String accountType;
    private Map<String, String> description;


    @Id
    @Column(name = "ACCOUNT_TYPE_ID")
    public String getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(String accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    @Column(name = "ACCOUNT_TYPE_VALUE")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        this.description = DataFormatter.GetLanguageSpecificDescriptionMap(accountType);
    }

    @Transient
    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

}
