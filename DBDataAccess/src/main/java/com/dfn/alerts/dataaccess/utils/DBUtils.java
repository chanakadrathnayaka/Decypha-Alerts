package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.beans.dcms.*;
import com.dfn.alerts.beans.financial.FinancialLineItem;
import com.dfn.alerts.beans.tickers.FundTickerDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.beans.tickers.snapshot.EquityTickerSnapshotDTO;
import com.dfn.alerts.beans.tickers.snapshot.IndexSnapshotDTO;
import com.dfn.alerts.beans.user.MiniUserDetails;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.News;
import com.dfn.alerts.dataaccess.utils.tickers.EquityTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.FixedIncomeTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.FundTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.IndexTickerDBHelper;
import com.dfn.alerts.utils.CommonUtils;
import com.dfn.alerts.utils.FormatUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import static com.dfn.alerts.constants.DBConstants.*;
import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.QUERY_OR;
import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.QUERY_QUOTE;

/**
 * Created by IntelliJ IDEA.
 * User: lasanthak
 * Date: 3/7/13
 * Time: 3:52 PM
 */
public class DBUtils {


    private static final Logger logger = LogManager.getLogger(DBUtils.class);

    private static final String DES_DELIMITER = "|";

    private static final String DERBY_PAGINATION_OFFSET = " OFFSET {0, number, #} ROWS  FETCH NEXT {1, number, #} ROWS ONLY ";
    private static final String DERBY_PAGINATION = " FETCH FIRST {0, number, #} ROWS ONLY ";
    private static final String H2_PAGINATION = " LIMIT {0, number, #} OFFSET {1, number, #}";

    private static final String DERBY_PAGINATION_OFFSET_QUERY = "{0} OFFSET {1, number, #} ROWS FETCH NEXT {2, number, #} ROWS ONLY ";
    private static final String DERBY_PAGINATION_QUERY = "{0} FETCH FIRST {1, number, #} ROWS ONLY ";
    private static final String H2_PAGINATION_QUERY = "{0} LIMIT {1, number, #} OFFSET {2, number, #}";
    private static final String ORACLE_PAGINATION_OFFSET_QUERY = "SELECT * FROM (SELECT ROWNUM RNUM, a.* FROM ({0}) WHERE ROWNUM <= {2, number, #} ) WHERE RNUM >= {1, number, #}";
    private static final String ORACLE_PAGINATION_QUERY = "SELECT * FROM ({0}) WHERE ROWNUM <= {1, number, #}";

    private static final String DERBY_DATE_FUNCTION = "DATE";
    private static final String H2_DATE_FUNCTION = "PARSEDATETIME";
    private static final String BRACKET_OPEN = "(";
    private static final String BRACKET_CLOSE = ")";
    private static final String COMMA = ",";
    private static final String QUOTE = "'";
    private static final String ORACLE_DATE_FUNTION = "TO_DATE";
    private static final String ORACLE_TIMESTAMP_FUNCTION = "TO_TIMESTAMP";
    private static final String DERBY_TIMESTAMP_FUNCTION = "TIMESTAMP";

    public static final int MAX_NO_OF_CHAR = 198;
    public static final int MAX_HEADLINE_LENGTH = 4000;
    public static final int ZERO_INDEX = 0;
    public static List<String> languages;

    static {
        languages = new ArrayList<String>(1);
        languages.add("EN");//todo : add it in a better way
        languages.add("AR");//todo : add it in a better way
    }

    private static final Logger log = LogManager.getLogger(DBUtils.class);

    public static Map<String, String> getProcessedLangStringAsMap(String langString) {

        Map<String, String> result = new HashMap<String, String>(4);

        if (langString != null && langString.contains(DES_DELIMITER)) {

            StringTokenizer strTkn = new StringTokenizer(langString, DES_DELIMITER);

            while (strTkn.hasMoreTokens()) {

                String[] keyVal = strTkn.nextToken().split(":");

                if (keyVal.length == 2) {
                    result.put(keyVal[0], keyVal[1]);
                }

            }
        }

        return result;
    }

    @Deprecated
    public static EquityTickerSnapshotDTO getEquityTickerSnapshotData(ResultSet results) throws SQLException {
        EquityTickerSnapshotDTO ticker = new EquityTickerSnapshotDTO();
        //set ticker metadata
        EquityTickerDBHelper.setEquityTickerData(ticker, results, languages);

        //add price data
        ticker.setTradeDate(results.getString(DBConstants.DBTickerSnapshotColumns.TRADE_DATE));
        ticker.setTradeTime(results.getString(DBConstants.DBTickerSnapshotColumns.TRADE_TIME));
        ticker.setPreviousTradeDate(results.getString(DBConstants.DBTickerSnapshotColumns.PREV_TRADE_DATE));
        ticker.setPreviousTradeTime(results.getString(DBTickerSnapshotColumns.PREV_TRADE_TIME));

        ticker.setTradePrice(results.getDouble(DBConstants.DBTickerSnapshotColumns.TRADE_PRICE));
        ticker.setMarketCap(results.getDouble(DBConstants.DBTickerSnapshotColumns.MARKETCAP));
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
        ticker.setBestBidPrice(results.getDouble(DBTickerSnapshotColumns.BEST_BID_PRICE));
        ticker.setBestAskPrice(results.getDouble(DBTickerSnapshotColumns.BEST_ASK_PRICE));
        ticker.setOpen(results.getDouble(DBTickerSnapshotColumns.OPEN_PRICE));
        ticker.setTodaysClose(results.getDouble(DBTickerSnapshotColumns.TODAYS_CLOSED));

        ticker.setAdjustedPrices();
        return ticker;
    }

    public static CurrencyDTO getCurrencyTickerSnapshotData(ResultSet results) throws SQLException {
        CurrencyDTO ticker = new CurrencyDTO();

        //add price data
        ticker.setTradeTime(results.getString(DBConstants.DBCurrencyTickersColumns.TRADE_TIME));
        ticker.setTradeDate(results.getString(DBConstants.DBCurrencyTickersColumns.TRADE_DATE));
        ticker.setPreviousTradeDate(results.getString(DBConstants.DBCurrencyTickersColumns.PREV_TRADE_DATE));
        ticker.setPreviousTradeTime(results.getString(DBCurrencyTickersColumns.PREV_TRADE_TIME));

        ticker.setCurrencyRate(results.getDouble(DBConstants.DBCurrencyTickersColumns.RATE));
        ticker.setChange(results.getDouble(DBConstants.DBCurrencyTickersColumns.CHANGE));
        ticker.setPercentageChange(results.getDouble(DBConstants.DBCurrencyTickersColumns.PERCENT_CHANGE));
        ticker.setHigh(results.getDouble(DBCurrencyTickersColumns.HIGH));
        ticker.setLow(results.getDouble(DBCurrencyTickersColumns.LOW));
        ticker.setOpen(results.getDouble(DBCurrencyTickersColumns.OPEN_PRICE));
        ticker.setCurrencyPair(results.getString(DBConstants.DBCurrencyTickersColumns.CURRENCY_PAIR));
        ticker.setExchange(results.getString(DBConstants.DBCurrencyTickersColumns.SOURCE_ID));
        ticker.setCmSubscriptionStatus(results.getString(DBConstants.DBCurrencyTickersColumns.SUBSCRIPTION_STATUS));

        return ticker;
    }

    public static CommodityDTO getCommodityTickerSnapshotData(ResultSet results) throws SQLException {
        CommodityDTO ticker = new CommodityDTO();

        //add price data
        ticker.setTradeTime(results.getString(DBConstants.DBCommoditySnapshotColumns.TRADE_TIME));
        ticker.setTradeDate(results.getString(DBConstants.DBCommoditySnapshotColumns.TRADE_DATE));
        ticker.setTradePrice(results.getDouble(DBConstants.DBCommoditySnapshotColumns.TRADE_PRICE));
        ticker.setPreviousTradeDate(results.getString(DBConstants.DBCommoditySnapshotColumns.PREV_TRADE_DATE));
        ticker.setPreviousTradeTime(results.getString(DBCommoditySnapshotColumns.PREV_TRADE_TIME));

        ticker.setChange(results.getDouble(DBConstants.DBCommoditySnapshotColumns.CHANGE));
        ticker.setPercentageChange(results.getDouble(DBConstants.DBCommoditySnapshotColumns.PERCENT_CHANGE));
        ticker.setTickerId(results.getString(DBConstants.DBCommoditySnapshotColumns.TICKER_ID));
        ticker.setExchange(results.getString(DBConstants.DBCommoditySnapshotColumns.SOURCE_ID));
        ticker.setInstrumentTypeId(results.getInt(DBConstants.DBCommoditySnapshotColumns.INSTRUMENT_TYPE));
        ticker.setCmSubscriptionStatus(results.getString(DBConstants.DBCommoditySnapshotColumns.SUBSCRIPTION_STATUS));
        ticker.setShortDescriptionLn(results.getString(DBConstants.DBCommoditySnapshotColumns.SHORT_DESCRIPTION));
        ticker.setLongDescriptionLn(results.getString(DBConstants.DBCommoditySnapshotColumns.LONG_DESCRIPTION));
        ticker.setCurrencyId(results.getString(DBConstants.DBCommoditySnapshotColumns.CURRENCY_ID));
        ticker.setHigh(results.getDouble(DBCurrencyTickersColumns.HIGH));
        ticker.setLow(results.getDouble(DBCurrencyTickersColumns.LOW));
        ticker.setOpen(results.getDouble(DBCurrencyTickersColumns.OPEN_PRICE));

        return ticker;
    }

