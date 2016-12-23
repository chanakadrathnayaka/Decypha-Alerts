package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.KpiDTO;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.tickers.TickerDBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;
import static com.dfn.alerts.constants.DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME;
import static com.dfn.alerts.constants.DBConstants.TablesIMDB.TABLE_KPI_MASTER;
import static com.dfn.alerts.constants.DBConstants.TablesORACLE.TABLE_KPI_DEFINITION_DESC;

/**
 * Created by nipunu on 8/25/2015.
 */
public class KPIDBHelper {

    //region QUERY CONSTANTS

    /**
     * SELECT * FROM KPI_DEFINITION_DESC
     * WHERE LANGUAGE_ID = 'lang'
     */
    public static final String QUERY_LOAD_KPI_DEFINITIONS = QUERY_SELECT_ALL_FROM + TABLE_KPI_DEFINITION_DESC +
            QUERY_WHERE + LANGUAGE_ID + QUERY_EQUAL + QUERY_QUOTE + "#lang#" + QUERY_QUOTE;

    /**
     * SELECT * FROM (
     SELECT
     KPI_VALUE,
     KPI_YEAR
     FROM KPI_MASTER
     WHERE COMPANY_ID = 13337 AND KPI_DEFINITION = 1 AND KPI_PERIOD IN (5,4)
     ORDER BY KPI_YEAR DESC, KPI_PERIOD DESC
     ) WHERE ROWNUM = 1;
     */
    public static final String QUERY_LOAD_NUMBER_OF_EMPLOYEES = QUERY_SELECT_ALL_FROM + QUERY_BRACKET_OPEN + QUERY_SELECT +
            DBConstants.DatabaseColumns.KPI_VALUE + DBConstants.SQL_COMMA + DBConstants.DatabaseColumns.KPI_YEAR + FROM + DBConstants.TablesORACLE.TABLE_KPI_MASTER +
            QUERY_WHERE + DBConstants.DatabaseColumns.COMPANY_ID + QUERY_EQUAL + "#companyId#" + QUERY_AND + DBConstants.DatabaseColumns.KPI_DEFINITION +
            QUERY_EQUAL + "1" + QUERY_AND + DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_IN + QUERY_BRACKET_OPEN + "5,4" + QUERY_BRACKET_CLOSE + QUERY_ORDER +
            DBConstants.DatabaseColumns.KPI_YEAR + QUERY_DESC_ORDER + DBConstants.SQL_COMMA +
            DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_DESC_ORDER +
            QUERY_BRACKET_CLOSE + QUERY_WHERE + DBConstants.DatabaseColumns.ROWNUM + QUERY_EQUAL + "1";

    /**
     * SELECT * FROM KPI_MASTER
     * WHERE COMPANY_ID = companyId AND STATUS = 1
     * ORDER BY KPI_DEFINITION ASC, KPI_YEAR ASC, KPI_PERIOD ASC
     */
    private static final String QUERY_LOAD_COMPANY_KPI = QUERY_SELECT_ALL_FROM + TABLE_KPI_MASTER +
            QUERY_WHERE + COMPANY_ID + QUERY_EQUAL + "#companyId#" + QUERY_AND + QUERY_STATUS_FILTER +

            QUERY_ORDER + DBConstants.DatabaseColumns.KPI_DEFINITION + QUERY_ASC_ORDER + QUERY_COMMA +
            DBConstants.DatabaseColumns.KPI_YEAR + QUERY_ASC_ORDER + QUERY_COMMA +
            DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_ASC_ORDER;

    /*
     * SELECT * FROM KPI_MASTER WHERE COMPANY_ID = companyId AND STATUS = 1  AND (KPI_YEAR <= toYear AND KPI_YEAR >= fromYear)
     * AND (KPI_PERIOD = 5 OR KPI_PERIOD = 4) ORDER BY KPI_DEFINITION ASC ,KPI_YEAR ASC ,KPI_PERIOD ASC
    */
    private static final String QUERY_LOAD_COMPANY_KPI_ON_ANNUAL_BASIS = QUERY_SELECT_ALL_FROM + TABLE_KPI_MASTER +
            QUERY_WHERE + COMPANY_ID + QUERY_EQUAL + "#companyId#" + QUERY_AND + QUERY_STATUS_FILTER +
            QUERY_AND + SQL_BRACKET_OPEN + DBConstants.DatabaseColumns.KPI_YEAR + QUERY_LESS_OR_EQUAL_THAN + "#toYear#" +
            QUERY_AND + DBConstants.DatabaseColumns.KPI_YEAR + QUERY_GREATER_OR_EQUAL_THAN + "#fromYear#" + SQL_BRACKET_CLOSE +
            QUERY_AND + SQL_BRACKET_OPEN + DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_EQUAL + "5" +
            QUERY_OR + DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_EQUAL + "4" + SQL_BRACKET_CLOSE +
            QUERY_ORDER + DBConstants.DatabaseColumns.KPI_DEFINITION + QUERY_ASC_ORDER + QUERY_COMMA +
            DBConstants.DatabaseColumns.KPI_YEAR + QUERY_ASC_ORDER + QUERY_COMMA +
            DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_ASC_ORDER;

