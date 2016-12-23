package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.FundPositionItem;
import com.dfn.alerts.beans.FundPositionsDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 3/5/14
 * Time: 10:25 AM
 */
public class FundPositionDBHelper {


    public static final Logger LOG = LogManager.getLogger(FundPositionDBHelper.class);

    private static final String tickerSerialPlaceHolder = "#s#";
    private static final String startDatePlaceHolder = "#sdate#";
    private static final String endDatePlaceHolder = "#edate#";

    private static final String MAX_FUND_POSITION_SQL =
            "SELECT FUND_TICKER_SERIAL, INVESTED_TICKER_SERIAL, TXN_DATE, RATIO, NUMBER_OF_SHARES, ESTIMATED_SHARES, IS_SINGLE_RECORD" +
                    " FROM DFN_FUND_INVEST_ALLOC WHERE RATIO >= 0 AND INVESTED_TICKER_SERIAL = #s# AND STATUS = 1 ORDER BY TXN_DATE ASC";

    private static final String FUND_POSITIONS_SQL =
            "SELECT * FROM (" +
                    "SELECT FUND_TICKER_SERIAL, INVESTED_TICKER_SERIAL, TXN_DATE, RATIO, NUMBER_OF_SHARES, ESTIMATED_SHARES, IS_SINGLE_RECORD " +
                    "FROM DFN_FUND_INVEST_ALLOC " +
                    "WHERE STATUS = 1 AND RATIO >= 0 AND INVESTED_TICKER_SERIAL = #s# AND " +
                        "TXN_DATE BETWEEN TO_DATE('#sdate#','yyyy-MM-dd')  AND TO_DATE('#edate#','yyyy-MM-dd') " +
                    "UNION " +
                    "SELECT FUND_TICKER_SERIAL, INVESTED_TICKER_SERIAL, TXN_DATE, RATIO, NUMBER_OF_SHARES, ESTIMATED_SHARES, IS_SINGLE_RECORD " +
                    "FROM (" +
                        "SELECT DENSE_RANK() OVER (PARTITION BY FUND_TICKER_SERIAL ORDER BY TXN_DATE DESC)rnk, r.* " +
                        "FROM (" +
                            "SELECT * FROM DFN_FUND_INVEST_ALLOC " +
                            "WHERE STATUS = 1 AND INVESTED_TICKER_SERIAL = #s# AND TXN_DATE < TO_DATE('#sdate#','yyyy-MM-dd')" +
                        ") r" +
                    ") WHERE rnk < 2 AND RATIO >= 0 " +
            ") ORDER BY TXN_DATE DESC";

    private static final String FUND_POSITIONS_FOR_TABLE_SQL =
            "SELECT FUND_TICKER_SERIAL, INVESTED_TICKER_SERIAL, TXN_DATE, RATIO, NUMBER_OF_SHARES, ESTIMATED_SHARES, IS_SINGLE_RECORD, rank " +
            "FROM (" +
                "SELECT DENSE_RANK() OVER (PARTITION BY FUND_TICKER_SERIAL ORDER BY TXN_DATE DESC)rank, r.* " +
                "FROM (" +
                    "SELECT * FROM DFN_FUND_INVEST_ALLOC WHERE STATUS = 1 AND INVESTED_TICKER_SERIAL = #s# AND RATIO >= 0 " +
                ") r" +
            ") WHERE rank < 3 ORDER BY rank ASC";


    /**
     * fill the fund investment allocation dto from DB
     *
     * @param resultSet resultset
     * @param dates start date + "|" + end date
     * @throws SQLException
     */
    public static FundPositionsDTO getFundPositions(ResultSet resultSet, String dates) throws SQLException {
        FundPositionsDTO fundPositionsDTO = new FundPositionsDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat(IConstants.DateFormats.FORMAT3);

        String[] dateArray = dates.split(IConstants.Delimiter.UNDERSCORE);
        fundPositionsDTO.setStartDate(dateArray[0]);
        fundPositionsDTO.setEndDate(dateArray[1]);

        Date startDate = null;
        try{
            startDate = dateFormat.parse(dateArray[0]);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }

        while (resultSet.next() && startDate != null) {

            long fundTickerSerial = resultSet.getLong(DBConstants.DatabaseColumns.FUND_TICKER_SERIAL);
            java.sql.Date transactionDate = resultSet.getDate(DBConstants.DatabaseColumns.TXN_DATE);

            FundPositionItem fundPositionItem = getFundPositionItem(resultSet);

            String fund = fundTickerSerial + "";
            if (!isHistory(transactionDate, startDate)) {
                fundPositionsDTO.addCurrentFundPositions(fund, fundPositionItem);
            } else {
                fundPositionsDTO.addFundPositionHistory(fund, fundPositionItem);
            }

            if (!fundPositionsDTO.isFundTickerAdded(fund)) {
                fundPositionsDTO.addFundTickerSerials(fund);
            }
        }
        return fundPositionsDTO;
    }

