package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.tickers.EquityTickerDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 4/2/2014.
 */
public class EquityTickerDBHelper {

    private static final String EQUITY_TICKER_QUERY = "SELECT * FROM TICKERS";
    private static final String TICKER_UPDATE_WHERE = " WHERE TICKER_SERIAL = ?";
    private static final String INSERT_TICKER_SERIAL = ",TICKER_SERIAL";
    private static final String LAST_SYNC_TIME_FILTER = "  LAST_SYNC_TIME > ? ";
    private static final String QUERY_WHERE = DBConstants.CommonDatabaseParams.QUERY_WHERE;
    private static final String SQL_VALUES = DBConstants.CommonDatabaseParams.SQL_VALUES;
    private static final String SQL_BRACKET_OPEN = DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN;
    private static final String SQL_BRACKET_CLOSE = DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
    private static final String SQL_UPDATE = DBConstants.CommonDatabaseParams.QUERY_UPDATE;
    private static final String SQL_SET = DBConstants.CommonDatabaseParams.QUERY_SET;
    private static final String SQL_INSERT = DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO;
    private static final String SQL_TICKER = DBConstants.TablesIMDB.TABLE_TICKERS;
    private static final String INSERT_TICKER_SERIAL_PARAMS = DBConstants.CommonDatabaseParams.SQL_COMMA + DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;
    private static final String EQUITY_TICKER_FILTER = " TICKER_SERIAL > 0";
    private static final String TICKER_SERIAL = DBConstants.DatabaseColumns.TICKER_SERIAL;
    private static final String QUERY_AND = DBConstants.CommonDatabaseParams.QUERY_AND;
    private static final String QUERY_QUOTE = DBConstants.CommonDatabaseParams.QUERY_QUOTE;
    private static final String SELECT = DBConstants.CommonDatabaseParams.QUERY_SELECT;
    private static final String VWAP = DBConstants.DatabaseColumns.VWAP;
    private static final String FROM = DBConstants.CommonDatabaseParams.FROM;
    private static final String EQUAL = "=";

    private static String updateEquityTickerColumnString;
    private static String insertEquityTickerColumnString;
    private static String insertEquityTickerColumnParams;

    private static final String UPDATE_PLACEHOLDER = TickerDBHelper.UPDATE_PLACEHOLDER;

    private static final String[] EQUITY_TICKER_COLUMNS = new String[]{LOT_SIZE, IS_MAIN_STOCK, SECTOR_NAME_LAN, UNIT,
            NO_OF_SHARES, LISTED_IDX, SECTOR_ID, TICKER_SOURCE, PER, USE_SUBUNIT, PREV_NO_OF_SHARES, STOCK_MARKET_CAP, STOCK_MARKET_CAP_USD };


    /**
     * get Query to get equity tickers form tickers table.
     * adding condition : TICKER_SERIAL > 0
     *
     * @param lastRunTime last run time
     * @return Query
     */
    public static String getEquityTickerQuery(Timestamp lastRunTime) {
        return EQUITY_TICKER_QUERY + QUERY_WHERE + EQUITY_TICKER_FILTER + ((lastRunTime != null) ? DBConstants.CommonDatabaseParams.QUERY_AND + LAST_SYNC_TIME_FILTER : IConstants.EMPTY);
    }

    public static void setEquityTickerData(EquityTickerDTO tickerDTO, ResultSet results, List<String> supportedLanguages) throws SQLException {
        TickerDBHelper.setTickerData(tickerDTO, results, supportedLanguages);
        tickerDTO.setLotSize(results.getInt(LOT_SIZE));
        tickerDTO.setUnit(results.getString(UNIT));
        tickerDTO.setSectorCode(results.getString(SECTOR_ID));
        tickerDTO.setSectorNameLn(results.getString(SECTOR_NAME_LAN));
        tickerDTO.setMainStock(results.getInt(IS_MAIN_STOCK));
        tickerDTO.setNoOfShares(results.getLong(NO_OF_SHARES));
        tickerDTO.setUseSubunit(results.getInt(USE_SUBUNIT));
        tickerDTO.setListedIndexes(results.getString(LISTED_IDX));
        tickerDTO.setTickerSource(results.getString(TICKER_SOURCE));
        if (results.getString(PER) != null) {
            tickerDTO.setPer(results.getDouble(PER));
        } else {
            tickerDTO.setPer(null);
        }

        tickerDTO.setPrevNoOfShares(results.getLong(PREV_NO_OF_SHARES));
        tickerDTO.setStockMarketCap(results.getLong(STOCK_MARKET_CAP));
        tickerDTO.setStockMarketCapUSD(results.getLong(STOCK_MARKET_CAP_USD));
    }

