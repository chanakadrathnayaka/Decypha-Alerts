package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.ownershipLimits.OwnershipLimitSeriesDTO;
import com.dfn.alerts.beans.ownershipLimits.StockOwnershipDTO;
import com.dfn.alerts.beans.ownershipLimits.StockOwnershipLimitSeriesDTO;
import com.dfn.alerts.beans.ownershipLimits.StockOwnershipValueDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by hasarindat on 2/17/2015.
 */
public class OwnershipLimitsDBHelper {

    private static final String S_PREFIX = " s ";
    private static final String S_PREFIX_DOT = " s.";

    private static final String L_PREFIX = " l ";
    private static final String L_PREFIX_DOT = " l.";

    private static final String RANK_COL = "rnk";

    private static final String DENSE_RANK_FN_ORDER_ONLY = "dense_rank() over (ORDER by ";

    private static final String TO_DATE = "TO_DATE";
    private static final String DATE_FORMAT = "'yyyy-MM-dd'";

    private static final String STATUS_DATE_FILTER_PLACEHOLDER = "#statusDate#";

    private static final String RANK_SQL = DENSE_RANK_FN_ORDER_ONLY + S_PREFIX_DOT + OWNERSHIP_DATE + QUERY_DESC_ORDER + SQL_BRACKET_CLOSE + RANK_COL;

    private final static String EXCHANGE_FILTER = SERIES_ID + QUERY_IN + QUERY_BRACKET_OPEN +
            QUERY_SELECT + SERIES_ID + FROM + DBConstants.TablesORACLE.TABLE_OWNERSHIP_LIMIT_SERIES + QUERY_WHERE + SOURCE_ID + EQUAL_QUETION_MARK +
            SQL_BRACKET_CLOSE;

    private static String SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE = null;
    private static String SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE = null;
    private static String SQL_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE_RANGE = null;

    private static final String SQL_LOAD_OWNERSHIP_LIMIT_SERIES_DEF_BY_SOURCE_ID =
            QUERY_SELECT_ALL_FROM + DBConstants.TablesORACLE.TABLE_OWNERSHIP_LIMIT_SERIES + L_PREFIX +
                    QUERY_WHERE + SOURCE_ID + EQUAL_QUETION_MARK +
                    QUERY_AND + QUERY_STATUS_FILTER + QUERY_AND + EXISTS +
                    SQL_BRACKET_OPEN + QUERY_SELECT + 1 + FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES +
                        QUERY_WHERE + L_PREFIX_DOT + SERIES_ID + QUERY_EQUAL + SERIES_ID + SQL_BRACKET_CLOSE;

    private static String getLatestOwnershipDataQueryForMarket(){
        if(SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE == null){
            SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE = QUERY_SELECT_ALL_FROM + SQL_BRACKET_OPEN +
                    QUERY_SELECT + S_PREFIX_DOT + TICKER_SERIAL + SQL_COMMA +
                    S_PREFIX_DOT + SERIES_ID + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_DATE + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VALUE + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VWAP + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VWAP_USD + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_NO_OF_SHARES + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_CA + SQL_COMMA +
                    L_PREFIX_DOT + OWNERSHIP_LIMIT + SQL_COMMA +
                    RANK_SQL +
                    FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES + S_PREFIX +
                    QUERY_JOIN + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_LIMITS + L_PREFIX +
                    QUERY_ON + SQL_BRACKET_OPEN +
                    S_PREFIX_DOT + TICKER_SERIAL + QUERY_EQUAL + L_PREFIX_DOT + TICKER_SERIAL +
                    QUERY_AND + S_PREFIX_DOT + SERIES_ID + QUERY_EQUAL + L_PREFIX_DOT + SERIES_ID +
                    QUERY_AND + S_PREFIX_DOT + QUERY_STATUS_FILTER +
                    QUERY_AND + L_PREFIX_DOT + QUERY_STATUS_FILTER +
                    SQL_BRACKET_CLOSE +
                    QUERY_WHERE + L_PREFIX_DOT + EXCHANGE_FILTER +
                    SQL_BRACKET_CLOSE + QUERY_WHERE + RANK_COL + QUERY_LESS_OR_EQUAL_THAN + 2 +
                    QUERY_ORDER + TICKER_SERIAL + SQL_COMMA + RANK_COL + QUERY_DESC_ORDER;
        }
        return SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE;
    }

