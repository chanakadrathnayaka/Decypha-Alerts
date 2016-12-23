package com.dfn.alerts.beans.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/5/14
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserSessionDetailsDTO {


    private Long userID;

    private Integer productId;

    private String webSessionId;

    private Date lastLoginDate;

    private Date lastLogoutDate;

    private Boolean systemLogout;

    private String lastLoginIp;

    private String lastLoginCountry;

    private Integer loginStatus;

    private Date lastUpdatedTime;

    private Map<String,String> userSessionDetails = new HashMap<String, String>(8);


    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getWebSessionId() {
        return webSessionId;
    }

    public void setWebSessionId(String webSessionId) {
        this.webSessionId = webSessionId;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLogoutDate() {
        return lastLogoutDate;
    }

    public void setLastLogoutDate(Date lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    public Boolean isSystemLogout() {
        return systemLogout;
    }

    public void setSystemLogout(Boolean systemLogout) {
        this.systemLogout = systemLogout;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginCountry() {
        return lastLoginCountry;
    }

    public void setLastLoginCountry(String lastLoginCountry) {
        this.lastLoginCountry = lastLoginCountry;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Map<String, String> getUserSessionDetails() {
        return userSessionDetails;
    }

    public void setUserSessionDetails(Map<String, String> userSessionDetails) {
        this.userSessionDetails = userSessionDetails;
    }
}