    /**
     * fill the fund investment allocation dto from DB
     * ORDER IS IMPORTANT
     * ORDER in asc
     * start date is first instance of a multiple investment from same fund with ratio > 0
     *
     * @param resultSet resultset
     * @throws SQLException
     */
    public static FundPositionsDTO getFundPositionsForMax(ResultSet resultSet, String endDate) throws SQLException {
        FundPositionsDTO fundPositionsDTO = new FundPositionsDTO();

        java.sql.Date startDate = null;
        fundPositionsDTO.setEndDate(endDate);

        while (resultSet.next()) {

            long fundTickerSerial = resultSet.getLong(DBConstants.DatabaseColumns.FUND_TICKER_SERIAL);
            java.sql.Date transactionDate = resultSet.getDate(DBConstants.DatabaseColumns.TXN_DATE);

            String fund = fundTickerSerial + "";

            FundPositionItem fundPositionItem = getFundPositionItem(resultSet);

            if (startDate == null) {
                if (!fundPositionsDTO.isFundTickerAdded(fund)) {
                    fundPositionsDTO.addFundTickerSerials(fund);
                    fundPositionsDTO.addFundPositionHistory(fund, fundPositionItem);
                } else {
                    startDate = transactionDate;
                    fundPositionsDTO.setStartDate(startDate.toString());
                    fundPositionsDTO.addCurrentFundPositionsForMax(fund, fundPositionItem);
                }
            } else {
                if (!isHistory(transactionDate, startDate)) {
                    fundPositionsDTO.addCurrentFundPositionsForMax(fund, fundPositionItem);
                } else {
                    fundPositionsDTO.addFundPositionHistory(fund, fundPositionItem);
                }
                if (!fundPositionsDTO.isFundTickerAdded(fund)) {
                    fundPositionsDTO.addFundTickerSerials(fund);
                }
            }
        }

        fundPositionsDTO.reverseOrderCurrentFundPositions();
        return fundPositionsDTO;
    }


    public static FundPositionItem getFundPositionItem(ResultSet resultSet) throws SQLException {
        long investedTickerSerial = resultSet.getLong(DBConstants.DatabaseColumns.INVESTED_TICKER_SERIAL);
        long noOfShares = resultSet.getLong(DBConstants.DatabaseColumns.NUMBER_OF_SHARES);
        long estimatedShares = resultSet.getLong(DBConstants.DatabaseColumns.ESTIMATED_SHARES);
        double investmentPercentage = resultSet.getDouble(DBConstants.DatabaseColumns.RATIO);
        java.sql.Date transactionDate = resultSet.getDate(DBConstants.DatabaseColumns.TXN_DATE);
        boolean singleRecord = resultSet.getBoolean(DBConstants.DatabaseColumns.IS_SINGLE_RECORD);
        return new FundPositionItem(transactionDate.toString(), investedTickerSerial, investmentPercentage, noOfShares,
                estimatedShares, singleRecord);
    }

    /**
     * Check whether the provided date is belongs to history
     *
     * @param date      provided date
     * @param dateStart start date of the filter period
     * @return whether the provided date is belongs to history or not
     */
    private static boolean isHistory(Date date, Date dateStart) {
        return date != null && dateStart != null && date.before(dateStart);
    }

    /**
     * get sql query to load fund position data for MAX period
     * @param tickerSerial symbol
     * @return query
     */
    public static String getMaxFundPositionSql(String tickerSerial){
        return MAX_FUND_POSITION_SQL.replaceAll(tickerSerialPlaceHolder, tickerSerial);
    }

    /**
     * get sql query to load fund position data
     * @param tickerSerial symbol
     * @param startDate start date
     * @param endDate end date
     * @return query
     */
    public static String getFundPositionsSql(String tickerSerial, String startDate, String endDate){
        String query = FUND_POSITIONS_SQL.replaceAll(tickerSerialPlaceHolder, tickerSerial);
        query = query.replaceAll(startDatePlaceHolder, startDate);
        query = query.replaceAll(endDatePlaceHolder, endDate);
        return query;
    }

    /**
     * get sql query to load fund position data for table
     * @param tickerSerial symbol
     * @return query
     */
    public static String getFundPositionsForTableSql(String tickerSerial){
        return FUND_POSITIONS_FOR_TABLE_SQL.replaceAll(tickerSerialPlaceHolder, tickerSerial);
    }
}
