package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.dataaccess.dao.CommonDAO;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;

import java.util.List;

/**
 * Created by chathurag
 * On 12/6/2016
 */
public interface EarningAnnouncementDAO extends CommonDAO {

    public int insertEarningNotifications(List<EarningNotification> announcements);

    public int updateEarningNotifications(List<EarningNotification> newEarnings);

    public int deleteEarningNotifications();

    public List<EarningNotification> getEarningNotifications(Object searchCriteria);
}
