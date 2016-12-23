package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.CountryIndicatorGroupDTO;
import com.dfn.alerts.beans.CountryIndicatorTypeDTO;
import com.dfn.alerts.beans.CountryIndicatorValuesDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aravindal on 28/05/14.
 */
public class MacroEconomyDBHelper {

    public static final Logger LOG = LogManager.getLogger(MacroEconomyDBHelper.class);

    private static final String SEARCH_COUNTRY_INDICATOR_TYPES = "SELECT * FROM MACRO_ECONOMY_INDICATOR_MASTER ORDER BY "
                                                                            + DBConstants.DatabaseColumns.INDICATOR_CODE;

    private static final String SEARCH_COUNTRY_INDICATOR_GROUP_TYPES = "SELECT * FROM MACRO_ECONOMY_GROUP_MASTER ORDER BY "
                                                                             + DBConstants.DatabaseColumns.INDICATOR_GROUP_ID;

    private static String QUERY_COUNTRY_INDICATOR_SEARCH = null;
    public static final String HELPER_PARAM_01 = "miv.";
    public static final String HELPER_PARAM_02 = "mim.";
    public static final String HELPER_PARAM_03 = " miv ";
    public static final String HELPER_PARAM_04 = " mim ";

    public static String getAllCountryIndicatorTypesQuery(){
        return  SEARCH_COUNTRY_INDICATOR_TYPES;
    }

    public static String getAllCountryIndicatorGroupTypesQuery(){
        return  SEARCH_COUNTRY_INDICATOR_GROUP_TYPES;
    }

    public static String getCountryIndicatorValuesByCountryQuery(){
        String searchQuery;

        if(QUERY_COUNTRY_INDICATOR_SEARCH == null){
            searchQuery = DBConstants.CommonDatabaseParams.QUERY_SELECT +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_CODE + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_VALUE_DATE + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_VALUE + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.PROVIDER + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.IS_FORECAST + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_LAST_REVIEW_DATE + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.SCALE + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.UNIT + IConstants.Delimiter.COMMA +
                    HELPER_PARAM_02 + DBConstants.DatabaseColumns.INDICATOR_GROUP_ID +
                    DBConstants.CommonDatabaseParams.FROM + DBConstants.TablesORACLE.MACRO_ECONOMY_INDICATOR_VALUES +
                    HELPER_PARAM_03 + DBConstants.CommonDatabaseParams.QUERY_LEFT_OUTER_JOIN +
                    DBConstants.TablesORACLE.MACRO_ECONOMY_INDICATOR_MASTER + HELPER_PARAM_04 +
                    DBConstants.CommonDatabaseParams.QUERY_ON + DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN +
                    HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_CODE + DBConstants.CommonDatabaseParams.QUERY_EQUAL +
                    HELPER_PARAM_02 + DBConstants.DatabaseColumns.INDICATOR_CODE + DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE +
                    DBConstants.CommonDatabaseParams.QUERY_WHERE + DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN + HELPER_PARAM_01 +
                    DBConstants.DatabaseColumns.COUNTRY_CODE + DBConstants.CommonDatabaseParams.QUERY_EQUAL +
                    DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK + DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE +
                    DBConstants.CommonDatabaseParams.QUERY_ORDER + HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_CODE +
                    DBConstants.CommonDatabaseParams.QUERY_COMMA + HELPER_PARAM_01 + DBConstants.DatabaseColumns.INDICATOR_VALUE_DATE;

            QUERY_COUNTRY_INDICATOR_SEARCH = searchQuery;
            LOG.debug("<!--COUNTRY_INDICATOR_SEARCH_QUERY | "+searchQuery);
        }else {
            searchQuery = QUERY_COUNTRY_INDICATOR_SEARCH;
        }

        return  searchQuery;
    }


