package com.dfn.alerts.beans.user;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nimilaa on 1/10/14.
 */
public class UserAccountExpireNotificationDTO implements Serializable {
    private final Long userID;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final Date accountExpiryDate;
    private final String email;
    private final String title;
    private final String salesRepEmail;
    private final int accountType;

    public UserAccountExpireNotificationDTO(Long userID, String username, String firstName, String lastName,
                                            Date accountExpiryDate, String email, String title , String salesRepEmail, int accountType){
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountExpiryDate = accountExpiryDate;
        this.email = email;
        this.title = title;
        this.salesRepEmail = salesRepEmail;
        this.accountType = accountType;
    }

    public Long getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
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

    public String getTitle() {
        return title;
    }

    public String getSalesRepEmail() {
        return salesRepEmail;
    }

    public int getAccountType() {
        return accountType;
    }
}
