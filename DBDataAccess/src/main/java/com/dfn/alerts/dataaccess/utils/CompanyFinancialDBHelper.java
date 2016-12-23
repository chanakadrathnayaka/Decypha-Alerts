package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.CompanySizeDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.tickers.CompanyDBHelper;
import com.dfn.alerts.utils.CommonUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;
import static com.dfn.alerts.constants.IConstants.Delimiter.ASTERISK;
import static com.dfn.alerts.constants.IConstants.Delimiter.DOT;

/**
 * User: chathurag
 * Date: 12/30/2015
 * Time: 10.51 AM
 */
public class CompanyFinancialDBHelper {

    private final static String INCLUDED_TICKER_CLASSES = "62,64,65,61,63,66,68,67";
    private final static String TICKERS_ALIAS = "t";
    private final static String COMPANY_SIZE_ALIAS = "s";
    private final static String LISTED_COMPANY = "1";
    private final static String MULTI_LISTED_COMPANY = "2";
    private final static String UNLISTED_COMPANY = "3";
    private final static String ALL_LISTING_STATUS = "1,2,3";
    private final static String ALL_COMPANY_TYPES = "1,18,7";
    private final static String TEMP = "_TEMP";
    private final static String YEAR_TEMP = "_YEAR_TEMP";
    private final static String BKT_YEAR = "_BKT_YEAR";
    private final static String ACT_YEAR = "_ACT_YEAR";
    private final static String USD = "_USD";
    private final static String MIN_USD = "_MIN_USD";
    private final static String[] COMPANY_SIZE_TABLE_COLUMNS = new String[]{TOTAL_ASSETS_USD,
            TOTAL_ASSETS_MIN_USD, TOTAL_ASSETS_MAX_USD, TOTAL_ASSETS_ACT_YEAR, TOTAL_ASSETS_BKT_YEAR,
            TOTAL_LIABILITIES_USD, TOTAL_LIABILITIES_MIN_USD, TOTAL_LIABILITIES_MAX_USD, TOTAL_LIABILITIES_ACT_YEAR,
            TOTAL_LIABILITIES_BKT_YEAR, TOTAL_REVENUE_USD, TOTAL_REVENUE_MIN_USD, TOTAL_REVENUE_MAX_USD,
            TOTAL_REVENUE_ACT_YEAR, TOTAL_REVENUE_BKT_YEAR, NET_INCOME_USD, NET_INCOME_MIN_USD, NET_INCOME_MAX_USD,
            NET_INCOME_ACT_YEAR, NET_INCOME_BKT_YEAR, REVENUE_GROWTH_ACT, REVENUE_GROWTH_MIN, REVENUE_GROWTH_MAX};

    /**
     * This method generates search query for company size
     * @param dataRequestMap request parameters
     * @return search query
     */
    public static String getCompanySizeSearchQuery(Map<String, String> dataRequestMap){
        String companySizeType = dataRequestMap.get(IConstants.CompanyHome.COMPANY_SIZE_TYPE);
        int page = Integer.parseInt(dataRequestMap.get(IConstants.PAGE_INDEX));

        StringBuilder query = new StringBuilder();

        setSelectQuery(query, companySizeType);

        setJoinQuery(query);

        addCountryFilter(query, dataRequestMap);

        addCommonCompanyFilter(query, ALL_LISTING_STATUS);

        setSortingQuery(query, companySizeType, dataRequestMap);

        return DBUtils.getPaginationQuery(query.toString(), page, IConstants.DEFAULT_PAGE_SIZE_5, true, DBConstants.DatabaseTypes.DB_TYPE_ORACLE);
    }

    /**
     * This method adds join query with tickers table in company size
     * @param query current query
     */
    private static void setJoinQuery(StringBuilder query) {
        query.append(FROM).append(DBConstants.TablesORACLE.TABLE_COMPANY_SIZE).append(QUERY_SPACE).append(COMPANY_SIZE_ALIAS);
        query.append(QUERY_JOIN).append(DBConstants.TablesORACLE.TABLE_TICKERS).append(QUERY_SPACE).append(TICKERS_ALIAS);
        query.append(QUERY_ON).append(QUERY_BRACKET_OPEN);
        query.append(COMPANY_SIZE_ALIAS).append(DOT).append(DBConstants.DatabaseColumns.COMPANY_ID).append(QUERY_EQUAL);
        query.append(TICKERS_ALIAS).append(DOT).append(DBConstants.DatabaseColumns.COMPANY_ID);
        query.append(QUERY_BRACKET_CLOSE);
        query.append(QUERY_WHERE);
    }

