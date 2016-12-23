package com.dfn.alerts.dataaccess.dao.impl.notifications;

import com.dfn.alerts.dataaccess.dao.api.FundamentalDataNotificationDAO;
import com.dfn.alerts.dataaccess.orm.impl.notifications.FundamentalDataNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.util.List;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 13:16
 */
public class FundamentalDataNotificationDAOImpl implements FundamentalDataNotificationDAO {
    private SessionFactory sessionFactory = null;
    private static final Logger LOG = LogManager.getLogger(FundamentalDataNotificationDAOImpl.class);

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {

    }

    @Override
    public int save(List<FundamentalDataNotification> notifications) {
        int status = 0;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            long start = System.currentTimeMillis();
            for (FundamentalDataNotification notification : notifications) {
                session.save(notification);
            }
            transaction.commit();
            System.out.println((System.currentTimeMillis() - start)/1000);
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
}
