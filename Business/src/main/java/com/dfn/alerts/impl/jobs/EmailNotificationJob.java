package com.dfn.alerts.impl.jobs;

import com.dfn.alerts.api.NotificationSender;
import com.dfn.alerts.beans.SchedulerJobResult;
import com.dfn.alerts.constants.IConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chathurag
 * On 12/20/2016
 */
public class EmailNotificationJob implements Job {

    private Logger LOG = LogManager.getLogger(EmailNotificationJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        ApplicationContext context = new ClassPathXmlApplicationContext("alert-notification-bean.xml");
        NotificationSender emailNotification = (NotificationSender) context.getBean("emailSender");

        SchedulerJobResult schedulerJobResult = null;
        LOG.debug(" Start processing executeEmailNotificationJob ");
        try {
            schedulerJobResult = emailNotification.sendNotification();
        } finally {
            LOG.debug(" Completed processing executeEmailNotificationJob ");
        }
    }
}
