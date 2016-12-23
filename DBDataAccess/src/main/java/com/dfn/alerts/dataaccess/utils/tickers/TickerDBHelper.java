package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.beans.tickers.TickerLangDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 4/3/2014.
 */
public class TickerDBHelper {

    private static String updateTickerColumnString;
    private static String insertTickerColumnString;
    private static String insertTickerColumnParams;

    public static final String UPDATE_PLACEHOLDER = CompanyDBHelper.UPDATE_PLACEHOLDER;

    public static final String[] TICKER_COLUMNS = new String[]{CURRENCY_ID, DECIMAL_PLACES, DISPLAY_TICKER,  INSTRUMENT_TYPE_ID,
            ISIN_CODE, PARENT_SOURCE_ID,  SOURCE_ID,  TICKER_ID, TICKER_LONG_DES_LN, TICKER_SHORT_DES_LN,  STATUS, LAST_UPDATED_TIME,
            LAST_SYNC_TIME, TICKER_COUNTRY_CODE, ELIGIBILITY_ID, TICKER_CLASS_L1,TICKER_CLASS_L2,TICKER_CLASS_L3};

    private static final String[] LANGUAGE_SPECIFIC_TICKER_COLUMNS = new String[]{TICKER_SHORT_DESCRIPTION, TICKER_LONG_DESCRIPTION, SECTOR_NAME, TICKER_COUNTRY_DESC};

    private static final String LIST_SYMBOL_FILTER = TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND + QUERY_STATUS_FILTER;

    // ( (TICKER_SERIAL=0 AND LISTING_STATUS IN (3,4))  OR  (TICKER_SERIAL > 0 AND STATUS = 1  AND LISTING_STATUS=2 AND IS_MAIN_STOCK = 1 )  OR  (TICKER_SERIAL > 0 AND STATUS = 1  AND LISTING_STATUS=1 AND INSTRUMENT_TYPE_ID IN  (0,60,61) ) )
    private static final String TICKERS_TABLE_FILTER_TO_REMOVE_DUPLICATES =
            QUERY_BRACKET_OPEN +
                QUERY_BRACKET_OPEN +
                    TICKER_SERIAL + QUERY_EQUAL + 0 +
                       QUERY_AND +
                    LISTING_STATUS + QUERY_IN + SQL_BRACKET_OPEN +
                        IConstants.CompanyListingType.UNLISTED + DBConstants.SQL_COMMA + IConstants.CompanyListingType.DELISTED +
                    SQL_BRACKET_CLOSE +
                QUERY_BRACKET_CLOSE +
              QUERY_OR +
                QUERY_BRACKET_OPEN +
                        LIST_SYMBOL_FILTER +
                            QUERY_AND +
                        LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.MULTY_LISTED  +
                            QUERY_AND +
                        QUERY_IS_MAIN_STOCK +
                QUERY_BRACKET_CLOSE +
              QUERY_OR +
                QUERY_BRACKET_OPEN +
                    LIST_SYMBOL_FILTER +
                      QUERY_AND +
                    LISTING_STATUS + QUERY_EQUAL +IConstants.CompanyListingType.LISTED  +
                      QUERY_AND +
                    INSTRUMENT_TYPE_ID + QUERY_IN + QUERY_BRACKET_OPEN +
                                                      IConstants.InstrumentTypes.getEquityInstrumentTypes() +
                                                    QUERY_BRACKET_CLOSE +
                QUERY_BRACKET_CLOSE +
            QUERY_BRACKET_CLOSE;

