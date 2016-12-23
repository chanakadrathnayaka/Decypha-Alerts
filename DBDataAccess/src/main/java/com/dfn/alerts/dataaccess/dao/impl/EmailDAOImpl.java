package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.notification.EmailDTO;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.dao.api.EmailDAO;
import com.dfn.alerts.dataaccess.orm.impl.notification.EmailContent;
import com.dfn.alerts.dataaccess.orm.impl.notification.EmailMetaData;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasarindat on 4/4/2015.
 */
public class EmailDAOImpl implements EmailDAO {

    private SessionFactory sessionFactory;

    private static final Logger LOG = LogManager.getLogger(EmailDAOImpl.class);
    private static final String LOG_PREFIX = " :: EmailDAOImpl :: ";

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int save(EmailDTO emailMetaData) {
        int emailId = -1;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            EmailMetaData e = convertToHibernateObj(emailMetaData, session);
            session.save(e);
            transaction.commit();
            emailId = e.getId();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " saving email : " + emailMetaData, ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return emailId;
    }

    @Override
    public Map<Object, Integer> save(Map<Object, EmailDTO> emails) {
        Session session = null;
        Transaction transaction = null;

        Map<Object, Integer> returnMap = new HashMap<Object, Integer>(emails.size());

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            for (Object id : emails.keySet()) {
                EmailMetaData email = convertToHibernateObj(emails.get(id), session);
                session.save(email);
                returnMap.put(id, email.getId());
            }
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " saving email multiple ", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnMap;
    }

    @SuppressWarnings("JpaQlInspection")
    private static final String UPDATE_NOTIFICATION_SUCCESS_QUERY =
            "update EmailMetaData set status = :status, notificationTime =:notificationTime, " +
                    "updatedTime = systimestamp() where id =:id";

    @SuppressWarnings("JpaQlInspection")
    private static final String UPDATE_NOTIFICATION_FAILURE_QUERY =
            "update EmailMetaData set status = :status, notificationTime =:notificationTime, " +
                    "retryCount = retryCount + 1, " +
                    "updatedTime = systimestamp() where id =:id";

    @Override
    public int update(int emailId, Timestamp notificationTime, int status) {
        int updateStatus = IConstants.DBOperationStatus.UPDATE_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query;
            if (status == 1) {
                query = session.createQuery(UPDATE_NOTIFICATION_SUCCESS_QUERY);
            } else {
                query = session.createQuery(UPDATE_NOTIFICATION_FAILURE_QUERY);
            }
            query.setParameter("id", emailId);
            query.setParameter("status", status);
            query.setParameter("notificationTime", notificationTime);
            query.executeUpdate();

            transaction.commit();
            updateStatus = IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " updating email : " + emailId + " with status : " + status, ex);
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
    public List<EmailDTO> get(Map<String, Object> params) {
        List<EmailDTO> emails = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(EmailMetaData.class);
            for (String filter : params.keySet()) {
                criteria.add(Restrictions.eq(filter, params.get(filter)));
            }
            emails = convertToDTO(criteria.list());
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading notification list ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return emails;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EmailDTO> getPendingEmails(int status, int retryCount) {
        List<EmailDTO> emails = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(EmailMetaData.class);
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.le("retryCount", retryCount));
            emails = convertToDTO(criteria.list());
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading pending emails list ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return emails;
    }

    private static final String SQL_REMOVE_EMAILS_FROM_HISTORY =
            "DELETE FROM EMAIL_META_DATA_HISTORY WHERE LAST_UPDATED_TIME < SYSDATE-30";

    private static final String SQL_REMOVE_EMAILS_FROM_HISTORY_CONTENT =
            "delete from email_content_history e where e.email_id in (" +
                    "select email_id from email_meta_data_history where LAST_UPDATED_TIME<SYSDATE-30" +
                    ")";

