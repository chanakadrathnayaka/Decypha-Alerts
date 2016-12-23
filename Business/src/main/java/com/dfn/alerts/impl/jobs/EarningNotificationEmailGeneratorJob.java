package com.dfn.alerts.impl.jobs;

import com.dfn.alerts.EarningAnnouncementManager;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by chathurag
 * On 12/19/2016
 */
public class EarningNotificationEmailGeneratorJob implements Job {

    private EarningAnnouncementManager earningAnnouncementManager;

    private Logger LOG = LogManager.getLogger(EarningNotificationEmailGeneratorJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        ApplicationContext context = new ClassPathXmlApplicationContext("alert-business-bean.xml");
        earningAnnouncementManager = (EarningAnnouncementManager) context.getBean("earningAnnouncementManager");

        if (jobExecutionContext != null) {
            List<EarningNotification> notifications = (List<EarningNotification>) earningAnnouncementManager.searchAlerts();
            for (EarningNotification notification : notifications) {
                System.out.println(notification.getAlertId() + " | " + notification.getStatus() + " | " + notification.getAlertText());
            }
        }

    }
}
