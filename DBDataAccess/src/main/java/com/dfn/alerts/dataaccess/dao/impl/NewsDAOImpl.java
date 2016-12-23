package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.LatestNews;
import com.dfn.alerts.beans.NewsHeadLines;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.TopNewsDataDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.News;
import com.dfn.alerts.dataaccess.dao.NewsDAO;
import com.dfn.alerts.dataaccess.utils.CriteriaGenerator;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.dataaccess.utils.NewsDBHelper;
import com.dfn.alerts.dataaccess.utils.news.NewsQueryGenerator;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 3/20/13
 * Time: 10:42 AM
 */
public class NewsDAOImpl implements NewsDAO , InitializingBean {

    public static final Logger LOG = LogManager.getLogger(NewsDAOImpl.class);
    
    private SessionFactory sessionFactory = null;
    
    private DataSource imdbDriverManagerDataSource;

    private DataSource oracleDriverManagerDataSource;

    private CriteriaGenerator criteriaGenerator;

    private NewsQueryGenerator newsQueryGenerator ;

    private static final String SELECT_LATEST_NEWS   = " SELECT * FROM NEWS WHERE LAST_SYNC_TIME > ? AND NEWS_DATE > ? AND LANGUAGE_ID = ? ";

    private static final String UPDATE_ORACLE_NEWS_NODE_STATUS  = " UPDATE NEWS SET  {0}_STATUS = ? WHERE LANGUAGE_ID = ? AND NEWS_ID IN ({1})";

    private static final String NEWS_MAX_SEQ_ID_IMDB_QUERY = "SELECT MAX(SEQ_ID) FROM NEWS WHERE LANGUAGE_ID = ?";
    private static final String NEWS_MAX_LAST_SYNC_TIME_IMDB_QUERY = "SELECT MAX(LAST_SYNC_TIME) FROM NEWS WHERE LANGUAGE_ID = ?";

    private static final String NEWS_MIN_NEWS_DATE_IMDB_QUERY = "SELECT MIN(NEWS_DATE) FROM NEWS WHERE LANGUAGE_ID = ?";

    private static final String REMOVE_OLD_NEWS_FROM_IMDB = "DELETE FROM NEWS WHERE LANGUAGE_ID=? AND NEWS_ID IN (SELECT NEWS_ID FROM NEWS WHERE LANGUAGE_ID=? ORDER BY NEWS_DATE DESC,SEQ_ID DESC OFFSET ? ROWS)";

    public static final int DEFAULT_NEWS_COUNT = 20;

    public static final int DEFAULT_NEWS_PRIORITY = 5;
    public static final int COMMIT_BATCH_SIZE = 50;
    public static final int TOP_NEWS_COMMIT_BATCH_SIZE = 10;
    public static final int MAX_NO_OF_CHAR = 198;
    public static final int MAX_NO_OF_SYMBOL_CHAR  = 1998;
    public static final int MAX_NO_OF_COUNTRY_CHAR =  998;
    public static final int MAX_HEADLINE_LENGTH = 4000;
    public static final int ZERO_INDEX = 0;
    public static final int ZERO_VALUE = 0;

    private static final String MODIFIED_N  = "M";

    private static final String NEW_N       = "N";

    private static final String DELETED_N   = "D";

