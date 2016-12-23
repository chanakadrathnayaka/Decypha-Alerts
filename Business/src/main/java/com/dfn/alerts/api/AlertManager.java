package com.dfn.alerts.api;

/**
 * Created by chathurag
 * On 12/5/2016
 */
public interface AlertManager {

    public int saveAlerts(Object data);

    public Object searchAlerts();

    public int updateCache();

}
