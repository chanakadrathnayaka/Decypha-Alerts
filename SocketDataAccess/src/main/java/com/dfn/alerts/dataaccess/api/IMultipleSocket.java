package com.dfn.alerts.dataaccess.api;

import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.exception.SocketAccessException;

import java.util.Map;

/**
 * Created by udaras on 6/4/2015.
 */
public interface IMultipleSocket {
    /**
     * Method to request multiple data with specified timeout in properties.
     * @param requestData list
     * @return
     * @throws com.dfn.alerts.exception.SocketAccessException
     */
    Map<String, ResponseObj> getData(Map<String, String> requestData) throws SocketAccessException;

    /**
     * Method to request multiple data with dynamic timeout.
     * @param requestData list
     * @param timeout  request timeout (default timeout will be set if this is equal or less than zero)
     * @return
     * @throws SocketAccessException
     */
    Map<String, ResponseObj> getData(Map<String, String> requestData, int timeout) throws SocketAccessException;
}