    private static final String LOG_PREFIX = " :: NewsDAOImpl :: ";
    
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.sessionFactory, "sessionFactory is mandatory");
        Assert.notNull(this.imdbDriverManagerDataSource, "imdbDriverManagerDataSource is mandatory");
        Assert.notNull(this.oracleDriverManagerDataSource, "oracleDriverManagerDataSource is mandatory");
        Assert.notNull(this.criteriaGenerator, "criteriaGenerator is mandatory");
    }

    /**
     * Get Max seq id from IMDB news table
     * @param language language
     * @return max id
     */
    
    public String getNewsMaxSeqIdFromOracle(String language) {
        Object lastUpdatedSeqId = null;
        Session session = null;
        String returnVal = "-1";
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(News.class);
            criteria.add(Restrictions.like("languageId", language));
            criteria.setProjection(Projections.max("seqId"));
            lastUpdatedSeqId = criteria.uniqueResult();
        } catch (HibernateException e) {
            LOG.error("Error while opening a new session" + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        if(lastUpdatedSeqId != null){
            returnVal =  Integer.toString((Integer)lastUpdatedSeqId);
        }

        return   returnVal;
    }

    /**
     * Get max news date from oracle
     * @param language  news language
     * @return max date
     */
    
    public Date getNewsMaxNewsDateFromOracle(String language) {
        Object maxDate = null;
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(News.class);
            criteria.add(Restrictions.like("languageId", language));
            criteria.setProjection(Projections.max("newsDate"));
            maxDate = criteria.uniqueResult();
        } catch (HibernateException e) {
            LOG.error("Error while opening a new session" + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return (Date)maxDate;
    }


    /**
     * Get MAX deq form oracle
     * @param language language
     * @return max id
     */
    
    public String getNewsMaxSeqIdFromIMDB(String language) {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        String maxSeqId = null;
        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(NEWS_MAX_SEQ_ID_IMDB_QUERY);
            preparedStatement.setString(1, language);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                maxSeqId  = rs.getString(1);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return maxSeqId;
    }

    /**
     * Get MAX deq form oracle
     *
     * @param language language
     * @return max id
     */

    public Timestamp getMaxSyncDateFromIMDB(String language) {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        Timestamp timestamp = null;
        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(NEWS_MAX_LAST_SYNC_TIME_IMDB_QUERY);
            preparedStatement.setString(1, language);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                timestamp  = rs.getTimestamp(1);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return timestamp;
    }


    /**
     * Get MIN news date form IMDB
     * @param language language
     * @return min news date
     */

    public Timestamp getMinNewsDateFromIMDB(String language) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        Timestamp minNewsDate = null;
        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(NEWS_MIN_NEWS_DATE_IMDB_QUERY);
            preparedStatement.setString(1, language);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                minNewsDate  = rs.getTimestamp(1);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return minNewsDate;
    }

    /**
     * Delete news from
     * @param lang language
     * @param newsIdsString newsIds to Delete
     * @param sqlQuery query
     * @return  status
     */
    @SuppressWarnings("unchecked")

    public int deleteNewsFromIMDB(String sqlQuery, String lang , String newsIdsString) {
        int status = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = sqlQuery.replace("\\", "");
        sql =  MessageFormat.format(sql, newsIdsString);
        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lang);
            status = preparedStatement.executeUpdate();
            connection.commit();
            LOG.debug("<!--News Delete from IMDB on News Update Completed | Count :" + status);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            status = 0;
            LOG.error("<!--SQL exception occurred in deleting news from IMDB..");
            LOG.error(sqlE.getMessage(), sqlE);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException ex) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            status = 0;
            LOG.error("<!--SQL exception occurred in deleting news from IMDB..");
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }

        return status;
    }


    /**
     *
     * @param lang language
     * @param newsIdList  news ids list
     * @param sqlQuery query
     * @return  status
     */

    public int deleteNewsFromOracle(String sqlQuery, List<Integer> newsIdList ,String lang ) {
        int status = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int index = 1;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlQuery.replace("\\", ""));
            for(int newsId : newsIdList ){
                preparedStatement.setInt(index++, newsId);
            }
            preparedStatement.setString(index, lang);
            status = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            status = 0;
            LOG.error("<!--SQL exception occurred in deleting news from RDBMS..");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement,null);
        }

        return status;
    }


    /**
     * Search by news ids and return
     * @param queryList  search sql
     * @param language language
     * @return list of matched news id
     */

    public List<Integer> searchNewsByNewsId(List<String> queryList , String language) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(LOG_PREFIX + "Start searchNewsByNewsId:.. ");
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> availableNewsIdList = null;

        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            long startTime = System.currentTimeMillis();
            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "prepared statement.. " + startTime);
            }
            for(String query : queryList){
                preparedStatement = connection.prepareStatement(query.replace("\\", ""));
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "prepared statement end " + (System.currentTimeMillis() - startTime));
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "after setting news ids ... ");
                }
                preparedStatement.setString(1, language);
                resultSet = preparedStatement.executeQuery();
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "after execution ... ");
                }
                if (resultSet != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + "result set not null ... ");
                    }
                    if(availableNewsIdList == null) {
                        availableNewsIdList = new ArrayList<Integer>();
                    }
                    while (resultSet.next()) {
                        int newsId = resultSet.getInt(DBConstants.NewsDatabaseColumns.NEWS_ID);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(LOG_PREFIX + "Available News : " + newsId);
                        }
                        availableNewsIdList.add(newsId);
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "End searchNewsByNewsId... ");
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, resultSet);
        }

        return availableNewsIdList;
    }



    /**
     * Search by news ids and return
     * @param newsIdList news ids list to search
     * @param query  search sql
     * @param language news language
     * @return list of matched news id
     */

    public List<Integer> searchNewsByNewsIdOracle(List<Integer> newsIdList , String query , String language ) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int index = 1;
        ResultSet resultSet = null;
        List<Integer> availableNewsIdList = null;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(query.replace("\\", ""));
            for (int newsId : newsIdList) {
                preparedStatement.setInt(index++, newsId);
            }
            preparedStatement.setString(index, language);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                availableNewsIdList = new ArrayList<Integer>();
                while (resultSet.next()) {
                    availableNewsIdList.add(resultSet.getInt(DBConstants.NewsDatabaseColumns.NEWS_ID));
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, resultSet);
        }

        return availableNewsIdList;
    }

    /**
     * delete excess news from IMDB
     * @param language
     * @param maxNewsCount
     * @return
     */
    public int deleteNewsFromIMDB(String language, int maxNewsCount){
        int count = -1;
        Connection imdbConnection = null;
        PreparedStatement statement = null;
        try{
            imdbConnection  = imdbDriverManagerDataSource.getConnection();
            imdbConnection.setAutoCommit(true);
            statement = imdbConnection.prepareStatement(REMOVE_OLD_NEWS_FROM_IMDB);
            statement.setString(1, language);
            statement.setString(2, language);
            statement.setInt(3, maxNewsCount);
            count = statement.executeUpdate();
        }catch (SQLException sqlE){
            LOG.error(sqlE.getMessage(), sqlE);
        }finally {
             DBUtils.finalizeDBResources(imdbConnection, statement, null);
        }
        return count;
    }

    /**
     * Keep limited number of news in IMDB and transfer rest to Oracle at EOD
     * Step 1 : Insert to Oracle
     * Step 2 : Delete from IMDB
     *
     * Since couple of databases are involved for this, We need to handle distributed transactions here.
     * Simple  two-phase commit protocol used here
     *
     *
     * @param newsList News Objects List to transfer
     * @return number of success items
     */

    public int transferNewsFromIMDBToRDBMS(List<News> newsList) {
        Session session                     = null;
        Transaction tx                      = null;
//        Connection imdbConnection           = null;
        boolean abnormalExit                = false;

        int numberOfNewsToTransfer      =  newsList.size();
        int commitCount                 = 1000;
        int counter                     = 0;

        List<Integer> successNewsSeqs        = new ArrayList<Integer>();

        //List to hold news seq ids which is Failed to insert to Oracle and not deleting from IMDB
        List<Integer> failedNewsSeqs         = new ArrayList<Integer>();

//        List<Integer> failedNewsInIMDBSeqs   = new ArrayList<Integer>();


//        StringBuilder newsSeqIds       =  new StringBuilder();
        List<Integer> newsSeqIdsList   =  new ArrayList<Integer>();
//        String        newsSeqIdsString =  null;

        try{
            session         = this.sessionFactory.openSession();
            tx              = session.beginTransaction();
//            imdbConnection  = imdbDriverManagerDataSource.getConnection();

            Collections.reverse(newsList);
            //Go through the news list, one by one
            for (News news : newsList) {
                try{
//                    System.out.println(" News Date " + news.getNewsDate());
                    insertUpdateNewsToRDBMS(session, news);
                }catch (HibernateException e) {
                    //Do not delete news from IMDB if any exception during save or update
                    //continue to next record
                    //Manually correct this record
                    LOG.error(" EOD News insert to Oracle failed!" + news.getSeqId() );
                    failedNewsSeqs.add(news.getSeqId());

                    tx.rollback();
                    //if any error, break this and inform dev team about his error
                    abnormalExit = true;
                    break;
                }

                counter++;

                //Oracle insert update success. add this to IMDB delete list
//                newsSeqIdsString = addToString(newsSeqIds, news.getSeqId());
                newsSeqIdsList.add(news.getSeqId());

                //Time to commit Oracle inserts and delete from IMDB
                //reached commit count or end of list
                if ((counter % commitCount == 0) || (numberOfNewsToTransfer == counter) ) {
                    System.out.println("counter " + counter);
                    System.out.println("(counter % commitCount == 0) " + (counter % commitCount == 0));
                    System.out.println("(numberOfNewsToTransfer == counter)" + (numberOfNewsToTransfer == counter));
                    //reset seq IDs
                    //Delete news from IMDB
//                    try{

//                        imdbConnection.setAutoCommit(false);

//                        String sql = MessageFormat.format(NEWS_EOD_IMDB_DELETE, newsSeqIdsString);

//                        deleteNewsFromIMDB(sql , imdbConnection);

                        session.flush();
                        session.clear();
                        tx.commit();

                        //of Oracle success then commit IMDB
                        if(tx.getStatus() == TransactionStatus.COMMITTED){
//                            imdbConnection.commit();
                        }else {
                            failedNewsSeqs.addAll(newsSeqIdsList);
                            LOG.error(" Oracle insert/update commit failed ");

                            tx.rollback();
//                            imdbConnection.rollback();
                            abnormalExit = true;
                            break;
                        }

                        successNewsSeqs.addAll(newsSeqIdsList);

//                    }catch (SQLException e) {
//                        failedNewsInIMDBSeqs.addAll(newsSeqIdsList);
//                        try{
//                            tx.rollback();
//                            imdbConnection.rollback();
//                            LOG.error(" Insert to Oracle was success but delete from IMDB was failed ! Correct this manually");
//                        }catch (SQLException imdbE){
//                            LOG.error(imdbE.getMessage(), imdbE);
//                        }
//                        abnormalExit = true;
//                        break;
//                    }

//                    newsSeqIds       = new StringBuilder();
//                    newsSeqIdsString = null;
                    newsSeqIdsList   = new ArrayList<Integer>();

                    tx = session.beginTransaction();

                }
            }
//        }catch (SQLException sqlE){
//            LOG.error(sqlE.getMessage(), sqlE);
        }finally {

            if(abnormalExit){
                LOG.error(" Abnormal Exit from the loop.");
            }

            if(LOG.isDebugEnabled()){
                LOG.debug(successNewsSeqs + " Successfully Transferred");
                LOG.debug(failedNewsSeqs +  " Failed while Transferring to Oracle");
//                LOG.debug(failedNewsInIMDBSeqs +  " Successfully Transferred to Oracle Failed while deleting from IMDB");
            }

//            if(imdbConnection != null){
//
//                try{
//                    imdbConnection.close();
//                }catch ( SQLException e){
//                    LOG.error("Error while closing connection " + e);
//                }
//            }

            if (session != null) {
                session.close();
            }
        }

        return successNewsSeqs.size();

    }


    /**
     * Getting all news which is need to transfer from IMDB to Oracle
     * @param lang news language
     * @param maxNewsCount Max news count which is need to keep in IMDB
     * @param sql sql to use
     * @return news map
     */

    public NewsHeadLines getNewsToTransfer(String lang, int maxNewsCount, String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        Map<String, String> newsItem;
        List<News> newsPersistList;
        News news;
        ArrayList<Integer> newsIdList;


        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(sql.replace("\\", ""));
            preparedStatement.setString(1, lang);
            preparedStatement.setInt(2, maxNewsCount);
            rs = preparedStatement.executeQuery();
            newsIdList = new ArrayList<Integer>();
            newsPersistList = new ArrayList<News>();
            while (rs.next()) {
                newsItem = DBUtils.setNewsItem(rs);  //set news items attribute/data map
                news = new News();
                DBUtils.setNewsPersistItem(news, newsItem);
                newsPersistList.add(news);
            }
            newsHeadLines = new NewsHeadLines();
            if (newsPersistList.size() > 0) {
                newsHeadLines.setMinSeqId(newsPersistList.get(newsPersistList.size() - 1).getNewsId());
            }
            newsHeadLines.setDataList(newsPersistList);
            newsHeadLines.setSeqIdList(newsIdList);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }



    /**
     * Get MAX news date form Oracle
     * @param requestDBDTO request data
     * @return max news data in oracle
     */

    public java.sql.Date getMaxNewsDateInOracle(RequestDBDTO requestDBDTO){
        java.sql.Date maxDate = null;
        Connection connection = null ;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery());
            preparedStatement.setString(1, requestDBDTO.getParams());//language
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                maxDate = rs.getDate(1);//only date requested
            }
        }catch (SQLException sqlE){
            LOG.error(sqlE.getMessage(), sqlE);
        }finally {
            DBUtils.finalizeDBResources(connection,preparedStatement,rs);
        }
        return maxDate;
    }


    /**
     * Insert or update news to IMDB
     * @param requestDBDTO object which is contains all news to insert and update.
     * @param isTagFields true if news tagging required
     * @return number of news inserted and updated
     */

    public int insertUpdateNewsToIMDB(RequestDBDTO requestDBDTO, boolean isTagFields) {
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement updatePreparedStatement = null;
        Connection connection = null;
        int totalUpdateCount = 0;
        int totalInsertCount = 0;
        int insertCount = 0;
        int updateCount = 0;

        List<Map<String,String>> newsList = new ArrayList<Map<String, String>>();
        newsList.addAll((Collection)requestDBDTO.getData());
        String insertQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.INSERT_QUERY);
        String updateQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.UPDATE_QUERY);
        List<Integer> availableNewsIdListInIMDB = (List<Integer>) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.AVAILABLE_SEQ_ID_LIST);

        Set<Integer> allNewsIdSet = new HashSet<Integer>();

        try {
            connection = imdbDriverManagerDataSource.getConnection();
            insertPreparedStatement = connection.prepareStatement(insertQuery.replace("\\", ""));
            updatePreparedStatement = connection.prepareStatement(updateQuery.replace("\\", ""));
            for (Map<String, String> news : newsList) {
                int newsId = Integer.valueOf(news.get(IConstants.MIXDataField.ID));

                if(allNewsIdSet.contains(newsId)){
                    continue;
                }else{
                    allNewsIdSet.add(newsId);
                }

                if (availableNewsIdListInIMDB.contains(newsId)) {
                    setNewsUpdateColumnIMDBValues(updatePreparedStatement, news, isTagFields);
                    updatePreparedStatement.addBatch();
                    updateCount++;
                    if (updateCount > 0 && updateCount % COMMIT_BATCH_SIZE == 0) {
                        totalUpdateCount += executeInsertUpdateBatch(updatePreparedStatement);
                    }
                } else {
                    setNewsUpdateColumnIMDBValues(insertPreparedStatement, news, isTagFields);
                    insertPreparedStatement.addBatch();
                    insertCount++;
                    if (insertCount > 0 && insertCount % COMMIT_BATCH_SIZE == 0) {
                        totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
                    }
                }
            }

            if (updateCount > 0) {
                totalUpdateCount += executeInsertUpdateBatch(updatePreparedStatement);
            }

            if (insertCount > 0) {
                totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
            }

            LOG.debug("<!--News update completed-->");
            LOG.debug("<!--News count to be inserted/Updated : " + newsList.size() + " | Updated/inserted News Count : " + totalUpdateCount + totalInsertCount);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            LOG.error("<!--SQL exception occurred..");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, new PreparedStatement[]{insertPreparedStatement,updatePreparedStatement}, null);
        }
        return totalUpdateCount + totalInsertCount;
    }
 /**
     * Insert or update news to IMDB
     * @param requestDBDTO object which is contains all news to insert and update.
     * @param isTagFields true if news tagging required
     * @return number of news inserted and updated
     */

    public List<String> insertUpdateNewsToIMDBFromOracle(RequestDBDTO requestDBDTO, boolean isTagFields) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(LOG_PREFIX + "insertUpdateNewsToIMDBFromOracle.. ");
        }
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement updatePreparedStatement = null;
        Connection connection = null;
        int insertCount = 0;
        int updateCount = 0;


        StringBuilder newsIdsAllSB           =  new StringBuilder();
        List<String>   newsIdsAllList        = new ArrayList<String>();

        StringBuilder newsIdsInsertSBTemp        =  new StringBuilder();
        String        newsIdsInsertStringTemp    =  null;

        StringBuilder newsIdsUpdateSBTemp        =  new StringBuilder();
        String        newsIdsUpdateStringTemp    =  null;


        List<Map<String,String>> newsList  = (List)requestDBDTO.getData();

        String insertQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.INSERT_QUERY);
        String updateQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.UPDATE_QUERY);

        List<Integer> availableNewsIdListInIMDB = (List<Integer>) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.AVAILABLE_SEQ_ID_LIST);


        try {
            connection = imdbDriverManagerDataSource.getConnection();
            insertPreparedStatement = connection.prepareStatement(insertQuery.replace("\\", ""));
            updatePreparedStatement = connection.prepareStatement(updateQuery.replace("\\", ""));
            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "iterating newsList.. ");
            }
            for (Map<String, String> news : newsList) {
                int newsId = Integer.valueOf(news.get(IConstants.MIXDataField.ID));

                if (availableNewsIdListInIMDB != null && availableNewsIdListInIMDB.contains(newsId)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + "availableNewsIdListInIMDB.contains : " + newsId);
                    }
                    newsIdsUpdateStringTemp    = addToString(newsIdsUpdateSBTemp, newsId);
                    setNewsUpdateColumnIMDBValues(updatePreparedStatement, news, isTagFields);
                    updatePreparedStatement.addBatch();
                    updateCount++;
                    if ((updateCount > 0 && updateCount % COMMIT_BATCH_SIZE == 0)) {
                        try {
                            executeInsertUpdateBatch(updatePreparedStatement);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(LOG_PREFIX + "executeInsertUpdateBatch ::: ");
                            }
                            if(newsIdsAllSB.length() > 0){
                                newsIdsAllSB.append(",");
                            }
                            newsIdsAllSB.append(newsIdsUpdateStringTemp);
                        }catch (SQLException e){
                            //batch update failed..
                            //ignore those news
                            LOG.error("error in update : insertUpdateNewsToIMDBFromOracle()", e);
                        } finally {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("-------------------- insertUpdateNewsToIMDBFromOracle @ news update : batch news id string : " + newsIdsUpdateStringTemp);
                            }
                            newsIdsAllList.add(newsIdsUpdateStringTemp);
                            newsIdsUpdateStringTemp = null;
                            newsIdsUpdateSBTemp = new StringBuilder();
                        }
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + "availableNewsIdListInIMDB not contains .. " + newsId);
                    }
                    newsIdsInsertStringTemp    = addToString(newsIdsInsertSBTemp, newsId);
                    setNewsUpdateColumnIMDBValues(insertPreparedStatement, news, isTagFields);
                    insertPreparedStatement.addBatch();
                    insertCount++;
                    if (insertCount > 0 && insertCount % COMMIT_BATCH_SIZE == 0) {
                        try {
                            executeInsertUpdateBatch(insertPreparedStatement);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(LOG_PREFIX + "executeInsertUpdateBatch >> ");
                            }
                            if(newsIdsAllSB.length() > 0){
                                newsIdsAllSB.append(",");
                            }
                            newsIdsAllSB.append(newsIdsInsertStringTemp);
                        }catch (SQLException e){
                            //batch update failed..
                            //ignore those news
                            LOG.error("error in insert : insertUpdateNewsToIMDBFromOracle()", e);
                        } finally {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("-------------------- insertUpdateNewsToIMDBFromOracle @ news insert : batch news id string : " + newsIdsInsertStringTemp);
                            }
                            newsIdsAllList.add(newsIdsInsertStringTemp);
                            newsIdsInsertSBTemp           =  new StringBuilder();
                            newsIdsInsertStringTemp    =  null;
                        }
                    }
                }
            }


            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "end iterating newsList.. ");
            }

            if (updateCount > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "update count>0.. ");
                }
                try {
                    executeInsertUpdateBatch(updatePreparedStatement);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + "executeInsertUpdateBatch.. ");
                    }
                    if(newsIdsAllSB.length() > 0){
                        newsIdsAllSB.append(",");
                    }
                    newsIdsAllSB.append(newsIdsUpdateStringTemp);
                }catch (SQLException e){
                    //batch update failed..
                    //ignore those news
                    LOG.error("error in update : insertUpdateNewsToIMDBFromOracle()", e);
                } finally {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-------------------- insertUpdateNewsToIMDBFromOracle @ news update : batch news id string : " + newsIdsUpdateStringTemp);
                    }
                    newsIdsAllList.add(newsIdsUpdateStringTemp);
                }
            }

            if (insertCount > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + "insert count>0.. ");
                }
                try {
                    executeInsertUpdateBatch(insertPreparedStatement);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(LOG_PREFIX + "executeInsertUpdateBatch insert.. ");
                    }
                    if(newsIdsAllSB.length() > 0){
                        newsIdsAllSB.append(",");
                    }
                    newsIdsAllSB.append(newsIdsInsertStringTemp);
                }catch (SQLException e){
                    //batch update failed..
                    //ignore those news
                    LOG.error("error in insert : insertUpdateNewsToIMDBFromOracle()", e);
                } finally {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-------------------- insertUpdateNewsToIMDBFromOracle @ news insert : batch news id string : " + newsIdsInsertStringTemp);
                    }
                    newsIdsAllList.add(newsIdsInsertStringTemp);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("<!--News update completed-->");
            }
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            LOG.error("<!--SQL exception occurred..");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, new PreparedStatement[]{insertPreparedStatement,updatePreparedStatement}, null);
        }

        return newsIdsAllList;
    }

    /**
     * Insert or update news to Oracle
     * @param requestDBDTO object which is contains all news to insert and update.
     * @param isTagFields true if news tagging required
     * @return number of news inserted and updated
     */

    public int insertUpdateNewsToOracle(RequestDBDTO requestDBDTO, boolean isTagFields) {
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement updatePreparedStatement = null;
        Connection connection = null;
        int totalUpdateCount = 0;
        int totalInsertCount = 0;
        int insertCount = 0;
        int updateCount = 0;

        List<Map<String,String>> newsList = new ArrayList<Map<String, String>>();
        newsList.addAll((Collection)requestDBDTO.getData());
        String insertQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.INSERT_QUERY);
        String updateQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.UPDATE_QUERY);
        List<Integer> availableNewsIdListInOracle = (List<Integer>) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.AVAILABLE_SEQ_ID_LIST);

        Set<Integer> allNewsIdSet = new HashSet<Integer>();

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            insertPreparedStatement = connection.prepareStatement(insertQuery.replace("\\", ""));
            updatePreparedStatement = connection.prepareStatement(updateQuery.replace("\\", ""));
            for (Map<String, String> news : newsList) {
                int newsId = Integer.valueOf(news.get(IConstants.MIXDataField.ID));

                if(allNewsIdSet.contains(newsId)){
                    continue;
                }else{
                    allNewsIdSet.add(newsId);
                }

                if (availableNewsIdListInOracle.contains(newsId)) {
                    setNewsUpdateColumnValues(updatePreparedStatement, news, isTagFields);
                    updatePreparedStatement.addBatch();
                    updateCount++;
                    if (updateCount > 0 && updateCount % COMMIT_BATCH_SIZE == 0) {
                        totalUpdateCount += executeInsertUpdateBatch(updatePreparedStatement);
                        updateCount = 0;
                    }
                } else {
                    setNewsUpdateColumnValues(insertPreparedStatement, news, isTagFields);
                    insertPreparedStatement.addBatch();
                    insertCount++;
                    if (insertCount > 0 && insertCount % COMMIT_BATCH_SIZE == 0) {
                        totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
                        insertCount = 0;
                    }
                }
            }

            if (updateCount > 0) {
                totalUpdateCount += executeInsertUpdateBatch(updatePreparedStatement);
            }

            if (insertCount > 0) {
                totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
            }

            LOG.debug("<!--News update completed-->");
            LOG.debug("<!--News count to be inserted/Updated : " + newsList.size() + " | Updated/inserted News Count : " + totalUpdateCount + totalInsertCount);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            LOG.error("<!--SQL exception occurred..");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, new PreparedStatement[]{insertPreparedStatement,updatePreparedStatement}, null);
        }
        return totalUpdateCount + totalInsertCount;
    }

    /**
     * Insert or update news to Oracle
     * @param requestDBDTO object which is contains all news to insert and update.
     * @return number of news inserted and deleted
     */
    public int updateNewsIndividualsToOracle(RequestDBDTO requestDBDTO) {
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement deletePreparedStatement = null;
        Connection connection = null;
        int totalInsertCount = 0;
        int totalDeleteCount = 0;
        int insertCount = 0;

        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        newsList.addAll((Collection) requestDBDTO.getData());
        String insertQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.INSERT_QUERY);
        String deleteQuery = (String) requestDBDTO.getCustomParams().get(DBConstants.CustomParameters.DELETE_QUERY);

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            insertPreparedStatement = connection.prepareStatement(insertQuery.replace("\\", ""));
            deletePreparedStatement = connection.prepareStatement(deleteQuery.replace("\\", ""));

            int index = 0;
            for (Map<String, String> news : newsList) {
                index++;
                setNewsIndividualsDeleteColumnValues(deletePreparedStatement, news, index);
            }
            deletePreparedStatement.addBatch();
            totalDeleteCount = executeInsertUpdateBatch(deletePreparedStatement);
            LOG.debug("<!--News count deleted : " + totalDeleteCount);

            for (Map<String, String> news : newsList) {
                String [] indvArray = processCommaSeparatedString(news.get(IConstants.MIXDataField.INDV));
                if (indvArray != null) {
                    for (String indvStr : indvArray) {
                        int indvId = processInt(indvStr);
                        if (indvId > 0) {
                            setNewsIndividualsInsertColumnValues(insertPreparedStatement, news, indvId);
                            insertPreparedStatement.addBatch();
                            insertCount++;
                        }
                    }
                }
                if (insertCount >= COMMIT_BATCH_SIZE) {
                    totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
                    insertCount = 0;
                }
            }

            if (insertCount > 0) {
                totalInsertCount += executeInsertUpdateBatch(insertPreparedStatement);
            }

            LOG.debug("<!--News individuals update completed-->");
            LOG.debug("<!--News count to be inserted/Updated : " + newsList.size() + " | Updated/inserted News Count : " + totalInsertCount);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            LOG.error("<!--SQL exception occurred..");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, new PreparedStatement[]{insertPreparedStatement, deletePreparedStatement}, null);
        }
        return totalInsertCount;
    }

    /**
     * Search news from IMDB for give criteria
     * @param sql DB query to execute in IMDB
     * @return NewsHeadLines object
     */

    public NewsHeadLines searchNewsFromImdb(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        List<Map<String, String>> newsList;
        Map<String, String> newsItem;
        List<Integer> newsIdList;

        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(sql.replace("\\", ""));
            rs = preparedStatement.executeQuery();

            newsList = new ArrayList<Map<String, String>>(DEFAULT_NEWS_COUNT);
            newsIdList = new ArrayList<Integer>(DEFAULT_NEWS_COUNT);

            while (rs.next()) {
                newsItem = DBUtils.setNewsItem(rs);  //set news items attribute/data map
                newsList.add(newsItem);
                newsIdList.add(Integer.valueOf(newsItem.get(DBConstants.NewsDatabaseColumns.NEWS_ID)));
            }

            newsHeadLines = new NewsHeadLines();
            newsHeadLines.setNews(newsList);
            newsHeadLines.setNewsIdList(newsIdList);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }

    public NewsHeadLines searchTotalNewsCountFromOracle(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        int totalNewsCount = 0;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql.replace("\\", ""));
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
              totalNewsCount = rs.getInt(1);
            }
            newsHeadLines = new NewsHeadLines();
            newsHeadLines.setTotalCount(totalNewsCount);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }

    /**
     * Search news from oracle
     * @param requestData request filters
     * @return matched news
     */

    public NewsHeadLines searchNewsFromOracle(Map<String, String> requestData) {
        Session session = null;
        List<Object> newsObjList;
        List<Integer> newsIdList;
        List<Map<String, String>> newsList;
        NewsHeadLines newsHeadLines = null;
        Map<String, String> aNews;

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(News.class);
            this.criteriaGenerator.generateCriteria(criteria, requestData);
            newsObjList = criteria.list();
            if (newsObjList != null) {
                newsList = new ArrayList<Map<String, String>>(newsObjList.size());
                newsIdList = new ArrayList<Integer>(newsObjList.size());

                for (Object obj : newsObjList) {
                    aNews = ((News) obj).accessNewsHeadLine();
                    newsList.add(aNews);
                    newsIdList.add(Integer.valueOf(aNews.get(DBConstants.NewsDatabaseColumns.NEWS_ID)));
                }
                newsHeadLines = new NewsHeadLines();
                newsHeadLines.setNews(newsList);
                newsHeadLines.setNewsIdList(newsIdList);
            }
        } catch (HibernateException hie) {
            if(LOG.isDebugEnabled()){
                LOG.debug("Error occurred while retrieving data from RDBMS.." + hie);
            }
        } catch (Exception ignored){

        }finally {
            if (session != null) {
                session.close();
            }
        }
        return newsHeadLines;
    }


    /**
     * News home page specific method
     * @param sqlQuery sql to run in database
     * @return matched news
     */

    public NewsHeadLines searchAllSectionsNewsFromImdb(String sqlQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        List<Map<String, String>> newsList;
        Map<String, String> newsItem;

        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            preparedStatement = connection.prepareStatement(sqlQuery.replace("\\", ""));
            rs = preparedStatement.executeQuery();
            newsList = new ArrayList<Map<String, String>>(DEFAULT_NEWS_COUNT);
            while (rs.next()) {
                newsItem = DBUtils.setNewsSectionItem(rs);  //set news items attribute/data map
                newsList.add(newsItem);
            }
            newsHeadLines = new NewsHeadLines();
            newsHeadLines.setNews(newsList);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }


    /**
     * News home page specific method
     * @param requestDBDTO sql and
     * @return matched news
     */
    
    public NewsHeadLines searchAllSectionsNewsFromOracle(RequestDBDTO requestDBDTO) {
        Connection connection = null ;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        List<Map<String,String>> newsList = null;
        Map<String,String> newsItem;

        try{
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\",""));
            rs = preparedStatement.executeQuery();
            newsList = new ArrayList<Map<String,String>>();
            while (rs.next()){
                newsItem = DBUtils.setNewsSectionItem(rs);  //set news items attribute/data map
                newsList.add(newsItem);
            }
            newsHeadLines = new NewsHeadLines();
            newsHeadLines.setNews(newsList);
        }catch (SQLException sqlE){
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        }finally {
            DBUtils.finalizeDBResources(connection,preparedStatement,rs);
        }
        return newsHeadLines;
    }

    public NewsHeadLines searchNewsIndividualsFromOracle(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        List<Map<String, String>> newsList = null;
        Map<String, String> newsItem;

        try {
            int pageSize = Integer.parseInt(requestDBDTO.getCustomParams().get(IConstants.MIXDataField.PGS).toString());
            int pageIndex = Integer.parseInt(requestDBDTO.getCustomParams().get(IConstants.MIXDataField.PGI).toString());
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            newsList = new ArrayList<Map<String, String>>();
            int count = 0;
            while (rs.next()) {
                newsItem = DBUtils.setNewsIndividualItem(rs);  //set news items attribute/data map
                newsList.add(newsItem);
                count++;
            }
            newsHeadLines = new NewsHeadLines();
            if (count > pageSize) {
                newsHeadLines.setNews(newsList.subList(0, pageSize));
            } else {
                newsHeadLines.setNews(newsList);
            }
            newsHeadLines.setTotalCount((pageIndex * pageSize) + count);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }

    //Private methods

    private String addToString(StringBuilder stringBuilder, int seqId){

         if(stringBuilder.length() > 0){
            stringBuilder.append(",");
        }

        stringBuilder.append(seqId);

        return stringBuilder.toString();

    }

    public void insertUpdateNewsToRDBMS(Session session , News news) throws HibernateException {
        try {
            session.saveOrUpdate(news);
        } catch (HibernateException e) {
            LOG.error("Hibernate Exception Occurred "+ e.getMessage(), e.getCause());
            throw e;
        }

    }

    private int deleteNewsFromIMDB(String sql ,Connection imdbConnection) throws SQLException{
        int count = -1;
        Statement statement = null;
        try {
            statement = imdbConnection.createStatement();
            count = statement.executeUpdate(sql);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            throw sqlE;
        } finally {
            if(statement != null){
                statement.close();
            }
        }

        return count;
    }

    /**
     * Process String so that maximum no of characters allows for the field is '200' characters
     *
     * @param dataVal data value
     * @return str
     */
    private String processString(String dataVal) {
        if(dataVal == null){
            return null;
        }

        return processString(dataVal, MAX_NO_OF_CHAR);
    }

    private String[] processCommaSeparatedString(String dataVal) {
        if (dataVal == null) {
            return null;
        }
        return dataVal.split(IConstants.Delimiter.COMMA + "");
    }

    /**
     * Process String so that maximum no of characters allows for the field is '200' characters
     *
     * @param dataVal data value
     * @param maxVal max value to keep for this string.
     * @return str
     */
    private String processString(String dataVal , int maxVal) {
        String returnStr = null;
        if (dataVal != null && !dataVal.isEmpty()) {
            returnStr = dataVal.substring(ZERO_INDEX, Math.min(dataVal.length(), maxVal));
        }
        return returnStr;
    }

    /**
     * Process String so that maximum no of characters allows for the field is '200' characters
     *
     * @param dataVal data value
     * @return str
     */
    private String processHeadLine(String dataVal) {
        String returnStr = null;
        if (dataVal != null && !dataVal.isEmpty()) {
            returnStr = dataVal.substring(ZERO_INDEX, Math.min(dataVal.length(), MAX_HEADLINE_LENGTH));
        }
        return returnStr;
    }

    /**
     * Add commas before and after the string
     *
     * @param rawStr raw string
     * @return comma added string
     */
    private String addCommasToString(String rawStr) {
        if(rawStr == null){
            return null;
        }
        return addCommasToString(rawStr , MAX_NO_OF_CHAR);
    }

    private String addCommasToString(String rawStr, int maxVal) {
        String returnStr = null;
        String comma = Character.toString(IConstants.Delimiter.COMMA);
        if (rawStr != null && !rawStr.isEmpty()) {
            returnStr = comma + processString(rawStr , maxVal) + comma;
        }
        return returnStr;
    }

    /**
     * Convert string to int values
     *
     * @param str String value
     * @return int value
     */
    private int processInt(String str) {
        int returnVal = 0;
        if (str != null && !str.isEmpty()) {
            try {
                returnVal = Integer.parseInt(str);
            } catch (NumberFormatException numEx) {
                LOG.error(numEx.getMessage(), numEx.getCause());
            }
        }
        return returnVal;
    }

    /**
     * Set default news priority value to news if not specified.
     *
     * @param str priority
     * @return str
     */
    private int processHtind(String str) {
        int newsPriority = DEFAULT_NEWS_PRIORITY;
        if (!str.equals("")) {
            newsPriority = Integer.parseInt(str);
        }
        return newsPriority;
    }




    /**
     *  Set News database columns for update
     * @param preparedStatement PreparedStatement
     * @param aNews  news map
     * @param tagFields true if tagging enable
     * @throws SQLException SQL Exception if any error
     */

    private void setNewsUpdateColumnValues(PreparedStatement preparedStatement, Map<String, String> aNews, boolean tagFields) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, ZERO_VALUE);//ext id ???
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.PRV)));//provider
        preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.AST)));//asset class
        preparedStatement.setTimestamp(index++, new Timestamp(CommonUtils.formatDate(aNews.get(IConstants.MIXDataField.DT)).getTime()));//news date
        preparedStatement.setInt(index++, processHtind(aNews.get(IConstants.MIXDataField.HTIND)));//hot news indicator
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.SSID)));//source serial id
        preparedStatement.setTimestamp(index++, new Timestamp(new Date().getTime()));//last updated time
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.STATUS)));//status
        preparedStatement.setString(index++, processHeadLine(aNews.get(IConstants.MIXDataField.HED)));//headline
        preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.EDT)));//editorial code
        preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.GEO)));//geo region
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.GOV)));//government
        preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.INDV)));//individual
        preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.INDST)));//industry
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.MKT)));//market sector code
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.PRODSRV)));//product service code
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.INTCLS)));//internal class type
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.APPRVSTAT)));//approval status
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.URL)));//url
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.SRCSEQ)));//source sequence
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.SCODE));//source id
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.SOU));//source id desc
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.SI)));//seq id

