package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.dataaccess.orm.impl.notifications.FundamentalDataNotification;

import java.util.List;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 13:15
 */
public interface FundamentalDataNotificationDAO extends CommonDAO{

    int save(List<FundamentalDataNotification> notifications);
}