    public static MarketSnapshotDTO getMarketSnapshotData(ResultSet results) throws SQLException {
        MarketSnapshotDTO marketSnapshotDTO = new MarketSnapshotDTO();
        //set ticker metadata
        //add price data
        marketSnapshotDTO.setExchangeCode(results.getString(DBConstants.DBMarketSnapshotColumns.SOURCE_ID));
        marketSnapshotDTO.setMainIndex(results.getString(DBConstants.DBMarketSnapshotColumns.MAIN_INDEX_TICKER));
        marketSnapshotDTO.setTimeZone(results.getString(DBConstants.DBMarketSnapshotColumns.TIMEZONE_ID));
        marketSnapshotDTO.setCountryCode(results.getString(DBConstants.DBMarketSnapshotColumns.COUNTRY_CODE));
        marketSnapshotDTO.setCurrency(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_CURRENCY));
        marketSnapshotDTO.setDecimalPlaces(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_DECIMAL_PLACES));
        marketSnapshotDTO.setDisplayExchange(results.getString(DBConstants.DBMarketSnapshotColumns.DISPLAY_CODE));
        marketSnapshotDTO.setTimeZoneOffset(results.getString(DBConstants.DBMarketSnapshotColumns.TIMEZONE_ID));
        marketSnapshotDTO.setExpandSubMarkets(results.getString(DBConstants.DBMarketSnapshotColumns.IS_EXPAND_SUBMKTS));
        marketSnapshotDTO.setIsDefaultMkt(results.getInt(DBConstants.DBMarketSnapshotColumns.IS_DEFAULT));
        marketSnapshotDTO.setWindowTypes(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_WINDOW_TYPES));
        marketSnapshotDTO.setVirtualExchange(results.getString(DBConstants.DBMarketSnapshotColumns.IS_VIRTUAL_EXCHANGE));
        marketSnapshotDTO.setMainIndexSource(results.getString(DBConstants.DBMarketSnapshotColumns.MAIN_INDEX_SOURCE));

        marketSnapshotDTO.setLongDescriptions(getProcessedLangStringAsMap(results.getString(DBConstants.DBMarketSnapshotColumns.LONG_DESCRIPTION_LAN)));
        marketSnapshotDTO.setShortDescriptions(getProcessedLangStringAsMap(results.getString(DBConstants.DBMarketSnapshotColumns.SHORT_DESCRIPTION_LAN)));

        marketSnapshotDTO.setMarketID(results.getString(DBConstants.DBMarketSnapshotColumns.MARKET_ID));
        marketSnapshotDTO.setDecimalCorrFactor(results.getInt(DBConstants.DBMarketSnapshotColumns.DECIMAL_CORR_FACTOR));
        marketSnapshotDTO.setMarketStatus(results.getInt(DBConstants.DBMarketSnapshotColumns.MARKET_STATUS));
        marketSnapshotDTO.setLastEODDate(results.getString(DBConstants.DBMarketSnapshotColumns.LAST_EOD_DATE));
        marketSnapshotDTO.setMarketDate(results.getString(DBConstants.DBMarketSnapshotColumns.MARKET_DATE));
        marketSnapshotDTO.setMarketTime(results.getString(DBConstants.DBMarketSnapshotColumns.MARKET_TIME));
        marketSnapshotDTO.setVolume(results.getLong(DBConstants.DBMarketSnapshotColumns.VOLUME));
        marketSnapshotDTO.setTurnover(results.getLong(DBConstants.DBMarketSnapshotColumns.TURNOVER));
        marketSnapshotDTO.setNoOfTrades(results.getInt(DBConstants.DBMarketSnapshotColumns.NO_OF_TRADES));
        marketSnapshotDTO.setSymbolsTraded(results.getInt(DBConstants.DBMarketSnapshotColumns.SYMBOLS_TRADED));
        marketSnapshotDTO.setNoOfUps(results.getInt(DBConstants.DBMarketSnapshotColumns.NO_OF_UPS));
        marketSnapshotDTO.setNoOfDown(results.getInt(DBConstants.DBMarketSnapshotColumns.NO_OF_DOWN));
        marketSnapshotDTO.setNoOfNoChange(results.getInt(DBConstants.DBMarketSnapshotColumns.NO_OF_NO_CHANGE));
        marketSnapshotDTO.setSubMarket(results.getBoolean(DBConstants.DBMarketSnapshotColumns.IS_SUBMARKET));
        marketSnapshotDTO.setCmSubscriptionStatus(results.getString(DBConstants.DBMarketSnapshotColumns.SUBSCRIPTION_STATUS));

        marketSnapshotDTO.setListedStockCount(results.getInt(DBConstants.DBMarketSnapshotColumns.LISTED_STOCKS_COUNT));
        marketSnapshotDTO.setSectorCount(results.getInt(DBConstants.DBMarketSnapshotColumns.SECTOR_COUNT));
        marketSnapshotDTO.setMarketCap(getMarketCap(results));

        marketSnapshotDTO.setRealTimeFeed(results.getInt(DBConstants.DatabaseColumns.IS_REALTIME_FEED));
        marketSnapshotDTO.setOffDays(getOffDays(results.getString(DatabaseColumns.OFF_DAYS)));
        marketSnapshotDTO.setMarketOpenInLocalTime(results.getString(DatabaseColumns.MARKET_OPEN_TIME));
        marketSnapshotDTO.setMarketCloseInLocalTime(results.getString(DatabaseColumns.MARKET_CLOSE_TIME));

        return marketSnapshotDTO;
    }

    private static double getMarketCap(ResultSet results) throws SQLException {
        double snapMarketCap = results.getDouble(DBConstants.DBMarketSnapshotColumns.SS_MARKET_CAP);
        double marketCap = results.getDouble(DBConstants.DBMarketSnapshotColumns.S_MARKET_CAP);

        if (snapMarketCap > 0) {
            marketCap = snapMarketCap;
        }
        return marketCap;
    }

    private static List<String> getOffDays(String offDays) throws SQLException {
        List<String> offDaysList;
        if (offDays != null && !offDays.isEmpty()) {
            offDaysList = Arrays.asList(offDays.split(String.valueOf(IConstants.Delimiter.COMMA)));
        } else {
            offDaysList = Collections.emptyList();
        }
        return offDaysList;
    }

    /**
     * Method to set MarketDTO
     *
     * @param results Result Set
     * @return MarketDTO object
     * @throws SQLException
     */
    public static MarketDTO getMarketData(ResultSet results) throws SQLException {
        MarketDTO marketDTO = new MarketSnapshotDTO();
        marketDTO.setExchangeCode(results.getString(DBConstants.DBMarketSnapshotColumns.SOURCE_ID));
        marketDTO.setMainIndex(results.getString(DBConstants.DBMarketSnapshotColumns.MAIN_INDEX_TICKER));
        marketDTO.setTimeZone(results.getString(DBConstants.DBMarketSnapshotColumns.TIMEZONE_ID));
        marketDTO.setCountryCode(results.getString(DBConstants.DBMarketSnapshotColumns.COUNTRY_CODE));
        marketDTO.setCurrency(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_CURRENCY));
        marketDTO.setDecimalPlaces(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_DECIMAL_PLACES));
        marketDTO.setDisplayExchange(results.getString(DBConstants.DBMarketSnapshotColumns.DISPLAY_CODE));
        marketDTO.setTimeZoneOffset(results.getString(DBConstants.DBMarketSnapshotColumns.TIMEZONE_ID));
        marketDTO.setExpandSubMarkets(results.getString(DBConstants.DBMarketSnapshotColumns.IS_EXPAND_SUBMKTS));
        marketDTO.setVirtualExchange(results.getString(DBConstants.DBMarketSnapshotColumns.IS_VIRTUAL_EXCHANGE));
        marketDTO.setMainIndexSource(results.getString(DBConstants.DBMarketSnapshotColumns.MAIN_INDEX_SOURCE));
        marketDTO.setLongDescriptions(getProcessedLangStringAsMap(results.getString(DBConstants.DBMarketSnapshotColumns.LONG_DESCRIPTION_LAN)));
        marketDTO.setShortDescriptions(getProcessedLangStringAsMap(results.getString(DBConstants.DBMarketSnapshotColumns.SHORT_DESCRIPTION_LAN)));
        marketDTO.setListedStockCount(results.getInt(DBConstants.DBMarketSnapshotColumns.LISTED_STOCKS_COUNT));
        marketDTO.setSectorCount(results.getInt(DBConstants.DBMarketSnapshotColumns.SECTOR_COUNT));
        marketDTO.setIsDefaultMkt(results.getInt(DBConstants.DBMarketSnapshotColumns.IS_DEFAULT));
        marketDTO.setWindowTypes(results.getString(DBConstants.DBMarketSnapshotColumns.DEFAULT_WINDOW_TYPES));
        marketDTO.setScreenerCode(results.getString(DBConstants.DBMarketSnapshotColumns.SCREENER_CODE));
        marketDTO.setMarketCap(results.getDouble(DBConstants.DBMarketSnapshotColumns.MARKET_CAP));

        marketDTO.setRealTimeFeed(results.getInt(DatabaseColumns.IS_REALTIME_FEED));
        marketDTO.setOffDays(getOffDays(results.getString(DatabaseColumns.OFF_DAYS)));
        marketDTO.setMarketOpenInLocalTime(results.getString(DatabaseColumns.MARKET_OPEN_TIME));
        marketDTO.setMarketCloseInLocalTime(results.getString(DatabaseColumns.MARKET_CLOSE_TIME));
        /*
        TODO remove this method and use getMarketSnapshotData()
         */
        return marketDTO;
    }

    public static IndexSnapshotDTO getIndexSnapshotData(ResultSet results, List<String> supportedLanguages) throws SQLException {
        IndexSnapshotDTO indexDTO = new IndexSnapshotDTO();
        IndexTickerDBHelper.setIndexData(indexDTO, results, supportedLanguages);
        indexDTO.setIndexValue(results.getDouble(DBConstants.DBIndicesColumns.INDEX_VALUE));
        indexDTO.setIndexOpen(results.getDouble(DBConstants.DBIndicesColumns.INDEX_OPEN));
        indexDTO.setAdjPrevClosed(results.getDouble(DBConstants.DBIndicesColumns.ADJ_PREV_CLOSED));
        indexDTO.setNetChange(results.getDouble(DBConstants.DBIndicesColumns.NET_CHANGE));
        indexDTO.setPcntChange(results.getDouble(DBConstants.DBIndicesColumns.PCNT_CHANGE));
        indexDTO.setIndexHigh(results.getDouble(DBConstants.DBIndicesColumns.INDEX_HIGH));
        indexDTO.setIndexLow(results.getDouble(DBConstants.DBIndicesColumns.INDEX_LOW));
        indexDTO.setVolume(results.getLong(DBConstants.DBIndicesColumns.VOLUME));
        indexDTO.setTurnover(results.getDouble(DBConstants.DBIndicesColumns.TURNOVER));
        indexDTO.setWeightedIndexValue(results.getDouble(DBConstants.DBIndicesColumns.WEIGHTED_INDEX_VALUE));
        indexDTO.setIndexClose(results.getDouble(DBConstants.DBIndicesColumns.INDEX_CLOSE));
        indexDTO.setDecimalCorrFactor(results.getInt(DBConstants.DBIndicesColumns.DECIMAL_CORR_FACTOR));
        indexDTO.setInstrumentType(results.getInt(DBConstants.DBIndicesColumns.INSTRUMENT_TYPE));
        indexDTO.setForcastedIndexValue(results.getDouble(DBConstants.DBIndicesColumns.FORCASTED_INDEX_VALUE));
        indexDTO.setForcastedNetChange(results.getDouble(DBConstants.DBIndicesColumns.FORCASTED_NET_CHANGE));
        indexDTO.setForcastedPctChange(results.getDouble(DBConstants.DBIndicesColumns.FORCASTED_PCT_CHANGE));
        indexDTO.setForecasting(results.getInt(DBConstants.DBIndicesColumns.IS_FORECASTING));
        indexDTO.setSessionPecentageChange(results.getFloat(DBConstants.DBIndicesColumns.SESSION_PECENTAGE_CHANGE));
        indexDTO.setWeekPecentageChange(results.getFloat(DBConstants.DBIndicesColumns.WEEK_PECENTAGE_CHANGE));
        indexDTO.setMonthPecentageChange(results.getFloat(DBConstants.DBIndicesColumns.MONTH_PECENTAGE_CHANGE));
        indexDTO.setYearPecentageChange(results.getFloat(DBConstants.DBIndicesColumns.YEAR_PECENTAGE_CHANGE));
        indexDTO.setSessionChange(results.getFloat(DBConstants.DBIndicesColumns.SESSION_CHANGE));
        indexDTO.setWeekChange(results.getFloat(DBConstants.DBIndicesColumns.WEEK_CHANGE));
        indexDTO.setMonthChange(results.getFloat(DBConstants.DBIndicesColumns.MONTH_CHANGE));
        indexDTO.setYearChange(results.getFloat(DBConstants.DBIndicesColumns.YEAR_CHANGE));
        indexDTO.setHigh52Week(results.getFloat(DBConstants.DBIndicesColumns.HIGH_52WEEK));
        indexDTO.setLow52Week(results.getFloat(DBConstants.DBIndicesColumns.LOW_52WEEK));
        indexDTO.setAverage30DTurnover(results.getDouble(DBConstants.DBIndicesColumns.AVERAGE_30D_TURNOVER));
        indexDTO.setLastUpdatedDateTime(results.getString(DBConstants.DBIndicesColumns.LAST_UPDATED_DATE_TIME));
        indexDTO.setCmSubscriptionStatus(results.getString(DBConstants.DBIndicesColumns.SUBSCRIPTION_STATUS));
        indexDTO.setMarketCap(results.getDouble(DBConstants.DBIndicesColumns.MARKET_CAP));
        indexDTO.setChangeMarketCap(results.getDouble(DBConstants.DBIndicesColumns.CHANGE_IN_MARKET_CAP));
        indexDTO.setAverage30DTransaction(results.getDouble(DBConstants.DBIndicesColumns.AVERAGE_30D_TRANSACTION));
        indexDTO.setNumberOfUps(results.getInt(DBConstants.DBIndicesColumns.NO_OF_UPS));
        indexDTO.setNumberOfDowns(results.getInt(DBConstants.DBIndicesColumns.NO_OF_DOWN));
        indexDTO.setNumberOfNoChanges(results.getInt(DBConstants.DBIndicesColumns.NO_OF_NO_CHANGE));
        indexDTO.setTradedSymbols(results.getInt(DBConstants.DBIndicesColumns.SYMBOLS_TRADED));
        indexDTO.setNumberOfTrades(results.getInt(DBConstants.DBIndicesColumns.NUMBER_OF_TRADES));//not  in CM
        indexDTO.setPriceChangeYTD(results.getFloat(DBIndicesColumns.YTD_PRICE_CHANGE));
        indexDTO.setPricePercentageChangeYTD(results.getFloat(DBIndicesColumns.YTD_PRICE_PERCENTAGE_CHANGE));

        return indexDTO;
    }


    /**
     * Get Column values from the result set and generate PriceSnapshotAdjustedDTO
     *
     * @param resultSet Result Set result set
     * @return PriceSnapshotAdjustedDTO price snapshot object
     * @throws SQLException
     */
    public static PriceSnapshotAdjustedDTO getAdjustedPriceSnapshotData(ResultSet resultSet) throws SQLException {
        PriceSnapshotAdjustedDTO priceSnapshotAdjustedDTO = new PriceSnapshotAdjustedDTO();

        priceSnapshotAdjustedDTO.setTickerSerial(resultSet.getInt(DatabaseColumns.TICKER_SERIAL));
        priceSnapshotAdjustedDTO.setTradePrice(resultSet.getDouble(DatabaseColumns.TRADE_PRICE));
        priceSnapshotAdjustedDTO.setTradePriceUpdatedDate(resultSet.getDate(DatabaseColumns.TRADE_PRICE_UPDATED_TIME));
        priceSnapshotAdjustedDTO.setBestAskPrice(resultSet.getDouble(DatabaseColumns.BEST_ASK_PRICE));
        priceSnapshotAdjustedDTO.setBestAskPriceUpdatedDate(resultSet.getDate(DatabaseColumns.BEST_ASK_PRICE_UPDATED_TIME));
        priceSnapshotAdjustedDTO.setBestBidPrice(resultSet.getDouble(DatabaseColumns.BEST_BID_PRICE));
        priceSnapshotAdjustedDTO.setBestBidPriceUpdatedDate(resultSet.getDate(DatabaseColumns.BEST_BID_PRICE_UPDATED_TIME));

        return priceSnapshotAdjustedDTO;
    }

    /**
     * Get TickerDTO from result set
     *
     * @param results Result Set
     * @return Ticker DTO
     * @throws SQLException any sql error *
     */
    public static SourceDTO getSourceData(ResultSet results) throws SQLException {
        SourceDTO source = new SourceDTO();
        source.setSourceId(results.getString(DBConstants.DatabaseColumns.S_SOURCE_ID));
        source.setCloseTime(results.getTimestamp(DBConstants.DatabaseColumns.S_CLOSE_TIME));
        source.setCountryCode(results.getString(DBConstants.DatabaseColumns.S_COUNTRY_CODE));
        source.setDefaultCurrency(results.getString(DBConstants.DatabaseColumns.S_DEFAULT_CURRENCY));
        source.setDefaultDecimalPlaces(results.getInt(DBConstants.DatabaseColumns.S_DEFAULT_DECIMAL_PLACES));
        source.setDisplayCode(results.getString(DBConstants.DatabaseColumns.S_DISPLAY_CODE));
        source.setExpandSubmkts(results.getInt(DBConstants.DatabaseColumns.S_IS_EXPAND_SUBMKTS));
        source.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.S_LAST_UPDATED_TIME));
        source.setLongDescriptionLan(results.getString(DBConstants.DatabaseColumns.S_LONG_DESCRIPTION_LAN));
        source.setMainIndexSource(results.getString(DBConstants.DatabaseColumns.S_MAIN_INDEX_SOURCE));
        source.setMainIndexTicker(results.getString(DBConstants.DatabaseColumns.S_MAIN_INDEX_TICKER));
        source.setMainIndexTickerSerial(results.getLong(DBConstants.DatabaseColumns.S_MAIN_INDEX_TICKER_SERIAL));
        source.setOpenTime(results.getTimestamp(DBConstants.DatabaseColumns.S_OPEN_TIME));
        source.setShortDescriptionLan(results.getString(DBConstants.DatabaseColumns.S_SHORT_DESCRIPTION_LAN));
        source.setLongDescriptionLan(results.getString(DBConstants.DatabaseColumns.S_LONG_DESCRIPTION_LAN));
        source.setSourceSerial(results.getInt(DBConstants.DatabaseColumns.S_SOURCE_SERIAL));
        source.setStatus(results.getInt(DBConstants.DatabaseColumns.S_STATUS));
        source.setTimezoneId(results.getInt(DBConstants.DatabaseColumns.S_TIMEZONE_ID));
        source.setVirtualExchange(results.getInt(DBConstants.DatabaseColumns.S_IS_VIRTUAL_EXCHANGE));
        source.setWeekStart(results.getInt(DBConstants.DatabaseColumns.S_WEEK_START));
        source.setListedStocks(results.getInt(DBConstants.DatabaseColumns.S_LISTED_STOCKS_COUNT));
        source.setSectorCount(results.getInt(DBConstants.DatabaseColumns.S_SECTOR_COUNT));
        source.setIsDefaultMkt(results.getInt(DBConstants.DatabaseColumns.S_IS_DEFAULT));
        source.setWindowTypes(results.getString(DBConstants.DatabaseColumns.S_DEFAULT_WINDOW_TYPE));
        source.setScreenerCode(results.getString(DBConstants.DatabaseColumns.S_SCREENER_CODE));
        source.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));
        source.setMarketCap(results.getDouble(DBConstants.DatabaseColumns.MARKET_CAP));
        source.setRealTimeFeed(results.getInt(DBConstants.DatabaseColumns.IS_REALTIME_FEED));
        source.setOffDays(results.getString(DBConstants.DatabaseColumns.OFF_DAYS));
        return source;
    }

    /**
     * Get Individual DTO
     *
     * @param results Result Set
     * @return IndividualDTO Object
     * @throws SQLException any sql error *
     */
    public static IndividualDTO getIndividual(ResultSet results) throws SQLException {
        IndividualDTO individualDTO = new IndividualDTO();
        individualDTO.setIndividualId(results.getInt(DBConstants.DatabaseColumns.IND_DETAIL_ID));
        individualDTO.setIndividualName(results.getString(DBConstants.DatabaseColumns.IND_DETAIL_NAME));
        individualDTO.setEmail(results.getString(DatabaseColumns.EMAIL));
        individualDTO.setPhone(results.getString(DatabaseColumns.PHONE_NO));
        individualDTO.setRelatedCompanies(results.getString(DBConstants.DatabaseColumns.IND_DETAIL_RELATED_COM));
        individualDTO.setRelatedIndividuals(results.getString(DBConstants.DatabaseColumns.IND_DETAIL_RELATED_IND));
        individualDTO.setRelatedOwners(results.getString(DBConstants.DatabaseColumns.RELATED_OWNERS));
        individualDTO.setRelatedManagers(results.getString(DBConstants.DatabaseColumns.RELATED_MEMBERS));
        individualDTO.setRelatedInsTraders(results.getString(DBConstants.DatabaseColumns.RELATED_INSIDER_TRANS));
        individualDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
        setIndividualDTOMap(individualDTO, results, languages);

        boolean logo = false;
        if (results.getBlob(DBConstants.DatabaseColumns.LOGO) != null) {
            logo = true;
        }
        individualDTO.setLogo(logo);

        return individualDTO;
    }

    /**
     * For Country profile page.
     * Need to get only individual's name and id
     *
     * @param results result set
     * @return map of data
     * @throws SQLException if any reading error
     */
    public static Map<String, String> getLastUpdatedIndividuals(ResultSet results) throws SQLException {
        Map<String, String> individualMap = new HashMap<String, String>(3);
        individualMap.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID));
        individualMap.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME));
        individualMap.put(DBConstants.DatabaseColumns.IND_COUNTRY_CODE, results.getString(DBConstants.DatabaseColumns.IND_COUNTRY_CODE));
        return individualMap;
    }

    /**
     * get kpi definition map
     *
     * @param results results
     * @return map
     * @throws SQLException
     */
    public static Map<String, String> getKPIDefinition(ResultSet results) throws SQLException {
        Map<String, String> definitionMap = new HashMap<String, String>(3);
        definitionMap.put(DBConstants.DatabaseColumns.KPI_TITLE, results.getString(DBConstants.DatabaseColumns.KPI_TITLE));
        definitionMap.put(DBConstants.DatabaseColumns.KPI_DEFINITION_ID, results.getString(DBConstants.DatabaseColumns.KPI_DEFINITION_ID));
        definitionMap.put(DBConstants.DatabaseColumns.KPI_MEASURING_UNIT, results.getString(DBConstants.DatabaseColumns.KPI_MEASURING_UNIT));
        return definitionMap;
    }

    public static TickerDTO getTickerData(ResultSet results, IConstants.AssetType type, List<String> supportedLanguages) throws SQLException {
        TickerDTO tickerDTO;
        switch (type) {
            case EQUITY:
                tickerDTO = EquityTickerDBHelper.getEquityTickerData(results, supportedLanguages);
                break;
            case MUTUAL_FUNDS:
                tickerDTO = FundTickerDBHelper.getFundTickerData(results, supportedLanguages);
                break;
            case FIXED_INCOME:
                tickerDTO = FixedIncomeTickerDBHelper.getFixedIncomeTickerData(results, supportedLanguages);
                break;
            case INDEX:
                tickerDTO = IndexTickerDBHelper.getIndexTickerData(results, supportedLanguages);
                break;
            default:
                tickerDTO = null;
                break;

        }
        return tickerDTO;
    }

    /**
     * @param results result set
     * @return CountryTickerDTO
     */
    public static CountryTickerDTO getCountryTickerData(ResultSet results) throws SQLException {
        CountryTickerSnapshotDTO ticker = new CountryTickerSnapshotDTO();
        ticker.setTickerSerial(results.getLong(DBConstants.DatabaseColumns.COUNTRY_TICKER_SERIAL));
        ticker.setTickerId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_ID));
        ticker.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_COUNTRY_CODE));
        ticker.setCurrencyId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_CURRENCY_ID));
        ticker.setDecimalPlaces(results.getInt(DBConstants.DatabaseColumns.COUNTRY_TICKER_DECIMAL_PLACES));
        ticker.setDisplayTicker(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_DISPLAY_TICKER));
        ticker.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.COUNTRY_TICKER_LAST_UPDATED_TIME));
        ticker.setSourceId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_SOURCE_ID));
        ticker.setTickerShortDesLn(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_SHORT_DES_LN));
        // removed UNIT and TICKER_LONG_DES_LN columns since those are not in use and no columns found in oracle DB
        ticker.setInstrumentTypeId(results.getInt(DBConstants.DatabaseColumns.COUNTRY_TICKER_INSTRUMENT_TYPE_ID));
        ticker.setSourceId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_SOURCE_ID));
        return ticker;
    }

    /**
     * @param results result set
     * @return CountryTickerDTO
     * @throws SQLException
     */
    public static CountryTickerDTO getCountryTickerDataSnapshot(ResultSet results) throws SQLException {
        CountryTickerSnapshotDTO ticker = (CountryTickerSnapshotDTO) getCountryTickerData(results);
        ticker.setHistoryData(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_HISTORY_DATA));
        return ticker;
    }

    public static FixedIncomeTypeCountDTO geFixedIncomeTickerTypeCount(ResultSet results) throws SQLException {
        FixedIncomeTypeCountDTO fixedIncomeTypeCountDTO = new FixedIncomeTypeCountDTO();
        fixedIncomeTypeCountDTO.setBondType(results.getInt(DBConstants.FixedIncomeCountColumns.BOND_TYPE));
        fixedIncomeTypeCountDTO.setInstrumentTypeId(results.getInt(DBConstants.FixedIncomeCountColumns.INSTRUMENT_TYPE_ID));
        fixedIncomeTypeCountDTO.setOutsatndingSize(results.getLong(DBConstants.FixedIncomeCountColumns.TOTAL_AMOUNT_OUTSTANDING));
        fixedIncomeTypeCountDTO.setCount(results.getInt(DBConstants.FixedIncomeCountColumns.TYPE_COUNT));

        return fixedIncomeTypeCountDTO;
    }

    public static String getIndexConstituentsSql(String indexConstituents) {
        return CommonDatabaseParams.QUERY_SELECT + "T.*, TS.*" + CommonDatabaseParams.FROM + TablesIMDB.TABLE_TICKERS + " T " +
                CommonDatabaseParams.QUERY_LEFT_OUTER_JOIN + TablesIMDB.TABLE_TICKER_SNAPSHOT + " TS " + CommonDatabaseParams.QUERY_ON +
                CommonDatabaseParams.SQL_BRACKET_OPEN + "TS." + DatabaseColumns.TICKER_SERIAL + CommonDatabaseParams.QUERY_EQUAL +
                " T." + DatabaseColumns.TICKER_SERIAL + CommonDatabaseParams.SQL_BRACKET_CLOSE +
                DBConstants.WHERE + "T." + DatabaseColumns.TICKER_SERIAL + CommonDatabaseParams.QUERY_IN +
                CommonDatabaseParams.SQL_BRACKET_OPEN + indexConstituents + CommonDatabaseParams.SQL_BRACKET_CLOSE +
                CommonDatabaseParams.QUERY_ORDER + DBTickerSnapshotColumns.MARKETCAP + CommonDatabaseParams.QUERY_DESC_ORDER;
    }

    @Deprecated
    public static String getIndexConstituentsSql(long indexTickerSerial, String sourceID) {
        //TODO add constants

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT T.*, TS.*");
        stringBuilder.append(" FROM TICKERS T LEFT OUTER JOIN TICKER_SNAPSHOT TS ON TS.TICKER_ID = T.TICKER_ID AND TS.SOURCE_ID = T.SOURCE_ID");
        stringBuilder.append(" WHERE ");
        stringBuilder.append(" LISTED_INDEXS like");
        stringBuilder.append(" '%");
        stringBuilder.append(indexTickerSerial);
        stringBuilder.append("%' ");
        stringBuilder.append("AND T.SOURCE_ID ='");
        stringBuilder.append(sourceID);
        stringBuilder.append("'");
        stringBuilder.append(" AND T.STATUS = 1 ORDER BY MARKETCAP DESC");

        return stringBuilder.toString();
    }

    public static void finalizeDBResources(Connection connection, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
    }

    public static void finalizeDBResources(Connection connection, Statement[] prepStatements, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
        if (prepStatements != null && prepStatements.length > 0) {
            try {
                for (Statement preps : prepStatements) {
                    if (preps != null) {
                        preps.close();
                    }
                }

            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * Set News Item
     *
     * @param results Result Set
     * @return Ticker Object
     * @throws SQLException any sql error
     */
    public static Map<String, String> setNewsItem(ResultSet results) throws SQLException {
        Map<String, String> newsItem = new HashMap<String, String>(33);
        for (Field field : DBConstants.NewsDatabaseColumns.class.getFields()) {
            newsItem.put(field.getName(), results.getString(field.getName()));
        }
        return newsItem;
    }

    /**
     * Set News Items for news sections
     * Virtual columns are used for data manipulation purposes
     *
     * @param results Result Set
     * @return Ticker Object
     * @throws SQLException any sql error
     */
    public static Map<String, String> setNewsSectionItem(ResultSet results) throws SQLException {
        Map<String, String> newsItem = new HashMap<String, String>(35);
        for (Field field : DBConstants.NewsDatabaseColumns.class.getFields()) {
            newsItem.put(field.getName(), results.getString(field.getName()));
        }
        for (Field field : DBConstants.NewsDatabaseVirtualColumns.class.getFields()) {
            newsItem.put(field.getName(), results.getString(field.getName()));
        }
        return newsItem;
    }

    /**
     * Set News Items for news individuals
     *
     * @param results Result Set
     * @return news object
     * @throws SQLException any sql error
     */
    public static Map<String, String> setNewsIndividualItem(ResultSet results) throws SQLException {
        // TODO: move to a class
        String[] colArray = new String[]{"NEWS_ID", "NEWS_HEADLINE_LN", "INDIVIDUAL_ID", "NAME", "COUNTRY_CODE", "NEWS_DATE"};
        Map<String, String> newsItem = new HashMap<String, String>(colArray.length);
        for (String field : colArray) {
            newsItem.put(field, results.getString(field));
        }
        return newsItem;
    }

    public static void setNewsPersistItem(News news, Map<String, String> newsMap) {
        //28
        news.setNewsId(Integer.parseInt(newsMap.get(NewsDatabaseColumns.NEWS_ID)));
        if (newsMap.get(NewsDatabaseColumns.EXT_ID) != null) {
            news.setExtId(Integer.parseInt(newsMap.get(NewsDatabaseColumns.EXT_ID)));
        } else {
            news.setExtId(0);
        }
        news.setNewsDate(formatDate(newsMap.get(NewsDatabaseColumns.NEWS_DATE)));
        news.setExchange(newsMap.get(NewsDatabaseColumns.EXCHANGE));
        news.setSymbol(newsMap.get(NewsDatabaseColumns.SYMBOL));
        news.setNewsHeadlineLn(newsMap.get(NewsDatabaseColumns.NEWS_HEADLINE_LN));
        if (newsMap.get(NewsDatabaseColumns.HOT_NEWS_INDICATOR) != null) {
            news.setHotNewsIndicator(processInt(newsMap.get(NewsDatabaseColumns.HOT_NEWS_INDICATOR)));
        } else {
            news.setHotNewsIndicator(5);
        }
        news.setLanguageId(newsMap.get(NewsDatabaseColumns.LANGUAGE_ID));
        news.setNewsProvider(newsMap.get(NewsDatabaseColumns.NEWS_PROVIDER));
        news.setAssetClass(newsMap.get(NewsDatabaseColumns.ASSET_CLASS));
        news.setEditorialCode(newsMap.get(NewsDatabaseColumns.EDITORIAL_CODE));
        news.setGeoRegionCode(newsMap.get(NewsDatabaseColumns.GEO_REGION_CODE));
        news.setGovernmentCode(newsMap.get(NewsDatabaseColumns.GOVERNMENT_CODE));
        news.setIndividualCode(newsMap.get(NewsDatabaseColumns.INDIVIDUAL_CODE));
        news.setIndustryCode(newsMap.get(NewsDatabaseColumns.INDUSTRY_CODE));
        news.setMarketSectorCode(newsMap.get(NewsDatabaseColumns.MARKET_SECTOR_CODE));
        news.setProductServicesCode(newsMap.get(NewsDatabaseColumns.PRODUCT_SERVICES_CODE));
        news.setSeqId(Integer.parseInt(newsMap.get(NewsDatabaseColumns.SEQ_ID)));
        news.setStatus(newsMap.get(NewsDatabaseColumns.STATUS));
        news.setInternalClassType(newsMap.get(NewsDatabaseColumns.APPROVAL_STATUS));
        if (newsMap.get(NewsDatabaseColumns.APPROVAL_STATUS) != null) {
            news.setApprovalStatus(processInt(newsMap.get(NewsDatabaseColumns.APPROVAL_STATUS)));
        } else {
            news.setApprovalStatus(0);
        }
        news.setSourceId(newsMap.get(NewsDatabaseColumns.SOURCE_ID));
        news.setTickerId(newsMap.get(NewsDatabaseColumns.TICKER_ID));
        news.setUrl(newsMap.get(NewsDatabaseColumns.URL));
        news.setCountry(newsMap.get(NewsDatabaseColumns.COUNTRY));
        if (newsMap.get(NewsDatabaseColumns.UNIQUE_SEQUENCE_ID) != null) {
            news.setUniqueSequenceId(processInt(newsMap.get(NewsDatabaseColumns.UNIQUE_SEQUENCE_ID)));
        } else {
            news.setUniqueSequenceId(0);
        }
        if (newsMap.get(NewsDatabaseColumns.LAST_UPDATED_TIME) != null) {
            news.setLastUpdatedTime(formatDate(newsMap.get(NewsDatabaseColumns.LAST_UPDATED_TIME)));
        } else {
            news.setLastUpdatedTime(new Date());
        }
        news.setCompanyId(newsMap.get(NewsDatabaseColumns.COMPANY_ID));

        news.setNewsSourceId(newsMap.get(NewsDatabaseColumns.NEWS_SOURCE_ID));
        news.setNewsSourceDesc(newsMap.get(NewsDatabaseColumns.NEWS_SOURCE_DESC));
    }

    /**
     * Set seqId value
     *
     * @param results result set
     * @param type    type
     * @return max seq id
     * @throws SQLException
     */
    public static String setSeqId(ResultSet results, int type) throws SQLException {
        String id = null;
        switch (type) {
            case IConstants.ControlPathTypes.NEWS_SEARCH_MAX_SEQ_ID:
                id = results.getString(DBConstants.DatabaseColumns.SEQ_ID_MAX);
                break;
            case IConstants.ControlPathTypes.ANNOUNCE_SEARCH_MAX_SEQ_ID:
                id = results.getString(DBConstants.DatabaseColumns.ANN_ID_MAX);
                break;
        }
        return id;
    }

    public static Integer processInt(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(str);
    }

    public static Date formatDate(String dateStr) {
        Date date = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            logger.error("Error", e);
        }
        return date;
    }

    /**
     * populate Individual Snapshot DTO
     * /**
     * populate Individual Snapshot DTO
     *
     * @param results Result Set
     * @return IndividualSnapshotDTO Object
     * @throws SQLException *
     */
    public static IndividualSnapshotDTO populateIndividualSnapShot(ResultSet results) throws SQLException {
        IndividualSnapshotDTO individualSnapshotDTO;
        List<Map<String, String>> individualNews = new ArrayList<Map<String, String>>(3);
        List<Map<String, String>> insiderTransactions = new ArrayList<Map<String, String>>(3);
        List<Map<String, String>> ownershipUpdates = new ArrayList<Map<String, String>>(3);
        List<Map<String, String>> designationUpdates = new ArrayList<Map<String, String>>(3);
        List<Map<String, String>> lastUpdatedProfiles = new ArrayList<Map<String, String>>(1);
        Map<String, String> dataRow;
        Map<String, String> mixObject;

        while (results.next()) {
            dataRow = new HashMap<String, String>(8);
            mixObject = getMIXObject(results.getString(DBConstants.DatabaseColumns.IND_HED), results.getString(DBConstants.DatabaseColumns.IND_DAT));

            IConstants.IndividualSnapshotTypes snapType
                    = IConstants.IndividualSnapshotTypes.valueOf(results.getString(DBConstants.DatabaseColumns.IND_SNAP_TYPE));
            switch (snapType) {
                case INTR:
                    dataRow = getIndividualInsiders(mixObject);
                    insiderTransactions.add(dataRow);
                    break;
                case OWN:
                    dataRow = getIndividualOwnership(mixObject);
                    ownershipUpdates.add(dataRow);
                    break;
                case DESG:
                    dataRow = getIndividualDesignations(mixObject);
                    designationUpdates.add(dataRow);
                    break;
                case INDV:
                    dataRow = getIndividualProfile(mixObject);
                    lastUpdatedProfiles.add(dataRow);
                    break;
                case NEWS:
                    dataRow = getIndividualNews(mixObject);
                    individualNews.add(dataRow);
                    break;
                default:
                    break;
            }

            dataRow.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID));
            dataRow.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME));
            dataRow.put(DBConstants.DatabaseColumns.COUNTRY_CODE, results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
            dataRow.put(DBConstants.DatabaseColumns.LOGO, results.getString(DBConstants.DatabaseColumns.LOGO));
        }


        individualSnapshotDTO = new IndividualSnapshotDTO();
        individualSnapshotDTO.setDesignationUpdates(designationUpdates);
        individualSnapshotDTO.setIndividualNews(individualNews);
        individualSnapshotDTO.setOwnershipUpdates(ownershipUpdates);
        individualSnapshotDTO.setInsiderTransactions(insiderTransactions);
        individualSnapshotDTO.setLastUpdatedProfiles(lastUpdatedProfiles);

        return individualSnapshotDTO;
    }

    public static void setCurrencyMasterData(CurrencyMasterDataDTO currency, ResultSet results, List<String> supportedLanguages) throws SQLException {
        currency.setCurrencyCode(results.getString(DatabaseColumns.CURRENCY_CODE));

        if (supportedLanguages != null) {
            setCurrencyLangDTOMap(currency, results, supportedLanguages);
        }
    }

    private static void setCurrencyLangDTOMap(CurrencyMasterDataDTO currency, ResultSet results, List<String> supportedLang) throws SQLException {
        Map<String, CurrencyLangDTO> currencyLangDTOMap = new HashMap<String, CurrencyLangDTO>();

        for (String lang : supportedLang) {
            lang = lang.toUpperCase();
            CurrencyLangDTO currencyLangDTO = new CurrencyLangDTO();
            currencyLangDTO.setDescription(results.getString(DatabaseColumns.CURRENCY_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + lang));
            currencyLangDTOMap.put(lang, currencyLangDTO);
        }

        currency.setCurrencyLangDTOMap(currencyLangDTOMap);
    }

    /**
     * Get insider individuals
     *
     * @param mixObject mix object
     * @return map Map<String, String>
     */
    private static Map<String, String> getIndividualInsiders(Map<String, String> mixObject) {
        Map<String, String> insiders = new HashMap<String, String>();
        insiders.put(DBConstants.DatabaseColumns.IND_INSIDER_ID, mixObject.get(DBConstants.DatabaseColumns.IND_INSIDER_ID));
        insiders.put(DBConstants.DatabaseColumns.IND_INSIDER_TYPE, mixObject.get(DBConstants.DatabaseColumns.IND_INSIDER_TYPE));
        insiders.put(DBConstants.DatabaseColumns.IND_COMPANY_ID, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_ID));
        insiders.put(DBConstants.DatabaseColumns.IND_COMPANY_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_NAME));
        insiders.put(DBConstants.DatabaseColumns.IND_SOURCE_ID, mixObject.get(DBConstants.DatabaseColumns.IND_SOURCE_ID));
        insiders.put(DBConstants.DatabaseColumns.IND_TICKER_ID, mixObject.get(DBConstants.DatabaseColumns.IND_TICKER_ID));
        insiders.put(DBConstants.DatabaseColumns.IND_COMPANY_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_NAME));
        insiders.put(DBConstants.DatabaseColumns.QUANTITY, mixObject.get(DBConstants.DatabaseColumns.QUANTITY));

        return insiders;
    }

    /**
     * Get individual ownership updates
     *
     * @param mixObject mix object
     * @return map Map<String, String> *
     */
    private static Map<String, String> getIndividualOwnership(Map<String, String> mixObject) {
        Map<String, String> owners = new HashMap<String, String>();
        owners.put(DBConstants.DatabaseColumns.IND_OWNERSHIP_PER, mixObject.get(DBConstants.DatabaseColumns.IND_OWNERSHIP_PER));
        owners.put(DBConstants.DatabaseColumns.PRV_IND_OWNERSHIP_PER, mixObject.get(DBConstants.DatabaseColumns.PRV_IND_OWNERSHIP_PER));
        owners.put(DBConstants.DatabaseColumns.IND_COMPANY_ID, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_ID));
        owners.put(DBConstants.DatabaseColumns.IND_COMPANY_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_NAME));
        return owners;
    }

    /**
     * Get individuals designation updates
     *
     * @param mixObject mix object
     * @return map Map<String, String>*
     */
    private static Map<String, String> getIndividualDesignations(Map<String, String> mixObject) {
        Map<String, String> designations = new HashMap<String, String>();
        designations.put(DBConstants.DatabaseColumns.IND_COUNTRY_CODE, mixObject.get(DBConstants.DatabaseColumns.IND_COUNTRY_CODE));
        designations.put(DBConstants.DatabaseColumns.IND_DESIGNATION, mixObject.get(DBConstants.DatabaseColumns.IND_DESIGNATION));
        designations.put(DBConstants.DatabaseColumns.IND_COMPANY_ID, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_ID));
        designations.put(DBConstants.DatabaseColumns.IND_COMPANY_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_NAME));
        return designations;
    }

    /**
     * Get individuals profile updates
     *
     * @param mixObject mix object
     * @return map Map<String, String>*
     */
    private static Map<String, String> getIndividualProfile(Map<String, String> mixObject) {
        Map<String, String> profile = new HashMap<String, String>();
        profile.put(DBConstants.DatabaseColumns.IND_RESIDENCE, mixObject.get(DBConstants.DatabaseColumns.IND_RESIDENCE));
        profile.put(DBConstants.DatabaseColumns.IND_FULL_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_FULL_NAME));
        profile.put(DBConstants.DatabaseColumns.IND_WEBSITE, mixObject.get(DBConstants.DatabaseColumns.IND_WEBSITE));
        profile.put(DBConstants.DatabaseColumns.IND_COMPANY_ID, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_ID));
        profile.put(DBConstants.DatabaseColumns.IND_COMPANY_NAME, mixObject.get(DBConstants.DatabaseColumns.IND_COMPANY_NAME));
        return profile;
    }

    /**
     * Get individuals news updates
     *
     * @param mixObject mix object
     * @return map Map<String, String> *
     */
    private static Map<String, String> getIndividualNews(Map<String, String> mixObject) {
        Map<String, String> news = new HashMap<String, String>();
        news.put(DBConstants.DatabaseColumns.IND_NEWS_HEAD, mixObject.get(DBConstants.DatabaseColumns.IND_NEWS_HEAD));
        news.put(DBConstants.DatabaseColumns.IND_NEWS_ID, mixObject.get(DBConstants.DatabaseColumns.IND_NEWS_ID));
        return news;
    }

    /**
     * Create CountryTickerSnapshot data from RDBMS result set
     *
     * @param results result set
     * @return CountryTickerSnapshtoDTO
     */
    public static CountryTickerSnapshotDTO getCountryTickerSnapshot(ResultSet results) throws SQLException {
        CountryTickerSnapshotDTO countrySnapDTO = new CountryTickerSnapshotDTO();

        countrySnapDTO.setTickerSerial(results.getLong(DBConstants.DatabaseColumns.COUNTRY_TICKER_SERIAL));
        countrySnapDTO.setTickerId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_ID));
        countrySnapDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_COUNTRY_CODE));
        countrySnapDTO.setCurrencyId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_CURRENCY_ID));
        countrySnapDTO.setDecimalPlaces(results.getInt(DBConstants.DatabaseColumns.COUNTRY_TICKER_DECIMAL_PLACES));
        countrySnapDTO.setDisplayTicker(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_DISPLAY_TICKER));
        countrySnapDTO.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.COUNTRY_TICKER_LAST_UPDATED_TIME));
        countrySnapDTO.setSourceId(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_SOURCE_ID));
        countrySnapDTO.setTickerShortDesLn(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_SHORT_DES_LN));
        // removed UNIT and TICKER_LONG_DES_LN columns since those are not in use and no columns found in oracle DB
        countrySnapDTO.setInstrumentTypeId(results.getInt(DBConstants.DatabaseColumns.COUNTRY_TICKER_INSTRUMENT_TYPE_ID));
        countrySnapDTO.setStatus(results.getString(DBConstants.DatabaseColumns.COUNTRY_TICKER_STATUS));

        return countrySnapDTO;
    }

    /**
     * Get Map from MIX DAT and HED objects. U
     *
     * @param header header list H1|h2|H3
     * @param data   data list D1|D2|D3
     * @return map Map<String, String>*
     */
    private static Map<String, String> getMIXObject(String header, String data) {
        Map<String, String> dataMap = new HashMap<String, String>();
        String[] headerList = header.split("\\|");
        List<String> dataList = new ArrayList<String>(Arrays.asList(data.split("\\|")));

        while (dataList.size() < headerList.length) {
            dataList.add("");
        }

        for (int headerIndex = 0; headerIndex < headerList.length; headerIndex++) {
            dataMap.put(headerList[headerIndex], dataList.get(headerIndex));
        }
        return dataMap;
    }

    //region DCMS

    //----------------------------------DCMS Region Start---------------------------------------

    /**
     * Method to set DOC FILE data and return object
     *
     * @param results ResultSet
     * @return docFileDTO object
     * @throws SQLException
     */
    public static DocFileDTO setDocFileData(ResultSet results, List<String> supportedLang) throws SQLException {

        DocFileDTO docFileDTO = new DocFileDTO();

        docFileDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docFileDTO.setCategoryId(results.getInt(DBConstants.DocFileColumns.CATEGORY_ID));
        docFileDTO.setSubCategoryId(results.getInt(DBConstants.DocFileColumns.SUB_CATEGORY_ID));
        docFileDTO.setReportDate(results.getDate(DBConstants.DocFileColumns.REPORT_DATE));
        docFileDTO.setProvider(results.getString(DBConstants.DocFileColumns.PROVIDER));
        docFileDTO.setPublisher(results.getInt(DBConstants.DocFileColumns.PUBLISHER));
        docFileDTO.setSymbols(results.getString(DBConstants.DocFileColumns.SYMBOLS));
        docFileDTO.setExchanges(results.getString(DocFileColumns.EXCHANGES));
        docFileDTO.setCountryCodes(results.getString(DocFileColumns.COUNTRY_CODES));
        docFileDTO.setDefaultFileLang(results.getString(DBConstants.DocFileColumns.DEFAULT_FILE_LANG));
        docFileDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docFileDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docFileDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));
        docFileDTO.setIndustryCodes(results.getString(DBConstants.DocFileColumns.INDUSTRY_CODES));

        setDocFileLangDTOMap(docFileDTO, results, supportedLang);

        return docFileDTO;
    }

    public static DocCompaniesDTO setDocCompanyData(ResultSet results) throws SQLException {

        DocCompaniesDTO docCompaniesDTO = new DocCompaniesDTO();

        docCompaniesDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docCompaniesDTO.setCompanyId(results.getInt(DBConstants.DocFileColumns.COMPANY_ID));
        docCompaniesDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docCompaniesDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docCompaniesDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docCompaniesDTO;
    }

    public static DocCountriesDTO setDocCountryData(ResultSet results) throws SQLException {

        DocCountriesDTO docCountriesDTO = new DocCountriesDTO();

        docCountriesDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docCountriesDTO.setCountryCode(results.getString(DBConstants.DocFileColumns.COUNTRY_CODE));
        docCountriesDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docCountriesDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docCountriesDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docCountriesDTO;
    }

    public static DocExchangesDTO setDocExchangeData(ResultSet results) throws SQLException {

        DocExchangesDTO docExchangesDTO = new DocExchangesDTO();

        docExchangesDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docExchangesDTO.setSourceId(results.getString(DBConstants.DocFileColumns.SOURCE_ID));
        docExchangesDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docExchangesDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docExchangesDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docExchangesDTO;
    }

    public static DocIndustriesDTO setDocIndustryData(ResultSet results) throws SQLException {

        DocIndustriesDTO docIndustriesDTO = new DocIndustriesDTO();

        docIndustriesDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docIndustriesDTO.setClassificationSerial(results.getInt(DocFileColumns.CLASSIFICATION_SERIAL));
        docIndustriesDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docIndustriesDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docIndustriesDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));
        docIndustriesDTO.setClassificationCode(results.getString(DBConstants.DocFileColumns.CLASSIFICATION_CODE));

        return docIndustriesDTO;
    }

    public static DocPeriodsDTO setDocPeriodData(ResultSet results) throws SQLException {

        DocPeriodsDTO docPeriodsDTO = new DocPeriodsDTO();

        docPeriodsDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docPeriodsDTO.setPeriodId(results.getInt(DBConstants.DocFileColumns.PERIOD_ID));
        docPeriodsDTO.setYear(results.getInt(DBConstants.DocFileColumns.YEAR));
        docPeriodsDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docPeriodsDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docPeriodsDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docPeriodsDTO;
    }

    public static DocRegionsDTO setDocRegionData(ResultSet results) throws SQLException {

        DocRegionsDTO docRegionsDTO = new DocRegionsDTO();

        docRegionsDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docRegionsDTO.setRegionId(results.getInt(DBConstants.DocFileColumns.REGION_ID));
        docRegionsDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docRegionsDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docRegionsDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docRegionsDTO;
    }

    public static DocSectorsDTO setDocSectorData(ResultSet results) throws SQLException {

        DocSectorsDTO docSectorsDTO = new DocSectorsDTO();

        docSectorsDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docSectorsDTO.setGroupSerial(results.getInt(DBConstants.DocFileColumns.GROUP_SERIAL));
        docSectorsDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docSectorsDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docSectorsDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docSectorsDTO;
    }

    public static DocSymbolsDTO setDocSymbolData(ResultSet results) throws SQLException {

        DocSymbolsDTO docSymbolsDTO = new DocSymbolsDTO();

        docSymbolsDTO.setFileId(results.getInt(DBConstants.DocFileColumns.FILE_ID));
        docSymbolsDTO.setTickerSerial(results.getLong(DBConstants.DocFileColumns.TICKER_SERIAL));
        docSymbolsDTO.setStatus(results.getInt(DBConstants.DocFileColumns.STATUS));
        docSymbolsDTO.setLastUpdatedTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_UPDATED_TIME));
        docSymbolsDTO.setLastSyncTime(results.getTimestamp(DBConstants.DocFileColumns.LAST_SYNC_TIME));

        return docSymbolsDTO;
    }

    private static void setDocFileLangDTOMap(DocFileDTO docFileDTO, ResultSet results, List<String> supportedLang) throws SQLException {

        Map<String, DocFileLangDTO> docFileLangDTOMap = new HashMap<String, DocFileLangDTO>();

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                lang = lang.toUpperCase();
                DocFileLangDTO docFileLangDTO = new DocFileLangDTO();
                docFileLangDTO.setDisplayName(results.getString(DBConstants.DocFileColumns.DISPLAY_NAME + IConstants.Delimiter.UNDERSCORE + lang));
                docFileLangDTO.setSummary(results.getString(DBConstants.DocFileColumns.SUMMARY + IConstants.Delimiter.UNDERSCORE + lang));
                docFileLangDTO.setFileGuid(results.getString(DBConstants.DocFileColumns.FILE_GUID + IConstants.Delimiter.UNDERSCORE + lang));
                docFileLangDTO.setIndustryCodesDesc(results.getString(DBConstants.DocFileColumns.INDUSTRY_CODES_DESC + IConstants.Delimiter.UNDERSCORE + lang));
                docFileLangDTOMap.put(lang, docFileLangDTO);
            }
        }
        docFileDTO.setDocFileLangDTOMap(docFileLangDTOMap);
    }

    //----------------------------------DCMS Region End---------------------------------------

    //endregion DCMS

    public static Map<String, String> setReportObject(ResultSet results, Map<String, Integer> columnsMap) throws SQLException {

        Map<String, String> reportObj = new HashMap<String, String>();

        reportObj.put(IConstants.MIXDataField.DOC_ID, results.getString(IConstants.MIXDataField.DOC_ID));
        reportObj.put(IConstants.MIXDataField.LANGUAGE_CODE, results.getString(IConstants.MIXDataField.LANGUAGE_CODE));
        reportObj.put(IConstants.MIXDataField.PROVIDER, results.getString(IConstants.MIXDataField.PROVIDER));
        reportObj.put(IConstants.MIXDataField.DOC_TITLE, results.getString(IConstants.MIXDataField.DOC_TITLE));
        reportObj.put(IConstants.MIXDataField.CT_ID, results.getString(IConstants.MIXDataField.CT_ID));
        reportObj.put(IConstants.MIXDataField.SCT_ID, results.getString(IConstants.MIXDataField.SCT_ID));
        reportObj.put(IConstants.MIXDataField.REPORT_DATE, getDateMixDatePattern(results.getString(IConstants.MIXDataField.REPORT_DATE)));
        reportObj.put(IConstants.MIXDataField.PUBLISHER, results.getString(IConstants.MIXDataField.PUBLISHER));
        reportObj.put(IConstants.MIXDataField.FILE_GUID, results.getString(IConstants.MIXDataField.FILE_GUID));
        reportObj.put(IConstants.MIXDataField.TICKER_SERIAL_LIST, results.getString(IConstants.MIXDataField.TICKER_SERIAL_LIST));
        reportObj.put(IConstants.MIXDataField.EXCHANGES_LIST, results.getString(IConstants.MIXDataField.EXCHANGES_LIST));
        reportObj.put(IConstants.MIXDataField.CTRY_CODE_LIST, results.getString(IConstants.MIXDataField.CTRY_CODE_LIST));
        reportObj.put(IConstants.MIXDataField.INDUSTRY, results.getString(IConstants.MIXDataField.INDUSTRY));

        if (columnsMap.get(IConstants.MIXDataField.YEAR) != null) {
            reportObj.put(IConstants.MIXDataField.YEAR, results.getString(IConstants.MIXDataField.YEAR));
        }
        if (columnsMap.get(IConstants.MIXDataField.PERIOD_ID) != null) {
            reportObj.put(IConstants.MIXDataField.PERIOD_ID, results.getString(IConstants.MIXDataField.PERIOD_ID));
        }

        return reportObj;
    }

    /**
     * Method to get existing map of columns with their indices from given result set
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static Map<String, Integer> getAvailableColumns(ResultSet resultSet) throws SQLException {
        Map<String, Integer> columnsMap = new HashMap<String, Integer>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int x = 1; x <= columnCount; x++) {
            columnsMap.put(rsmd.getColumnName(x), x);
        }
        return columnsMap;
    }

    private static String getDateMixDatePattern(String date) {
        return (date != null) ? date.replace("-", "") : null;
    }

    /**
     * Retrieve FundManagerDTO s from the Result set and add them to the given Map
     *
     * @param rs              result set
     * @param fundManagersMap company id vs fund managers dto
     */
    public static void getFundManagersToMap(ResultSet rs, Map<String, FundManagersDTO> fundManagersMap) throws SQLException {
        String companyIdList = rs.getString(DBConstants.DatabaseColumns.MANAGED_COMPANIES);
        if (StringUtils.isNotBlank(companyIdList)) {
            String[] companyIdArray = companyIdList.split(Character.toString(IConstants.Delimiter.COMMA));
            for (String companyId : companyIdArray) {
                if (StringUtils.isNotBlank(companyId)) {
                    if (fundManagersMap.containsKey(companyId)) {
                        //this fundManager is already created. Update status
                        FundManagersDTO existRecord = fundManagersMap.get(companyId);
                        updateStateOfFundManagerDTO(existRecord, companyId, rs);
                    } else {
                        //create the fundManager and add to map
                        FundManagersDTO newFundManager = updateStateOfFundManagerDTO(null, companyId, rs);
                        fundManagersMap.put(companyId, newFundManager);
                    }
                }

            }
        }
    }

    /**
     * Retrieve FundIssuersDTO s from the Result set and add them to the given Map
     *
     * @param rs             result set
     * @param fundIssuersMap company id vs fund issuer
     */
    public static void getFundIssuersToMap(ResultSet rs, Map<String, FundIssuersDTO> fundIssuersMap) throws SQLException {
        String companyIdList = rs.getString(DBConstants.DatabaseColumns.ISSUED_COMPANIES);
        if (StringUtils.isNotBlank(companyIdList)) {
            String[] companyIdArray = companyIdList.split(Character.toString(IConstants.Delimiter.COMMA));
            for (String companyId : companyIdArray) {
                if (StringUtils.isNotBlank(companyId)) {
                    if (fundIssuersMap.containsKey(companyId)) {
                        //this fundManager is already created. Update status
                        FundIssuersDTO existRecord = fundIssuersMap.get(companyId);
                        updateStateOfFundIssuerDTO(existRecord, companyId, rs);
                    } else {
                        //create the fundManager and add to map
                        FundIssuersDTO newFundIssuer = updateStateOfFundIssuerDTO(null, companyId, rs);
                        fundIssuersMap.put(companyId, newFundIssuer);
                    }
                }

            }
        }
    }

    /**
     * Retrieve FundTickerDTO s from the Result set and add them to fundTickerDTO object
     *
     * @param results result set
     */
    public static FundTickerDTO getFundManagerDetailsDTO(ResultSet results) throws SQLException {

        FundTickerDTO fundTickerDTO = new FundTickerDTO();

        fundTickerDTO.setCompanyId(results.getInt(DBConstants.DatabaseColumns.COMPANY_ID));
        fundTickerDTO.setChangePer1Y(results.getDouble(DBConstants.DatabaseColumns.CHG_PER_1Y));
        fundTickerDTO.setFundClass(results.getInt(DBConstants.DatabaseColumns.FUND_CLASS));
        fundTickerDTO.setTickerId(results.getString(DBConstants.DatabaseColumns.TICKER_ID));
        fundTickerDTO.setTickerShortDes(results.getString(DBConstants.DatabaseColumns.TICKER_SHORT_DES_LN));
        fundTickerDTO.setTickerLongDesLn(results.getString(DBConstants.DatabaseColumns.TICKER_LONG_DES_LN));
        if (results.getString(DatabaseColumns.TNAV_USD) != null && results.getDouble(DatabaseColumns.TNAV_USD) != -1) {
            fundTickerDTO.setTNavUSD(results.getDouble(DatabaseColumns.TNAV_USD));
        }
        if (results.getString(DatabaseColumns.TNAV) != null) {
            fundTickerDTO.setTNav(results.getDouble(DatabaseColumns.TNAV));
        }
        fundTickerDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
        fundTickerDTO.setFundIssuers(results.getString(DBConstants.DatabaseColumns.ISSUED_COMPANIES));
        fundTickerDTO.setChangePerYTD(results.getDouble(DBConstants.DatabaseColumns.CHG_PER_YTD));
        fundTickerDTO.setSourceId(results.getString(DBConstants.DatabaseColumns.SOURCE_ID));

        return fundTickerDTO;
    }

    public static FundTickerDTO getFundIssuerDetails(ResultSet results) throws SQLException {

        FundTickerDTO fundTickerDTO = new FundTickerDTO();

        fundTickerDTO.setChangePer1Y(results.getDouble(DBConstants.DatabaseColumns.CHG_PER_1Y));
        if (results.getString(DatabaseColumns.TNAV_USD) != null && results.getDouble(DatabaseColumns.TNAV_USD) != -1) {
            fundTickerDTO.setTNavUSD(results.getDouble(DatabaseColumns.TNAV_USD));
        }
        if (results.getString(DatabaseColumns.TNAV) != null) {
            fundTickerDTO.setTNav(results.getDouble(DatabaseColumns.TNAV));
        }
        fundTickerDTO.setSourceId(results.getString(DBConstants.DatabaseColumns.SOURCE_ID));
        fundTickerDTO.setTickerId(results.getString(DBConstants.DatabaseColumns.TICKER_ID));
        fundTickerDTO.setTickerLongDesLn(results.getString(DBConstants.DatabaseColumns.TICKER_LONG_DES_LN));
        fundTickerDTO.setTickerShortDes(results.getString(DBConstants.DatabaseColumns.TICKER_SHORT_DES_LN));
        fundTickerDTO.setFundClass(results.getInt(DBConstants.DatabaseColumns.FUND_CLASS));
        fundTickerDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
        fundTickerDTO.setManagedCompanies(results.getString(DBConstants.DatabaseColumns.MANAGED_COMPANIES));

        return fundTickerDTO;
    }

    public static FundTickerDTO getFundScreenerMetaData(ResultSet results) throws SQLException {

        FundTickerDTO fundTickerDTO = new FundTickerDTO();

        fundTickerDTO.setFundIssuers(results.getString(DBConstants.DatabaseColumns.ISSUED_COMPANIES));
        fundTickerDTO.setManagedCompanies(results.getString(DBConstants.DatabaseColumns.MANAGED_COMPANIES));

        return fundTickerDTO;
    }

    /**
     * Updated the statistics of the fundmanagersDTO. If fundmanagerDTO is null a new object will be initialized.
     *
     * @param existRecord fundmanagersDTO record to be update
     * @param companyId   company id of the fund manager
     * @param rs          result set
     * @return fund managers dto
     * @throws SQLException
     */
    private static FundManagersDTO updateStateOfFundManagerDTO(FundManagersDTO existRecord, String companyId, ResultSet rs) throws SQLException {

        String fundNameLan = rs.getString(DBConstants.DatabaseColumns.COMPANY_NAME_LAN);
        int fundId = rs.getInt(DBConstants.DatabaseColumns.IND_COMPANY_ID);
        Double percentageOf1Ychange = rs.getDouble(DBConstants.DatabaseColumns.CHG_PER_1Y);
        Double tnaUsdValue = null;
        if (rs.getString(DatabaseColumns.TNAV_USD) != null) {
            tnaUsdValue = rs.getDouble(DatabaseColumns.TNAV_USD);
        }
        Double tnaValue = null;
        if (rs.getString(DatabaseColumns.TNAV) != null) {
            tnaValue = rs.getDouble(DatabaseColumns.TNAV);
        }
        Double validatedTnaUsd = validateTnav(tnaUsdValue, tnaValue);
        String sourceId = rs.getString(DBConstants.DatabaseColumns.SOURCE_ID);
        String tickerId = rs.getString(DBConstants.DatabaseColumns.TICKER_ID);
        String fundLongDesc = rs.getString(DBConstants.DatabaseColumns.TICKER_LONG_DES_LN);
        String fundShortDesc = rs.getString(DBConstants.DatabaseColumns.TICKER_SHORT_DES_LN);

        TickerDTO fundCompany = new TickerDTO();
        fundCompany.setCompanyNameLan(fundNameLan);
        fundCompany.setTickerShortDes(fundShortDesc);
        fundCompany.setTickerLongDesLn(fundLongDesc);
        fundCompany.setSourceId(sourceId);
        fundCompany.setTickerId(tickerId);
        fundCompany.setCompanyId(fundId);

        if (existRecord != null) {
            //update the exist record. Note that validations are applied in the setter methods of DTO class
            existRecord.updateFundCount();
            existRecord.setMinChangePercentage(percentageOf1Ychange, fundCompany);
            existRecord.setMaxChangePercentage(percentageOf1Ychange, fundCompany);
            existRecord.setTotalTNA(validatedTnaUsd);
            return existRecord;
        } else {
            return new FundManagersDTO(Integer.parseInt(companyId), validatedTnaUsd, percentageOf1Ychange, fundCompany);
        }
    }

    /**
     * Updated the statistics of the fundissuersDTO. If fundissuersDTO is null a new object will be initialized.
     *
     * @param existRecord fundissuersDTO record to be update
     * @param companyId   company id of the fund manager
     * @param rs          result set
     * @return fund issuers dto
     * @throws SQLException
     */
    private static FundIssuersDTO updateStateOfFundIssuerDTO(FundIssuersDTO existRecord, String companyId, ResultSet rs) throws SQLException {

        FundIssuersDTO fundIssuersDTO;

        String fundNameLan = rs.getString(DBConstants.DatabaseColumns.COMPANY_NAME_LAN);
        int fundId = rs.getInt(DBConstants.DatabaseColumns.IND_COMPANY_ID);
        Double percentageOf1Ychange = rs.getDouble(DBConstants.DatabaseColumns.CHG_PER_1Y);
        Double tnaUsdValue = null;
        if (rs.getString(DatabaseColumns.TNAV_USD) != null) {
            tnaUsdValue = rs.getDouble(DatabaseColumns.TNAV_USD);
        }
        Double tnaValue = null;
        if (rs.getString(DatabaseColumns.TNAV) != null) {
            tnaValue = rs.getDouble(DatabaseColumns.TNAV);
        }
        Double validatedTnaUsd = validateTnav(tnaUsdValue, tnaValue);
        String sourceId = rs.getString(DBConstants.DatabaseColumns.SOURCE_ID);
        String tickerId = rs.getString(DBConstants.DatabaseColumns.TICKER_ID);
        int classification = rs.getInt(DBConstants.DatabaseColumns.FUND_CLASS);
        String fundLongDesc = rs.getString(DBConstants.DatabaseColumns.TICKER_LONG_DES_LN);
        String fundShortDesc = rs.getString(DBConstants.DatabaseColumns.TICKER_SHORT_DES_LN);

        TickerDTO fundCompany = new TickerDTO();
        fundCompany.setCompanyNameLan(fundNameLan);
        fundCompany.setTickerShortDes(fundShortDesc);
        fundCompany.setTickerLongDesLn(fundLongDesc);
        fundCompany.setSourceId(sourceId);
        fundCompany.setTickerId(tickerId);
        fundCompany.setCompanyId(fundId);

        if (existRecord != null) {
            //update the exist record. Note that validations are applied in the setter methods of DTO class
            existRecord.updateFundCount();
            existRecord.addClassificationTypes(classification);
            existRecord.setMinChangePercentage(percentageOf1Ychange, fundCompany);
            existRecord.setMaxChangePercentage(percentageOf1Ychange, fundCompany);
            existRecord.setTotalTNA(validatedTnaUsd);
            fundIssuersDTO = existRecord;
        } else {
            fundIssuersDTO = new FundIssuersDTO(Integer.parseInt(companyId), validatedTnaUsd, percentageOf1Ychange, fundCompany, classification);
        }

        return fundIssuersDTO;
    }

    /**
     * validate whether tnaUSD value is real or not.
     * if tnaUSD value is not correct this returns -1.0
     * otherwise return the correct tnaUSD value
     *
     * @param tnaUsdValue tna value in usd
     * @param tnaValue    tna value
     * @return tnaUsdValue
     */
    private static Double validateTnav(Double tnaUsdValue, Double tnaValue) {
        if (tnaUsdValue != null && tnaValue != null && ((tnaUsdValue >= 0 && tnaValue >= 0) || tnaUsdValue > 0)) {
            return tnaUsdValue;
        } else {
            return -1.0;
        }
    }

    /**
     * Generate exchanges string formatted String like 'AAA','BBB','CCC'
     *
     * @param supportedExgs Supported exchanges list
     * @return formatted string
     */
    public static String getFormattedExgStrings(List<String> supportedExgs) {
        StringBuilder formattedStrBuilder = new StringBuilder();
        String tempString = StringUtils.join(supportedExgs, IConstants.Delimiter.COMMA);
        formattedStrBuilder.append(IConstants.Delimiter.QUOTE);
        formattedStrBuilder.append(tempString.replace(Character.toString(IConstants.Delimiter.COMMA), IConstants.Delimiter.QUOTE_COMMA_QUOTE));
        formattedStrBuilder.append(IConstants.Delimiter.QUOTE);
        return formattedStrBuilder.toString();
    }

    /**
     * Set DB columns to DTO object properties
     *
     * @param rs result set
     * @return DataSyncStatusDTO dto Object
     * @throws SQLException sqlException
     */
    public static DataSyncStatusDTO getDataSyncStatusData(ResultSet rs) throws SQLException {
        DataSyncStatusDTO dataSyncStatusDTO = new DataSyncStatusDTO();

        dataSyncStatusDTO.setType(rs.getInt(DBConstants.DatabaseColumns.TYPE));
        dataSyncStatusDTO.setId(rs.getString(DBConstants.DatabaseColumns.ID));
        dataSyncStatusDTO.setParams(rs.getString(DBConstants.DatabaseColumns.PARAMS));
        dataSyncStatusDTO.setDescription(rs.getString(DBConstants.DatabaseColumns.DESCRIPTION));
        dataSyncStatusDTO.setLastUpdatedTime(rs.getTimestamp(DBConstants.DatabaseColumns.LAST_UPDATED_TIME));
        dataSyncStatusDTO.setLastId(rs.getString(DBConstants.DatabaseColumns.LAST_ID));

        return dataSyncStatusDTO;
    }

    public static String getTickerSnapshotTableName(boolean isDelayed) {
        String table = REAL_TIME_SNAPSHOT_TABLE;

        if (isDelayed) {
            table = DELAYED_SNAPSHOT_TABLE;
        }

        return table;
    }

    public static String getSourcesSnapshotTableName(boolean isDelayed) {
        String table = REAL_TIME_SOURCES_SNAPSHOT_TABLE;

        if (isDelayed) {
            table = DELAYED_SOURCES_SNAPSHOT_TABLE;
        }

        return table;
    }

    public static String getIndexSnapshotTableName(boolean isDelayed) {
        String table = REAL_TIME_INDEX_SNAPSHOT_TABLE;

        if (isDelayed) {
            table = DELAYED_INDEX_SNAPSHOT_TABLE;
        }

        return table;
    }

    //region FUND INVEST ALLOCATION

    public static FundInvestmentAllocationDTO getFundInvestmentAllocationDTO(ResultSet resultSet) throws SQLException {
        FundInvestmentAllocationDTO fundInvestmentAllocationDTO = new FundInvestmentAllocationDTO();
        fundInvestmentAllocationDTO.setFundTickerSerial(resultSet.getInt(DBConstants.DatabaseColumns.FUND_TICKER_SERIAL));
        fundInvestmentAllocationDTO.setTransactionSequence(resultSet.getInt(DBConstants.DatabaseColumns.TXN_SEQ));
        fundInvestmentAllocationDTO.setInvestmentType(resultSet.getInt(DBConstants.DatabaseColumns.INV_TYPE));
        fundInvestmentAllocationDTO.setAllocationId(resultSet.getInt(DBConstants.DatabaseColumns.ALLOCATION_ID));
        fundInvestmentAllocationDTO.setInvestmentTypeDesc(resultSet.getString(DBConstants.DatabaseColumns.INV_TYPE_DESCRIPTION));
        fundInvestmentAllocationDTO.setTransactionDate(resultSet.getDate(DBConstants.DatabaseColumns.TXN_DATE).toString());
        fundInvestmentAllocationDTO.setFundAssetId(resultSet.getInt(DBConstants.DatabaseColumns.INVESTED_ASSET_ID));
        fundInvestmentAllocationDTO.setFundAssetDesc(resultSet.getString(DBConstants.DatabaseColumns.INVESTED_ASSET_DESCRIPTION));
        fundInvestmentAllocationDTO.setInvestedTickerSerial(resultSet.getInt(DBConstants.DatabaseColumns.INVESTED_TICKER_SERIAL));
        fundInvestmentAllocationDTO.setInvestedTickerDesc(resultSet.getString(DBConstants.DatabaseColumns.INVESTED_TICKER_DESCRIPTION));
        fundInvestmentAllocationDTO.setInvestedCountryCode(resultSet.getString(DBConstants.DatabaseColumns.INVESTED_COUNTRY_CODE));
        fundInvestmentAllocationDTO.setInvestedCountryDesc(resultSet.getString(DBConstants.DatabaseColumns.INVESTED_COUNTRY_DESCRIPTION));
        fundInvestmentAllocationDTO.setInvestedSector(resultSet.getInt(DBConstants.DatabaseColumns.INVESTED_SECTOR));
        fundInvestmentAllocationDTO.setInvestedSectorDesc(resultSet.getString(DBConstants.DatabaseColumns.INVESTED_SECTOR_DESCRIPTION));
        fundInvestmentAllocationDTO.setInvestmentPercentage(resultSet.getDouble(DBConstants.DatabaseColumns.RATIO));
        fundInvestmentAllocationDTO.setFactSheetId(resultSet.getInt(DBConstants.DatabaseColumns.FILE_ID));
        fundInvestmentAllocationDTO.setFileGUIDs(resultSet.getString(DBConstants.DatabaseColumns.FILE_IDS));
        return fundInvestmentAllocationDTO;
    }

    /**
     * fill the fund investment allocation dto from DB
     *
     * @param resultSet resultset
     * @throws SQLException
     */
    public static FundInvestment getFundInvestmentAllocation(ResultSet resultSet) throws SQLException {
        FundInvestment fundInvestment = null;
        Map<Integer, Map<Integer, List<FundInvestmentAllocationDTO>>> dataMap = null;

        while (resultSet.next()) {
            if (fundInvestment == null) {
                dataMap = new LinkedHashMap<Integer, Map<Integer, List<FundInvestmentAllocationDTO>>>();
                fundInvestment = new FundInvestment();
            }
            int sequence = resultSet.getInt(DBConstants.DatabaseColumns.TXN_SEQ);
            int investmentType = resultSet.getInt(DBConstants.DatabaseColumns.INV_TYPE);

            if (dataMap.containsKey(sequence)) {
                if (!dataMap.get(sequence).containsKey(investmentType)) {
                    dataMap.get(sequence).put(investmentType, new ArrayList<FundInvestmentAllocationDTO>());
                }
            } else {
                dataMap.put(sequence, new LinkedHashMap<Integer, List<FundInvestmentAllocationDTO>>());
                dataMap.get(sequence).put(investmentType, new ArrayList<FundInvestmentAllocationDTO>());
            }

            dataMap.get(sequence).get(investmentType).add(getFundInvestmentAllocationDTO(resultSet));
        }
        if (fundInvestment != null) {
            fundInvestment.setData(dataMap);
        }
        return fundInvestment;
    }

    /**
     * get market investments
     *
     * @param resultSet results
     * @param language  language
     * @return MarketFundInvestmentsDTO
     * @throws SQLException
     */
    public static MarketFundInvestmentsDTO getFundInvestmentsByMarket(ResultSet resultSet, String language) throws SQLException {
        MarketFundInvestmentsDTO marketFundInvestmentsDTO = new MarketFundInvestmentsDTO();
        Map<String, String> stockInvestment;
        while (resultSet.next()) {
            stockInvestment = new HashMap<String, String>(15);
            stockInvestment.put(IConstants.MarketFundInvestments.ALLOCATION_ID, resultSet.getString(DatabaseColumns.ALLOCATION_ID));
            stockInvestment.put(IConstants.MarketFundInvestments.FUND_TICKER_SERIAL, resultSet.getString(DatabaseColumns.FUND_TICKER_SERIAL));
            stockInvestment.put(IConstants.MarketFundInvestments.FUND_NAME, resultSet.getString(LangSpecificDatabaseColumns.FUND_NAME + language));
            stockInvestment.put(IConstants.MarketFundInvestments.FUND_TICKER, resultSet.getString(DatabaseColumns.FUND_TICKER));
            stockInvestment.put(IConstants.MarketFundInvestments.FUND_SOURCE, resultSet.getString(DatabaseColumns.FUND_SOURCE));
            stockInvestment.put(IConstants.MarketFundInvestments.INVESTED_TICKER_SERIAL, resultSet.getString(DatabaseColumns.INVESTED_TICKER_SERIAL));
            stockInvestment.put(IConstants.MarketFundInvestments.TICKER_ID, resultSet.getString(DatabaseColumns.TICKER_ID));
            stockInvestment.put(IConstants.MarketFundInvestments.TICKER_DESCRIPTION, resultSet.getString(LangSpecificDatabaseColumns.TICKER_SHORT_DESCRIPTION + language));
            stockInvestment.put(IConstants.MarketFundInvestments.SOURCE_ID, resultSet.getString(DatabaseColumns.SOURCE_ID));
            stockInvestment.put(IConstants.MarketFundInvestments.SECTOR_ID, resultSet.getString(DatabaseColumns.SECTOR_ID));
            stockInvestment.put(IConstants.MarketFundInvestments.SECTOR_NAME, resultSet.getString(LangSpecificDatabaseColumns.SECTOR_NAME + language));
            stockInvestment.put(IConstants.MarketFundInvestments.GICS_L3_CODE, resultSet.getString(DatabaseColumns.GICSL4_CODE));
            stockInvestment.put(IConstants.MarketFundInvestments.GICS_L3, resultSet.getString(LangSpecificDatabaseColumns.GICS_L3_DESC + language));
            stockInvestment.put(IConstants.MarketFundInvestments.COUNTRY_CODE, resultSet.getString(DatabaseColumns.TICKER_COUNTRY_CODE)); //fund country
            stockInvestment.put(IConstants.MarketFundInvestments.COUNTRY_DESC, resultSet.getString(LangSpecificDatabaseColumns.TICKER_COUNTRY_DESC + language));   //fund country desc
            stockInvestment.put(IConstants.MarketFundInvestments.NUMBER_OF_SHARES, resultSet.getString(DatabaseColumns.NUMBER_OF_SHARES));
            stockInvestment.put(IConstants.MarketFundInvestments.TXN_DATE, resultSet.getString(DatabaseColumns.TXN_DATE));
            stockInvestment.put(IConstants.MarketFundInvestments.FILE_ID, resultSet.getString(DatabaseColumns.FILE_ID));
            stockInvestment.put(IConstants.MarketFundInvestments.VWAP, resultSet.getString(DatabaseColumns.VWAP));
            stockInvestment.put(IConstants.MarketFundInvestments.CURRENCY_RATE, resultSet.getString(DatabaseColumns.CURRENCY_RATE));
            marketFundInvestmentsDTO.addStockInvestment(stockInvestment, language);
        }
        return marketFundInvestmentsDTO;
    }

    //endregion

    /**
     * get user session details list
     *
     * @param resultSet resultSet
     * @return session details
     * @throws SQLException
     */
    public static List<Map<String, Object>> setUserSessionDetails(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> userSessionDetailsList = new ArrayList<Map<String, Object>>(10);
        Map<String, Object> userSessionDetails;
        while (resultSet.next()) {
            userSessionDetails = new HashMap<String, Object>(10);

            userSessionDetails.put(IConstants.UserSessionDetails.USERNAME, resultSet.getString(DatabaseColumns.USERNAME));
            userSessionDetails.put(IConstants.UserSessionDetails.FIRST_NAME, resultSet.getString(DatabaseColumns.FIRST_NAME));
            userSessionDetails.put(IConstants.UserSessionDetails.LAST_NAME, resultSet.getString(DatabaseColumns.LAST_NAME));
            userSessionDetails.put(IConstants.UserSessionDetails.COMPANY, resultSet.getString(DatabaseColumns.COMPANY));
            userSessionDetails.put(IConstants.UserSessionDetails.COUNTRY, resultSet.getString(DatabaseColumns.COUNTRY));
            userSessionDetails.put(IConstants.UserSessionDetails.MOB, resultSet.getString(DatabaseColumns.PHONE_NO));
            userSessionDetails.put(IConstants.UserSessionDetails.TEL, resultSet.getString(DatabaseColumns.WORK_TEL));
            userSessionDetails.put(IConstants.UserSessionDetails.EMAIL, resultSet.getString(DatabaseColumns.EMAIL));
            userSessionDetails.put(IConstants.UserSessionDetails.ACCOUNT_MANAGER, resultSet.getString(DatabaseColumns.SALES_REP_NAME));
            userSessionDetails.put(IConstants.UserSessionDetails.START_DATE, resultSet.getDate(DatabaseColumns.START_DATE));
            userSessionDetails.put(IConstants.UserSessionDetails.EXPIRY_DATE, resultSet.getDate(DatabaseColumns.EXPIRY_DATE));
            userSessionDetails.put(IConstants.UserSessionDetails.LOGIN_DATE, resultSet.getDate(DatabaseColumns.LOGIN_DATE));
            userSessionDetails.put(IConstants.UserSessionDetails.LOGOUT_DATE, resultSet.getDate(DatabaseColumns.LOGOUT_DATE));
            userSessionDetailsList.add(userSessionDetails);
        }

        return userSessionDetailsList;
    }

    public static Map<Long, MiniUserDetails> setMiniUserDetails(ResultSet resultSet) throws SQLException {
        Map<Long, MiniUserDetails> userDetailsList = new HashMap<Long, MiniUserDetails>();
        while (resultSet.next()) {

            Long userId = resultSet.getLong(DatabaseColumns.USER_ID);

            MiniUserDetails miniUserDetails = new MiniUserDetails(userId);
            miniUserDetails.setAccountExpiryDate(resultSet.getDate(DatabaseColumns.ACCOUNT_EXPIRE_DATE));
            miniUserDetails.setAccountStatus(resultSet.getInt(DatabaseColumns.ACCOUNT_STATUS));
            miniUserDetails.setAccountType(resultSet.getInt(DatabaseColumns.ACCOUNT_TYPE));
            miniUserDetails.setEmail(resultSet.getString(DatabaseColumns.EMAIL));
            miniUserDetails.setEmailReleaseNotes(resultSet.getInt(DatabaseColumns.EMAIL_RELEASE_NOTES));
            miniUserDetails.setFirstName(resultSet.getString(DatabaseColumns.FIRST_NAME));
            miniUserDetails.setLastName(resultSet.getString(DatabaseColumns.LAST_NAME));
            miniUserDetails.setTitle(resultSet.getString(DatabaseColumns.TITLE));

            userDetailsList.put(userId, miniUserDetails);
        }

        return userDetailsList;
    }

    /**
     * get campaign active count
     *
     * @param resultSet resultSet
     * @return session details
     * @throws SQLException
     */
    public static Map<Integer, Integer> setCampaignActiveCount(ResultSet resultSet) throws SQLException {
        Map<Integer, Integer> activeCountMap = new HashMap<Integer, Integer>();
        while (resultSet.next()) {
            activeCountMap.put(resultSet.getInt(DatabaseColumns.CAMPAIGN_ID), resultSet.getInt(DatabaseColumns.ACTIVE_COUNT));
        }
        return activeCountMap;
    }

    /**
     * set Email Summary Data
     *
     * @param resultSet ResultSet
     * @return email type vs status vs count
     * @throws SQLException
     */
    public static Map<String, Map<String, String>> setEmailSummaryData(ResultSet resultSet) throws SQLException {
        Map<String, Map<String, String>> emailSummaryData = new HashMap<String, Map<String, String>>();
        String emailType;

        while (resultSet.next()) {
            Map<String, String> statusCountMap;
            emailType = resultSet.getString(DatabaseColumns.EMAIL_TYPE);

            if (emailType != null) {
                if (emailSummaryData.containsKey(emailType)) {
                    statusCountMap = emailSummaryData.get(emailType);
                } else {
                    statusCountMap = new HashMap<String, String>();
                }

                statusCountMap.put(resultSet.getString(DatabaseColumns.STATUS), resultSet.getString(DatabaseColumns.EMAIL_COUNT));
                emailSummaryData.put(emailType, statusCountMap);
            }
        }

        return emailSummaryData;
    }

    //region INVESTOR TYPES

    /**
     * Method to set KPI data and return object
     *
     * @param results result set
     * @return KpiDTO object
     * @throws SQLException
     */
    public static InvestorTypeValueDTO setInvestorTypeValuesData(ResultSet results) throws SQLException {

        InvestorTypeValueDTO investorTypeValueDTO = new InvestorTypeValueDTO();

        investorTypeValueDTO.setId(results.getInt(DBConstants.DatabaseColumns.INVESTOR_TYPE_VALUE_ID));
        investorTypeValueDTO.setNationalityId(results.getInt(DBConstants.DatabaseColumns.NATIONALITY_ID));
        investorTypeValueDTO.setInvestorTypeId(results.getInt(DBConstants.DatabaseColumns.INVESTOR_TYPE_ID));
        investorTypeValueDTO.setInvestmentDate(results.getDate(DBConstants.DatabaseColumns.INVESTMENT_DATE));
        investorTypeValueDTO.setReferenceDocId(results.getInt(DBConstants.DatabaseColumns.REF_DOC_ID));
        investorTypeValueDTO.setBuyValue(results.getDouble(DBConstants.DatabaseColumns.BUY_VALUE));
        investorTypeValueDTO.setSellValue(results.getDouble(DBConstants.DatabaseColumns.SELL_VALUE));
        investorTypeValueDTO.setBuyPercentage(results.getDouble(DBConstants.DatabaseColumns.BUY_PERCENTAGE));
        investorTypeValueDTO.setSellPercentage(results.getDouble(DBConstants.DatabaseColumns.SELL_PERCENTAGE));
        investorTypeValueDTO.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.LAST_UPDATED_TIME));
        investorTypeValueDTO.setLastSyncTime(results.getTimestamp(DatabaseColumns.LAST_SYNC_TIME));
        investorTypeValueDTO.setStatus(results.getInt(DBConstants.DatabaseColumns.STATUS));

        return investorTypeValueDTO;
    }

    //endregion

    //region utils

    /**
     * add imdb pagination filter
     *
     * @param beginIndex the beginning index, exclusive.
     * @param pageSize   the pageSize
     * @return sql
     */
    public static String getImdbPaginationQuery(String query, int beginIndex, int pageSize, int databaseType) throws IllegalArgumentException{
        if (beginIndex < 0) {
            throw new IllegalArgumentException("Begin index out of range : " + beginIndex);
        }

        String paginationQuery;
        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                if (beginIndex == 0) {
                    paginationQuery = MessageFormat.format(DERBY_PAGINATION_QUERY, query, pageSize);
                } else {
                    paginationQuery = MessageFormat.format(DERBY_PAGINATION_OFFSET_QUERY, query, beginIndex, pageSize);
                }
                break;
            case DatabaseTypes.DB_TYPE_H2:
                paginationQuery = MessageFormat.format(H2_PAGINATION_QUERY, query, pageSize, beginIndex);
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                if (beginIndex == 0) {
                    paginationQuery = MessageFormat.format(ORACLE_PAGINATION_QUERY, query, pageSize);
                } else {
                    paginationQuery = MessageFormat.format(ORACLE_PAGINATION_OFFSET_QUERY, query, beginIndex, pageSize);
                }
                break;
            default:
                throw new IllegalArgumentException(" DB type not supported : " + databaseType);
        }

        return paginationQuery;
    }

    /**
     * add imdb pagination filter
     *
     * @param beginIndex the beginning index, exclusive.
     * @param pageSize   the pageSize
     * @return sql
     */
    public static String getImdbPaginationQuery(int beginIndex, int pageSize, int databaseType) {

        if (beginIndex < 0) {
            throw new RuntimeException("Begin index out of range : " + beginIndex);
        }

        String query;

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                if (beginIndex == 0) {
                    query = MessageFormat.format(DERBY_PAGINATION, pageSize);
                } else {
                    query = MessageFormat.format(DERBY_PAGINATION_OFFSET, beginIndex, pageSize);
                }
                break;
            case DatabaseTypes.DB_TYPE_H2:
                query = MessageFormat.format(H2_PAGINATION, pageSize, beginIndex);
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                if (beginIndex == 0) {
                    query = MessageFormat.format(DERBY_PAGINATION, pageSize);
                } else {
                    query = MessageFormat.format(DERBY_PAGINATION_OFFSET, beginIndex, pageSize);
                }
                query = "select * from ( select rownum rnum, a.* from (your_query) where rownum <= :M ) where rnum >= :N";
                query = "select * from (your_query) where rownum <= 10";
                break;
            default:
                throw new RuntimeException(" IMDB type does not supported");
        }

        return query;


    }

    public static String getPaginationQuery(int databaseType, String query, int startIndex, int endIndex) {
        if (startIndex < 0) {
            throw new RuntimeException("Start index out of range : " + startIndex);
        }

        String paginationQuery;

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                if (startIndex == 0) {
                    paginationQuery = query + MessageFormat.format(DERBY_PAGINATION, endIndex);
                } else {
                    paginationQuery = query + MessageFormat.format(DERBY_PAGINATION_OFFSET, startIndex, endIndex - startIndex);
                }
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                if (startIndex == 0) {
                    paginationQuery = "SELECT * FROM (" + query + ") WHERE ROWNUM <= " + endIndex;
                } else {
                    paginationQuery = "SELECT * FROM (SELECT ROWNUM rnum, a.* FROM (" + query + ")a WHERE ROWNUM <= " + endIndex + " ) WHERE rnum > " + startIndex;
                }
                break;
            default:
                throw new RuntimeException(" DB type is not supported ");
        }
        return paginationQuery;
    }

    /**
     * add imdb pagination filter
     *
     * @param pageIndex the beginning index, exclusive.
     * @param pageSize   the pageSize
     * @return sql
     */
    public static String getPaginationQuery(String query, int pageIndex, int pageSize, boolean pagination, int databaseType) {
        if (pageIndex < 0) {
            throw new RuntimeException("Page index out of range : " + pageIndex);
        }

        String paginationQuery;

        int startIndex;
        if(pageIndex == 0 || !pagination){
            startIndex = 0;
        }else{
            startIndex = pageIndex * pageSize;
        }
        int endIndex = startIndex + pageSize;
        if(pagination){
            endIndex++;
        }

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                if (pageIndex == 0) {
                    paginationQuery = query + MessageFormat.format(DERBY_PAGINATION, endIndex);
                } else {
                    paginationQuery = query + MessageFormat.format(DERBY_PAGINATION_OFFSET, startIndex, endIndex - startIndex);
                }
                break;
            case DatabaseTypes.DB_TYPE_H2:
                paginationQuery = query + MessageFormat.format(H2_PAGINATION, endIndex - startIndex, startIndex);
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                if (pageIndex == 0) {
                    paginationQuery = "SELECT * FROM (" + query + ") WHERE ROWNUM <= " + endIndex;
                } else {
                    paginationQuery = "SELECT * FROM (SELECT ROWNUM rnum, a.* FROM (" + query + ") a) WHERE rnum BETWEEN " + (startIndex + 1) + " AND " + endIndex;
                }
                break;
            default:
                throw new RuntimeException(" DB type is not supported ");
        }
        return paginationQuery;
    }

    public static String getDateFunctionQuery(String dateString, String dateFormat, int databaseType) {

        StringBuilder queryBuilder = new StringBuilder();

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                queryBuilder.append(DERBY_DATE_FUNCTION).append(BRACKET_OPEN).append(QUOTE).append(dateString).append(QUOTE).append(BRACKET_CLOSE);
                break;
            case DatabaseTypes.DB_TYPE_H2:
                queryBuilder.append(H2_DATE_FUNCTION).append(BRACKET_OPEN).append(QUOTE).append(dateString).append(QUOTE).append(COMMA)
                        .append(QUOTE).append(dateFormat).append(QUOTE).append(BRACKET_CLOSE);
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                queryBuilder.append(ORACLE_DATE_FUNTION + DBConstants.SQL_BRACKET_OPEN + QUERY_QUOTE + dateString + QUERY_QUOTE + DBConstants.SQL_COMMA + QUERY_QUOTE + dateFormat + QUERY_QUOTE + DBConstants.SQL_BRACKET_CLOSE);
                break;
            default:
                throw new RuntimeException(" IMDB type does not supported");

        }

        return queryBuilder.toString();

    }

    public static String getTimeFunctionQuery(String dateString, String dateFormat, int databaseType) {

        StringBuilder queryBuilder = new StringBuilder();

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                queryBuilder.append(DERBY_TIMESTAMP_FUNCTION + DBConstants.SQL_BRACKET_OPEN + QUERY_QUOTE + dateString + QUERY_QUOTE + DBConstants.SQL_BRACKET_CLOSE);
                break;
            case DatabaseTypes.DB_TYPE_H2:
                break;
            case DatabaseTypes.DB_TYPE_ORACLE:
                queryBuilder.append(ORACLE_TIMESTAMP_FUNCTION + DBConstants.SQL_BRACKET_OPEN + QUERY_QUOTE + dateString + QUERY_QUOTE + DBConstants.SQL_COMMA + QUERY_QUOTE + dateFormat + QUERY_QUOTE + DBConstants.SQL_BRACKET_CLOSE);
                break;
            default:
                throw new RuntimeException(" IMDB type does not supported");

        }

        return queryBuilder.toString();

    }

    public static String getAddDateQuery(int databaseType, int dateType, int noOfDateOrMonth, String baseDate) {

        StringBuilder query = new StringBuilder();

        switch (databaseType) {
            case DatabaseTypes.DB_TYPE_DERBY:
                query.append(" DATE({fn TIMESTAMPADD(");
                switch (dateType) {
                    case DateTypes.DAY:
                        query.append("SQL_TSI_DAY,").append(noOfDateOrMonth).append(",").append(baseDate).append(")})");
                        break;
                    case DateTypes.MONTH:
                        query.append("SQL_TSI_MONTH,").append(noOfDateOrMonth).append(",").append(baseDate).append(")})");
                        break;
                }
                break;
            case DatabaseTypes.DB_TYPE_H2:
                query.append(" DATEADD(");
                switch (dateType) {
                    case DateTypes.DAY:
                        query.append("'DAY',").append(noOfDateOrMonth).append(",").append(baseDate).append(")");
                        break;
                    case DateTypes.MONTH:
                        query.append("'MONTH',").append(noOfDateOrMonth).append(",").append(baseDate).append(")");
                        break;
                }
                break;
            default:
                throw new RuntimeException(" IMDB type does not supported");

        }

        return query.toString();
    }

    /**
     * append in query statement for given queryBuilder
     *
     * @param queryBuilder         StringBuilder
     * @param column               String
     * @param commaSeparatedString String
     */
    public static boolean addInQueryPhrase(StringBuilder queryBuilder, String column, String commaSeparatedString, boolean isInteger, boolean addAnd) {
        return addInQueryPhrase(queryBuilder, column, commaSeparatedString, isInteger, addAnd, false);
    }

    /**
     * append custom query statement for given queryBuilder
     */
    public static boolean addNewsCustomQuery(StringBuilder queryBuilder, String sources, String hotNewsIndicater, String excludeSource, boolean addAnd) {
        return addCustomNewsQueryPhrase(queryBuilder, sources, hotNewsIndicater, excludeSource, addAnd);
    }


    public static boolean addCustomNewsQueryPhrase(StringBuilder queryBuilder, String sources, String hotNewsIndicater,
                                                   String excludeSource, boolean addAnd) {
        boolean added = false;

        String sourceIdWithoutHotNewsIndicater;
        String[] sourceList = sources.split(",");
        StringBuilder sourceIdList = new StringBuilder("");
        String prefix="";


        sourceIdWithoutHotNewsIndicater = IConstants.NewsSourceType.LIVE_MUBASHER_DOW_JONES_SOURCE_EXLUDED_HOT_NEWS_INDICATER.getDefaultValue();
        for (String aSourceList : sourceList) {
            if (aSourceList.equals(IConstants.NewsSourceType.LIVE_MUBASHER_DOW_JONES_SOURCE_EXLUDED_HOT_NEWS_INDICATER.getDefaultValue())) {

            } else {
                sourceIdList.append(prefix);
                prefix = ",";
                sourceIdList.append(aSourceList);
            }
        }
        queryBuilder.append(CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(CommonDatabaseParams.SQL_BRACKET_OPEN);
        queryBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID);
        queryBuilder.append(CommonDatabaseParams.QUERY_SPACE);
        queryBuilder.append(CommonDatabaseParams.QUERY_IN);
        queryBuilder.append(SQL_BRACKET_OPEN);
        queryBuilder.append(CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(sourceIdList.toString().replace(",","','"));
        queryBuilder.append(CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(SQL_BRACKET_CLOSE);
        queryBuilder.append(CommonDatabaseParams.QUERY_SPACE);

        queryBuilder.append(CommonDatabaseParams.QUERY_OR);
        queryBuilder.append(SQL_BRACKET_OPEN).append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID).append(CommonDatabaseParams.QUERY_EQUAL).append(CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(sourceIdWithoutHotNewsIndicater).append(CommonDatabaseParams.QUERY_QUOTE).append(CommonDatabaseParams.QUERY_SPACE);
        queryBuilder.append(CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(NewsDatabaseColumns.HOT_NEWS_INDICATOR);
        queryBuilder.append(CommonDatabaseParams.QUERY_NOT_EQUALS);
        queryBuilder.append(IConstants.NewsSourceType.LIVE_MUBASHER_DOW_JONES_EXLUDED_HOT_NEWS_INDICATER.getDefaultValue());
        queryBuilder.append(SQL_BRACKET_CLOSE);
        queryBuilder.append(SQL_BRACKET_CLOSE);
        added = true;

        return added;

    }
    /**
     * append in query statement for given queryBuilder
     *
     * @param queryBuilder         StringBuilder
     * @param column               String
     * @param commaSeparatedString String
     */
    public static boolean addInQueryPhrase(StringBuilder queryBuilder, String column, String commaSeparatedString,
                                           boolean isInteger, boolean addAnd, boolean toUpper) {
        boolean added = false;
        if (commaSeparatedString != null && !commaSeparatedString.isEmpty()) {
            if (isInteger) {
                commaSeparatedString = FormatUtils.removeConsecutiveCommas(commaSeparatedString);
            }
            if (addAnd) {
                queryBuilder.append(CommonDatabaseParams.QUERY_AND);
            }
            if (toUpper && !isInteger) {
                queryBuilder.append(CommonDatabaseParams.QUERY_UPPER).append(SQL_BRACKET_OPEN).append(column).append(SQL_BRACKET_CLOSE);
            } else {
                queryBuilder.append(column);
            }
            if (commaSeparatedString.indexOf(",") > 0) {
                queryBuilder.append(CommonDatabaseParams.QUERY_IN);
                queryBuilder.append(SQL_BRACKET_OPEN);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append((isInteger) ? commaSeparatedString :
                        ((toUpper) ? commaSeparatedString.toUpperCase().replace(",", "','") : commaSeparatedString.replace(",", "','")));
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append(SQL_BRACKET_CLOSE);
            } else {
                queryBuilder.append(CommonDatabaseParams.QUERY_EQUAL);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append((toUpper) ? commaSeparatedString.toUpperCase() : commaSeparatedString);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
            }

            added = true;
        }
        return added;
    }

    /**
     * append equal query statement for given queryBuilder
     *
     * @param queryBuilder StringBuilder
     * @param column       String
     * @param equalString  String
     */
    public static boolean addEqualQueryPhrase(StringBuilder queryBuilder, String column, String equalString, boolean isInteger, boolean addAnd) {
        boolean added = false;
        if (equalString != null && !equalString.isEmpty()) {
            if (addAnd) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(column);
            queryBuilder.append(CommonDatabaseParams.QUERY_EQUAL);
            queryBuilder.append((isInteger) ? "" : DBConstants.CommonDatabaseParams.QUERY_QUOTE);
            queryBuilder.append(equalString);
            queryBuilder.append((isInteger) ? "" : DBConstants.CommonDatabaseParams.QUERY_QUOTE);
            added = true;
        }
        return added;
    }

    /**
     * append in query statement for given queryBuilder
     *
     * @param queryBuilder         StringBuilder
     * @param column               String
     * @param commaSeparatedString String
     */
    public static boolean addNotInQueryPhrase(StringBuilder queryBuilder, String column, String commaSeparatedString,
                                           boolean isInteger, boolean addAnd, boolean toUpper) {
        boolean added = false;
        if (commaSeparatedString != null && !commaSeparatedString.isEmpty()) {
            if (isInteger) {
                commaSeparatedString = FormatUtils.removeConsecutiveCommas(commaSeparatedString);
            }
            if (addAnd) {
                queryBuilder.append(CommonDatabaseParams.QUERY_AND);
            }
            if (toUpper) {
                if (!isInteger) {
                    queryBuilder.append(CommonDatabaseParams.QUERY_UPPER).append(SQL_BRACKET_OPEN).append(column).append(SQL_BRACKET_CLOSE);
                }
            } else {
                queryBuilder.append(column);
            }
            if (commaSeparatedString.indexOf(",") > 0) {
                queryBuilder.append(CommonDatabaseParams.QUERY_NOT_IN);
                queryBuilder.append(SQL_BRACKET_OPEN);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append((isInteger) ? commaSeparatedString :
                        ((toUpper) ? commaSeparatedString.toUpperCase().replace(",", "','") : commaSeparatedString.replace(",", "','")));
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append(SQL_BRACKET_CLOSE);
            } else {
                queryBuilder.append(CommonDatabaseParams.QUERY_NOT_EQUALS);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append((toUpper) ? commaSeparatedString.toUpperCase() : commaSeparatedString);
                queryBuilder.append((isInteger) ? "" : CommonDatabaseParams.QUERY_QUOTE);
            }

            added = true;
        }
        return added;
    }

    /**
     * append equal query statement for given queryBuilder
     *
     * @param queryBuilder StringBuilder
     * @param column       String
     * @param equalString  String
     */
    public static boolean addNotEqualQueryPhrase(StringBuilder queryBuilder, String column, String equalString, boolean isInteger, boolean addAnd) {
        boolean added = false;
        if (equalString != null && !equalString.isEmpty()) {
            if (addAnd) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(column);
            queryBuilder.append(CommonDatabaseParams.QUERY_NOT_EQUALS);
            queryBuilder.append((isInteger) ? "" : DBConstants.CommonDatabaseParams.QUERY_QUOTE);
            queryBuilder.append(equalString);
            queryBuilder.append((isInteger) ? "" : DBConstants.CommonDatabaseParams.QUERY_QUOTE);
            added = true;
        }
        return added;
    }

    public static boolean addNotNullQueryPhrase(StringBuilder queryBuilder, String column, boolean addAnd) {
        if (addAnd) {
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        }
        queryBuilder.append(column);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);
        return true;
    }

    /**
     * append equal query statement for given queryBuilder
     *
     * @param queryBuilder StringBuilder
     * @param column       String
     * @param likeString   String
     */
    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, String likeString,
                                             boolean addComma, boolean startsWith, boolean endsWith, boolean addAnd) {
        return addLikeQueryPhrase(queryBuilder, column, likeString, addComma, startsWith, endsWith, false, addAnd);
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, String likeString,
                                             boolean addComma, boolean startsWith, boolean endsWith, boolean toUpper, boolean addAnd) {
        return addLikeQueryPhrase(queryBuilder, column, likeString, addComma, addComma, startsWith, endsWith, toUpper, addAnd);
    }

    /**
     * append equal query statement for given queryBuilder
     *
     * @param queryBuilder StringBuilder
     * @param column       String
     * @param likeString   String
     */
    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, String likeString,
                                             boolean addLeadingComma, boolean addTrailingComma, boolean startsWith, boolean endsWith, boolean toUpper, boolean addAnd) {
        boolean added = false;
        if (likeString != null && !likeString.isEmpty()) {
            if (addAnd) {
                queryBuilder.append(AND);
            }
            if (toUpper) {
                queryBuilder.append(CommonDatabaseParams.QUERY_UPPER).append(SQL_BRACKET_OPEN).append(column).append(SQL_BRACKET_CLOSE);
            } else {
                queryBuilder.append(column);
            }
            queryBuilder.append(CommonDatabaseParams.QUERY_LIKE).append(CommonDatabaseParams.QUERY_QUOTE);
            if (!startsWith) {
                queryBuilder.append(CommonDatabaseParams.QUERY_PREC);
            }
            if (addLeadingComma) {
                queryBuilder.append(COMMA);
            }
            queryBuilder.append(toUpper ? likeString.toUpperCase() : likeString);
            if (addTrailingComma) {
                queryBuilder.append(COMMA);
            }
            if (!endsWith) {
                queryBuilder.append(CommonDatabaseParams.QUERY_PREC);
            }
            queryBuilder.append(CommonDatabaseParams.QUERY_QUOTE);
            added = true;
        }
        return added;
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, List<String> likeString,
                                             boolean addComma, boolean startsWith, boolean endsWith, String joinPhrase) {
        return addLikeQueryPhrase(queryBuilder, column, likeString, addComma, addComma, startsWith, endsWith, joinPhrase);
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, List<String> likeString,
                                             boolean addLeadingComma, boolean addTrailingComma, boolean startsWith, boolean endsWith, String joinPhrase) {
        return addLikeQueryPhrase(queryBuilder, column, likeString, addLeadingComma, addTrailingComma, startsWith, endsWith, false, joinPhrase);
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, List<String> likeString,
                                             boolean addLeadingComma, boolean addTrailingComma, boolean startsWith, boolean endsWith,
                                             boolean isUpper, String joinPhrase) {
        return addLikeQueryPhrase(queryBuilder, column, likeString, addLeadingComma, addTrailingComma, startsWith, endsWith,
                isUpper, false, joinPhrase);
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, List<String> likeString,
                                             boolean addLeadingComma, boolean addTrailingComma, boolean startsWith, boolean endsWith,
                                             boolean isUpper, boolean addAnd, String joinPhrase) {
        boolean added = false;
        if (likeString != null && !likeString.isEmpty()) {
            if (addAnd) {
                queryBuilder.append(AND);
            }
            queryBuilder.append(CommonDatabaseParams.SQL_BRACKET_OPEN);
            for (int i = 0; i < likeString.size(); i++) {
                addLikeQueryPhrase(queryBuilder, column, likeString.get(i), addLeadingComma, addTrailingComma, startsWith, endsWith, isUpper, false);
                if (i + 1 < likeString.size()) {
                    queryBuilder.append(joinPhrase);
                }
            }
            queryBuilder.append(CommonDatabaseParams.SQL_BRACKET_CLOSE);
            added = true;
        }
        return added;
    }

    public static boolean addLikeQueryPhrase(StringBuilder queryBuilder, String column, List<String> queryParameterList,
                                             boolean addLeadingComma, boolean addTrailingComma, boolean startsWith,
                                             boolean endsWith, boolean isUpper, boolean addOr, boolean addBracket, String joinPhrase) {
        boolean added = false;
        if (queryParameterList != null && !queryParameterList.isEmpty()) {
            if (addOr) {
                queryBuilder.append(QUERY_OR);
            } else {
                queryBuilder.append(AND);
                queryBuilder.append(CommonDatabaseParams.SQL_BRACKET_OPEN);
            }

            added = addLikeQueryPhrase(queryBuilder, column, queryParameterList, addLeadingComma, addTrailingComma, startsWith,
                    endsWith, isUpper, false, joinPhrase);

            if (!addBracket) {
                queryBuilder.append(CommonDatabaseParams.SQL_BRACKET_CLOSE);
            }
        }
        return added;
    }

    public static boolean addBetweenQueryPhrase(StringBuilder queryBuilder, String column, double startValue, double endValue, boolean addAnd) {
        boolean added = false;
        if (startValue >= 0 || endValue >= 0) {
            NumberFormat formatter = new DecimalFormat("###.#####");
            if (addAnd) {
                queryBuilder.append(AND);
            }
            //(column between startValue and endValue) or (column > startValue) or (column < endValue)
            queryBuilder.append(SQL_BRACKET_OPEN).append(column);
            if (startValue >= 0) {
                if (endValue >= 0) {
                    queryBuilder.append(CommonDatabaseParams.QUERY_BETWEEN).append(formatter.format(startValue)).append(AND).append(formatter.format(endValue));
                } else {
                    queryBuilder.append(CommonDatabaseParams.QUERY_GREATER_THAN).append(formatter.format(startValue));
                }
            } else {
                queryBuilder.append(CommonDatabaseParams.QUERY_LESS_THAN).append(formatter.format(endValue));
            }
            queryBuilder.append(SQL_BRACKET_CLOSE);
            added = true;
        }
        return added;
    }

    public static boolean addDateBetweenQueryPhrase(StringBuilder queryBuilder, String column, String startDate, String endDate, boolean addAnd, int dbType) {
        return addDateBetweenQueryPhrase(queryBuilder, column, startDate, endDate, addAnd, dbType, false);
    }

    public static boolean addDateBetweenQueryPhrase(StringBuilder queryBuilder, String column, String startDate, String endDate, boolean addAnd, int dbType, boolean isTimestamp) {
        boolean added = false;
        if ((startDate != null && !startDate.isEmpty()) || (endDate != null && !endDate.isEmpty())) {
            if (addAnd) {
                queryBuilder.append(AND);
            }
            queryBuilder.append(SQL_BRACKET_OPEN).append(column);
            if (startDate != null && !startDate.isEmpty()) {
                if (endDate != null && !endDate.isEmpty()) {
                    queryBuilder.append(QUERY_BETWEEN).append(isTimestamp? DBUtils.getTimeFunctionQuery(startDate, timeFormat, dbType) : DBUtils.getDateFunctionQuery(startDate, dateFormat, dbType)).
                            append(AND).append(isTimestamp? DBUtils.getTimeFunctionQuery(endDate, timeFormat, dbType) : DBUtils.getDateFunctionQuery(endDate, dateFormat, dbType));
                } else {
                    queryBuilder.append(CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN).append(isTimestamp? DBUtils.getTimeFunctionQuery(startDate, timeFormat, dbType) : DBUtils.getDateFunctionQuery(startDate, dateFormat, dbType));
                }
            } else {
                queryBuilder.append(CommonDatabaseParams.QUERY_LESS_OR_EQUAL_THAN).append(isTimestamp? DBUtils.getTimeFunctionQuery(endDate, timeFormat, dbType) : DBUtils.getDateFunctionQuery(endDate, dateFormat, dbType));
            }
            queryBuilder.append(SQL_BRACKET_CLOSE);
            added = true;
        }
        return added;
    }


    /**
     * get SUBSTR function added column name
     *
     * @param columnName String
     * @param start      int
     * @param end        int
     * @return String of subStr column name
     */
    public static String getSubStringColumnName(String columnName, int start, int end) {
        StringBuilder sb = new StringBuilder();
        sb.append(DBConstants.CommonDatabaseParams.QUERY_SUBSTR_FUNC)
                .append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN)
                .append(columnName)
                .append(DBConstants.CommonDatabaseParams.QUERY_COMMA)
                .append(start)
                .append(DBConstants.CommonDatabaseParams.QUERY_COMMA)
                .append(end)
                .append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return sb.toString();
    }

    /**
     * read the clob & return string
     *
     * @param clob clob - email body
     * @return string
     */
    public static String readClob(Clob clob) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(clob.getCharacterStream());
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (SQLException e) {
                logger.error("SQL ERROR in readClob()", e);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (IOException e) {
            logger.error("IO ERROR in readClob()", e);
        }
        return StringEscapeUtils.unescapeJava(sb.toString());
    }

    /**
     * convert string to clob
     *
     * @param content string (email body)
     * @param session hibernate session
     * @return clob
     */
    public static Clob setClob(String content, Session session) {
        return Hibernate.getLobCreator(session).createClob(content);
    }

    public static Double getDoubleFromResultSet(ResultSet resultSet, String columnName) throws SQLException {
        Double columnValue = resultSet.getDouble(columnName);
        if (resultSet.wasNull()) {
            columnValue = null;
        }
        return columnValue;
    }

    private static void setIndividualDTOMap(IndividualDTO individualDTO, ResultSet results, List<String> supportedLang) throws SQLException {

        Map<String, IndividualLangDTO> individualLangDTOMap = new HashMap<String, IndividualLangDTO>();

        if (supportedLang != null) {
            for (String lang : supportedLang) {
                lang = lang.toUpperCase();
                IndividualLangDTO individualLangDTO = new IndividualLangDTO();
                individualLangDTO.setName(results.getString(DBConstants.LangSpecificDatabaseColumns.NAME_LANG + lang));
                individualLangDTO.setIndividualName(results.getString(LangSpecificDatabaseColumns.INDIVIDUAL_NAME_LANG + lang));
                individualLangDTO.setPrefix(results.getString(LangSpecificDatabaseColumns.PREFIX_LANG + lang));
                individualLangDTOMap.put(lang, individualLangDTO);
            }
        }
        individualDTO.setIndividualLangDTOMap(individualLangDTOMap);
    }
    //endregion

    /**
     * get financial line items for screener filter
     *
     * @param segmentList List
     * @return Map<String, List<FinancialLineItem>>
     */
    public static Map<String, List<FinancialLineItem>> generateFinancialLineItemMap(List segmentList) {
        Map<String, List<FinancialLineItem>> financialLineItemListMap = new LinkedHashMap<String, List<FinancialLineItem>>(3);
        List<FinancialLineItem> financialLineItemIsList = new ArrayList<FinancialLineItem>();
        List<FinancialLineItem> financialLineItemBsList = new ArrayList<FinancialLineItem>();
        List<FinancialLineItem> financialLineItemCfList = new ArrayList<FinancialLineItem>();

        for (Object o : segmentList) {
            Object[] row = (Object[]) o;
            FinancialLineItem financialLineItem = new FinancialLineItem();
            String infoType = row[1].toString();
            financialLineItem.setFieldId(Integer.parseInt(row[0].toString()));
            financialLineItem.setInfoType(infoType);
            if (row[2] != null) {
                financialLineItem.setDescription(row[2].toString());
            }
            if (infoType.equalsIgnoreCase(IConstants.FinancialTypes.IS.toString())) {
                financialLineItemIsList.add(financialLineItem);
            } else if (infoType.equalsIgnoreCase(IConstants.FinancialTypes.BS.toString())) {
                financialLineItemBsList.add(financialLineItem);
            } else {
                financialLineItemCfList.add(financialLineItem);
            }
        }
        sortFinancialLineItemIsList(financialLineItemIsList);
        sortFinancialLineItemIsList(financialLineItemBsList);
        sortFinancialLineItemIsList(financialLineItemCfList);
        financialLineItemListMap.put(IConstants.FinancialTypes.IS.toString(), financialLineItemIsList);
        financialLineItemListMap.put(IConstants.FinancialTypes.BS.toString(), financialLineItemBsList);
        financialLineItemListMap.put(IConstants.FinancialTypes.CF.toString(), financialLineItemCfList);

        return financialLineItemListMap;
    }

    /**
     * sort the list asc
     *
     * @param financialLineItemIsList is an array list
     */
    private static void sortFinancialLineItemIsList(List<FinancialLineItem> financialLineItemIsList) {
        try {
            Collections.sort(financialLineItemIsList, new Comparator<FinancialLineItem>() {
                public int compare(FinancialLineItem fl1, FinancialLineItem fl2) {
                    if (fl1.getDescription() != null && fl2.getDescription() != null) {
                        return fl1.getDescription().compareTo(fl2.getDescription());
                    } else {
                        return (fl1.getFieldId() > fl2.getFieldId() ? 1 : -1);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Error in sorting financialLineItemListMap" + e);
        }
    }

    /**
     * populateIndividualProfiles
     *
     * @param results sql result set
     * @return individual profile list
     * @throws SQLException
     */
    public static List<Map<String, String>> populateIndividualProfiles(ResultSet results) throws SQLException {
        List<Map<String, String>> individualProfileList = new ArrayList<Map<String, String>>();
        while (results.next()) {
            Map<String, String> individualProfileMap = new HashMap<String, String>();
            individualProfileMap.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_ID));
            individualProfileMap.put(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME, results.getString(DBConstants.DatabaseColumns.IND_INDIVIDUAL_NAME));
            individualProfileMap.put(DatabaseColumns.LAST_UPDATED_TIME, String.valueOf(results.getTimestamp(DBConstants.DatabaseColumns.LAST_UPDATED_TIME)));
            individualProfileList.add(individualProfileMap);
        }
        return individualProfileList;
    }

    /**
     * setCompanyCoverageData
     *
     * @param results             sql result set
     * @param companyCoverageDTOs companyCoverageDTOs
     * @throws SQLException
     */
    public static void setCompanyCoverageData(ResultSet results, Map<String, CompanyCoverageDTO> companyCoverageDTOs) throws SQLException {
        String code;
        while (results.next()) {
            code = results.getString(DBConstants.DatabaseColumns.CODE);
            if (!CommonUtils.isNullOrEmptyString(code)) {
                CompanyCoverageDTO companyCoverageDTO = new CompanyCoverageDTO();
                companyCoverageDTO.setCode(code);
                companyCoverageDTO.setPrivateCount(results.getInt(DBConstants.DatabaseColumns.PRIVATE_COUNT));
                companyCoverageDTO.setPublicCount(results.getInt(DBConstants.DatabaseColumns.PUBLIC_COUNT));
                companyCoverageDTO.setTotalCount(results.getInt(DBConstants.DatabaseColumns.TOTAL));
                companyCoverageDTOs.put(code, companyCoverageDTO);
            }
        }
    }

    public static void setCompanyAggregatesData(ResultSet results, Map<String, Map<String, Map<String, String>>> companyAggregatesMap) throws SQLException {
        Map<String, Map<String, String>> countryMap;
        String sectorId = results.getString(DBConstants.DatabaseColumns.SECTOR_CODE);

        if (sectorId == null) {
            return;
        }

        Map<String, String> valueMap;

        if (companyAggregatesMap.containsKey(sectorId)) {
            countryMap = companyAggregatesMap.get(sectorId);
        } else {
            countryMap = new HashMap<String, Map<String, String>>(1);
        }

        String countryCode = results.getString(DatabaseColumns.COUNTRY_CODE);
        if (countryMap.containsKey(countryCode)) {
            valueMap = countryMap.get(countryCode);
        } else {
            valueMap = new HashMap<String, String>(1);
        }

        valueMap.put(IConstants.PUBLIC, results.getString(DatabaseColumns.PUBLIC_COUNT));
        valueMap.put(IConstants.PRIVATE, results.getString(DatabaseColumns.PRIVATE_COUNT));
        valueMap.put(IConstants.TOTAL, results.getString(DatabaseColumns.TOTAL));
        valueMap.put(IConstants.PUBLIC_COMPANY_COUNT, results.getString(DatabaseColumns.PUBLIC_COMPANY_COUNT));
        valueMap.put(IConstants.PRIVATE_COMPANY_COUNT, results.getString(DatabaseColumns.PRIVATE_COMPANY_COUNT));
        valueMap.put(IConstants.TOTAL_COMPANY_COUNT, results.getString(DatabaseColumns.TOTAL_COMPANY_COUNT));
        countryMap.put(countryCode, valueMap);
        companyAggregatesMap.put(sectorId, countryMap);
    }
}