    @Override
    public void deleteHistoryData() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query q = session.createSQLQuery(SQL_REMOVE_EMAILS_FROM_HISTORY_CONTENT);
            q.executeUpdate();
            q = session.createSQLQuery(SQL_REMOVE_EMAILS_FROM_HISTORY);
            q.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(" delete email history ", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static final String SQL_ARCHIVE_EMAILS = "INSERT INTO EMAIL_META_DATA_HISTORY(" +
            "EMAIL_ID, EMAIL_FROM, EMAIL_TO, EMAIL_BCC, EMAIL_CC, NOTIFICATION_TIME, " +
            "STATUS, LAST_UPDATED_TIME, RETRY_COUNT, EMAIL_TYPE, SOURCE_ID" +
            ") " +
            "SELECT EMAIL_ID, EMAIL_FROM, EMAIL_TO, EMAIL_BCC, EMAIL_CC, NOTIFICATION_TIME, " +
            "STATUS, LAST_UPDATED_TIME, RETRY_COUNT, EMAIL_TYPE, SOURCE_ID " +
            "FROM EMAIL_META_DATA WHERE STATUS = 1";

    private static final String SQL_ARCHIVE_EMAIL_CONTENT = "INSERT INTO EMAIL_CONTENT_HISTORY(" +
            "EMAIL_ID, LANGUAGE_ID, EMAIL_FROM_NAME, EMAIL_TITLE, EMAIL_BODY, IS_LOAD_ATTACHMENT, " +
            "ATTACHED_LOCATION" +
            ") " +
            "SELECT c.EMAIL_ID, c.LANGUAGE_ID, c.EMAIL_FROM_NAME, c.EMAIL_TITLE, c.EMAIL_BODY, c.IS_LOAD_ATTACHMENT, " +
            "c.ATTACHED_LOCATION " +
            "FROM EMAIL_CONTENT c JOIN EMAIL_META_DATA m ON (m.EMAIL_ID = c.EMAIL_ID AND m.STATUS = 1)";

    private static final String SQL_REMOVE_EMAILS = "DELETE FROM EMAIL_META_DATA WHERE STATUS = 1";

    private static final String SQL_REMOVE_EMAIL_CONTENT =
            "delete from email_content c where c.email_id in (" +
                    "select email_id from email_meta_data where status = 1" +
                    ")";

    @Override
    public int archive(int days) {
        int returnVal = IConstants.DBOperationStatus.INSERT_FAIL.getDefaultValue();
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query q = session.createSQLQuery(SQL_ARCHIVE_EMAILS);
            q.executeUpdate();
            q = session.createSQLQuery(SQL_ARCHIVE_EMAIL_CONTENT);
            q.executeUpdate();
            q = session.createSQLQuery(SQL_REMOVE_EMAIL_CONTENT);
            q.executeUpdate();
            q = session.createSQLQuery(SQL_REMOVE_EMAILS);
            q.executeUpdate();
            transaction.commit();
            returnVal = IConstants.DBOperationStatus.INSERT_SUCCESS.getDefaultValue();
        } catch (Exception ex) {
            LOG.error(" insert emails to meta history", ex);
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


    /**
     * convert DTO to hibernate obj
     *
     * @param email DTO
     * @return hibernate obj
     */
    private EmailMetaData convertToHibernateObj(EmailDTO email, Session session) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        EmailMetaData emailMetaData = new EmailMetaData();
        emailMetaData.setUpdatedTime(now);
        emailMetaData.setBcc(email.getBcc());
        emailMetaData.setCc(email.getCc());
        emailMetaData.setTo(email.getTo());
        emailMetaData.setFrom(email.getFrom());
        emailMetaData.setNotificationTime(now);
        emailMetaData.setStatus(IConstants.DBStatus.PENDING.getDefaultValue());
        emailMetaData.setRetryCount(0);
        emailMetaData.setSource(email.getSource());
        if (email.getType() != null) {
            emailMetaData.setType(email.getType().getCode());
        }

        EmailContent emailContent = new EmailContent();
        emailContent.setAttachment(email.isAttachment());
        emailContent.setAttachmentLocation(email.getAttachmentLocation());
        emailContent.setAttachmentName(email.getAttachmentName());
        emailContent.setBody(DBUtils.setClob(email.getBody(), session));
        emailContent.setFromName(email.getFromName());
        emailContent.setLanguage(email.getLanguage());
        emailContent.setSubject(email.getSubject());

        emailMetaData.setEmailContent(emailContent);
        emailContent.setEmailMetaData(emailMetaData);

        return emailMetaData;
    }

    /**
     * hibernate obj to DTO
     *
     * @param email hibernate objects
     * @return DTO list
     */
    private List<EmailDTO> convertToDTO(List email) {
        List<EmailDTO> data = null;
        if (email != null && !email.isEmpty()) {
            data = new ArrayList<EmailDTO>(email.size());
            for (Object emailMetaData : email) {
                EmailDTO emailDTO = convertToDTO((EmailMetaData) emailMetaData);
                if (emailDTO != null) {
                    data.add(emailDTO);
                }
            }
        }
        return data;
    }

    /**
     * hibernate obj list to DTO
     *
     * @param email EmailMetaData
     * @return DTO
     */
    private EmailDTO convertToDTO(EmailMetaData email) {
        if (email.getEmailContent() == null) {
            return null;
        } else {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setSubject(email.getEmailContent().getSubject());
            emailDTO.setLanguage(email.getEmailContent().getLanguage());
            emailDTO.setFromName(email.getEmailContent().getFromName());
            emailDTO.setFrom(email.getFrom());
            emailDTO.setAttachment(email.getEmailContent().isAttachment());
            emailDTO.setAttachmentLocation(email.getEmailContent().getAttachmentLocation());
            emailDTO.setAttachmentName(email.getEmailContent().getAttachmentName());
            emailDTO.setBcc(email.getBcc());
            emailDTO.setCc(email.getCc());
            emailDTO.setBody(DBUtils.readClob(email.getEmailContent().getBody()));
            emailDTO.setId(email.getId());
            emailDTO.setSource(email.getSource());
            emailDTO.setStatus(email.getStatus());
            emailDTO.setTo(email.getTo());
            emailDTO.setType(IConstants.EmailType.valueOf(email.getType()));
            return emailDTO;
        }
    }


}
