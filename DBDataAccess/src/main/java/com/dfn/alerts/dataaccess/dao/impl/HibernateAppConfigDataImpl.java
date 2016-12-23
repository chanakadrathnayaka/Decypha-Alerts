package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.EmailsRecipientsDTO;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.dao.IAppConfigData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/2/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateAppConfigDataImpl implements IAppConfigData{

    private static final Logger LOG = LogManager.getLogger(HibernateAppConfigDataImpl.class);

    private SessionFactory sessionFactory;

    private static final String QUERY_EMAILS_RECIPIENTS    = "from EmailsRecipients where emailType = :emailType and recipientType = :recipientType ";

    public boolean insertSecurityFilterMetadata(SecurityFilterMetadata securityFilterMetadata) {
        boolean returnVal       = false;
        Session session         = null;
        Transaction transaction = null;
        try {
            session     = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(securityFilterMetadata);
            transaction.commit();
            returnVal = true;
        }catch (Exception e) {
            LOG.error(" Insert Security Filter Metadata " , e);
            if(transaction != null){
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }

    public boolean insertURLMetadata(URLMetadata urlMetadata) {
        boolean returnVal       = false;
        Session session         = null;
        Transaction transaction = null;
        try {
            session     = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(urlMetadata);
            transaction.commit();
            returnVal = true;
        }catch (Exception e) {
            LOG.error(" Insert URL Metadata " , e);
            if(transaction != null){
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }

    public boolean updateSecurityFilterMetadata(SecurityFilterMetadata securityFilterMetadata) {
        return false; 
    }

    public boolean deleteSecurityFilterMetadata(Long securityFilterMetadataId) {
        return false; 
    }

    public SecurityFilterMetadata getSecurityFilterMetadata(Long securityFilterMetadataId) {
        return null; 
    }

    public List<SecurityFilterMetadata> getAllSecurityFilterMetadata() {
        Session session         = null;
        List<SecurityFilterMetadata> list = null ;
        try {
            session     = this.sessionFactory.openSession();
            Query q     = session.createQuery("from SecurityFilterMetadata order by sortOrder");
            list        = q.list();
        }catch (Exception e) {
            LOG.error(" get all Security Filter Metadata " , e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public List<URLMetadata> getAllUrls() {
        Session session         = null;
        List<URLMetadata> list = null ;
        try {
            session     = this.sessionFactory.openSession();
            Query q     = session.createQuery("from URLMetadata order by displayOrder asc");
            list        = q.list();
        }catch (Exception e) {
            LOG.error(" get all Security Filter Metadata " , e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public boolean insertSecurityRole(SecurityRoles securityRoles) {
        boolean returnVal       = false;
        Session session         = null;
        Transaction transaction = null;
        try {
            session     = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(securityRoles);
            transaction.commit();
            returnVal = true;
        }catch (Exception e) {
            LOG.error(" Insert Security Role " , e);
            if(transaction != null){
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }

    public boolean updateSecurityRole(SecurityRoles securityRoles) {
        return false; 
    }

    public boolean deleteSecurityRole(Long securityRolesId) {
        return false; 
    }

    public SecurityFilterMetadata getSecurityRole(Long securityRolesId) {
        return null; 
    }

    public List<SecurityRoles> getAllSecurityRoles() {
        Session session           = null;
        List<SecurityRoles> list  = null;
        try {
            session     = this.sessionFactory.openSession();
            Query q     = session.createQuery("from SecurityRoles");
            list        = q.list();
        }catch (Exception e) {
            LOG.error(" Get all Security roles " , e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public boolean insertWidgetUsrPermission(SecurityWidgetUsrPermission widgetUsrPermission) {
        boolean returnVal       = false;
        Session session         = null;
        Transaction transaction = null;
        try {
            session     = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(widgetUsrPermission);
            transaction.commit();
            returnVal = true;
        }catch (Exception e) {
            LOG.error(" Insert widget User Permission  " , e);
            if(transaction != null){
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    public boolean updateWidgetUsrPermission(SecurityWidgetUsrPermission securityRoles) {
        return false;
    }

    public boolean deleteWidgetUsrPermission(Long securityRolesId) {
        return false;
    }

    public SecurityWidgetUsrPermission getWidgetUsrPermission(Long securityRolesId) {
        return null;
    }

    public List<SecurityWidgetUsrPermission> getAllWidgetUsrPermission() {
        Session session                        = null;
        List<SecurityWidgetUsrPermission> list  = null;
        try {
            session     = this.sessionFactory.openSession();
            Query q     = session.createQuery("from SecurityWidgetUsrPermission");
            list        = q.list();
        }catch (Exception e) {
            LOG.error(" Get all Security roles " , e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        //
    }

    @Override
    public List<EmailsRecipientsDTO> getAllEmailsRecipients() {
        return null;
    }

    /**
     *
     * @param emailType EmailType
     * @param receiptType EmailReceiptType
     * @return list of  EmailsRecipientsDTO
     */
    public List<EmailsRecipientsDTO> getEmailsRecipients(int emailType, int receiptType) {

        List<EmailsRecipients> emailsRecipientsList = null;
        Session session         = null;
        List<EmailsRecipientsDTO> emailsRecipientsDTOs = null;

        try {
            session     = this.sessionFactory.openSession();
            Query q = session.createQuery(QUERY_EMAILS_RECIPIENTS);
            q.setLong("emailType", emailType);
            q.setInteger("recipientType", receiptType);

            emailsRecipientsList = (List<EmailsRecipients>)q.list();

            if (emailsRecipientsList != null && !emailsRecipientsList.isEmpty()) {
                emailsRecipientsDTOs  = new ArrayList<EmailsRecipientsDTO>(emailsRecipientsList.size());
                for (EmailsRecipients emailsRecipients : emailsRecipientsList) {
                    emailsRecipientsDTOs.add(new EmailsRecipientsDTO(emailsRecipients.getEmailAddress() , emailsRecipients.getEmailType() , emailsRecipients.getRecipientType()));
                }
            }
        } catch (Exception ex) {
            LOG.error(" Get email list fro given email type and receiptType " , ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return emailsRecipientsDTOs;
    }

    public List<EmailsRecipientsDTO> getAllEmailsRecipients(int emailType, int receiptType) {

        List<EmailsRecipients> emailsRecipientsList = null;
        Session session         = null;
        List<EmailsRecipientsDTO> emailsRecipientsDTOs = null;

        try {
            session     = this.sessionFactory.openSession();
            Query q = session.createQuery(QUERY_EMAILS_RECIPIENTS);
            q.setLong("emailType", emailType);
            q.setInteger("recipientType", receiptType);

            emailsRecipientsList = (List<EmailsRecipients>)q.list();

            if (emailsRecipientsList != null && !emailsRecipientsList.isEmpty()) {
                emailsRecipientsDTOs  = new ArrayList<EmailsRecipientsDTO>(emailsRecipientsList.size());
                for (EmailsRecipients emailsRecipients : emailsRecipientsList) {
                    emailsRecipientsDTOs.add(new EmailsRecipientsDTO(emailsRecipients.getEmailAddress() , emailsRecipients.getEmailType() , emailsRecipients.getRecipientType()));
                }
            }
        } catch (Exception ex) {
            LOG.error(" Get email list fro given email type and receiptType " , ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return emailsRecipientsDTOs;
    }


   @Override
   public boolean insertEmailsRecipients(String email, int emailType, int receiptType){
       boolean returnVal = false;
       Session session         = null;
       Transaction transaction = null;

       try {
           EmailsRecipients emailsRecipients = new EmailsRecipients();
           emailsRecipients.setEmailAddress(email);
           emailsRecipients.setEmailType(Integer.toString(emailType));
           emailsRecipients.setRecipientType(receiptType);

           session = this.sessionFactory.openSession();
           transaction = session.beginTransaction();
           session.save(emailsRecipients);
           transaction.commit();
           returnVal = true;
       } catch (Exception e) {
           LOG.error("  insertEmailsRecipients  ", e);
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
    public boolean updateEmailsRecipients(String email, int emailType, int receiptType,long id) {
        boolean returnVal = false;
        Session session = null;
        Transaction transaction = null;
        try {

            session = this.sessionFactory.openSession();

            EmailsRecipients emailsRecipients =  (EmailsRecipients)session.load(EmailsRecipients.class,new Long(id));

            if(email != null){
                emailsRecipients.setEmailAddress(email);
            }
            emailsRecipients.setEmailType(Integer.toString(emailType));
            emailsRecipients.setRecipientType(receiptType);

            transaction = session.beginTransaction();
            session.update(emailsRecipients);
            transaction.commit();
            returnVal = true;
        } catch (Exception e) {
            LOG.error("  updateEmailsRecipients  ", e);
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
}
