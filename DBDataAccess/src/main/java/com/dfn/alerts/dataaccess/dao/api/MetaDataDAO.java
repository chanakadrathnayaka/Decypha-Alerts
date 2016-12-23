package com.dfn.alerts.dataaccess.dao.api;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/29/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MetaDataDAO extends CommonDAO {

    List<Object> getAll(int metadataType);

    Object get(int metadataType, Object id);

    boolean insert(int metadataType, Object data);

    boolean delete(int metadataType, Object data);
}
