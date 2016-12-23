package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.SchedulerJobResult;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.constants.JobConstants;
import com.dfn.alerts.dataaccess.dao.api.IJobSchedulerDAO;
import com.dfn.alerts.dataaccess.orm.impl.SchedulerJobStatus;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

// TODO REMOVE

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/20/14
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class JDBCJobSchedulerDAOImpl implements IJobSchedulerDAO {

    final static Logger LOG = LogManager.getLogger(JDBCJobSchedulerDAOImpl.class);

    private DataSource driverManagerDataSource;
    
    final static private String SQL_INSERT_JOB_SCHEDULER_STATUS          = "INSERT INTO SCHEDULER_JOB_STATUS_CLUSTER " +
            "(JOB_SCHEDULE_ID, SCHEDULER_TYPE,  JOB_NAME ,  IS_DEPENDANT_JOB_AVAILABLE, START_TIME ,JOB_STATUS ,NODE_1_STATUS,NODE_2_STATUS) VALUES (?,?,?,?,SYSDATE,?,?,?)";
    final static private String SQL_UPDATE_JOB_SCHEDULER_STATUS_END_TIME = "UPDATE SCHEDULER_JOB_STATUS_CLUSTER SET END_TIME = SYSDATE,JOB_STATUS=?,PARAMS = ? WHERE JOB_SCHEDULE_ID=? ";
    final static private String SQL_UPDATE_JOB_SCHEDULER_STATUS_EMAIL    = "UPDATE SCHEDULER_JOB_STATUS_CLUSTER SET IS_NOTIFICATION_SEND = 1";
    final static private String SQL_DELETE_JOB_SCHEDULER_STATUS          = "DELETE FROM SCHEDULER_JOB_STATUS_CLUSTER WHERE SCHEDULER_TYPE = ?";
    final static private String SQL_GET_PENDING_DEPENDANT_JOBS           = "SELECT JOB_NAME FROM SCHEDULER_JOB_STATUS_CLUSTER WHERE IS_DEPENDANT_JOB_AVAILABLE = ? AND {0}_STATUS = ? AND JOB_STATUS = ?";
    final static private String SQL_GET_PENDING_DEPENDANT_SINGLE_JOB     = "SELECT * FROM SCHEDULER_JOB_STATUS_CLUSTER WHERE IS_DEPENDANT_JOB_AVAILABLE = ? AND {0}_STATUS = ? AND JOB_STATUS = ? AND JOB_NAME = ?";
    final static private String SQL_UPDATE_DEPENDANT_JOB_STATUS          = "UPDATE SCHEDULER_JOB_STATUS_CLUSTER SET {0}_STATUS = ? WHERE JOB_SCHEDULE_ID = ?";

    @Override
    public boolean insertJobSchedulerStatus(SchedulerJobStatus schedulerJobStatus) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        boolean returnVal = false;
        try {
            connection = driverManagerDataSource.getConnection();
            preStatement = connection.prepareStatement(SQL_INSERT_JOB_SCHEDULER_STATUS);
            preStatement.setString(1,schedulerJobStatus.getJobScheduleId());   //JOB_SCHEDULE_ID
            preStatement.setString(2, schedulerJobStatus.getSchedulerType());  //SCHEDULER_TYPE
            preStatement.setString(3, schedulerJobStatus.getSchedulerName());  //JOB_NAME
            preStatement.setBoolean(4, schedulerJobStatus.isDependantJobAvailable()); //IS_DEPENDANT_JOB_AVAILABLE
            //START_TIME        SYSDATE
            preStatement.setInt(5, JobConstants.JOB_STATUS_IN_PROGRESS);    //JOB_STATUS
            preStatement.setInt(6, JobConstants.JOB_STATUS_DEFAULT);        //NODE_1_STATUS
            preStatement.setInt(7, JobConstants.JOB_STATUS_DEFAULT);        //NODE_2_STATUS
            returnVal = preStatement.execute();

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preStatement, null);
        }

        return returnVal;
    }

    @Override
    public boolean updateJobSchedulerStatus(String jobScheduleId , SchedulerJobResult schedulerJobResult) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean returnVal = false;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_JOB_SCHEDULER_STATUS_END_TIME);

            if(schedulerJobResult != null) {
                statement.setInt(1, schedulerJobResult.getJobStatus()); //JOB_STATUS

                Clob clob = connection.createClob();
                clob.setString(1,schedulerJobResult.getParams());
                statement.setClob(2, clob);  //PARAMS

            }else {
                statement.setInt(1, JobConstants.JOB_STATUS_COMPLETED); //JOB_STATUS
                statement.setClob(2, connection.createClob()); //PARAMS
            }
            statement.setString(3, jobScheduleId);//JOB_SCHEDULE_ID
            int records = statement.executeUpdate();
            returnVal = (records == 1);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, statement, null);
        }

        return returnVal;
    }

    @Override
    public boolean updateJobSchedulerEmailStatus(String jobScheduleId ,int emailStatus ) {
        Connection connection = null;
        Statement statement = null;
        boolean returnVal = false;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.createStatement();
            int records = statement.executeUpdate(SQL_UPDATE_JOB_SCHEDULER_STATUS_EMAIL);
            returnVal = (records == 1);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, statement, null);
        }

        return returnVal;
    }


    @Override
    public boolean deleteJobSchedulerStatus(IConstants.SchedulerTypes schedulerTypes, int noOfDaysToKeep) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        boolean returnVal = false;
        try {
            connection = driverManagerDataSource.getConnection();
            preStatement = connection.prepareStatement(SQL_DELETE_JOB_SCHEDULER_STATUS);
            preStatement.setString(1,schedulerTypes.toString());
//            preStatement.setInt(1,noOfDaysToKeep);
            returnVal = preStatement.execute();

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preStatement, null);
        }

        return returnVal;
    }


    @Override
    public List<SchedulerJobResult> getJobDetails(String nodeId, String jobName) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<SchedulerJobResult> schedulerJobResults = new ArrayList<SchedulerJobResult>();
        String sql = MessageFormat.format(SQL_GET_PENDING_DEPENDANT_SINGLE_JOB, nodeId);

        try {
            connection = driverManagerDataSource.getConnection();
            preStatement = connection.prepareStatement(sql);
            preStatement.setInt(1, JobConstants.JOB_IS_DEPENDANT_JOB_AVAILABLE);
            preStatement.setInt(2, JobConstants.JOB_STATUS_DEFAULT);
            preStatement.setInt(3, JobConstants.JOB_STATUS_COMPLETED);
            preStatement.setString(4, jobName);
            resultSet = preStatement.executeQuery();


            while (resultSet.next()){
                SchedulerJobResult schedulerJobResult = new SchedulerJobResult();
                schedulerJobResult.setParams(DBUtils.readClob(resultSet.getClob("PARAMS")));
                schedulerJobResult.setJobScheduleId(resultSet.getString("JOB_SCHEDULE_ID"));
                schedulerJobResults.add(schedulerJobResult);
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preStatement, resultSet);
        }

        return schedulerJobResults;
    }

    @Override
    public List<String> getPendingDependantJobs(String nodeId) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<String> stringList = new ArrayList<String>();
        String sql = MessageFormat.format(SQL_GET_PENDING_DEPENDANT_JOBS, nodeId);

        try {
            connection = driverManagerDataSource.getConnection();
            preStatement = connection.prepareStatement(sql);
            preStatement.setInt(1, JobConstants.JOB_IS_DEPENDANT_JOB_AVAILABLE);
            preStatement.setInt(2, JobConstants.JOB_STATUS_DEFAULT);
            preStatement.setInt(3, JobConstants.JOB_STATUS_COMPLETED);
            resultSet = preStatement.executeQuery();


            while (resultSet.next()){
                stringList.add(resultSet.getString("JOB_NAME"));
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preStatement, resultSet);
        }

        return stringList;
    }

    @Override
    public boolean updateCompletedDependantJobStatus(String nodeId, String jobScheduleId) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        String sql = MessageFormat.format(SQL_UPDATE_DEPENDANT_JOB_STATUS, nodeId);
        boolean returnVal = false;
        try {
            connection = driverManagerDataSource.getConnection();
            preStatement = connection.prepareStatement(sql);
            preStatement.setInt(1, JobConstants.JOB_STATUS_COMPLETED);
            preStatement.setString(2, jobScheduleId);
            int records = preStatement.executeUpdate();
            returnVal = (records == 1);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preStatement, null);
        }

        return returnVal;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }
}
