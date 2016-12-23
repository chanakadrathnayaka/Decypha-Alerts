package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.dataaccess.orm.impl.UserRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 4/1/14
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAuditDAO  extends CommonDAO{

    boolean saveUserRequest(UserRequest userRequest);
}
