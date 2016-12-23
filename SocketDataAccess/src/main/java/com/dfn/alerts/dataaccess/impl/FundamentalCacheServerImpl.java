package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.dataaccess.api.ISocket;
import com.dfn.alerts.dataaccess.utils.GsonHelper;
import com.dfn.alerts.exception.SocketAccessException;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 12/6/12
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FundamentalCacheServerImpl extends DataRetriever implements ISocket {

    private String fundamentalServerIP     =  null;

    private int fundamentalServerPort      =  -1;

    private String fundamentalServerIPFailOverIP = null;

    private int fundamentalServerFailOverPort = -1;

    public Object getData(String requestData)  throws SocketAccessException {
        return getData(requestData, -1);
    }

    public Object getData(String requestData, int timeout)  throws SocketAccessException {

        if(log.isDebugEnabled())
        {
            log.debug(logPrefix + "Requesting data from Fundamental server IP :" + this.fundamentalServerIP + "Port : " + this.fundamentalServerPort);
        }
        String jsonResponse = getSocketResponse(this.fundamentalServerIP, this.fundamentalServerPort, requestData, timeout,
                this.fundamentalServerIPFailOverIP, this.fundamentalServerFailOverPort);
        return GsonHelper.getObject(jsonResponse);
    }

    public String getAjaxData(String requestData) throws SocketAccessException  {
        if(log.isDebugEnabled())
        {
            log.debug(logPrefix + "Requesting data from Fundamental server IP :" + this.fundamentalServerIP + "Port : " + this.fundamentalServerPort);
        }

        return getSocketResponse(this.fundamentalServerIP, this.fundamentalServerPort, requestData, -1,
                this.fundamentalServerIPFailOverIP,this.fundamentalServerFailOverPort);
    }

    public void setFundamentalServerIP(String fundamentalServerIP) {
        this.fundamentalServerIP = fundamentalServerIP;
    }

    public void setFundamentalServerPort(int fundamentalServerPort) {
        this.fundamentalServerPort = fundamentalServerPort;
    }

    public void setFundamentalServerIPFailOverIP(String fundamentalServerIPFailOverIP) {
        this.fundamentalServerIPFailOverIP = fundamentalServerIPFailOverIP;
    }

    public void setFundamentalServerFailOverPort(int fundamentalServerFailOverPort) {
        this.fundamentalServerFailOverPort = fundamentalServerFailOverPort;
    }
}