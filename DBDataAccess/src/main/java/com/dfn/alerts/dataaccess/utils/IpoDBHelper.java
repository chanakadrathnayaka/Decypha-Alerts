package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.IpoDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 1/30/14
 * Time: 2:41 PM
 */
public class IpoDBHelper {

    //region IMDB sync

    //region IMDB UPDATE

    private static final String QUERY_IPO_UPDATE = "UPDATE IPO_MASTER SET ";

    private static final String IPO_UPDATE_COLUMNS =
            "COMPANY_ID=?, ANNOUNCEMENT_DATE=?, SUBSCRIPTION_TYPE=?, IPO_START_DATE=?, IPO_END_DATE=?, VOLUME=?, ISSUE_PRICE=?," +
                    "ANNOUNCEMENT_ID=?, NEWS_ID=?, COVERAGE_PERCENTAGE=?, COVERAGE_VOLUME=?, CURRENCY=?, IPO_STATUS=?, EXCHANGE=?," +
                    "CUSTODY_SETTLEMENT_DATE=?, REFUND_DATE=?, LAST_UPDATED_TIME=?, UPCOMING_NEWS_ID=?, DETAILS_RELEASED_NEWS_ID=?," +
                    "ACTIVE_NEWS_ID=?, TO_BE_TRADED_NEWS_ID=?, TRADED_NEWS_ID=?, WITHDRAWN_NEWS_ID=?, DOCUMENT_ID=?, DOCUMENT_URL=?, MANAGER_ID_LIST=?, " +
                    "TICKER_SERIAL=?, PRICE_ON_TRADING_DATE=?, STATUS=?, TRADING_DATE=?, CANCELLATION_DATE=?, DETAILS_DISCLOSING_DATE=?, LATEST_DATE=?, " +
                    "COUNTRY_CODE=?,COMPANY_NAME_LAN=?,GICS_L2_CODE=?, GICS_L3_CODE=?, GICS_L4_CODE=?, GICS_L2_LAN=?, GICS_L3_LAN=?, GICS_L4_LAN=?,LAST_SYNC_TIME=? ";