    public static TickerDTO getEquityTickerData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        EquityTickerDTO ticker = new EquityTickerDTO();
        setEquityTickerData(ticker, results, supportedLanguages);
        return ticker;
    }

    /**
     * build insert equity ticker sql string
     *
     * @return SQL String
     */
    public static String getInsertEquityTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_INSERT);
        sql.append(SQL_TICKER);
        sql.append(SQL_BRACKET_OPEN);
        sql.append(getInsertTickerColumnString(supportedLanguages)).append(INSERT_TICKER_SERIAL);
        sql.append(SQL_VALUES);
        sql.append(getEquityTickerInsertColumnParams(supportedLanguages)).append(INSERT_TICKER_SERIAL_PARAMS);
        sql.append(SQL_BRACKET_CLOSE);
        return sql.toString();
    }

    /**
     * build update equity ticker sql string
     *
     * @return SQL String
     */
    public static String getUpdateEquityTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(SQL_TICKER);
        sql.append(SQL_SET);
        sql.append(getUpdateTickerColumnString(supportedLanguages));
        sql.append(TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    public static String getUpdateTickerColumnString(List<String> supportedLang) {
        if (updateEquityTickerColumnString == null) {
            updateEquityTickerColumnString = StringUtils.join(EQUITY_TICKER_COLUMNS, UPDATE_PLACEHOLDER) + UPDATE_PLACEHOLDER;//join TICKER_COLUMNS with "=?,"
            updateEquityTickerColumnString += TickerDBHelper.getUpdateTickerColumnString(supportedLang);
        }
        return updateEquityTickerColumnString;
    }

    public static String getInsertTickerColumnString(List<String> supportedLang) {
        if (insertEquityTickerColumnString == null) {
            insertEquityTickerColumnString = StringUtils.join(EQUITY_TICKER_COLUMNS, IConstants.Delimiter.COMMA) + IConstants.Delimiter.COMMA;//join TICKER_COLUMNS with ","
            insertEquityTickerColumnString += TickerDBHelper.getInsertTickerColumnString(supportedLang);
        }
        return insertEquityTickerColumnString;
    }

    private static String getEquityTickerInsertColumnParams(List<String> supportedLang) {
        if (insertEquityTickerColumnParams == null) {
            insertEquityTickerColumnParams = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK, DBConstants.CommonDatabaseParams.SQL_COMMA,
                    EQUITY_TICKER_COLUMNS.length);
            insertEquityTickerColumnParams += DBConstants.CommonDatabaseParams.SQL_COMMA + TickerDBHelper.getTickerInsertColumnParams(supportedLang);
        }
        return insertEquityTickerColumnParams;
    }

    public static void setUpdateEquityValues(PreparedStatement preparedStatement, EquityTickerDTO ticker, List<String> supportedLanguages) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, ticker.getLotSize());
        preparedStatement.setInt(index++, ticker.getMainStock());
        preparedStatement.setString(index++, ticker.getSectorNameLn());
        preparedStatement.setString(index++, ticker.getUnit());
        preparedStatement.setLong(index++, ticker.getNoOfShares());
        preparedStatement.setString(index++, ticker.getListedIndexes());
        preparedStatement.setString(index++, ticker.getSectorCode());
        if (ticker.getTickerId() != null && ticker.getSourceId() != null) {
            preparedStatement.setString(index++, ticker.getSourceId().toUpperCase() + Character.toString(IConstants.Delimiter.TILDE) + ticker.getTickerId().toUpperCase());
        } else {
            preparedStatement.setString(index++, "");
        }
        if (ticker.getPer() != null) {
            preparedStatement.setDouble(index++, ticker.getPer());
        } else {
            preparedStatement.setString(index++, null);
        }
        preparedStatement.setInt(index++, ticker.getUseSubunit());
        preparedStatement.setLong(index++, ticker.getPrevNoOfShares());
        preparedStatement.setDouble(index++, ticker.getStockMarketCap());
        preparedStatement.setDouble(index++, ticker.getStockMarketCapUSD());

        index = TickerDBHelper.setUpdateTickerSetValues(preparedStatement, ticker, index, supportedLanguages);

        preparedStatement.setLong(index, ticker.getTickerSerial());
    }

    public static List<String> getEquityTickerColumns(List<String> supportedLanguages) {
        List<String> columns = new ArrayList<String>(TickerDBHelper.getAllTickerColumns(supportedLanguages));
        columns.addAll(Arrays.asList(EQUITY_TICKER_COLUMNS));
        return columns;
    }

    public static String getTickersByTickerSerials(Collection<String> tickerSerialList, List<String> supportedLanguages) {
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String tickers = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);
        String filter = QUERY_WHERE + TICKER_SERIAL;
        if (tickers.indexOf(IConstants.Delimiter.COMMA) > 0) {
            filter += QUERY_IN + QUERY_BRACKET_OPEN + tickers + QUERY_BRACKET_CLOSE;
        } else {
            filter += QUERY_EQUAL + tickers;
        }
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter;
    }

    public static String getTickersByTickerSerials(Collection<String> tickerSerialList, String sourceId, List<String> supportedLanguages) {
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String tickers = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);
        String filter = QUERY_WHERE + TICKER_SERIAL;
        if (tickers.indexOf(IConstants.Delimiter.COMMA) > 0) {
            filter += QUERY_IN + QUERY_BRACKET_OPEN + tickers + QUERY_BRACKET_CLOSE;
        } else {
            filter += QUERY_EQUAL + tickers;
        }
        filter += QUERY_AND + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + sourceId + QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter;
    }

    public static String getTickersBySymbolExchange(String tickerId, String sourceId, List<String> supportedLanguages) {
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + QUERY_UPPER + QUERY_BRACKET_OPEN + TICKER_ID + QUERY_BRACKET_CLOSE + QUERY_EQUAL +
                QUERY_QUOTE + tickerId.toUpperCase() + QUERY_QUOTE + QUERY_AND + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE +
                sourceId + QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter;
    }

    public static String getTickersByTickerIdSourceIdComposite(Set<String> sourceTicker, List<String> supportedLanguages){
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + TICKER_SOURCE + QUERY_IN + QUERY_BRACKET_OPEN + QUERY_QUOTE +
                StringUtils.join(sourceTicker, IConstants.Delimiter.QUOTE+IConstants.Delimiter.COMMA+IConstants.Delimiter.QUOTE)
                +QUERY_QUOTE+ QUERY_BRACKET_CLOSE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter;
    }

    public static RequestDBDTO getTickersBySourceId(String exchange, String instrumentTypes, List<String> supportedLanguages){
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + exchange + QUERY_QUOTE;
        filter += QUERY_AND + TICKER_CLASS_L3 + QUERY_IN + QUERY_BRACKET_OPEN + instrumentTypes + QUERY_BRACKET_CLOSE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLanguages);
        requestDBDTO.setQuery(selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter);
        return requestDBDTO;
    }

    public static RequestDBDTO getTickersBySectorAndSourceId(String sectorCode, String exchange, String instrumentTypes, List<String> supportedLanguages){
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + SECTOR_ID + QUERY_EQUAL + QUERY_QUOTE + sectorCode + QUERY_QUOTE + QUERY_AND +
                SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + exchange + QUERY_QUOTE;
        filter += QUERY_AND + TICKER_CLASS_L3 + QUERY_IN + QUERY_BRACKET_OPEN + instrumentTypes + QUERY_BRACKET_CLOSE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLanguages);
        requestDBDTO.setQuery(selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter);
        return requestDBDTO;
    }

    public static RequestDBDTO getTickersByGicsL2AndSourceId(String gicsL2Code, String exchange, String instrumentTypes, List<String> supportedLanguages){
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + GICSL2_CODE + QUERY_EQUAL + QUERY_QUOTE + gicsL2Code + QUERY_QUOTE + QUERY_AND +
                SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + exchange + QUERY_QUOTE;
        filter += QUERY_AND + TICKER_CLASS_L3 + QUERY_IN + QUERY_BRACKET_OPEN + instrumentTypes + QUERY_BRACKET_CLOSE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLanguages);
        requestDBDTO.setQuery(selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter);
        return requestDBDTO;
    }

    public static RequestDBDTO getTickersByGicsL3AndSourceId(String gicsL3Code, String exchange, String instrumentTypes, List<String> supportedLanguages){
        List<String> tickerColumns = getEquityTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + GICSL3_CODE + QUERY_EQUAL + QUERY_QUOTE + gicsL3Code + QUERY_QUOTE + QUERY_AND +
                SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + exchange + QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        filter += QUERY_AND + TICKER_CLASS_L3 + QUERY_IN + QUERY_BRACKET_OPEN + instrumentTypes + QUERY_BRACKET_CLOSE;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLanguages);
        requestDBDTO.setQuery(selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter);
        return requestDBDTO;
    }

    public static RequestDBDTO getVWAPForTickersQuery(Long tickerSerial) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(SELECT + VWAP + FROM + SQL_TICKER + QUERY_WHERE + TICKER_SERIAL + EQUAL + tickerSerial);
        return requestDBDTO;
    }

    public static RequestDBDTO getSearchEquityBYCompanyQuery(int companyId, String instrumentTypes, List<String> supportedLanguages){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        String filter = QUERY_WHERE + DBConstants.DatabaseColumns.COMPANY_ID + QUERY_EQUAL + companyId +
                QUERY_AND + DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND +
                CompanyDBHelper.getCompanyFilterQuery(instrumentTypes);
        requestDBDTO.setQuery(QUERY_SELECT_ALL_FROM + DBConstants.TablesIMDB.TABLE_TICKERS + filter);
        requestDBDTO.setSupportedLang(supportedLanguages);
        return requestDBDTO;
    }
}

