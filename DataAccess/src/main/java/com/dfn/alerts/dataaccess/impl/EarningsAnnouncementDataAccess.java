package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.DataAccessRequestDTO;
import com.dfn.alerts.beans.EarningsAnnouncement;
import com.dfn.alerts.constants.CacheKeyConstant;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;
import com.dfn.alerts.dataaccess.utils.ClientRequestParser;
import com.dfn.alerts.dataaccess.utils.DataAccessUtils;
import com.dfn.alerts.exception.SocketAccessException;

import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 12/19/12
 * Time: 12:50 PM
 */
public class EarningsAnnouncementDataAccess extends DataAccess {
    /**
     * Helper class to generate earnings request
     */
    private IRequestGenerator requestGenerator;

    /**
     * Constants
     */
    private static final String FIRST_PAGE_INDEX = "0";
    private static final String ANNOUNCED_VALUE = "ANNOUNCED_VALUE";
    private static final String COMPARED_VALUE = "COMPARED_VALUE";
    private static final String CHANGE = "CHANGE";
    private static final String PER_CHANGE = "PER_CHANGE";

    /*==========================================Setters=================================================================*/

    public void setRequestGenerator(IRequestGenerator requestGenerator) {
        this.requestGenerator = requestGenerator;
    }

    /*==========================================Implement Methods For DataAccess========================================*/

    @Override
    public Object getMemoryData(Map<String, String> requestData) {
        return null;
    }

