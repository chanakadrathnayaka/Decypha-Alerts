package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.tickers.FundTickerDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 4/3/2014.
 */
public class FundTickerDBHelper {
    private static final String FUND_TICKER_QUERY = "SELECT * FROM FUND_TICKERS";
    private static final String TICKER_UPDATE_WHERE = " WHERE TICKER_SERIAL = ?";
    private static final String INSERT_TICKER_SERIAL = ",TICKER_SERIAL";
    private static final String LAST_SYNC_TIME_FILTER = "  LAST_SYNC_TIME > ? ";
    private static final String SQL_FUND = DBConstants.TablesIMDB.TABLE_FUND_TICKERS;
    private static final String QUERY_WHERE = DBConstants.CommonDatabaseParams.QUERY_WHERE;
    private static final String SQL_VALUES = DBConstants.CommonDatabaseParams.SQL_VALUES;
    private static final String SQL_BRACKET_OPEN = DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN;
    private static final String SQL_BRACKET_CLOSE = DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
    private static final String SQL_UPDATE = DBConstants.CommonDatabaseParams.QUERY_UPDATE;
    private static final String SQL_SET = DBConstants.CommonDatabaseParams.QUERY_SET;
    private static final String SQL_INSERT = DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO;
    private static final String INSERT_TICKER_SERIAL_PARAMS = DBConstants.CommonDatabaseParams.SQL_COMMA + DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;

    private static String updateFundTickerColumnString;
    private static String insertFundTickerColumnString;
    private static String insertFundTickerColumnParams;

    private static final String UPDATE_PLACEHOLDER = TickerDBHelper.UPDATE_PLACEHOLDER;

    private static final String[] FUND_TICKER_COLUMNS = new String[]{
            FUND_CLASS, CHG_3M, CHG_YTD, CHG_1Y, CHG_PER_YTD, CHG_PER_3M, CHG_PER_1Y, NAV, NAV_DATE, MANAGED_COMPANIES,
            TNAV, TNAV_DATE, TNAV_USD, PRV_TNAV, PRV_TNAV_DATE, PRV_TNAV_USD, ISSUED_COMPANIES, ESTB_DATE, ADMIN_FEE, MGT_FEE, CUSTODIAN_FEE, PERFORMANCE_FEE,
            REDEMPTION_FEE, SUBSCRIPTION_FEE, SUBSEQUENT_SUBS, MIN_SUBSCRIPTION, SERVICE_FEE, OTHER_EXP, NAV_FREQ_ID,
            CHG_PER_1M, CHG_PER_3Y, CHG_PER_5Y, LOW_52_WEEK, HIGH_52_WEEK, TEN_YEARS_PCT_CHANGE, LIFETIME_PCT_CHANGE,
            FOCUSED_COUNTRIES, FOCUSED_REGIONS, FYS_DAY, FYS_MONTH, FYE_DAY, FYE_MONTH, FUND_DURATION, FUND_STATUS,
            MANAGED_COUNTRIES, ISSUED_COUNTRIES,
            THREE_YEAR_MONTHLY_RETURN, FIVE_YEAR_MONTHLY_RETURN, THREE_YEAR_ANNUAL_RETURN, FIVE_YEAR_ANNUAL_RETURN,
            THREE_YR_ANNUAL_RETURN_STD_DEV, FIVE_YR_ANNUAL_RETURN_STD_DEV
    };

    public static String getFundTickerQuery(Timestamp lastRunTime) {
        return FUND_TICKER_QUERY + ((lastRunTime != null) ? QUERY_WHERE + LAST_SYNC_TIME_FILTER : IConstants.EMPTY);
    }

