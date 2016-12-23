package com.dfn.alerts.beans.user;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/5/14
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class SalesRepDetails implements Serializable {

    private final int salesRepId;
    private  String firstName;
    private  String lastName;
    private  String designation;
    private final String email;
    private  String mobileNumber;
    private  String plusAccountId;

    public SalesRepDetails(int salesRepId , String email){
        this.salesRepId = salesRepId;
        this.email      = email;
    }


    public int getSalesRepId() {
        return salesRepId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPlusAccountId() {
        return plusAccountId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPlusAccountId(String plusAccountId) {
        this.plusAccountId = plusAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        SalesRepDetails that = (SalesRepDetails) o;

        if (salesRepId != that.salesRepId){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return salesRepId;
    }
}
