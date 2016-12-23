package com.dfn.alerts.utils;

import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: lasanthak
 * Date: 3/8/13
 * Time: 4:37 PM
 */
@SuppressWarnings("unused")
public class CommonUtils {

    private static final Logger LOG = LogManager.getLogger(CommonUtils.class);
    public static final String BONDS = "BONDS";
    public static final String DEFAULT_TIME_ZONE = "+0000";
    public static final String DEFAULT_TIME_ZONE_HOURS = "00";
    public static final String DEFAULT_TIME_ZONE_MINUTES = "00";
    public static final String TIME_ZONE_SEPARATOR = "\\.";
    public static final String CONST_PLUS = "+";
    public static final String CONST_MINUS = "-";
    public static final String CONST_DOT = ".";
    public static final String CONST_ZERO = "0";
    public static final int SIXTY_SECONDS = 60;
    public static final String IST = "IST";
    private static final String DEFAULT_DATE = "00-JAN-0000";
    private static final String GMT = "GMT";

    private static final String LOG_PREFIX = " :: CommonUtils :: ";

    //Prevent initializing
    private CommonUtils() {
    }

    private static final int MILLIONS_FACTOR = 1000000;

    public static String getUniqueSymbol(String symbolCode, String exchangeCode) {

        if (symbolCode == null || exchangeCode == null) {
            return null;
        }

        return exchangeCode + IConstants.Delimiter.TILDE + symbolCode;
    }

    /**
     * Method to get no of month for a requested period
     *
     * @param periodType PERIOD TYPE
     * @return no of months
     */
    public static int getMonthCountForPeriod(String periodType) {
        int noOfMonths = 0;
        try {
            IConstants.PeriodTypes period = IConstants.PeriodTypes.valueOf(periodType);
            switch (period) {
                case ONE_MONTH: {
                    noOfMonths = IConstants.TimePeriodConstants.ONE_MONTH;
                    break;
                }
                case THREE_MONTH: {
                    noOfMonths = IConstants.TimePeriodConstants.THREE_MONTHS;
                    break;
                }
                case SIX_MONTH: {
                    noOfMonths = IConstants.TimePeriodConstants.SIX_MONTHS;
                    break;
                }
                case ONE_YEAR: {
                    noOfMonths = IConstants.TimePeriodConstants.ONE_YEAR;
                    break;
                }
                case TWO_YEAR: {
                    noOfMonths = IConstants.TimePeriodConstants.TWO_YEARS;
                    break;
                }
                default: {
                    noOfMonths = IConstants.TimePeriodConstants.DEFAULT;
                    break;
                }

            }
        } catch (IllegalArgumentException iae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Incorrect Period Type " + iae.getCause(), iae);
            }
        }

