package com.dfn.alerts.impl;

import com.dfn.alerts.dataaccess.beans.ResponseObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 11:53
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:alert-business-bean.xml"})

public class FundamentalDataNotificationManagerTest {
    @Resource
    protected FundamentalDataNotificationManager notificationManager;

    @Test
    public void saveAlerts() throws Exception {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("M","0");
        requestData.put("H","1");
        requestData.put("TSID","400000");
        int status = notificationManager.saveAlerts(requestData);
        Assert.isTrue(status == 1);
    }

    @Test
    public void searchAlerts() throws Exception {

    }

    @Test
    public void getModelData() throws Exception {
        //SID=sid&UID=123&RT=128&M=0&H=1&TSID=400000
        Map<String, String> requestData = new HashMap<>();
        requestData.put("M","0");
        requestData.put("H","1");
        requestData.put("TSID","400000");
        ResponseObj notifications = (ResponseObj)notificationManager.getData(requestData, true);

        Assert.notNull(notifications);
    }

}