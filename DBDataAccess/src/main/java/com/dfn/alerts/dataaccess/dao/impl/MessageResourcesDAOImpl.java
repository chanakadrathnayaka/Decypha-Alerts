package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.dataaccess.orm.impl.MessageResourceBean;
import com.dfn.alerts.dataaccess.dao.MessageResourcesDAO;
import com.dfn.alerts.dataaccess.orm.impl.earnings.ApplicationSetting;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.UncategorizedSQLException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Dao class to load message resources
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/10/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageResourcesDAOImpl implements MessageResourcesDAO {

    private SessionFactory sessionFactory = null;


    /**
     * Load all messages from database
     * @return all messages
     */
    public List<MessageResourceBean>  getAllMessages(){
        List<MessageResourceBean> list = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from MessageResourceBean");
            list   =  q.list();

        } finally {
            if(session != null){
                session.close();
            }
        }

        return list;
    }

    /**
     * get all resources
     * @param basenames  base name
     * @return   related resources
     */
    public Collection getAllResources(String[] basenames){
        List resources = new ArrayList();

        for (String base : basenames) {
            if (base.equals(MessageResourceBean.TYPE)) {
                resources.addAll(getAllMessages());
            } else if (base.equals(ApplicationSetting.TYPE)) {
                resources.addAll(getAllSettings());
            }
        }
        return resources;
    }

    /**
     * Method to get all settings
     * @return all settings
     */
    public List<ApplicationSetting> getAllSettings(){

        List<ApplicationSetting> list = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from ApplicationSetting");
            list   =  q.list();

        } finally {
            if(session != null){
                session.close();
            }
        }

        return list;
    }


    public List<MessageResourceBean> getMessages(String locale){
        return null;
    }

    /**
     * insert message resources object to database
     * @param messageResourceBean object to be stored in db
     * @return status
     */
    public int insertMessage(MessageResourceBean messageResourceBean) {
        int returnVal = DBConstants.InsertStatus.INSERT_FAILED;

        if(isAlreadyExists(messageResourceBean)){
            return DBConstants.InsertStatus.INSERT_OBJECT_ALREADY_EXISTS;
        }

        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            //temporary solutions TODO
            String sql = "INSERT INTO INFO_MESSAGES VALUES MESSAGES_SEQ.nextval,"
                    + messageResourceBean.getLocale() +"'  ,'" + messageResourceBean.getResourceKey() + "','" + messageResourceBean.getValue() + "')";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
            returnVal = DBConstants.InsertStatus.INSERT_SUCCESS;

        } finally {
            if(session != null){
                session.close();
            }
        }

        return returnVal;

    }

    /**
     * check if key and local is already available or not
     * @param messageResourceBean resources object
     * @return true if already avaliable
     */
    private boolean isAlreadyExists(MessageResourceBean messageResourceBean) {

        Session session = null;
        List<MessageResourceBean> messageResourceBeans = null;

        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from MessageResourceBean where resourceKey = :resourceKey and locale = :locale");
            query.setParameter("resourceKey",messageResourceBean.getResourceKey());
            query.setParameter("locale",messageResourceBean.getLocale());
            messageResourceBeans = query.list();
        }  catch (UncategorizedSQLException e){
            //
        } finally {
            if(session != null){
                session.close();
            }
        }

        return messageResourceBeans.size() > 0;

    }
    public boolean deleteMessage(String resourceKey) {
        return false;
    }

    /**
     * Search message object
     * @param resourceKey key
     * @return  results
     */
    public List<MessageResourceBean> findMessage(String resourceKey) {
        Session session = null;
        List<MessageResourceBean> messageResourceBeans = null;
        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from MessageResourceBean where resourceKey = :resourceKey");
            query.setParameter("resourceKey",resourceKey);
            messageResourceBeans = query.list();
        }  catch (UncategorizedSQLException e){
            //
        } finally {
            if(session != null){
                session.close();
            }
        }

        return messageResourceBeans;
    }

    public boolean updateMessage(MessageResourceBean messageResourceBean) {
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            session.saveOrUpdate(messageResourceBean);

        } finally {
            if(session != null){
                session.close();
            }
        }

        return true;
    }

    public List<MessageResourceBean> getAllStyles() {
        return null;
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