    /**
     * Set Country indicator group column values to CountryIndicatorGroupDTO Object
     *
     * @param resultSet result set
     * @param indicatorGroupDTO group object
     * @param supportedLanguages supported languages
     * @throws SQLException exception
     */
    public static void setCountryIndicatorGroupColumnValues(ResultSet resultSet,
                                                           CountryIndicatorGroupDTO indicatorGroupDTO,
                                                           List<String> supportedLanguages) throws SQLException {

        indicatorGroupDTO.setIndicatorGroupId(resultSet.getInt(DBConstants.DatabaseColumns.INDICATOR_GROUP_ID));

        Map<String,String> indicatorGroupLangDesc =  new HashMap<String, String>();
        for(String lang : supportedLanguages){
            indicatorGroupLangDesc.put(lang.toUpperCase(),resultSet.getString(DBConstants.DatabaseColumns.INDICATOR_GROUP_DESC+
                    IConstants.Delimiter.UNDERSCORE+lang.toUpperCase()));
        }
        indicatorGroupDTO.setIndicatorGroupLangDesc(indicatorGroupLangDesc);
        indicatorGroupDTO.setProviderId(resultSet.getInt(DBConstants.DatabaseColumns.PROVIDER));
    }


    /**
     * Set Country indicator type column values to CountryIndicatorTypeDTO Object
     *
     * @param resultSet result set
     * @param indicatorTypeDTO indicator type object
     * @param supportedLanguages supported languages
     * @throws SQLException exception
     */
    public static void setCountryIndicatorTypeColumnValues(ResultSet resultSet,
                                                           CountryIndicatorTypeDTO indicatorTypeDTO,
                                                           List<String> supportedLanguages) throws SQLException {

        indicatorTypeDTO.setIndicatorCode(resultSet.getString(DBConstants.DatabaseColumns.INDICATOR_CODE));
        indicatorTypeDTO.setIndicatorGroupId(resultSet.getInt(DBConstants.DatabaseColumns.INDICATOR_GROUP_ID));

        Map<String,String> indicatorLangDesc =  new HashMap<String, String>();
        for(String lang : supportedLanguages){
            indicatorLangDesc.put(lang.toUpperCase(),resultSet.getString(DBConstants.DatabaseColumns.INDICATOR_DESC+
                                                                   IConstants.Delimiter.UNDERSCORE+lang.toUpperCase()));
        }
        indicatorTypeDTO.setIndicatorLangDesc(indicatorLangDesc);
    }

    /**
     * Set Country indicator values to CountryIndicatorValuesDTO Object
     *
     * @param resultSet result set
     * @param indicator indicator value object
     * @throws SQLException exception
     */
    public static void setCountryIndicatorColumnValues(ResultSet resultSet,
                                                       CountryIndicatorValuesDTO indicator) throws SQLException{

        indicator.setIndicatorCode(resultSet.getString(DBConstants.DatabaseColumns.INDICATOR_CODE));
        indicator.setIndicatorGroupId(resultSet.getInt(DBConstants.DatabaseColumns.INDICATOR_GROUP_ID));
        indicator.setProviderId(resultSet.getInt(DBConstants.DatabaseColumns.PROVIDER));

        Object[] historyValues = new Object[7];
        historyValues[0] = resultSet.getDate(DBConstants.DatabaseColumns.INDICATOR_VALUE_DATE).getTime()/1000;
        historyValues[1] = resultSet.getDouble(DBConstants.DatabaseColumns.INDICATOR_VALUE);
        historyValues[2] = resultSet.getInt(DBConstants.DatabaseColumns.PROVIDER);
        Date lastReviewDate = resultSet.getDate(DBConstants.DatabaseColumns.INDICATOR_LAST_REVIEW_DATE);
        if(lastReviewDate != null) {
            historyValues[3] = lastReviewDate.getTime() / 1000;
        } else {
            historyValues[3] = null;
        }
        historyValues[4] = resultSet.getInt(DBConstants.DatabaseColumns.IS_FORECAST);
        historyValues[5] = DataFormatter.GetLanguageSpecificDescriptionMap(resultSet.getString(DBConstants.DatabaseColumns.SCALE));
        historyValues[6] = DataFormatter.GetLanguageSpecificDescriptionMap(resultSet.getString(DBConstants.DatabaseColumns.UNIT));

        indicator.setIndicatorValue(historyValues);

        indicator.setIndicatorValueDate(resultSet.getDate(DBConstants.DatabaseColumns.INDICATOR_VALUE_DATE));

    }

}
