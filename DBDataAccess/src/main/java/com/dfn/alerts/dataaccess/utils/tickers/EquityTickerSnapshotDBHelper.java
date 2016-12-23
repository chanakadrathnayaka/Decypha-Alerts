package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.beans.tickers.snapshot.EquityTickerSnapshotDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 6/3/2015.
 */
public class EquityTickerSnapshotDBHelper {

    private final static String TICKERS_ALIAS = "t";
    private final static String TICKER_SNAPSHOT_ALIAS = "ts";

    public static String getEquityTickerSnapshotQuery(Collection<String> companyIdList, String instrumentTypes) {
        String query = null;
        String companies = StringUtils.join(companyIdList, IConstants.Delimiter.COMMA);

        if(!companies.isEmpty()) {

            query = QUERY_SELECT_ALL_FROM + QUERY_BRACKET_OPEN + QUERY_SELECT_ALL_FROM + DBConstants.TablesIMDB.TABLE_TICKERS + QUERY_WHERE + COMPANY_ID;

            if (companies.indexOf(IConstants.Delimiter.COMMA) > 0) {
                query += QUERY_IN + QUERY_BRACKET_OPEN + companies + QUERY_BRACKET_CLOSE;
            } else {
                query += QUERY_EQUAL + companies;
            }
            query += QUERY_AND + CompanyDBHelper.getCompanyFilterQuery(instrumentTypes);
            query += QUERY_BRACKET_CLOSE +" t"+ QUERY_LEFT_OUTER_JOIN + DBConstants.TablesIMDB.TABLE_TICKER_SNAPSHOT + " ts" + QUERY_ON +
                    QUERY_BRACKET_OPEN + "t." + TICKER_SERIAL + QUERY_EQUAL + "ts." + TICKER_SERIAL + QUERY_BRACKET_CLOSE;
        }
        return query;
    }

    public static TickerDTO getTickerDataForSubscription(ResultSet results) throws SQLException {
        TickerDTO ticker = new TickerDTO();
        ticker.setSourceId(results.getString(SOURCE_ID));
        ticker.setTickerId(results.getString(TICKER_ID));
        return ticker;
    }

    public static EquityTickerSnapshotDTO getEquityTickerSnapshotData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        EquityTickerSnapshotDTO ticker = new EquityTickerSnapshotDTO();
        //set ticker metadata
        EquityTickerDBHelper.setEquityTickerData(ticker, results, supportedLanguages);

        //add price data
        ticker.setTradeDate(results.getString(DBConstants.DBTickerSnapshotColumns.TRADE_DATE));
        ticker.setTradeTime(results.getString(DBConstants.DBTickerSnapshotColumns.TRADE_TIME));
        ticker.setPreviousTradeDate(results.getString(DBConstants.DBTickerSnapshotColumns.PREV_TRADE_DATE));
        ticker.setPreviousTradeTime(results.getString(DBConstants.DBTickerSnapshotColumns.PREV_TRADE_TIME));

