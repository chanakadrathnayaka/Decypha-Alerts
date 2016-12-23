package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.orm.impl.CurrencyRate;
import com.dfn.alerts.dataaccess.dao.MetaDataDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.UncategorizedSQLException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.dfn.alerts.constants.DBConstants.MetaDataType.*;

/**
 * Dao implementation to work with Exchange data ( Sources )
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/29/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateMetaDataDAOImpl implements MetaDataDAO {

    private SessionFactory sessionFactory = null;

    /**
     * Public method to get all types of metadata
     *
     * @param metadataType meta data type
     * @return list of meta data for given type
     */
    public List<Object> getAll(final int metadataType) {

        List<Object> returnObj = null;

        switch (metadataType) {
            case META_DATA_CURRENCY_RATES: {
                returnObj = getAllCurrencyRates();
                break;
            }
            case META_DATA_GET_TOP_NEWS_EDITION_DATA:{
                returnObj = getAllTopNewsEditions();
                break;
            }
            default:
                break;
        }

        return returnObj;
    }

    /**
     * Public method to get all types of metadata
     *
     * @param metadataType meta data type
     * @param id           unique id for given type
     * @return meta data object for given type
     */
    public Object get(int metadataType, Object id) {
        Object returnObj = null;

        switch (metadataType) {
            case META_DATA_CURRENCY_RATES: {
                returnObj = getCurrencyRate((String) id);
                break;
            }

            default:
                break;
        }

        return returnObj;
    }

    public boolean insert(int metadataType, Object data) {
        return false;
    }

    public boolean delete(int metadataType, Object data) {
        return false;
    }

    /**
     * Method to get all Currency Rates from database
     *
     * @return list of CurrencyRate objects
     * @see com.dfn.alerts.dataaccess.orm.impl.CurrencyRate
     */
    private List<Object> getAllCurrencyRates() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from CurrencyRate");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get all Currency Rates from database
     *
     * @return list of CurrencyRate objects
     * @see com.dfn.alerts.dataaccess.orm.impl.CurrencyRate
     */
    private List<Object> getAllTopNewsEditions() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from TopNewsEdition");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get Currency Rate for given pair
     *
     * @param pair Currency pair
     * @return CurrencyRate objects
     */
    private Object getCurrencyRate(String pair) {
        Session session = null;
        CurrencyRate currencyRate = null;
        try {
            session = this.sessionFactory.openSession();
            currencyRate = (CurrencyRate) session.get(CurrencyRate.class, pair);
        } catch (UncategorizedSQLException ignored) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return currencyRate;
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
    }
}

