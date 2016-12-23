package com.dfn.alerts.dataaccess.orm.impl;

/**
 * Created with IntelliJ IDEA.
 * User: Pubuduj
 * Date: 12/26/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SALES_REPRESENTATIVE")
public class SalesRepresentative implements Serializable {

    private int salesRepId;
    private String firstName;
    private String lastName;
    private String designation;
    private String email;
    private String mobileNumber;
    private String plusAccountId;

    @Id
    @Column(name = "REP_ID")
    public int getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(int salesRepId) {
        this.salesRepId = salesRepId;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name= "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getName() {
        return firstName + " " + lastName;
    }

    @Column(name = "DESIGNATION")
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "MOBILE_NUMBER")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(name = "PLUS_ACCOUNT_ID")
    public String getPlusAccountId() {
        return plusAccountId;
    }

    public void setPlusAccountId(String plusAccountId) {
        this.plusAccountId = plusAccountId;
    }





}