    /**
     * add language specific columns to update query
     * @param sqlQuery update query
     * @param languages supported languages
     */
    private static void addIPOLangSpecificColumnsToUpdateSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String language : languages) {
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_STATUS_DESC).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_TYPE_DESC).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L2_DESC).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L3_DESC).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L4_DESC).append(language).append("=").append("?");
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_COUNTRY_DESC).append(language).append("=").append("?");
        }
    }

    //endregion

    //region IMDB INSERT

    private static final String IPO_COLUMNS = "COMPANY_ID, ANNOUNCEMENT_DATE, SUBSCRIPTION_TYPE, IPO_START_DATE, IPO_END_DATE, VOLUME, ISSUE_PRICE, " +
            "ANNOUNCEMENT_ID, NEWS_ID, COVERAGE_PERCENTAGE, COVERAGE_VOLUME, CURRENCY, IPO_STATUS, EXCHANGE," +
            "CUSTODY_SETTLEMENT_DATE, REFUND_DATE, LAST_UPDATED_TIME, UPCOMING_NEWS_ID, DETAILS_RELEASED_NEWS_ID," +
            "ACTIVE_NEWS_ID, TO_BE_TRADED_NEWS_ID, TRADED_NEWS_ID, WITHDRAWN_NEWS_ID, DOCUMENT_ID, DOCUMENT_URL, MANAGER_ID_LIST," +
            "TICKER_SERIAL, PRICE_ON_TRADING_DATE, STATUS, TRADING_DATE, CANCELLATION_DATE, DETAILS_DISCLOSING_DATE, LATEST_DATE, " +
            "COUNTRY_CODE, COMPANY_NAME_LAN, GICS_L2_CODE, GICS_L3_CODE,GICS_L4_CODE, GICS_L2_LAN, GICS_L3_LAN, GICS_L4_LAN,LAST_SYNC_TIME";

    private static final String IPO_INSERT_COLUMN_PLACEHOLDERS = "?,?,?,?,?,?,?," +
            "?,?,?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?,?,?," +
            "?,?,?,?,?,?,?," +
            "?,?,?,?,?,?,?,?,?";

    private static final String QUERY_IPO_INSERT = "INSERT INTO IPO_MASTER ";

    /**
     * add ipo language related columns to insert query
     * @param sqlQuery insert query
     * @param languages supported languages
     */
    private static void addIPOLangSpecificColumnsToInsertSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String language : languages) {
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_STATUS_DESC).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_TYPE_DESC).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L4_DESC).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L3_DESC).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L2_DESC).append(language);
            sqlQuery.append(",").append(DBConstants.LangSpecificDatabaseColumns.IPO_COUNTRY_DESC).append(language);
        }
    }

    /**
     * add placeholders for ipo language related columns to insert query
     * @param sqlQuery insert query
     * @param languages supported languages
     */
    private static void addIPOLangSpecificPlaceHoldersToInsertSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String ignored : languages) {
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
            sqlQuery.append(",").append("?");
        }
    }

    //endregion
    /**
     * add values to insert/update prepared statement
     * IMPORTANT - maintain order
     * @param preparedStatement prepared statement
     * @param index column index
     * @param ipoDTO data object
     * @param languages supported languages
     * @return prepared statement
     * @throws SQLException exceptions
     */
    private static int addIPOLangSpecificColumnsToPreparedStatement(PreparedStatement preparedStatement, int index, IpoDTO ipoDTO, List<String> languages) throws SQLException {
        for (String language : languages) {
            preparedStatement.setString(index++, ipoDTO.getStatusDesc(language));
            preparedStatement.setString(index++, ipoDTO.getTypeDesc(language));
            preparedStatement.setString(index++, ipoDTO.getCompanyName(language));
            preparedStatement.setString(index++, ipoDTO.getGICSL2(language));
            preparedStatement.setString(index++, ipoDTO.getGICSL3(language));
            preparedStatement.setString(index++, ipoDTO.getGICSL4(language));
            preparedStatement.setString(index++, ipoDTO.getCountryName(language));
        }
        return index;
    }

    //endregion

    //region IPO Obj populate

    /**
     * Method to set IPO data and return object
     *
     * @param results result set
     * @return IpoDTO object
     * @throws SQLException
     */
    public static IpoDTO setIPOData(ResultSet results, List<String> languages) throws SQLException {
        IpoDTO ipoDTO = new IpoDTO();

        ipoDTO.setCompanyId(results.getInt(DBConstants.DatabaseColumns.COMPANY_ID));
        ipoDTO.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
        ipoDTO.setGicsL2Code(results.getString(DBConstants.DatabaseColumns.GICSL2_CODE));
        ipoDTO.setGicsL3Code(results.getString(DBConstants.DatabaseColumns.GICSL3_CODE));
        ipoDTO.setGicsL4Code(results.getString(DBConstants.DatabaseColumns.GICSL4_CODE));

        ipoDTO.setId(results.getInt(DBConstants.DatabaseColumns.IPO_ID));
        ipoDTO.setAnnounceDate(results.getDate(DBConstants.DatabaseColumns.ANNOUNCEMENT_DATE));
        ipoDTO.setSubscriptionType(results.getInt(DBConstants.DatabaseColumns.SUBSCRIPTION_TYPE));
        ipoDTO.setStartDate(results.getDate(DBConstants.DatabaseColumns.IPO_START_DATE));
        ipoDTO.setEndDate(results.getDate(DBConstants.DatabaseColumns.IPO_END_DATE));
        ipoDTO.setIpoVolume(results.getDouble(DBConstants.DatabaseColumns.VOLUME));
        ipoDTO.setIssuePrice(results.getDouble(DBConstants.DatabaseColumns.ISSUE_PRICE));
        ipoDTO.setAnnouncementId(results.getInt(DBConstants.DatabaseColumns.ANNOUNCEMENT_ID));
        ipoDTO.setNewsId(results.getInt(DBConstants.DatabaseColumns.NEWS_ID));
        ipoDTO.setCoveragePercentage(results.getDouble(DBConstants.DatabaseColumns.COVERAGE_PERCENTAGE));
        ipoDTO.setCoverageVolume(results.getDouble(DBConstants.DatabaseColumns.COVERAGE_VOLUME));
        ipoDTO.setCurrencyId(results.getString(DBConstants.DatabaseColumns.CURRENCY));
        ipoDTO.setIpoStatus(results.getInt(DBConstants.DatabaseColumns.IPO_STATUS));
        ipoDTO.setSourceId(results.getString(DBConstants.DatabaseColumns.EXCHANGE));
        ipoDTO.setCustodySettlementDate(results.getDate(DBConstants.DatabaseColumns.CUSTODY_SETTLEMENT_DATE));
        ipoDTO.setRefundDate(results.getDate(DBConstants.DatabaseColumns.REFUND_DATE));
        ipoDTO.setUpcomingNewsId(results.getInt(DBConstants.DatabaseColumns.UPCOMING_NEWS_ID));
        ipoDTO.setDetailsReleasedNewsId(results.getInt(DBConstants.DatabaseColumns.DETAILS_RELEASED_NEWS_ID));
        ipoDTO.setActiveNewsId(results.getInt(DBConstants.DatabaseColumns.ACTIVE_NEWS_ID));
        ipoDTO.setToBeTradedNewsId(results.getInt(DBConstants.DatabaseColumns.TO_BE_TRADED_NEWS_ID));
        ipoDTO.setTradedNewsId(results.getInt(DBConstants.DatabaseColumns.TRADED_NEWS_ID));
        ipoDTO.setWithdrawnNewsId(results.getInt(DBConstants.DatabaseColumns.WITHDRAWN_NEWS_ID));
        ipoDTO.setDocumentId(results.getString(DBConstants.DatabaseColumns.DOCUMENT_ID));
        ipoDTO.setDocumentURL(results.getString(DBConstants.DatabaseColumns.DOCUMENT_URL));
        ipoDTO.setManagerIds(results.getString(DBConstants.DatabaseColumns.MANAGER_ID_LIST));
        ipoDTO.setLastUpdatedTime(results.getDate(DBConstants.DatabaseColumns.LAST_UPDATED_TIME));
        ipoDTO.setTickerSerial(results.getInt(DBConstants.DatabaseColumns.TICKER_SERIAL));
        ipoDTO.setPriceOnTradingDate(results.getDouble(DBConstants.DatabaseColumns.PRICE_ON_TRADING_DATE));
        ipoDTO.setStatus(results.getInt(DBConstants.DatabaseColumns.STATUS));
        ipoDTO.setTradingDate(results.getDate(DBConstants.DatabaseColumns.TRADING_DATE));
        ipoDTO.setCancellationDate(results.getDate(DBConstants.DatabaseColumns.CANCELLATION_DATE));
        ipoDTO.setDetailsDisclosingDate(results.getDate(DBConstants.DatabaseColumns.DETAILS_DISCLOSING_DATE));
        ipoDTO.setLatestDate(results.getDate(DBConstants.DatabaseColumns.LATEST_DATE));
        ipoDTO.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));

        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_STATUS_DESC, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_TYPE_DESC, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L2_DESC, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L3_DESC, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L4_DESC, results, languages);
        addLanguageSpecificIPOData(ipoDTO, DBConstants.LangSpecificDatabaseColumns.IPO_COUNTRY_DESC, results, languages);

        return ipoDTO;
    }

    /**
     * add the lang specific values to data object
     * @param ipoDTO data object
     * @param columnPrefix column name prefix
     * @param resultSet result set
     * @param languages supported exchanges
     * @throws SQLException exceptions
     */
    private static void addLanguageSpecificIPOData(IpoDTO ipoDTO, String columnPrefix, ResultSet resultSet, List<String> languages) throws SQLException {
        for (String language : languages) {
            ipoDTO.addLangSpecificDescs(language, columnPrefix, resultSet.getString(columnPrefix + language));
        }
    }

    //endregion

    //region IN_MEMORY_SEARCH

    /**
     * add pagination to query
     * @param queryBuilder    appender
     * @param pageId          Page index
     * @param pageSize        Page size
     * @param bindExtraRecord whether to bind extra record or not
     * @param dbType          derby or h2
     * @param sortType       Sort column
     * @param sortOrder      Sort order
     */
    public static void addPagination(StringBuilder queryBuilder, int pageId, int pageSize, boolean bindExtraRecord, int dbType,
                                     String sortType, String language, String sortOrder) {
        language = language.toUpperCase();
        if (sortType != null && !sortType.trim().isEmpty()) {
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_ORDER);
            queryBuilder.append(getSortTypeColumn(sortType, language));
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_SPACE);
            queryBuilder.append(sortOrder == null || sortOrder.isEmpty() ? DBConstants.CommonDatabaseParams.QUERY_DESC_ORDER : sortOrder);
        }

    }

    /**
     * add filters(where clauses) to query
     * @param ipoId          unique id
     * @param countryList    comma separated list of country codes
     * @param type           IPO type
     * @param status         IPO status
     * @param gics           IPO industry
     * @param ipoStartDate   IPO start date
     * @param ipoEndDate     IPO end date
     * @param latestDateFrom latest date - start
     * @param latestDateTo   latest date - end
     * @param dbType         derby or h2
     *                       SELECT * FROM IPO_MASTER WHERE COMPANY_ID <> 0 AND STATUS = 1
     *                       AND IPO_ID = ipoId
     *                       AND COUNTRY_CODE IN (countryList)
     *                       AND SUBSCRIPTION_TYPE in (type)
     *                       AND IPO_STATUS in (status)
     *                       AND GICS_L4_CODE in (industry)
     *                       AND IPO_START_DATE >= ipoStartDate
     *                       AND IPO_END_DATE >= ipoEndDate
     *                       AND LATEST_DATE between latestDateFrom AND latestDateTo
     *                       ORDER BY sortType sortOrder
     *                       OFFSET ROWS (pageId * pageSize) FETCH NEXT pageSize ROWS ONLY
     */
    public static void addFilters(StringBuilder queryBuilder, int ipoId, String countryList, String type, String status, Map<String, String> gics,
                                  String ipoStartDate, String ipoEndDate, String latestDateFrom, String latestDateTo, int dbType) {
        if (ipoId > 0) {
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.IPO_ID, ipoId + "", true, true);
        }

        if (countryList != null && !countryList.isEmpty()) {
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.COUNTRY_CODE, countryList, false, true);
        }

        if (type != null && !type.isEmpty()) {
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.SUBSCRIPTION_TYPE, type, true, true);
        }

        if (status != null && !status.isEmpty()) {
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.IPO_STATUS, status, true, true);
        }

        if (gics != null) {
            for (String key : gics.keySet()) {
                String gicsCodes = gics.get(key);
                if (gicsCodes != null && !gicsCodes.isEmpty()) {
                    String subStringColumnName;
                    switch (IConstants.GicsLevel.valueOf(Integer.parseInt(key))) {
                        case GICS1:
                            subStringColumnName = DBUtils.getSubStringColumnName(DBConstants.DatabaseColumns.GICS_L4_CODE, 1, 2);
                            break;
                        case GICS2:
                            subStringColumnName = DBUtils.getSubStringColumnName(DBConstants.DatabaseColumns.GICS_L4_CODE, 1, 4);
                            break;
                        case GICS3:
                            subStringColumnName = DBUtils.getSubStringColumnName(DBConstants.DatabaseColumns.GICS_L4_CODE, 1, 6);
                            break;
                        default:
                            subStringColumnName = DBConstants.DatabaseColumns.GICS_L4_CODE;
                            break;
                    }
                    DBUtils.addInQueryPhrase(queryBuilder, subStringColumnName, gicsCodes, false, true);
                }
            }
        }

        if (ipoStartDate != null && !ipoStartDate.isEmpty()) {
            queryBuilder.append(DBConstants.AND);
            queryBuilder.append(DBConstants.DatabaseColumns.IPO_START_DATE);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN);
            queryBuilder.append(DBUtils.getDateFunctionQuery(ipoStartDate, DBConstants.dateFormat, dbType));
        }

        if (ipoEndDate != null && !ipoEndDate.isEmpty()) {
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.IPO_END_DATE);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN);
            queryBuilder.append(DBUtils.getDateFunctionQuery(ipoEndDate, DBConstants.dateFormat, dbType));
        }

        if (latestDateFrom != null && !latestDateFrom.isEmpty()) {
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.LATEST_DATE);
            if (latestDateTo != null && !latestDateTo.isEmpty()) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BETWEEN);
                queryBuilder.append(DBUtils.getDateFunctionQuery(latestDateFrom, DBConstants.dateFormat, dbType));
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
                queryBuilder.append(DBUtils.getDateFunctionQuery(latestDateTo, DBConstants.dateFormat, dbType));
            } else {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN);
                queryBuilder.append(DBUtils.getDateFunctionQuery(latestDateFrom, DBConstants.dateFormat, dbType));
            }
        } else if (latestDateTo != null && !latestDateTo.isEmpty()) {
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_LESS_OR_EQUAL_THAN);
            queryBuilder.append(DBUtils.getDateFunctionQuery(latestDateTo, DBConstants.dateFormat, dbType));
        }

    }

    /**
     * map column name to json name
     * @param sortType json name - IConstants
     * @param language supported languages
     * @return database column
     */
    private static String getSortTypeColumn(String sortType, String language) {
        if(IConstants.LATEST_DATE.equalsIgnoreCase(sortType)){
            return DBConstants.DatabaseColumns.LATEST_DATE;
        }else if(IConstants.COUNTRY_DESC.equalsIgnoreCase(sortType)){
            return DBConstants.LangSpecificDatabaseColumns.IPO_COUNTRY_DESC + language;
        }else if (IConstants.COMPANY.equalsIgnoreCase(sortType)) {
            return DBConstants.LangSpecificDatabaseColumns.IPO_COMPANY_NAME + language;
        }else if (IConstants.GICSL3_DESC.equalsIgnoreCase(sortType)) {
            return DBConstants.LangSpecificDatabaseColumns.IPO_GICS_L3_DESC + language;
        }else if (IConstants.IPO_TYPE_DESC.equalsIgnoreCase(sortType)) {
            return DBConstants.LangSpecificDatabaseColumns.IPO_TYPE_DESC + language;
        }else if (IConstants.IPO_STATUS_DESC.equalsIgnoreCase(sortType)) {
            return DBConstants.LangSpecificDatabaseColumns.IPO_STATUS_DESC + language;
        }
        return sortType;
    }
    //endregion
}
