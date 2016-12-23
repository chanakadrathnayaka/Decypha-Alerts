package com.dfn.alerts.dataaccess.dao.api;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Common dao interface.
 * use this interface if you go with hibernate.
 *
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/17/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommonDAO {

    void setSessionFactory(SessionFactory sessionFactory);
    void setDriverManagerDataSource(DataSource driverManagerDataSource);
}
