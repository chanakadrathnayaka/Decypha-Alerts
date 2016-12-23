package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.tickers.FixedIncomeTickerDTO;
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
public class FixedIncomeTickerDBHelper {

    private static final String FIXED_INCOME_TICKER_QUERY = "SELECT * FROM FIXED_INCOME_TICKERS";
    private static final String TICKER_UPDATE_WHERE = " WHERE TICKER_SERIAL = ?";
    private static final String INSERT_TICKER_SERIAL = ",TICKER_SERIAL";
    private static final String LAST_SYNC_TIME_FILTER = "  LAST_SYNC_TIME > ? ";
    private static final String SQL_FIXED_INCOME = DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS;
    private static final String QUERY_WHERE = DBConstants.CommonDatabaseParams.QUERY_WHERE;
    private static final String SQL_VALUES = DBConstants.CommonDatabaseParams.SQL_VALUES;
    private static final String SQL_BRACKET_OPEN = DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN;
    private static final String SQL_BRACKET_CLOSE = DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
    private static final String SQL_UPDATE = DBConstants.CommonDatabaseParams.QUERY_UPDATE;
    private static final String SQL_SET = DBConstants.CommonDatabaseParams.QUERY_SET;
    private static final String SQL_INSERT = DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO;
    private static final String INSERT_TICKER_SERIAL_PARAMS = DBConstants.CommonDatabaseParams.SQL_COMMA + DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;

    private static String updateFixedIncomeTickerColumnString;
    private static String insertFixedIncomeTickerColumnString;
    private static String insertFixedIncomeTickerColumnParams;

    private static final String UPDATE_PLACEHOLDER = TickerDBHelper.UPDATE_PLACEHOLDER;

    private static final String[] FIXED_INCOME_TICKER_COLUMNS = new String[]{COUPON_DATE_FIRST,ANNOUNCE_DATE,AUCTION_DATE,
            SETTLE_DATE_FIRST,INTEREST_ACR_DATE,MATURITY_DATE,DATE_OF_ISSUE,WKN,ISSUED_PERIOD,COUNTRY_OF_ISSUE,MATURITY_PERIOD,
            AMOUNT_ISSUED,AMOUNT_OUTSTANDING,MINIMUM_PIECE,MINIMUM_INCREMENT,ISSUE_PRICE,COUPON_RATE,MIN_PRICE,YIELD, AUCTION_AMOUNT,
            ISSUE_MIN_RATE, ISSUE_MAX_RATE,ISSUE_WGT_AVG_RATE,FLOATING_COUPON_A,FLOATING_COUPON_B,FLOATING_COUPON_C,FLOATING_COUPON_D,
            FLOATING_COUPON_E,FLOATING_COUPON_IBOR1,FLOATING_COUPON_IBOR2,FLOATING_COUPON_TBILL1, FLOATING_COUPON_TBILL2,
            AUCTION_MIN_RATE,AUCTION_MAX_RATE,AUCTION_WGT_AVG_RATE,SUKUK_TYPE,LEAD_MANAGER,MARKET_TYPE,BOND_TYPE,COUPON_FREQUENCY,
            COUPON_TYPE,COUPON_DAY_COUNT,AUCTION_BIDS,ISSUE_BIDS,REMAINING_COUPON_COUNT,LAST_COUPON_DATE,NEXT_COUPON_DATE,
            LAST_TRADED_DATE,AMOUNT_ISSUED_USD,REQUIRED_AMOUNT,AMOUNT_ISSUED_ISSUER_CUR,FACE_VALUE,PAR_VALUE,COUPON_DAY_COUNT_DESC,
            EMBEDED_OPTIONS,TRADING_CLEARANCE_DAYS, YTM, AMOUNT_ISSUED_USD_LATEST,IS_PRICE_PERCENTAGE};

    public static String getFixedIncomeTickerQuery(Timestamp lastRunTime) {
        return FIXED_INCOME_TICKER_QUERY+ ((lastRunTime != null)?QUERY_WHERE+LAST_SYNC_TIME_FILTER: IConstants.EMPTY);
    }

    public static FixedIncomeTickerDTO getFixedIncomeTickerData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        FixedIncomeTickerDTO ticker = new FixedIncomeTickerDTO();
        TickerDBHelper.setTickerData(ticker, results, supportedLanguages);

