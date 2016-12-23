package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.IConstants;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.CharUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/20/12
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientRequestParser extends CharUtils {

    private static final List<String> MIX_COLLECTION_FIELDS = new ArrayList<String>();

    private static final Logger log  = LogManager.getLogger(ClientRequestParser.class);

    private static AtomicInteger count = new AtomicInteger(1);


    static {
        MIX_COLLECTION_FIELDS.add(IConstants.MIXDataField.IFLD);
        MIX_COLLECTION_FIELDS.add(IConstants.MIXDataField.XFLD);
        MIX_COLLECTION_FIELDS.add(IConstants.MIXDataField.ST);
        MIX_COLLECTION_FIELDS.add(IConstants.MIXDataField.UE);
    }


    public static String handleRequest(Map<String, String[]> requestData) {
        String requestSting = processRequest(requestData);
        JSONObject json;
        Map<Object, Object> reqMap = new LinkedHashMap<Object, Object>();

        try {

            json = JSONObject.fromObject(requestSting);
        } catch (Exception e) {
            json = null;
        }

        if (count.get() == Integer.MAX_VALUE) {
            count = new AtomicInteger(1);
        }

        reqMap.put(IConstants.SocketServiceTags.SEQ, "" + count.getAndIncrement());
        reqMap.put(IConstants.SocketServiceTags.VER, "2");
        if (json != null){
            reqMap.put(IConstants.SocketServiceTags.REQ, json);
        }
        else{
            reqMap.put(IConstants.SocketServiceTags.REQ, requestSting);
        }

        String jsonStr = JSONObject.fromObject(reqMap).toString();
        int jsonLength = jsonStr.getBytes().length;// work in linux machines
        return  new StringBuilder().append(jsonLength).append(jsonStr).toString();
    }

    public static String processRequest(Map<String, String[]> requestMap) {
        int type = Integer.parseInt(requestMap.get(IConstants.MIXDataField.RT)[0]);
        List<String> localCollectionFields = new ArrayList<String>();

        switch (type) {
            case IConstants.Symbol.SYMBOL_DATA:
                localCollectionFields.add(IConstants.MIXDataField.S);
                break;
            case IConstants.Symbol.PARTIAL_SYMBOL_SEARCH:
                requestMap.remove("req");
                localCollectionFields.add(IConstants.MIXDataField.S);
                localCollectionFields.add(IConstants.MIXDataField.SYMT);
                break;
            case IConstants.News.NEWS_KEYWORDS_REQUEST:
                localCollectionFields.add(IConstants.MIXDataField.S);
                break;
            case IConstants.FileDownloadRequests.DICTIONARY:
                break;
            case IConstants.MarketScreener.MARKET_SECREENER_SYMBOL_DATA_REQUEST:
                String[] value = requestMap.get(IConstants.MIXDataField.OPTNL);
                if (value != null && value.length > 0 && value[0] != null && value[0].length() > 0) {
                    if (!(value[0].contains(toString(IConstants.Delimiter.COMMA)))) {
                        value[0] = new StringBuilder().append(value[0]).append(toString(IConstants.Delimiter.COMMA)).toString();
                        requestMap.remove(IConstants.MIXDataField.OPTNL);
                        requestMap.put(IConstants.MIXDataField.OPTNL, value);
                    }
                }
                break;
            case IConstants.Alerts.SET_PRICE_AND_NEWS_ALERTS_REQUEST:
                value = requestMap.get(IConstants.MIXDataField.SRC);
                if (value != null && value.length > 0 && value[0] != null && value[0].length() > 0) {
                    if (!(value[0].contains(String.valueOf(IConstants.Delimiter.COMMA)))) {
                        value[0] = new StringBuilder().append(value[0]).append(String.valueOf(IConstants.Delimiter.COMMA)).toString();
                        requestMap.remove(IConstants.MIXDataField.SRC);
                        requestMap.put(IConstants.MIXDataField.SRC, value);
                    }
                }

                break;
        }

        return JSONObject.fromObject(buildParameterMap(requestMap, localCollectionFields, type)).toString();
    }

    private static Map<String, Object> buildParameterMap(Map<String, String[]> reqMap, List<String> localCollectionFields, int requestType) {
        Map<String, Object> paraMap = new LinkedHashMap<String, Object>();
        Set<String> reqKeys = reqMap.keySet();

        for (String key : reqKeys) {
            String parameter = reqMap.get(key)[0];

            if (parameter == null || parameter.length() == 0) {
                continue;
            }

            if(parameter.contains(toString(IConstants.Delimiter.CHAR30)) && parameter.contains(toString(IConstants.Delimiter.CHAR29))) {
                paraMap.put(key, buildGroupMapForSeparators(parameter, IConstants.Delimiter.VL, IConstants.Delimiter.COLON));
            } else if (parameter.contains(toString(IConstants.Delimiter.COMMA)) && parameter.contains(toString(IConstants.Delimiter.VL))
                    && parameter.contains(toString(IConstants.Delimiter.COLON))) {
                paraMap.put(key, buildMapForSeparators(split(parameter, toString(IConstants.Delimiter.COMMA)),
                        IConstants.Delimiter.VL, IConstants.Delimiter.COLON));
            } else if (parameter.contains(toString(IConstants.Delimiter.COMMA)) && parameter.contains(toString(IConstants.Delimiter.COLON)) && parameter.contains(toString(IConstants.Delimiter.LCB)) && parameter.contains(toString(IConstants.Delimiter.LSB))) {
                log.debug("%%%%%COLLECTION KEY " + key + " PARAMETER " + parameter);

                JSONObject job = JSONObject.fromObject(parameter);
                log.debug("%%%%%COLLECTION " + job);
                paraMap.put(key, job);
                log.debug("DONE");
            } else if (parameter.contains(toString(IConstants.Delimiter.COMMA))) {
                if (key.equals(IConstants.MIXDataField.ACR)) {
                    log.debug("Key : " + key + " Param : " + parameter);
                    paraMap.put(key, parameter);
                } else {
                    paraMap.put(key, split(parameter, toString(IConstants.Delimiter.COMMA)));
                }
            } else {
                if (MIX_COLLECTION_FIELDS.contains(key) || localCollectionFields.contains(key)) {
                    paraMap.put(key, new String[]{parameter});
                } else {
                    paraMap.put(key, parameter);
                }
            }
        }
        return paraMap;
    }

    private static Object[] buildMapForSeparators(String[] strList, char firstDelimiter, char secondDelimiter) {
        int size = strList.length;
        Object[] objArray = new Object[size];
        String firstDelimiterStr = new StringBuilder("\\").append(firstDelimiter).toString();

        for (int i = 0; i < size; i++) {
            Map<String, Object> paraMap = new LinkedHashMap<String, Object>();
            String str = strList[i];
            String[] firstLevelArray = split(str, firstDelimiterStr);
            for (String keyValuePair : firstLevelArray) {
                String[] secondLevelArray = split(keyValuePair, toString(secondDelimiter));
                if (secondLevelArray.length > 1) {
                    paraMap.put(secondLevelArray[0], secondLevelArray[1]);
                } else {
                    paraMap.put(secondLevelArray[0], "");
                }
            }
            objArray[i] = paraMap;
        }

        return objArray;
    }

    private static Object[] buildGroupMapForSeparators(String parameter, char firstDelimiter, char secondDelimiter) {
        String[] groups = split(parameter, toString(IConstants.Delimiter.CHAR29));
        int size = groups.length;
        Object[] objArray = new Object[size];
        String[] subObjArray = null;


        for (int i = 0; i < size; i++) {
            Map<String, Object> paraMap = new LinkedHashMap<String, Object>();
            String str = groups[i];
            String[] firstLevelArray = split(str, toString(IConstants.Delimiter.CHAR30));
            for (String keyValuePair : firstLevelArray) {
                String[] secondLevelArray = split(keyValuePair, toString(IConstants.Delimiter.CHAR28));
                if (secondLevelArray.length > 1) {
                    if(secondLevelArray[1].contains(toString(IConstants.Delimiter.VL))  && secondLevelArray[1].contains(toString(IConstants.Delimiter.COLON))) {
                        subObjArray = split(secondLevelArray[1], toString(IConstants.Delimiter.COMMA));
                        paraMap.put(secondLevelArray[0], buildMapForSeparators(subObjArray, IConstants.Delimiter.VL, IConstants.Delimiter.COLON));
                    } else {
                        paraMap.put(secondLevelArray[0], secondLevelArray[1]);
                    }
                } else {
                    paraMap.put(secondLevelArray[0], "");
                }
            }
            objArray[i] = paraMap;
        }

        return objArray;
    }

    public static String[] split(String stringToSplit, String delimiter) {
        Pattern pattern = Pattern.compile(delimiter);
        return pattern.split(stringToSplit);
    }
}
