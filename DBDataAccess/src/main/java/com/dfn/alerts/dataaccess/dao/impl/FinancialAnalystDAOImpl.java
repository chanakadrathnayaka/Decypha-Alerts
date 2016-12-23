package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialAnalystNotification;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialAnalystPreferences;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialNotification;
import com.dfn.alerts.dataaccess.orm.impl.financialAnalyst.FinancialRegionPreference;
import com.dfn.alerts.dataaccess.dao.FinancialAnalystDAO;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by hasarindat on 4/3/2015.
 */
public class FinancialAnalystDAOImpl implements FinancialAnalystDAO{

    private SessionFactory sessionFactory = null;

    private static final Logger LOG = LogManager.getLogger(FinancialAnalystDAOImpl.class);
    private static final String LOG_PREFIX = " :: Financial Analyst :: ";

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        //do nothing
    }

    //region notification data

    @Override
    public int save(List<FinancialNotification> notifications) {
        int status = IConstants.DBOperationStatus.INSERT_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            for (FinancialNotification notification : notifications) {
                session.save(notification);
            }
            transaction.commit();
            status = IConstants.DBOperationStatus.INSERT_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " adding notifications ", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return status;
    }

    @SuppressWarnings("JpaQlInspection")
    private static final String UPDATE_NOTIFICATION_STATUS_QUERY =
            "update FinancialNotification set status = :status, updatedTime = systimestamp() where transactionId in (:transactionIds)";

    @Override
    public int updateNotificationStatus(List<Integer> transactionIds, int status) {
        int updateStatus = IConstants.DBOperationStatus.UPDATE_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery(UPDATE_NOTIFICATION_STATUS_QUERY);
            query.setParameter("status", status);
            query.setParameterList("transactionIds", transactionIds);
            query.executeUpdate();

            transaction.commit();
            updateStatus = IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " updating notifications(" + StringUtils.join(transactionIds, ",") + ") with status : " + status, ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return updateStatus;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FinancialNotification> loadNotificationsByStatus(int status) {
        List<FinancialNotification> notifications = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialNotification.class);
            criteria.add(Restrictions.eq("status", status));
            criteria.addOrder(Order.desc("companyId"));
            criteria.addOrder(Order.desc("financialYear"));
            criteria.addOrder(Order.desc("financialQuarter"));
            notifications = criteria.list();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading notification list ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return notifications;
    }

    @SuppressWarnings("JpaQlInspection")
    private static final String DELETE_EXPIRED_QUERY =
            "delete from FinancialNotification where createdTime < sysdate() - :expiredDateCount";

    /**
     * remove expired notifications
     * does not check status COMPLETED or PENDING
     * @param expiredDateCount expire days
     * @return status
     */
    public int deleteNotifications(final int expiredDateCount){
        int updateStatus = IConstants.DBOperationStatus.UPDATE_FAIL.getDefaultValue();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery(DELETE_EXPIRED_QUERY);
            query.setParameter("expiredDateCount", expiredDateCount);
            query.executeUpdate();
            updateStatus = IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading notification list ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return updateStatus;
    }

    //endregion

    //region analyst preferences

    @Override
    public FinancialAnalystPreferences loadAnalystPreferences(int analystId){
        FinancialAnalystPreferences financialAnalystPreferences = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialAnalystPreferences.class);
            criteria.add(Restrictions.eq("individualId", analystId));
            Object analystObj = criteria.uniqueResult();
            if (analystObj != null) {
                financialAnalystPreferences = (FinancialAnalystPreferences) analystObj;
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading FinancialAnalystPreferences : " + analystId, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return financialAnalystPreferences;
    }

    @Override
    public int updateAnalystPreferences(FinancialAnalystPreferences financialAnalystPreferences) {
        int status = IConstants.DBOperationStatus.UPDATE_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(financialAnalystPreferences);
            transaction.commit();
            status = IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " updating FinancialAnalystPreferences : " + financialAnalystPreferences.getIndividualId(), ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return status;
    }

    /**
     * update/insert region preference
     *
     * @param preference region preference obj to save
     * @return insert/update status
     */
    @Override
    public int updateAnalystRegionPreference(FinancialRegionPreference preference) {
        int status = IConstants.DBOperationStatus.INSERT_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;

        try{
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(preference);
            transaction.commit();
            status = IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue();

        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " updating updateAnalystPreferencesByEmail : For email : " + preference.getEmail(), ex);
            if (transaction != null) {
                transaction.rollback();
            }
            status = IConstants.DBOperationStatus.INSERT_FAIL.getDefaultValue();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return status;
    }

    /**
     *  search region preferences for preference criteria
     *
     * @param preferenceObj criteria
     * @return list of matched preferences
     */
    @Override
    public List<FinancialRegionPreference> searchAnalystRegionPreferences(FinancialRegionPreference preferenceObj) {

        Session session = null;
        List<FinancialRegionPreference> preferenceData = new ArrayList<FinancialRegionPreference>(20);

        try{
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialRegionPreference.class, "pref");

            if(!CommonUtils.isNullOrEmptyString(preferenceObj.getEmail())){
                criteria.add(Restrictions.eq("pref.email", preferenceObj.getEmail()));
            }

            if(!CommonUtils.isNullOrEmptyString(preferenceObj.getName())){
                criteria.add(Restrictions.like("pref.name", "%" + preferenceObj.getName() + "%").ignoreCase());
            }

            if(!CommonUtils.isNullOrEmptyString(preferenceObj.getCountries())){
                criteria.add(Restrictions.like("pref.countries", "%" + preferenceObj.getCountries() + "%").ignoreCase());
            }

            if(!CommonUtils.isNullOrEmptyString(preferenceObj.getListingStatus())){
                criteria.add(Restrictions.like("pref.listingStatus", "%" + preferenceObj.getListingStatus() + "%").ignoreCase());
            }

            List analystObj = criteria.list();
            FinancialRegionPreference preference;

            if (analystObj != null) {
                for (Object o : analystObj) {
                    preference = (FinancialRegionPreference) o;

                    if (!CommonUtils.isNullOrEmptyString(preference.getCountries())) {
                        preference.setCountries(preference.getCountries().substring(1, preference.getCountries().length() - 1));
                    }
                    if (!CommonUtils.isNullOrEmptyString(preference.getListingStatus())) {
                        preference.setListingStatus(preference.getListingStatus().substring(1, preference.getListingStatus().length() - 1));
                    }
                    preferenceData.add(preference);
                }
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " updating updateAnalystPreferencesByEmail : For email : " + preferenceObj.getEmail(), ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return preferenceData;
    }

    @Override
    public int saveFinancialAnalystNotifications(List<FinancialAnalystNotification> financialAnalystNotifications) {
        int status = IConstants.DBOperationStatus.INSERT_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            for(FinancialAnalystNotification notification : financialAnalystNotifications) {
                session.save(notification);
            }
            transaction.commit();
            status = IConstants.DBOperationStatus.INSERT_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " adding FinancialAnalystNotification ", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return status;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getInterestedAnalysts(Collection<Integer> companies, String language){
        Map<String, List<String>> map = new HashMap<String, List<String>>(companies.size());
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialAnalystPreferences.class, "pref");
            criteria.createAlias("pref.companyPreferences", "comPref");
            criteria.add(Restrictions.eq("pref.notify", true));
            criteria.add(Restrictions.eq("pref.language", language));
            criteria.add(Restrictions.in("comPref.key.companyId", companies));
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("pref.individualId"));
            proList.add(Projections.property("comPref.key.companyId"));
            criteria.setProjection(proList);
            List analystObj = criteria.list();
            for(Object o : analystObj){
                Object[] row = (Object[]) o;
                String companyId = String.valueOf(row[1]);
                String analystId = String.valueOf(row[0]);
                if(!map.containsKey(companyId)){
                    map.put(companyId, new ArrayList<String>(2));
                }
                map.get(companyId).add(analystId);
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading getInterestedAnalysts : " + StringUtils.join(companies, ","), ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return map;
    }
    //endregion

    @Override
    @SuppressWarnings("unchecked")
    public List<FinancialAnalystNotification> getAnalystStats(int analystId){
        List<FinancialAnalystNotification> notifications = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialAnalystNotification.class);
            criteria.add(Restrictions.eq("individualId", analystId));
            Object analystObj = criteria.list();
            if (analystObj != null) {
                notifications = (List<FinancialAnalystNotification>) analystObj;
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading getAnalystStats : " + analystId, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return notifications;
    }
}
