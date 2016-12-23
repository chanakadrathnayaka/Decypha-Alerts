package com.dfn.alerts.beans.user;

import java.util.Date;

/**
 * Created by Lasanthak on 4/7/2015.
 */
public class MiniUserDetails {

    private final long userId;

    private  int accountType;

    private  Integer accountStatus;

    private  String title;

    private  String firstName;

    private  String lastName;

    private  Date accountExpiryDate;

    private  String email;

    private int emailReleaseNotes;

    private String publicKey;

    public MiniUserDetails(final long userId){
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountExpiryDate(Date accountExpiryDate) {
        this.accountExpiryDate = accountExpiryDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccountType() {
        return accountType;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getAccountExpiryDate() {
        return accountExpiryDate;
    }

    public String getEmail() {
        return email;
    }

    public int getEmailReleaseNotes() {
        return emailReleaseNotes;
    }

    public void setEmailReleaseNotes(int emailReleaseNotes) {
        this.emailReleaseNotes = emailReleaseNotes;
    }

    public boolean isAccountNonExpired() {
        return accountExpiryDate.after(new Date());
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
