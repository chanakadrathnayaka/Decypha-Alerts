package com.dfn.alerts.beans;

import com.dfn.alerts.constants.JobConstants;

/**
 * Created by Lasanthak on 9/2/2014.
 */
public class SchedulerJobResult {

    private String jobScheduleId;
    private int jobStatus = JobConstants.JOB_STATUS_DEFAULT;
    private String params;

    public String getJobScheduleId() {
        return jobScheduleId;
    }

    public void setJobScheduleId(String jobScheduleId) {
        this.jobScheduleId = jobScheduleId;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
