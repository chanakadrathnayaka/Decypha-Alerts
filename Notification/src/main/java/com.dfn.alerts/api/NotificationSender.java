package com.dfn.alerts.api;

import com.dfn.alerts.beans.SchedulerJobResult;

/**
 * Created by Danuran on 4/2/2015.
 */
public interface NotificationSender {
    int NOTIFICATION_SEND_SUCCESS = 1;
    int NOTIFICATION_SEND_ERROR = 0;

    SchedulerJobResult sendNotification();

    int sendNotification(Object notificationDetails);

    void archiveNotification();
}
