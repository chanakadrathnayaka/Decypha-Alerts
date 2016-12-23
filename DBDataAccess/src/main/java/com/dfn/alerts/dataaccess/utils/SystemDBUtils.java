package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.beans.dcms.*;
import com.dfn.alerts.beans.tickers.*;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.tickers.EquityTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.FixedIncomeTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.FundTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.IndexTickerDBHelper;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lasanthak
 * Date: 3/7/13
 * Time: 3:52 PM
 */
public class SystemDBUtils {

    private SystemDBUtils() {

    }

    private static final Logger LOG = LogManager.getLogger(SystemDBUtils.class);

    private static final String SQL_UPDATE = " UPDATE ";

    private static final String SQL_SET = " SET ";

    //source table
    private static final String SQL_SOURCES = " SOURCES ";
    private static final String SOURCE_INSERT_SQL = "INSERT INTO SOURCES (SOURCE_ID,STATUS,OPEN_TIME,CLOSE_TIME,SHORT_DESCRIPTION_LAN," +
            "LONG_DESCRIPTION_LAN,LAST_UPDATED_TIME,DEFAULT_CURRENCY ,TIMEZONE_ID,DISPLAY_CODE,DEFAULT_DECIMAL_PLACES,IS_VIRTUAL_EXCHANGE," +
            "IS_EXPAND_SUBMKTS,WEEK_START,MAIN_INDEX_SOURCE,MAIN_INDEX_TICKER,MAIN_INDEX_TICKER_SERIAL,COUNTRY_CODE,LISTED_STOCKS_COUNT," +
            "SECTOR_COUNT,IS_DEFAULT,DEFAULT_WINDOW_TYPES,SCREENER_CODE,LAST_SYNC_TIME,MARKET_CAP,IS_REALTIME_FEED,OFF_DAYS," +
            "MARKET_OPEN_TIME, MARKET_CLOSE_TIME, SOURCE_SERIAL) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SOURCE_UPDATE_SQL = "SOURCE_ID=?,STATUS =?,OPEN_TIME=?,CLOSE_TIME=?,SHORT_DESCRIPTION_LAN=?,LONG_DESCRIPTION_LAN=?,LAST_UPDATED_TIME=?," +
            "DEFAULT_CURRENCY =?,TIMEZONE_ID=?,DISPLAY_CODE=?,DEFAULT_DECIMAL_PLACES=?,IS_VIRTUAL_EXCHANGE=?," +
            "IS_EXPAND_SUBMKTS=?,WEEK_START=?,MAIN_INDEX_SOURCE=?,MAIN_INDEX_TICKER=?,MAIN_INDEX_TICKER_SERIAL=?," +
            "COUNTRY_CODE=?,LISTED_STOCKS_COUNT=?,SECTOR_COUNT=?, IS_DEFAULT=?, DEFAULT_WINDOW_TYPES=?, SCREENER_CODE=?, " +
            "LAST_SYNC_TIME=?, MARKET_CAP=?, IS_REALTIME_FEED=?, OFF_DAYS=?, MARKET_OPEN_TIME=?, MARKET_CLOSE_TIME = ? " +
            "WHERE SOURCE_SERIAL=?";

    //Production db data sync SP IN/OUT params.
    public static final String SP_IN_PARA_LAST_UPDATE_DATE = "P_LAST_SYNC_DATE";
    public static final String SP_IN_PARA_P_TABLE = "P_TABLE";
    public static final String SP_IN_PARA_P_GICS = "P_GICS";
    public static final String SP_IN_PARA_P_SOURCE_ID = "P_SOURCE_ID";
    public static final String SP_IN_PARA_P_SYNC_ID = "P_SYNC_ID";
    public static final String SP_IN_PARA_P_COUNTRY_CODE = "P_COUNTRY_CODE";
    public static final String SP_IN_PARA_P_SCHEDULER_TYPE = "P_SCHEDULER_TYPE";
    public static final String SP_IN_PARA_P_START_DATE = "P_START_DATE";
    public static final String SP_IN_PARA_P_END_DATE = "P_END_DATE";

    // Data sync status
    private static final String DATA_SYNC_STATUS_UPDATE = " UPDATE LOG_DATA_SYNC_STATUS SET LAST_ID=?, LAST_UPDATED_TIME=?, DESCRIPTION=?, PARAMS=? WHERE ID=? AND TYPE=? ";
    private static final String DATA_SYNC_STATUS_INSERT = " INSERT INTO LOG_DATA_SYNC_STATUS (LAST_ID, LAST_UPDATED_TIME, DESCRIPTION, PARAMS, ID, TYPE) VALUES (?,?,?,?,?,?) ";

    //fund benchmarks
    private static final String UPDATE_FUND_BENCHMARK_SQL = "UPDATE FUND_BENCHMARKS SET BENCHMARK_RATE = ?, LAST_UPDATED_TIME = ? WHERE TICKER_SERIAL = ? AND BENCHMARK_TICKER = ?";
    private static final String INSERT_FUND_BENCHMARK_SQL = "INSERT INTO FUND_BENCHMARKS(BENCHMARK_RATE, LAST_UPDATED_TIME, TICKER_SERIAL, BENCHMARK_TICKER) VALUES (?,?,?,?)";

    //adjusted price snapshot
    public static final String QUERY_UPDATE_ADJUSTED_PRICE_SNAPSHOT = "UPDATE TICKER_SNAPSHOT_ADJUSTED SET TRADE_PRICE = ?, TRADE_PRICE_UPDATED_TIME= ?,BEST_ASK_PRICE= ?,BEST_ASK_PRICE_UPDATED_TIME= ?,BEST_BID_PRICE= ?,BEST_BID_PRICE_UPDATED_TIME= ? WHERE TICKER_SERIAL= ?";
    public static final String QUERY_INSERT_ADJUSTED_PRICE_SNAPSHOT = "INSERT INTO TICKER_SNAPSHOT_ADJUSTED (TRADE_PRICE,TRADE_PRICE_UPDATED_TIME,BEST_ASK_PRICE,BEST_ASK_PRICE_UPDATED_TIME,BEST_BID_PRICE,BEST_BID_PRICE_UPDATED_TIME,TICKER_SERIAL) VALUES (?,?,?,?,?,?,?)";

