package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.tickers.EquityTickerDTO;
import com.dfn.alerts.beans.tickers.IndexDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.beans.tickers.TickerLangDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 4/4/2014.
 */
public class IndexTickerDBHelper {

    private static String updateIndexColumnString;
    private static String insertIndexColumnString;
    private static String insertIndexColumnParams;

    private static final String SQL_VALUES = ") VALUES(";
    private static final String SQL_BRACKET_OPEN = "(";
    private static final String SQL_BRACKET_CLOSE = ")";
    private static final String SQL_UPDATE = " UPDATE ";
    private static final String SQL_SET = " SET ";
    private static final String SQL_INSERT = " INSERT INTO ";
    private static final String QUERY_INDEX_TICKER = "SELECT * FROM TICKERS WHERE INSTRUMENT_TYPE_ID=" + Integer.parseInt(IConstants.InstrumentTypes.INDICES.getDefaultValues());
    private static final String QUERY_AND = " AND ";
    private static final String LAST_SYNC_TIME_FILTER = "  LAST_SYNC_TIME > ? ";
    private static final String SQL_INDEX_TICKER = " INDEX_SNAPSHOT ";
    private static final String SQL_INDEX_TICKER_DELAYED = " INDEX_SNAPSHOT_DELAYED ";
    private static final String INDEX_TICKER_UPDATE_WHERE = " WHERE TICKER_SERIAL = ?";
    private static final String INSERT_TICKER_SERIAL = ",TICKER_SERIAL";
    private static final String INSERT_TICKER_SERIAL_PARAMS = DBConstants.CommonDatabaseParams.SQL_COMMA + DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;

    private static final String[] INDEX_TICKER_INSERT_COLUMNS = new String[]{ DECIMAL_PLACES,DISPLAY_TICKER,INSTRUMENT_TYPE_ID,LAST_UPDATED_TIME,LOT_SIZE,SOURCE_ID,
            TICKER_ID,TICKER_LONG_DES_LN,TICKER_SHORT_DES_LN,STATUS,UNIT,CURRENCY_ID,LAST_SYNC_TIME};

    private static final String[] LANGUAGE_SPECIFIC_INDEX_COLUMNS = new String[]{TICKER_SHORT_DESCRIPTION, TICKER_LONG_DESCRIPTION,  TICKER_COUNTRY_DESC};

    public static final String UPDATE_PLACEHOLDER = QUERY_EQUAL + SQL_QUESTION_MARK + QUERY_COMMA;



    public static String getIndexTickerQuery(Timestamp lastRunTime) {
        return QUERY_INDEX_TICKER + ((lastRunTime != null) ? QUERY_AND + LAST_SYNC_TIME_FILTER : IConstants.EMPTY);
    }

