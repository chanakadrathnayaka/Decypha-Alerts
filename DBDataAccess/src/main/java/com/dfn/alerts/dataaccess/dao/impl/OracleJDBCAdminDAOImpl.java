package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.dataaccess.orm.impl.CustomerPublicInfo;
import com.dfn.alerts.dataaccess.dao.AdminDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 10/30/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleJDBCAdminDAOImpl implements AdminDAO {


    private static final String SQL_GET_TABLES = "SELECT table_name FROM user_tables";


    private DataSource driverManagerDataSource;

    private static final Logger LOG = LogManager.getLogger(MasterDataDAOImpl.class);

    public int executeSql(String sql) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Map<String, Object>> select(String sql)throws SQLException{
        Connection connection = null ;
        Statement statement = null;
        ResultSet rs = null;
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String,Object> oneRow = null;
        try{
            connection = driverManagerDataSource.getConnection();
            statement  = connection.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] columnNames = getColumnNames(rsmd);

            while (rs.next()){
                oneRow = new HashMap<String,Object>();
                for(String colName : columnNames){
                    Object o =  rs.getString(colName);
                    oneRow.put(colName , o);
                }
                rows.add(oneRow);
            }
        }catch (SQLException sqlE){
            throw new SQLException(sqlE);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
        }
        return rows;
    }

    private String[] getColumnNames(ResultSetMetaData rsmd) throws SQLException{

        int numberOfColumns = rsmd.getColumnCount();

        int count = 1 ; // start counting from 1 always

        String[] columnNames = new String[numberOfColumns];

        while(numberOfColumns >= count){
            columnNames [count -1] = rsmd.getColumnName(count);
            count ++;
        }

        return columnNames;
    }

    public List<String> getAllTables(int type) {
        Connection connection = null ;
        Statement statement = null;
        ResultSet rs = null;
        List<String> tables = new ArrayList<String>();
        try{
            connection = driverManagerDataSource.getConnection();
            statement  = connection.createStatement();
            rs = statement.executeQuery(SQL_GET_TABLES);
            while (rs.next()){
                tables.add(rs.getString("table_name"));
            }
        }catch (SQLException sqlE){
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e.getCause());
                }
            }
        }

        return tables;
    }

    public boolean insert(int metadataType, Object data) {
        return false;
    }

    public boolean delete(int metadataType, Object data) {
        return false;
    }

    public Object get(int metadataType, Object id) {
        return null;
    }

    @Override
    public CustomerPublicInfo getCustomerPublicInfoKey(int type, long id) {
        return null;
    }

    @Override
    public List<CustomerPublicInfo> getCustomerPublicInfoKey(int type, Set<Long> id) {
        return null;
    }

    @Override
    public CustomerPublicInfo getCustomerInfoByPublicKey(String publicKey) {
        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        //
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }
}