    public static final String SQL_TICKER_SNAP_DATA_CORRECTION = "DELETE FROM {0} WHERE TICKER_SERIAL IN ( SELECT ts.TICKER_SERIAL FROM {0} ts  left join TICKERS t  ON" +
            " ((t.TICKER_ID=ts.TICKER_ID AND t.SOURCE_ID=ts.SOURCE_ID)) WHERE t.TICKER_SERIAL <> ts.TICKER_SERIAL OR t.STATUS=0 OR t.TICKER_SERIAL IS NULL )";


    public static String getInsertTickerSQL(IConstants.AssetType type, boolean isDelayed, List<String> supportedLanguages) {
        String sql = null;

        switch (type) {
            case EQUITY:
                sql = EquityTickerDBHelper.getInsertEquityTickerSql(supportedLanguages);
                break;
            case MUTUAL_FUNDS:
                sql = FundTickerDBHelper.getInsertFundTickerSql(supportedLanguages);
                break;
            case FIXED_INCOME:
                sql = FixedIncomeTickerDBHelper.getInsertFixedIncomeTickerSql(supportedLanguages);
                break;
            case INDEX:
                sql = IndexTickerDBHelper.getInsertIndexTickersSql(isDelayed, supportedLanguages);
                break;
        }
        return sql;
    }

    /**
     * get insert sql for insert sources
     *
     * @return Sql string
     */
    public static String getInsertSourceSql() {
        return SOURCE_INSERT_SQL;
    }

    public static String getUpdateTickerSql(IConstants.AssetType type, boolean isDelayed, List<String> supportedLanguages) {
        String sql = null;

        switch (type) {
            case EQUITY:
                sql = EquityTickerDBHelper.getUpdateEquityTickerSql(supportedLanguages);
                break;
            case MUTUAL_FUNDS:
                sql = FundTickerDBHelper.getUpdateFundTickerSql(supportedLanguages);
                break;
            case FIXED_INCOME:
                sql = FixedIncomeTickerDBHelper.getUpdateFixedIncomeTickerSql(supportedLanguages);
                break;
            case INDEX:
                sql = IndexTickerDBHelper.getUpdateIndexTickerSql(isDelayed, supportedLanguages);
                break;
        }
        return sql;
    }

