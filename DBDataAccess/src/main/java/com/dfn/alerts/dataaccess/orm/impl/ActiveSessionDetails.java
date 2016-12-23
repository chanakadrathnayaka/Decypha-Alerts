package com.dfn.alerts.dataaccess.orm.impl;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by udaras on 12/7/2016.
 */

public class ActiveSessionDetails implements Serializable {
    private Map<String, List<UserSessionDetails>> userSessionDetailsMap;

    public ActiveSessionDetails(Map<String, List<UserSessionDetails>> userSessionDetailsMap) {
        this.userSessionDetailsMap = userSessionDetailsMap;
    }

    public Map<String, List<UserSessionDetails>> getUserSessionDetailsMap() {
        return userSessionDetailsMap;
    }
}