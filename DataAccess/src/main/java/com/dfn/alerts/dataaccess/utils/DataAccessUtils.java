package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.api.ICacheManager;
import com.dfn.alerts.dataaccess.impl.BigMemoryCacheImpl;
import com.dfn.alerts.exception.ApplicationException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 4/18/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataAccessUtils {

    private static final Logger LOG = LogManager.getLogger(DataAccessUtils.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE_UTC = "UTC";

    private static final int DEFAULT_ARRAY_SIZE = 1;
    private static final int DEFAULT_MAP_SIZE = 10;
    private static final int DEFAULT_REQ_TIME_OUT = -1;

    private DataAccessUtils(){}

    /**
     * process given company information type and returns a list of data object
     *
     * @param hedObj          head section of the response
     * @param dataObj         data section of the response
     * @param infoType company information type
     * @return List of data objects of requested information type
     */
    public static List<Map<String, String>> processInfoType(Map hedObj, Map dataObj, String infoType) {

        List<Map<String, String>> dataList = null;
        String[] headers;
        Map<String, String> dataMap;
        List<String> data;
        List<String> dataItems;
        String head;

        if (hedObj != null && dataObj!= null) {
            head = (String) hedObj.get(infoType);
            data = (List) dataObj.get(infoType);

            if (head != null && data != null && !data.isEmpty()) {
                headers = head.split("\\|");
                // create new data list
                dataList = new ArrayList<Map<String, String>>(DEFAULT_ARRAY_SIZE);

                // iterate through each data record and add to the data list
                for (String d : data) {
                    dataItems = new ArrayList(Arrays.asList(d.split("\\|")));

                    while (dataItems.size() < headers.length) {
                        dataItems.add("");
                    }

                    dataMap = new HashMap<String, String>(DEFAULT_MAP_SIZE);
                    // iterate through each header field
                    for (int j = 0; j < headers.length; j++) {
                        dataMap.put(headers[j], dataItems.get(j));
                    }

                    dataList.add(dataMap);
                }
            }
        }
        return dataList;
    }

    /**
     *
     * @param hedObj
     * @param dataObj
     * @param infoType
     * @param keyField
     * @return
     */
    public static Map<String,Map<String, String>> processInfoTypeToMap(Map hedObj, Map dataObj, String infoType, String keyField) {

        Map<String,Map<String, String>> dataList = null;
        String[] headers;
        Map<String, String> dataMap;
        List<String> data;
        List<String> dataItems;
        String head;

        if (hedObj != null && dataObj!= null) {
            head = (String) hedObj.get(infoType);
            data = (List) dataObj.get(infoType);

            if (head != null && data != null && !data.isEmpty()) {
                headers = head.split("\\|");
                // create new data list
                dataList = new HashMap<String,Map<String, String>>(DEFAULT_ARRAY_SIZE);

                // iterate through each data record and add to the data list
                for (String d : data) {
                    dataItems = new ArrayList(Arrays.asList(d.split("\\|")));

                    while (dataItems.size() < headers.length) {
                        dataItems.add("");
                    }

                    dataMap = new HashMap<String, String>(DEFAULT_MAP_SIZE);
                    // iterate through each header field
                    for (int j = 0; j < headers.length; j++) {
                        dataMap.put(headers[j], dataItems.get(j));
                    }
                    if(dataMap.get(keyField)!= null) {
                    dataList.put(dataMap.get(keyField),dataMap);
                    }
                }
            }
        }
        return dataList;
    }

    /**
     * Utility method to search cache keys
     * @param key           key to search from cache keys
     * @param cacheManager  cacheManager to be search from
     * @return  List<String> list of cache keys
     * @throws ApplicationException
     */
    public static List<String> searchCacheKeys(String key, ICacheManager cacheManager)  {

        if(key == null || "".equals(key.trim())){
            return null;
        }

        List<String> returnList = new ArrayList<String>();
        List<String> keyList = null;

        if(cacheManager instanceof BigMemoryCacheImpl) {
            keyList = cacheManager.getKeys();
        }

        if(keyList != null && !keyList.isEmpty()){

            if(key.endsWith("*") && key.startsWith("*")) {

                String keyPre = key.substring(1,key.length()-1);
                for(String s : keyList){
                    if(s.contains(keyPre)){
                        returnList.add(s);
                    }
                }

            }else if(key.endsWith("*")){
                String keyPre = key.substring(0,key.length()-1);
                for(String s : keyList){

                    if(s.startsWith(keyPre)){
                        returnList.add(s);
                    }
                }
            }else if(key.startsWith("*")) {
                String keyPre = key.substring(1,key.length());
                for(String s : keyList){

                    if(s.endsWith(keyPre)){
                        returnList.add(s);
                    }
                }
            }

        }

        return returnList;
    }

    /**
     * Utility method to get timeout from request
     * @param request
     * @return
     */
    public static int getRequestTimeout(Map<String,String> request){
         int timeout = DEFAULT_REQ_TIME_OUT;
         String timeoutStr = request.get(IConstants.CustomDataField.R_TIMEOUT);
         try{
            if( timeoutStr!= null && !timeoutStr.trim().isEmpty()){
                timeout = Integer.parseInt(timeoutStr);
            }
         } catch (NumberFormatException nfe){
            LOG.error("Invalid timeout value" + nfe.getMessage());
         }
        return timeout;
    }

    /**
     *  get current UTC time
     * @return Date : current UTC time
     */
    public static Date getUTCDateTimeAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));

        String strDate = sdf.format(new Date());
        Date dateToReturn = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            dateToReturn = dateFormat.parse(strDate);
        } catch (ParseException e) {
            LOG.error(e.getMessage() , e);
        }
        return dateToReturn;
    }
}