    public static String getUpdateSourceSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(SQL_UPDATE);
        sql.append(SQL_SOURCES);
        sql.append(SQL_SET);
        sql.append(SOURCE_UPDATE_SQL);
        return sql.toString();
    }

    /**
     * get update prepared statement for fund benchmarks
     *
     * @return prepared statement sql
     */
    public static String getUpdateFundBenchmarksSql() {
        return UPDATE_FUND_BENCHMARK_SQL;
    }

    /**
     * get insert prepared statement for fund benchmarks
     *
     * @return prepared statement sql
     */
    public static String getInsertFundBenchmarksSql() {
        return INSERT_FUND_BENCHMARK_SQL;
    }

    public static void setTickerValuesToStmt(PreparedStatement preparedStatement,
                                             TickerDTO ticker,
                                             IConstants.AssetType type, List<String> supportedLanguages) throws SQLException {
        switch (type) {
            case EQUITY:
                EquityTickerDBHelper.setUpdateEquityValues(preparedStatement, (EquityTickerDTO) ticker, supportedLanguages);
                break;
            case MUTUAL_FUNDS:
                FundTickerDBHelper.setUpdateFundTickerValues(preparedStatement, (FundTickerDTO) ticker, supportedLanguages);
                break;
            case FIXED_INCOME:
                FixedIncomeTickerDBHelper.setUpdateFixedIncomeTickerValues(preparedStatement, (FixedIncomeTickerDTO) ticker, supportedLanguages);
                break;
            case INDEX:
                IndexTickerDBHelper.setUpdateIndexTickerValues(preparedStatement, (EquityTickerDTO) ticker, supportedLanguages);
        }
    }

    /**
     * set prepared statement values for update sources
     *
     * @param preparedStatement prepared statement
     * @param ticker            sourceDTO
     * @throws SQLException
     */
    public static void setSourceValues(PreparedStatement preparedStatement, SourceDTO ticker) throws SQLException {
        int index = 1;
        java.util.Date today = new java.util.Date();
        preparedStatement.setString(index++, ticker.getSourceId());
        preparedStatement.setInt(index++, ticker.getStatus());
        preparedStatement.setDate(index++, new Date(ticker.getOpenTime() != null ? ticker.getOpenTime().getTime() : today.getTime()));
        preparedStatement.setDate(index++, new Date(ticker.getCloseTime() != null ? ticker.getCloseTime().getTime() : today.getTime()));
        preparedStatement.setString(index++, ticker.getShortDescriptionLan());
        preparedStatement.setString(index++, ticker.getLongDescriptionLan());
        preparedStatement.setDate(index++, new Date(ticker.getLastUpdatedTime() != null ? ticker.getLastUpdatedTime().getTime() : today.getTime()));
        preparedStatement.setString(index++, ticker.getDefaultCurrency());
        preparedStatement.setInt(index++, ticker.getTimezoneId());
        preparedStatement.setString(index++, ticker.getDisplayCode());
        preparedStatement.setInt(index++, ticker.getDefaultDecimalPlaces());
        preparedStatement.setInt(index++, ticker.getVirtualExchange());
        preparedStatement.setInt(index++, ticker.getExpandSubmkts());
        preparedStatement.setInt(index++, ticker.getWeekStart());
        preparedStatement.setString(index++, ticker.getMainIndexSource());
        preparedStatement.setString(index++, ticker.getMainIndexTicker());
        preparedStatement.setLong(index++, ticker.getMainIndexTickerSerial());
        preparedStatement.setString(index++, ticker.getCountryCode());
        preparedStatement.setInt(index++, ticker.getListedStocks());
        preparedStatement.setInt(index++, ticker.getSectorCount());
        preparedStatement.setInt(index++, ticker.getIsDefaultMkt());
        preparedStatement.setString(index++, ticker.getWindowTypes());
        preparedStatement.setString(index++, ticker.getScreenerCode());
        preparedStatement.setTimestamp(index++, ticker.getLastSyncTime());
        preparedStatement.setDouble(index++, ticker.getMarketCap());
        preparedStatement.setInt(index++, ticker.getRealTimeFeed());
        preparedStatement.setString(index++, ticker.getOffDays());
        preparedStatement.setString(index++, ticker.getMarketOpenInLocalTime());
        preparedStatement.setString(index++, ticker.getMarketCloseInLocalTime());
        preparedStatement.setInt(index, ticker.getSourceSerial());
    }

    /**
     * set prepared statement values for update fund benchmarks
     *
     * @param preparedStatement prepared statement
     * @param ticker            fund benchmark
     * @throws SQLException
     */
    public static void setFundBenchmarkValues(PreparedStatement preparedStatement, FundBenchmarkDTO ticker) throws SQLException {
        int index = 1;
        preparedStatement.setDouble(index++, ticker.getBenchmarkRate());
        preparedStatement.setTimestamp(index++, ticker.getBenchmarkLastUpdatedTime());
        preparedStatement.setLong(index++, ticker.getFundTickerSerial());
        preparedStatement.setLong(index, ticker.getTickerSerial());
    }

    /**
     * set prepared statement values for update sources
     *
     * @param preparedStatement prepared statement
     * @param ticker            KpiDTO
     * @throws SQLException
     */
    public static void setKPIValues(PreparedStatement preparedStatement, KpiDTO ticker) throws SQLException {
        int index = 1;
        java.util.Date today = new java.util.Date();

        // company data
        preparedStatement.setInt(index++, ticker.getCompanyId());
        preparedStatement.setInt(index++, ticker.getDefinitionId());
        preparedStatement.setDate(index++, ticker.getDate() != null ? new Date(ticker.getDate().getTime()) : null);
        preparedStatement.setDouble(index++, ticker.getValue());
        preparedStatement.setDate(index++, new Date(ticker.getLastUpdatedTime() != null ? ticker.getLastUpdatedTime().getTime() : today.getTime()));
        preparedStatement.setInt(index++, ticker.getYear());
        preparedStatement.setInt(index++, ticker.getPeriod());
        preparedStatement.setTimestamp(index++, ticker.getLastSyncTime());
        preparedStatement.setInt(index++, ticker.getStatus());
        preparedStatement.setInt(index, ticker.getId());
    }

    /**
     * Get Update query for adjusted price snapshot
     *
     * @return
     */
    public static String getAdjustedPriceSnapshotUpdateQuery() {
        return QUERY_UPDATE_ADJUSTED_PRICE_SNAPSHOT;
    }

    /**
     * Get insert query for adjusted price snapshot
     *
     * @return
     */
    public static String getAdjustedPriceSnapshotInsertQuery() {
        return QUERY_INSERT_ADJUSTED_PRICE_SNAPSHOT;
    }

    /**
     * Method to set Adjusted price snapshot values to prepared statement
     * Used to update IMDB from RDBMS
     *
     * @param preparedStatement        Prepared statement
     * @param priceSnapshotAdjustedDTO price snapshot DTO
     * @throws SQLException exception on error
     */
    public static void setAdjustedPriceSnapshotValues(PreparedStatement preparedStatement, PriceSnapshotAdjustedDTO priceSnapshotAdjustedDTO) throws SQLException {
        int index = 1;

        preparedStatement.setDouble(index++, priceSnapshotAdjustedDTO.getTradePrice());
        if (priceSnapshotAdjustedDTO.getTradePriceUpdatedDate() != null) {
            preparedStatement.setDate(index++, new Date(priceSnapshotAdjustedDTO.getTradePriceUpdatedDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        preparedStatement.setDouble(index++, priceSnapshotAdjustedDTO.getBestAskPrice());
        if (priceSnapshotAdjustedDTO.getBestAskPriceUpdatedDate() != null) {
            preparedStatement.setDate(index++, new Date(priceSnapshotAdjustedDTO.getBestAskPriceUpdatedDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        preparedStatement.setDouble(index++, priceSnapshotAdjustedDTO.getBestBidPrice());
        if (priceSnapshotAdjustedDTO.getBestBidPriceUpdatedDate() != null) {
            preparedStatement.setDate(index++, new Date(priceSnapshotAdjustedDTO.getBestBidPriceUpdatedDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        preparedStatement.setLong(index, priceSnapshotAdjustedDTO.getTickerSerial());
    }

    /**
     * @param assetType
     * @param isDelayed
     * @return
     */
    public static String getSnapCorrectionQuery(IConstants.AssetType assetType, boolean isDelayed) {
        String query = null;
        switch (assetType) {
            case EQUITY:
                query = MessageFormat.format(SQL_TICKER_SNAP_DATA_CORRECTION, DBUtils.getTickerSnapshotTableName(isDelayed));
                break;
            default:
                break;
        }
        return query;
    }

    /**
     * Switch to set procedure params based on the procedure type
     *
     * @param syncType     syncType
     * @param requestDBDTO dbDTOObject
     * @param clblStmnt    callable statement
     * @throws SQLException propagate exception caller method
     * @see DBConstants.DBDataSyncTypes
     */
    public static void setProcedureParams(DBConstants.DBDataSyncTypes syncType, RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        setCommonParams(requestDBDTO, clblStmnt);
        switch (syncType) {
            case IND_SNAP:
                setIndividualSnapParams(requestDBDTO, clblStmnt);
                break;
            case TICKERS:
                setEquityTickerParams(requestDBDTO, clblStmnt);
                break;
            case FUND_TICKERS:
                setFundTickerParams(requestDBDTO, clblStmnt);
                break;
            case FI_TICKERS:
                setFixedIncomeTickerParams(requestDBDTO, clblStmnt);
                break;
            case TICKER_DES:
                setTickerDescParams(requestDBDTO, clblStmnt);
                break;
            case TRADING_NAME:
                setTradingNameParams(requestDBDTO, clblStmnt);
                break;
            case TICKER_GICS:
                setGicsParams(requestDBDTO, clblStmnt);
                break;
            case COMP_DESC:
                setCompanyDescParams(requestDBDTO, clblStmnt);
                break;
            case CURRENCY_RATES:
                setCurrencyRatesParams(requestDBDTO, clblStmnt);
                break;
            case SOURCE:
                setSourceParams(requestDBDTO, clblStmnt);
                break;
            case SECTORS:
                setSectorsParams(requestDBDTO, clblStmnt);
                break;
            case LISTED_INDXS:
                setListedIndexesParams(requestDBDTO, clblStmnt);
                break;
            case RELTD_INDV:
                setRelatedIndivParams(requestDBDTO, clblStmnt);
                break;
            case SOURCES_MAIN_INDX:
                setSourceMainIndxParams(requestDBDTO, clblStmnt);
                break;
            case IND_DETAILS:
                setIndxDetailsParams(requestDBDTO, clblStmnt);
                break;
            case UNLSTD_COMPS:
                setUnlistedCompParams(requestDBDTO, clblStmnt);
                break;
            case IPO_DATA:
                setIPOsUpdateParams(requestDBDTO, clblStmnt);
                break;
            case MA_DATA:
                setMAUpdateParams(requestDBDTO, clblStmnt);
                break;
            case OWNERSHIP_LIMIT_DEF:
                setOwnershipLimitDefinitionUpdateParams(requestDBDTO, clblStmnt);
                break;
            case KPI_DATA:
                setKPIUpdateParams(requestDBDTO, clblStmnt);
                break;
            case FILE_PUBLISHERS:
                setFilePublisherParams(requestDBDTO, clblStmnt);
                break;
            case FUND_INVESTMENT_ALLOCATIONS:
                setFundInvestments(requestDBDTO, clblStmnt);
                break;
            case IPO_GICS:
                setGicsParams(requestDBDTO, clblStmnt);
                break;
            case DOC_FILE:
                setDCMSDataParams(requestDBDTO, clblStmnt);
                break;
            case CITY:
                setCityParams(requestDBDTO, clblStmnt);
                break;
            case COUNTRY:
                setCountryTickerUpdateParams(requestDBDTO, clblStmnt);
                break;
            case INV_VAL:
                setInvestorTypesValuesUpdateParams(requestDBDTO, clblStmnt);
                break;
            case PRICE_SNAPSHOT_ADJUSTED:
                setPriceSnapshotUpdateParams(requestDBDTO, clblStmnt);
                break;
            case COMPANY_TYPE_DESC:
                setCompanyTypeDescParams(requestDBDTO, clblStmnt);
                break;
            case COUNTRY_INDICATORS:
                setCountryIndicatorValueParams(requestDBDTO, clblStmnt);
                break;
            case COUNTRY_INDICATOR_MASTER:
                setCountryIndicatorMasterParams(requestDBDTO, clblStmnt);
                break;
            case COUNTRY_INDICATOR_GROUP_MASTER:
                setCountryIndicatorGroupMasterParams(requestDBDTO, clblStmnt);
                break;
            case NEWS_HISTORY:
                setNewsHistoryUpdateParams(requestDBDTO, clblStmnt);
                break;
            case ANNOUNCEMENT_HISTORY:
                setAnnoucementHistoryUpdateParams(requestDBDTO, clblStmnt);
                break;
            case FUND_SNAPSHOT:
                setFundSnapshotParams(requestDBDTO, clblStmnt);
                break;
            case TAG_NEWS_CID:
                setNewsTagParams(requestDBDTO, clblStmnt);
                break;
            case TAG_NEWS_TSID:
                setNewsTagParams(requestDBDTO, clblStmnt);
                break;
            case COUNTRY_MASTER_DATA:
                setCountryMasterDataUpdateParams(requestDBDTO,clblStmnt);
                break;
            case TIMEZONE_DATA:
                setTimezoneDataUpdateParams(requestDBDTO,clblStmnt);
                break;
            case TOP_NEWS_HISTORY:
                setTopNewsHistoryParams(requestDBDTO, clblStmnt);
                break;
            case INDICES:
                setIndicerParams(requestDBDTO, clblStmnt);
                break;
            case INDEX_SNAPSHOT:
                setIndexSnapshotParams(requestDBDTO, clblStmnt);
                break;
            case FINANCIAL_ANALYST_PREFERENCE:
                setFinancialAnalystPrefParams(requestDBDTO, clblStmnt);
                break;
            case FINANCIAL_SEGMENT_DATA:
                setFinancialSegmentDataParams(requestDBDTO, clblStmnt);
                break;
            case COMPANY_OWNER_COMPANY:
                setCompanyOwnerCompanyParams(requestDBDTO, clblStmnt);
                break;
            case COMPANY_CPC:
                setCompanyCPCParams(requestDBDTO, clblStmnt);
                break;
            case COMPANY_SIZE_ACT:
                setCompanySizeActParams(requestDBDTO, clblStmnt);
                break;
            case COMPANY_SIZE_BKT:
                setCompanySizeBktParams(requestDBDTO, clblStmnt);
                break;
            case REVENUE_GROWTH_ACT:
                setRevenueGrowthActParams(requestDBDTO, clblStmnt);
                break;
            case REVENUE_GROWTH_BKT:
                setRevenueGrowthBktParams(requestDBDTO, clblStmnt);
                break;
            case CAL_COMPANY_GROWTH:
                setCalRevenueGrowthRateParams(requestDBDTO, clblStmnt);
                break;
        }
    }

    /**
     * Set Investor Types Values update parameters
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException sql exception on error
     */
    private static void setInvestorTypesValuesUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set Country tickers update parameters
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException sql exception on error
     */
    private static void setCountryTickerUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for 'PKG_OWNERSHIP_LIMIT_DEF' procedure
     *
     * @param requestDBDTO object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setOwnershipLimitDefinitionUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_MA_DATA' procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setFilePublisherParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_FUND_INVESTMENTS' procedure
     *
     * @param requestDBDTO db object
     * @param clblStmnt    pkg
     * @throws SQLException exception
     */
    private static void setFundInvestments(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_MA_DATA' procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setMAUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_MA_DATA' procedure
     *
     * @param requestDBDTO requestDBDTO
     * @param clblStmnt    clblStmnt
     * @throws SQLException
     */
    private static void setKPIUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_IPO_DATA' procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setIPOsUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_GICS' procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setGicsParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_TABLE, requestDBDTO.getTableName());
        clblStmnt.setString(SP_IN_PARA_P_GICS, requestDBDTO.getGicsCode());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());

        StringBuilder sbID = new StringBuilder();
        sbID.append(requestDBDTO.getSyncTypes().toString());
        sbID.append(IConstants.Delimiter.VL);
        sbID.append(requestDBDTO.getTableName());

        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, sbID.toString());
    }

    /**
     * Set parameters for 'UPDATE_INDIVIDUAL_SNAPSHOT'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setIndividualSnapParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'update_company_owner_company'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setCompanyOwnerCompanyParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setCompanyCPCParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setCompanySizeActParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setCompanySizeBktParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setRevenueGrowthActParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setRevenueGrowthBktParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setCalRevenueGrowthRateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_EQUITY_TICKERS'  procedure
     *
     * @param requestDBDTO sp request object
     * @param clblStmnt    sp callable statement
     * @throws SQLException exception
     */
    private static void setEquityTickerParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SOURCE_ID, requestDBDTO.getSourceId());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_FUND_TICKERS'  procedure
     *
     * @param requestDBDTO sp request object
     * @param clblStmnt    sp callable statement
     * @throws SQLException exception
     */
    private static void setFundTickerParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SOURCE_ID, requestDBDTO.getSourceId());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_FIXED_INCOME_TICKERS'  procedure
     *
     * @param requestDBDTO sp request object
     * @param clblStmnt    sp callable statement
     * @throws SQLException exception
     */
    private static void setFixedIncomeTickerParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SOURCE_ID, requestDBDTO.getSourceId());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_TICKER_DESC'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setTickerDescParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_TABLE, requestDBDTO.getTableName());
        clblStmnt.setString(SP_IN_PARA_P_SOURCE_ID, requestDBDTO.getSourceId());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());

        StringBuilder sbID = new StringBuilder();
        sbID.append(requestDBDTO.getSyncTypes().toString());
        sbID.append(IConstants.Delimiter.VL);
        sbID.append(requestDBDTO.getTableName());

        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, sbID.toString());
    }

    /**
     * Set parameters for 'UPDATE_TRADING_NAME'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setTradingNameParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_TABLE, requestDBDTO.getTableName());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());

        StringBuilder sbID = new StringBuilder();
        sbID.append(requestDBDTO.getSyncTypes().toString());
        sbID.append(IConstants.Delimiter.VL);
        sbID.append(requestDBDTO.getTableName());

        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, sbID.toString());
    }

    /**
     * Set parameters for 'UPDATE_UNLISTED_COMPANIES'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setUnlistedCompParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'INSERT_MASTER_IND_DETAILS'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setIndxDetailsParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_COMPANY_DESC'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setCompanyDescParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_TABLE, requestDBDTO.getTableName());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());

        StringBuilder sbID = new StringBuilder();
        sbID.append(requestDBDTO.getSyncTypes().toString());
        sbID.append(IConstants.Delimiter.VL);
        sbID.append(requestDBDTO.getTableName());

        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, sbID.toString());
    }

    private static void setDCMSDataParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for 'UPDATE_CITIES'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setCityParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for 'UPDATE_CURRENCY_RATES'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setCurrencyRatesParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_SOURCES'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setSourceParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'INSERT_MASTER_SECTORS'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setSectorsParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_LISTED_INDEXES'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setListedIndexesParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_RELATED_INDIVIDUALS'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setRelatedIndivParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_SOURCES_MAIN_INDEX'  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setSourceMainIndxParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for 'UPDATE_PRICE_SNAPSHOT_ADJUSTED'  procedure
     *
     * @param requestDBDTO request Object
     * @param clblStmnt    callable statement
     * @throws SQLException sql
     */
    private static void setPriceSnapshotUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for setCompanyTypeDescParams  procedure
     *
     * @param requestDBDTO
     * @param clblStmnt
     * @throws SQLException
     */
    private static void setCompanyTypeDescParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set parameters for update counry indicators sp
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setCountryIndicatorValueParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_COUNTRY_CODE, requestDBDTO.getCountryCode());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for update country indicator master data update SP
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setCountryIndicatorMasterParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * Set parameters for update country indicator group master data update SP
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setCountryIndicatorGroupMasterParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    /**
     * set params for fund snapshot update
     *
     * @param requestDBDTO request object
     * @param clblStmnt    statement
     * @throws SQLException exception if any error
     */
    private static void setFundSnapshotParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    private static void setIndexSnapshotParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setFinancialAnalystPrefParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    private static void setFinancialSegmentDataParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
    }

    private static void setNewsTagParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        Date startDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.SD), IConstants.DateFormats.FORMAT4);
        Date endDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.ED), IConstants.DateFormats.FORMAT4);

        clblStmnt.setDate(SP_IN_PARA_P_START_DATE, startDate);
        clblStmnt.setDate(SP_IN_PARA_P_END_DATE, endDate);
    }

    /**
     * Set parameters for news history update SP
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setNewsHistoryUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        Date startDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.SD), IConstants.DateFormats.FORMAT4);
        Date endDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.ED), IConstants.DateFormats.FORMAT4);

        clblStmnt.setDate(SP_IN_PARA_P_START_DATE, startDate);
        clblStmnt.setDate(SP_IN_PARA_P_END_DATE, endDate);
    }

    /**
     * Set parameters for announcement history update SP
     *
     * @param requestDBDTO request object
     * @param clblStmnt    callable statement
     * @throws SQLException
     */
    private static void setAnnoucementHistoryUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        Date startDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.SD), IConstants.DateFormats.FORMAT4);
        Date endDate = CommonUtils.convertToSqlDate((String) requestDBDTO.getCustomParams().get(IConstants.MIXDataField.ED), IConstants.DateFormats.FORMAT4);

        clblStmnt.setDate(SP_IN_PARA_P_START_DATE, startDate);
        clblStmnt.setDate(SP_IN_PARA_P_END_DATE, endDate);
    }

    /**
     * Set Country master data update parameters
     *
     * @param requestDBDTO request object
     * @param clblStmnt  callable statement
     *
     * @throws SQLException  sql exception on error
     */
    private static void setCountryMasterDataUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set Country master data update parameters
     *
     * @param requestDBDTO request object
     * @param clblStmnt  callable statement
     *
     * @throws SQLException  sql exception on error
     */
    private static void setTimezoneDataUpdateParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    /**
     * Set Top news history update SP Parameters
     *
     * @param requestDBDTO request object
     * @param clblStmnt callable statement
     * @throws SQLException
     */
    private static void setTopNewsHistoryParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setIndicerParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SOURCE_ID, requestDBDTO.getSourceId());
        clblStmnt.setTimestamp(SP_IN_PARA_LAST_UPDATE_DATE, requestDBDTO.getLastRunTime());
        clblStmnt.setString(SP_IN_PARA_P_SYNC_ID, requestDBDTO.getSyncTypes().toString());
    }

    private static void setCommonParams(RequestDBDTO requestDBDTO, CallableStatement clblStmnt) throws SQLException {
        clblStmnt.setString(SP_IN_PARA_P_SCHEDULER_TYPE, requestDBDTO.getSchedulerTypes());
    }

    /*=====================================Data Synchronization Logs====================================================*/

    /**
     * Returns update sql for data sync status
     *
     * @return
     */
    public static String getDataSyncStatusUpdateSQL() {
        return DATA_SYNC_STATUS_UPDATE;
    }

    /**
     * Returns insert sql for data sync status
     *
     * @return
     */
    public static String getDataSyncStatusInsertSQL() {
        return DATA_SYNC_STATUS_INSERT;
    }

    /**
     * Method to set values to prepared statement for inserting country tickers to IMDB,where data retrieved from RDBMS
     *
     * @param preparedStatement prepared statement 'Insert/Update'
     * @param dataSyncStatusDTO data sync object
     * @throws SQLException
     */
    public static void setDataSyncStatusValues(PreparedStatement preparedStatement, DataSyncStatusDTO dataSyncStatusDTO) throws SQLException {
        int index = 1;
        preparedStatement.setString(index++, dataSyncStatusDTO.getLastId());
        preparedStatement.setTimestamp(index++, dataSyncStatusDTO.getLastUpdatedTime());
        preparedStatement.setString(index++, dataSyncStatusDTO.getDescription());
        preparedStatement.setString(index++, dataSyncStatusDTO.getParams());
        preparedStatement.setString(index++, dataSyncStatusDTO.getId());
        preparedStatement.setInt(index++, dataSyncStatusDTO.getType());

    }

    /**
     * Helper method to set last runtime for filters
     *
     * @param preparedStatement
     * @param lastRunTime
     * @param index
     * @throws SQLException
     */
    public static void setLasRunTimeValue(PreparedStatement preparedStatement, Timestamp lastRunTime, int index) throws SQLException {
        preparedStatement.setTimestamp(index, lastRunTime);
    }

    //region DCMS

    //-----------------------------------region DCMS--------------------------------------------------------------

    private static final String[] LANGUAGE_SPECIFIC_DOC_COLUMNS = new String[]{"DISPLAY_NAME", "SUMMARY", "FILE_GUID","INDUSTRY_CODES_DESC"};

    //DOC FILE table
    private static final String SQL_DOC_FILES_TABLE = " DOC_FILES ";
    private static final String placeHolder = "?";

    public static final String INSERT_DOC_COMPANIES = "INSERT INTO DOC_COMPANIES (STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,COMPANY_ID) VALUES (?,?,?,?,?)";

    public static final String UPDATE_DOC_COMPANIES = "UPDATE	DOC_COMPANIES SET 	STATUS = ?,LAST_UPDATED_TIME = ?,LAST_SYNC_TIME = ? WHERE FILE_ID = ? and	COMPANY_ID = ?";

    public static final String INSERT_DOC_COUNTRIES = "INSERT INTO DOC_COUNTRIES	(STATUS,LAST_UPDATED_TIME,LAST_SYNC_TIME,FILE_ID,COUNTRY_CODE) VALUES (?,?,?,?,?)";

    public static final String UPDATE_DOC_COUNTRIES = "UPDATE DOC_COUNTRIES SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE FILE_ID = ? and COUNTRY_CODE = ?";

    public static final String INSERT_DOC_EXCHANGES = "INSERT INTO DOC_EXCHANGES	( STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,SOURCE_ID) VALUES 	(?,?,?,?,?)";

    public static final String UPDATE_DOC_EXCHANGES = "UPDATE	DOC_EXCHANGES SET STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE 	FILE_ID = ? and	SOURCE_ID = ?";

    public static final String INSERT_DOC_INDUSTRIES = "INSERT INTO DOC_INDUSTRIES	( STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,CLASSIFICATION_SERIAL,CLASSIFICATION_CODE) VALUES 	(?,?,?,?,?,?)";

    public static final String UPDATE_DOC_INDUSTRIES = "UPDATE	DOC_INDUSTRIES SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ?,CLASSIFICATION_CODE = ? WHERE FILE_ID = ? and	CLASSIFICATION_SERIAL = ?";

    public static final String INSERT_DOC_PERIODS = "INSERT INTO DOC_PERIODS	( STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,\"YEAR\",PERIOD_ID) VALUES 	(?,?,?,?,?, ?)";

    public static final String UPDATE_DOC_PERIODS = "UPDATE	DOC_PERIODS SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE FILE_ID = ? and \"YEAR\" = ? and PERIOD_ID = ?";

    public static final String INSERT_DOC_REGIONS = "INSERT INTO DOC_REGIONS	(STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,REGION_ID) VALUES 	(?,?,?,?,?)";

    public static final String UPDATE_DOC_REGIONS = "UPDATE	DOC_REGIONS SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE FILE_ID = ? and 	REGION_ID = ?";

    public static final String INSERT_DOC_SYMBOLS = "INSERT INTO DOC_SYMBOLS	(STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,TICKER_SERIAL) VALUES 	(?,?,?,?,?)";

    public static final String UPDATE_DOC_SYMBOLS = "UPDATE	DOC_SYMBOLS SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE FILE_ID = ? and	TICKER_SERIAL = ?";

    public static final String INSERT_DOC_SECTORS = "INSERT INTO DOC_SECTORS	(STATUS, LAST_UPDATED_TIME, LAST_SYNC_TIME,FILE_ID,GROUP_SERIAL) VALUES 	(?,?,?,?,?)";

    public static final String UPDATE_DOC_SECTORS = "UPDATE	DOC_SECTORS SET	STATUS = ?,	LAST_UPDATED_TIME = ?,	LAST_SYNC_TIME = ? WHERE FILE_ID = ? and	GROUP_SERIAL = ?";


    /**
     * get insert query for DCMS
     *
     * @return INSERT INTO DOC_FILES (REPORT_DATE,PUBLISHER,PROVIDER,CATEGORY_ID,SUB_CATEGORY_ID,DEFAULT_FILE_LANG,SYMBOLS,EXCHANGES,COUNTRY_CODES
     *         DISPLAY_NAME_EN,DISPLAY_NAME_AR,SUMMARY_EN,SUMMARY_AR,FILE_GUID_EN,FILE_GUID_AR,
     *         LAST_UPDATED_TIME,LAST_SYNC_TIME,STATUS,FILE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
     */
    public static String getInsertDocFileSql(List<String> supportedLang) {
        StringBuilder sql = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO);
        sql.append(SQL_DOC_FILES_TABLE);
        sql.append(DBConstants.SQL_BRACKET_OPEN);
        sql.append(DBConstants.DocFileColumns.REPORT_DATE).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.PUBLISHER).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.PROVIDER).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.CATEGORY_ID).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.SUB_CATEGORY_ID).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.DEFAULT_FILE_LANG).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.SYMBOLS).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.EXCHANGES).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.COUNTRY_CODES).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.INDUSTRY_CODES).append(DBConstants.SQL_COMMA);

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_DOC_COLUMNS) {
                    sql.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang.toUpperCase()).append(DBConstants.SQL_COMMA);
                }
            }
        }

        sql.append(DBConstants.DocFileColumns.LAST_UPDATED_TIME).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.LAST_SYNC_TIME).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.STATUS).append(DBConstants.SQL_COMMA);

        sql.append(DBConstants.DocFileColumns.FILE_ID).append(DBConstants.SQL_VALUES);

        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_DOC_COLUMNS) {
                    sql.append(placeHolder).append(DBConstants.SQL_COMMA);
                }
            }
        }

        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(placeHolder).append(DBConstants.SQL_BRACKET_CLOSE);

        return sql.toString();
    }

    /**
     * get update query for DCMS
     *
     * @return UPDATE DOC_FILES SET REPORT_DATE=?,PUBLISHER=?,PROVIDER=?,CATEGORY_ID=?,SUB_CATEGORY_ID=?,DEFAULT_FILE_LANG=?,SYMBOLS=?,EXCHANGES=?,
     *         DISPLAY_NAME_EN=?,DISPLAY_NAME_AR=?,SUMMARY_EN=?,SUMMARY_AR=?,FILE_GUID_EN=?,FILE_GUID_AR=?,
     *         LAST_UPDATED_TIME=?,LAST_SYNC_TIME=?,STATUS=? WHERE FILE_ID=?
     */
    public static String getUpdateDocFileSql(List<String> supportedLang) {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(SQL_DOC_FILES_TABLE);
        sql.append(SQL_SET);
        sql.append(DBConstants.DocFileColumns.REPORT_DATE).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.PUBLISHER).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.PROVIDER).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.CATEGORY_ID).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.SUB_CATEGORY_ID).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.DEFAULT_FILE_LANG).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.SYMBOLS).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.EXCHANGES).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.COUNTRY_CODES).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.INDUSTRY_CODES).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_DOC_COLUMNS) {
                    sql.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang.toUpperCase()).
                            append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
                }
            }
        }

        sql.append(DBConstants.DocFileColumns.LAST_UPDATED_TIME).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.LAST_SYNC_TIME).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder).append(DBConstants.SQL_COMMA);
        sql.append(DBConstants.DocFileColumns.STATUS).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder);

        sql.append(DBConstants.WHERE).append(DBConstants.DocFileColumns.FILE_ID).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(placeHolder);

        return sql.toString();
    }

    /**
     * set prepared statement values for update doc file
     *
     * @param preparedStatement PreparedStatement
     * @param docFileDTO        DocFileDTO
     * @throws SQLException
     */
    public static void setDocFileValues(PreparedStatement preparedStatement, DocFileDTO docFileDTO, List<String> supportedLang) throws SQLException {
        int index = 1;

        preparedStatement.setDate(index++, docFileDTO.getReportDate() != null ? new Date(docFileDTO.getReportDate().getTime()) : null);
        preparedStatement.setInt(index++, docFileDTO.getPublisher());
        preparedStatement.setString(index++, docFileDTO.getProvider());
        preparedStatement.setInt(index++, docFileDTO.getCategoryId());
        preparedStatement.setInt(index++, docFileDTO.getSubCategoryId());
        preparedStatement.setString(index++, docFileDTO.getDefaultFileLang());
        preparedStatement.setString(index++, docFileDTO.getSymbols());
        preparedStatement.setString(index++, docFileDTO.getExchanges());
        preparedStatement.setString(index++, docFileDTO.getCountryCodes());
        preparedStatement.setString(index++, docFileDTO.getIndustryCodes());

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                DocFileLangDTO docFileLangDTO = docFileDTO.getDocFileLangDTOMap().get(lang.toUpperCase());
                for (String column : LANGUAGE_SPECIFIC_DOC_COLUMNS) {
                    if (column.equalsIgnoreCase(DBConstants.DocFileColumns.DISPLAY_NAME)) {
                        preparedStatement.setString(index++, docFileLangDTO.getDisplayName());
                    } else if (column.equalsIgnoreCase(DBConstants.DocFileColumns.SUMMARY)) {
                        preparedStatement.setString(index++, docFileLangDTO.getSummary());
                    } else if (column.equalsIgnoreCase(DBConstants.DocFileColumns.FILE_GUID)) {
                        preparedStatement.setString(index++, docFileLangDTO.getFileGuid());
                    } else if (column.equalsIgnoreCase(DBConstants.DocFileColumns.INDUSTRY_CODES_DESC)) {
                        preparedStatement.setString(index++, docFileLangDTO.getIndustryCodesDesc());
                    }
                }
            }
        }

        preparedStatement.setTimestamp(index++, docFileDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docFileDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docFileDTO.getStatus());
        preparedStatement.setInt(index, docFileDTO.getFileId());
    }


    public static void setDocCompanyValues(PreparedStatement preparedStatement, DocCompaniesDTO docCompaniesDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docCompaniesDTO.getStatus());
        preparedStatement.setTimestamp(index++, docCompaniesDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docCompaniesDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docCompaniesDTO.getFileId());
        preparedStatement.setInt(index, docCompaniesDTO.getCompanyId());
    }

    public static void setDocCountryValues(PreparedStatement preparedStatement, DocCountriesDTO docCountriesDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docCountriesDTO.getStatus());
        preparedStatement.setTimestamp(index++, docCountriesDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docCountriesDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docCountriesDTO.getFileId());
        preparedStatement.setString(index, docCountriesDTO.getCountryCode());
    }

    public static void setDocExchangeValues(PreparedStatement preparedStatement, DocExchangesDTO docExchangesDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docExchangesDTO.getStatus());
        preparedStatement.setTimestamp(index++, docExchangesDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docExchangesDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docExchangesDTO.getFileId());
        preparedStatement.setString(index, docExchangesDTO.getSourceId());
    }

    /**
     * set values to update statement
     *
     * @param preparedStatement update statement
     * @param docIndustriesDTO docIndustries DTO to update
     * @throws SQLException
     */
    public static void setDocIndustryValues(PreparedStatement preparedStatement, DocIndustriesDTO docIndustriesDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docIndustriesDTO.getStatus());
        preparedStatement.setTimestamp(index++, docIndustriesDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docIndustriesDTO.getLastSyncTime());
        preparedStatement.setString(index++, docIndustriesDTO.getClassificationCode());
        preparedStatement.setInt(index++, docIndustriesDTO.getFileId());
        preparedStatement.setInt(index, docIndustriesDTO.getClassificationSerial());
    }

    /**
     * set values to insert statement
     *
     * @param preparedStatement insert statement
     * @param docIndustriesDTO docIndustries DTO to insert
     * @throws SQLException
     */
    public static void setInsertDocIndustryValues(PreparedStatement preparedStatement, DocIndustriesDTO docIndustriesDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docIndustriesDTO.getStatus());
        preparedStatement.setTimestamp(index++, docIndustriesDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docIndustriesDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docIndustriesDTO.getFileId());
        preparedStatement.setInt(index++, docIndustriesDTO.getClassificationSerial());
        preparedStatement.setString(index, docIndustriesDTO.getClassificationCode());
    }

    public static void setInsertDocPeriodsValues(PreparedStatement preparedStatement, DocPeriodsDTO docPeriodsDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docPeriodsDTO.getStatus());
        preparedStatement.setTimestamp(index++, docPeriodsDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docPeriodsDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docPeriodsDTO.getFileId());
        preparedStatement.setInt(index++, docPeriodsDTO.getYear());
        preparedStatement.setInt(index, docPeriodsDTO.getPeriodId());
    }

    public static void setDocRegionValues(PreparedStatement preparedStatement, DocRegionsDTO docRegionsDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docRegionsDTO.getStatus());
        preparedStatement.setTimestamp(index++, docRegionsDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docRegionsDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docRegionsDTO.getFileId());
        preparedStatement.setInt(index, docRegionsDTO.getRegionId());
    }

    public static void setDocSectorValues(PreparedStatement preparedStatement, DocSectorsDTO docSectorsDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docSectorsDTO.getStatus());
        preparedStatement.setTimestamp(index++, docSectorsDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docSectorsDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docSectorsDTO.getFileId());
        preparedStatement.setInt(index, docSectorsDTO.getGroupSerial());
    }

    public static void setDocSymbolValues(PreparedStatement preparedStatement, DocSymbolsDTO docSymbolsDTO) throws SQLException {
        int index = 1;

        preparedStatement.setInt(index++, docSymbolsDTO.getStatus());
        preparedStatement.setTimestamp(index++, docSymbolsDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, docSymbolsDTO.getLastSyncTime());
        preparedStatement.setInt(index++, docSymbolsDTO.getFileId());
        preparedStatement.setLong(index, docSymbolsDTO.getTickerSerial());
    }

    //--------------------------------------------endregion DCMS-------------------------------------------------


}
