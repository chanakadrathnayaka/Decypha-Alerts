package com.dfn.alerts.impl;

import com.dfn.alerts.beans.EarningsAnnouncement;
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
 * Date 2016-12-15 15:51
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:alert-business-bean.xml"})
public class EarningsAnnouncementManagerTest {

    @Resource
    protected EarningsAnnouncementManager announcementManager;

    @Test
    public void getModelData() throws Exception {



        Map requestData = new HashMap();
        requestData.put("ITK","SYMBOL_STATUS:1");
        requestData.put("E","CASE,ITSE,BSE,CSE,DFM,ADSM,DIFX,TDWL,KSE,DSM,LSM,MSM,LBBSE,PSE,TNTSE,ASE,ISX");
        requestData.put("SCDT","EANNML");
        requestData.put("IFC","1");
        requestData.put("UNC","0");
        requestData.put("L","EN");
        requestData.put("FDK","ANNOUNCE_DATE~3~20121215");
        requestData.put("M","0");
        requestData.put("SID","DECYPHA");
        requestData.put("UID","DECYPHA");
        requestData.put("SF","ANNOUNCE_DATE");
        requestData.put("PGI","0");
        requestData.put("SO","DESC");
        requestData.put("FC","1");
        requestData.put("PGS","20");

        Object results = announcementManager.getData(requestData, true);

        Assert.notNull(results, "Results are null");
        Assert.notEmpty(((EarningsAnnouncement)results).getEarningsAnnouncements(), "Results are empty");
    }

}