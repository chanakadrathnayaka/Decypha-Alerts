package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.dao.*;
import com.dfn.alerts.dataaccess.dao.api.EarningAnnouncementDAO;
import com.dfn.alerts.dataaccess.dao.api.FundamentalDataNotificationDAO;
import com.dfn.alerts.dataaccess.dao.notification.EmailDAO;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/17/13
 * Time: 2:40 PM
 */
public class JDBCDAOFactoryImpl extends DAOFactory {

    private DataSource driverManagerDataSource;
    private JDBCMetaDataDAOImpl jdbcMetaDataDAO = null;
    private JDBCFundamentalDataDAOImpl jdbcFundamentalDataDAO = null;
    private OracleJDBCAdminDAOImpl oracleAdminDAO = null;
    private JDBCAnnouncementDAOImpl announcementDAO = null;
    private JDBCUserDetailDAOImpl userDAO = null;
    private FinancialSegmentDAOImpl financialSegmentDAO = null;

    @Override
    public MessageResourcesDAO getMessageResourcesDAO() {
        return null;
    }

    @Override
    public MetaDataDAO getMetaDataDAO() {
        if (jdbcMetaDataDAO == null) {
            jdbcMetaDataDAO = new JDBCMetaDataDAOImpl();
            jdbcMetaDataDAO.setDriverManagerDataSource(this.driverManagerDataSource);
        }
        return jdbcMetaDataDAO;
    }

    @Override
    public MasterDataDAO getFundamentalDataDAO() {
        if (jdbcFundamentalDataDAO == null) {
            jdbcFundamentalDataDAO = new JDBCFundamentalDataDAOImpl();
            jdbcFundamentalDataDAO.setDriverManagerDataSource(this.driverManagerDataSource);
        }
        return jdbcFundamentalDataDAO;
    }

    @Override
    public MasterDataDAO getMasterDataDAO() {
        return null;
    }

    @Override
    public NewsDAOImpl getNewsDAO() {
        return null;
    }

    @Override
    public IAppConfigData getAppConfigDataDAO() {
        return null;
    }

    public CommonDAO getAnnouncementDAO() {
        if (announcementDAO == null) {
            announcementDAO = new JDBCAnnouncementDAOImpl();
            announcementDAO.setDriverManagerDataSource(this.driverManagerDataSource);
        }
        return announcementDAO;
    }

    @Override
    public UserDetailsDAO getUerDAO() {
        if (userDAO == null) {
            userDAO = new JDBCUserDetailDAOImpl();
            userDAO.setDriverManagerDataSource(this.driverManagerDataSource);
        }
        return userDAO;
    }

    @Override
    public FinancialSegmentDAO getFinancialSegmentDAO() {
        if (financialSegmentDAO == null) {
            financialSegmentDAO = new FinancialSegmentDAOImpl();
            financialSegmentDAO.setDriverManagerDataSource(this.driverManagerDataSource);
        }
        return financialSegmentDAO;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }


    @Override
    public SmsDAO getSmsDAO() {
        return null;
    }

    @Override
    public EmailDAO getEmailDAO() {
        return null;
    }

    @Override
    public EarningAnnouncementDAO getEarningAnnouncementDAO() {
        return null;
    }

    @Override
    public FundamentalDataNotificationDAO getFundamentalDataNotificationDAO() {
        return null;
    }

}
