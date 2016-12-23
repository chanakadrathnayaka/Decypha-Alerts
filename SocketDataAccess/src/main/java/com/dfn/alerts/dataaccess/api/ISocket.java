package com.dfn.alerts.dataaccess.api;

import com.dfn.alerts.exception.SocketAccessException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/26/12
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISocket {

    /**
     * Method to request data with specified timeout in properties.
     * @param requestData
     * @return
     * @throws SocketAccessException
     */
    Object getData(String requestData) throws SocketAccessException;

    /**
     * Method to request data with dynamic timeout.
     * @param requestData
     * @param timeout  request timeout (default timeout will be set if this is equal or less than zero)
     * @return
     * @throws SocketAccessException
     */
    Object getData(String requestData, int timeout) throws SocketAccessException, IOException;

    /**
     * Method to get Ajax data
     * @param requestData
     * @return
     * @throws SocketAccessException
     */
    String getAjaxData(String requestData) throws SocketAccessException;



}
