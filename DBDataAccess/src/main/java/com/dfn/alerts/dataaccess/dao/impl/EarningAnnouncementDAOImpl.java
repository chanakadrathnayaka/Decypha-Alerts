package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.dao.api.EarningAnnouncementDAO;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chathurag
 * On 12/6/2016
 */
public class EarningAnnouncementDAOImpl implements EarningAnnouncementDAO {

    private SessionFactory sessionFactory = null;
    private DriverManagerDataSource dataSource = null;

    private Logger LOG = LogManager.getLogger(EarningAnnouncementDAOImpl.class);

    private static final String UPDATE_NOTIFICATION_STATUS_QUERY =
            "update EarningAnnouncement set status = :status, updatedTime = systimestamp() where transactionId in (:transactionIds)";

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {

    }

    @Override
    public synchronized int insertEarningNotifications(List<EarningNotification> announcements) {
        int status = 0;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            for (EarningNotification notification : announcements) {
                session.save(notification);
            }
            transaction.commit();

            status = 1;

        } catch (Exception ex) {
            LOG.error(" insertEarningNotifications failed  ", ex);
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
    public int updateEarningNotifications(List<EarningNotification> newEarnings) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            if (newEarnings != null) {
                for (EarningNotification announcement : newEarnings) {
                    if (announcement != null) {
                        session.update(announcement);
                    }
                }
            }

            transaction.commit();
            status = 1;

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return 0;
    }

    @Override
    public int deleteEarningNotifications() {
        return 0;
    }

    @Override
    public List<EarningNotification> getEarningNotifications(Object searchCriteria) {
        Session session = null;
        List<EarningNotification> earningNotifications = new ArrayList<>();

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(EarningNotification.class, "earningAnn");
            List resultList = criteria.list();

            EarningNotification earningObj = null;
            if (resultList != null) {
                for (Object result : resultList) {
                    earningNotifications.add((EarningNotification) result);
                }
            }

            LOG.info(" getEarningAnnouncements success ");
        } catch (Exception ex) {
            LOG.error(" getEarningAnnouncements failed  ", ex);
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return earningNotifications;
    }
}