        ticker.setTradePrice(results.getDouble(DBConstants.DBTickerSnapshotColumns.TRADE_PRICE));
        ticker.setMarketCap(results.getDouble(DBConstants.DBTickerSnapshotColumns.MARKETCAP));
        ticker.setMarketCapUSD(results.getDouble(DBConstants.DBTickerSnapshotColumns.MARKETCAP_USD));
        ticker.setHighPriceOf52Weeks(results.getDouble(DBConstants.DBTickerSnapshotColumns.HIGH_PRICE_OF_52_WEEKS));
        ticker.setLowPriceOf52Weeks(results.getDouble(DBConstants.DBTickerSnapshotColumns.LOW_PRICE_OF_52_WEEKS));
        ticker.setNetChange(results.getDouble(DBConstants.DBTickerSnapshotColumns.NETCHANGE));
        ticker.setPercentChange(results.getDouble(DBConstants.DBTickerSnapshotColumns.PERCENT_CHANGE));
        ticker.setYearChange(results.getFloat(DBConstants.DBTickerSnapshotColumns.YEAR_CHANGE));
        ticker.setYearPecentageChange(results.getFloat(DBConstants.DBTickerSnapshotColumns.YEAR_PERCENTAGE_CHANGE));
        ticker.setYTDPriceChang(results.getFloat(DBConstants.DBTickerSnapshotColumns.YTD_PRICE_CHANG));
        ticker.setYTDPricePercentageChange(results.getFloat(DBConstants.DBTickerSnapshotColumns.YTD_PRICE_PERCENTAGE_CHANGE));
        ticker.setYearTurnoverRatio(results.getFloat(DBConstants.DBTickerSnapshotColumns.YEAR_TURNOVER_RATIO));
        ticker.setNoOfTrades(results.getInt(DBConstants.DBTickerSnapshotColumns.NO_OF_TRADES));
        ticker.setTurnover(results.getDouble(DBConstants.DBTickerSnapshotColumns.TURNOVER));
        ticker.setVolume(results.getLong(DBConstants.DBTickerSnapshotColumns.VOLUME));
        ticker.setAverageVolume30D(results.getDouble(DBConstants.DBTickerSnapshotColumns.AVERAGE_VOLUME_30D));
        ticker.setHigh(results.getDouble(DBConstants.DBTickerSnapshotColumns.HIGH));
        ticker.setLow(results.getDouble(DBConstants.DBTickerSnapshotColumns.LOW));
        ticker.setAverageTurnover30D(results.getDouble(DBConstants.DBTickerSnapshotColumns.AVERAGE_TURNOVER_30D));
        ticker.setAverageTransactionValue30D(results.getDouble(DBConstants.DBTickerSnapshotColumns.AVERAGE_TRANSACTION_VALUE_30D));
        ticker.setVwap(results.getDouble(DBConstants.DBTickerSnapshotColumns.VWAP));
        ticker.setBeta(results.getDouble(DBConstants.DBTickerSnapshotColumns.BETA));
        ticker.setPreviousClosed(results.getDouble(DBConstants.DBTickerSnapshotColumns.PREVIOUS_CLOSED));
        ticker.setLastUpdatedTime(results.getTimestamp(DBConstants.DBTickerSnapshotColumns.LAST_UPDATED_TIME));
        ticker.setCmSubscriptionStatus(results.getString(DBConstants.DBTickerSnapshotColumns.SUBSCRIPTION_STATUS));
        ticker.setBestBidPrice(results.getDouble(DBConstants.DBTickerSnapshotColumns.BEST_BID_PRICE));
        ticker.setBestAskPrice(results.getDouble(DBConstants.DBTickerSnapshotColumns.BEST_ASK_PRICE));
        ticker.setOpen(results.getDouble(DBConstants.DBTickerSnapshotColumns.OPEN_PRICE));
        ticker.setTodaysClose(results.getDouble(DBConstants.DBTickerSnapshotColumns.TODAYS_CLOSED));
        ticker.setAdjustedPrices();
        return ticker;
    }

    /**
     * Get companySizeMcap Query
     * SELECT t.*,ts.*
     * FROM
     *  (
     *      SELECT * FROM TICKERS
     *      WHERE COUNTRY_CODE IN ('BH','EG','IQ','JO','KW','LB','LY','MA','OM','PS','QA','SA','TN','AE') AND COMPANY_ID > 0
     *      AND  (
     *             TICKER_SERIAL = 0
     *             OR  (
     *                  TICKER_SERIAL > 0 AND STATUS = 1 AND TICKER_CLASS_L3 IN  (62,64,65,61,63,66,68,67)
     *                  AND  (
     *                        LISTING_STATUS = 1 OR  (LISTING_STATUS = 2 AND IS_MAIN_STOCK = 1)
     *                       )
     *                  )
     *            )
     *   ) t
     * LEFT OUTER JOIN
     * TICKER_SNAPSHOT ts ON t.TICKER_SERIAL = ts.TICKER_SERIAL
     * WHERE ts.MARKETCAP IS NOT NULL
     *
     * @param tickerClass tickerClasses
     * @param page          pageNo
     * @param countries     countryList
     * @return query
     */
    public static String getCompanySizeQuery(String tickerClass, String page, String order, String column, String countries, String language) {
        String countryList = DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                StringUtils.join(countries.split(Character.toString(IConstants.Delimiter.COMMA)), DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                        DBConstants.SQL_COMMA + DBConstants.CommonDatabaseParams.QUERY_QUOTE) + DBConstants.CommonDatabaseParams.QUERY_QUOTE;

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SELECT);
        queryBuilder.append(TICKERS_ALIAS + IConstants.Delimiter.DOT + IConstants.ASTERISK + IConstants.COMMA);
        queryBuilder.append(TICKER_SNAPSHOT_ALIAS + IConstants.Delimiter.DOT + IConstants.ASTERISK + IConstants.COMMA);
        queryBuilder.append(getMcapChangeFunction());
        queryBuilder.append(FROM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(getCompanyHomeSubQuery(tickerClass, countryList));
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(IConstants.SPACE + TICKERS_ALIAS);
        queryBuilder.append(QUERY_LEFT_OUTER_JOIN);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_TICKER_SNAPSHOT + " ts");
        queryBuilder.append(QUERY_ON);
        queryBuilder.append(TICKERS_ALIAS + IConstants.Delimiter.DOT + TICKER_SERIAL);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(TICKER_SNAPSHOT_ALIAS + IConstants.Delimiter.DOT + TICKER_SERIAL);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(TICKERS_ALIAS + IConstants.Delimiter.DOT + DBConstants.DatabaseColumns.STOCK_MARKET_CAP_USD + QUERY_NOT_NULL);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(TICKERS_ALIAS + IConstants.Delimiter.DOT + DBConstants.DatabaseColumns.STOCK_MARKET_CAP_USD + QUERY_NOT_EQUALS + IConstants.ZERO);
        queryBuilder.append(getCompanyHomeSortingQuery(column, order, language));
        queryBuilder.append(paginationQuery(page));

        return queryBuilder.toString();

    }

    /**
     * get sql function to calculate mcap change
     * CASE  WHEN  (TRADE_PRICE > 0 AND PREVIOUS_CLOSED > 0 AND PREV_NO_OF_SHARES > 0 )
     * THEN  ( (TRADE_PRICE*NO_OF_SHARES-PREVIOUS_CLOSED*PREV_NO_OF_SHARES) / (PREVIOUS_CLOSED*PREV_NO_OF_SHARES) ) *100
     * ELSE 0 END CHANGE
     *
     * @return mcapchange
     */
    private static String getMcapChangeFunction() {
        String mcapChange = QUERY_CASE + QUERY_WHEN + QUERY_BRACKET_OPEN + TRADE_PRICE + QUERY_GREATER_THAN + 0 + QUERY_AND + PREVIOUS_CLOSED + QUERY_GREATER_THAN + 0 + QUERY_AND +
                PREV_NO_OF_SHARES + QUERY_GREATER_THAN + 0 + QUERY_BRACKET_CLOSE + QUERY_THEN + QUERY_BRACKET_OPEN + QUERY_BRACKET_OPEN + TRADE_PRICE + IConstants.Delimiter.ASTERISK +
                NO_OF_SHARES + IConstants.Delimiter.HYPHEN + PREVIOUS_CLOSED + IConstants.Delimiter.ASTERISK + PREV_NO_OF_SHARES + QUERY_BRACKET_CLOSE + "/" + QUERY_BRACKET_OPEN +
                PREVIOUS_CLOSED + IConstants.Delimiter.ASTERISK + PREV_NO_OF_SHARES + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE + IConstants.Delimiter.ASTERISK + 100 + QUERY_ELSE +
                0 + QUERY_END + DBConstants.DatabaseColumns.CHANGE;
        return mcapChange;
    }

    /**
     * Get sorting query for companySize mcap
     * default query ORDER BY MARKETCAP DESC
     *
     * @param column
     * @param order
     * @param language
     * @return sortingQuery
     */
    private static String getCompanyHomeSortingQuery(String column, String order, String language) {
        String sortQuery = QUERY_ORDER + DBConstants.DatabaseColumns.STOCK_MARKET_CAP_USD + QUERY_DESC_ORDER;
        if (column != null) {
            if (column.equals(IConstants.CompanyHome.MCAP)) {
                sortQuery = QUERY_ORDER + DBConstants.DatabaseColumns.STOCK_MARKET_CAP_USD + IConstants.SPACE + order.toUpperCase();
            } else if (column.equals(IConstants.CompanyHome.INDUSTRY)) {
                sortQuery = QUERY_ORDER + DBConstants.LangSpecificDatabaseColumns.GICS_L3_DESC + language.toUpperCase() + IConstants.SPACE + order.toUpperCase();
            } else if (column.equals(IConstants.CompanyHome.CHANGE)) {
                sortQuery = QUERY_ORDER + DBConstants.DatabaseColumns.CHANGE + IConstants.SPACE + order.toUpperCase();
            }
        } else {
            sortQuery = QUERY_ORDER + DBConstants.DatabaseColumns.STOCK_MARKET_CAP_USD + QUERY_DESC_ORDER;
        }

        return sortQuery;
    }

    /**
     * Get subquery for companySize
     *
     * @param tickerClass
     * @return filterQuery
     */
    private static String getCompanyHomeSubQuery(String tickerClass, String countryList) {

        String filterQuery = COMPANY_ID + QUERY_GREATER_THAN + 0 + QUERY_AND + QUERY_BRACKET_OPEN +
                QUERY_BRACKET_OPEN + TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND + STATUS + QUERY_EQUAL + 1 + QUERY_AND +
                TICKER_CLASS_L3 + QUERY_EQUAL + tickerClass + QUERY_AND + QUERY_BRACKET_OPEN +
                LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.LISTED + QUERY_OR + QUERY_BRACKET_OPEN +
                LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.MULTY_LISTED + QUERY_AND + IS_MAIN_STOCK + QUERY_EQUAL + 1 + QUERY_BRACKET_CLOSE +
                QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE;

        String subQuery = QUERY_SELECT_ALL_FROM + DBConstants.TablesIMDB.TABLE_TICKERS + QUERY_WHERE + DBConstants.DatabaseColumns.COUNTRY_CODE +
                QUERY_IN + QUERY_BRACKET_OPEN + countryList + QUERY_BRACKET_CLOSE + QUERY_AND + filterQuery;

        return subQuery;
    }

    /**
     * Get paginationQuery for companySize
     * @param pageNo
     * @return paginationQuery
     */
    private static String paginationQuery(String pageNo) {
        String query = "";
        if (pageNo.equals(Integer.toString(IConstants.DEFAULT_PAGE_INDEX_0))) {
            query = QUERY_TOP + IConstants.SPACE + 6 + IConstants.SPACE + QUERY_ROWS_ONLY;
        } else {
            int offset = (Integer.parseInt(pageNo) - 1) * IConstants.DEFAULT_PAGE_SIZE_5;
            query = SQL_OFFSET + offset + SQL_ROWS + FETCH_NEXT + 6 + IConstants.SPACE + QUERY_ROWS_ONLY;
        }
        return query;
    }
}
