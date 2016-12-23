package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.tickers.FundBenchmarkDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.TickerDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS;
import static com.dfn.alerts.constants.DBConstants.TablesIMDB.TABLE_SOURCES;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 6/5/14
 * Time: 3:37 PM
 */
public class FundRatiosDBHelper {

    private static final String GET_SOURCES_SQL = QUERY_SELECT_ALL_FROM + TABLE_SOURCES + QUERY_WHERE +
            DBConstants.DatabaseColumns.S_IS_VIRTUAL_EXCHANGE + QUERY_NOT_EQUAL + 1 + QUERY_AND +
            DBConstants.DatabaseColumns.S_MAIN_INDEX_TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND;

    /**
     * get benchmarks
     *
     * @param fundTickerSerial fund ticker serial
     * @param language         language
     * @return requestDBDTO
     */
    public static RequestDBDTO getBenchmarksForFund(String fundTickerSerial, String language) {
        return FundBenchmarkDBHelper.getBenchmarksForFund(fundTickerSerial, language);
    }

    /**
     * get risk free rate calc tickers
     * SELECT * FROM FIXED_INCOME_TICKERS
     * WHERE STATUS = 1
     * AND ((INSTRUMENT_TYPE_ID IN (75,78,79,121) AND BOND_TYPE = 1) OR INSTRUMENT_TYPE_ID = 108)
     * AND COUNTRY_OF_ISSUE = 'xx'
     * AND MATURITY_DATE > yy
     * AND YTM > zz
     * ORDER BY DATE_OF_ISSUE desc
     *
     * @param language     language
     * @param country      country
     * @param bondType     bond type
     * @param maturityDate maturity date start
     * @param ytm          yield to maturity
     * @return query
     */
    public static RequestDBDTO getFundRatioRiskFreeRateFixedIncomeTickers(String language, String country, String bondType, String maturityDate, double ytm) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_SELECT_ALL_FROM).append(TABLE_FIXED_INCOME_TICKERS).append(QUERY_WHERE).
                append(DBConstants.DatabaseColumns.STATUS).append(QUERY_EQUAL).append("1").append(QUERY_AND).append(SQL_BRACKET_OPEN).
                append(SQL_BRACKET_OPEN).append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID).append(QUERY_IN).
                append(SQL_BRACKET_OPEN).append("75,78,79,121").append(SQL_BRACKET_CLOSE).append(QUERY_AND).
                append(DBConstants.DatabaseColumns.BOND_TYPE).append(QUERY_EQUAL).append(bondType).append(SQL_BRACKET_CLOSE).
                append(QUERY_OR).append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID).append(QUERY_EQUAL).append("108").append(SQL_BRACKET_CLOSE);

        //add issuer country
        if (!country.isEmpty()) {
            queryBuilder.append(QUERY_AND).append(DBConstants.DatabaseColumns.COUNTRY_OF_ISSUE).append(QUERY_EQUAL).
                    append(QUERY_QUOTE).append(country).append(QUERY_QUOTE);
        }

        //add maturity date
        if (!maturityDate.isEmpty()) {
            queryBuilder.append(QUERY_AND).append(DBConstants.DatabaseColumns.MATURITY_DATE);
            queryBuilder.append(QUERY_GREATER_THAN).append(DBUtils.getDateFunctionQuery(maturityDate, DBConstants.dateFormat, DBConstants.DatabaseTypes.DB_TYPE_DERBY));
        }

        if (ytm >= 0) {
            queryBuilder.append(QUERY_AND).append(DBConstants.DatabaseColumns.YTM).append(QUERY_GREATER_THAN).append(ytm);
        }

        //add ordering
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.DATE_OF_ISSUE).append(QUERY_SPACE).append(IConstants.DEFAULT_SORT_ORDER_DESC);

        List<String> lang = new ArrayList<String>(1);
        lang.add(language);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(lang);
        //Search fixed income
        return requestDBDTO;
    }

    public static RequestDBDTO getTickersByTickerSerial(Set<String> tickerSerialList, String language) {
        List<String> lang = new ArrayList<String>(1);
        lang.add(language);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(TickerDBHelper.getAllTickersByTickerSerials(tickerSerialList, lang));
        requestDBDTO.setSupportedLang(lang);

        return requestDBDTO;
    }

    public static RequestDBDTO getSourceDTO(String sourceId, String countryCode, String language) {
        List<String> lang = new ArrayList<String>(1);
        lang.add(language);

        String query;
        String param;
        if(countryCode != null && !countryCode.isEmpty()){
            query = GET_SOURCES_SQL + QUERY_BRACKET_OPEN + DBConstants.DatabaseColumns.S_SOURCE_ID + QUERY_EQUAL + SQL_QUESTION_MARK +
                    QUERY_OR + DBConstants.DatabaseColumns.S_COUNTRY_CODE + QUERY_EQUAL + SQL_QUESTION_MARK + QUERY_BRACKET_CLOSE;
            param = sourceId + IConstants.Delimiter.TILDE + countryCode;
        }else{
            query = GET_SOURCES_SQL + DBConstants.DatabaseColumns.S_SOURCE_ID + QUERY_EQUAL + SQL_QUESTION_MARK;
            param = sourceId;
        }
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setParams(param);
        requestDBDTO.setSupportedLang(lang);

        return requestDBDTO;
    }
}
