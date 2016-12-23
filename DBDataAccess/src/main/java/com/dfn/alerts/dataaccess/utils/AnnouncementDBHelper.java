package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.Announcements;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 3/14/14
 * Time: 1:21 PM
 */
public class AnnouncementDBHelper {

    private static final Logger LOG = LogManager.getLogger(AnnouncementDBHelper.class);
    private static final String annIdInPlaceHolder = "#id#";

    //region ANNOUNCEMENT IMDB UPDATE

    //region ANNOUNCEMENT INSERT

    private static final String ANNOUNCEMENT_COLUMNS = "ANN_DATE, SOURCE_ID, TICKER_ID, SECTOR_CODE, TICKER_SERIAL, ANN_SOURCE_SERIAL, " +
            "PRIORITY_ID, STATUS, SEQ_ID, GICS_L3_CODE, GICS_L2_CODE, DCMS_IDS";

    private static final String ANNOUNCEMENT_INSERT_COLUMN_PLACEHOLDERS = "?,?,?,?,?,?," +
            "?,?,?,?,?,?";

    private static final String QUERY_ANNOUNCEMENT_INSERT = "INSERT INTO ANNOUNCEMENTS ";

    /**
     * get announcement insert query
     * @param languages supported languages
     * @return insert query
     */
    public static RequestDBDTO getInsertQuery(List<String> languages, List<Integer> data, Map<Integer, Announcements> announcements) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();