    private static String getLatestOwnershipDataQueryForMarketByDate(){
        if(SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE == null){
            SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE = QUERY_SELECT_ALL_FROM + SQL_BRACKET_OPEN +
                    QUERY_SELECT + S_PREFIX_DOT + TICKER_SERIAL + SQL_COMMA +
                    S_PREFIX_DOT + SERIES_ID + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_DATE + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VALUE + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VWAP + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_VWAP_USD + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_NO_OF_SHARES + SQL_COMMA +
                    S_PREFIX_DOT + OWNERSHIP_CA + SQL_COMMA +
                    L_PREFIX_DOT + OWNERSHIP_LIMIT + SQL_COMMA +
                    RANK_SQL +
                    FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES + S_PREFIX +
                    QUERY_JOIN + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_LIMITS + L_PREFIX +
                    QUERY_ON + SQL_BRACKET_OPEN +
                    S_PREFIX_DOT + TICKER_SERIAL + QUERY_EQUAL + L_PREFIX_DOT + TICKER_SERIAL +
                    QUERY_AND + S_PREFIX_DOT + SERIES_ID + QUERY_EQUAL + L_PREFIX_DOT + SERIES_ID +
                    QUERY_AND + S_PREFIX_DOT + QUERY_STATUS_FILTER +
                    QUERY_AND + L_PREFIX_DOT + QUERY_STATUS_FILTER +
                    SQL_BRACKET_CLOSE +
                        QUERY_WHERE + L_PREFIX_DOT + EXCHANGE_FILTER +
                        QUERY_AND + S_PREFIX_DOT + OWNERSHIP_DATE + QUERY_LESS_THAN + "#eDate#" +
                    SQL_BRACKET_CLOSE + QUERY_WHERE + RANK_COL + QUERY_LESS_OR_EQUAL_THAN + 1 +
                    QUERY_ORDER + TICKER_SERIAL + SQL_COMMA + OWNERSHIP_DATE + QUERY_ASC_ORDER;
        }
        return SQL_LATEST_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE;
    }

    private static String getOwnershipDataQueryForMarketByDateRange(){
        if(SQL_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE_RANGE == null){
            SQL_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE_RANGE =
                    QUERY_SELECT + S_PREFIX_DOT + TICKER_SERIAL + SQL_COMMA +
                            S_PREFIX_DOT + SERIES_ID + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_DATE + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_VALUE + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_VWAP + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_VWAP_USD + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_NO_OF_SHARES + SQL_COMMA +
                            S_PREFIX_DOT + OWNERSHIP_CA + SQL_COMMA +
                            L_PREFIX_DOT + OWNERSHIP_LIMIT +
                    FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES + S_PREFIX +
                    QUERY_JOIN + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_LIMITS + L_PREFIX +
                    QUERY_ON + SQL_BRACKET_OPEN +
                            S_PREFIX_DOT + TICKER_SERIAL + QUERY_EQUAL + L_PREFIX_DOT + TICKER_SERIAL +
                    QUERY_AND + S_PREFIX_DOT + SERIES_ID + QUERY_EQUAL + L_PREFIX_DOT + SERIES_ID +
                    QUERY_AND + S_PREFIX_DOT + QUERY_STATUS_FILTER +
                    QUERY_AND + L_PREFIX_DOT + QUERY_STATUS_FILTER +
                    SQL_BRACKET_CLOSE +
                    QUERY_WHERE + L_PREFIX_DOT + EXCHANGE_FILTER +
                            QUERY_AND + OWNERSHIP_DATE + QUERY_BETWEEN + "#sDate#" + QUERY_AND + "#eDate#" +
                    QUERY_ORDER + TICKER_SERIAL + SQL_COMMA + OWNERSHIP_DATE + QUERY_ASC_ORDER;
        }
        return SQL_OWNERSHIP_DATA_FOR_EXCHANGE_BY_DATE_RANGE;
    }

