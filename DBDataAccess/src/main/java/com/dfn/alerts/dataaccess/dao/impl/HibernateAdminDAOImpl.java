package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.orm.impl.CustomerPublicInfo;
import com.dfn.alerts.dataaccess.dao.AdminDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Danuran on 4/10/2015.
 */
public class HibernateAdminDAOImpl implements AdminDAO {

    private SessionFactory sessionFactory = null;
    public static final boolean STATUS_FAILED = false;
    public static final boolean STATUS_SUCCESS = true;
    private static final Logger LOG = LogManager.getLogger(HibernateAdminDAOImpl.class);
    private static final String LOG_PREFIX = " :: HibernateAdminDAOImpl :: ";

    @Override
    public int executeSql(String sql) throws SQLException {
        return 0;
    }

    @Override
    public List<Map<String, Object>> select(String sql) throws SQLException {
        return null;
    }

    @Override
    public List<String> getAllTables(int type) {
        return null;
    }

    @Override
    public boolean insert(int metadataType, Object data) {
        return false;
    }

    @Override
    public boolean delete(int metadataType, Object data) {
        return false;
    }

    @Override
    public Object get(int metadataType, Object id) {
        return null;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {

    }

    public CustomerPublicInfo getCustomerPublicInfoKey(int type, long id) {
        CustomerPublicInfo customerPublicInfo = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(CustomerPublicInfo.class);
            criteria.add(Restrictions.eq("type", type));
            criteria.add(Restrictions.eq("id", id));
            Object object = criteria.uniqueResult();
            if (object != null) {
                customerPublicInfo = (CustomerPublicInfo) object;
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading customer public key ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerPublicInfo;
    }

    public List<CustomerPublicInfo> getCustomerPublicInfoKey(int type, Set<Long> id) {
        List<CustomerPublicInfo> customerPublicInfoList = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(CustomerPublicInfo.class);
            criteria.add(Restrictions.eq("type", type));
            criteria.add(Restrictions.in("id", id));
            Object object = criteria.list();
            if (object != null) {
                customerPublicInfoList = (List<CustomerPublicInfo>)object;
            }else{
                customerPublicInfoList = Collections.emptyList();
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading customer public key ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerPublicInfoList;
    }

    public CustomerPublicInfo getCustomerInfoByPublicKey(String publicKey) {
        CustomerPublicInfo customerPublicInfo = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(CustomerPublicInfo.class);
            criteria.add(Restrictions.eq("publicId", publicKey));
            Object object = criteria.uniqueResult();
            if (object != null) {
                customerPublicInfo = (CustomerPublicInfo) object;
            }
        } catch (Exception ex) {
            LOG.error(LOG_PREFIX + " Loading customer public information by public key ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerPublicInfo;
    }
}
