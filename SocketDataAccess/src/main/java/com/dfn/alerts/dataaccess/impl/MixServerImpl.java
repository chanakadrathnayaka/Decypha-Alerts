package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.dataaccess.api.ISocket;
import com.dfn.alerts.dataaccess.utils.GsonHelper;
import com.dfn.alerts.exception.SocketAccessException;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/26/12
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class MixServerImpl extends DataRetriever implements ISocket {

    private String mixServerIP     =  null;

    private int mixServerPort      =  -1;

    public Object getData(String requestData)  throws SocketAccessException {
        return getData(requestData, -1);
    }

    public Object getData(String requestData, int timeout) throws SocketAccessException{
        if(log.isDebugEnabled())
        {
            log.debug(logPrefix + "Requesting data from MIX server IP : " + this.mixServerIP + " Port : " + this.mixServerPort);
        }

        String jsonResponse = getSocketResponse(this.mixServerIP, this.mixServerPort, requestData, timeout);
        return GsonHelper.getObject(jsonResponse);
    }

    public String getAjaxData(String requestData) throws SocketAccessException{
        if(log.isDebugEnabled())
        {
            log.debug(logPrefix + "Requesting AJAX data from MIX server IP : " + this.mixServerIP + " Port : " + this.mixServerPort);
        }
        String jsonResponse = getSocketResponse(this.mixServerIP,  this.mixServerPort, requestData, -1);
        return new GsonHelper().getJsonString(jsonResponse);
    }

    public void setMixServerIP(String mixServerIP) {
        this.mixServerIP = mixServerIP;
    }

    public void setMixServerPort(int mixServerPort) {
        this.mixServerPort = mixServerPort;
    }
}
