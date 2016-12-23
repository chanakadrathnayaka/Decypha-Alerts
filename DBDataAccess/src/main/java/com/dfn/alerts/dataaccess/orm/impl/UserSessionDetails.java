package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 1/21/14
 * Time: 3:11 AM
 */
@Entity
@Table(name = "USR_SESSIONS")
public class UserSessionDetails implements Serializable{

    private Long userID;

    private Integer productId;

    private String username;

    private String webSessionId;

    private Date lastLoginDate;

    private Date lastLogoutDate;

    private Boolean systemLogout;

    private String lastLoginIp;

    private String lastLoginCountry;

    private Integer loginStatus;

    private Date lastUpdatedTime;

    private Map<String,String> userSessionDetails = new HashMap<String, String>(8);

    @Id
    @Column(name = "USER_ID")
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
        userSessionDetails.put("USER_ID", String.valueOf(userID));
    }

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
        userSessionDetails.put("PRODUCT_ID", String.valueOf(productId));
    }

    @Id
    @Column(name = "SESSION_ID", length = 64, nullable = false)
    public String getWebSessionId() {
        return webSessionId;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebSessionId(String webSessionId) {
        this.webSessionId = webSessionId;
        userSessionDetails.put("SESSION_ID", webSessionId);
    }

    @Column(name = "LOGIN_DATE", nullable = false)
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        userSessionDetails.put("LOGIN_DATE", String.valueOf(lastLoginDate));
    }

    @Column(name = "LOGOUT_DATE", nullable = false)
    public Date getLastLogoutDate() {
        return lastLogoutDate;
    }

    public void setLastLogoutDate(Date lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
        userSessionDetails.put("LOGOUT_DATE", String.valueOf(lastLogoutDate));
    }

    @Column(name = "SYSTEM_LOGOUT", nullable = false)
    public Boolean isSystemLogout() {
        return systemLogout;
    }

    public void setSystemLogout(Boolean systemLogout) {
        this.systemLogout = systemLogout;
    }

    @Column(name = "LOGIN_IP_PUBLIC", length = 32, nullable = false)
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        userSessionDetails.put("LOGIN_IP_PUBLIC", lastLoginIp);
    }

    @Column(name = "LOGIN_COUNTRY", length = 32, nullable = false)
    public String getLastLoginCountry() {
        return lastLoginCountry;
    }

    public void setLastLoginCountry(String lastLoginCountry) {
        this.lastLoginCountry = lastLoginCountry;
        userSessionDetails.put("LOGIN_COUNTRY", lastLoginCountry);
    }

    @Column(name = "LOGIN_STATUS")
    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
        userSessionDetails.put("LOGIN_STATUS", String.valueOf(loginStatus));
    }

    @Column(name = "LAST_REQUEST_TIME")
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        userSessionDetails.put("LAST_REQUEST_TIME", String.valueOf(lastUpdatedTime));
    }


    public Map<String, String> generateUserSessionDetails() {
        return userSessionDetails;
    }

}