    /**
     * Generate the unique cache key using request data map
     *
     * @param requestData user request data map
     * @return Generated cache key for given request data
     */
    @Override
    protected String generateCacheKey(Map<String, String> requestData) {
        StringBuilder cacheKey = new StringBuilder();
        cacheKey.append(CacheKeyConstant.CACHE_KEY_PREFIX_EARNINGS);
        cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));

        cacheKey.append(requestData.get(IConstants.MIXDataField.E));

        cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));
        cacheKey.append(requestData.get(IConstants.MIXDataField.L));

        if ((requestData.get(IConstants.MIXDataField.STK) != null)) {
            cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));
            cacheKey.append(requestData.get(IConstants.MIXDataField.STK));
        }

        if ((requestData.get(IConstants.MIXDataField.SCDT) != null)) {
            cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));
            cacheKey.append(requestData.get(IConstants.MIXDataField.SCDT));
        }

        /**
         * caching the first pages of both ascending and descending order of earnings
         * This caching is only happens when the pagination is enabled at client level.
         * */
        if ((requestData.get(IConstants.MIXDataField.PGI) != null && requestData.get(IConstants.MIXDataField.PGI).equals(FIRST_PAGE_INDEX)) && requestData.get(IConstants.MIXDataField.SO) != null && requestData.get(IConstants.MIXDataField.SF) != null) {
            cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));
            cacheKey.append(requestData.get(IConstants.MIXDataField.SO));
            cacheKey.append(ClientRequestParser.toString(IConstants.Delimiter.TILDE));
            cacheKey.append(requestData.get(IConstants.MIXDataField.SF));
        }

        return cacheKey.toString();
    }

    /**
     * This method provides processed earnings data, while caching the custom object in the cache
     *
     * @param requestData    user requested data map
     * @param isDirectData   flag to indicate that the data should always comes from socket instead of cache
     * @param isJsonResponse flag to indicate the client needs the jason response
     * @return EarningsAnnouncement object or json object
     */
    @Override
    public Object getData(Map<String, String> requestData, boolean isDirectData, boolean isJsonResponse) {
        EarningsAnnouncement earningsAnnouncement;
        String cacheKey = generateCacheKey(requestData);

        if (isDirectData || !isCacheEnabled) {
            earningsAnnouncement = getEarningsAnnouncementData(requestData, isJsonResponse);
        } else {
            earningsAnnouncement = null;

            if (earningsAnnouncement == null) {
                //get the requested earnings data
                earningsAnnouncement = getEarningsAnnouncementData(requestData, isJsonResponse);

                if (earningsAnnouncement != null && earningsAnnouncement.getEarningsAnnouncements() != null && earningsAnnouncement.getEarningsAnnouncements().size() > 0) {
                    //caching the requested earnings data
//                    putData(cacheKey, earningsAnnouncement, getTimeToLive());
                }
            }
        }

        return earningsAnnouncement;
    }

    @Override
    protected Object getSocketResponse(String request, boolean isJsonResponse) {
        return null;
    }

    /**
     * Get the socket response
     *
     * @param request        generated request string
     * @param isJsonResponse flag to indicate that the clinet needs jason response
     * @return processed earnings data
     */
    @Override
    protected EarningsAnnouncement getSocketResponse(String request,int timeout, boolean isJsonResponse) {
        return null;
    }

    /**
     * Get the socket response
     *
     * @param request        generated request string
     * @param isJsonResponse flag to indicate that the clinet needs jason response
     * @return processed earnings data
     */
    protected EarningsAnnouncement getSocketResponse(String request, String earningsType, int timeout, boolean isJsonResponse) {
        EarningsAnnouncement announcementItem = null;

        try {
            ResponseObj response = (ResponseObj) socketManager.getData(request, timeout);
            announcementItem = processResponse(response, earningsType);
        } catch (SocketAccessException | IOException e) {
            e.printStackTrace();
        }

        return announcementItem;
    }

    /**
     * Process the earnings response and store in system optimized object
     *
     * @param response socket response object
     * @return prcessed earnings data
     */
    @Override
    @SuppressWarnings("unchecked")
    protected EarningsAnnouncement processResponse(ResponseObj response) {
        return null;
    }

    /**
     * Process the earnings response and store in system optimized object
     *
     * @param response socket response object
     * @return prcessed earnings data
     */
    @SuppressWarnings("unchecked")
    protected EarningsAnnouncement processResponse(ResponseObj response, String earningsType) {

        EarningsAnnouncement earningsAnnouncement = null;
        List<Map<String, String>> earningsAnnouncementList;
        String head;
        List<String> data;
        String rowCount;

        if (response != null && response.getHED() != null) {
            earningsAnnouncementList = new ArrayList<Map<String, String>>();
            head = (String) ((Map) response.getHED().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(earningsType);

            if (head != null) {
                String[] headers = head.split("\\|");
                data = (List<String>) ((Map) response.getDAT().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(earningsType);

                Map<String, String> dataMap;

                for (String d : data) {
                    List<String> dataItems = new ArrayList(Arrays.asList(d.split("\\|")));

                    while (dataItems.size() < headers.length) {
                        dataItems.add("");
                    }

                    dataMap = new HashMap<String, String>(14);

                    for (int j = 0; j < headers.length; j++) {
                        dataMap.put(headers[j], dataItems.get(j));
                    }

                    String announcedValue = dataMap.get(ANNOUNCED_VALUE);
                    String comparedValue = dataMap.get(COMPARED_VALUE);

                    //calculate change and % change if announced vale and compared value is available
                    calculateChangeData(announcedValue, comparedValue, dataMap);

                    earningsAnnouncementList.add(dataMap);
                }

                rowCount = ((Map) response.getROW().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(earningsType).toString();
                earningsAnnouncement = new EarningsAnnouncement();
                earningsAnnouncement.setEarningsAnnouncements(earningsAnnouncementList);
                earningsAnnouncement.setMaxCount(Integer.parseInt(rowCount));
            }
        }

        return earningsAnnouncement;
    }

    @Override
    public int updateData(DataAccessRequestDTO requestDTO) {
        return 0;
    }

    @Override
    public void initUpdateData() {

    }

    /**
     * Scheduled cache update process. update cache if any custom object is available in the cache
     *
     * @param requestData map of request data
     * @return status
     */
    public int updateCache(List<Map<String, String>> requestData) {
        return -1;
    }


    /**
     * Method to delete earnings caches
     *
     * @param notifications notifications
     * @return status
     */
    public int deleteCache(Object notifications) {
        int status = 0;
        // get all the cache keys that holds earnings data.
        List<String> cacheKeysToDelete = DataAccessUtils.searchCacheKeys(CacheKeyConstant.CACHE_KEY_PREFIX_EARNINGS + IConstants.Delimiter.S_ASTERISK, null);

        if (cacheKeysToDelete != null) {
            // delete one by one
            for (String cacheKey : cacheKeysToDelete) {
//                cacheManager.delete(cacheKey);
                status++;
            }
        }
        return status;
    }

    /*========================================Private Methods===========================================================*/

    /**
     * Handles the earnings request and get the relevant data from socket or cache
     *
     * @param requestData         User request data map
     * @param isJasonResponse     flag to indicate that the client need the jason response
     * @return EarningsAnnouncement object with earnings data
     */
    private EarningsAnnouncement getEarningsAnnouncementData(Map<String, String> requestData, boolean isJasonResponse) {
        EarningsAnnouncement earningsAnnouncement;
        String request = this.requestGenerator.generateRequest(requestData, IConstants.RequestDataType.EARNINGS);
        String earningsType = requestData.get(IConstants.MIXDataField.SCDT);

        int timeout = DataAccessUtils.getRequestTimeout(requestData);

        //Get processed socket response
        earningsAnnouncement = getSocketResponse(request, earningsType, timeout, isJasonResponse);

        return earningsAnnouncement;
    }

    /**
     * Calculate change and % change using annouced value and compared value
     *
     * @param announcedValue announced value
     * @param comparedValue  compared value
     * @param dataMap        data map
     */
    private void calculateChangeData(String announcedValue, String comparedValue, Map<String, String> dataMap) {
        if (announcedValue != null && comparedValue != null && !announcedValue.isEmpty() && !comparedValue.isEmpty()) {
            double annValue = Double.parseDouble(announcedValue);
            double comValue = Double.parseDouble(comparedValue);
            double change = annValue - comValue;
            double perChange = 100;

            if (comValue != 0) {
                perChange = (change * 100) / Math.abs(comValue);
            }
            dataMap.put(CHANGE, Double.toString(change));
            dataMap.put(PER_CHANGE, Double.toString(perChange));
        } else {
            dataMap.put(CHANGE, IConstants.NA_VAL);
            dataMap.put(PER_CHANGE, IConstants.NA_VAL);
        }
    }

    public void setHibernateDaoFactory(DAOFactory hibernateDaoFactory) {
        this.daoFactory = hibernateDaoFactory;
    }

    public int insertAlerts(Object alerts) {
        List<EarningNotification> earningNotification = (List<EarningNotification>)alerts;
        return this.daoFactory.getEarningAnnouncementDAO().insertEarningNotifications(earningNotification);
    }
}
