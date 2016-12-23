package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.Announcements;
import com.dfn.alerts.dataaccess.dao.CommonDAO;
import com.dfn.alerts.dataaccess.utils.AnnouncementDBHelper;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aravindal on 3/17/14.
 */
public class JDBCAnnouncementDAOImpl implements CommonDAO {

    private DataSource driverManagerDataSource;

    private static final Logger LOG = LogManager.getLogger(JDBCAnnouncementDAOImpl.class);
    private static final String ANN_LOG_PREFIX = " ANNOUNCEMENTS DAO :: ";

    public static final int COMMIT_FREQUENCY = 100;
    public static final int ZERO_VALUE=0;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        //
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }

    //region ANNOUNCEMENT MIGRATE

    /**
     * insert in to Oracle DB - migration
     * @param requestDBDTO requestDBDTO
     * @return inserted announcement id list
     */
    @SuppressWarnings("unchecked")
    public List<Integer> insertExcessAnnouncementsToOracle(RequestDBDTO requestDBDTO) {
        PreparedStatement preparedStatement = null;
        Statement deleteStatement = null;
        Connection connection = null;
        List<Integer> iList = null;
        int announcementCount = 0;
        try {
            connection = driverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            deleteStatement   = connection.createStatement();
            iList = new ArrayList<Integer>();
            List<Integer> annList = new ArrayList<Integer>(COMMIT_FREQUENCY);
            List<Announcements> announcements = (List<Announcements>) requestDBDTO.getData();
            for (Announcements announcement : announcements) {
                AnnouncementDBHelper.setParametersToPreparedStatement(preparedStatement, announcement, requestDBDTO.getSupportedLang());
                preparedStatement.addBatch();
                announcementCount++;
                annList.add(announcement.getAnnouncementId());
                // Execute every 100 items.
                if ((announcementCount) % COMMIT_FREQUENCY == ZERO_VALUE) {
                    try {


                        RequestDBDTO sql = AnnouncementDBHelper.getIMDBDeleteQuery(annList);
                        deleteStatement.executeUpdate(sql.getQuery());
                        preparedStatement.executeBatch();
                        connection.commit();
                        preparedStatement.clearBatch();
                        iList.addAll(annList);
                        annList = new ArrayList<Integer>(COMMIT_FREQUENCY);
                    } catch (BatchUpdateException sqlE) {
                        LOG.error("BATCH INSERT ERROR IN insertExcessAnnouncementsToOracle()", sqlE);
                    }
                    announcementCount = ZERO_VALUE;
                }
            }
            RequestDBDTO sql = AnnouncementDBHelper.getIMDBDeleteQuery(annList);
            int count = deleteStatement.executeUpdate(sql.getQuery());
            preparedStatement.executeBatch();
            connection.commit();
            preparedStatement.clearBatch();
            iList.addAll(annList);
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOG.error("CONNECTION CLOSING ERROR IN insertExcessAnnouncementsToOracle()", e);
                }
            }
        } finally {
            DBUtils.finalizeDBResources(connection, new Statement[]{preparedStatement,deleteStatement}, null);
        }
        return iList;
    }

    //endregion

    //region ANNOUNCEMENT_SEARCH

    /**
     * Search announcements in RDBMS
     * Query is generated,
     * @see AnnouncementDBHelper class
     * @param requestDBDTO request Object containing the search query
     *                     and other parameters
     *                     ** News Supported languages
     *                     ** Preferred Lang
     * @return announcement list
     */
    public List<Map<String, String>> searchAnnouncementsRDBMS(RequestDBDTO requestDBDTO){
        List<Map<String, String>> announcementsList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> preferredLang = new ArrayList<String>(1);
        preferredLang.add(requestDBDTO.getPreferredLang());

        try {
            connection = this.driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            resultSet = preparedStatement.executeQuery();
            announcementsList = new ArrayList<Map<String, String>>();
            while (resultSet.next()){
            //Generate announcement Object list and convert it to a announcement Map List
                announcementsList.add(AnnouncementDBHelper.setAnnouncementData(resultSet,preferredLang)
                                                                     .getAnnouncement(requestDBDTO.getPreferredLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, resultSet);
        }
        return announcementsList;
    }

    /**
     * check whether announcements are available for the given criteria
     * @param requestDBDTO query
     * @return ann_date
     */
    public int isAnnouncementsAvailableInRDBMS(RequestDBDTO requestDBDTO){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int count = -1;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException sqlE) {
            LOG.error(ANN_LOG_PREFIX + "ERROR IN getAnnouncementCountInRDBMS()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return count;
    }

    //endregion

    //region ANNOUNCEMENT_HISTORY

    /**
     * get the oldest announcement in Oracle DB
     * checks for new announcements(status N)
     * @param requestDBDTO query
     * @return ann_date
     */
    public Date getMinAnnDateInOracle(RequestDBDTO requestDBDTO){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Date minAnnDate = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                minAnnDate = rs.getDate(1);
            }
        } catch (SQLException sqlE) {
            LOG.error(ANN_LOG_PREFIX + "ERROR IN getMinAnnDateInOracle()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return minAnnDate;
    }

    /**
     * update IMDB with latest announcements
     * IGNORE announcements with status D
     * add UPDATE announcements to INSERT set
     * DIVIDE announcements with INSERT set to insert/update(due to requesting from multiple languages & having flat DB)
     * INSERT announcements with INSERT set after above step
     * UPDATE all other announcements
     * @param language requested language
     * @param announcementMap ann id, announcement map
     * @param announcementStatusMap ann_status, List<ann_id>
     * @return status of update
     */
    public boolean updateOracle(String language,  Map<Integer, Announcements> announcementMap, Map<String,List<Integer>> announcementStatusMap) {
        Connection connection = null;

        List<String> languages = new ArrayList<String>(1);
        languages.add(language);

        int count = 0;

        if(LOG.isInfoEnabled()){
            LOG.info(ANN_LOG_PREFIX + "ALL ANNOUNCEMENTS : " + announcementMap.size());
        }

        try {
            connection = driverManagerDataSource.getConnection();
            connection.setAutoCommit(false);

            if(LOG.isInfoEnabled()){
                LOG.info(ANN_LOG_PREFIX + "PROCESSING ANNOUNCEMENTS TO INSERT");
            }

            if(announcementStatusMap.containsKey(IConstants.DCPStatus.DELETED.getStatus())){
                count = announcementStatusMap.get(IConstants.DCPStatus.DELETED.getStatus()).size();
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "IGNORED DELETED ANNOUNCEMENTS : " + count);
                }
            }

            if(announcementStatusMap.containsKey(IConstants.DCPStatus.NEW.getStatus())){
                if(announcementStatusMap.containsKey(IConstants.DCPStatus.MODIFIED.getStatus())){
                    if(LOG.isInfoEnabled()){
                        int mCount = announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()).size();
                        int nCount = announcementStatusMap.get(IConstants.DCPStatus.NEW.getStatus()).size();
                        LOG.info(ANN_LOG_PREFIX + "MODIFIED ANNOUNCEMENTS : " + mCount);
                        LOG.info(ANN_LOG_PREFIX + "NEW ANNOUNCEMENTS : " + nCount);
                        LOG.info(ANN_LOG_PREFIX + "ADDING MODIFIED ANNOUNCEMENTS TO NEW LIST");
                    }
                    announcementStatusMap.get(IConstants.DCPStatus.NEW.getStatus()).addAll(announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()));
                    announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()).clear();
                }
            }else if(announcementStatusMap.containsKey(IConstants.DCPStatus.MODIFIED.getStatus())){
                if(LOG.isInfoEnabled()){
                    int mCount = announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()).size();
                    LOG.info(ANN_LOG_PREFIX + "MODIFIED ANNOUNCEMENTS : " + mCount);
                    LOG.info(ANN_LOG_PREFIX + "ADDING MODIFIED ANNOUNCEMENTS TO NEW LIST");
                }
                announcementStatusMap.put(IConstants.DCPStatus.NEW.getStatus(), announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()));
                announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()).clear();
            }

            if(announcementStatusMap.containsKey(IConstants.DCPStatus.NEW.getStatus())){
                List<Integer> announcementsToInsert = announcementStatusMap.get(IConstants.DCPStatus.NEW.getStatus());
                int inLoops = (int)Math.ceil((double) announcementsToInsert.size() / 1000);
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "LOOPING CHECKING FOR NEW ANNOUNCEMENTS WE ALREADY HAVE, LOOPS : " + inLoops);
                }
                List<Announcements> announcementsList = new ArrayList<Announcements>();
                for(int i = 0; i < inLoops; i++){
                    int from = i * 1000;
                    int to = (inLoops == 1 || i == inLoops - 1) ? announcementsToInsert.size() : (from + 1000);
                    List<Integer> annsToInsert = announcementsToInsert.subList(from, to);
                    if(LOG.isInfoEnabled()){
                        LOG.info(ANN_LOG_PREFIX + "CHECKING FOR NEW ANNOUNCEMENTS WE ALREADY HAVE : " + i);
                    }
                    RequestDBDTO requestDBDTO = AnnouncementDBHelper.getUpdateListSelectQuery(annsToInsert, languages);
                    announcementsList.addAll(getJDBCAnnouncements(requestDBDTO, connection));
                    if(LOG.isInfoEnabled()){
                        LOG.info(ANN_LOG_PREFIX + "LOADED NEW ANNOUNCEMENTS WE ALREADY HAVE : " + announcementsList.size());
                    }
                }

                for(Announcements a : announcementsList){
                    announcementStatusMap.get(IConstants.DCPStatus.NEW.getStatus()).remove(a.getAnnouncementId());
                    if(!announcementStatusMap.containsKey(IConstants.DCPStatus.MODIFIED.getStatus())){
                        announcementStatusMap.put(IConstants.DCPStatus.MODIFIED.getStatus(), new ArrayList<Integer>());
                    }
                    announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus()).add(a.getAnnouncementId());
                    if(LOG.isInfoEnabled()){
                        LOG.info(ANN_LOG_PREFIX + "REMOVING FROM NEW LIST & ADDING TO MODIFY LIST : " + a.getAnnouncementId());
                    }
                }

                List<Integer> inserts = announcementStatusMap.get(IConstants.DCPStatus.NEW.getStatus());
                RequestDBDTO insertRequestDBDTO = AnnouncementDBHelper.getInsertQuery(languages, inserts, announcementMap);
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "INSERTING NEW ANNOUNCEMENTS : " + inserts.size());
                }
                int i = insertAnnouncementsToOracle(insertRequestDBDTO, connection);
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "INSERTED NEW ANNOUNCEMENTS : " + i);
                }
                count += i;
            }

            if(LOG.isInfoEnabled()){
                LOG.info(ANN_LOG_PREFIX + "PROCESSING ANNOUNCEMENTS TO MODIFY");
            }
            if(announcementStatusMap.containsKey(IConstants.DCPStatus.MODIFIED.getStatus())){
                List<Integer> updates = announcementStatusMap.get(IConstants.DCPStatus.MODIFIED.getStatus());
                RequestDBDTO updateRequestDBDTO = AnnouncementDBHelper.getUpdateQuery(languages, updates, announcementMap);
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "UPDATING ANNOUNCEMENTS : " + updates.size());
                }
                int u = insertAnnouncementsToOracle(updateRequestDBDTO, connection);
                if(LOG.isInfoEnabled()){
                    LOG.info(ANN_LOG_PREFIX + "UPDATED ANNOUNCEMENTS : " + u);
                }
                count += u;
            }


        } catch (SQLException sqlE) {
            LOG.error(ANN_LOG_PREFIX + "ERROR IN updateIMDB()", sqlE);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error(ANN_LOG_PREFIX + "CONNCETION CLOSING ERROR IN updateIMDB()", e);
                }
            }
        }
        if(LOG.isInfoEnabled()){
            LOG.info(ANN_LOG_PREFIX + "ALL ANNOUNCEMENTS : " + count);
        }
        return count == announcementMap.size();
    }

    /**
     * load announcements
     * @param requestDBDTO select query & params
     *                     param -> updateOracle(DIVIDE announcements with status N to insert/update) -> null(uses IN clause)
     * @param connection IMDB connection
     * @return delete count
     */
    private List<Announcements> getJDBCAnnouncements(RequestDBDTO requestDBDTO, Connection connection) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Announcements> announcementList = null;
        try {
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if(requestDBDTO.getParams() != null){
                preparedStatement.setString(1, requestDBDTO.getParams());
            }
            rs = preparedStatement.executeQuery();
            announcementList = new ArrayList<Announcements>();
            while (rs.next()) {
                announcementList.add(AnnouncementDBHelper.setAnnouncementData(rs, requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(ANN_LOG_PREFIX + "ERROR IN getJDBCAnnouncements()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(null, preparedStatement, rs);
        }
        return announcementList;
    }

    /**
     * insert in to Oracle DB - migration
     * @param requestDBDTO requestDBDTO
     * @return inserted announcement id list
     */
    @SuppressWarnings("unchecked")
    private int insertAnnouncementsToOracle(RequestDBDTO requestDBDTO, Connection connection) {
        PreparedStatement preparedStatement = null;
        int count = 0;
        int announcementCount = 0;
        try {
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            Object[] data = (Object[])requestDBDTO.getData();
            List<Integer> annList = (List<Integer>) data[0];
            Map<Integer, Announcements> announcements = (Map<Integer, Announcements>) data[1];
            for (Integer i : annList) {
                Announcements announcement = announcements.get(i);
                AnnouncementDBHelper.setParametersToPreparedStatement(preparedStatement, announcement, requestDBDTO.getSupportedLang());
                preparedStatement.addBatch();
                announcementCount++;
                // Execute every 100 items.
                if ((announcementCount) % COMMIT_FREQUENCY == ZERO_VALUE) {
                    try {
                        preparedStatement.executeBatch();
                        connection.commit();
                        preparedStatement.clearBatch();
                        count += announcementCount;
                    } catch (BatchUpdateException sqlE) {
                        LOG.error(ANN_LOG_PREFIX + "BATCH INSERT ERROR IN insertExcessAnnouncementsToOracle()", sqlE);
                        //rollback the complete batch if batch fails
                        connection.rollback();
                        //what if rollback fails????
                    }
                    announcementCount = ZERO_VALUE;
                }
            }

            preparedStatement.executeBatch();
            connection.commit();
            preparedStatement.clearBatch();
            count += announcementCount;
        } catch (SQLException sqlE) {
            LOG.error(ANN_LOG_PREFIX + "ERROR IN insertAnnouncementsToOracle()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(null, preparedStatement, null);
        }
        return count;
    }

    //endregion

}
