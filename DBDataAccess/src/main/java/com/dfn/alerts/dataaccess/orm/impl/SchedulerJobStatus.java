package com.dfn.alerts.dataaccess.orm.impl;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/20/14
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchedulerJobStatus {

    private final String jobScheduleId;
    private final String schedulerType;
    private final String schedulerName;
    private Date startTime;
    private Date endTime;
    private int notificationSendStatus;
    private boolean dependantJobAvailable;

    public SchedulerJobStatus(String jobScheduleId, String schedulerName, String nodeId){
          this.jobScheduleId = jobScheduleId;
          this.schedulerType = schedulerName + "_" + nodeId;
          this.schedulerName = schedulerName;
    }

    public String getSchedulerType() {
        return schedulerType;
    }

    public Date getStartTime() {
        return startTime;
    }


    public Date getEndTime() {
        return endTime;
    }


    public int getNotificationSendStatus() {
        return notificationSendStatus;
    }

    public void setNotificationSendStatus(int notificationSendStatus) {
        this.notificationSendStatus = notificationSendStatus;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public String getJobScheduleId() {
        return jobScheduleId;
    }


    public boolean isDependantJobAvailable() {
        return dependantJobAvailable;
    }

    public void setDependantJobAvailable(boolean dependantJobAvailable) {
        this.dependantJobAvailable = dependantJobAvailable;
    }
}