        ticker.setCouponDateFirst(results.getDate(COUPON_DATE_FIRST));
        ticker.setAnnounceDate(results.getDate(ANNOUNCE_DATE));
        ticker.setAuctionDate(results.getDate(AUCTION_DATE));
        ticker.setSettleDateFirst(results.getDate(SETTLE_DATE_FIRST));
        ticker.setInterestAcrDate(results.getDate(INTEREST_ACR_DATE));
        ticker.setMaturityDate(results.getDate(MATURITY_DATE));
        ticker.setDateOfIssue(results.getDate(DATE_OF_ISSUE));
        ticker.setWkn(results.getString(WKN));
        ticker.setIssuedPeriod(results.getString(ISSUED_PERIOD));
        ticker.setCountryOfIssue(results.getString(COUNTRY_OF_ISSUE));
        ticker.setMaturityPeriod(results.getString(MATURITY_PERIOD));
        ticker.setAmountIssued(results.getDouble(AMOUNT_ISSUED));
        ticker.setAmountOutstanding(results.getDouble(AMOUNT_OUTSTANDING));
        ticker.setMinimumPiece(results.getDouble(MINIMUM_PIECE));
        ticker.setMinimumIncrement(results.getDouble(MINIMUM_INCREMENT));
        ticker.setIssuePrice(results.getDouble(ISSUE_PRICE));
        ticker.setCouponRate(results.getDouble(COUPON_RATE));
        ticker.setMinPrice(results.getDouble(MIN_PRICE));
        ticker.setYield(results.getDouble(YIELD));
        ticker.setAuctionAmount(results.getDouble(AUCTION_AMOUNT));
        ticker.setIssueMinRate(results.getDouble(ISSUE_MIN_RATE));
        ticker.setIssueMaxRate(results.getDouble(ISSUE_MAX_RATE));
        ticker.setIssueWgtAvgRate(results.getDouble(ISSUE_WGT_AVG_RATE));
        ticker.setFloatingCouponA(results.getDouble(FLOATING_COUPON_A));
        ticker.setFloatingCouponB(results.getDouble(FLOATING_COUPON_B));
        ticker.setFloatingCcouponC(results.getDouble(FLOATING_COUPON_C));
        ticker.setFloatingCouponD(results.getDouble(FLOATING_COUPON_D));
        ticker.setFloatingCouponE(results.getDouble(FLOATING_COUPON_E));
        ticker.setFloatingCouponIbor1(results.getDouble(FLOATING_COUPON_IBOR1));
        ticker.setFloatingCouponIbor2(results.getDouble(FLOATING_COUPON_IBOR2));
        ticker.setFloatingCouponTbill1(results.getDouble(FLOATING_COUPON_TBILL1));
        ticker.setFloatingCouponTbill2(results.getDouble(FLOATING_COUPON_TBILL2));
        ticker.setAuctionMinRate(results.getDouble(AUCTION_MIN_RATE));
        ticker.setAuctionMaxRate(results.getDouble(AUCTION_MAX_RATE));
        ticker.setAuctionWgtAvgRate(results.getDouble(AUCTION_WGT_AVG_RATE));
        ticker.setSukukType(results.getInt(SUKUK_TYPE));
        ticker.setLeadManager(results.getInt(LEAD_MANAGER));
        ticker.setMarketType(results.getInt(MARKET_TYPE));
        ticker.setBondType(results.getInt(BOND_TYPE));
        ticker.setCouponFrequency(results.getInt(COUPON_FREQUENCY));
        ticker.setCouponType(results.getInt(COUPON_TYPE));
        ticker.setCouponDayCount(results.getInt(COUPON_DAY_COUNT));
        ticker.setAuctionBids(results.getLong(AUCTION_BIDS));
        ticker.setIssueBids(results.getLong(ISSUE_BIDS));
        ticker.setNextCouponDate(results.getDate(NEXT_COUPON_DATE));
        ticker.setRemainingCouponCount(results.getInt(REMAINING_COUPON_COUNT));
        ticker.setLastCouponDate(results.getDate(LAST_COUPON_DATE));
        ticker.setLastTradedDate(results.getDate(LAST_TRADED_DATE));
        ticker.setAmountIssuedUSD(results.getDouble(AMOUNT_ISSUED_USD));
        ticker.setRequiredAmount(results.getDouble(REQUIRED_AMOUNT));
        ticker.setAmountIssuedInIssuerCurrency(results.getDouble(AMOUNT_ISSUED_ISSUER_CUR));
        ticker.setParValue(results.getDouble(PAR_VALUE));
        ticker.setFaceValue(results.getDouble(FACE_VALUE));
        ticker.setCouponDayCountDesc(results.getString(COUPON_DAY_COUNT_DESC));
        ticker.setEmbeddedOption(results.getString(EMBEDED_OPTIONS));
        ticker.setTradingClearanceDays(results.getInt(TRADING_CLEARANCE_DAYS));
        ticker.setYtm(results.getDouble(YTM));
        ticker.setAmountIssuedInLatestUsd(results.getDouble(AMOUNT_ISSUED_USD_LATEST));
        ticker.setIsPricePercentage(results.getInt(IS_PRICE_PERCENTAGE));
        return ticker;
    }

    public static String getUpdateFixedIncomeTickerColumnString(List<String> supportedLang) {
        if (updateFixedIncomeTickerColumnString == null) {
            updateFixedIncomeTickerColumnString = StringUtils.join(FIXED_INCOME_TICKER_COLUMNS, UPDATE_PLACEHOLDER)+UPDATE_PLACEHOLDER;//join TICKER_COLUMNS with "=?,"
            updateFixedIncomeTickerColumnString +=TickerDBHelper.getUpdateTickerColumnString(supportedLang);
        }
        return updateFixedIncomeTickerColumnString;
    }

    public static String getInsertFixedIncomeTickerColumnString(List<String> supportedLang) {
        if(insertFixedIncomeTickerColumnString == null){
            insertFixedIncomeTickerColumnString =StringUtils.join(FIXED_INCOME_TICKER_COLUMNS, IConstants.Delimiter.COMMA) +IConstants.Delimiter.COMMA;//join TICKER_COLUMNS with ","
            insertFixedIncomeTickerColumnString +=TickerDBHelper.getInsertTickerColumnString(supportedLang);
        }
        return insertFixedIncomeTickerColumnString;
    }

    private static String getFixedIncomeTickerInsertColumnParams(List<String> supportedLang){
        if (insertFixedIncomeTickerColumnParams == null) {
            insertFixedIncomeTickerColumnParams = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK,DBConstants.CommonDatabaseParams.SQL_COMMA,
                    FIXED_INCOME_TICKER_COLUMNS.length);
            insertFixedIncomeTickerColumnParams += DBConstants.CommonDatabaseParams.SQL_COMMA +TickerDBHelper.getTickerInsertColumnParams(supportedLang);
        }
        return insertFixedIncomeTickerColumnParams;
    }

    /**
     * build insert equity ticker sql string
     *
     * @return SQL String
     */
    public static String getInsertFixedIncomeTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_INSERT);
        sql.append(SQL_FIXED_INCOME);
        sql.append(SQL_BRACKET_OPEN);
        sql.append(getInsertFixedIncomeTickerColumnString(supportedLanguages)).append(INSERT_TICKER_SERIAL);
        sql.append(SQL_VALUES);
        sql.append(getFixedIncomeTickerInsertColumnParams(supportedLanguages)).append(INSERT_TICKER_SERIAL_PARAMS);
        sql.append(SQL_BRACKET_CLOSE);
        return sql.toString();
    }

    /**
     * build update equity ticker sql string
     *
     * @return SQL String
     */
    public static String getUpdateFixedIncomeTickerSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(SQL_UPDATE);
        sql.append(SQL_FIXED_INCOME);
        sql.append(SQL_SET);
        sql.append(getUpdateFixedIncomeTickerColumnString(supportedLanguages));
        sql.append(TICKER_UPDATE_WHERE);
        return sql.toString();
    }

    public static void setUpdateFixedIncomeTickerValues(PreparedStatement preparedStatement, FixedIncomeTickerDTO ticker, List<String> supportedLanguages) throws SQLException {
        int index = 1;
        if (ticker.getCouponDateFirst() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getCouponDateFirst().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getAnnounceDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getAnnounceDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getAuctionDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getAuctionDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getSettleDateFirst() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getSettleDateFirst().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getInterestAcrDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getInterestAcrDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getMaturityDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getMaturityDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getDateOfIssue() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getDateOfIssue().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        preparedStatement.setString(index++, ticker.getWkn());
        preparedStatement.setString(index++, ticker.getIssuedPeriod());
        preparedStatement.setString(index++, ticker.getCountryOfIssue());
        preparedStatement.setString(index++, ticker.getMaturityPeriod());
        preparedStatement.setDouble(index++, ticker.getAmountIssued());
        preparedStatement.setDouble(index++, ticker.getAmountOutstanding());
        preparedStatement.setDouble(index++, ticker.getMinimumPiece());
        preparedStatement.setDouble(index++, ticker.getMinimumIncrement());
        preparedStatement.setDouble(index++, ticker.getIssuePrice());
        preparedStatement.setDouble(index++, ticker.getCouponRate());
        preparedStatement.setDouble(index++, ticker.getMinPrice());
        preparedStatement.setDouble(index++, ticker.getYield());
        preparedStatement.setDouble(index++, ticker.getAuctionAmount());
        preparedStatement.setDouble(index++, ticker.getIssueMinRate());
        preparedStatement.setDouble(index++, ticker.getIssueMaxRate());
        preparedStatement.setDouble(index++, ticker.getIssueWgtAvgRate());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponA());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponB());
        preparedStatement.setDouble(index++, ticker.getFloatingCcouponC());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponD());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponE());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponIbor1());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponIbor2());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponTbill1());
        preparedStatement.setDouble(index++, ticker.getFloatingCouponTbill2());
        preparedStatement.setDouble(index++, ticker.getAuctionMinRate());
        preparedStatement.setDouble(index++, ticker.getAuctionMaxRate());
        preparedStatement.setDouble(index++, ticker.getAuctionWgtAvgRate());
        preparedStatement.setDouble(index++, ticker.getSukukType());
        preparedStatement.setInt(index++, ticker.getLeadManager());
        preparedStatement.setInt(index++, ticker.getMarketType());
        preparedStatement.setInt(index++, ticker.getBondType());
        preparedStatement.setInt(index++, ticker.getCouponFrequency());
        preparedStatement.setInt(index++, ticker.getCouponType());
        preparedStatement.setInt(index++, ticker.getCouponDayCount());
        preparedStatement.setLong(index++, ticker.getAuctionBids());
        preparedStatement.setLong(index++, ticker.getIssueBids());
        preparedStatement.setInt(index++, ticker.getRemainingCouponCount());
        if (ticker.getLastCouponDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getLastCouponDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        if (ticker.getNextCouponDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getNextCouponDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }

        if (ticker.getLastTradedDate() != null) {
            preparedStatement.setDate(index++, new Date(ticker.getLastTradedDate().getTime()));
        } else {
            preparedStatement.setNull(index++, Types.DATE);
        }
        preparedStatement.setDouble(index++, ticker.getAmountIssuedUSD());
        preparedStatement.setDouble(index++, ticker.getRequiredAmount());
        preparedStatement.setDouble(index++, ticker.getAmountIssuedInIssuerCurrency());
        preparedStatement.setDouble(index++,ticker.getFaceValue());
        preparedStatement.setDouble(index++,ticker.getParValue());
        preparedStatement.setString(index++,ticker.getCouponDayCountDesc());
        preparedStatement.setString(index++,ticker.getEmbeddedOption());
        preparedStatement.setInt(index++,ticker.getTradingClearanceDays());
        preparedStatement.setDouble(index++,ticker.getYtm());
        preparedStatement.setDouble(index++,ticker.getAmountIssuedInLatestUsd());
        preparedStatement.setInt(index++,ticker.getIsPricePercentage());

        index = TickerDBHelper.setUpdateTickerSetValues(preparedStatement, ticker, index, supportedLanguages);
        preparedStatement.setLong(index,ticker.getTickerSerial());
    }

    public static List<String> getFixedIncomeTickerColumns(List<String> supportedLanguages){
        List<String> columns = new ArrayList<String>(TickerDBHelper.getAllTickerColumns(supportedLanguages));
        columns.addAll(Arrays.asList(FIXED_INCOME_TICKER_COLUMNS));
        return columns;
    }

    public static String getTickersByTickerSerials(Collection<String> tickerSerialList, List<String> supportedLanguages){
        List<String> tickerColumns = getFixedIncomeTickerColumns(supportedLanguages);
        String tickers = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);
        String filter = QUERY_WHERE + TICKER_SERIAL;
        if(tickers.indexOf(IConstants.Delimiter.COMMA) > 0){
            filter += QUERY_IN + QUERY_BRACKET_OPEN + tickers + QUERY_BRACKET_CLOSE;
        }else{
            filter += QUERY_EQUAL + tickers;
        }
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS + filter;
    }

    public static String getTickersBySymbolExchange(String tickerId, String sourceId, List<String> supportedLanguages){
        List<String> tickerColumns = getFixedIncomeTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE + QUERY_UPPER + QUERY_BRACKET_OPEN + TICKER_ID + QUERY_BRACKET_CLOSE + QUERY_EQUAL +
                QUERY_QUOTE + tickerId.toUpperCase() + QUERY_QUOTE +  QUERY_AND + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE +
                sourceId + QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS + filter;
    }
}
