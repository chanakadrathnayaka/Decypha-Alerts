package com.dfn.alerts.dataaccess.utils.news;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.TopNewsDataDTO;
import com.dfn.alerts.beans.news.NewsDTO;
import com.dfn.alerts.beans.news.NewsIndividualDTO;
import com.dfn.alerts.beans.news.NewsLangDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.dfn.alerts.constants.DBConstants.NewsDatabaseColumns.*;

/**
 * abstract class
 * Created by hasarindat on 7/6/2015.
 */
public abstract class NewsQueryGenerator implements INewsQueryGenerator {

    private static final Logger LOG = LogManager.getLogger(NewsQueryGenerator.class);

    private static final String q = ":: Query :: ";

    private static final String HEAD = "HEAD";

    private static final String PRV = "PRV";

    private static final String DT = "DT";

    //region update

    private final String[] NEWS_COLUMNS = new String[]{
            LAST_UPDATED_TIME, LAST_SYNC_TIME, EDITORIAL_CODE, INDIVIDUAL_CODE, COUNTRY, EXCHANGE, INDUSTRY_CODE, MARKET_SECTOR_CODE,
            SYMBOL, TICKER_ID, COMPANY_ID, ASSET_CLASS, HOT_NEWS_INDICATOR, NEWS_SOURCE_ID, NEWS_PROVIDER, STATUS, NEWS_DATE, SEQ_ID, IS_TOP_STORY
    };

    private final String[] NEWS_LANG_COLUMNS = new String[]{
            NEWS_HEADLINE_LN, NEWS_SOURCE_DESC, URL, APPROVAL_STATUS, GICSL3_DESC, NEWS_CONTRIBUTED_DATE, NEWS_LAST_UPDATED_TIME
    };

    RequestDBDTO getAvailableNews(Set<Long> newsIdList, String tableName) {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        boolean orRequired;
        int loop = 0;
        String query = DBConstants.CommonDatabaseParams.QUERY_SELECT + DBConstants.NewsDatabaseColumns.NEWS_ID +
                DBConstants.CommonDatabaseParams.FROM + tableName + DBConstants.WHERE;
        do {
            int from = loop * 1000;
            int to = (newsIdList.size() < (loop + 1) * 1000) ? newsIdList.size() : (loop + 1) * 1000;

            List<Long> subList = new ArrayList<Long>(newsIdList).subList(from, to);
            query += DBConstants.NewsDatabaseColumns.NEWS_ID + DBConstants.CommonDatabaseParams.QUERY_IN +
                    DBConstants.SQL_BRACKET_OPEN + StringUtils.join(subList, DBConstants.SQL_COMMA) + DBConstants.SQL_BRACKET_CLOSE;

            orRequired = newsIdList.size() > to + 1;
            if (orRequired) {
                query += DBConstants.CommonDatabaseParams.QUERY_OR;
                loop++;
            }
        } while (orRequired);

        requestDBDTO.setQuery(query);
        return requestDBDTO;
    }