    private static final String SQL_LOAD_SYMBOL_OWNERSHIP_LIMITS =
            QUERY_SELECT_ALL_FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_LIMITS + S_PREFIX +
                    QUERY_JOIN + DBConstants.TablesORACLE.TABLE_OWNERSHIP_LIMIT_SERIES + L_PREFIX +
                    QUERY_ON + QUERY_BRACKET_OPEN + S_PREFIX_DOT + SERIES_ID + QUERY_EQUAL +
                    L_PREFIX_DOT + SERIES_ID + QUERY_BRACKET_CLOSE +
                    QUERY_WHERE + TICKER_SERIAL + EQUAL_QUETION_MARK + QUERY_AND +
                    S_PREFIX_DOT + QUERY_STATUS_FILTER + QUERY_AND +
                    L_PREFIX_DOT + QUERY_STATUS_FILTER +
                    QUERY_ORDER + L_PREFIX_DOT + SERIES_ID;

    private static final String SQL_LOAD_STOCK_OWNERSHIP_HISTORY =
            QUERY_SELECT_ALL_FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES +
                    QUERY_WHERE + TICKER_SERIAL + EQUAL_QUETION_MARK + QUERY_AND + QUERY_STATUS_FILTER;

    public static final String SQL_LOAD_STOCK_OWNERSHIP_HISTORY_LAST_2_RECORDS =
            QUERY_SELECT_ALL_FROM + QUERY_BRACKET_OPEN +
                    QUERY_SELECT + S_PREFIX_DOT + IConstants.ASTERISK + SQL_COMMA + RANK_SQL +
            FROM + DBConstants.TablesORACLE.TABLE_STOCK_OWNERSHIP_VALUES + S_PREFIX +
                    QUERY_WHERE + TICKER_SERIAL + EQUAL_QUETION_MARK + QUERY_AND + QUERY_STATUS_FILTER + STATUS_DATE_FILTER_PLACEHOLDER +
                    SQL_BRACKET_CLOSE + QUERY_WHERE + RANK_COL + QUERY_LESS_OR_EQUAL_THAN + 2 +
                    QUERY_ORDER + RANK_COL + QUERY_DESC_ORDER;

    //region market

