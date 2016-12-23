package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.dataaccess.orm.impl.CustomerPublicInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/11/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AdminDAO extends CommonDAO {

    int executeSql(String sql) throws SQLException;

    List<Map<String, Object>> select(String sql) throws SQLException;

    List<String> getAllTables(int type);

    boolean insert(int metadataType, Object data);

    boolean delete(int metadataType, Object data);

    Object get(int metadataType, Object id);

    CustomerPublicInfo getCustomerPublicInfoKey(int type, long id);

    List<CustomerPublicInfo> getCustomerPublicInfoKey(int type, Set<Long> id);

    CustomerPublicInfo getCustomerInfoByPublicKey(String publicKey);
}