    /**
     * add country filter
     * @param query current query
     * @param dataRequestMap dataRequest map
     */
    private static void addCountryFilter(StringBuilder query, Map<String, String> dataRequestMap) {
        String countries = dataRequestMap.get(IConstants.COUNTRIES);
        String column = TICKERS_ALIAS + DOT + DBConstants.DatabaseColumns.COUNTRY_CODE;
        DBUtils.addInQueryPhrase(query, column, countries, false, false);
    }

    /**
     * this method adds adds sorting query to company size query
     * @param query current query
     * @param companySizeType type
     * @param dataRequestMap request parameters
     */
    private static void setSortingQuery(StringBuilder query, String companySizeType, Map<String, String> dataRequestMap){
        String sortIndex = dataRequestMap.get(IConstants.SORT_FIELD);
        String sortOrder = dataRequestMap.get(IConstants.SORT_ORDER);
        String language = dataRequestMap.get(IConstants.LANGUAGE);

        query.append(QUERY_ORDER).append(getCompanySizeSortColumn(sortIndex, companySizeType, language)).append(QUERY_SPACE).append(sortOrder).append(QUERY_SPACE);
        query.append(QUERY_BRACKET_CLOSE).append(QUERY_WHERE).append(companySizeType).append(TEMP).append(QUERY_NOT_NULL);
    }
    /**
     * method to add select query for company size
     * @param query current query
     * @param type company size type
     */
    private static void setSelectQuery(StringBuilder query,String type){
        query.append(QUERY_SELECT_ALL_FROM).append(QUERY_BRACKET_OPEN);
        query.append(QUERY_SELECT).append(TICKERS_ALIAS).append(DOT).append(ASTERISK);
        for(String column: COMPANY_SIZE_TABLE_COLUMNS){
            query.append(IConstants.Delimiter.COMMA).append(COMPANY_SIZE_ALIAS).append(DOT).append(column);
        }
        addCaseQueryByCompanySizeType(query, type);
    }

    /**
     * method to add select query for company size
     *
     * @param companyId int
     */
    public static String getCompanySizeSearchQueryForCompany(int companyId) {
        StringBuilder query = new StringBuilder();
        query.append(QUERY_SELECT_ALL_FROM).append(DBConstants.TablesORACLE.TABLE_COMPANY_SIZE)
                .append(QUERY_WHERE).append(DBConstants.DatabaseColumns.COMPANY_ID).append(QUERY_EQUAL)
                .append(companyId);
        return query.toString();
    }

    /**
     * This method generates case query to select appropriate columns by given company size type
     * @param query current query
     * @param companySizeType selected type
     */
    private static void addCaseQueryByCompanySizeType(StringBuilder query, String companySizeType){

        String [] selectingTempColumns = new String[]{companySizeType + TEMP, companySizeType + YEAR_TEMP};
        String [][] selectingColumns = new String[][]{{companySizeType + USD, companySizeType + MIN_USD},
                                                      {companySizeType + ACT_YEAR, companySizeType + BKT_YEAR}};

        for(int index = 0; index < selectingTempColumns.length; index++){
            query.append(IConstants.Delimiter.COMMA);
            query.append(QUERY_CASE).append(QUERY_WHEN).append(QUERY_BRACKET_OPEN);
            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(USD).append(QUERY_NOT_NULL);
            query.append(QUERY_AND).append(companySizeType).append(ACT_YEAR).append(QUERY_NOT_NULL);
            query.append(QUERY_BRACKET_CLOSE);

            query.append(QUERY_AND);
            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(BKT_YEAR).append(QUERY_NULL);
            query.append(QUERY_OR);
            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(ACT_YEAR).append(QUERY_GREATER_OR_EQUAL_THAN);
            query.append(companySizeType).append(BKT_YEAR);
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_THEN).append(selectingColumns[index][0]);

            query.append(QUERY_WHEN).append(QUERY_BRACKET_OPEN);
            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(USD).append(QUERY_NULL);
            query.append(QUERY_OR).append(companySizeType).append(ACT_YEAR).append(QUERY_NULL);
            query.append(QUERY_BRACKET_CLOSE);

            query.append(QUERY_OR);

            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(BKT_YEAR).append(QUERY_NOT_NULL);
            query.append(QUERY_AND);
            query.append(QUERY_BRACKET_OPEN);
            query.append(companySizeType).append(ACT_YEAR).append(QUERY_LESS_THAN);
            query.append(companySizeType).append(BKT_YEAR);
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_BRACKET_CLOSE);

