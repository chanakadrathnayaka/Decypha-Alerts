package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.beans.SchedulerJobResult;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.SchedulerJobStatus;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/20/14
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IJobSchedulerDAO extends CommonDAO {
    /**
     * Insert job status to database
     * @param schedulerJobStatus object
     * @return true if operation success
     */
    boolean insertJobSchedulerStatus(SchedulerJobStatus schedulerJobStatus);

    /**
     * update end time of Job
     * @param jobScheduleId  unique id
     * @return true if operation success
     */
    boolean updateJobSchedulerStatus(String jobScheduleId, SchedulerJobResult schedulerJobResult);

    /**
     * update email send status
     * INotificationSender for emailStatus
     * @param jobScheduleId  unique id
     * @param emailStatus  email sent, fail or not send
     * @return  true if operation success
     */
    boolean updateJobSchedulerEmailStatus(String jobScheduleId, int emailStatus);


    /**
     * Delete specific job types
     * @param schedulerTypes type
     * @param noOfDaysToKeep no of days to keep records
     * @return status
     */
    boolean deleteJobSchedulerStatus(IConstants.SchedulerTypes schedulerTypes, int noOfDaysToKeep);


    /**
     * getting pending dependant job to execute for each node
     * @param nodeId  Node ID
     * @return List of pending jobs
     */
    List<String> getPendingDependantJobs(String nodeId);


    List<SchedulerJobResult> getJobDetails(String nodeId, String jobName);


    /**
     * Update dependant job status once it complete
     * @param nodeId Node ID
     * @return true if success
     */
    boolean updateCompletedDependantJobStatus(String nodeId, String jobScheduleId);
}
