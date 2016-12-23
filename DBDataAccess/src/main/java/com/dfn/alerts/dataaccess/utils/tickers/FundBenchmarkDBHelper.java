package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.tickers.FundBenchmarkDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;
import static com.dfn.alerts.constants.DBConstants.TablesIMDB.TABLE_FUND_BENCHMARKS;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 6/25/14
 * Time: 10:40 AM
 */
public class FundBenchmarkDBHelper {

    private static final String BT_SC = " fb";
    private static final String BT_SC_DOT = " fb.";
    private static final String TT_SC = " t";
    private static final String TT_SC_DOT = " t.";
    private static final String FT_SC = " f";
    private static final String FT_SC_DOT = " f.";
    private static final String FIT_SC = " fi";
    private static final String FIT_SC_DOT = " fi.";

    /**
     * SELECT BENCHMARK_TICKER, BENCHMARK_RATE, TICKER_SERIAL
     * FROM FUND_BENCHMARKS
     * WHERE TICKER_SERIAL
     */
    private static final String BENCHMARKS_FOR_FUND_SQL = QUERY_SELECT + BENCHMARK_TICKER + QUERY_COMMA + BENCHMARK_RATE + QUERY_COMMA + TICKER_SERIAL +
            FROM + TABLE_FUND_BENCHMARKS + QUERY_WHERE + TICKER_SERIAL;

    private static final String BENCHMARKS_COLUMNS = BENCHMARK_TICKER + QUERY_COMMA + BENCHMARK_RATE + QUERY_COMMA + TICKER_SERIAL + QUERY_COMMA + FUND_TICKER_SERIAL;

    private static final String BENCHMARKS_COLUMNS_FOR_FILTER =  BENCHMARK_TICKER + QUERY_COMMA + BENCHMARK_RATE + QUERY_COMMA + TICKER_SERIAL + SQL_AS + FUND_TICKER_SERIAL;

    private static final String BENCHMARK_TICKER_SERIAL_SQL = QUERY_SELECT + QUERY_DISTINCT + BENCHMARK_TICKER + FROM + TABLE_FUND_BENCHMARKS;

    private static final String FUND_BENCHMARKS_SQL = QUERY_SELECT + TICKER_SERIAL + SQL_AS + FUND_TICKER_SERIAL +
            QUERY_COMMA + BENCHMARK_TICKER + QUERY_COMMA + BENCHMARK_RATE + QUERY_COMMA + LAST_UPDATED_TIME + FROM + "FUND_BENCHMARK"; //DBConstants.TablesORACLE.TABLE_FUND_BENCHMARKS;

    /**
     * get benchmarks
     *
     * @param fundTickerSerial fund ticker serial
     * @param language         language
     * @return requestDBDTO
     */
    public static RequestDBDTO getBenchmarksForFund(String fundTickerSerial, String language) {
        List<String> lang = new ArrayList<String>(1);
        lang.add(language);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(BENCHMARKS_FOR_FUND_SQL + QUERY_EQUAL + SQL_QUESTION_MARK);
        requestDBDTO.setParams(fundTickerSerial);
        requestDBDTO.setSupportedLang(lang);

        return requestDBDTO;
    }

    /**
     * get benchmarks
     *
     * @return requestDBDTO
     */
    public static RequestDBDTO getBenchmarks() {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(FUND_BENCHMARKS_SQL);
        return requestDBDTO;
    }

