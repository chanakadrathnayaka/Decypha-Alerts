package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.DataAccessRequestDTO;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.dao.api.FundamentalDataNotificationDAO;
import com.dfn.alerts.dataaccess.orm.impl.notifications.FundamentalDataNotification;
import com.dfn.alerts.exception.SocketAccessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 11:06
 */
public class FundamentalDataNotificationDataAccess extends DataAccess {

    /**
     * Helper class to generate notification request
     */
    private IRequestGenerator requestGenerator;

    private static final Logger LOG = LogManager.getLogger(FundamentalDataNotificationDataAccess.class);

    public void setRequestGenerator(IRequestGenerator requestGenerator) {
        this.requestGenerator = requestGenerator;
    }

    @Override
    public Object getMemoryData(Map<String, String> requestData) {
        return null;
    }

    @Override
    protected String generateCacheKey(Map<String, String> requestData) {
        return null;
    }

    /**
     * This method get notifications from ONLY MIX RT=128 on a scheduled time period
     * Notifications are not cached,
     * Always get direct data
     * @param requestData requestData
     * @param isDirectData isDirectData
     * @param isJsonResponse isJsonResponse
     * @return Object
     */
    @Override
    public Object getData(Map<String, String> requestData, boolean isDirectData, boolean isJsonResponse) {
        return getSocketResponse(this.requestGenerator.generateRequest(requestData, IConstants.RequestDataType.UPDATE_NOTIFICATION), isJsonResponse);
    }

    @Override
    protected Object getSocketResponse(String request, boolean isJsonResponse) {
        ResponseObj notifications = null;
        try {
            ResponseObj response = (ResponseObj) socketManager.getData(request);
            if (response != null) {
                notifications = (ResponseObj) processResponse(response);
            }
        } catch (SocketAccessException e) {
            LOG.error("Socket Exception caught while getting fundamental data notifications");
        }

        return notifications;
    }

    @Override
    protected Object getSocketResponse(String request, int timeout, boolean isJsonResponse) {
        return null;
    }

    /**
     * response object has been returned since no need of an extra processing
     * @param response ResponseObj
     * @return ResponseObj
     */
    @Override
    protected Object processResponse(ResponseObj response) {
        return response;
    }

    @Override
    public int updateData(DataAccessRequestDTO requestDTO) {
        return 0;
    }

    @Override
    public void initUpdateData() {
    }

    public int saveAlerts(List<FundamentalDataNotification> notifications){
        FundamentalDataNotificationDAO dataNotificationDAO = daoFactory.getFundamentalDataNotificationDAO();
        return dataNotificationDAO.save(notifications);
    }
}
