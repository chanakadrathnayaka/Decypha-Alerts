package com.dfn.alerts.dataaccess.utils.news;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.CharUtils;

import java.util.List;
import java.util.Set;

/**
 * oracle news query generation
 * Created by hasarindat on 7/3/2015.
 */
public class OracleNewsQueryGenerator extends NewsQueryGenerator {

    protected final String table = DBConstants.TablesORACLE.TABLE_NEWS;


    public StringBuilder getSelectWhereQueryForCount(int dbType, List<String> supportedLanguages) {
        StringBuilder stringBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_SELECT_COUNT_ALL_FROM);
        switch (dbType) {
            case DBConstants.DatabaseTypes.DB_TYPE_ORACLE:
                stringBuilder.append(DBConstants.TablesORACLE.TABLE_NEWS);
                break;
            default:
                stringBuilder.append(DBConstants.TablesIMDB.TABLE_NEWS);
                break;
        }
        stringBuilder.append(DBConstants.WHERE);
        addGenericFilters(stringBuilder, supportedLanguages);
        return stringBuilder;
    }

    public RequestDBDTO getMaxSeqIdQuery() {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery("SELECT MAX(" + DBConstants.NewsDatabaseColumns.SEQ_ID + ") FROM " + table);
        return requestDBDTO;
    }

    public RequestDBDTO getLatestNewsQuery() {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM + table +
                DBConstants.WHERE + DBConstants.NewsDatabaseColumns.LAST_UPDATED_TIME + DBConstants.CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN + DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
        return requestDBDTO;
    }

    @Override
    public RequestDBDTO getAvailableNewsItems(Set<Long> newsIdList) {
        return super.getAvailableNews(newsIdList, table);
    }

    @Override
    public RequestDBDTO getDeleteQuery(int count) {
        return null;
    }

    @Override
    public RequestDBDTO getEODDeleteQuery() {
        return null;
    }

    @Override
    public RequestDBDTO getInsertQuery(List<String> languages) {
        return super.getInsertQuery(languages, table);
    }

    @Override
    public RequestDBDTO getUpdateQuery(List<String> languages) {
        return super.getUpdateQuery(languages, table);
    }

    @Override
    public RequestDBDTO getTopNewsUpdateQuery() {
        return null;
    }

    public void addNewsDelayBySourceFilter(StringBuilder queryBuilder, String delayTimeMinutes, String delaySources) {

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_WHEN);
        queryBuilder.append(DBConstants.SQL_BRACKET_OPEN).append(DBConstants.SQL_BRACKET_OPEN);
        queryBuilder.append(DBConstants.CommonDatabaseParams.SYSDATE).append(DBConstants.CommonDatabaseParams.MINUS);
        queryBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_DATE).append(DBConstants.SQL_BRACKET_CLOSE)
                .append(DBConstants.CommonDatabaseParams.MULTIPLY).append(DBConstants.CommonDatabaseParams.HOURS).append(DBConstants.CommonDatabaseParams.MULTIPLY).append(DBConstants.CommonDatabaseParams.MINUTES);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN).append(delayTimeMinutes);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND).append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_EQUAL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(delaySources);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(DBConstants.SQL_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_THEN);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
        queryBuilder.append(delaySources);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);

    }

    @Override
    public boolean addNewsDelayBySourceFilter(StringBuilder query, String delayedSources) {
        boolean added = false;
        if (delayedSources != null && !delayedSources.isEmpty()) {
            String[] delayedSourcesList = delayedSources.split(CharUtils.toString(IConstants.Delimiter.COMMA));
            query.append(DBConstants.CommonDatabaseParams.QUERY_AND).append(DBConstants.CommonDatabaseParams.QUERY_NVL);
            query.append(DBConstants.SQL_BRACKET_OPEN).append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID);
            query.append(DBConstants.CommonDatabaseParams.SQL_COMMA).append(DBConstants.CommonDatabaseParams.ZERO).append(DBConstants.SQL_BRACKET_CLOSE);
            query.append(DBConstants.CommonDatabaseParams.QUERY_EQUAL).append(DBConstants.CommonDatabaseParams.QUERY_CASE);

            for (String delayedSource : delayedSourcesList) {
                String[] delayedSourceTime = delayedSource.split(CharUtils.toString(IConstants.Delimiter.TILDE));
                addNewsDelayBySourceFilter(query, delayedSourceTime[1], delayedSourceTime[0]);
            }

            query.append(DBConstants.CommonDatabaseParams.QUERY_WHEN);
            query.append(DBConstants.CommonDatabaseParams.QUERY_NVL).append(DBConstants.SQL_BRACKET_OPEN);
            query.append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID).append(DBConstants.CommonDatabaseParams.SQL_COMMA);
            query.append(DBConstants.CommonDatabaseParams.ZERO).append(DBConstants.SQL_BRACKET_CLOSE).append(DBConstants.CommonDatabaseParams.QUERY_NOT_IN);
            query.append(DBConstants.SQL_BRACKET_OPEN);

            if (delayedSources.indexOf(IConstants.COMMA) > 0) {
                String prefix = IConstants.EMPTY;
                for (String delayedSource : delayedSourcesList) {
                    String[] delayedSourceTime = delayedSource.split(CharUtils.toString(IConstants.Delimiter.TILDE));
                    query.append(prefix);
                    prefix = DBConstants.CommonDatabaseParams.SQL_COMMA;
                    query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                    query.append(delayedSourceTime[0]);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                }
            } else {
                String[] delayedSourceTime = delayedSources.split(CharUtils.toString(IConstants.Delimiter.TILDE));
                query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                query.append(delayedSourceTime[0]);
                query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
            }

            query.append(DBConstants.SQL_BRACKET_CLOSE);
            query.append(DBConstants.CommonDatabaseParams.QUERY_THEN);
            query.append(DBConstants.CommonDatabaseParams.QUERY_NVL);
            query.append(DBConstants.SQL_BRACKET_OPEN);
            query.append(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID).append(DBConstants.CommonDatabaseParams.SQL_COMMA);
            query.append(DBConstants.CommonDatabaseParams.ZERO).append(DBConstants.SQL_BRACKET_CLOSE);

            query.append(DBConstants.CommonDatabaseParams.QUERY_END);
            added = true;

        }

        return added;
    }
}