    public static TickerDTO getIndexTickerData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        return EquityTickerDBHelper.getEquityTickerData(results, supportedLanguages);
    }

    /**
     * get comma separated list of columns to be inserted for index
     * @param supportedLang supported languages
     * @return insert column string
     */
    public static String getInsertTickerColumnString(List<String> supportedLang) {
        if (insertIndexColumnString == null) {
            insertIndexColumnString = StringUtils.join(INDEX_TICKER_INSERT_COLUMNS, IConstants.Delimiter.COMMA) + IConstants.Delimiter.COMMA;//join TICKER_COLUMNS with ","
            StringBuilder insertIndexStringBuilder = new StringBuilder();

            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_INDEX_COLUMNS) {
                    lang = lang.toUpperCase();
                    insertIndexStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(IConstants.Delimiter.COMMA);
                }
            }
            insertIndexColumnString += insertIndexStringBuilder.toString();
            insertIndexColumnString = CommonUtils.removeLast(insertIndexColumnString);
        }
        return insertIndexColumnString;
    }

    /**
     * get String for no of params of insert query
     * @param supportedLang supported languages
     * @return insert column string
     */
    private static String getEquityTickerInsertColumnParams(List<String> supportedLang) {
        if (insertIndexColumnParams == null) {
            insertIndexColumnParams = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK, DBConstants.CommonDatabaseParams.SQL_COMMA,
                    INDEX_TICKER_INSERT_COLUMNS.length + LANGUAGE_SPECIFIC_INDEX_COLUMNS.length * supportedLang.size() ) ;
        }
        return insertIndexColumnParams;
    }

    /***
     * get columns for update query
     * @param supportedLang supported languages
     * @return update column string
     */
    public static String getUpdateTickerColumnString(List<String> supportedLang) {
        if (updateIndexColumnString == null) {
            updateIndexColumnString = StringUtils.join(INDEX_TICKER_INSERT_COLUMNS, UPDATE_PLACEHOLDER) + UPDATE_PLACEHOLDER;//join TICKER_COLUMNS with "=?,"

            if(supportedLang != null ) {
                StringBuilder updateIndexStringBuilder = new StringBuilder();
                for (String lang : supportedLang) {
                    for (String column : LANGUAGE_SPECIFIC_INDEX_COLUMNS) {
                        lang = lang.toUpperCase();
                        updateIndexStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(UPDATE_PLACEHOLDER);
                    }
                }
                updateIndexColumnString += updateIndexStringBuilder.toString();
            }
            updateIndexColumnString = CommonUtils.removeLast(updateIndexColumnString);
        }
        return updateIndexColumnString;
    }

    /**
     * build insert index tickers sql string
     *
     * @return SQL String
     */
    public static String getInsertIndexTickersSql(boolean isDelayed, List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder();
        sql.append(SQL_INSERT);
        if(isDelayed){
            sql.append(SQL_INDEX_TICKER_DELAYED);
        }
        else {
            sql.append(SQL_INDEX_TICKER);
        }
        sql.append(SQL_BRACKET_OPEN);
        sql.append(getInsertTickerColumnString(supportedLanguages)).append(INSERT_TICKER_SERIAL);
        sql.append(SQL_VALUES);
        sql.append(getEquityTickerInsertColumnParams(supportedLanguages)).append(INSERT_TICKER_SERIAL_PARAMS);
        sql.append(SQL_BRACKET_CLOSE);
        return sql.toString();
    }

    /**
     * build update index tickers sql string
     *
     * @return SQL String
     */
    public static String getUpdateIndexTickerSql(boolean isDelayed, List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder();
        sql.append(SQL_UPDATE);
        if(isDelayed){
            sql.append(SQL_INDEX_TICKER_DELAYED);
        }
        else {
            sql.append(SQL_INDEX_TICKER);
        }
        sql.append(SQL_SET);
        sql.append(getUpdateTickerColumnString(supportedLanguages));
        sql.append(INDEX_TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    public static void setUpdateIndexTickerValues(PreparedStatement preparedStatement, EquityTickerDTO tickerDTO,  List<String> supportedLanguages) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, tickerDTO.getDecimalPlaces());
        preparedStatement.setString(index++, tickerDTO.getDisplayTicker());
        preparedStatement.setInt(index++, tickerDTO.getInstrumentTypeId());
        preparedStatement.setTimestamp(index++, tickerDTO.getLastUpdatedTime());
        preparedStatement.setInt(index++, tickerDTO.getLotSize());
        preparedStatement.setString(index++, tickerDTO.getSourceId());
        preparedStatement.setString(index++, tickerDTO.getTickerId());
        preparedStatement.setString(index++, tickerDTO.getTickerLongDesLn());
        preparedStatement.setString(index++, tickerDTO.getTickerShortDes());
        preparedStatement.setInt(index++, tickerDTO.getStatus());
        preparedStatement.setString(index++, tickerDTO.getUnit());
        preparedStatement.setString(index++, tickerDTO.getCurrencyId());
        preparedStatement.setTimestamp(index++, tickerDTO.getLastSyncTime());


        if (supportedLanguages != null) {
            for (String lang : supportedLanguages) {
                if (tickerDTO.getTickerLangDTOMap() != null && tickerDTO.getTickerLangDTOMap().get(lang) != null) {
                    preparedStatement.setString(index++, tickerDTO.getTickerLangDTOMap().get(lang).getTickerShortDesc());
                    preparedStatement.setString(index++, tickerDTO.getTickerLangDTOMap().get(lang).getTickerLongDesc());
                    preparedStatement.setString(index++, tickerDTO.getTickerLangDTOMap().get(lang).getTickerCountryDesc());
                }
            }
        }
        preparedStatement.setLong(index, tickerDTO.getTickerSerial());
    }


    /**
     * Return indexDTO created from given result set
     * @param results ResultSet
     * @param supportedLanguages supported languages
     * @return index dto
     * @throws SQLException
     */
    public static IndexDTO getIndexData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        IndexDTO index = new IndexDTO();
        setIndexData(index, results, supportedLanguages);

        return index;
    }

    /**
     * set data indexDTO from given result set
     * @param index index dto
     * @param results ResultSet
     * @param supportedLanguages supported languages
     * @throws SQLException
     */
    public static void setIndexData(IndexDTO index, ResultSet results, List<String> supportedLanguages) throws SQLException {
        index.setSourceId(results.getString(DBConstants.DatabaseColumns.SOURCE_ID));
        index.setTickerId(results.getString(DBConstants.DatabaseColumns.TICKER_ID));
        index.setTickerSerial(results.getLong(DBConstants.DatabaseColumns.TICKER_SERIAL));
        index.setDisplayTicker(results.getString(DBConstants.DatabaseColumns.DISPLAY_TICKER));
        index.setStatus(results.getInt(DBConstants.DatabaseColumns.STATUS));
        index.setTickerShortDes(results.getString(DBConstants.DatabaseColumns.SHORT_DESC_LAN));
        index.setTickerLongDesLn(results.getString(DBConstants.DatabaseColumns.LONG_DESC_LAN));
        index.setInstrumentTypeId(results.getInt(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID));
        index.setCurrencyId(results.getString(DBConstants.DatabaseColumns.CURRENCY_ID));
        index.setDecimalPlaces(results.getInt(DBConstants.DatabaseColumns.DECIMAL_PLACES));
        index.setLotSize(results.getInt(DBConstants.DatabaseColumns.LOT_SIZE));
        index.setUnit(results.getString(DBConstants.DatabaseColumns.UNIT));

        if (supportedLanguages != null) {
            setIndexLangDTOMap(index, results, supportedLanguages);
        }
    }


    /**
     * Method to set language specific values of indexDTP
     * @param indexDTO index dto
     * @param results ResultSet
     * @param supportedLang supported languages
     * @throws SQLException
     */
    private static void setIndexLangDTOMap(IndexDTO indexDTO, ResultSet results, List<String> supportedLang) throws SQLException {
        Map<String, TickerLangDTO> indexLangDTOMap = new HashMap<String, TickerLangDTO>();

        for (String lang : supportedLang) {
            lang = lang.toUpperCase();
            TickerLangDTO indexLangDTO = new TickerLangDTO();
            indexLangDTO.setTickerShortDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_SHORT_DESCRIPTION + lang));
            indexLangDTO.setTickerLongDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_LONG_DESCRIPTION + lang));
            indexLangDTO.setTickerCountryDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_COUNTRY_DESC + lang));
            indexLangDTOMap.put(lang, indexLangDTO);
        }

        indexDTO.setTickerLangDTOMap(indexLangDTOMap);
    }

    //region NEW

    private static String updateIndexColumnStringNew;
    private static String insertIndexColumnStringNew;
    private static String insertIndexColumnParamsNew;

    private static String cleanUpIMDBQuery;

    private static final String[] TICKER_COLUMNS = new String[]{CURRENCY_ID, DECIMAL_PLACES, DISPLAY_TICKER,  INSTRUMENT_TYPE_ID,
            ISIN_CODE, SOURCE_ID,  TICKER_ID, STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME, TICKER_COUNTRY_CODE,
            ELIGIBILITY_ID, TICKER_CLASS_L1, TICKER_CLASS_L2, TICKER_CLASS_L3};

    private static final String[] LANGUAGE_SPECIFIC_TICKER_COLUMNS = new String[]{
            TICKER_SHORT_DESCRIPTION, TICKER_LONG_DESCRIPTION, SECTOR_NAME, TICKER_COUNTRY_DESC};

    private static final String[] INDEX_COLUMNS_NEW = new String[]{EXCHANGES, COUNTRIES, REGIONS, SECTOR_ID,
            PROVIDER, LAUNCH_DATE, CONSTITUENTS ,UPDATE_FREQUENCY
    };

    private static final String[] INDEX_SNAP_COLUMNS = new String[]{ LAST_TRADE_PRICE, LAST_TRADE_DATE,
            ANNUALIZED_RETURN_QTD, ANNUALIZED_RETURN_YTD, ANNUALIZED_RETURN_1Y, ANNUALIZED_RETURN_2Y, ANNUALIZED_RETURN_3Y,
            ANNUALIZED_RETURN_5Y, ANNUALIZED_RETURN_LIFETIME,
            MONTHLY_HIGH, MONTHLY_HIGH_DATE, MONTHLY_LOW, MONTHLY_LOW_DATE,
            QUARTERLY_HIGH, QUARTERLY_HIGH_DATE, QUARTERLY_LOW, QUARTERLY_LOW_DATE,
            YTD_HIGH, YTD_HIGH_DATE, YTD_LOW, YTD_LOW_DATE,
            ONE_YEAR_HIGH, ONE_YEAR_HIGH_DATE, ONE_YEAR_LOW, ONE_YEAR_LOW_DATE,
            TWO_YEAR_HIGH, TWO_YEAR_HIGH_DATE, TWO_YEAR_LOW, TWO_YEAR_LOW_DATE,
            THREE_YEAR_HIGH, THREE_YEAR_HIGH_DATE, THREE_YEAR_LOW, THREE_YEAR_LOW_DATE,
            LIFETIME_HIGH, LIFETIME_HIGH_DATE, LIFETIME_LOW, LIFETIME_LOW_DATE,CLOSE_START_DATE
    };

    public static String getAllIndices(Timestamp lastRunTime){
        return QUERY_SELECT_ALL_FROM + DBConstants.TablesORACLE.TABLE_INDICES + ((lastRunTime != null) ? QUERY_WHERE + LAST_SYNC_TIME_FILTER : IConstants.EMPTY);
    }

    /**
     * set data indexDTO from given result set
     * @param results ResultSet
     * @param supportedLanguages supported languages
     * @throws SQLException
     */
    public static IndexDTO getIndexDTO(ResultSet results, List<String> supportedLanguages) throws SQLException {
        IndexDTO index = new IndexDTO();
        setTickerData(index, results, supportedLanguages);

        index.setRelatedExchanges(results.getString(DBConstants.DatabaseColumns.EXCHANGES));
        index.setRelatedCountries(results.getString(DBConstants.DatabaseColumns.COUNTRIES));
        index.setRelatedRegions(results.getString(DBConstants.DatabaseColumns.REGIONS));

        index.setSectorId(results.getString(DBConstants.DatabaseColumns.SECTOR_ID));
        index.setProvider(results.getInt(DBConstants.DatabaseColumns.PROVIDER));
        index.setLaunchDate(results.getDate(DBConstants.DatabaseColumns.LAUNCH_DATE));

        index.setConstituents(results.getString(DBConstants.DatabaseColumns.CONSTITUENTS));
        index.setUpdateFrequency(results.getString(DBConstants.DatabaseColumns.UPDATE_FREQUENCY));

        setSnapshotData(index, results);

        return index;
    }

    public static void setSnapshotData(IndexDTO index, ResultSet results) throws SQLException{
        index.setLastTradePrice(DBUtils.getDoubleFromResultSet(results, DBConstants.DatabaseColumns.LAST_TRADE_PRICE));
        index.setLastTradeDate(results.getTimestamp(DBConstants.DatabaseColumns.LAST_TRADE_DATE));

        index.setAnnualizedReturnQtd(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_QTD));
        index.setAnnualizedReturnYtd(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_YTD));
        index.setAnnualizedReturn1Y(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_1Y));
        index.setAnnualizedReturn2Y(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_2Y));
        index.setAnnualizedReturn3Y(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_3Y));
        index.setAnnualizedReturn5Y(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_5Y));
        index.setAnnualizedReturnLifetime(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ANNUALIZED_RETURN_LIFETIME));

        index.setMonthlyHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.MONTHLY_HIGH));
        index.setMonthlyHighDate(results.getDate(DBConstants.DatabaseColumns.MONTHLY_HIGH_DATE));
        index.setMonthlyLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.MONTHLY_LOW));
        index.setMonthlyLowDate(results.getDate(DBConstants.DatabaseColumns.MONTHLY_LOW_DATE));

        index.setQuarterlyHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.QUARTERLY_HIGH));
        index.setQuarterlyHighDate(results.getDate(DBConstants.DatabaseColumns.QUARTERLY_HIGH_DATE));
        index.setQuarterlyLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.QUARTERLY_LOW));
        index.setQuarterlyLowDate(results.getDate(DBConstants.DatabaseColumns.QUARTERLY_LOW_DATE));

        index.setYtdHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.YTD_HIGH));
        index.setYtdHighDate(results.getDate(DBConstants.DatabaseColumns.YTD_HIGH_DATE));
        index.setYtdLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.YTD_LOW));
        index.setYtdLowDate(results.getDate(DBConstants.DatabaseColumns.YTD_LOW_DATE));

        index.setHighPriceOf52Weeks(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ONE_YEAR_HIGH));
        index.setHighPriceDateOf52Weeks(results.getDate(DBConstants.DatabaseColumns.ONE_YEAR_HIGH_DATE));
        index.setLowPriceOf52Weeks(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.ONE_YEAR_LOW));
        index.setLowPriceDateOf52Weeks(results.getDate(DBConstants.DatabaseColumns.ONE_YEAR_LOW_DATE));

        index.setTwoYearHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.TWO_YEAR_HIGH));
        index.setTwoYearHighDate(results.getDate(DBConstants.DatabaseColumns.TWO_YEAR_HIGH_DATE));
        index.setTwoYearLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.TWO_YEAR_LOW));
        index.setTwoYearLowDate(results.getDate(DBConstants.DatabaseColumns.TWO_YEAR_LOW_DATE));

        index.setThreeYearHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.THREE_YEAR_HIGH));
        index.setThreeYearHighDate(results.getDate(DBConstants.DatabaseColumns.THREE_YEAR_HIGH_DATE));
        index.setThreeYearLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.THREE_YEAR_LOW));
        index.setThreeYearLowDate(results.getDate(DBConstants.DatabaseColumns.THREE_YEAR_LOW_DATE));

        index.setLifetimeHigh(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.LIFETIME_HIGH));
        index.setLifetimeHighDate(results.getDate(DBConstants.DatabaseColumns.LIFETIME_HIGH_DATE));
        index.setLifetimeLow(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.LIFETIME_LOW));
        index.setLifetimeLowDate(results.getDate(DBConstants.DatabaseColumns.LIFETIME_LOW_DATE));

        index.setCloseStartDate(results.getDate(DBConstants.DatabaseColumns.CLOSE_START_DATE));
    }

    public static void setTickerData(TickerDTO ticker, ResultSet results, List<String> supportedLanguages) throws SQLException {
        ticker.setSourceId(results.getString(SOURCE_ID));
        ticker.setTickerId(results.getString(TICKER_ID));
        ticker.setIsinCode(results.getString(ISIN_CODE));
        ticker.setDisplayTicker(results.getString(DISPLAY_TICKER));
        ticker.setStatus(results.getInt(STATUS));
        ticker.setTickerSerial(results.getLong(TICKER_SERIAL));
        ticker.setInstrumentTypeId(results.getInt(INSTRUMENT_TYPE_ID));
        ticker.setCurrencyId(results.getString(CURRENCY_ID));
        ticker.setDecimalPlaces(results.getInt(DECIMAL_PLACES));
        ticker.setLastUpdatedTime(results.getTimestamp(LAST_UPDATED_TIME));
        ticker.setLastSyncTime(results.getTimestamp(LAST_SYNC_TIME));
        ticker.setTickerCountryCode(results.getString(TICKER_COUNTRY_CODE));
        ticker.setEligibility(results.getInt(ELIGIBILITY_ID));

        ticker.setTickerClassLevel1(results.getInt(TICKER_CLASS_L1));
        ticker.setTickerClassLevel2(results.getInt(TICKER_CLASS_L2));
        ticker.setTickerClassLevel3(results.getInt(TICKER_CLASS_L3));

        if (supportedLanguages != null) {
            setTickerLangDTOMap(ticker, results, supportedLanguages);
        }
    }

    private static void setTickerLangDTOMap(TickerDTO tickerDTO, ResultSet results, List<String> supportedLang) throws SQLException {
        Map<String, TickerLangDTO> tickerLangDTOMap = new HashMap<String, TickerLangDTO>();

        for (String lang : supportedLang) {
            lang = lang.toUpperCase();
            TickerLangDTO tickerLangDTO = new TickerLangDTO();
            tickerLangDTO.setTickerShortDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_SHORT_DESCRIPTION + lang));
            tickerLangDTO.setTickerLongDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_LONG_DESCRIPTION + lang));
            tickerLangDTO.setSectorName(results.getString(DBConstants.LangSpecificDatabaseColumns.SECTOR_NAME + lang));
            tickerLangDTO.setTickerCountryDesc(results.getString(DBConstants.LangSpecificDatabaseColumns.TICKER_COUNTRY_DESC + lang));
            tickerLangDTOMap.put(lang, tickerLangDTO);
        }

        tickerDTO.setTickerLangDTOMap(tickerLangDTOMap);
    }

    public static String getUpdateIndexColumnString(List<String> supportedLang) {
        if (updateIndexColumnStringNew == null) {
            StringBuilder updateTickerStringBuilder = new StringBuilder();
            updateTickerStringBuilder.append(StringUtils.join(TICKER_COLUMNS, UPDATE_PLACEHOLDER)).append(UPDATE_PLACEHOLDER);//join TICKER_COLUMNS with =?,"
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_TICKER_COLUMNS) {
                    lang = lang.toUpperCase();
                    updateTickerStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(UPDATE_PLACEHOLDER);
                }
            }
            updateTickerStringBuilder.append(StringUtils.join(INDEX_COLUMNS_NEW, UPDATE_PLACEHOLDER)).append(UPDATE_PLACEHOLDER);//join TICKER_COLUMNS with =?,"
            updateTickerStringBuilder.append(StringUtils.join(INDEX_SNAP_COLUMNS, UPDATE_PLACEHOLDER)).append(QUERY_EQUAL).append(SQL_QUESTION_MARK);//join TICKER_COLUMNS with =?,"
            updateIndexColumnStringNew = updateTickerStringBuilder.toString();
        }
        return updateIndexColumnStringNew;
    }

    public static String getInsertIndexColumnString(List<String> supportedLang) {
        if (insertIndexColumnStringNew == null) {
            StringBuilder insertTickerStringBuilder = new StringBuilder();
            insertTickerStringBuilder.append(StringUtils.join(TICKER_COLUMNS, IConstants.Delimiter.COMMA)).append(IConstants.Delimiter.COMMA);//join TICKER_COLUMNS with ","
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_TICKER_COLUMNS) {
                    lang = lang.toUpperCase();
                    insertTickerStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(IConstants.Delimiter.COMMA);
                }
            }
            insertTickerStringBuilder.append(StringUtils.join(INDEX_COLUMNS_NEW, IConstants.Delimiter.COMMA)).append(IConstants.Delimiter.COMMA);//join TICKER_COLUMNS with ","
            insertTickerStringBuilder.append(StringUtils.join(INDEX_SNAP_COLUMNS, IConstants.Delimiter.COMMA));//join TICKER_COLUMNS with ","
            insertIndexColumnStringNew = insertTickerStringBuilder.toString();
        }
        return insertIndexColumnStringNew;
    }

    public static String getIndexInsertColumnParams(List<String> supportedLang) {
        if (insertIndexColumnParamsNew == null) {
            insertIndexColumnParamsNew = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK, DBConstants.CommonDatabaseParams.SQL_COMMA,
                    TICKER_COLUMNS.length + INDEX_COLUMNS_NEW.length + INDEX_SNAP_COLUMNS.length + LANGUAGE_SPECIFIC_TICKER_COLUMNS.length * supportedLang.size());
        }
        return insertIndexColumnParamsNew;
    }

    /**
     * build insert equity ticker sql string
     *
     * @return SQL String
     */
    public static String getInsertIndicesSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_INSERT);
        sql.append(DBConstants.TablesIMDB.TABLE_INDICES);
        sql.append(SQL_BRACKET_OPEN);
        sql.append(getInsertIndexColumnString(supportedLanguages)).append(INSERT_TICKER_SERIAL);
        sql.append(SQL_VALUES);
        sql.append(getIndexInsertColumnParams(supportedLanguages)).append(INSERT_TICKER_SERIAL_PARAMS);
        sql.append(SQL_BRACKET_CLOSE);
        return sql.toString();
    }

    /**
     * build update equity ticker sql string
     *
     * @return SQL String
     */
    public static String getUpdateIndicesSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(DBConstants.TablesIMDB.TABLE_INDICES);
        sql.append(SQL_SET);
        sql.append(getUpdateIndexColumnString(supportedLanguages));
        sql.append(INDEX_TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    /**
     * Method to get SQL for get indices by ticker serials
     * @param tickerSerialList list of ticker serials
     * @return query
     */
    public static String getIndicesByTickerSerial(List<String> tickerSerialList){
        String tickerSerials = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);

        StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_INDICES);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.TICKER_SERIAL).append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(tickerSerials);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);
        return queryBuilder.toString();
    }

    /**
     * Search Index by Symbol and Exchange
     *
     * @param symbol symbol code
     * @param exchange exchange code
     * @return IndexDTO
     */
    public static String getIndexBySymbolExchange(String symbol, String exchange){

        StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_INDICES)
                .append(QUERY_WHERE)
                .append(DBConstants.DatabaseColumns.SOURCE_ID)
                    .append(QUERY_EQUAL).append(QUERY_QUOTE).append(exchange).append(QUERY_QUOTE)
                .append(QUERY_AND)
                    .append(QUERY_UPPER)
                        .append(QUERY_BRACKET_OPEN)
                    .append(DBConstants.DatabaseColumns.TICKER_ID)
                .append(QUERY_BRACKET_CLOSE)
                    .append(QUERY_EQUAL).append(QUERY_QUOTE).append(symbol).append(QUERY_QUOTE)
                .append(QUERY_AND)
                    .append(QUERY_STATUS_FILTER);
        return queryBuilder.toString();
    }

    public static int setUpdateTickerSetValues(PreparedStatement preparedStatement,
                                               TickerDTO ticker, int index, List<String> supportedLanguages) throws SQLException {

        preparedStatement.setString(index++, ticker.getCurrencyId());
        preparedStatement.setDouble(index++, ticker.getDecimalPlaces());
        preparedStatement.setString(index++, ticker.getDisplayTicker());
        preparedStatement.setInt(index++, ticker.getInstrumentTypeId());
        preparedStatement.setString(index++, ticker.getIsinCode());
        preparedStatement.setString(index++, ticker.getSourceId());
        preparedStatement.setString(index++, ticker.getTickerId());
        preparedStatement.setInt(index++, ticker.getStatus());
        preparedStatement.setTimestamp(index++, ticker.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, ticker.getLastSyncTime());
        preparedStatement.setString(index++, ticker.getTickerCountryCode());
        preparedStatement.setInt(index++, ticker.getEligibility());

        preparedStatement.setInt(index++, ticker.getTickerClassLevel1());
        preparedStatement.setInt(index++, ticker.getTickerClassLevel2());
        preparedStatement.setInt(index++, ticker.getTickerClassLevel3());

        if (supportedLanguages != null) {
            for (String lang : supportedLanguages) {
                if (ticker.getTickerLangDTOMap() != null && ticker.getTickerLangDTOMap().get(lang) != null) {
                    preparedStatement.setString(index++, ticker.getTickerLangDTOMap().get(lang).getTickerShortDesc());
                    preparedStatement.setString(index++, ticker.getTickerLangDTOMap().get(lang).getTickerLongDesc());
                    preparedStatement.setString(index++, ticker.getTickerLangDTOMap().get(lang).getSectorName());
                    preparedStatement.setString(index++, ticker.getTickerLangDTOMap().get(lang).getTickerCountryDesc());
                }
            }
        }
        return index;
    }

    public static int setUpdateIndexSetValues(PreparedStatement preparedStatement, IndexDTO indexDTO, List<String> supportedLanguages) throws SQLException {
        int index = 1;
        index = setUpdateTickerSetValues(preparedStatement, indexDTO, index, supportedLanguages);
        preparedStatement.setString(index++, indexDTO.getRelatedExchanges());
        preparedStatement.setString(index++, indexDTO.getRelatedCountries());
        preparedStatement.setString(index++, indexDTO.getRelatedRegions());

        preparedStatement.setString(index++, indexDTO.getSectorId());
        preparedStatement.setInt(index++, indexDTO.getProvider());
        preparedStatement.setDate(index++, indexDTO.getLaunchDate());

        preparedStatement.setString(index++, indexDTO.getConstituents());
        preparedStatement.setString(index++, indexDTO.getUpdateFrequency());

        index = setUpdateIndexSetSnapValues(index, preparedStatement, indexDTO);

        preparedStatement.setLong(index, indexDTO.getTickerSerial());
        return index;
    }

    public static int setUpdateIndexSetSnapValues(int index, PreparedStatement preparedStatement, IndexDTO indexDTO) throws SQLException{
        index = setValue(preparedStatement, index, indexDTO.getLastTradePrice(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getLastTradeDate(), null, Types.TIMESTAMP);

        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturnQtd(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturnYtd(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturn1Y(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturn2Y(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturn3Y(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturn5Y(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getAnnualizedReturnLifetime(), null, Types.DOUBLE);

        index = setValue(preparedStatement, index, indexDTO.getMonthlyHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getMonthlyHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getMonthlyLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getMonthlyLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getQuarterlyHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getQuarterlyHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getQuarterlyLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getQuarterlyLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getYtdHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getYtdHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getYtdLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getYtdLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getHighPriceOf52Weeks(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getHighPriceDateOf52Weeks(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getLowPriceOf52Weeks(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getLowPriceDateOf52Weeks(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getTwoYearHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getTwoYearHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getTwoYearLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getTwoYearLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getThreeYearHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getThreeYearHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getThreeYearLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getThreeYearLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getLifetimeHigh(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getLifetimeHighDate(), null, Types.DATE);
        index = setValue(preparedStatement, index, indexDTO.getLifetimeLow(), null, Types.DOUBLE);
        index = setValue(preparedStatement, index, indexDTO.getLifetimeLowDate(), null, Types.DATE);

        index = setValue(preparedStatement, index, indexDTO.getCloseStartDate(), null, Types.DATE);

        return index;
    }

    private static int setValue(PreparedStatement preparedStatement, int index, Object value, Object def, int type) throws SQLException{
        if(value == null && def == null){
            preparedStatement.setNull(index++, type);
        }else{
            if(value == null){
                value = def;
            }
            switch (type){
                case Types.INTEGER:
                    preparedStatement.setInt(index++, (Integer) value);
                    break;
                case Types.DOUBLE:
                    preparedStatement.setDouble(index++, (Double) value);
                    break;
                case Types.DATE:
                    preparedStatement.setDate(index++, (Date) value);
                    break;
                case Types.TIMESTAMP:
                    preparedStatement.setTimestamp(index++, (Timestamp) value);
                    break;
                default:
                    preparedStatement.setString(index++, (String)value);
                    break;
            }
        }
        return index;
    }

    public static RequestDBDTO getDataCleanUpDBDTO(){
        if(cleanUpIMDBQuery == null){
            cleanUpIMDBQuery = DELETE_FROM + DBConstants.TablesIMDB.TABLE_INDICES + QUERY_WHERE + STATUS + QUERY_EQUAL + "0";
        }
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(cleanUpIMDBQuery);
        return requestDBDTO;
    }

    public static String getAllSnapUpdatedIndices(Timestamp lastRunTime){
        return QUERY_SELECT + TICKER_SERIAL + SQL_COMMA + LAST_TRADE_PRICE + SQL_COMMA + LAST_TRADE_DATE + SQL_COMMA + LAST_SYNC_TIME + FROM +
                DBConstants.TablesORACLE.TABLE_INDICES + ((lastRunTime != null) ? QUERY_WHERE + LAST_SYNC_TIME_FILTER : IConstants.EMPTY);
    }

    /**
     * set data indexDTO from given result set
     * @param results ResultSet
     * @throws SQLException
     */
    public static IndexDTO getIndexSnapDTO(ResultSet results) throws SQLException {
        IndexDTO index = new IndexDTO();
        index.setTickerSerial(results.getLong(TICKER_SERIAL));
        index.setLastTradePrice(DBUtils.getDoubleFromResultSet(results,DBConstants.DatabaseColumns.LAST_TRADE_PRICE));
        index.setLastTradeDate(results.getTimestamp(DBConstants.DatabaseColumns.LAST_TRADE_DATE));
        index.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));
        return index;
    }

    /**
     * build update equity ticker sql string
     *
     * @return SQL String
     */
    public static String getUpdateIndexSnapshotSql() {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(DBConstants.TablesIMDB.TABLE_INDICES);
        sql.append(SQL_SET);
        sql.append(LAST_TRADE_PRICE).append(UPDATE_PLACEHOLDER);
        sql.append(LAST_TRADE_DATE).append(UPDATE_PLACEHOLDER);
        sql.append(LAST_SYNC_TIME).append(QUERY_EQUAL).append(SQL_QUESTION_MARK);
        sql.append(INDEX_TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    public static int setUpdateIndexSnapSetValues(PreparedStatement preparedStatement, IndexDTO indexDTO) throws SQLException {
        int index = 1;
        preparedStatement.setDouble(index++, indexDTO.getLastTradePrice());
        preparedStatement.setTimestamp(index++, indexDTO.getLastTradeDate());
        preparedStatement.setTimestamp(index++, indexDTO.getLastSyncTime());
        preparedStatement.setLong(index, indexDTO.getTickerSerial());
        return index;
    }

    //endregion

}
