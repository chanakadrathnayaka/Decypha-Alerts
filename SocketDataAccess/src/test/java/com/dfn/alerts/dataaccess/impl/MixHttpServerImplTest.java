package com.dfn.alerts.dataaccess.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-06 16:33
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class MixHttpServerImplTest {

    @Test
    public void testGetData(){
        //http://ldc-uat.directfn.com/mix2/ClientServiceProvider?SID=sid&UID=123&RT=128&M=0&H=1&TSID=400000
        MixHttpServerImpl httpServer = new MixHttpServerImpl();
        httpServer.setScheme("http");
        httpServer.setHost("ldc-uat.directfn.com");
        httpServer.setResourcePath("/mix2/ClientServiceProvider");

        Object data = httpServer.getData("SID=sid&UID=123&RT=128&M=0&H=1&TSID=400000", 10000);
        Assert.notNull(data,"can not be not null");
    }
}