        return noOfMonths;
    }

    /**
     * Get language map from formatted language string
     *
     * @param description language specific description string in |LAN:DESC|LAN:DES.. format
     * @return Description 'Map<LANG,desc>'
     */
    public static Map<String, String> getLanguageDescriptionMap(String description) {
        Map<String, String> descriptionMap = null;
        String token;
        if (description != null && description.contains(Character.toString(IConstants.Delimiter.VL))) {
            descriptionMap = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(description, Character.toString(IConstants.Delimiter.VL));
            while (strTkn.hasMoreTokens()) {
                token = strTkn.nextToken();
                String[] keyVal = token.split(":");

                if (keyVal.length >= 2) {
                    descriptionMap.put(keyVal[0].toUpperCase(), token.substring(keyVal[0].length() + 1));
                }
            }
        } else if (description != null) {
            descriptionMap = new HashMap<String, String>(4);
            descriptionMap.put("EN", description);
        }
        return descriptionMap;
    }

    /**
     * format date string into Date format - yyyyMMddHHmmSS
     *
     * @param dateStr date string
     * @return Date
     */
    public static Date formatDate(String dateStr) {
        return formatDate(dateStr, IConstants.DateFormats.FORMAT12);
    }

    /**
     * formatDate
     *
     * @param dateStr   date string
     * @param formatter date format
     * @return Date
     */
    public static Date formatDate(String dateStr, String formatter){
        Date date = null;
        if (!isNullOrEmptyString(dateStr) && !isNullOrEmptyString(formatter)) {
            DateFormat dateFormat = new SimpleDateFormat(formatter);
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "formatDate " + e.getMessage());
                }
            }
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        String dateStr = "";
        if (date != null) {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            dateStr = formatter.format(date);
        }
        return dateStr;
    }

    /**
     * converts input date string to required output date string format
     * @param inputDate input
     * @param inputFormatString current date format
     * @param requiredFormatString required date format
     * @return formatted date string
     */
    public static String changeDateFormat(String inputDate, String inputFormatString, String requiredFormatString) {
        String dateStr = IConstants.NA_VAL;
        if(!isNullOrEmptyString(inputDate) && !isNullOrEmptyString(inputFormatString) && !isNullOrEmptyString(requiredFormatString)){

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputFormatString);
            SimpleDateFormat outputDate = new SimpleDateFormat(requiredFormatString);
            try {
                dateStr  = outputDate.format(inputFormat.parse(inputDate));
            } catch (ParseException e) {
                // todo LOG
                dateStr =  IConstants.NA_VAL;
            }
        }
        return dateStr;
    }

    /**
     * converts input Date object to required output date string format
     * @param inputDate input
     * @param inputFormatString current date format
     * @param requiredFormatString required date format
     * @return formatted date string
     */
    public static Date changeDateFormat(Date inputDate, String inputFormatString, String requiredFormatString) {
        Date formatted = null;
        if(inputDate != null && !isNullOrEmptyString(inputFormatString) && !isNullOrEmptyString(requiredFormatString)){

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputFormatString);
            SimpleDateFormat outputDate = new SimpleDateFormat(requiredFormatString);
            try {
                String formattedStr  = inputFormat.format(inputDate);
                formatted = outputDate.parse(formattedStr);
            } catch (ParseException e) {
                formatted =  null;
            }
        }
        return formatted;
    }

    public static Date convertToDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS.SSS");
        DateTime dt = formatter.parseDateTime(dateStr);
        return dt.toDate();
    }

    /**
     * Convert string date to java.sql.Date
     *
     * @param dateStr date string
     * @param format  date format
     * @return
     */
    public static java.sql.Date convertToSqlDate(String dateStr, String format) {
        java.sql.Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = new java.sql.Date(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return date;
    }

    public static String removeLast(String str) {


        if (str == null || str.length() < 0) {
            return null;
        }

        if (!str.endsWith(",")) {

            return str;
        }

        return str.substring(0, str.length() - 1);
    }

    public static double getValueInMillions(double value) {
        return value / MILLIONS_FACTOR;
    }

    public static double getBaseValueFormMillions(double value) {
        return value * MILLIONS_FACTOR;
    }

    /**
     * sort map by values
     *
     * @param unsortedMap Map  unsorted
     * @return Map sorted  by values
     */
    @SuppressWarnings("unchecked")
    public static Map sortMapByValues(Map unsortedMap, Comparator comparator) {
        Map sortedMap = new LinkedHashMap();
        if (unsortedMap != null && !unsortedMap.isEmpty()) {
            List<Map.Entry> list = new LinkedList<Map.Entry>(unsortedMap.entrySet());

            // sort list based on comparator
            Collections.sort(list, comparator);

            // put sorted list into map again
            //LinkedHashMap make sure order in which keys were inserted
            for (Map.Entry entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return sortedMap;

    }

    /**
     * get comma separated String from a String set
     *
     * @param set Set<String>
     * @return String
     */
    public static String getCommaSeperatedStringFromSet(Set<String> set) {
        return getCommaSeparatedStringFromCollection(set);
    }

    /**
     * get comma separated String from a String set
     *
     * @param list list<String>
     * @return String
     */
    public static String getCommaSeperatedStringFromList(Collection<String> list) {
        return getCommaSeparatedStringFromCollection(list);
    }

    /**
     * get comma separated String from a String set
     *
     * @param list list<String>
     * @return String
     */
    public static String getCommaSeparatedStringFromCollection(Collection<String> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (list != null && !list.isEmpty()) {
            for (String s : list) {
                if (!StringUtils.isBlank(s)) {
                    sb.append(s).append((i != list.size() - 1) ? IConstants.Delimiter.COMMA : "");
                }
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * Utility method to get gicsL1Code,gicsL2Code,gicsL3Code from gicsL4Code
     *
     * @param gicsL4Code gicsL4Code
     * @param type       required gics code type  [1,2,3]
     * @return gics code
     */
    public static String getGicsCodeFromGicsL4(String gicsL4Code, int type) {
        String gicsCode = null;
        if (isValidGics(gicsL4Code, type)) {
            gicsCode = gicsL4Code.substring(0, 2 * type);
        }
        return gicsCode;
    }

    /**
     * Validate gics L4 code and required type
     *
     * @param gicsL4Code gicsL4Code
     * @param type       required gics code type  [1,2,3]
     * @return GICS 1/2/3
     */
    private static boolean isValidGics(String gicsL4Code, int type) {
        return (gicsL4Code != null && !gicsL4Code.isEmpty() && gicsL4Code.length() == 8 && 0 < type && type < 4);
    }


    public static String getJobSchedulerID(String serverUrl) {

        Calendar c = Calendar.getInstance();
        StringBuilder jobSchedulerID = new StringBuilder();
        jobSchedulerID.append(serverUrl);
        jobSchedulerID.append(IConstants.Delimiter.UNDERSCORE);
        jobSchedulerID.append(c.getTimeInMillis());
        jobSchedulerID.append(IConstants.Delimiter.UNDERSCORE);
        jobSchedulerID.append(RandomSequenceGenerator.generateHexRandomSequence(8));

        return jobSchedulerID.toString();
    }

    /**
     * Method to get corrected timezone after applying timezone adjustment
     *
     * @param timezoneOffset
     * @param adjustedTimezoneOffset
     * @return
     */
    public static String getCorrectedTimezoneOffset(String timezoneOffset, String adjustedTimezoneOffset) {
        String formattedOffset = formatTimezoneOffset(timezoneOffset);
        String newOffset = formattedOffset;
        try {
            if (formattedOffset != null && !formattedOffset.trim().isEmpty() && formattedOffset.trim().length() >= 5
                    && adjustedTimezoneOffset != null && !adjustedTimezoneOffset.trim().isEmpty()) {
                String formattedAdjustedOffset = formatTimezoneOffset(adjustedTimezoneOffset);
                float sign = (formattedOffset.trim().startsWith(CONST_MINUS)) ? -1 : 1;
                float hours = sign * Integer.parseInt(formattedOffset.trim().substring(1, 3));
                float mins = sign * (Float.parseFloat(formattedOffset.trim().substring(3, 5)) / SIXTY_SECONDS);
                float adjustedHours = 0;
                float adjustedMins = 0;
                if (formattedAdjustedOffset.trim().length() <= 3) {
                    adjustedHours = Integer.parseInt(formattedAdjustedOffset);
                } else {
                    if (formattedAdjustedOffset.trim().startsWith(CONST_MINUS)) {
                        adjustedHours = -1 * Integer.parseInt(formattedAdjustedOffset.trim().substring(1, 3));
                        adjustedMins = -1 * (Float.parseFloat(formattedAdjustedOffset.trim().substring(3, formattedAdjustedOffset.trim().length())) / SIXTY_SECONDS);
                    } else if (formattedAdjustedOffset.trim().startsWith(CONST_PLUS)) {
                        adjustedHours = Integer.parseInt(formattedAdjustedOffset.trim().substring(1, 3));
                        adjustedMins = Float.parseFloat(formattedAdjustedOffset.trim().substring(3, formattedAdjustedOffset.trim().length())) / SIXTY_SECONDS;
                    } else {
                        adjustedHours = Integer.parseInt(formattedAdjustedOffset.trim().substring(0, 2));
                        adjustedMins = Float.parseFloat(formattedAdjustedOffset.trim().substring(2, formattedAdjustedOffset.trim().length())) / SIXTY_SECONDS;
                    }
                }
                if (adjustedHours != 0 || adjustedMins != 0) {
                    float newTimezone = hours + mins + adjustedHours + adjustedMins;
                    Double minutesAfterAdj = (newTimezone - Math.floor(newTimezone)) * SIXTY_SECONDS;
                    int hoursAfterAdj = ((Double) Math.floor(newTimezone)).intValue();
                    int signAfterAdj = (newTimezone > 0) ? 1 : -1;
                    String prefix = ((hoursAfterAdj * signAfterAdj) < 10) ? CONST_ZERO : "";
                    String minuteStr = (minutesAfterAdj == 0) ? DEFAULT_TIME_ZONE_MINUTES : Integer.toString(minutesAfterAdj.intValue() * signAfterAdj);
                    String signStr = (signAfterAdj > 0) ? CONST_PLUS : CONST_MINUS;

                    newOffset = signStr + prefix + Integer.toString(((Double) Math.floor(newTimezone)).intValue() * signAfterAdj) + minuteStr;
                }
            }
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
        }
        return newOffset;
    }

    /**
     * Method to format timezone offset to format of "(+/-)HHMM" :: [1 digit - sign][2digit - hours][ 2 digit - minutes]
     *
     * @param offset
     * @return
     */
    public static String formatTimezoneOffset(String offset) {
        String modifiedOffset = DEFAULT_TIME_ZONE;
        String sign = CONST_PLUS;
        if (offset != null) {
            if (offset.startsWith(CONST_MINUS)) {
                sign = CONST_MINUS;
            }
            if (offset.startsWith(CONST_MINUS) || offset.startsWith(CONST_PLUS)) {
                offset = offset.substring(1, offset.length());
            }

            if (!offset.contains(CONST_DOT) && (offset.length() >= 4)) {
                modifiedOffset = sign + offset;
            } else if (offset.contains(CONST_DOT) && offset.length() >= 5) {
                modifiedOffset = sign + offset.replace(CONST_DOT, "");
            } else if (offset.contains(CONST_DOT)) {
                String[] arr = offset.split(TIME_ZONE_SEPARATOR);
                String hr = (arr[0].length() == 1) ? CONST_ZERO + arr[0] : (arr[0].length() == 0) ? DEFAULT_TIME_ZONE_HOURS : arr[0];
                String min = (arr[1].length() == 1) ? arr[1] + CONST_ZERO : (arr[1].length() == 0) ? DEFAULT_TIME_ZONE_MINUTES : arr[1];
                modifiedOffset = sign + hr + min;
            } else {
                String hr = (offset.length() > 2) ? offset.substring(0, 2) : offset;
                hr = (hr.length() == 1) ? CONST_ZERO + hr : (hr.length() == 0) ? DEFAULT_TIME_ZONE_HOURS : hr;
                String min = (offset.length() > 2) ? offset.substring(2, offset.length()) : DEFAULT_TIME_ZONE_MINUTES;
                min = (min.length() == 1) ? min + CONST_ZERO : (min.length() == 0) ? DEFAULT_TIME_ZONE_MINUTES : min;
                modifiedOffset = sign + hr + min;
            }
        }
        return modifiedOffset;
    }

    public static Timestamp getTimeStamp(String dateTime) {
        Timestamp timestamp = null;
        Date date = CommonUtils.formatDate(dateTime);
        if (date != null) {
            timestamp = new Timestamp(date.getTime());
        }
        return timestamp;
    }

    /**
     * Add commas before and after the string
     *
     * @param rawStr raw string
     * @return comma added string
     */
    public static String addCommasToString(String rawStr, int maxLength) {
        String returnStr = null;
        String comma = Character.toString(IConstants.Delimiter.COMMA);
        if (!rawStr.isEmpty()) {
            returnStr = comma + processString(rawStr, maxLength) + comma;
        }
        return returnStr;
    }

    public static final int ZERO_INDEX = 0;

    /**
     * Process String so that maximum no of characters allows for the field is '200' characters
     *
     * @param dataVal data value
     * @return get string value
     */
    public static String processString(String dataVal, int maxLength) {
        String returnStr = null;
        if (dataVal != null && !dataVal.isEmpty()) {
            returnStr = dataVal.substring(ZERO_INDEX, Math.min(dataVal.length(), maxLength));
        }
        return returnStr;
    }

    /**
     * checks string for null or empty
     * @param value string value
     * @return true if null or empty, false otherwise
     */

    public static boolean isNullOrEmptyString(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * checks string for email or not
     * @param value string value
     * @return true if email, false otherwise
     */
    public static boolean isEmailAddress(String value){
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * checks string for valid username, contains only characters,numbers,underscore,dot or "-"
     * @param value string value
     * @return true if valid,false otherwise
     */
    public static boolean isUsernameValid(String value){
        Pattern p = Pattern.compile("^[\\w\\.\\-]+$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * This method will return a date in requested String format
     * Default Timezone is gmt
     *
     * @param date   Date
     * @param format Format
     * @return String date
     */
    public static String convertDateToStringFormat(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone(IST));
        return df.format(date.getTime());
    }

    /**
     * Sort String Objects in a List<Map<String, Object>>
     *
     * @param mapList List of Maps
     * @param sf      sort field
     */
    public static void setStringOrder(List<Map<String, Object>> mapList, final String sf) {
        Collections.sort(mapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String a = (String) (o1.get(sf));
                String b = (String) (o2.get(sf));
                return a.compareTo(b);
            }
        });
    }

    /**
     * Sort Dates when Date objects are in List<Map<String, Object>>
     *
     * @param mapList    List of Maps
     * @param sf         sort field
     * @param dateFormat String format
     */
    public static void setDateOrder(List<Map<String, Object>> mapList, final String sf, String dateFormat) {
        final SimpleDateFormat defaultFormatter = new SimpleDateFormat(IConstants.DateFormats.FORMAT1);
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Collections.sort(mapList, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Date a = null;
                Date b = null;
                try {
                    a = defaultFormatter.parse(DEFAULT_DATE);
                    b = defaultFormatter.parse(DEFAULT_DATE);

                    String date = (String) (o1.get(sf));
                    if (date != null && !date.equalsIgnoreCase(IConstants.NA_VAL)) {
                        a = formatter.parse((String) (o1.get(sf)));
                    }

                    date = (String) (o2.get(sf));
                    if (date != null && !date.equalsIgnoreCase(IConstants.NA_VAL)) {
                        b = formatter.parse((String) (o2.get(sf)));
                    }

                } catch (ParseException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + e.getMessage());
                    }
                }

                return a.compareTo(b);

            }
        });
    }

    /**
     * Sort numeric Objects in a List<Map<String, Object>> in reverse order
     *
     * @param mapList List of Maps
     * @param sf      sort field
     */
    public static void setNumericOrder(List<Map<String, Object>> mapList, final String sf) {
        Collections.sort(mapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double a = (Double) (o1.get(sf));
                Double b = (Double) (o2.get(sf));
                return a.compareTo(b);
            }
        });
    }

    /**
     * Sort numeric Objects in a List<Map<String, Object>> in reverse order
     *
     * @param mapList List of Maps
     * @param sf      sort field
     */
    public static void setIntegerOrder(List<Map<String, Object>> mapList, final String sf) {
        Collections.sort(mapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer a = (Integer) (o1.get(sf));
                Integer b = (Integer) (o2.get(sf));
                return a.compareTo(b);
            }
        });
    }

    /**
     * convert given date to gmt date
     * @param date Date
     * @return gmt Date
     */
    public static Date getGMTDate(Date date){
        Date gmtDate = null;

        if(date!=null) {
            TimeZone.setDefault(TimeZone.getTimeZone(GMT));
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.setTime(date);
            gmtDate = cal.getTime();
        }

        return gmtDate;
    }
}
