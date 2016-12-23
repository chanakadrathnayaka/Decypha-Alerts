package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.utils.GsonHelper;
import com.dfn.alerts.exception.SocketAccessException;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: hasarindat
 * Date: 11/22/12
 * Time: 5:11 PM
 */
public abstract class DataRetriever {

    private int socketTimeOut = 60000;

    protected static Logger log = LogManager.getLogger(DataRetriever.class);

    protected String logPrefix;


    private boolean isServiceFailedResponse(String response) {
        boolean returnValue = false;
        if (response == null) {
            returnValue = true;
        } else {
            ResponseObj responseObj = GsonHelper.getObject(response);

            if (responseObj == null) {
                returnValue = true;
            } else {
                Object statusObject = responseObj.getSTAT();
                if (statusObject != null) {

                    Object statLeave = getLeaveOfSTATMap(statusObject);
                    String status = (String) statLeave;

                    if ("-100|UnexpectedError".equals(status)) {
                        returnValue = true;
                    }

                } else {
                    returnValue = true;
                }

            }
        }
        return returnValue;
    }

    /*
   * This method returns the leaf node of status of
   * a response
   *
    */
    @SuppressWarnings("unchecked")
    private Object getLeaveOfSTATMap(Object map) {

        Object returnValue = null;
        if (map instanceof HashMap) {
            Map<String, Object> statusObjectMap = (HashMap<String, Object>) map;
            for (Object o : statusObjectMap.values()) {
                if (o instanceof HashMap) {
                    Map<String, Object> oMap = (HashMap<String, Object>) o;
                    returnValue = getLeaveOfSTATMap(oMap);
                } else {
                    returnValue = o;
                    break;
                }
            }
        } else {
            returnValue = map;
        }
        return returnValue;
    }

    protected String getSocketResponse(String ip, int port, String requestData, int timeout) throws SocketAccessException {
        return getSocketResponse(ip, port, requestData, timeout, null, null);
    }

    protected String getSocketResponse(String ip, int port, String requestData, int timeout, String failOverIp,
                                       Integer failOverPort) throws SocketAccessException {

        if (port < 0 || port > 0xFFFF) {
            throw new IllegalArgumentException("port out of range: " + port);
        }
        if (ip == null || "".equals(ip)) {
            throw new IllegalArgumentException("hostname can't be null/empty");
        }

        timeout = (timeout > 0) ? timeout : this.socketTimeOut;
        String response = null;
        Socket requestSocket = null;

        UUID correlationId = null;

        if (log.isDebugEnabled()) {
            correlationId = UUID.randomUUID();
            log.debug(logPrefix + correlationId + " IP : " + ip + " Port:" + port + " OUTGOING : \n" + requestData + "\n");
        }
        try {

            requestSocket = new Socket(ip, port);
            requestSocket.setSoTimeout(timeout);

            if (log.isDebugEnabled()) {
                log.debug(logPrefix + "Connected to " + ip + "  port " + port);
            }

            write(requestData, requestSocket);
            response = read(requestSocket);

            if (log.isDebugEnabled()) {
                log.debug(logPrefix + correlationId + " IP : " + ip + " Port: " + port + " INCOMING : \n" + response + "\n");
            }

            if (isServiceFailedResponse(response) &&  failOverIp != null && !IConstants.EMPTY.equals(failOverIp) && failOverPort != null) {
                log.error(logPrefix + "Error Response from: " + ip + " in port " + port);
                log.info(logPrefix + "Trying Failover IP:" + failOverIp + "Port:" + failOverPort);
                response = getSocketResponse(failOverIp, failOverPort, requestData, timeout);
            }

        } catch (IOException ioException) {
            handleException(ioException, ip, port);
            log.info(logPrefix + "Trying Failover IP:" + failOverIp + "Port:" + failOverPort);
            if (failOverIp == null || IConstants.EMPTY.equals(failOverIp)) {
                log.info(logPrefix + "Failover Ip failed");
                throw new SocketAccessException(ioException);
            } else {
                response = getSocketResponse(failOverIp, failOverPort, requestData, timeout);
            }

        } finally {
            if (requestSocket != null) {
                try {
                    requestSocket.close();
                } catch (IOException ex) {
                    log.error(logPrefix + "Exception is: ", ex);
                    log.error(logPrefix + "Error while returning the object to pool");
                }
            }

        }

        return response;
    }

    /**
     * getSocketResponse from primary service
     * @param ip primary ip
     * @param port primary port
     * @param requestDataMap request data map
     * @param timeout socket timeout
     * @return response map
     * @throws SocketAccessException
     */
    protected Map<String, String> getSocketResponse(String ip, int port, Map<String, String> requestDataMap, int timeout) throws SocketAccessException {
        return getSocketResponse(ip, port, requestDataMap, timeout, null, null);
    }

