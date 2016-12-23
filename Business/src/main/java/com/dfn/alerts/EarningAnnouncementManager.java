package com.dfn.alerts;

import com.dfn.alerts.api.AlertManager;
import com.dfn.alerts.dataaccess.impl.EarningsAnnouncementDataAccess;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chathurag
 * On 12/5/2016
 */
public class EarningAnnouncementManager implements AlertManager {

    private EarningsAnnouncementDataAccess earningAnnouncementDataAccess;

    public void setEarningAnnouncementDataAccess(EarningsAnnouncementDataAccess earningAnnouncementDataAccess) {
        this.earningAnnouncementDataAccess = earningAnnouncementDataAccess;
    }

    @Override
    public int saveAlerts(Object data) {

        EarningNotification announcement = new EarningNotification();
        announcement.setAlertId(5);
        announcement.setStatus(1);
        announcement.setAlertText("3 Alert");

        List<EarningNotification> earningNotifications = new ArrayList<>();
        earningNotifications.add(announcement);
        earningAnnouncementDataAccess.insertAlerts(earningNotifications);

        return 0;
    }

    @Override
    public Object searchAlerts() {
        return earningAnnouncementDataAccess.getData(null, false, false);
    }

    @Override
    public int updateCache() {
        return 0;
    }
}
