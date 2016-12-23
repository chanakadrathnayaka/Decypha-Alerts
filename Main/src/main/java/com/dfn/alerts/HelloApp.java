package com.dfn.alerts;


import com.dfn.alerts.impl.EarningsAnnouncementManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class HelloApp {

//    @Autowired
//    @Qualifier("earningAnnouncementManager")
//    private static EarningsAnnouncementManager manager;

    public static void main(String[] args) {
        ApplicationContext context =  new ClassPathXmlApplicationContext("alert-business-bean.xml");

        EarningsAnnouncementManager manager = (EarningsAnnouncementManager)context.getBean("earningAnnouncementManager");
        Map<String , String> requestData = new HashMap();
        requestData.put("ITK","SYMBOL_STATUS:1");
        requestData.put("E","CASE,ITSE,BSE,CSE,DFM,ADSM,DIFX,TDWL,KSE,DSM,LSM,MSM,LBBSE,PSE,TNTSE,ASE,ISX");
        requestData.put("SCDT","EANNML");
        requestData.put("IFC","1");
        requestData.put("UNC","0");
        requestData.put("L","EN");
        requestData.put("FDK","ANNOUNCE_DATE~3~20151215");
        requestData.put("M","0");
        requestData.put("SID","DECYPHA");
        requestData.put("UID","DECYPHA");
        requestData.put("SF","ANNOUNCE_DATE");
        requestData.put("PGI","0");
        requestData.put("SO","DESC");
        requestData.put("FC","1");
        requestData.put("PGS","20");
        manager.getData(requestData, true);
        System.out.println("hello");
    }
}