    /**
     * SELECT * FROM OWNERSHIP_LIMIT_SERIES l
     * WHERE SOURCE_ID = ?  AND STATUS = 1
     *      AND EXISTS(SELECT 1 FROM STOCK_OWNERSHIP_VALUES WHERE  l.SERIES_ID = SERIES_ID)
     * @param sourceId exchange
     * @return requestDBDTO
     */
    public static RequestDBDTO getOwnershipSeriesDataForMarket(String sourceId) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(SQL_LOAD_OWNERSHIP_LIMIT_SERIES_DEF_BY_SOURCE_ID);
        requestDBDTO.setParams(sourceId);
        return requestDBDTO;
    }

    /**
     * SELECT * FROM (
     *      SELECT  s.TICKER_SERIAL, s.SERIES_ID, s.OWNERSHIP_VALUE_DATE, s.OWNERSHIP_VALUE, s.OWNERSHIP_DATE_VWAP, s.OWNERSHIP_DATE_VWAP_USD,
     *          s.NUMBER_OF_SHARES_ON_DATE, s.HAS_CORPORATE_ACTIONS_ON_DATE, l.OWNERSHIP_LIMIT,
     *          dense_rank() over (ORDER BY  s.OWNERSHIP_VALUE_DATE DESC )rnk
     *      FROM STOCK_OWNERSHIP_VALUES s
     *      JOIN STOCK_OWNERSHIP_LIMIT l  ON ( s.TICKER_SERIAL =  l.TICKER_SERIAL AND  s.SERIES_ID =  l.SERIES_ID AND  s.STATUS = 1  AND  l.STATUS = 1 )
     *      WHERE  l.SERIES_ID IN  (SELECT SERIES_ID FROM OWNERSHIP_LIMIT_SERIES WHERE SOURCE_ID = ? )
     * ) WHERE rnk <= 2
     * ORDER BY TICKER_SERIAL,rnk DESC
     * @param sourceId exchange
     * @return requestDBDTO
     */
    public static RequestDBDTO getStockOwnershipDataForMarket(String sourceId) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        String query = getLatestOwnershipDataQueryForMarket();
        requestDBDTO.setQuery(query);
        requestDBDTO.setParams(sourceId);
        return requestDBDTO;
    }

    /**
     * SELECT * FROM (
     *      SELECT  s.TICKER_SERIAL, s.SERIES_ID, s.OWNERSHIP_VALUE_DATE, s.OWNERSHIP_VALUE, s.OWNERSHIP_DATE_VWAP, s.OWNERSHIP_DATE_VWAP_USD,
     *          s.NUMBER_OF_SHARES_ON_DATE, s.HAS_CORPORATE_ACTIONS_ON_DATE, l.OWNERSHIP_LIMIT,
     *          dense_rank() over (partition by  s.TICKER_SERIAL ORDER BY  s.OWNERSHIP_VALUE_DATE DESC )rnk
     *      FROM STOCK_OWNERSHIP_VALUES s
     *      JOIN STOCK_OWNERSHIP_LIMIT l  ON ( s.TICKER_SERIAL =  l.TICKER_SERIAL AND  s.SERIES_ID =  l.SERIES_ID AND  s.STATUS = 1  AND  l.STATUS = 1 )
     *      WHERE  l.SERIES_ID IN  (SELECT SERIES_ID FROM OWNERSHIP_LIMIT_SERIES WHERE SOURCE_ID = ? )
     *          AND  s.OWNERSHIP_VALUE_DATE < TO_DATE('2015-03-11','yyyy-MM-dd')
     * ) WHERE rnk <= 1
     * ORDER BY TICKER_SERIAL,OWNERSHIP_VALUE_DATE ASC
     * @param sourceId exchange
     * @param date date (eg : 2015-03-11)
     * @return requestDBDTO
     */
    public static RequestDBDTO getStockOwnershipDataForMarket(String sourceId, String date) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        String query = getLatestOwnershipDataQueryForMarketByDate();
        query = query.replaceAll("#eDate#", getSQLDate(date));
        requestDBDTO.setQuery(query);
        requestDBDTO.setParams(sourceId);
        return requestDBDTO;
    }

    /**
     * SELECT  s.TICKER_SERIAL, s.SERIES_ID, s.OWNERSHIP_VALUE_DATE, s.OWNERSHIP_VALUE, s.OWNERSHIP_DATE_VWAP, s.OWNERSHIP_DATE_VWAP_USD,
     *      s.NUMBER_OF_SHARES_ON_DATE, s.HAS_CORPORATE_ACTIONS_ON_DATE, l.OWNERSHIP_LIMIT
     * FROM STOCK_OWNERSHIP_VALUES s
     * JOIN STOCK_OWNERSHIP_LIMIT l  ON ( s.TICKER_SERIAL =  l.TICKER_SERIAL AND  s.SERIES_ID =  l.SERIES_ID AND  s.STATUS = 1  AND  l.STATUS = 1 )
     * WHERE  l.SERIES_ID IN  (SELECT SERIES_ID FROM OWNERSHIP_LIMIT_SERIES WHERE SOURCE_ID = ? )
     *      AND OWNERSHIP_VALUE_DATE BETWEEN TO_DATE('2015-01-01','yyyy-MM-dd') AND TO_DATE('2015-03-11','yyyy-MM-dd')
     * ORDER BY TICKER_SERIAL,OWNERSHIP_VALUE_DATE ASC
     * @param sourceId exchange
     * @param startDate startDate (eg : 2015-01-01)
     * @param endDate endDate (eg : 2015-03-11)
     * @return requestDBDTO
     */
    public static RequestDBDTO getStockOwnershipDataForMarket(String sourceId, String startDate, String endDate) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        String query = getOwnershipDataQueryForMarketByDateRange();
        query = query.replaceAll("#sDate#", getSQLDate(startDate));
        query = query.replaceAll("#eDate#", getSQLDate(endDate));
        requestDBDTO.setQuery(query);
        requestDBDTO.setParams(sourceId);
        return requestDBDTO;
    }

    //endregion

    //region stock

    /**
     * SELECT * FROM STOCK_OWNERSHIP_LIMIT s
     * JOIN OWNERSHIP_LIMIT_SERIES l  ON  ( s.SERIES_ID =  l.SERIES_ID)
     * WHERE TICKER_SERIAL = ?  AND  s.STATUS = 1  AND  l.STATUS = 1
     * ORDER BY  l.SERIES_ID
     * @param tickerSerial tickerSerial
     * @return requestDBDTO
     */
    public static RequestDBDTO getOwnershipSeriesDataForStock(String tickerSerial) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(SQL_LOAD_SYMBOL_OWNERSHIP_LIMITS);
        requestDBDTO.setParams(tickerSerial);
        return requestDBDTO;
    }

    /**
     * SELECT * FROM  (
     *      SELECT  s.*,dense_rank() over (ORDER BY  s.OWNERSHIP_VALUE_DATE DESC )rnk
     *      FROM STOCK_OWNERSHIP_VALUES s
     *      WHERE TICKER_SERIAL = ?  AND STATUS = 1 {AND OWNERSHIP_VALUE_DATE <= {statusDate}}
     * ) WHERE rnk <= 2
     * ORDER BY rnk DESC
     * @param tickerSerial tickerSerial
     * @return requestDBDTO
     */
    public static RequestDBDTO getOwnershipDataForStock(String tickerSerial, String statusDate) {
        String builder = SQL_LOAD_STOCK_OWNERSHIP_HISTORY_LAST_2_RECORDS;
        if(statusDate == null || statusDate.isEmpty()){
            builder = builder.replace(STATUS_DATE_FILTER_PLACEHOLDER, IConstants.EMPTY);
        }else{
            String statusDateFilter = QUERY_AND + OWNERSHIP_DATE + QUERY_LESS_OR_EQUAL_THAN + getSQLDate(statusDate);
            builder = builder.replace(STATUS_DATE_FILTER_PLACEHOLDER, statusDateFilter);
        }
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(builder);
        requestDBDTO.setParams(tickerSerial);
        return requestDBDTO;
    }

    /**
     * SELECT * FROM STOCK_OWNERSHIP_VALUES
     * WHERE TICKER_SERIAL = ?  AND STATUS = 1
     * AND (OWNERSHIP_VALUE_DATE BETWEEN TO_DATE('2015-01-01','yyyy-MM-dd') AND TO_DATE('2015-02-16','yyyy-MM-dd'))
     * ORDER BY OWNERSHIP_VALUE_DATE ASC
     * @param tickerSerial tickerSerial
     * @param startDate startDate (eg : 2015-01-01)
     * @param endDate endDate (eg : 2015-02-16)
     * @return requestDBDTO
     */
    public static RequestDBDTO getOwnershipDataForStock(String tickerSerial, String startDate, String endDate) {
        String builder = SQL_LOAD_STOCK_OWNERSHIP_HISTORY +
                    DBConstants.AND + DBConstants.SQL_BRACKET_OPEN + DBConstants.DatabaseColumns.OWNERSHIP_DATE +
                    DBConstants.QUERY_BETWEEN + getSQLDate(startDate) + DBConstants.AND + getSQLDate(endDate) +
                    DBConstants.SQL_BRACKET_CLOSE + DBConstants.QUERY_ORDER_BY + DBConstants.DatabaseColumns.OWNERSHIP_DATE + QUERY_ASC_ORDER;
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(builder);
        requestDBDTO.setParams(tickerSerial);
        return requestDBDTO;
    }

    //endregion

    /**
     * set date parameter
     *
     * @param date yyyy-MM-dd
     * @return string - to_date('xxxx-xx-xx', 'yyyy-MM-dd');
     */
    private static String getSQLDate(String date) {
        return TO_DATE + DBConstants.SQL_BRACKET_OPEN + QUERY_QUOTE + date + QUERY_QUOTE + DBConstants.SQL_COMMA + DATE_FORMAT + DBConstants.SQL_BRACKET_CLOSE;
    }

    /**
     * fill the ownership limit dto from oracle db result set
     *
     * @param results result set
     * @throws SQLException
     */
    private static void getOwnershipLimit(ResultSet results, OwnershipLimitSeriesDTO ownershipLimitSeriesDTO) throws SQLException {
        ownershipLimitSeriesDTO.setSeriesId(results.getInt(DBConstants.DatabaseColumns.SERIES_ID));
        ownershipLimitSeriesDTO.setSeriesName(results.getString(DBConstants.DatabaseColumns.SERIES_DESC));
        ownershipLimitSeriesDTO.setDirection(results.getString(DBConstants.DatabaseColumns.DIRECTION));
        ownershipLimitSeriesDTO.setUpdateFrequencyId(results.getInt(DBConstants.DatabaseColumns.UPDATE_FREQUENCY));
        ownershipLimitSeriesDTO.setUpdateFrequencyDescription(results.getString(DBConstants.DatabaseColumns.UPDATE_FREQUENCY_DESC));
    }

    /**
     * fill the ownership limit dto from oracle db result set
     *
     * @param results result set
     * @return StockOwnershipLimitSeriesDTO
     * @throws SQLException
     */
    public static OwnershipLimitSeriesDTO getOwnershipLimit(ResultSet results) throws SQLException {

        OwnershipLimitSeriesDTO ownershipLimitSeriesDTO = new StockOwnershipLimitSeriesDTO();
        getOwnershipLimit(results, ownershipLimitSeriesDTO);

        return ownershipLimitSeriesDTO;
    }

    /**
     * fill the ownership limit dto from oracle db result set
     *
     * @param results result set
     * @param dataMap data map
     * @throws SQLException
     */
    public static void getMarketStockOwnership(ResultSet results, Map<String, StockOwnershipDTO> dataMap) throws SQLException {
        long tickerSerial = results.getLong(DBConstants.DatabaseColumns.TICKER_SERIAL);
        int seriesId = results.getInt(DBConstants.DatabaseColumns.SERIES_ID);
        StockOwnershipDTO stockOwnershipDTO;
        if (dataMap.containsKey(String.valueOf(tickerSerial))) {
            stockOwnershipDTO = dataMap.get(String.valueOf(tickerSerial));
            StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO;
            if(stockOwnershipDTO.getTypes().containsKey(String.valueOf(seriesId))){
                stockOwnershipLimitSeriesDTO = stockOwnershipDTO.getTypes().get(String.valueOf(seriesId));
                addDataObject(results, tickerSerial, stockOwnershipLimitSeriesDTO, true);
            }else{
                stockOwnershipLimitSeriesDTO = new StockOwnershipLimitSeriesDTO();
                stockOwnershipLimitSeriesDTO.setSeriesId(seriesId);
                stockOwnershipLimitSeriesDTO.setLimit(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_LIMIT));
                addDataObject(results, tickerSerial, stockOwnershipLimitSeriesDTO, false);
                stockOwnershipDTO.addType(stockOwnershipLimitSeriesDTO);
            }
        } else {
            stockOwnershipDTO = new StockOwnershipDTO(tickerSerial);
            StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO = new StockOwnershipLimitSeriesDTO();
            stockOwnershipLimitSeriesDTO.setSeriesId(seriesId);
            stockOwnershipLimitSeriesDTO.setLimit(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_LIMIT));
            stockOwnershipDTO.addType(stockOwnershipLimitSeriesDTO);
            addDataObject(results, tickerSerial, stockOwnershipLimitSeriesDTO, false);
        }
        dataMap.put(String.valueOf(tickerSerial), stockOwnershipDTO);
    }

    private static void addDataObject(ResultSet results, long tickerSerial, StockOwnershipLimitSeriesDTO limitSeriesDTO, boolean isCurrent) throws SQLException {
        String date = results.getDate(DBConstants.DatabaseColumns.OWNERSHIP_DATE).toString();
        double value = results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VALUE);
        StockOwnershipValueDTO stockOwnershipValueDTO = new StockOwnershipValueDTO(tickerSerial, limitSeriesDTO.getSeriesId(), date);
        stockOwnershipValueDTO.setOwnershipPercentage(value);
        stockOwnershipValueDTO.setNoOfShares(results.getLong(DBConstants.DatabaseColumns.OWNERSHIP_NO_OF_SHARES));
        stockOwnershipValueDTO.setVwap(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP));
        stockOwnershipValueDTO.setVwapUSD(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP_USD));
        stockOwnershipValueDTO.setCA(results.getBoolean(DBConstants.DatabaseColumns.OWNERSHIP_CA));
        limitSeriesDTO.addDataValue(stockOwnershipValueDTO);

        limitSeriesDTO.setCurrentValue(value);
        limitSeriesDTO.setCurrentVwap(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP));
        limitSeriesDTO.setCurrentVwapUSD(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP_USD));
        limitSeriesDTO.setCurrentNoOfShares(results.getLong(DBConstants.DatabaseColumns.OWNERSHIP_NO_OF_SHARES));
        limitSeriesDTO.setCurrentDate(date);
        if(!isCurrent){
            limitSeriesDTO.setComparativeValue(value);
            limitSeriesDTO.setComparativeVwap(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP));
            limitSeriesDTO.setComparativeVwapUSD(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_VWAP_USD));
            limitSeriesDTO.setComparativeNoOfShares(results.getLong(DBConstants.DatabaseColumns.OWNERSHIP_NO_OF_SHARES));
            limitSeriesDTO.setComparativeDate(date);
        }
    }

    /**
     * fill the stock ownership limit dto from oracle db result set
     *
     * @param results result set
     * @return StockOwnershipDTO
     * @throws SQLException
     */
    public static StockOwnershipLimitSeriesDTO getStockOwnershipSeriesData(ResultSet results) throws SQLException {
        StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO = new StockOwnershipLimitSeriesDTO();
        getOwnershipLimit(results, stockOwnershipLimitSeriesDTO);
        stockOwnershipLimitSeriesDTO.setEligibility(results.getBoolean(DBConstants.DatabaseColumns.OWNERSHIP_ELIGIBILITY));
        stockOwnershipLimitSeriesDTO.setLimit(results.getDouble(DBConstants.DatabaseColumns.OWNERSHIP_LIMIT));
        return stockOwnershipLimitSeriesDTO;
    }

    /**
     * get history values for stock ownership
     * what happens if no data is in DB for one series for one day???
     *
     * @param results           query result set
     * @param stockOwnershipDTO StockOwnershipDTO
     * @throws SQLException
     */
    public static StockOwnershipDTO getStockOwnershipOnly(ResultSet results, StockOwnershipDTO stockOwnershipDTO) throws SQLException {
        long tickerSerial = results.getLong(DBConstants.DatabaseColumns.TICKER_SERIAL);
        if (stockOwnershipDTO == null) {
            stockOwnershipDTO = new StockOwnershipDTO(tickerSerial);
        }
        int seriesId = results.getInt(DBConstants.DatabaseColumns.SERIES_ID);
        String kSeriesId = String.valueOf(seriesId);
        StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO;

        if (stockOwnershipDTO.getTypes().containsKey(kSeriesId)) {
            stockOwnershipLimitSeriesDTO = stockOwnershipDTO.getTypes().get(kSeriesId);
            addDataObject(results, tickerSerial, stockOwnershipLimitSeriesDTO, true);
        } else {
            stockOwnershipLimitSeriesDTO = new StockOwnershipLimitSeriesDTO();
            stockOwnershipLimitSeriesDTO.setSeriesId(seriesId);
            addDataObject(results, tickerSerial, stockOwnershipLimitSeriesDTO, false);
            stockOwnershipDTO.addType(stockOwnershipLimitSeriesDTO);
        }
        return stockOwnershipDTO;
    }
}