    public static TickerDTO getFundTickerData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        FundTickerDTO ticker = new FundTickerDTO();
        TickerDBHelper.setTickerData(ticker, results, supportedLanguages);
        ticker.setFundClass(results.getInt(FUND_CLASS));
        ticker.setChange3M(results.getDouble(CHG_3M));
        ticker.setChangeYTD((results.getString(CHG_YTD) != null) ? (results.getDouble(CHG_YTD)) : null);
        ticker.setChange1Y((results.getString(CHG_1Y) != null) ? (results.getDouble(CHG_1Y)) : null);
        ticker.setChangePer3M(results.getDouble(CHG_PER_3M));
        ticker.setChangePerYTD((results.getString(CHG_PER_YTD) != null) ? (results.getDouble(CHG_PER_YTD)) : null);
        ticker.setChangePer1Y((results.getString(CHG_PER_1Y) != null) ? (results.getDouble(CHG_PER_1Y)) : null);
        ticker.setNav(results.getDouble(NAV));
        ticker.setNavDate(results.getString(NAV_DATE));
        ticker.setManagedCompanies(results.getString(MANAGED_COMPANIES));
        ticker.setFundIssuers(results.getString(ISSUED_COMPANIES));
        ticker.setTNav(results.getString(TNAV) != null ? results.getDouble(TNAV) : null);
        ticker.setTNavUSD(results.getString(TNAV_USD) != null && results.getDouble(TNAV_USD) != -1 ? results.getDouble(TNAV_USD) : null);
        ticker.setTNavDate(results.getDate(TNAV_DATE));
        ticker.setPrvTNav(results.getDouble(PRV_TNAV));
        ticker.setPrvTNavUSD((results.getString(PRV_TNAV_USD) != null && results.getDouble(PRV_TNAV_USD) != -1 )? results.getDouble(PRV_TNAV_USD) : null);
        ticker.setPrvTNavDate(results.getDate(PRV_TNAV_DATE));
        ticker.setEstbDate(results.getDate(ESTB_DATE));
        ticker.setAdminFee(results.getDouble(ADMIN_FEE));
        ticker.setMgtFee(results.getDouble(MGT_FEE));
        ticker.setCustodianFee(results.getDouble(CUSTODIAN_FEE));
        ticker.setPerformanceFee(results.getDouble(PERFORMANCE_FEE));
        ticker.setRedemptionFee(results.getDouble(REDEMPTION_FEE));
        ticker.setSubscriptionFee(results.getDouble(SUBSCRIPTION_FEE));
        ticker.setSubsequentSubs(results.getDouble(SUBSEQUENT_SUBS));
        ticker.setMinSubscription(results.getDouble(MIN_SUBSCRIPTION));
        ticker.setServiceFee(results.getDouble(SERVICE_FEE));
        ticker.setOtherExp(results.getDouble(OTHER_EXP));
        ticker.setNavFrequency(results.getInt(NAV_FREQ_ID));
        ticker.setChangePer1M(results.getDouble(CHG_PER_1M));
        ticker.setChangePer3Y(results.getDouble(CHG_PER_3Y));
        ticker.setChangePer5Y(results.getDouble(CHG_PER_5Y));
        ticker.setLow52Week(results.getDouble(LOW_52_WEEK));
        ticker.setHigh52Week(results.getDouble(HIGH_52_WEEK));
        ticker.setChangePer10Y(results.getDouble(TEN_YEARS_PCT_CHANGE));
        ticker.setChangePerLifeTime(results.getDouble(LIFETIME_PCT_CHANGE));
        ticker.setFocusedCountries(results.getString(FOCUSED_COUNTRIES));
        ticker.setFocusedRegions(results.getString(FOCUSED_REGIONS));
        ticker.setFundFinancialYearStartDay(results.getInt(FYS_DAY));
        ticker.setFundFinancialYearStartMonth(results.getInt(FYS_MONTH));
        ticker.setFundFinancialYearEndDay(results.getInt(FYE_DAY));
        ticker.setFundFinancialYearEndMonth(results.getInt(FYE_MONTH));
        ticker.setDuration(results.getInt(FUND_DURATION));
        ticker.setFundStatus(results.getInt(FUND_STATUS));
        ticker.setManagedCountries(results.getString(MANAGED_COUNTRIES));
        ticker.setIssuedCountries(results.getString(ISSUED_COUNTRIES));

        ticker.setMonthlyReturn3Y(results.getDouble(THREE_YEAR_MONTHLY_RETURN));
        ticker.setMonthlyReturn5Y(results.getDouble(FIVE_YEAR_MONTHLY_RETURN));
        ticker.setAnnualReturn3Y(results.getDouble(THREE_YEAR_ANNUAL_RETURN));
        ticker.setAnnualReturn5Y(results.getDouble(FIVE_YEAR_ANNUAL_RETURN));
        ticker.setStdDevAnnualReturn3Y(results.getDouble(THREE_YR_ANNUAL_RETURN_STD_DEV));
        ticker.setStdDevAnnualReturn5Y(results.getDouble(FIVE_YR_ANNUAL_RETURN_STD_DEV));

