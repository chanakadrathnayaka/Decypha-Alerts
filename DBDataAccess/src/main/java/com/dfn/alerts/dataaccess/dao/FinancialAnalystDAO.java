package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialAnalystNotification;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialAnalystPreferences;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialNotification;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialRegionPreference;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by hasarindat on 4/3/2015.
 */
public interface FinancialAnalystDAO extends CommonDAO {

    int save(List<FinancialNotification> notifications);

    int updateNotificationStatus(List<Integer> notifications, final int status);

    List<FinancialNotification> loadNotificationsByStatus(final int status);

    int deleteNotifications(final int expiredDateCount);

    FinancialAnalystPreferences loadAnalystPreferences(int analystId);

    int updateAnalystPreferences(FinancialAnalystPreferences financialAnalystPreferences);

    int updateAnalystRegionPreference(FinancialRegionPreference financialAnalystPreference);

    List<FinancialRegionPreference> searchAnalystRegionPreferences(FinancialRegionPreference financialRegionPreference);

    int saveFinancialAnalystNotifications(List<FinancialAnalystNotification> financialAnalystNotifications);

    Map<String, List<String>> getInterestedAnalysts(Collection<Integer> companies, String language);

    List<FinancialAnalystNotification> getAnalystStats(int analystId);
}
