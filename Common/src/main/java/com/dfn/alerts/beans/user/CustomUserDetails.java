package com.dfn.alerts.beans.user;

import com.dfn.alerts.constants.UserDetailsConstants;
import com.dfn.alerts.constants.UserManagementConstants;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


/**
 * Basic user details object extension of {@link org.springframework.security.core.userdetails.User}
 *
 * Created with IntelliJ IDEA.
 * User: aravindal  , lasanthak
 * Date: 7/11/13
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User implements Serializable {

    private static int PREMIUM_USER_WEIGHT    = UserManagementConstants.UserRoles.ROLE_PREMIUM_USER.weight();

    /*Basic user Props*/

    private final long userId;

    private String email;

    private String userRoles;

    private int accountType;

    private Integer accountStatus;

    private String title;

    private String firstName;

    private String lastName;

    private String phone;

    private String workTel;

    private String country;

    private String company;

    private Date accountCreationDate;

    private Date accountExpiryDate;

    private Date passwordChangedDate;

    private Date passwordExpiryDate;

    private Date lastLoginDate;

    private String lastLoginIp;

    private String lastLoginCountry;

    private Integer noOfFailedAttempts;

    private String lastWebSession;

    private Integer loginStatus;


   /* user role weight*/
    private int maxUserRoleWeight;

    /* current log in details do not persist*/

    private String loginIp;

    /* current log in details do not persist*/
    private String loginCountry;

    private String loginCountryCode;

    private PremiumAccountDetails premiumAccountDetails;

    private UserPreferenceDetails userPreferenceDetails;

    private int userVersion;

    private String designation;

    private String city;

    private String address1;

    private String address2;

    private Integer lastUpdatedBy;

    /**
     *
     * @param username username
     * @param password password
     * @param userId   user id
     * @param authorities authorities
     * @param noOfFailedAttempts no of failed attempts
     * @param passwordExpiryDate password expiry date
     * @param status user status : first time user, locked user etc
     * @param maxUserRoleWeight user level
     */
    public CustomUserDetails(
            String username,
            String password,
            long userId ,
            Collection<? extends GrantedAuthority> authorities,
            Integer noOfFailedAttempts,
            Date passwordExpiryDate,
            int status,
            int maxUserRoleWeight) {
        super(username, password, true, true, true, true, authorities);
        this.userId = userId;
        this.noOfFailedAttempts = noOfFailedAttempts;
        this.passwordExpiryDate = passwordExpiryDate;
        this.accountStatus = status;
        this.maxUserRoleWeight = maxUserRoleWeight;
    }

    /**
     *
     * @param username username
     * @param password password
     * @param userId   user id
     * @param enabled  is user enabled
     * @param accountNonExpired  account non expired
     * @param credentialsNonExpired  credentials Non Expired
     * @param accountNonLocked    account Non Locked
     * @param authorities      authorities
     * @param noOfFailedAttempts no of failed attempts
     * @param passwordExpiryDate password expiry date
     * @param status user status : first time user, locked user etc
     * @param maxUserRoleWeight user level

     */
    public CustomUserDetails(
            String username,
            String password,
            long userId ,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities ,
            Integer noOfFailedAttempts,
            Date passwordExpiryDate,
            int status,
            int  maxUserRoleWeight)  {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.noOfFailedAttempts = noOfFailedAttempts;
        this.passwordExpiryDate = passwordExpiryDate;
        this.accountStatus = status;
        this.maxUserRoleWeight = maxUserRoleWeight;
    }

    public boolean isPremiumUser(){
        return this.maxUserRoleWeight >= PREMIUM_USER_WEIGHT;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public Date getAccountExpiryDate() {
        return accountExpiryDate;
    }

    public void setAccountExpiryDate(Date accountExpiryDate) {
        this.accountExpiryDate = accountExpiryDate;
    }

    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }

    public Date getPasswordExpiryDate() {
        return passwordExpiryDate;
    }

    public void setPasswordExpiryDate(Date passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    public String getLoginCountryCode() {
        return loginCountryCode;
    }

    public void setLoginCountryCode(String loginCountryCode) {
        this.loginCountryCode = loginCountryCode;
    }

    public void setLastLoginCountry(String lastLoginCountry) {
        this.lastLoginCountry = lastLoginCountry;
    }

    public Integer getNoOfFailedAttempts() {
        return noOfFailedAttempts;
    }

    public void setNoOfFailedAttempts(Integer noOfFailedAttempts) {
        this.noOfFailedAttempts = noOfFailedAttempts;
    }

    public String getLastWebSession() {
        return lastWebSession;
    }

    public void setLastWebSession(String lastWebSession) {
        this.lastWebSession = lastWebSession;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getMaxUserRoleWeight() {
        return maxUserRoleWeight;
    }

    public void setMaxUserRoleWeight(int maxUserRoleWeight) {
        this.maxUserRoleWeight = maxUserRoleWeight;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry;
    }

    public PremiumAccountDetails getPremiumAccountDetails() {
        return premiumAccountDetails;
    }

    public void setPremiumAccountDetails(PremiumAccountDetails premiumAccountDetails) {
        this.premiumAccountDetails = premiumAccountDetails;
    }
    
    public UserPreferenceDetails getUserPreferenceDetails() {
        if(userPreferenceDetails == null){
            //Create dummy user details object
            userPreferenceDetails = new UserPreferenceDetails(this.userId , true);
        }
        return userPreferenceDetails;
    }

    public void setUserPreferenceDetails(UserPreferenceDetails userPreferenceDetails) {
        this.userPreferenceDetails = userPreferenceDetails;
    }

    public boolean isEnabled() {
        return true;
    }

    //todo : logic correct???
    public boolean isAccountEnabled() {
        boolean enabled = false;
        switch (accountStatus){
            case UserDetailsConstants.UserStatus.USER_STATUS_ACTIVE:
            case UserDetailsConstants.UserStatus.USER_STATUS_ACTIVE_FIRST_TIME:
            case UserDetailsConstants.UserStatus.USER_STATUS_SUBSCRIPTION_FAILURE:
                enabled = true;
                break;
            default:
                break;
        }
        return enabled;
    }


    public boolean isEmailVerificationRequired() {
        return accountStatus == UserDetailsConstants.UserStatus.USER_STATUS_WAITING_FOR_CONFIRMATION;
    }


    public boolean isPendingApprovalRequired() {
        return accountStatus == UserDetailsConstants.UserStatus.USER_STATUS_PENDING_APPROVAL;
    }


    public boolean isAccountNonExpired() {
        return accountExpiryDate.after(new Date());
    }

    public boolean isAccountNonLocked() {
        return accountStatus != UserDetailsConstants.UserStatus.USER_STATUS_LOCKED;
    }

    public boolean isCredentialsNonExpired() {
        return accountStatus != UserDetailsConstants.UserStatus.USER_STATUS_PASSWORD_EXPIRED;
    }

    public int getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(int userVersion) {
        this.userVersion = userVersion;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