    public static String getUpdateTickerColumnString(List<String> supportedLang) {
        if (updateTickerColumnString == null) {
            StringBuilder updateTickerStringBuilder = new StringBuilder();
            updateTickerStringBuilder.append(StringUtils.join(TICKER_COLUMNS, UPDATE_PLACEHOLDER)).append(UPDATE_PLACEHOLDER);//join TICKER_COLUMNS with =?,"
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_TICKER_COLUMNS) {
                    lang = lang.toUpperCase();
                    updateTickerStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(UPDATE_PLACEHOLDER);
                }
            }
            updateTickerColumnString = updateTickerStringBuilder.toString() + CompanyDBHelper.getUpdateCompanyColumnString(supportedLang);
        }
        return updateTickerColumnString;
    }

    public static String getInsertTickerColumnString(List<String> supportedLang) {
        if (insertTickerColumnString == null) {
            StringBuilder insertTickerStringBuilder = new StringBuilder();
            insertTickerStringBuilder.append(StringUtils.join(TICKER_COLUMNS, IConstants.Delimiter.COMMA)).append(IConstants.Delimiter.COMMA);//join TICKER_COLUMNS with ","
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_TICKER_COLUMNS) {
                    lang = lang.toUpperCase();
                    insertTickerStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(IConstants.Delimiter.COMMA);
                }
            }
            insertTickerColumnString = insertTickerStringBuilder.toString() + CompanyDBHelper.getInsertCompanyColumnString(supportedLang);
        }
        return insertTickerColumnString;
    }


    public static void setTickerData(TickerDTO ticker, ResultSet results, List<String> supportedLanguages) throws SQLException {
        CompanyDBHelper.setCompanyData(ticker, results, supportedLanguages);
        ticker.setSourceId(results.getString(SOURCE_ID));
        ticker.setTickerId(results.getString(TICKER_ID));
        ticker.setIsinCode(results.getString(ISIN_CODE));
        ticker.setDisplayTicker(results.getString(DISPLAY_TICKER));
        ticker.setStatus(results.getInt(STATUS));
        ticker.setTickerSerial(results.getLong(TICKER_SERIAL));
        ticker.setParentSourceId(results.getString(PARENT_SOURCE_ID));
        ticker.setTickerShortDes(results.getString(SHORT_DESC_LAN));
        ticker.setTickerLongDesLn(results.getString(LONG_DESC_LAN));
        ticker.setInstrumentTypeId(results.getInt(INSTRUMENT_TYPE_ID));
        ticker.setCurrencyId(results.getString(CURRENCY_ID));
        ticker.setDecimalPlaces(results.getInt(DECIMAL_PLACES));
        ticker.setLastUpdatedTime(results.getTimestamp(LAST_UPDATED_TIME));
        ticker.setLastSyncTime(results.getTimestamp(LAST_SYNC_TIME));
        ticker.setCityId(results.getInt(CITY_ID));
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

    public static String getTickerInsertColumnParams(List<String> supportedLang) {
        if (insertTickerColumnParams == null) {
            insertTickerColumnParams = StringUtils.repeat(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK,DBConstants.CommonDatabaseParams.SQL_COMMA, TICKER_COLUMNS.length + LANGUAGE_SPECIFIC_TICKER_COLUMNS.length * supportedLang.size());
            insertTickerColumnParams += DBConstants.CommonDatabaseParams.SQL_COMMA + CompanyDBHelper.getCompanyInsertColumnParams(supportedLang);
        }
        return insertTickerColumnParams;
    }

    public static int setUpdateTickerSetValues(PreparedStatement preparedStatement,
                                               TickerDTO ticker, int index, List<String> supportedLanguages) throws SQLException {

        preparedStatement.setString(index++, ticker.getCurrencyId());
        preparedStatement.setDouble(index++, ticker.getDecimalPlaces());
        preparedStatement.setString(index++, ticker.getDisplayTicker());
        preparedStatement.setInt(index++, ticker.getInstrumentTypeId());
        preparedStatement.setString(index++, ticker.getIsinCode());
        preparedStatement.setString(index++, ticker.getParentSourceId());
        preparedStatement.setString(index++, ticker.getSourceId());
        preparedStatement.setString(index++, ticker.getTickerId());
        preparedStatement.setString(index++, ticker.getTickerLongDesLn());
        preparedStatement.setString(index++, ticker.getTickerShortDes());
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
        index = CompanyDBHelper.setUpdateCompanySetValues(preparedStatement, ticker, index, supportedLanguages);
        return index;
    }

    public static List<String> getAllTickerColumns(List<String> supportedLanguages){
        List<String> columns = new ArrayList<String>(CompanyDBHelper.getAllCompanyColumns(supportedLanguages));
        columns.addAll(Arrays.asList(TICKER_COLUMNS));
        columns.add(TICKER_SERIAL);//todo ???
        for (String lang : supportedLanguages) {
            lang = lang.toUpperCase();
            for (String column : LANGUAGE_SPECIFIC_TICKER_COLUMNS) {
                columns.add(column + IConstants.Delimiter.UNDERSCORE + lang);
            }
        }
        return columns;
    }

    public static String getAllTickersByTickerSerials(Collection<String> tickerSerialList, List<String> supportedLanguages){
        List<String> tickerColumns = getAllTickerColumns(supportedLanguages);
        String tickers = CommonUtils.getCommaSeperatedStringFromList(tickerSerialList);
        String filter = QUERY_WHERE + TICKER_SERIAL;
        if(tickers.indexOf(IConstants.Delimiter.COMMA) > 0){
            filter += QUERY_IN + QUERY_BRACKET_OPEN + tickers + QUERY_BRACKET_CLOSE;
        }else{
            filter += QUERY_EQUAL + tickers;
        }
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter +
                UNION_ALL + selectFrom + DBConstants.TablesIMDB.TABLE_FUND_TICKERS + filter +
                UNION_ALL + selectFrom + DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS + filter;
    }

    /**
     * Get Listed or Unlisted tickers from ticker table
     *
     * ~ IF_EQUITY_TICKER - (IS_MAIN_STOCK = 1) only
     * @param companyIdList COMPANY id List
     * @param supportedLanguages supported languages
     * @return query
     */
    public static String getTickersByCompanyIdList(Collection<String> companyIdList, List<String> supportedLanguages) {
        List<String> companyColumns = getAllTickerColumns(supportedLanguages);
        String companies = CommonUtils.getCommaSeperatedStringFromList(companyIdList);
        String filter = QUERY_WHERE + COMPANY_ID;

        if (companies.indexOf(IConstants.Delimiter.COMMA) > 0) {
            filter += QUERY_IN + QUERY_BRACKET_OPEN + companies + QUERY_BRACKET_CLOSE;
        } else {
            filter += QUERY_EQUAL + companies;
        }
        String selectFrom = QUERY_SELECT + StringUtils.join(companyColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter + QUERY_AND + TICKERS_TABLE_FILTER_TO_REMOVE_DUPLICATES;
    }

    public static String getAllTickersBySymbolExchange(String tickerId, String sourceId, List<String> supportedLanguages){
        List<String> tickerColumns = getAllTickerColumns(supportedLanguages);
        String filter = QUERY_WHERE +  QUERY_UPPER + QUERY_BRACKET_OPEN + TICKER_ID + QUERY_BRACKET_CLOSE + QUERY_EQUAL
                + QUERY_QUOTE + tickerId.toUpperCase() + QUERY_QUOTE +  QUERY_AND + SOURCE_ID + QUERY_EQUAL + QUERY_QUOTE + sourceId +
                QUERY_QUOTE;
        filter += QUERY_AND + QUERY_STATUS_FILTER;
        String selectFrom = QUERY_SELECT + StringUtils.join(tickerColumns, IConstants.Delimiter.COMMA) + FROM;
        return selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter +
                UNION_ALL + selectFrom + DBConstants.TablesIMDB.TABLE_FUND_TICKERS + filter +
                UNION_ALL + selectFrom + DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS + filter;
    }

    public static String getSelectUniqueTickerFilter(){
        return TICKERS_TABLE_FILTER_TO_REMOVE_DUPLICATES;
    }
}