//        preparedStatement.setInt(index++, IConstants.NEWS_NODE_STATUS_DEFAULT);//NODE_1_STATUS
//        preparedStatement.setInt(index++, IConstants.NEWS_NODE_STATUS_DEFAULT);//NODE_2_STATUS

        if (tagFields) {
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.CC) , MAX_NO_OF_COUNTRY_CHAR));//country
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.TSID)));//ticker serial
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.E)));//exchange
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.S) , MAX_NO_OF_SYMBOL_CHAR));//symbol
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.CID)));//company id
        }
        //where clause for update, LANGUAGE_ID and NEWS_ID
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.ID)));//news id
        preparedStatement.setString(index, aNews.get(IConstants.MIXDataField.L));//language
    }

    /**
     * Set News database columns for insert
     * @param preparedStatement PreparedStatement
     * @param aNews             news map
     * @throws SQLException SQL Exception if any error
     */
    private void setNewsIndividualsInsertColumnValues(PreparedStatement preparedStatement, Map<String, String> aNews, int indvId) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.ID)));//news id
        preparedStatement.setInt(index, indvId);//individual
    }

    /**
     * Set News database columns for delete
     * @param preparedStatement PreparedStatement
     * @param aNews             news map
     * @throws SQLException SQL Exception if any error
     */
    private void setNewsIndividualsDeleteColumnValues(PreparedStatement preparedStatement, Map<String, String> aNews, int index) throws SQLException {
        preparedStatement.setInt(index, processInt(aNews.get(IConstants.MIXDataField.ID)));//news id
    }

    private void setNewsUpdateColumnIMDBValues(PreparedStatement preparedStatement, Map<String, String> aNews, boolean tagFields) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, ZERO_VALUE);//ext id ???
        preparedStatement.setString(index++,aNews.get(IConstants.MIXDataField.PRV));//provider
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.AST));//asset class
//        preparedStatement.setTimestamp(index++, new Timestamp(CommonUtils.formatDate(aNews.get(IConstants.MIXDataField.DT)).getTime()));//news date
        preparedStatement.setTimestamp(index++, (Timestamp.valueOf(aNews.get(IConstants.MIXDataField.DT))));//news date
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.HTIND)));//hot news indicator
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.SSID));//source serial id
        preparedStatement.setTimestamp(index++, new Timestamp(new Date().getTime()));//last updated time
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.STATUS));//status
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.HED));//headline
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.EDT));//editorial code
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.GEO));//geo region
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.GOV));//government
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.INDV));//individual
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.INDST));//industry
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.MKT));//market sector code
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.PRODSRV));//product service code
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.INTCLS));//internal class type
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.APPRVSTAT)));//approval status
        preparedStatement.setString(index++, processString(aNews.get(IConstants.MIXDataField.URL)));//url
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.SRCSEQ)));//source sequence
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.SCODE));//source id
        preparedStatement.setString(index++, aNews.get(IConstants.MIXDataField.SOU));//source id desc
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.SI)));//seq id
        preparedStatement.setString(index++, (aNews.get(IConstants.MIXDataField.TNES)));//news date
        preparedStatement.setInt(index++, Integer.valueOf(aNews.get(IConstants.MIXDataField.ITS)));//news date
        preparedStatement.setTimestamp(index++, (Timestamp.valueOf(aNews.get(IConstants.MIXDataField.LST))));//news date


        if (tagFields) {
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.CC) , MAX_NO_OF_COUNTRY_CHAR));//country
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.TSID)));//ticker serial
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.E)));//exchange
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.S) , MAX_NO_OF_SYMBOL_CHAR));//symbol
            preparedStatement.setString(index++, addCommasToString(aNews.get(IConstants.MIXDataField.CID)));//company id
        }
        //where clause for update, LANGUAGE_ID and NEWS_ID
        preparedStatement.setInt(index++, processInt(aNews.get(IConstants.MIXDataField.ID)));//news id
        preparedStatement.setString(index, aNews.get(IConstants.MIXDataField.L));//language
    }



    /**
     * Add a News to Current batch. Execute and commit when
     * COMMIT_BATCH_SIZE reached
     *
     * @param preparedStatement prepared statement
     * @return count
     * @throws SQLException sql Exception
     */
    private int executeInsertUpdateBatch(PreparedStatement preparedStatement) throws SQLException {
        int count = 0;
        try {
            int[] r = preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            for(int i : r){
                if(i != -3){
                    count++;
                }
            }
        } catch (BatchUpdateException e) {
            LOG.error("<!--Batch Update Exception occurred.." + e.getMessage(), e);
            throw e;
        }

        return count;
    }

    //region top news history update

    public List<TopNewsDataDTO> getTopNewsHistoryForUpdate(RequestDBDTO requestDBDTO) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TopNewsDataDTO> topNewsEntryList = null;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                topNewsEntryList = new ArrayList<TopNewsDataDTO>();
                while (resultSet.next()) {
                    topNewsEntryList.add(NewsDBHelper.getTopNewsDataDTO(resultSet));
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, resultSet);
        }

        return topNewsEntryList;
    }

    /**
     * Update top stories in NEWS IMDB table
     *
     * @param requestDBDTO request object
     * @return updated news id list
     */
    @SuppressWarnings("unchecked")
    public Object updateTopStoriesIMDB(RequestDBDTO requestDBDTO) {
        int status;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int index = 1;
        int successCount = 0;

        List<TopNewsDataDTO> topNewsDataDTOList = (List<TopNewsDataDTO>) requestDBDTO.getData();
        List<TopNewsDataDTO> topNewsDataDTOListCopy = new ArrayList<TopNewsDataDTO>(topNewsDataDTOList);
        List<Integer> updateSuccessList = new ArrayList<Integer>();
        try {
            connection = imdbDriverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            for (TopNewsDataDTO topNewsDataDTO : topNewsDataDTOListCopy) {
                preparedStatement.setString(index++, topNewsDataDTO.getNewsEditionSection());
                preparedStatement.setInt(index, topNewsDataDTO.getNewsId());
                status = preparedStatement.executeUpdate();

                if (status == IConstants.Status.SUCCESS) {
                    updateSuccessList.add(topNewsDataDTO.getNewsId());
                    topNewsDataDTOList.remove(topNewsDataDTO); //remove already updated TopNewsDataDTO objects from original list
                    successCount++;
                }
                if (successCount > 0 && successCount % TOP_NEWS_COMMIT_BATCH_SIZE == 0) {
                    connection.commit();
                }
                index = 1;
            }

            connection.commit();
            LOG.debug("<!--Top Stories history update Completed | Update Count : " + topNewsDataDTOList.size() + " Success Count :" + successCount);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            LOG.error("<!--SQL exception occurred in updating top Stories");
            LOG.error(sqlE.getMessage(), sqlE);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException ex) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            LOG.error("<!--SQL exception occurred in deleting news from IMDB..");
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return updateSuccessList;
    }

    /**
     * Update top stories in NEWS IMDB table
     *
     * @param requestDBDTO request object
     * @return updated news id list
     */
    @SuppressWarnings("unchecked")
    public Object updateTopStoriesOracle(RequestDBDTO requestDBDTO) {
        int status;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int index = 1;
        List<TopNewsDataDTO> topNewsDataDTOList = (List<TopNewsDataDTO>) requestDBDTO.getData();
        List<Integer> updateSuccessList = new ArrayList<Integer>();
        int successCount = 0;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            for (TopNewsDataDTO topNewsDataDTO : topNewsDataDTOList) {
                preparedStatement.setString(index++, topNewsDataDTO.getNewsEditionSection());
                preparedStatement.setInt(index, topNewsDataDTO.getNewsId());
                status = preparedStatement.executeUpdate();

                if (status == IConstants.Status.SUCCESS) {
                    updateSuccessList.add(topNewsDataDTO.getNewsId());
                    successCount++;
                }
                if (successCount > 0 && successCount % TOP_NEWS_COMMIT_BATCH_SIZE == 0) {
                    connection.commit();
                }
                index = 1;
            }

            connection.commit();
            LOG.debug("<!--Top Stories history update in Oracle DB Completed | Update Count : " + topNewsDataDTOList.size() + " Success Count :" + successCount);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            LOG.error("<!--SQL exception occurred in updating top Stories");
            LOG.error(sqlE.getMessage(), sqlE);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException ex) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            LOG.error("<!--Top Stories history update in Oracle DB failed...");
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return updateSuccessList;
    }

    /**
     * Mark updated Top Stories as "UPDATED"(IS_NEWS_TAB_UPDATE = 1) IN TOP_NEWS_HISTORY table
     *
     * @param requestDBDTO request object
     * @return
     */
    @SuppressWarnings("unchecked")
    public int markTopStoriesAsUpdated(RequestDBDTO requestDBDTO) {
        int status = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Integer> topNewsDataDTOList = (List<Integer>) requestDBDTO.getData();
        int MAX_SIZE = 100;
        String queryString;
        String query;

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            connection.setAutoCommit(false);

            int val = (int) Math.ceil((double) topNewsDataDTOList.size() / MAX_SIZE);
            for (int j = 0; j < val; j++) {
                List<Integer> subList = topNewsDataDTOList.subList(MAX_SIZE * j, Math.min(MAX_SIZE * j + MAX_SIZE, topNewsDataDTOList.size()));
                queryString = requestDBDTO.getQuery();
                query = queryString.replace(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK, StringUtils.join(subList, IConstants.Delimiter.COMMA));
                preparedStatement = connection.prepareStatement(query.replace("\\", ""));
                preparedStatement.executeQuery();
                connection.commit();
            }
            LOG.debug("<!--Marking Top Stories as 'Updated', Completed | Update Count : " + topNewsDataDTOList.size());
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            LOG.error("<!--SQL exception occurred in updating top Stories");
            LOG.error(sqlE.getMessage(), sqlE);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOG.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException ex) {
                    LOG.error(e.getMessage(), e);
                    LOG.error("<!--transaction Rollback Failed..");
                }
            }
            status = 0;
            LOG.error("<!--Marking Top Stories as 'Updated', failed...");
            LOG.error(e.getMessage(), e);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return status;
    }

    
    public LatestNews getLatestNewsFormOracle(Timestamp lastSyncTime, Timestamp minNewsDateIMDB, String language) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(LOG_PREFIX + "Start getLatestNewsFromOracle ");
        }
        List<Map<String, String>> newNews = new ArrayList<Map<String, String>>();
        List<Integer> newsIdsToDelete = new ArrayList<Integer>();

        StringBuilder newsIdsToDeleteSB           =  new StringBuilder();
        String        newsIdsToDeleteString       =  null;
        List<String> newsIdsToDeleteArray         = new ArrayList<String>();

        StringBuilder newsIdsSB           =  new StringBuilder();
        String        newsSeqIdsString =  null;

        LatestNews latestNews = new LatestNews();
        List<Integer> newsIds = new ArrayList<Integer>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection        = this.oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_LATEST_NEWS);
            preparedStatement.setTimestamp(1, lastSyncTime);
            preparedStatement.setTimestamp(2, minNewsDateIMDB);
            preparedStatement.setString(3, language);

            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + " Query : " + SELECT_LATEST_NEWS + " -> {0} = " + lastSyncTime + ", {1} = " + minNewsDateIMDB + ", {2} = " + language);
            }

            rs =   preparedStatement.executeQuery();
            while (rs.next()){
                Map<String, String> newsItem = new HashMap<String, String>();

                String status = rs.getString("STATUS");
                int newsId    = rs.getInt("NEWS_ID");
                if (LOG.isDebugEnabled()) {
                    LOG.debug(LOG_PREFIX + " NEWS_ID : " + newsId + " STATUS : " + status);
                }

                newsIds.add(newsId);
                newsSeqIdsString = addToString(newsIdsSB, newsId);

                if(DELETED_N.equalsIgnoreCase(status)){
                    newsIdsToDelete.add(newsId);
                    newsIdsToDeleteString = addToString(newsIdsToDeleteSB, newsId);
                    if(newsIdsToDelete.size() % 100 == 0){
                        newsIdsToDeleteArray.add(newsIdsToDeleteString);
                        newsIdsToDeleteString = null;
                        newsIdsToDeleteSB     =  new StringBuilder();
                    }
                    continue;
                }

                newsItem.put(IConstants.MIXDataField.PRV, rs.getString("NEWS_PROVIDER"));
                newsItem.put(IConstants.MIXDataField.AST, rs.getString("ASSET_CLASS"));
                newsItem.put(IConstants.MIXDataField.DT, rs.getTimestamp("NEWS_DATE").toString());
                newsItem.put(IConstants.MIXDataField.HTIND, rs.getString("HOT_NEWS_INDICATOR"));
                newsItem.put(IConstants.MIXDataField.SSID, rs.getString("SOURCE_ID"));
                newsItem.put("LAST_UPDATED_TIME", rs.getString("LAST_UPDATED_TIME"));
                newsItem.put(IConstants.MIXDataField.STATUS,status);
                newsItem.put(IConstants.MIXDataField.HED, rs.getString("NEWS_HEADLINE_LN")) ;
                newsItem.put(IConstants.MIXDataField.EDT, rs.getString("EDITORIAL_CODE")) ;
                newsItem.put(IConstants.MIXDataField.GEO, rs.getString("GEO_REGION_CODE"));
                newsItem.put(IConstants.MIXDataField.GOV, rs.getString("GOVERNMENT_CODE"))  ;
                newsItem.put(IConstants.MIXDataField.INDV, rs.getString("INDIVIDUAL_CODE")) ;
                newsItem.put(IConstants.MIXDataField.INDST, rs.getString("INDUSTRY_CODE")) ;
                newsItem.put(IConstants.MIXDataField.MKT, rs.getString("MARKET_SECTOR_CODE"));
                newsItem.put(IConstants.MIXDataField.PRODSRV, rs.getString("PRODUCT_SERVICES_CODE"));
                newsItem.put(IConstants.MIXDataField.INTCLS, rs.getString("INTERNAL_CLASS_TYPE")) ;
                newsItem.put(IConstants.MIXDataField.APPRVSTAT, rs.getString("APPROVAL_STATUS")) ;
                newsItem.put(IConstants.MIXDataField.URL, rs.getString("URL")) ;
                newsItem.put(IConstants.MIXDataField.SRCSEQ, rs.getString("UNIQUE_SEQUENCE_ID")) ;
                newsItem.put(IConstants.MIXDataField.SCODE, rs.getString("NEWS_SOURCE_ID"));
                newsItem.put(IConstants.MIXDataField.SOU, rs.getString("NEWS_SOURCE_DESC"));
                newsItem.put(IConstants.MIXDataField.SI, rs.getString("SEQ_ID"));
//                newsItem.put(IConstants.MIXDataField., rs.getString("NODE_1_STATUS"))
//                newsItem.put(IConstants.MIXDataField., rs.getString("NODE_2_STATUS"))
                newsItem.put(IConstants.MIXDataField.CC, rs.getString("COUNTRY"));
                newsItem.put(IConstants.MIXDataField.TSID, rs.getString("TICKER_ID"));
                newsItem.put(IConstants.MIXDataField.E, rs.getString("EXCHANGE"));
                newsItem.put(IConstants.MIXDataField.S, rs.getString("SYMBOL"))  ;
                newsItem.put(IConstants.MIXDataField.CID, rs.getString("COMPANY_ID"))  ;
                newsItem.put(IConstants.MIXDataField.ID, Long.toString(newsId)) ;
                newsItem.put(IConstants.MIXDataField.L, rs.getString("LANGUAGE_ID"));
                newsItem.put(IConstants.MIXDataField.LST, rs.getTimestamp("LAST_SYNC_TIME").toString());
                newsItem.put(IConstants.MIXDataField.ITS, String.valueOf(rs.getInt("IS_TOP_STORY")));
                newsItem.put(IConstants.MIXDataField.TNES, rs.getString("TOP_NEWS_EDITION_SECTION"));
                newNews.add(newsItem);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "result set looped.. ");
            }

            if(newsIdsToDeleteString != null) {
                newsIdsToDeleteArray.add(newsIdsToDeleteString);
            }

            latestNews.setNewsIdsToDelete(newsIdsToDelete);
            latestNews.setNewNews(newNews);
            latestNews.setNewsIds(newsIds);
            latestNews.setStringNewsIdsToDelete(newsIdsToDeleteArray);
            latestNews.setStringNewsIds(newsSeqIdsString);
        } catch (SQLException e) {
            LOG.error("Error connecting to oracle db" + e.getMessage(),e);
        } finally {
            DBUtils.finalizeDBResources(connection , preparedStatement , rs);
        }

        return  latestNews;
    }

    
    public boolean updateOracleNodeStatus(String nodeId, String language, String newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean returnStatus = false;

        String sql = MessageFormat.format(UPDATE_ORACLE_NEWS_NODE_STATUS, nodeId , newsId);

        try {
            connection = oracleDriverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,IConstants.NEWS_NODE_STATUS_AVAILABLE_IN_IMDB);
            preparedStatement.setString(2, language.toUpperCase());
            preparedStatement.executeQuery();
            returnStatus = true;
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage() , sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return returnStatus;
    }

    //endregion

    //region setters

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setImdbDriverManagerDataSource(DataSource imdbDriverManagerDataSource) {
        this.imdbDriverManagerDataSource = imdbDriverManagerDataSource;
    }

    public void setOracleDriverManagerDataSource(DataSource oracleDriverManagerDataSource) {
        this.oracleDriverManagerDataSource = oracleDriverManagerDataSource;
    }

    public void setCriteriaGenerator(CriteriaGenerator criteriaGenerator) {
        this.criteriaGenerator = criteriaGenerator;
    }

    public NewsQueryGenerator getNewsQueryGenerator() {
        return newsQueryGenerator;
    }

    public void setNewsQueryGenerator(NewsQueryGenerator newsQueryGenerator) {
        this.newsQueryGenerator = newsQueryGenerator;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
    }

    //endregion
}
