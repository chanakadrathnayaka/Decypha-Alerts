package com.dfn.alerts.beans.user;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by aravindal on 1/23/14.
 */
public class SessionUser implements Serializable {

    private final long userId;
    private final boolean isPriceUser;
    private final String userName;
    private final String lastWebSession;
    private final String loginIp;
    private final String loginCountry;
    private Date lastLoginDate;

    public SessionUser(final long userId, boolean isPriceUser, String userName, final String lastWebSession, final String loginIp, final String loginCountry, final Date lastLoginDate){
        this.userId = userId;
        this.isPriceUser = isPriceUser;
        this.userName = userName;
        this.lastWebSession = lastWebSession;
        this.loginIp = loginIp;
        this.loginCountry = loginCountry;
        this.lastLoginDate = lastLoginDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastWebSession() {
        return lastWebSession;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isPriceUser() {
        return isPriceUser;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }
}
