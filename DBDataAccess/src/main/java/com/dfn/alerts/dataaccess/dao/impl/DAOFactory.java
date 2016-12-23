package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.dao.*;
import com.dfn.alerts.dataaccess.dao.api.EarningAnnouncementDAO;
import com.dfn.alerts.dataaccess.dao.api.FundamentalDataNotificationDAO;
import com.dfn.alerts.dataaccess.dao.notification.EmailDAO;

/**
 * Abstract factory to create concrete factories
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/17/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DAOFactory {

    /**
     * Hibernate dao factory type
     */
    public static final int HIBERNATE_DAO_FAC = 1;

    /**
     * JDBC dao factory type
     */
    public static final int JDBC_DAO_FAC = 2;

    /**
     * IMDB dao factory type
     */
    public static final int IMDB_DAO_FAC = 3;

    /**
     * Abstract method to get message resources dao
     *
     * @return <tt>MessageResourcesDAO</tt> interface
     */
    public abstract MessageResourcesDAO getMessageResourcesDAO();

    /**
     * Abstract method to get meta data dao
     *
     * @return <tt>MetaDataDAO</tt> interface
     */
    public abstract MetaDataDAO getMetaDataDAO();

    /**
     * Abstract method to get meta data dao
     *
     * @return <tt>MetaDataDAO</tt> interface
     */
    public abstract MasterDataDAO getFundamentalDataDAO();

    /**
     * Abstract method to get meta data dao
     *
     * @return <tt>MetaDataDAO</tt> interface
     */
    public abstract MasterDataDAO getMasterDataDAO();

    /**
     * Abstract method to get news updates in db dao
     *
     * @return
     */
    public abstract NewsDAOImpl getNewsDAO();

    /**
     * Abstract method to get authentication dao
     *
     * @return AuthenticationDAOImpl
     */
    public abstract UserDetailsDAO getUerDAO();

    /**
     * Abstract method to get financial segment dao
     *
     * @return ReleaseNoteDAOImpl
     */
    public abstract FinancialSegmentDAO getFinancialSegmentDAO();

    /**
     * Abstract method to get {@link IAppConfigData} dao
     *
     * @return AuthenticationDAOImpl
     */
    public abstract IAppConfigData getAppConfigDataDAO();

    public abstract CommonDAO getAnnouncementDAO();

    /**
     * Factory method to return DAOFactory
     *
     * @param type factory type
     * @return <tt>DAOFactory</tt> interface
     */
    public static DAOFactory getDAOFactory(int type) {
        switch (type) {
            case HIBERNATE_DAO_FAC:
                return new HibernateDAOFactoryImpl();
            case JDBC_DAO_FAC:
                return new JDBCDAOFactoryImpl();
            default:
                return new HibernateDAOFactoryImpl();
        }
    }

    public abstract SmsDAO getSmsDAO();

    /**
     * Abstract method to get financial analyst dao
     *
     * @return FinancialAnalystDAO
     */
    public abstract EmailDAO getEmailDAO();


    public abstract EarningAnnouncementDAO getEarningAnnouncementDAO();

    public abstract FundamentalDataNotificationDAO getFundamentalDataNotificationDAO();

}
