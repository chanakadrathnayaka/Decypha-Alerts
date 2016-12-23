package com.dfn.alerts.beans;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chanakar
 * Date: 10/14/13
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class CompanyProfileDTO implements Serializable {
    private String phone;
    private String web;
    private String fax;
    private String contactPerson;
    private Date estDate;
    private String email;
    private String address;

    private Map<String, String> addressDesc = null;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Date getEstDate() {
        return estDate;
    }

    public void setEstDate(Date estDate) {
        this.estDate = estDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.addressDesc = CommonUtils.getLanguageDescriptionMap(address);
    }

    public Map<String, String> getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(Map<String, String> addressDesc) {
        this.addressDesc = addressDesc;
    }
}
