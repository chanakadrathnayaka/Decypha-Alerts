package com.dfn.alerts.beans;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/16/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class LastLoginDetails {

    private Date lastLoginDate;

    private String lastLoginIp;

    private String lastLoginCountry;

    private String lastWebSession;


    public LastLoginDetails(Date lastLoginDate, String lastLoginIp, String lastLoginCountry, String lastWebSession){
        this.lastLoginDate = lastLoginDate;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginCountry = lastLoginCountry;
        this.lastWebSession = lastWebSession;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public String getLastLoginCountry() {
        return lastLoginCountry;
    }

    public String getLastWebSession() {
        return lastWebSession;
    }
}