        return ticker;
    }

    public static String getUpdateFundTickerColumnString(List<String> supportedLang) {
        if (updateFundTickerColumnString == null) {
            updateFundTickerColumnString = StringUtils.join(FUND_TICKER_COLUMNS, UPDATE_PLACEHOLDER) + UPDATE_PLACEHOLDER;//join TICKER_COLUMNS with "=?,"
            updateFundTickerColumnString += TickerDBHelper.getUpdateTickerColumnString(supportedLang);
        }
        return updateFundTickerColumnString;
    }

    public static String getInsertFundTickerColumnString(List<String> supportedLang) {
        if (insertFundTickerColumnString == null) {
            insertFundTickerColumnString = StringUtils.join(FUND_TICKER_COLUMNS, IConstants.Delimiter.COMMA) + IConstants.Delimiter.COMMA;//join TICKER_COLUMNS with ","
            insertFundTickerColumnString += TickerDBHelper.getInsertTickerColumnString(supportedLang);
        }
        return insertFundTickerColumnString;
    }

    private static String getFundTickerInsertColumnParams(List<String> supportedLang) {
        if (insertFundTickerColumnParams == null) {
            insertFundTickerColumnParams = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK, DBConstants.CommonDatabaseParams.SQL_COMMA,
                    FUND_TICKER_COLUMNS.length);
            insertFundTickerColumnParams += DBConstants.CommonDatabaseParams.SQL_COMMA + TickerDBHelper.getTickerInsertColumnParams(supportedLang);
        }
        return insertFundTickerColumnParams;
    }

    public static String getInsertFundTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_INSERT);
        sql.append(SQL_FUND);
        sql.append(SQL_BRACKET_OPEN);
        sql.append(getInsertFundTickerColumnString(supportedLanguages)).append(INSERT_TICKER_SERIAL);
        sql.append(SQL_VALUES);
        sql.append(getFundTickerInsertColumnParams(supportedLanguages)).append(INSERT_TICKER_SERIAL_PARAMS);
        sql.append(SQL_BRACKET_CLOSE);
        return sql.toString();
    }

    public static String getUpdateFundTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(SQL_FUND);
        sql.append(SQL_SET);
        sql.append(getUpdateFundTickerColumnString(supportedLanguages));
        sql.append(TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    public static void setUpdateFundTickerValues(PreparedStatement preparedStatement, FundTickerDTO ticker, List<String> supportedLanguages) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, ticker.getFundClass());
        preparedStatement.setDouble(index++, ticker.getChange3M());
        if(ticker.getChangeYTD() != null){
            preparedStatement.setDouble(index++, ticker.getChangeYTD());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        if(ticker.getChange1Y() != null){
            preparedStatement.setDouble(index++, ticker.getChange1Y());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        if(ticker.getChangePerYTD() != null){
            preparedStatement.setDouble(index++, ticker.getChangePerYTD());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        preparedStatement.setDouble(index++, ticker.getChangePer3M());
        if(ticker.getChangePer1Y() != null){
            preparedStatement.setDouble(index++, ticker.getChangePer1Y());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        preparedStatement.setDouble(index++, ticker.getNav());
        preparedStatement.setString(index++, ticker.getNavDate());
        preparedStatement.setString(index++, ticker.getManagedCompanies());
        if (ticker.getTNav() != null) {
            preparedStatement.setDouble(index++, ticker.getTNav());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        if (ticker.getTNavDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getTNavDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        if (ticker.getTNavUSD() != null) {
            preparedStatement.setDouble(index++, ticker.getTNavUSD());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        preparedStatement.setDouble(index++, ticker.getPrvTNav());
        if (ticker.getPrvTNavDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getPrvTNavDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        if (ticker.getPrvTNavUSD() != null) {
            preparedStatement.setDouble(index++, ticker.getPrvTNavUSD());
        } else {
            preparedStatement.setNull(index++, Types.DOUBLE);
        }
        preparedStatement.setString(index++, ticker.getFundIssuers());

        if (ticker.getEstbDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getEstbDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        preparedStatement.setDouble(index++, ticker.getAdminFee());
        preparedStatement.setDouble(index++, ticker.getMgtFee());
        preparedStatement.setDouble(index++, ticker.getCustodianFee());
        preparedStatement.setDouble(index++, ticker.getPerformanceFee());
        preparedStatement.setDouble(index++, ticker.getRedemptionFee());
        preparedStatement.setDouble(index++, ticker.getSubscriptionFee());
        preparedStatement.setDouble(index++, ticker.getSubsequentSubs());
        preparedStatement.setDouble(index++, ticker.getMinSubscription());
        preparedStatement.setDouble(index++, ticker.getServiceFee());
        preparedStatement.setDouble(index++, ticker.getOtherExp());
        preparedStatement.setInt(index++, ticker.getNavFrequency());
        preparedStatement.setDouble(index++, ticker.getChangePer1M());
        preparedStatement.setDouble(index++, ticker.getChangePer3Y());
        preparedStatement.setDouble(index++, ticker.getChangePer5Y());
        preparedStatement.setDouble(index++, ticker.getLow52Week());
        preparedStatement.setDouble(index++, ticker.getHigh52Week());
        preparedStatement.setDouble(index++, ticker.getChangePer10Y());
        preparedStatement.setDouble(index++, ticker.getChangePerLifeTime());
        preparedStatement.setString(index++, ticker.getFocusedCountries());
        preparedStatement.setString(index++, ticker.getFocusedRegions());
        preparedStatement.setInt(index++, ticker.getFundFinancialYearStartDay());
        preparedStatement.setInt(index++, ticker.getFundFinancialYearStartMonth());
        preparedStatement.setInt(index++, ticker.getFundFinancialYearEndDay());
        preparedStatement.setInt(index++, ticker.getFundFinancialYearEndMonth());
        preparedStatement.setInt(index++, ticker.getDuration());
        preparedStatement.setInt(index++, ticker.getFundStatus());
        preparedStatement.setString(index++, ticker.getManagedCountries());
        preparedStatement.setString(index++, ticker.getIssuedCountries());

        preparedStatement.setDouble(index++, ticker.getMonthlyReturn3Y());
        preparedStatement.setDouble(index++, ticker.getMonthlyReturn5Y());
        preparedStatement.setDouble(index++, ticker.getAnnualReturn3Y());
        preparedStatement.setDouble(index++, ticker.getAnnualReturn5Y());
        preparedStatement.setDouble(index++, ticker.getStdDevAnnualReturn3Y());
        preparedStatement.setDouble(index++, ticker.getStdDevAnnualReturn5Y());

        index = TickerDBHelper.setUpdateTickerSetValues(preparedStatement, ticker, index, supportedLanguages);
        preparedStatement.setLong(index, ticker.getTickerSerial());

    }

    public static List<String> getFundTickerColumns(List<String> supportedLanguages) {
        List<String> columns = new ArrayList<String>(TickerDBHelper.getAllTickerColumns(supportedLanguages));
        columns.addAll(Arrays.asList(FUND_TICKER_COLUMNS));
        return columns;
    }

    public static String getTickersByTickerSerials(Collection<String> tickerSerialList, List<String> supportedLanguages) {
        List<String> tickerColumns = getFundTickerColumns(supportedLanguages);
        String tickers = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);
        String filter = QUERY_WHERE + TICKER_SERIAL;
        if (tickers.indexOf(IConstants.Delimiter.COMMA) > 0) {
            filter += QUERY_IN + QUERY_BRACKET_OPEN + tickers + QUERY_BRACKET_CLOSE;
        } else {
            filter += QUERY_EQUAL + tickers;
        }
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_FUND_TICKERS + filter;
    }

    public static String getTickersBySymbolExchange(String tickerId, String sourceId, List<String> supportedLanguages) {
        List<String> tickerColumns = getFundTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + QUERY_UPPER + QUERY_BRACKET_OPEN + TICKER_ID + QUERY_BRACKET_CLOSE + QUERY_EQUAL +
                QUERY_QUOTE + tickerId.toUpperCase() + QUERY_QUOTE + QUERY_AND + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE +
                sourceId + QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_FUND_TICKERS + filter;
    }
}