    RequestDBDTO getInsertQuery(List<String> languages, String tableName) {
        StringBuilder stringBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO).append(tableName);
        stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN);
        for (String column : NEWS_COLUMNS) {
            stringBuilder.append(column).append(DBConstants.SQL_COMMA);
        }
        for (String lang : languages) {
            for (String column : NEWS_LANG_COLUMNS) {
                stringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(DBConstants.SQL_COMMA);
            }
        }
        stringBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_ID);

        stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_VALUES);
        for (String ignored : NEWS_COLUMNS) {
            stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK).append(DBConstants.SQL_COMMA);
        }
        for (String ignore : languages) {
            for (String ignored : NEWS_LANG_COLUMNS) {
                stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK).append(DBConstants.SQL_COMMA);
            }
        }
        stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);

        stringBuilder.append(DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(stringBuilder.toString());
        requestDBDTO.setSupportedLang(languages);
        return requestDBDTO;
    }

    RequestDBDTO getUpdateQuery(List<String> languages, String tableName) {
        StringBuilder stringBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_UPDATE).append(tableName);
        stringBuilder.append(DBConstants.CommonDatabaseParams.QUERY_SET);
        for (String column : NEWS_COLUMNS) {
            stringBuilder.append(column).append(DBConstants.CommonDatabaseParams.EQUAL_QUETION_MARK).append(DBConstants.SQL_COMMA);
        }
        for (String lang : languages) {
            for (String column : NEWS_LANG_COLUMNS) {
                stringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).
                        append(DBConstants.CommonDatabaseParams.EQUAL_QUETION_MARK).append(DBConstants.SQL_COMMA);
            }
        }

        stringBuilder.replace(stringBuilder.lastIndexOf(DBConstants.SQL_COMMA), stringBuilder.length(), DBConstants.WHERE);
        stringBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_ID);
        stringBuilder.append(DBConstants.CommonDatabaseParams.EQUAL_QUETION_MARK);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(stringBuilder.toString());
        requestDBDTO.setSupportedLang(languages);
        return requestDBDTO;
    }

    /**
     * LAST_UPDATED_TIME, LAST_SYNC_TIME, EDITORIAL_CODE, INDIVIDUAL_CODE, COUNTRY, EXCHANGE, INDUSTRY_CODE, MARKET_SECTOR_CODE,
     * SYMBOL, TICKER_SERIAL, COMPANY_ID, ASSET_CLASS, HOT_NEWS_INDICATOR, NEWS_SOURCE_ID, NEWS_PROVIDER, STATUS, NEWS_DATE, SEQ_ID
     * NEWS_HEADLINE, NEWS_SOURCE_DESC, URL, APPROVAL_STATUS
     * NEWS_ID,IS_TOP_STORY
     *
     * @param preparedStatement prepared statement
     * @param newsDTO           news object
     * @param languages         supported languages
     * @throws SQLException if parameterIndex does not correspond to a parameter
     */
    public void setNewsUpdateColumnValues(PreparedStatement preparedStatement, NewsDTO newsDTO, List<String> languages) throws SQLException {
        int index = 1;
        preparedStatement.setTimestamp(index++, newsDTO.getLastUpdatedTime());
        preparedStatement.setTimestamp(index++, newsDTO.getLastSyncTime());
        preparedStatement.setString(index++, newsDTO.getEditorialCodes());
        preparedStatement.setString(index++, newsDTO.getIndividuals());
        preparedStatement.setString(index++, newsDTO.getCountries());
        preparedStatement.setString(index++, newsDTO.getExchanges());
        preparedStatement.setString(index++, newsDTO.getGicsL3Codes());
        preparedStatement.setString(index++, newsDTO.getMarketSectorCodes());
        preparedStatement.setString(index++, newsDTO.getSymbols());
        preparedStatement.setString(index++, newsDTO.getSymbolTickerSerials());
        preparedStatement.setString(index++, newsDTO.getCompanies());
        preparedStatement.setString(index++, newsDTO.getAssetClasses());

        preparedStatement.setInt(index++, newsDTO.getHotNewsIndicator());
        preparedStatement.setInt(index++, newsDTO.getNewsSource());
        preparedStatement.setString(index++, newsDTO.getProvider());
        preparedStatement.setString(index++, newsDTO.getStatus().getStatus());
        preparedStatement.setTimestamp(index++, newsDTO.getNewsDate());
        preparedStatement.setLong(index++, newsDTO.getSeqId());
        preparedStatement.setInt(index++, newsDTO.getIsTopStory());

        for (String lang : languages) {
            if (newsDTO.getLangDTOMap().containsKey(lang) && newsDTO.getLangDTOMap().get(lang) != null) {
                NewsLangDTO newsLangDTO = newsDTO.getLangDTOMap().get(lang);
                preparedStatement.setString(index++, newsLangDTO.getNewsHeadline());
                preparedStatement.setString(index++, newsLangDTO.getNewsSourceDescription());
                preparedStatement.setString(index++, newsLangDTO.getUrl());
                preparedStatement.setInt(index++, newsLangDTO.getApprovalStatus());
                preparedStatement.setString(index++, newsLangDTO.getGicsL3Descriptions());
                preparedStatement.setTimestamp(index++, newsLangDTO.getNewsContributedTime());
                preparedStatement.setTimestamp(index++, newsLangDTO.getNewsLastUpdatedTime());
            } else {
                preparedStatement.setString(index++, null);
                preparedStatement.setString(index++, null);
                preparedStatement.setString(index++, null);
                preparedStatement.setInt(index++, -1);
                preparedStatement.setString(index++, null);
                preparedStatement.setTimestamp(index++, null);
                preparedStatement.setTimestamp(index++, null);
            }
        }
        preparedStatement.setLong(index, newsDTO.getNewsId());
    }

    /**
     * NEWS_ID,IS_TOP_STORY
     *
     * @param preparedStatement prepared statement
     * @param topNewsDataDTO    news object
     * @throws SQLException if parameterIndex does not correspond to a parameter
     */
    public void setTopNewsUpdateColumnValues(PreparedStatement preparedStatement, TopNewsDataDTO topNewsDataDTO) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, topNewsDataDTO.getIsTopStory());
        preparedStatement.setLong(index++, topNewsDataDTO.getNewsId());
    }


        public void setNewsInsertColumnValues(PreparedStatement preparedStatement, NewsDTO newsDTO, List<String> languages) throws SQLException {
        setNewsUpdateColumnValues(preparedStatement, newsDTO, languages);
    }

    //endregion

    public NewsDTO getNewsDTO(ResultSet resultSet, List<String> languages) throws SQLException {
        NewsDTO newsDTO = new NewsDTO(resultSet.getLong(NEWS_ID));
        newsDTO.setSeqId(resultSet.getLong(SEQ_ID));
        newsDTO.setNewsDate(resultSet.getTimestamp(NEWS_DATE));
        newsDTO.setStatus(IConstants.DCPStatus.getDCPStatus(resultSet.getString(STATUS)));
        newsDTO.setProvider(resultSet.getString(NEWS_PROVIDER));
        newsDTO.setNewsSource(resultSet.getInt(NEWS_SOURCE_ID));
        newsDTO.setHotNewsIndicator(resultSet.getInt(HOT_NEWS_INDICATOR));
        newsDTO.setAssetClasses(resultSet.getString(ASSET_CLASS));
        newsDTO.setCompanies(resultSet.getString(COMPANY_ID));
        newsDTO.setSymbolTickerSerials(resultSet.getString(TICKER_ID));
        newsDTO.setSymbols(resultSet.getString(SYMBOL));
        newsDTO.setMarketSectorCodes(resultSet.getString(MARKET_SECTOR_CODE));
        newsDTO.setGicsL3Codes(resultSet.getString(INDUSTRY_CODE));
        newsDTO.setExchanges(resultSet.getString(EXCHANGE));
        newsDTO.setCountries(resultSet.getString(COUNTRY));
        newsDTO.setIndividuals(resultSet.getString(INDIVIDUAL_CODE));
        newsDTO.setEditorialCodes(resultSet.getString(EDITORIAL_CODE));
        newsDTO.setLastSyncTime(resultSet.getTimestamp(LAST_SYNC_TIME));
        newsDTO.setLastUpdatedTime(resultSet.getTimestamp(LAST_UPDATED_TIME));
        newsDTO.setIsTopStory(resultSet.getInt(IS_TOP_STORY));

        for (String lang : languages) {
            NewsLangDTO newsLangDTO = new NewsLangDTO();
            newsLangDTO.setNewsHeadline(resultSet.getString(NEWS_HEADLINE_LN + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setNewsSourceDescription(resultSet.getString(NEWS_SOURCE_DESC + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setUrl(resultSet.getString(URL + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setApprovalStatus(resultSet.getInt(APPROVAL_STATUS + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setGicsL3Descriptions(resultSet.getString(GICSL3_DESC + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setNewsLastUpdatedTime(resultSet.getTimestamp(NEWS_LAST_UPDATED_TIME + IConstants.Delimiter.UNDERSCORE + lang));
            newsLangDTO.setNewsContributedTime(resultSet.getTimestamp(NEWS_CONTRIBUTED_DATE + IConstants.Delimiter.UNDERSCORE + lang));

            newsDTO.addLangDTO(lang, newsLangDTO);
        }
        return newsDTO;
    }

    public NewsIndividualDTO getNewsIndividualDTO(ResultSet resultSet, List<String> languages) throws SQLException {
        NewsIndividualDTO newsDTO = new NewsIndividualDTO(resultSet.getLong(NEWS_ID));
        newsDTO.setNewsDate(resultSet.getTimestamp(NEWS_DATE));
        newsDTO.setIndividualId(resultSet.getLong(INDIVIDUAL_ID));
        newsDTO.setName(resultSet.getString(NAME));

        newsDTO.setCountries(resultSet.getString(COUNTRY_CODE));
        newsDTO.setLastUpdatedTime(resultSet.getTimestamp(LAST_UPDATED_TIME));

        for (String lang : languages) {
            NewsLangDTO newsLangDTO = new NewsLangDTO();
            newsLangDTO.setNewsHeadline(resultSet.getString(NEWS_HEADLINE_LN + IConstants.Delimiter.UNDERSCORE + lang));
            if (resultSet.getString(APPROVAL_STATUS + IConstants.Delimiter.UNDERSCORE + lang) != null) {
                newsLangDTO.setApprovalStatus(Integer.parseInt(resultSet.getString(APPROVAL_STATUS + IConstants.Delimiter.UNDERSCORE + lang)));
            } else {
                LOG.debug("APPROVAL_STATUS is null for NEWS_ID = "+ newsDTO.getNewsId() + "and LANGUAGE = " + lang);
            }
            newsDTO.addLangDTO(lang, newsLangDTO);
        }
        return newsDTO;
    }


    //region search

    public StringBuilder getSelectWhereQuery(int dbType, List<String> supportedLanguages) {
        StringBuilder stringBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM);
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

    public boolean addGenericFilters(StringBuilder queryBuilder, List<String> supportedLanguages) {
        DBUtils.addNotEqualQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.STATUS, IConstants.DCPStatus.DELETED.getStatus(), false, false);
        String headlineFilter = IConstants.EMPTY;
        String approvalStatusFilter = IConstants.EMPTY;
        for (int i = 0; i < supportedLanguages.size(); i++) {
            headlineFilter += DBConstants.NewsDatabaseColumns.NEWS_HEADLINE_LN + IConstants.Delimiter.UNDERSCORE + supportedLanguages.get(i) +
                    DBConstants.CommonDatabaseParams.QUERY_NOT_NULL;
            approvalStatusFilter += DBConstants.NewsDatabaseColumns.APPROVAL_STATUS + IConstants.Delimiter.UNDERSCORE + supportedLanguages.get(i) +
                    DBConstants.CommonDatabaseParams.QUERY_EQUAL + IConstants.NewsApprovalStatus.PUBLISHED.getStatus();
            if (i != supportedLanguages.size() - 1) {
                headlineFilter += DBConstants.CommonDatabaseParams.QUERY_OR;
                approvalStatusFilter += DBConstants.CommonDatabaseParams.QUERY_OR;
            }
        }
        if (!CommonUtils.isNullOrEmptyString(headlineFilter)) {
            queryBuilder.append(DBConstants.AND).append(DBConstants.SQL_BRACKET_OPEN).append(headlineFilter).append(DBConstants.SQL_BRACKET_CLOSE);
        }
        if (!CommonUtils.isNullOrEmptyString(approvalStatusFilter)) {
            queryBuilder.append(DBConstants.AND).append(DBConstants.SQL_BRACKET_OPEN).append(approvalStatusFilter).append(DBConstants.SQL_BRACKET_CLOSE);
        }
        return true;
    }

    public boolean addSourcesFilter(StringBuilder queryBuilder, String sources, boolean addAnd) {
        return DBUtils.addInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID, sources, false, addAnd);
    }

    /**
     * Add query to filter MDJ news by non supported countries
     *
     * @param query current query
     * @param countries not supported countries
     * @param addAnd adding AND to query
     * @return filter added query
     */
    public boolean addCountryExcludeFilter(StringBuilder query, String countries, boolean addAnd){
        List<String> excludeCountries = Arrays.asList(countries.split(IConstants.COMMA));
        String prefix = IConstants.EMPTY;
        query.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        boolean added = false;
        for (String country : excludeCountries) {
            query.append(prefix);
            added = DBUtils.addNotEqualQueryPhrase(query, DBConstants.NewsDatabaseColumns.COUNTRY,
                    IConstants.COMMA + country + IConstants.COMMA, false, false) || addAnd;
            prefix = DBConstants.CommonDatabaseParams.QUERY_OR;
        }
        query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return added;
    }

    public boolean addSourcesFilterWithExludeHotNewsIindicater(StringBuilder queryBuilder, String sources, String excludedHotNewsIndicater,
                                                               String hotNewsIndicaterExludedSource,
                                                               boolean addAnd) {
        return DBUtils.addNewsCustomQuery(queryBuilder, sources, excludedHotNewsIndicater, hotNewsIndicaterExludedSource, addAnd);
    }

    public boolean addNotSourcesFilter(StringBuilder queryBuilder, String sources, boolean addAnd) {
        return DBUtils.addNotInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID, sources, false, addAnd, false);
    }

    public boolean addExchangeFilter(StringBuilder queryBuilder, String exchanges, boolean addAnd) {
        List<String> exchangeCodes = Arrays.asList(exchanges.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.EXCHANGE, exchangeCodes,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addIndustryFilter(StringBuilder queryBuilder, String industry, boolean addAnd) {
        //todo :: is this gics l3 or l4
        List<String> industries = Arrays.asList(industry.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.INDUSTRY_CODE, industries,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addSymbolFilter(StringBuilder queryBuilder, String symbol, boolean addAnd) {
        List<String> symbols = Arrays.asList(symbol.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.TICKER_ID, symbols,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addCountryFilter(StringBuilder queryBuilder, String country, boolean addAnd) {
        List<String> countries = Arrays.asList(country.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.COUNTRY, countries,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addTopNewsFilter(StringBuilder queryBuilder, String isTopStory, boolean addAnd) {
        return DBUtils.addEqualQueryPhrase(queryBuilder, IS_TOP_STORY, isTopStory, true, addAnd);
    }

    public abstract boolean addNewsDelayBySourceFilter(StringBuilder query, String delaySources);

    public boolean addEditorialCodeFilter(StringBuilder queryBuilder, String editorialCode, boolean addAnd) {
        List<String> editorialCodes = Arrays.asList(editorialCode.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.EDITORIAL_CODE, editorialCodes,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addEditorialCodeFilter(boolean isAssetClassAvailable, StringBuilder queryBuilder, String editorialCode) {
        List<String> editorialCodes = Arrays.asList(editorialCode.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.EDITORIAL_CODE, editorialCodes,
                true, true, false, false, false, false, isAssetClassAvailable, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addAssetClassFilter(StringBuilder queryBuilder, String assetClass, boolean addAnd) {
        List<String> assetClasses = Arrays.asList(assetClass.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.ASSET_CLASS, assetClasses,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addAssetClassFilter(boolean isEditorialAvailable, StringBuilder queryBuilder, String assetClass) {
        List<String> assetClasses = Arrays.asList(assetClass.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.ASSET_CLASS, assetClasses,
                true, true, false, false, false, isEditorialAvailable, false, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addIndividualFilter(StringBuilder queryBuilder, String individuals, boolean addAnd) {
        List<String> individualsList = Arrays.asList(individuals.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.INDIVIDUAL_CODE, individualsList,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addCompanyIdFilter(StringBuilder queryBuilder, String individuals, boolean addAnd) {
        List<String> companyIdList = Arrays.asList(individuals.split(IConstants.COMMA));
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.COMPANY_ID, companyIdList,
                true, true, false, false, false, addAnd, DBConstants.CommonDatabaseParams.QUERY_OR);
    }

    public boolean addHotNewsFilter(StringBuilder queryBuilder, String hotNewsKeys, boolean addAnd) {
        return DBUtils.addInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.HOT_NEWS_INDICATOR, hotNewsKeys, true, addAnd);
    }

    public boolean addNewsIdFilter(StringBuilder queryBuilder, String newsIds, boolean addAnd) {
        return DBUtils.addInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_ID, newsIds, true, addAnd);
    }

    public boolean addNewsIdExcludeFilter(StringBuilder queryBuilder, String newsIds, boolean addAnd) {
        return DBUtils.addNotInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_ID, newsIds, true, addAnd, false);
    }

    public boolean addTopNewsIdIncludeFilter(StringBuilder queryBuilder, String newsIds, boolean addAnd) {
        return DBUtils.addInQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_ID, newsIds, true, addAnd, false);
    }

    public boolean addHeadlineFilter(StringBuilder queryBuilder, String headline, String searchLanguage, boolean addAnd) {
        return DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_HEADLINE_LN + IConstants.Delimiter.UNDERSCORE + searchLanguage.toUpperCase(),
                headline.toUpperCase(), false, false, false, false, true, addAnd);
    }

    public boolean addTimeFilter(int dbType, StringBuilder queryBuilder, String fromDate, String toDate, boolean addAnd){
        return DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.NewsDatabaseColumns.NEWS_DATE, fromDate, toDate, addAnd, dbType, true);
    }

    public boolean addEditionFilter(StringBuilder query, String editionIds, String sectionIds) {
        boolean updated = false;

        if (!CommonUtils.isNullOrEmptyString(editionIds) && !CommonUtils.isNullOrEmptyString(sectionIds)) {
            query.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN.trim());
            String[] editions = editionIds.split(IConstants.COMMA);
            String[] sections = sectionIds.split(IConstants.COMMA);

            for (int i = 0; i <editions.length ; i++) {

                for (int j = 0; j <sections.length ; j++) {

                    if (i > 0 || j > 0) {
                        query.append(DBConstants.CommonDatabaseParams.QUERY_OR);
                    }

                    query.append(DBConstants.NewsDatabaseColumns.TOP_NEWS_EDITION_SECTION);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_LIKE);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_PREC);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN.trim());
                    query.append(editions[i]);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
                    query.append(sections[j]);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE.trim());
                    query.append(DBConstants.CommonDatabaseParams.QUERY_PREC);
                    query.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                }

            }

            query.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE.trim());
            updated = true;
        }

        return updated;
    }

    /**
     * this method is to add language support column ordering
     *
     * @param queryBuilder
     * @param column
     * @param order
     * @param supportedLanguage
     * @return
     */
    public String addOrder(StringBuilder queryBuilder, String column, String order, String supportedLanguage) {
        String sortField = column;
        /**
         * map news request columns with db columns
         */
        if (column.equalsIgnoreCase(HEAD)) {
            sortField = NEWS_HEADLINE_LN + IConstants.Delimiter.UNDERSCORE + supportedLanguage.toUpperCase();
        } else if (column.equalsIgnoreCase(PRV)) {
            sortField = NEWS_PROVIDER;
        } else if (column.equalsIgnoreCase(DT)) {
            sortField = NEWS_DATE;
        }
        return queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_ORDER).append(sortField).append(IConstants.SPACE).append(order.trim()).toString();
    }

    public String addOrder(StringBuilder queryBuilder, String column, String order) {
        return queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_ORDER).append(column).append(order).toString();
    }

    public String getQueryWithPagination(String query, int dbType, int startIndex, int endIndex) {
        return DBUtils.getPaginationQuery(dbType, query, startIndex, endIndex);
    }

    //endregion
}
