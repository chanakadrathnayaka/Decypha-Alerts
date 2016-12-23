package com.dfn.alerts.utils;

import com.dfn.alerts.constants.IConstants;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This Class contains utility functions for formatting data.
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/5/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormatUtils {

    private static final String REG_EXP_CONSECUTIVE_COMMA = ",,+";
    private static final String REG_EXP_MATCH_HTML = "</?[^>]+(>|$)";
    private static final String REG_EXP_LEADING_TRAILING_COMMA = "(^,)|(,$)";
    private static final String COMMA = ",";
    private static final String EMPTY_STRING = "";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE_UTC = "UTC";


    public static final Logger LOG = LogManager.getLogger(FormatUtils.class);

    /**
     * Utility method to get quoted string from a given list of string
     * @param dataList list of string
     * @param toUpper indicator whether to set uppercase or not
     * @return String generated string
     */
    public static String getQuotedArrayString(List<String> dataList, boolean toUpper) {
        StringBuilder result;
        if (dataList != null) {
            Iterator it = dataList.iterator();
            String tmpStr;
            result = new StringBuilder();
            while (it.hasNext()) {
                tmpStr = (String) it.next();
                result.append("'");
                if (toUpper) {
                    result.append(tmpStr.toUpperCase());
                } else {
                    result.append(tmpStr);
                }
                result.append("'");
                if (it.hasNext()) {
                    result.append(",");
                }
            }
            return result.toString();
        }
        return null;
    }

    /**
     * get key set of a map as a comma separated string
     *
     * @param map     map with a <String> key
     * @param toUpper to upper case or not
     * @return comma separated key string
     */
    public static String getKeySetAsCommaSeparatedString(Map<String, ?> map, Boolean toUpper) {
        StringBuilder commaSeparatedString = new StringBuilder();
        Set keySet = map.keySet();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
            if (toUpper) {
                temp = temp.toUpperCase();
            }
            commaSeparatedString.append(temp);
            if (iterator.hasNext()) {
                commaSeparatedString.append(Character.toString(IConstants.Delimiter.COMMA));
            }
        }
        return commaSeparatedString.toString();
    }

    /**
     * Helper method to create DecimalFormat for given depth
     * @param dep number of decimal places
     * @return DecimalFormat
     */
    private static DecimalFormat getDecimalFormat(int dep) {
        StringBuilder df = new StringBuilder("0");
        if (dep > 0) {
            df.append(".");

            for (int i = 0; i < dep; i++) {
                df.append("0");
            }
        }
        return new DecimalFormat(df.toString());
    }

    /**
     * Utility Method to format given Double value to given number of decimal places
     * @param value Double value to be format
     * @param decimalPlaces no of decimal places
     * @return String formatted value
     */
    public static String getDouble(Double value, int decimalPlaces) {
        if (value != null) {
            return getDecimalFormat(decimalPlaces).format(value);
        }
        return IConstants.NA_VAL;
    }

    /**
     * Utility Method to format given Float value to given number of decimal places
     * @param value Float value to be format
     * @param decimalPlaces no of decimal places
     * @return String formatted value
     */
    public static String getFloat(Float value, int decimalPlaces) {
        if (value != null) {
            return getDecimalFormat(decimalPlaces).format(value);
        }
        return IConstants.NA_VAL;
    }

    /**
     * Utility method to remove all the consecutive commas (two or more) and leading and trailing commas from a given string
     *
     * @param stringToFormat String to be formatted
     * @return String formatted string
     */
    public static String removeConsecutiveCommas(String stringToFormat) {
        String result = stringToFormat.replaceAll(REG_EXP_CONSECUTIVE_COMMA, COMMA).replaceAll(REG_EXP_LEADING_TRAILING_COMMA, EMPTY_STRING);
        return result.trim();
    }


    /**
     *  get current UTC time
     * @return Date : current UTC time
     */
    public static Date GetUTCDateTimeAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));

        String strDate = sdf.format(new Date());
        Date dateToReturn = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try
        {
            dateToReturn = dateFormat.parse(strDate);
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        return dateToReturn;
    }

    public static String getCurrentDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat(IConstants.DateFormats.FORMAT2);
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));
        return sdf.format(calendar.getTime());
    }

    /**
     * Remove all HTML elements.
     *
     * @param htmlText html text
     * @return text without html tags
     */
    public static String removeHtmlElements(String htmlText){
        return htmlText.replaceAll(REG_EXP_MATCH_HTML, IConstants.EMPTY);
    }

}
