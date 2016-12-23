package com.dfn.alerts;

import com.dfn.alerts.impl.EarningsAnnouncementManager;
//import com.dfn.alerts.impl.jobs.EmailJobManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chathurag
 * On 12/1/2016
 */
public class Startup {

    public static void main(String[] args) {
//        ApplicationContext context =  new ClassPathXmlApplicationContext("alert-spring-config.xml");
        ApplicationContext context =  new ClassPathXmlApplicationContext("alert-business-bean.xml");


        EarningsAnnouncementManager manager = (EarningsAnnouncementManager)context.getBean("earningAnnouncementManager");
//        EmailJobManager emailJobManager = (EmailJobManager)context.getBean("emailJobManager");
        manager.saveAlerts();
    }
}
