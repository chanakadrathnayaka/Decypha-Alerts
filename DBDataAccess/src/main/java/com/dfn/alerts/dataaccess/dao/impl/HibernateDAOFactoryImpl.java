package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.dao.*;
import com.dfn.alerts.dataaccess.dao.api.EarningAnnouncementDAO;
import com.dfn.alerts.dataaccess.dao.api.FundamentalDataNotificationDAO;
import com.dfn.alerts.dataaccess.dao.impl.notification.EmailDAOImpl;
import com.dfn.alerts.dataaccess.dao.impl.notifications.FundamentalDataNotificationDAOImpl;
import com.dfn.alerts.dataaccess.dao.notification.EmailDAO;
import com.dfn.alerts.dataaccess.utils.CriteriaGenerator;
import org.hibernate.SessionFactory;

/**
 * Concrete factory class to create hibernate dao
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/15/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateDAOFactoryImpl extends DAOFactory {

    private MessageResourcesDAOImpl resourceDAO = null;

    private HibernateMetaDataDAOImpl metaDataDAO = null;

    private MasterDataDAOImpl masterDataDAO = null;

    private NewsDAOImpl newsDAO = null;

    private UserDetailsDAOImpl userDAO = null;

    private FinancialSegmentDAOImpl financialSegmentDAO = null;

    private SessionFactory session;

    private CriteriaGenerator criteriaGenerator;

    private EmailDAO emailDAO = null;

    private EarningAnnouncementDAO earningAnnouncementDAO = null;

    private FundamentalDataNotificationDAO fundamentalDataNotificationDAO = null;

    public synchronized MessageResourcesDAO getMessageResourcesDAO() {
        if (resourceDAO == null) {
            resourceDAO = new MessageResourcesDAOImpl();
            resourceDAO.setSessionFactory(this.session);
        }
        return resourceDAO;
    }


    public synchronized MetaDataDAO getMetaDataDAO() {
        if (metaDataDAO == null) {
            metaDataDAO = new HibernateMetaDataDAOImpl();
            metaDataDAO.setSessionFactory(this.session);
        }
        return metaDataDAO;
    }

    public synchronized MasterDataDAOImpl getMasterDataDAO() {
        if (masterDataDAO == null) {
            masterDataDAO = new MasterDataDAOImpl();
            masterDataDAO.setSessionFactory(this.session);
            masterDataDAO.setCriteriaGenerator(this.criteriaGenerator);
        }
        return masterDataDAO;
    }


    @Override
    public MasterDataDAO getFundamentalDataDAO() {
        throw new RuntimeException("Not Implemented");
    }


    @Override
    public UserDetailsDAOImpl getUerDAO() {
        if (userDAO == null) {
            userDAO = new UserDetailsDAOImpl();
            userDAO.setSessionFactory(this.session);
        }
        return userDAO;
    }

    @Override
    public FinancialSegmentDAO getFinancialSegmentDAO() {
        if (financialSegmentDAO == null) {
            financialSegmentDAO = new FinancialSegmentDAOImpl();
            financialSegmentDAO.setSessionFactory(this.session);
        }
        return financialSegmentDAO;
    }

    @Override
    public IAppConfigData getAppConfigDataDAO() {
        return null;
    }

    public NewsDAOImpl getNewsDAO() {
        return newsDAO;
    }

    public void setNewsDAO(NewsDAOImpl newsDAO) {
        this.newsDAO = newsDAO;
    }

    public SessionFactory getSession() {
        return session;
    }

    public CommonDAO getAnnouncementDAO() {
        return null;
    }

    @Override
    public SmsDAO getSmsDAO() {
        return null;
    }

    public void setSession(SessionFactory session) {
        this.session = session;
    }

    public void setCriteriaGenerator(CriteriaGenerator criteriaGenerator) {
        this.criteriaGenerator = criteriaGenerator;
    }

    @Override
    public EmailDAO getEmailDAO() {
        if (emailDAO == null) {
            emailDAO = new EmailDAOImpl();
            emailDAO.setSessionFactory(this.session);
        }
        return emailDAO;
    }

    @Override
    public EarningAnnouncementDAO getEarningAnnouncementDAO() {
        if(earningAnnouncementDAO == null){
            earningAnnouncementDAO = new EarningAnnouncementDAOImpl();
            earningAnnouncementDAO.setSessionFactory(this.session);
        }
        return earningAnnouncementDAO;
    }

    @Override
    public FundamentalDataNotificationDAO getFundamentalDataNotificationDAO() {
        if(fundamentalDataNotificationDAO == null){
            fundamentalDataNotificationDAO = new FundamentalDataNotificationDAOImpl();
            fundamentalDataNotificationDAO.setSessionFactory(this.session);
        }
        return fundamentalDataNotificationDAO;
    }
}