    /*
     * SELECT * FROM KPI_MASTER WHERE COMPANY_ID = companyId AND STATUS=1 AND ((KPI_YEAR=toYear AND KPI_PERIOD<= toQuater )
     * OR (KPI_YEAR < toYear AND KPI_YEAR> fromYear) OR (KPI_PERIOD>= fromQuater AND KPI_YEAR= fromYear))
     * ORDER BY KPI_DEFINITION ASC ,KPI_YEAR ASC ,KPI_PERIOD ASC;
     */
    private static final String QUERY_LOAD_COMPANY_KPI_ON_QUATER_BASIS = QUERY_SELECT_ALL_FROM + TABLE_KPI_MASTER +
            QUERY_WHERE + COMPANY_ID + QUERY_EQUAL + "#companyId#" + QUERY_AND + QUERY_STATUS_FILTER +
            QUERY_AND + SQL_BRACKET_OPEN + SQL_BRACKET_OPEN + DBConstants.DatabaseColumns.KPI_YEAR + QUERY_EQUAL + "#toYear#" +
            QUERY_AND + DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_LESS_OR_EQUAL_THAN + "#toQuater#" + SQL_BRACKET_CLOSE +
            QUERY_OR + SQL_BRACKET_OPEN + DBConstants.DatabaseColumns.KPI_YEAR + QUERY_LESS_THAN + "#toYear#" + QUERY_AND +
            DBConstants.DatabaseColumns.KPI_YEAR + QUERY_GREATER_THAN + "#fromYear#" + SQL_BRACKET_CLOSE + QUERY_OR + SQL_BRACKET_OPEN +
            DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_GREATER_OR_EQUAL_THAN + "#fromQuater#" + QUERY_AND + DBConstants.DatabaseColumns.KPI_YEAR +
            QUERY_EQUAL + "#fromYear#" + SQL_BRACKET_CLOSE + SQL_BRACKET_CLOSE + QUERY_ORDER + DBConstants.DatabaseColumns.KPI_DEFINITION + QUERY_ASC_ORDER + QUERY_COMMA +
            DBConstants.DatabaseColumns.KPI_YEAR + QUERY_ASC_ORDER + QUERY_COMMA + DBConstants.DatabaseColumns.KPI_PERIOD + QUERY_ASC_ORDER;

    /**
     * SELECT COMPANY_NAME_language AS COMPANY_NAME_LAN, COUNTRY_CODE, k.*
     * FROM KPI_MASTER k
     * JOIN TICKERS t ON (k.COMPANY_ID = T.COMPANY_ID)
     * WHERE k.STATUS = 1 AND k.KPI_DEFINITION IN (kpiDefIds)
     * AND t.COUNTRY_CODE IN (countryCode)
     * AND ((TICKER_SERIAL = 0 AND LISTING_STATUS = 3) OR
     * (TICKER_SERIAL > 0 AND t.STATUS = 1 AND LISTING_STATUS = 2 AND IS_MAIN_STOCK = 1) OR
     * (TICKER_SERIAL > 0 AND t.STATUS = 1 AND LISTING_STATUS = 1 AND INSTRUMENT_TYPE_ID IN (0,60,61))
     * )
     */
    public static final String QUERY_LOAD_RELATED_KPI = QUERY_SELECT + COMPANY_NAME + "#lang#" + SQL_AS + COMPANY_NAME_LAN +
            QUERY_COMMA + DBConstants.DatabaseColumns.COUNTRY_CODE + QUERY_COMMA + "k.*" +
            FROM + DBConstants.TablesIMDB.TABLE_KPI_MASTER + " k " +
            QUERY_INNER_JOIN + DBConstants.TablesIMDB.TABLE_TICKERS + " t " + QUERY_ON + "k." + COMPANY_ID + QUERY_EQUAL + "t." + COMPANY_ID +
            QUERY_WHERE + "k." + QUERY_STATUS_FILTER + QUERY_AND +
            DBConstants.DatabaseColumns.KPI_DEFINITION + QUERY_IN + SQL_BRACKET_OPEN + "#kpiDefs#" + SQL_BRACKET_CLOSE + QUERY_AND +
            DBConstants.DatabaseColumns.COUNTRY_CODE + QUERY_IN + SQL_BRACKET_OPEN + QUERY_QUOTE + "#countryCodes#" + QUERY_QUOTE + SQL_BRACKET_CLOSE + QUERY_AND +
            TickerDBHelper.getSelectUniqueTickerFilter().replaceAll(QUERY_AND + QUERY_STATUS_FILTER, QUERY_AND + "t." + QUERY_STATUS_FILTER);
    //endregion