            query.append(QUERY_THEN).append(selectingColumns[index][1]);
            query.append(QUERY_END).append(QUERY_AS).append(selectingTempColumns[index]);
        }
    }

    /**
     * adding common company filter
     * @param query current query
     * @param listingStatus listing status comma separated
     */
    private static void addCommonCompanyFilter(StringBuilder query, String listingStatus) {
        List<String> statusList = Arrays.asList(listingStatus.split(Character.toString(IConstants.Delimiter.COMMA)));
        boolean unlistedAdded = false;

        query.append(QUERY_AND);
        query.append(TICKERS_ALIAS + DOT + COMPANY_ID + DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN + 0);
        query.append(QUERY_AND);
        query.append(QUERY_BRACKET_OPEN);
        if (statusList.contains(UNLISTED_COMPANY)) {
            query.append(QUERY_BRACKET_OPEN);
            DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + TICKER_SERIAL, String.valueOf(0), true, false);
            DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + LISTING_STATUS, UNLISTED_COMPANY, true, true);
            query.append(QUERY_BRACKET_CLOSE);
            unlistedAdded = true;
        }
        if (statusList.contains(LISTED_COMPANY) || statusList.contains(MULTI_LISTED_COMPANY)) {
            boolean listedAdded = false;
            if (unlistedAdded) {
                query.append(QUERY_OR);
            }
            query.append(QUERY_BRACKET_OPEN);
            query.append(TICKERS_ALIAS + DOT + TICKER_SERIAL + DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN + 0);
            DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + STATUS, String.valueOf(1), true, true);
            DBUtils.addInQueryPhrase(query, TICKERS_ALIAS + DOT + TICKER_CLASS_L3, INCLUDED_TICKER_CLASSES, true, true);
            query.append(QUERY_AND);
            query.append(QUERY_BRACKET_OPEN);
            if (statusList.contains(LISTED_COMPANY)) {
                DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + LISTING_STATUS, LISTED_COMPANY, true, false);
                listedAdded = true;
            }
            if (statusList.contains(MULTI_LISTED_COMPANY)) {
                if (listedAdded) {
                    query.append(QUERY_OR);
                }
                query.append(QUERY_BRACKET_OPEN);
                DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + LISTING_STATUS, MULTI_LISTED_COMPANY, true, false);
                DBUtils.addEqualQueryPhrase(query, TICKERS_ALIAS + DOT + IS_MAIN_STOCK, String.valueOf(1), true, true);
                query.append(QUERY_BRACKET_CLOSE);
            }
            query.append(QUERY_BRACKET_CLOSE);
            query.append(QUERY_BRACKET_CLOSE);
        }
        query.append(QUERY_BRACKET_CLOSE);
    }

    /**
     * Method to extract required data from result set for company size request
     * @param resultSet query result set
     * @param supportedLanguages supported languages list
     * @return CompanySizeDTO for result set
     * @throws SQLException
     */
    public static CompanySizeDTO getCompanySizeData(ResultSet resultSet, List<String> supportedLanguages) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        CompanySizeDTO companySizeDTO = new CompanySizeDTO();
        CompanyDBHelper.setCompanyData(companySizeDTO, resultSet, supportedLanguages);

        companySizeDTO.setTotalRevenue(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_USD));
        companySizeDTO.setTotalRevenueMin(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MIN_USD));
        companySizeDTO.setTotalRevenueMax(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MAX_USD));
        companySizeDTO.setTotalRevenueActYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_ACT_YEAR));
        companySizeDTO.setTotalRevenueBktYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_BKT_YEAR));
        if (hasColumn(metaData, DBConstants.DatabaseColumns.TOTAL_REVENUE_YEAR_TEMP)) {
            companySizeDTO.setTotalRevenueEffectiveYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_YEAR_TEMP));
        }

        companySizeDTO.setTotalAssets(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_USD));
        companySizeDTO.setTotalAssetsMin(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_MIN_USD));
        companySizeDTO.setTotalAssetsMax(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_MAX_USD));
        companySizeDTO.setTotalAssetsActYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_ASSETS_ACT_YEAR));
        companySizeDTO.setTotalAssetsBktYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_ASSETS_BKT_YEAR));
        if (hasColumn(metaData, DBConstants.DatabaseColumns.TOTAL_ASSETS_YEAR_TEMP)) {
            companySizeDTO.setTotalAssetsEffectiveYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_ASSETS_YEAR_TEMP));
        }

        return companySizeDTO;
    }

    /**
     * Method to extract required data from result set for company size request for companyId
     *
     * @param resultSet query result set
     * @return CompanySizeDTO for result set
     * @throws SQLException
     */
    public static CompanySizeDTO getCompanySizeDataForCompany(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        CompanySizeDTO companySizeDTO = new CompanySizeDTO();

        companySizeDTO.setTotalRevenue(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_USD));
        companySizeDTO.setTotalRevenueMin(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MIN_USD));
        if (resultSet.getString(DBConstants.DatabaseColumns.TOTAL_REVENUE_MIN) != null) {
            companySizeDTO.setTotalRevenueMinCompanyCurrency(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MIN));
        }

        companySizeDTO.setTotalRevenueMax(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MAX_USD));
        if (resultSet.getString(DBConstants.DatabaseColumns.TOTAL_REVENUE_MAX) != null) {
            companySizeDTO.setTotalRevenueMaxCompanyCurrency(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_REVENUE_MAX));
        }
        if (resultSet.getString(DBConstants.DatabaseColumns.TOTAL_REVENUE_ACT_YEAR) != null) {
            companySizeDTO.setTotalRevenueActYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_ACT_YEAR));
        }
        if (resultSet.getString(DBConstants.DatabaseColumns.TOTAL_REVENUE_BKT_YEAR) != null) {
            companySizeDTO.setTotalRevenueBktYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_BKT_YEAR));
        }
        if(hasColumn(metaData, DBConstants.DatabaseColumns.TOTAL_REVENUE_YEAR_TEMP)){
            companySizeDTO.setTotalRevenueEffectiveYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_REVENUE_YEAR_TEMP));
        }

        companySizeDTO.setTotalAssets(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_USD));
        companySizeDTO.setTotalAssetsMin(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_MIN_USD));
        companySizeDTO.setTotalAssetsMax(resultSet.getDouble(DBConstants.DatabaseColumns.TOTAL_ASSETS_MAX_USD));
        companySizeDTO.setTotalAssetsActYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_ASSETS_ACT_YEAR));
        companySizeDTO.setTotalAssetsBktYear(resultSet.getInt(DBConstants.DatabaseColumns.TOTAL_ASSETS_BKT_YEAR));
        if(hasColumn(metaData, DBConstants.DatabaseColumns.TOTAL_ASSETS_YEAR_TEMP)){
            companySizeDTO.setTotalAssetsEffectiveYear(resultSet.getInt( DBConstants.DatabaseColumns.TOTAL_ASSETS_YEAR_TEMP));
        }

        return companySizeDTO;
    }

    /**
     * method to check result set contains a column
     * @param metaData result set meta data
     * @param columnName check column
     * @return hasColumn
     * @throws SQLException
     */
    private static boolean hasColumn(ResultSetMetaData metaData, String columnName) throws SQLException {
        int columns = metaData.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(metaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to get relevant sort column for company size
     * @param sidx sort index
     * @param companySizeType company size data type
     * @param language selected language
     * @return required column
     */
    private static String getCompanySizeSortColumn(String sidx, String companySizeType, String language) {

        String sortCol = null;
        if(!CommonUtils.isNullOrEmptyString(sidx)){
            IConstants.CompanyFinancialsConstants.CompanySizeColumnMap column = IConstants.CompanyFinancialsConstants.CompanySizeColumnMap.valueOf(sidx);
            switch (column) {
            case comp:
            case inds:
                sortCol = column.getDefaultValue() + language;
                break;
            case assets:
            case assetsMax:
            case assetsMin:
            case assets_year:
            case revenue:
            case revenueMax:
            case revenueMin:
            case revenue_year:
                sortCol = column.getDefaultValue();
            }
        } else {
            sortCol = companySizeType + TEMP;
        }
        return sortCol;
    }
}