    /**
     * getSocketResponse
     * This method directed the request to failover service when primary service is down
     * @param ip primary ip
     * @param port primary port
     * @param requestDataMap request data map
     * @param timeout socket timeout
     * @param failOverIp failover ip
     * @param failOverPort failover port
     * @return response map
     * @throws SocketAccessException
     */
    protected Map<String, String> getSocketResponse(String ip, int port, Map<String, String> requestDataMap, int timeout,
                                                    String failOverIp, Integer failOverPort) throws SocketAccessException {

        if (port < 0 || port > 0xFFFF) {
            throw new IllegalArgumentException("port out of range: " + port);
        }
        if (ip == null || "".equals(ip)) {
            throw new IllegalArgumentException("hostname can't be null/empty");
        }

        Map<String, String> responseMap = new HashMap<String, String>(requestDataMap.size());

        timeout = (timeout > 0) ? timeout : this.socketTimeOut;
        String response;
        String requestData;
        Socket requestSocket = null;
        UUID correlationId = null;
        try {

            requestSocket = new Socket(ip, port);
            requestSocket.setSoTimeout(timeout);

            if (log.isDebugEnabled()) {
                log.debug(logPrefix + "Connected to IP: " + ip + "  port: " + port);
            }

            for (String id : requestDataMap.keySet()) {
                requestData = requestDataMap.get(id);

                if (log.isDebugEnabled()) {
                    correlationId = UUID.randomUUID();
                    log.debug(logPrefix + correlationId + " IP : " + ip + " Port:" + port + " OUTGOING : \n" + requestData + "\n");
                }

                write(requestData, requestSocket);
                response = read(requestSocket);
                responseMap.put(id, response);

                if (log.isDebugEnabled()) {
                    log.debug(logPrefix + correlationId + " IP : " + ip + " Port: " + port + " INCOMING : \n" + response + "\n");
                }

                if (isServiceFailedResponse(response)) {
                    log.error(logPrefix + "Service Failed Response From IP : " + ip + " Port : " + port);

                    if(!CommonUtils.isNullOrEmptyString(failOverIp) && failOverPort != null) {
                        log.info(logPrefix + "Trying To Connect via Failover IP :" + failOverIp + " Port: " + failOverPort);
                        responseMap = getSocketResponse(failOverIp, failOverPort, requestDataMap, timeout);
                    }

                    break;
                }
            }

        } catch (UnknownHostException unknownHost) {
            handleException(unknownHost, ip, port);
            log.info(logPrefix + "Trying Failover IP:" + failOverIp + "Port:" + failOverPort);

            if (CommonUtils.isNullOrEmptyString(failOverIp)) {
                log.info(logPrefix + "Failover Ip failed");
                throw new SocketAccessException(unknownHost);
            } else {
                responseMap = getSocketResponse(failOverIp, failOverPort, requestDataMap, timeout);
            }

        } catch (IOException ioException) {
            handleException(ioException, ip, port);
            log.info(logPrefix + "Trying Failover IP:" + failOverIp + "Port:" + failOverPort);

            if (CommonUtils.isNullOrEmptyString(failOverIp)) {
                log.info(logPrefix + "Failover Ip failed");
                throw new SocketAccessException(ioException);
            } else {
                responseMap = getSocketResponse(failOverIp, failOverPort, requestDataMap, timeout);
            }

        } finally {

            if (requestSocket != null) {
                try {
                    requestSocket.close();

                    if (log.isDebugEnabled()) {
                        log.debug(logPrefix + "Disconnected from IP :" + ip + "  Port: " + port);
                    }

                } catch (IOException ex) {
                    log.error(logPrefix + "Exception is: ", ex);
                    log.error(logPrefix + "Error while returning the object to pool");
                }
            }

        }

        return responseMap;
    }


    private void handleException(Exception e, String ip, int port) {
        log.error(logPrefix + "Not Connected to " + ip + " in port " + port);
        log.error(logPrefix + "Exception is: ", e);

    }


    private void write(String data, Socket requestSocket) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(requestSocket.getOutputStream()));
        out.write(data);
        out.flush();
    }

    private String read(Socket requestSocket) throws IOException {
        return readFrame(new BufferedInputStream(requestSocket.getInputStream()));
    }


    private String readFrame(BufferedInputStream in) throws IOException {
        if (in == null) {
            throw new IOException();
        }

        StringBuilder lengthBuffer = new StringBuilder();


        int i = in.read();

        if (i == -1) {
            throw new IOException();
        }

        char ch = (char) i;

        while (ch != '{') {
            lengthBuffer.append(ch);
            i = in.read();
            if (i == -1) {
                throw new IOException();
            }
            ch = (char) i;
        }

        int dataLength = 0;

        try {
            dataLength = Integer.parseInt(lengthBuffer.toString());
        } catch (Exception e) {
            log.error(logPrefix + "Error while returning the object to pool");
            log.error(logPrefix + "Exception is: ", e);
        }

        byte[] dataBytes = new byte[dataLength];

        for (int index = 1; index < dataLength; index++) {
            dataBytes[index - 1] = (byte) i;
            i = in.read();
            if (i == -1) {
                throw new IOException();
            }
        }

        dataBytes[dataLength - 1] = (byte) i;
        return new String(dataBytes, "UTF-8");
    }


    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }
}