        StringBuilder builder = new StringBuilder(QUERY_ANNOUNCEMENT_INSERT).append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN).append(ANNOUNCEMENT_COLUMNS);
        addAnnouncementLangSpecificColumnsToInsertSQL(builder, languages);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
        builder.append(DBConstants.CommonDatabaseParams.SQL_VALUES).append(ANNOUNCEMENT_INSERT_COLUMN_PLACEHOLDERS);
        addAnnouncementLangSpecificPlaceHoldersToInsertSQL(builder, languages);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

        requestDBDTO.setQuery(builder.toString());
        requestDBDTO.setSupportedLang(languages);
        requestDBDTO.setData(new Object[] {data, announcements});
        return requestDBDTO;
    }

    /**
     * add announcement language related columns to insert query
     * @param sqlQuery insert query
     * @param languages supported languages
     */
    private static void addAnnouncementLangSpecificColumnsToInsertSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String language : languages) {
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.LangSpecificDatabaseColumns.ANN_HEADLINE).append(language);
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.LangSpecificDatabaseColumns.ANN_URL).append(language);
        }
    }

    /**
     * add placeholders for announcement language related columns to insert query
     * @param sqlQuery insert query
     * @param languages supported languages
     */
    private static void addAnnouncementLangSpecificPlaceHoldersToInsertSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String ignored : languages) {
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
        }
    }

    //endregion

    //region ANNOUNCEMENT UPDATE

    private static final String QUERY_ANNOUNCEMENT_UPDATE = "UPDATE ANNOUNCEMENTS SET ";

    private static final String ANNOUNCEMENT_UPDATE_COLUMNS =
            "ANN_DATE=?, SOURCE_ID=?, TICKER_ID=?, SECTOR_CODE=?, TICKER_SERIAL=?, ANN_SOURCE_SERIAL=?, " +
                    "PRIORITY_ID=?, STATUS=?, SEQ_ID=?, GICS_L3_CODE=?, GICS_L2_CODE=?, DCMS_IDS=?";

    /**
     * get update query for ANNOUNCEMENTS
     * @param languages supported languages
     * @return update query
     */
    public static RequestDBDTO getUpdateQuery(List<String> languages, List<Integer> data, Map<Integer, Announcements> announcements) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();

        StringBuilder builder = new StringBuilder(QUERY_ANNOUNCEMENT_UPDATE).append(ANNOUNCEMENT_UPDATE_COLUMNS);
        addAnnouncementLangSpecificColumnsToUpdateSQL(builder, languages);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_WHERE).append(DBConstants.AnnouncementDatabaseColumns.ANN_ID).
                append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);

        requestDBDTO.setQuery(builder.toString());
        requestDBDTO.setSupportedLang(languages);
        requestDBDTO.setData(new Object[] {data, announcements});
        return requestDBDTO;
    }

    /**
     * add language specific columns to update query
     * @param sqlQuery update query
     * @param languages supported languages
     */
    private static void addAnnouncementLangSpecificColumnsToUpdateSQL(StringBuilder sqlQuery, List<String> languages) {
        for (String language : languages) {
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.LangSpecificDatabaseColumns.ANN_HEADLINE).
                    append(language).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
            sqlQuery.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.LangSpecificDatabaseColumns.ANN_URL).
                    append(language).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
        }
    }

    //endregion

    /**
     * set prepared statement values for update sources
     *
     * @param preparedStatement prepared statement
     * @throws SQLException exceptions
     */
    public static void setParametersToPreparedStatement(PreparedStatement preparedStatement, Announcements announcement, List<String> languages) throws SQLException {
        int index = 1;
        // announcement data
        preparedStatement.setTimestamp(index++, announcement.getDate() != null ? announcement.getDate() : null);
        preparedStatement.setString(index++, announcement.getSourceId());
        preparedStatement.setString(index++, announcement.getTickerId());
        preparedStatement.setString(index++, announcement.getSectorCode());
        if(announcement.getTickerSerial() != null && announcement.getTickerSerial() > 0){
            preparedStatement.setLong(index++, announcement.getTickerSerial());
        }else{
            preparedStatement.setNull(index++, java.sql.Types.INTEGER);
        }
        if(announcement.getAnnSourceSerial() != null){
            preparedStatement.setInt(index++, announcement.getAnnSourceSerial());
        }else{
            preparedStatement.setNull(index++, java.sql.Types.INTEGER);
        }
        if(announcement.getPriorityId() != null){
            preparedStatement.setInt(index++, announcement.getPriorityId());
        }else{
            preparedStatement.setNull(index++, java.sql.Types.INTEGER);
        }
        preparedStatement.setString(index++, announcement.getStatus());
        preparedStatement.setInt(index++, announcement.getSeqId());
        preparedStatement.setString(index++, announcement.getGicsL3Code());
        preparedStatement.setString(index++, announcement.getGicsL2Code());
        if(announcement.getDcmsId() != null){
            preparedStatement.setString(index++, announcement.getDcmsId());
        }else{
            preparedStatement.setNull(index++, java.sql.Types.INTEGER);
        }

        index = addAnnouncementLangSpecificColumnsToPreparedStatement(preparedStatement, index, announcement, languages);
        preparedStatement.setInt(index, announcement.getAnnouncementId());
    }

    /**
     * add values to insert/update prepared statement
     * IMPORTANT - maintain order
     * @param preparedStatement prepared statement
     * @param index column index
     * @param announcements data object
     * @param languages supported languages
     * @return prepared statement
     * @throws SQLException exceptions
     */
    private static int addAnnouncementLangSpecificColumnsToPreparedStatement(PreparedStatement preparedStatement, int index, Announcements announcements, List<String> languages) throws SQLException {
        for (String language : languages) {
            preparedStatement.setString(index++, announcements.getAnnouncementHeadline(language));
            preparedStatement.setString(index++, announcements.getAnnouncementUrl(language));
        }
        return index;
    }

    //endregion

    //region ANNOUNCEMENT_POPULATE

    /**
     * Method to set announcement data and return object
     *
     * @param results result set
     * @return Announcements object
     * @throws SQLException
     */
    public static Announcements setAnnouncementData(ResultSet results, List<String> languages) throws SQLException {
        Announcements announcements = new Announcements();
        announcements.setDate(results.getTimestamp(DBConstants.AnnouncementDatabaseColumns.ANN_DATE));
        announcements.setSourceId(results.getString(DBConstants.AnnouncementDatabaseColumns.SOURCE_ID));
        announcements.setTickerId(results.getString(DBConstants.AnnouncementDatabaseColumns.TICKER_ID));
        announcements.setSectorCode(results.getString(DBConstants.AnnouncementDatabaseColumns.SECTOR_CODE));
        announcements.setTickerSerial(results.getLong(DBConstants.AnnouncementDatabaseColumns.TICKER_SERIAL));
        announcements.setAnnSourceSerial(results.getInt(DBConstants.AnnouncementDatabaseColumns.ANN_SOURCE_SERIAL));
        announcements.setPriorityId(results.getInt(DBConstants.AnnouncementDatabaseColumns.PRIORITY_ID));
        announcements.setStatus(results.getString(DBConstants.AnnouncementDatabaseColumns.STATUS));
        announcements.setSeqId(results.getInt(DBConstants.AnnouncementDatabaseColumns.SEQ_ID));
        announcements.setGicsL3Code(results.getString(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L3_CODE));
        announcements.setGicsL2Code(results.getString(DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L2_CODE));
        announcements.setDcmsId(results.getString(DBConstants.AnnouncementDatabaseColumns.DCMS_IDS));

        addLanguageSpecificAnnouncementData(announcements, DBConstants.LangSpecificDatabaseColumns.ANN_HEADLINE, results, languages);
        addLanguageSpecificAnnouncementData(announcements, DBConstants.LangSpecificDatabaseColumns.ANN_URL, results, languages);

        announcements.setAnnouncementId(results.getInt(DBConstants.AnnouncementDatabaseColumns.ANN_ID));
        return announcements;
    }

    /**
     * add the lang specific values to data object
     * @param announcements data object
     * @param columnPrefix column name prefix
     * @param resultSet result set
     * @param languages supported exchanges
     * @throws SQLException exceptions
     */
    private static void addLanguageSpecificAnnouncementData(Announcements announcements, String columnPrefix, ResultSet resultSet, List<String> languages) throws SQLException {
        for (String language : languages) {
            announcements.addLangSpecificDescs(language, columnPrefix, resultSet.getString(columnPrefix + language));
        }
    }

    //endregion

    //region ANNOUNCEMENT MIGRATION

    private static final String LOAD_EXCESS_ANNOUNCEMENT_QUERY = "SELECT * FROM ANNOUNCEMENTS ORDER BY ANN_DATE DESC ,SEQ_ID DESC  OFFSET ? ROWS";

    private static final String LOAD_ANNOUNCEMENT_HISTORY_QUERY = "SELECT MIN(ANN_DATE) FROM ANNOUNCEMENTS WHERE STATUS = 'N' ";

    /**
     * get announcement insert query for JDBC
     * @param languages supported languages
     * @param data announcements to insert
     * @return insert query
     */
    public static RequestDBDTO getInsertQuery(List<String> languages, List<Announcements> data) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();

        StringBuilder builder = new StringBuilder(QUERY_ANNOUNCEMENT_INSERT).append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN).append(ANNOUNCEMENT_COLUMNS);
        addAnnouncementLangSpecificColumnsToInsertSQL(builder, languages);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
        builder.append(DBConstants.CommonDatabaseParams.SQL_VALUES).append(ANNOUNCEMENT_INSERT_COLUMN_PLACEHOLDERS);
        addAnnouncementLangSpecificPlaceHoldersToInsertSQL(builder, languages);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA).append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
        builder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

        requestDBDTO.setQuery(builder.toString());
        requestDBDTO.setSupportedLang(languages);
        requestDBDTO.setData(data);
        return requestDBDTO;
    }

    /**
     * load announcements to migrate to Oracle
     * @param languages supported languages
     * @param maxAnnCountInIMDB max announcement count in IMDB
     * @return requestDBDTO
     */
    public static RequestDBDTO getExcessIMDBRecordsQuery(List<String> languages, String maxAnnCountInIMDB){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(LOAD_EXCESS_ANNOUNCEMENT_QUERY);
        requestDBDTO.setParams(maxAnnCountInIMDB);
        requestDBDTO.setSupportedLang(languages);
        return requestDBDTO;
    }

    //endregion

    //region ANNOUNCEMENT IMDB UPDATE

    private static final String GET_TO_UPDATE_ANNOUNCEMENT_LIST_QUERY = "SELECT ANN_ID FROM ANNOUNCEMENTS WHERE ANN_ID in (#id#)";

    private static final String DELETE_ANNOUNCEMENT_QUERY = "DELETE FROM ANNOUNCEMENTS WHERE ANN_ID in (#id#)";

    private static final String MAX_SEQ_IN_ANNOUNCEMENT_QUERY = "SELECT MAX(SEQ_ID) FROM ANNOUNCEMENTS";

    /**
     * get announcements to update even though status is N
     * @param newAnnIdList new announcement ids
     * @param languages supported languages
     * @return requestDBDTO
     */
    public static RequestDBDTO getUpdateListSelectQuery(Collection<Integer> newAnnIdList, List<String> languages){
        String query = GET_TO_UPDATE_ANNOUNCEMENT_LIST_QUERY.replaceAll(annIdInPlaceHolder, StringUtils.join(newAnnIdList, IConstants.Delimiter.COMMA));

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(languages);
        return requestDBDTO;
    }

    /**
     * get delete query
     * @param delAnnIdList announcement ids
     * @return requestDBDTO
     */
    public static RequestDBDTO getIMDBDeleteQuery(Collection<Integer> delAnnIdList){

        String query = DELETE_ANNOUNCEMENT_QUERY.replaceAll(annIdInPlaceHolder, StringUtils.join(delAnnIdList, IConstants.Delimiter.COMMA));

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        return requestDBDTO;
    }

    /**
     * get query for max seq id in Announcements table
     * @return requestDBDTO
     */
    public static RequestDBDTO getMaxSeqIdInIMDBQuery(){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(MAX_SEQ_IN_ANNOUNCEMENT_QUERY);
        return requestDBDTO;
    }

    /**
     * method to load gics for announcement tickers
     * @param tickers tagged tickers
     * @return requestDBDTO
     */
    @SuppressWarnings("unused")
    public static RequestDBDTO getGICSIMDBQuery(Collection<Integer> tickers){
        StringBuilder stringBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_SELECT);
        stringBuilder.append(DBConstants.DatabaseColumns.TICKER_SERIAL).append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
        stringBuilder.append(DBConstants.DatabaseColumns.GICSL2_CODE).append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
        stringBuilder.append(DBConstants.DatabaseColumns.GICSL3_CODE).append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
        stringBuilder.append(DBConstants.CommonDatabaseParams.FROM).append(DBConstants.TablesIMDB.TABLE_TICKERS);
        stringBuilder.append(DBConstants.CommonDatabaseParams.QUERY_WHERE).append(DBConstants.DatabaseColumns.TICKER_SERIAL);
        stringBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN).append(DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN);
        stringBuilder.append(StringUtils.join(tickers, IConstants.Delimiter.COMMA)).append(DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(stringBuilder.toString());
        return requestDBDTO;
    }


    //endregion

    //region ANNOUNCEMENT SEARCH JDBC

    private static final String SELECT = DBConstants.CommonDatabaseParams.QUERY_SELECT;
    private static final String COMMA = DBConstants.CommonDatabaseParams.QUERY_COMMA;
    private static final String PREC = DBConstants.CommonDatabaseParams.QUERY_PREC;
    private static final String QUOTE = DBConstants.CommonDatabaseParams.QUERY_QUOTE;
    private static final String BRACKET_OPEN = DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN;
    private static final String BRACKET_CLOSE = DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE;
    private static final String WHERE = DBConstants.CommonDatabaseParams.QUERY_WHERE;
    private static final String LIKE = DBConstants.CommonDatabaseParams.QUERY_LIKE;
    private static final String AND = DBConstants.CommonDatabaseParams.QUERY_AND;
    private static final String IN = DBConstants.CommonDatabaseParams.QUERY_IN;
    private static final String OR = DBConstants.CommonDatabaseParams.QUERY_OR;
    private static final String DESC = DBConstants.CommonDatabaseParams.SQL_DESC;
    private static final String ORDER = DBConstants.CommonDatabaseParams.QUERY_ORDER;
    private static final String NULL = DBConstants.CommonDatabaseParams.QUERY_NULL;
    private static final String NOT_EQUAL = DBConstants.CommonDatabaseParams.QUERY_NOT_EQUAL;
    private static final String NOT_NULL_IN = DBConstants.CommonDatabaseParams.COALESCE;
    private static final String AS = DBConstants.CommonDatabaseParams.SQL_AS;
    private static final String QUERY_FROM_CUSTOM = COMMA + DBConstants.CommonDatabaseParams.QUERY_ROW_NUM_OVER +
                                                    BRACKET_OPEN + ORDER + DBConstants.AnnouncementDatabaseColumns.ANN_DATE+
                                                    DESC + COMMA + DBConstants.AnnouncementDatabaseColumns.SEQ_ID + DESC +// add seq id soring since result is wrong when data set has same ann date
                                                    BRACKET_CLOSE + " RNUM FROM ANNOUNCEMENTS N ";
    private static final String QUERY_SELECT_OUTER_PART_A = "SELECT A.* FROM (";
    private static final String QUERY_SELECT_OUTER_PART_B = ") A WHERE ";
    private static final String QUERY_PAGINATION = " RNUM BETWEEN _#S_INDEX#_ AND _#E_INDEX#_";
    private static final String START_INDEX_PLACE_HOLDER = "_#S_INDEX#_";
    private static final String END_INDEX_PLACE_HOLDER = "_#E_INDEX#_";
    private static final String PREFIX = "N.";


    private static String SQL_QUERY_SELECT = IConstants.EMPTY;

    public static String getSymbolAnnouncementsQuery(String symbol, String exchange){
        StringBuilder queryBuilder = new StringBuilder();
        if (symbol != null && !symbol.trim().isEmpty() && exchange!= null && !exchange.trim().isEmpty()) {
            queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.TICKER_ID);
            queryBuilder.append(LIKE);
            queryBuilder.append(getQuotePercentCommaQuery(symbol));
            queryBuilder.append(AND);
            queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.SOURCE_ID);
            queryBuilder.append(LIKE);
            queryBuilder.append(getQuotePercentCommaQuery(exchange));
        }
        return queryBuilder.toString();
    }

    public static String getExchangeAnnouncementsQuery(String exchange){
        List<String> exchangeList;
        StringBuilder queryBuilder = new StringBuilder();
        if (exchange != null && !exchange.trim().isEmpty()) {
            exchangeList = Arrays.asList(exchange.split(CharUtils.toString(IConstants.Delimiter.COMMA)));
            queryBuilder.append(BRACKET_OPEN);
            queryBuilder.append(getLikeOrQuery(exchangeList, DBConstants.AnnouncementDatabaseColumns.SOURCE_ID));
            queryBuilder.append(BRACKET_CLOSE);
        }
        return queryBuilder.toString();
    }

    public static String getGicsL2AnnouncementsQuery(String gicsL2Code, List<String> exchanges){
        StringBuilder queryBuilder = new StringBuilder();
        List<String> gicsList;
        if (gicsL2Code != null && !gicsL2Code.trim().isEmpty() && exchanges != null) {
            gicsList = Arrays.asList(gicsL2Code.split(Character.toString(IConstants.Delimiter.COMMA)));
            queryBuilder.append(BRACKET_OPEN);
            queryBuilder.append(getLikeFromStartQuery(gicsList, DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.GICS_L2_CODE));
            queryBuilder.append(BRACKET_CLOSE);
            queryBuilder.append(AND);
            queryBuilder.append(BRACKET_OPEN);
            queryBuilder.append(getLikeOrQuery(exchanges, DBConstants.AnnouncementDatabaseColumns.SOURCE_ID));
            queryBuilder.append(BRACKET_CLOSE);
        }
        return queryBuilder.toString();
    }

    public static String getRelatedAnnouncementsQuery(String annId,String symbol,String exchange) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(getSymbolAnnouncementsQuery(symbol,exchange));
        queryBuilder.append(AND).append(DBConstants.AnnouncementDatabaseColumns.ANN_ID).append(NOT_EQUAL).append(annId);
        return  queryBuilder.toString();
    }

    public static String getRelatedExchangeAnnouncementsQuery(String annId,String exchange) {
        StringBuilder query = new StringBuilder();
        query.append(getExchangeAnnouncementsQuery(exchange));
        query.append(AND);
        query.append(DBConstants.AnnouncementDatabaseColumns.TICKER_ID);
        query.append(NULL).append(AND);
        query.append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
        query.append(NOT_EQUAL);
        query.append(annId);

        return query.toString();
    }

    public static String getAnnouncementHeadLinesByIdQuery(String annIdList) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.ANN_ID).append(IN).append(BRACKET_OPEN);
        queryBuilder.append(annIdList);
        queryBuilder.append(BRACKET_CLOSE);

        return  queryBuilder.toString();
    }

    private static final String announcementCountQuery = "SELECT COUNT(*) FROM ANNOUNCEMENTS WHERE ";

    public static String getAnnouncementCountQuery() {
        return announcementCountQuery;
    }


    //----------------------------Commons - START---------------------------------------------------------------------//

    public static String addOracleFilters(String query, Map<String,String> requestData){
        query += DBConstants.CommonDatabaseParams.QUERY_AND + DBConstants.AnnouncementDatabaseColumns.STATUS +
                DBConstants.CommonDatabaseParams.QUERY_NOT_EQUAL + DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                IConstants.DCPStatus.DELETED.getStatus() + DBConstants.CommonDatabaseParams.QUERY_QUOTE;
        if (requestData.containsKey(IConstants.CustomDataField.MIN_ANN_DATE)) {
            String minDate = requestData.get(IConstants.CustomDataField.MIN_ANN_DATE);
            query += DBConstants.CommonDatabaseParams.QUERY_AND + DBConstants.AnnouncementDatabaseColumns.ANN_DATE +
                     DBConstants.CommonDatabaseParams.QUERY_LESS_THAN + "TO_TIMESTAMP" +
                     DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN + DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                     minDate + DBConstants.CommonDatabaseParams.QUERY_QUOTE + DBConstants.CommonDatabaseParams.QUERY_COMMA +
                     "'yyyy-MM-dd hh24:mi:ss.ff'" + DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE;
        }
        return query;
    }

    /**
     * Generate pagination  Settings for announcement search using,
     * START_INDEX,PAGE_SIZE,OFFSET
     *
     * @param query query
     * @param requestData request data
     * @return query
     */
    public static String getJDBCFilteredAnnouncementQuery(String query,Map<String,String> requestData){
        int pageIndex = 0;
        int offSet = 0;
        int annCount = Integer.parseInt(requestData.get(IConstants.CustomDataField.ANNOUNCEMENT_COUNT));
        int imdbPageLimit = 0;

        if (requestData.get(IConstants.MIXDataField.PGI) != null) {
            pageIndex = Integer.parseInt(requestData.get(IConstants.MIXDataField.PGI));
        } else {
            annCount --;
        }

        if (requestData.get(IConstants.CustomDataField.IM_M_PGI) != null) {
            imdbPageLimit = Integer.parseInt(requestData.get(IConstants.CustomDataField.IM_M_PGI));
        }

        if (requestData.get(IConstants.CustomDataField.ODB_OFFSET) != null) {
            offSet = Integer.parseInt(requestData.get(IConstants.CustomDataField.ODB_OFFSET));
        }

        int pPageIndex = (imdbPageLimit < 0 || pageIndex - imdbPageLimit == 0) ? 0 : pageIndex - imdbPageLimit - 1;

        return QUERY_SELECT_OUTER_PART_A + query + QUERY_SELECT_OUTER_PART_B + getPaginationQueryString(pPageIndex, annCount, offSet);
    }

    /**
     * Set Pagination properties to query using START_INDEX , END_INDEX
     *
     * @param pageIndex page index
     * @param resultsCount result count
     * @param offSet offset
     * @return pagination query
     */
    private static String getPaginationQueryString(int pageIndex, int resultsCount, int offSet) {
        LOG.info("fetching from : " + (offSet + (pageIndex * resultsCount)) + ", max results " + (resultsCount + 1) + " from oracle");

        int startIndex = offSet + (pageIndex * resultsCount);
        int endIndex = offSet + (pageIndex * resultsCount) + (resultsCount);

        String paginationQuery = QUERY_PAGINATION.replace(START_INDEX_PLACE_HOLDER,String.valueOf(startIndex));
        paginationQuery = paginationQuery.replace(END_INDEX_PLACE_HOLDER,String.valueOf(endIndex));

        return paginationQuery;
    }


    /**
     * Generate Select query for announcement search using announcement supported languages list
     *
     * @param supportedLanguages  supported languages list
     * @return query
     */
    public static String getLangSpecificSelectQuery(String preferredLang, List<String> supportedLanguages) {
        StringBuilder selectQueryBuilder = new StringBuilder();
        selectQueryBuilder.append(getCommonSelectQuery());
        selectQueryBuilder.append(IConstants.Delimiter.COMMA);
        String query;

        for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.class.getFields()) {
            selectQueryBuilder.append(NOT_NULL_IN);
            selectQueryBuilder.append(BRACKET_OPEN);
            selectQueryBuilder.append(PREFIX).append(field.getName()).append(IConstants.Delimiter.UNDERSCORE).append(preferredLang.toUpperCase());
            selectQueryBuilder.append(IConstants.Delimiter.COMMA);

            String sep = IConstants.EMPTY;
            for (String lang : supportedLanguages) {
                if (!lang.equalsIgnoreCase(preferredLang)) {
                    selectQueryBuilder.append(sep);
                    selectQueryBuilder.append(PREFIX).append(field.getName()).append(IConstants.Delimiter.UNDERSCORE).append(lang.toUpperCase());
                    sep = Character.toString(IConstants.Delimiter.COMMA);
                }
            }
            selectQueryBuilder.append(BRACKET_CLOSE);
            selectQueryBuilder.append(AS);
            selectQueryBuilder.append(field.getName()).append(IConstants.Delimiter.UNDERSCORE).append(preferredLang.toUpperCase());
            selectQueryBuilder.append(IConstants.Delimiter.COMMA);
        }

        query = selectQueryBuilder.toString();
        selectQueryBuilder.setLength(0);
        selectQueryBuilder.append(CommonUtils.removeLast(query));
        selectQueryBuilder.append(QUERY_FROM_CUSTOM).append(WHERE);
        query = selectQueryBuilder.toString();

        if(LOG.isDebugEnabled()){
            LOG.debug("<!-- Announcement Search Query | IMDB : "+query);
        }

        return selectQueryBuilder.toString();
    }

    /**
     * Generate select query for non-language specific columns,
     * this is generated only once.
     *
     * @return select query
     */
    private static String getCommonSelectQuery(){
        if (SQL_QUERY_SELECT.trim().isEmpty()) {
            StringBuilder selectQueryBuilder = new StringBuilder();
            selectQueryBuilder.append(SELECT);

            String sep = IConstants.EMPTY;
            for (Field field : DBConstants.AnnouncementDatabaseColumns.class.getFields()) {
                selectQueryBuilder.append(sep).append(PREFIX).append(field.getName());
                sep = Character.toString(IConstants.Delimiter.COMMA);
            }
            for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.class.getFields()) {
                selectQueryBuilder.append(sep).append(PREFIX).append(field.getName());
                sep = Character.toString(IConstants.Delimiter.COMMA);
            }
            SQL_QUERY_SELECT = selectQueryBuilder.toString();

        }
        return SQL_QUERY_SELECT;
    }

    /**
     * Generate like or query using param value list and db column
     *
     * eg: ((SOURCE_ID LIKE '%,AAA,%') OR (SOURCE_ID LIKE '%,BBB,%'))
     *
     * @param paramList list of params
     * @param dbColumn column to search
     * @return like or query
     */
    private static String getLikeOrQuery(Collection<String> paramList, String dbColumn) {
        String queryLike = "";
        StringBuilder queryBuilder = new StringBuilder();
        for (String paramVal : paramList) {
            if (!paramVal.trim().isEmpty()) {
                queryBuilder.append(queryLike);
                queryBuilder.append(dbColumn);
                queryBuilder.append(LIKE);
                queryBuilder.append(BRACKET_OPEN);
                queryBuilder.append(getQuotePercentCommaQuery(paramVal));
                queryBuilder.append(BRACKET_CLOSE);
                queryLike = OR;
            }
        }

        return queryBuilder.toString();
    }

    /**
     * Generate like query where ',' is only in start of the matching phrase
     * LIKE ('%,AAA%')
     *
     * @param paramList param list
     * @param dbColumn db column name
     * @return like query
     */
    private static String getLikeFromStartQuery(List<String> paramList, String dbColumn) {
        String queryLike = IConstants.EMPTY;
        StringBuilder queryBuilder = new StringBuilder();
        for (String paramVal : paramList) {
            if (!paramVal.trim().isEmpty()) {
                queryBuilder.append(queryLike);
                queryBuilder.append(dbColumn);
                queryBuilder.append(LIKE).append(BRACKET_OPEN).append(QUOTE).append(PREC).append(COMMA);
                queryBuilder.append(paramVal);
                queryBuilder.append(PREC).append(QUOTE).append(BRACKET_CLOSE);
                queryLike = OR;
            }
        }

        return queryBuilder.toString();
    }

    /**
     * Get '%,AAA,%' like String from AAA
     *
     * @param value param value
     * @return  '%,AAA,%'
     */
    private static String getQuotePercentCommaQuery(String value){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUOTE).append(PREC).append(COMMA);
        queryBuilder.append(value);
        queryBuilder.append(COMMA).append(PREC).append(QUOTE);
        return queryBuilder.toString();
    }

    //endregion

    /**
     * get query to load oldest announcement in Oracle
     * Checks for announcements with N status
     * @return db request
     */
    public static RequestDBDTO getMinAnnDateInOracle(){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(LOAD_ANNOUNCEMENT_HISTORY_QUERY);
        return requestDBDTO;
    }

    //region Insert or Update Announcements


    private static final String ANNOUNCEMENT_MAX_DATE_ORACLE_QUERY = "SELECT MAX(" + DBConstants.AnnouncementDatabaseColumns.ANN_DATE +
            ") AS ANNOUNCEMENT_DATE_MAX FROM " + DBConstants.TablesORACLE.TABLE_ANNOUNCEMENTS;

    public static final String KEY_UPDATE = "ANNOUNCEMENT_UPDATE_";
    public static final String KEY_INSERT = "ANNOUNCEMENT_INSERT_";

    private static Map<String,String> QUERY_MAP = new HashMap<String, String>();

    public static final String QUESTION_MARK = DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;
    public static final int PLACE_HOLDER_LENGTH = 500;


    /**
     * Get announcement which as the max(ANN_DATE) in oracle
     * @return SELECT MAX(NEWS_DATE) AS NEWS_DATE_MAX FROM NEWS WHERE LANGUAGE_ID = ?
     */
    public static String getMaxAnnouncementDateInOracleQuery() {
        return ANNOUNCEMENT_MAX_DATE_ORACLE_QUERY;
    }

    /**
     * Get Announcement Update Query
     *
     * @param isTagFields is some of the fields [GICSL3,GICSl4] needs to be manually tagged to announcement
     * @param language language
     * @return Query
     */
    public static String getAnnouncementUpdateQuery(boolean isTagFields, String language) {

        String updateQuery;
        String key = KEY_UPDATE + Boolean.valueOf(isTagFields).toString().toUpperCase() + IConstants.Delimiter.UNDERSCORE + language.toUpperCase();

        if (QUERY_MAP.get(key) == null) {

            StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_UPDATE).
                    append(DBConstants.TablesIMDB.TABLE_ANNOUNCEMENTS).
                    append(DBConstants.CommonDatabaseParams.QUERY_SET);

            String prefix = IConstants.EMPTY;
            for (Field field : DBConstants.AnnouncementDatabaseColumns.class.getFields()) {
                if (!field.getName().equals(DBConstants.AnnouncementDatabaseColumns.ANN_ID)) {
                    queryBuilder.append(prefix);
                    queryBuilder.append(field.getName()).append(IConstants.Delimiter.EQUAL).append(QUESTION_MARK);
                    prefix = COMMA;
                }

            }

            if (isTagFields) {
                for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.class.getFields()) {
                    queryBuilder.append(prefix);
                    queryBuilder.append(field.getName()).append(IConstants.Delimiter.EQUAL).append(QUESTION_MARK);
                    prefix = COMMA;
                }
            }

            for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.class.getFields()) {
                queryBuilder.append(prefix);
                queryBuilder.append(field.getName()).append(IConstants.Delimiter.UNDERSCORE).append(language.toUpperCase())
                        .append(IConstants.Delimiter.EQUAL).append(QUESTION_MARK);
            }

            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_WHERE);
            String query = queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.ANN_ID).
                    append(IConstants.Delimiter.EQUAL).append(QUESTION_MARK).toString();

            QUERY_MAP.put(key, query);

            updateQuery = query;
        } else {
            updateQuery = QUERY_MAP.get(key);
        }

        LOG.debug("<!--Announcement update Query : "+updateQuery);

        return updateQuery;
    }

    /**
     * Get announcement insert query [ Only for a specific language ]
     *
     * @param language language
     * @return Query
     */
    public static String getAnnouncementInsertQuery(boolean isTagFields, String language) {
        String insertQuery;
        String key = KEY_INSERT+ Boolean.valueOf(isTagFields).toString().toUpperCase()+IConstants.Delimiter.UNDERSCORE+language.toUpperCase();

        if (QUERY_MAP.get(key)==null) {

            StringBuilder builder = new StringBuilder(QUERY_ANNOUNCEMENT_INSERT).append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
            StringBuilder placeHolderBuilder = new StringBuilder();

            String prefix = IConstants.EMPTY;
            for (Field field : DBConstants.AnnouncementDatabaseColumns.class.getFields()) {
                if (!field.getName().equals(DBConstants.AnnouncementDatabaseColumns.ANN_ID)) {
                    builder.append(prefix);
                    builder.append(field.getName());
                    prefix = COMMA;
                    placeHolderBuilder.append(QUESTION_MARK).append(prefix);
                }

            }

            if (isTagFields) {
                for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnManualTaggingColumns.class.getFields()) {
                    builder.append(prefix);
                    builder.append(field.getName());
                    placeHolderBuilder.append(QUESTION_MARK).append(prefix);
                }
            }

            for (Field field : DBConstants.AnnouncementDatabaseColumns.AnnLangSpecificColumns.class.getFields()) {
                builder.append(prefix);
                builder.append(field.getName()).append(IConstants.Delimiter.UNDERSCORE).append(language.toUpperCase());
                placeHolderBuilder.append(QUESTION_MARK).append(prefix);
            }

            builder.append(COMMA).append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
            placeHolderBuilder.append(QUESTION_MARK);
            builder.append(DBConstants.CommonDatabaseParams.SQL_VALUES);
            builder.append(placeHolderBuilder.toString());
            builder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
            String query = builder.toString();

            QUERY_MAP.put(key, query);

            insertQuery = query;

        }else{
            insertQuery = QUERY_MAP.get(key);
        }

        LOG.debug("<!--Announcement insert Query : "+insertQuery);

        return insertQuery;
    }

    public static String getAnnouncementSearchByAnnIdQuery(int annListSize){
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("SELECT ANN_ID FROM ANNOUNCEMENTS WHERE");
        String prefix = IConstants.EMPTY;

        int val = annListSize / PLACE_HOLDER_LENGTH;
        int remainder = annListSize % PLACE_HOLDER_LENGTH;

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        if (val > 0) {
            for (int j = 0; j < val; j++) {
                queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
                for (int i = 0; i < PLACE_HOLDER_LENGTH; i++) {
                    queryBuilder.append(prefix);
                    queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                    prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
                }
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
                if (j < val - 1) {
                    queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
                    prefix = IConstants.EMPTY;
                }
            }
        }

        prefix = IConstants.EMPTY;
        if (remainder > 0) {
            if(val>0){
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
            }
            queryBuilder.append(DBConstants.AnnouncementDatabaseColumns.ANN_ID);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
            for (int k = 0; k < remainder; k++) {
                queryBuilder.append(prefix);
                queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
            }
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        }

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

        return queryBuilder.toString();
    }

    //endregion
}
