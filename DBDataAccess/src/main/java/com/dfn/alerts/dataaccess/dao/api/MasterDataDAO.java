package com.dfn.alerts.dataaccess.dao.api;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/5/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MasterDataDAO extends CommonDAO {

    List<Object> getAll(int metadataType);

    Object get(int metadataType, Object id);

    int update(int controlPath, Object sqlQuery, Object data);
}