    /**
     * create companyKPIRequestDBTO based on period basis and from, to periods
     *
     * @param companyIds
     * @param fromPeriod
     * @param toPeriod
     * @param periodBasis
     * @return
     */
    public static RequestDBDTO getCompanyKPIRequestDBDTO(List<String> companyIds, String fromPeriod, String toPeriod, String periodBasis) {
        String fromYear = IConstants.EMPTY, fromQuater = IConstants.EMPTY, toYear = IConstants.EMPTY,
                toQuater = IConstants.EMPTY, query = IConstants.EMPTY;
        String[] periodDetails;

        if (fromPeriod != null && !fromPeriod.isEmpty()) {
            periodDetails = fromPeriod.split(IConstants.SPACE);
            fromYear = periodDetails[1];
            fromQuater = periodDetails[0].replaceAll("Q", IConstants.EMPTY);
        }
        if (toPeriod != null && !toPeriod.isEmpty()) {
            periodDetails = toPeriod.split(IConstants.SPACE);
            toYear = periodDetails[1];
            toQuater = periodDetails[0].replaceAll("Q", IConstants.EMPTY);
        }

        if (periodBasis.equals("Q")) {
            query = QUERY_LOAD_COMPANY_KPI_ON_QUATER_BASIS.replaceAll("#fromYear#", fromYear);
            query = query.replaceAll("#toYear#", toYear);
            query = query.replaceAll("#fromQuater#", fromQuater);
            query = query.replaceAll("#toQuater#", toQuater);
        } else if (periodBasis.equals("A")) {
            query = QUERY_LOAD_COMPANY_KPI_ON_ANNUAL_BASIS.replaceAll("#toYear#", toYear);
            query = query.replaceAll("#fromYear#", fromYear);
        }

        Map<String, Object> customParam = new HashMap<String, Object>(1);
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        customParam.put(IConstants.CustomDataField.KPI_COMPANY_LIST, companyIds);
        requestDBDTO.setCustomParams(customParam);
        return requestDBDTO;
    }

    /**
     * Create KpiDTO object from given result set
     *
     * @param results result set
     * @return KpiDTO object
     * @throws SQLException
     */
    public static KpiDTO getKPIData(ResultSet results) throws SQLException {
        return setKPIData(results);
    }

    /**
     * Method to set KPI data and return object
     *
     * @param results result set
     * @return KpiDTO object
     * @throws SQLException
     */
    public static KpiDTO setKPIData(ResultSet results) throws SQLException {
        KpiDTO kpiDTO = new KpiDTO();

        kpiDTO.setId(results.getInt(DBConstants.DatabaseColumns.KPI_ID));
        kpiDTO.setCompanyId(results.getInt(DBConstants.DatabaseColumns.COMPANY_ID));
        kpiDTO.setDefinitionId(results.getInt(DBConstants.DatabaseColumns.KPI_DEFINITION));
        kpiDTO.setDate(results.getDate(DBConstants.DatabaseColumns.KPI_DATE));
        kpiDTO.setValue(results.getDouble(DBConstants.DatabaseColumns.KPI_VALUE));
        kpiDTO.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.LAST_UPDATED_TIME));
        kpiDTO.setYear(results.getInt(DBConstants.DatabaseColumns.KPI_YEAR));
        kpiDTO.setPeriod(results.getInt(DBConstants.DatabaseColumns.KPI_PERIOD));
        kpiDTO.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));
        kpiDTO.setStatus(results.getInt(DBConstants.DatabaseColumns.STATUS));

        return kpiDTO;
    }

    /**
     * Method to set KPI data and return object
     *
     * @param results result set
     * @return KpiDTO object
     * @throws SQLException
     */
    public static KpiDTO setDetailKPIData(ResultSet results) throws SQLException {

        KpiDTO kpiDTO = setKPIData(results);

        kpiDTO.setCompanyName(results.getString(DBConstants.DatabaseColumns.COMPANY_NAME_LAN));
        kpiDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));

        return kpiDTO;
    }

    /**
     * Create KpiDTO object from given result set
     *
     * @param results resultset
     * @return KpiDTO object
     * @throws SQLException
     */
    public static KpiDTO getDetailKPIData(ResultSet results) throws SQLException {
        return setDetailKPIData(results);
    }
}
