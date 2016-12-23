package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.beans.LatestNews;
import com.dfn.alerts.beans.NewsHeadLines;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.dataaccess.orm.impl.News;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 3/22/13
 * Time: 9:54 AM
 */
@SuppressWarnings("unused")
public interface NewsDAO extends CommonDAO {

    /**
     * Get Max seq id from IMDB news table
     * @param language language 
     * @return max id
     */
    String getNewsMaxSeqIdFromOracle(String language);


    /**
     * Get max news date from oracle
     * @param language  news language
     * @return max date
     */
    Date getNewsMaxNewsDateFromOracle(String language);

    /**
     * Get MAX seq form imdb
     * @param language language
     * @return max id
     */
    String getNewsMaxSeqIdFromIMDB(String language);

    /**
     * Get MAX(LAST SYNC TIME) in imdb
     * @param language news language
     * @return query
     */
    Timestamp getMaxSyncDateFromIMDB(String language);

    /**
     * Get MAX seq form imdb
     * @param language language
     * @return max id
     */
    Timestamp getMinNewsDateFromIMDB(String language);

    /**
     * Delete news from 
     * @param lang language
     * @param newsIdsString newsIds to Delete
     * @param sqlQuery query
     * @return  status
     */
    int deleteNewsFromIMDB(String sqlQuery, String lang, String newsIdsString);

    /**
     * 
     * @param lang language
     * @param newsIdList  news ids list
     * @param sqlQuery query
     * @return  status
     */
    int deleteNewsFromOracle(String sqlQuery, List<Integer> newsIdList, String lang);


    /**
     * Search by news ids and return
     * @param queryList  search sql
     * @param language language
     * @return list of matched news id
     */
    List<Integer> searchNewsByNewsId(List<String> queryList, String language);


     /**
     * Search by news ids and return
     * @param newsIdList news ids list to search
     * @param query  search sql
     * @param language news language
     * @return list of matched news id
     */
    List<Integer> searchNewsByNewsIdOracle(List<Integer> newsIdList, String query, String language);

    /**
     * Keep limited number of news in IMDB and remove rest
     * Oracle has all news
     * @param language News lang
     * @param maxNewsCount news count
     * @return number of success items
     */
    int deleteNewsFromIMDB(String language, int maxNewsCount);

    int transferNewsFromIMDBToRDBMS(List<News> newsList);

    /**
     * Getting all news which is need to transfer from IMDB to Oracle
     * @param lang news language
     * @param maxNewsCount Max news count which is need to keep in IMDB
     * @param sql sql to use
     * @return news map
     */
    NewsHeadLines getNewsToTransfer(String lang, int maxNewsCount, String sql);

    /**
     * Insert or update news to IMDB
     * @param requestDBDTO object which is contains all news to insert and update.
     * @param isTagFields true if news tagging required
     * @return number of news inserted and updated
     */
    int insertUpdateNewsToIMDB(RequestDBDTO requestDBDTO, boolean isTagFields);

    /**
     * Insert or update news to Oracle
     * @param requestDBDTO object which is contains all news to insert and update.
     * @param isTagFields true if news tagging required
     * @return number of news inserted and updated
     */
    int insertUpdateNewsToOracle(RequestDBDTO requestDBDTO, boolean isTagFields);

    /**
     * Update news individuals to oracle
     * @param requestDBDTO object which is contains all news individuals to update
     * @return number of news individuals updated
     */
    int updateNewsIndividualsToOracle(RequestDBDTO requestDBDTO);

    List<String> insertUpdateNewsToIMDBFromOracle(RequestDBDTO requestDBDTO, boolean isTagFields);

    /**
     * Search news from IMDB for give criteria
     * @param sql DB query to execute in IMDB
     * @return NewsHeadLines object
     */
    NewsHeadLines searchNewsFromImdb(String sql);

    /**
     * Search total news count from Oracle DB for give criteria
     * @param sql DB query to execute in IMDB
     * @return NewsHeadLines object
     */
    NewsHeadLines searchTotalNewsCountFromOracle(String sql);


    /**
     * Search news from oracle
     * @param requestData request filters
     * @return matched news
     */
    NewsHeadLines searchNewsFromOracle(Map<String, String> requestData);


    /**
     * News home page specific method
     * @param sqlQuery sql to run in database
     * @return matched news
     */
    NewsHeadLines searchAllSectionsNewsFromImdb(String sqlQuery);


    /**
     * News home page specific method
     * @param requestDBDTO sql and
     * @return matched news
     */
    NewsHeadLines searchAllSectionsNewsFromOracle(RequestDBDTO requestDBDTO);

    /**
     * People news page specific method
     * @param requestDBDTO sql
     * @return matched news
     */
    NewsHeadLines searchNewsIndividualsFromOracle(RequestDBDTO requestDBDTO);

    /**
     * Get MAX news date form Oracle
     * @param requestDBDTO request data
     * @return max news data in oracle
     */
    java.sql.Date getMaxNewsDateInOracle(RequestDBDTO requestDBDTO);


    /**
     * Get Top stories entry List from TOP_NEWS_HISTORY(IS_NEWS_TAB_UPDATED = 1) to update NEWS table.
     *
     * @return TopNewsDataDTO list
     */
    Object getTopNewsHistoryForUpdate(RequestDBDTO requestDBDTO);

    /**
     * Method to update top stories in IMDB NEWS table from TOP_NEWS_HISTORY table.
     *
     * @param requestDBDTO request object
     * @return updated news id list
     */
    Object updateTopStoriesIMDB(RequestDBDTO requestDBDTO);

    /**
     * Method to update top stories in Oracle NEWS table from TOP_NEWS_HISTORY table.
     *
     * @param requestDBDTO request object
     * @return updated news id list
     */
    Object updateTopStoriesOracle(RequestDBDTO requestDBDTO);

    /**
     * Mark list of top stories in TOP_NEWS_HISTORY as updated in NEWS tables (IS_NEWS_TAB_UPDATED = 1)
     *
     * @param requestDBDTO
     * @return
     */
    int markTopStoriesAsUpdated(RequestDBDTO requestDBDTO);


    LatestNews getLatestNewsFormOracle(Timestamp lastSyncTime, Timestamp minNewsDateIMDB, String language);


    boolean updateOracleNodeStatus(String nodeId, String language, String newsId);
}
