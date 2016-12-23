package com.dfn.alerts.impl;

import com.dfn.alerts.api.AlertManager;
import com.dfn.alerts.api.IBusinessManager;
import com.dfn.alerts.dataaccess.beans.ContributionItem;
import com.dfn.alerts.dataaccess.beans.DataNotification;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.impl.FundamentalDataNotificationDataAccess;
import com.dfn.alerts.dataaccess.orm.impl.notifications.FundamentalDataNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 10:58
 */
public class FundamentalDataNotificationManager implements IBusinessManager, AlertManager {

    private FundamentalDataNotificationDataAccess fundamentalDataNotificationDataAccess;

    public void setFundamentalDataNotificationDataAccess(FundamentalDataNotificationDataAccess fundamentalDataNotificationDataAccess) {
        this.fundamentalDataNotificationDataAccess = fundamentalDataNotificationDataAccess;
    }

    @Override
    public int saveAlerts(Object data) {
        Map<String, String> requestData = (Map<String, String>) data;
        ResponseObj response = (ResponseObj) getData(requestData, true);

        return fundamentalDataNotificationDataAccess.saveAlerts(getFundamentalDataNotifications(response));
    }

    @Override
    public Object searchAlerts() {
        return null;
    }

    @Override
    public int updateCache() {
        return 0;
    }

    @Override
    public Object getData(Object requestData, boolean isJasonResponse) {
        return this.fundamentalDataNotificationDataAccess.getData((Map<String, String>) requestData, true, isJasonResponse);
    }

    private List<FundamentalDataNotification> getFundamentalDataNotifications(ResponseObj response) {
        DataNotification[] dataNotifications = response.getFDN();
        List<FundamentalDataNotification> processedNotifications = null;
        if (dataNotifications != null && dataNotifications.length > 0) {
            processedNotifications = new ArrayList<>();
            for (DataNotification rawNotification : dataNotifications) {
                for (ContributionItem key: rawNotification.getCL()) {
                    FundamentalDataNotification notification = new FundamentalDataNotification();
                    notification.setTransactionId(rawNotification.getTSID());
                    notification.setOperationType(rawNotification.getOT());
                    notification.setInformationType(rawNotification.getITID());
                    notification.setContributionItem(key.toString());
                    processedNotifications.add(notification);
                }
            }
        }
        return processedNotifications;
    }
}
