package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.tickers.EquityTickerDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.tickers.CompanyDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.EquityTickerDBHelper;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.COMPANY_ID;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.TICKER_SERIAL;
import static com.dfn.alerts.constants.DBConstants.TablesORACLE.TABLE_TICKERS;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 4/11/14
 * Time: 1:57 PM
 */
public class UnlistedCompaniesDBHelper {

    private static String updateUnlistedCompanyColumnString;

    private static final String UNLISTED_COMPANY_FILTER = TICKER_SERIAL + " = 0";

    private static final String SELECT_UNLISTED_COMPANIES = QUERY_SELECT_ALL_FROM + TABLE_TICKERS + QUERY_WHERE + UNLISTED_COMPANY_FILTER;

    public static final String LAST_SYNC_TIME_FILTER = DBConstants.SQL_LAST_SYNC_TIME_FILTER;

    /**
     * select query to load all unlisted companies from Oracle
     *
     * @param lastRunTime last run time
     * @return request db dto
     */
    public static RequestDBDTO getUnlistedCompaniesQuery(List<String> supportedLanguages, Timestamp lastRunTime) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        String query = SELECT_UNLISTED_COMPANIES;
        if (lastRunTime != null) {
            query = query + QUERY_AND + LAST_SYNC_TIME_FILTER;
            requestDBDTO.setLastRunTime(lastRunTime);
            requestDBDTO.setLastRunTimeRequired(true);
        }
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(supportedLanguages);
        return requestDBDTO;
    }

    /**
     * build insert unlisted companies query (same as equity ticker sql string)
     *
     * @return SQL String
     */
    public static String getInsertUnlistedCompaniesSql(List<String> supportedLanguages) {
        return EquityTickerDBHelper.getInsertEquityTickerSql(supportedLanguages);
    }

    /**
     * build update unlisted companies query (key is company id & ticker_serial = 0)
     * same as CompanyDBHelper.getUpdateCompanyColumnString() without adding company id
     *
     * @return SQL String
     */
    private static String getUpdateUnlistedCompaniesColumnString(List<String> supportedLanguages) {
        if (updateUnlistedCompanyColumnString == null) {
            StringBuilder updateCompanyStringBuilder = new StringBuilder();
            updateCompanyStringBuilder.append(StringUtils.join(CompanyDBHelper.getCompanyProfileColumns(), CompanyDBHelper.UPDATE_PLACEHOLDER)).append(CompanyDBHelper.UPDATE_PLACEHOLDER);//join TICKER_COLUMNS with =?,"
            updateCompanyStringBuilder.append(StringUtils.join(CompanyDBHelper.getCompanyUpdateColumns(), CompanyDBHelper.UPDATE_PLACEHOLDER)).append(CompanyDBHelper.UPDATE_PLACEHOLDER);//join TICKER_COLUMNS with =?,"
            for (String lang : supportedLanguages) {
                for (String column : CompanyDBHelper.getLanguageSpecificCompanyColumns()) {
                    lang = lang.toUpperCase();
                    updateCompanyStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(CompanyDBHelper.UPDATE_PLACEHOLDER);
                }
            }
            updateUnlistedCompanyColumnString = CommonUtils.removeLast(updateCompanyStringBuilder.toString());
        }
        return updateUnlistedCompanyColumnString;
    }

    /**
     * build update unlisted companies sql string
     * only updates the company columns
     *
     * @return SQL String
     */
    public static String getUpdateUnlistedCompaniesSql(List<String> supportedLanguages) {
        StringBuilder sql = new StringBuilder(QUERY_UPDATE);
        sql.append(TABLE_TICKERS);
        sql.append(QUERY_SET);
        sql.append(getUpdateUnlistedCompaniesColumnString(supportedLanguages));
        sql.append(QUERY_WHERE);
        sql.append(UNLISTED_COMPANY_FILTER);
        sql.append(QUERY_AND);
        sql.append(COMPANY_ID);
        sql.append(QUERY_EQUAL);
        sql.append(SQL_QUESTION_MARK);
        return sql.toString();
    }

    /**
     * only updates the company columns
     * same as CompanyDBHelper.setUpdateCompanySetValues() without company id
     *
     * @param preparedStatement  prepared statement
     * @param ticker             ticker dto
     * @param index              index
     * @param supportedLanguages languages
     * @return index
     * @throws SQLException
     */
    public static int setUpdateCompanySetValues(PreparedStatement preparedStatement,
                                                TickerDTO ticker, int index, List<String> supportedLanguages) throws SQLException {

        index = CompanyDBHelper.setUpdateCompanyProfileSetValues(preparedStatement, ticker, index, supportedLanguages);

        preparedStatement.setString(index++, ticker.getCompanyNameLan());
        preparedStatement.setString(index++, ticker.getCountryCode());
        preparedStatement.setString(index++, ticker.getGicsL2Lan());
        preparedStatement.setString(index++, ticker.getGicsL3Lan());
        preparedStatement.setString(index++, ticker.getGicsL4Lan());
        preparedStatement.setInt(index++, ticker.getListingType());
        preparedStatement.setString(index++, ticker.getGicsL4Code());
        preparedStatement.setInt(index++, ticker.getFinancialYearStartMonth());
        preparedStatement.setInt(index++, ticker.getFinancialStFreq());
        preparedStatement.setInt(index++, ticker.getTemplateId());
        preparedStatement.setInt(index++, ticker.getCompanyType());

        if (ticker.getGicsL3Code() != null && !ticker.getGicsL3Code().trim().isEmpty()) {
            preparedStatement.setString(index++, ticker.getGicsL3Code());
        } else {
            String gicsL3Code = CommonUtils.getGicsCodeFromGicsL4(ticker.getGicsL4Code(), 3);
            preparedStatement.setString(index++, gicsL3Code);
        }

        if (ticker.getGicsL2Code() != null && !ticker.getGicsL2Code().trim().isEmpty()) {
            preparedStatement.setString(index++, ticker.getGicsL2Code());
        } else {
            String gicsL2Code = CommonUtils.getGicsCodeFromGicsL4(ticker.getGicsL4Code(), 2);
            preparedStatement.setString(index++, gicsL2Code);
        }

        preparedStatement.setString(index++, ticker.getDefaultCurrencyId());

        preparedStatement.setDouble(index++, ticker.getPaidCapital());
        preparedStatement.setString(index++, ticker.getPaidCapitalCurrency());
        preparedStatement.setDouble(index++, ticker.getAuthCapital());
        preparedStatement.setString(index++, ticker.getAuthCapitalCurrency());

        if (ticker.getSlaLevel() != 0) {
            preparedStatement.setInt(index++, ticker.getSlaLevel());
        } else {
            preparedStatement.setObject(index++, null);
        }

        if (ticker.getCompanyMarketCap() != 0) {
            preparedStatement.setDouble(index++, ticker.getCompanyMarketCap());
        } else {
            preparedStatement.setObject(index++, null);
        }
        preparedStatement.setString(index++, ticker.getFullyOwnedSubsidiary());
        preparedStatement.setString(index++, ticker.getPartiallyOwnedSubsidiary());

        if (supportedLanguages != null) {
            for (String lang : supportedLanguages) {
                if (ticker.getTickerLangDTOMap() != null && ticker.getTickerLangDTOMap().get(lang) != null) {
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getCompanyName());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getCountryDesc());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getCityDesc());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getTradingName());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getGicsL2Lan());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getGicsL3Lan());
                    preparedStatement.setString(index++, ticker.getCompanyLangDTOMap().get(lang).getGicsL4Lan());
                }
            }
        }
        return index;
    }

    /**
     * set values to prepared statement
     *
     * @param preparedStatement  prepared statement
     * @param ticker             ticker dto
     * @param supportedLanguages languages
     * @throws SQLException
     */
    public static void setUpdateUnlistedCompanyValues(PreparedStatement preparedStatement, TickerDTO ticker, List<String> supportedLanguages) throws SQLException {
        int index = 1;
        index = setUpdateCompanySetValues(preparedStatement, ticker, index, supportedLanguages);
        preparedStatement.setInt(index, ticker.getCompanyId());
    }

    /**
     * same as equity ticker insert
     *
     * @param preparedStatement  prepared statement
     * @param ticker             equity ticker
     * @param supportedLanguages languages
     * @throws SQLException
     */
    public static void setInsertUnlistedCompanyValues(PreparedStatement preparedStatement, EquityTickerDTO ticker, List<String> supportedLanguages) throws SQLException {
        EquityTickerDBHelper.setUpdateEquityValues(preparedStatement, ticker, supportedLanguages);
    }

}