    /**
     * get benchmarks
     * SELECT t.*, BENCHMARK_RATE, b.TICKER_SERIAL AS FUND FROM (
     *      SELECT BENCHMARK_TICKER, BENCHMARK_RATE, FUND_TICKER_SERIAL
     *      FROM FUND_BENCHMARKS
     *      WHERE TICKER_SERIAL IN ()
     * ) b JOIN
     * (
     *      TickerDBHelper.getAllTickersByTickerSerials()
     * ) t ON (b.BENCHMARK_TICKER = t.TICKER_SERIAL)
     *
     * @param fundTickerSerialList list of fund ticker serials
     * @param language         language
     * @return requestDBDTO
     */
    public static RequestDBDTO getBenchmarksForFunds(List<String> fundTickerSerialList, String language) {
        List<String> lang = new ArrayList<String>(1);
        lang.add(language);

        String benchmarkQuery = QUERY_SELECT + BENCHMARKS_COLUMNS_FOR_FILTER + FROM + DBConstants.TablesIMDB.TABLE_FUND_BENCHMARKS
            + QUERY_WHERE + TICKER_SERIAL + QUERY_IN + QUERY_BRACKET_OPEN + CommonUtils.getCommaSeperatedStringFromList(fundTickerSerialList) + QUERY_BRACKET_CLOSE;

        StringBuilder selectQuery = new StringBuilder(QUERY_SELECT).append(StringUtils.join(TickerDBHelper.getAllTickerColumns(lang), IConstants.Delimiter.COMMA))
            .append(SQL_COMMA).append(BENCHMARKS_COLUMNS).append(FROM).append(QUERY_BRACKET_OPEN).append(benchmarkQuery).append(QUERY_BRACKET_CLOSE).append(BT_SC);

        StringBuilder query = new StringBuilder(selectQuery).append(QUERY_INNER_JOIN).append(DBConstants.TablesIMDB.TABLE_TICKERS).append(TT_SC)
            .append(QUERY_ON).append(BT_SC_DOT).append(BENCHMARK_TICKER).append(QUERY_EQUAL).append(TT_SC_DOT).append(TICKER_SERIAL);

        query.append(UNION_ALL).append(selectQuery).append(QUERY_INNER_JOIN).append(DBConstants.TablesIMDB.TABLE_FUND_TICKERS).append(FT_SC)
            .append(QUERY_ON).append(BT_SC_DOT).append(BENCHMARK_TICKER).append(QUERY_EQUAL).append(FT_SC_DOT).append(TICKER_SERIAL);

        query.append(UNION_ALL).append(selectQuery).append(QUERY_INNER_JOIN).append(DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS).append(FIT_SC)
            .append(QUERY_ON).append(BT_SC_DOT).append(BENCHMARK_TICKER).append(QUERY_EQUAL).append(FIT_SC_DOT).append(TICKER_SERIAL) ;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query.toString());
        requestDBDTO.setSupportedLang(lang);

        return requestDBDTO;
    }

    public static RequestDBDTO getAllBenchmarkTickerSerials(){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(BENCHMARK_TICKER_SERIAL_SQL);
        return requestDBDTO;
    }

    /**
     * fill the fund benchmark dto from oracle db(through synonyms) result set
     *
     * @param results result set
     * @return FundBenchmarkDTO
     * @throws SQLException
     */
    public static FundBenchmarkDTO getFundBenchmarks(ResultSet results) throws SQLException {
        FundBenchmarkDTO fundBenchmarkDTO = new FundBenchmarkDTO();
        setFundBenchmarks(fundBenchmarkDTO, results);
        return fundBenchmarkDTO;
    }

    /**
     * fill the fund benchmark dto from  result set
     *
     * @param results result set
     * @param fundBenchmarkDTO benchmark
     * @throws SQLException
     */
    public static void setFundBenchmarks(FundBenchmarkDTO fundBenchmarkDTO, ResultSet results ) throws SQLException {
        fundBenchmarkDTO.setFundTickerSerial(results.getLong(DBConstants.DatabaseColumns.FUND_TICKER_SERIAL));
        fundBenchmarkDTO.setTickerSerial(results.getLong(DBConstants.DatabaseColumns.BENCHMARK_TICKER));
        fundBenchmarkDTO.setBenchmarkRate(results.getDouble(DBConstants.DatabaseColumns.BENCHMARK_RATE));
        fundBenchmarkDTO.setBenchmarkLastUpdatedTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_UPDATED_TIME));
    }

}
