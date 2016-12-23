package com.dfn.alerts.beans.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/17/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class PremiumAccountDetails implements Serializable {

    private long priceUserId = -1;
    private long accountId = -1;
    private long profileId = -1;
    private final String priceUsername;
    private Date expDate;
    private int version;

    private SalesRepDetails salesRepDetails;


    private final int status;

    private final Map<String, Boolean> subscriptionServices;

    //user details

    private CustomUserDetails userDetails;

    public PremiumAccountDetails(String priceUsername, int status, Map<String, Boolean> subscriptionServices) {
        this.priceUsername = priceUsername;
        this.status = status;
        this.subscriptionServices = subscriptionServices;
    }

    public PremiumAccountDetails(long priceUserId, long accountId, long profileId, String priceUsername, int status,
                                 Map<String, Boolean> subscriptionServices, Date expDate, int version) {
        this.priceUsername = priceUsername;
        this.priceUserId = priceUserId;
        this.profileId = profileId;
        this.accountId = accountId;
        this.status = status;
        this.subscriptionServices = subscriptionServices;
        this.expDate = expDate;
        this.version = version;
    }

    public String getPriceUsername() {
        return priceUsername;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, Boolean> getSubscriptionServices() {
        return subscriptionServices;
    }

    public long getPriceUserId() {
        return priceUserId;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getProfileId() {
        return profileId;
    }

    public boolean isThisServiceAvailable(String serviceCode) {
        Boolean isServiceAvailable = subscriptionServices.get(serviceCode);
        return isServiceAvailable != null && isServiceAvailable;
    }

    public Date getExpDate() {
        return expDate;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CustomUserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(CustomUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public SalesRepDetails getSalesRepDetails() {
        return salesRepDetails;
    }

    public void setSalesRepDetails(SalesRepDetails salesRepDetails) {
        this.salesRepDetails = salesRepDetails;
    }
}
