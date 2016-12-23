package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.beans.notification.EmailDTO;
import org.hibernate.SessionFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by hasarindat on 4/4/2015.
 */
public interface EmailDAO {

    /**
     * set hibernate session factory
     * @param sessionFactory hibernate
     */
    void setSessionFactory(SessionFactory sessionFactory);

    /**
     * save email
     * @param emailMetaData hibernate object
     * @return email id
     */
    int save(EmailDTO emailMetaData);

    /**
     * save emails
     * @param emails key vs email to insert
     * @return key vs email id of inserted
     */
    Map<Object, Integer> save(Map<Object, EmailDTO> emails);

    /**
     * update email notification status
     * @param emailId id
     * @param notificationTime notification time
     * @param status success(1) or fail(-1)
     * @return update status
     */
    int update(int emailId, Timestamp notificationTime, int status);

    /**
     * get emails by filter - must be equal matches eg: email_to = 'hasarinda86@gmail.com'
     * @param params filters property name of hibernate obj vs filter value for property
     * @return email list
     */
    List<EmailDTO> get(Map<String, Object> params);

    /**
     * archive old success emails
     * @param days expire days
     * @return IConstants.DBOperationStatus.INSERT_SUCCESS or INSERT_FAIL
     */
    int archive(int days);

    /*
    * get all pending emails
    * @param status pending
    * @param retryCount retry count 10
    * @return pending emails
    */
    List<EmailDTO> getPendingEmails(int status, int retryCount);

    /**
     * remove records from email history tables - delete all emails where max(archive_period)>30
     */
    public void deleteHistoryData();

}
