package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.orm.impl.SMSMessage;
import com.dfn.alerts.dataaccess.dao.SmsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Danuran on 3/11/2015.
 */
public class SmsDAOImpl implements SmsDAO{
    public static final int STATUS_FAILED = 0;
    public static final int STATUS_SUCCESS = 1;
    private SessionFactory sessionFactory = null;
    private static final String QUERY_WELCOME_SMS_MESSAGES = "from SMSMessage where status=0 and notifyStatus=1 and retryCount<10";
    private static final String QUERY_UPDATE_WELCOME_SMS = "UPDATE SMSMessage SET status = :status, sms_sent_time = :smsSentTime, last_updated_time=:lastUpdatedTime, retry_count = :retryCount WHERE  sms_id= :smsId";
    private static final String QUERY_UPDATE_WELCOME_SMS_RETRY = "UPDATE SMSMessage SET status = :status, last_updated_time=:lastUpdatedTime, retry_count = :retryCount WHERE  sms_id= :smsId";
    private static final Logger LOG = LogManager.getLogger(SmsDAOImpl.class);
    @Override
    public int updateSMSRecord(int smsId, int status, int retryCount) {
        int returnVal = STATUS_FAILED;
        Query q = null;
        LOG.debug(" updating sms message ");
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (status == 1) {
                q = session.createQuery(QUERY_UPDATE_WELCOME_SMS);
                q.setTimestamp("smsSentTime", new Timestamp(System.currentTimeMillis()));
            } else {
                q = session.createQuery(QUERY_UPDATE_WELCOME_SMS_RETRY);
            }
            q.setInteger("smsId", smsId);
            q.setInteger("status", status);
            q.setTimestamp("lastUpdatedTime", new Timestamp(System.currentTimeMillis()));
            q.setInteger("retryCount", retryCount);
            int updateCount = q.executeUpdate();
            transaction.commit();
            if(updateCount == 1){
                returnVal =STATUS_SUCCESS;
            }
        } catch (Exception e) {
            LOG.error("updating sms notification ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    @Override
    public int insertSms(SMSMessage smsMessage) {
            int returnVal = STATUS_FAILED;
            LOG.debug(" creating sms notification ");
            Session session = null;
            Transaction transaction = null;
            try {
                session = this.sessionFactory.openSession();
                transaction = session.beginTransaction();
                session.save(smsMessage);
                transaction.commit();
                returnVal = STATUS_SUCCESS;
            } catch (Exception e) {
                LOG.error("  creating sms notification ", e);
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            return returnVal;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {

    }
    @Override
    public List<SMSMessage> getSmsFromDB() {
        Session session         = null;
        List<SMSMessage> smsMessages = null;
        try {
            session     = this.sessionFactory.openSession();
            Query q = session.createQuery(QUERY_WELCOME_SMS_MESSAGES);
            smsMessages=q.list();
        } catch (Exception ex) {
            LOG.error(" get sms from db", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smsMessages;
    }
}
