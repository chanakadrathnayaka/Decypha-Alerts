package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.dataaccess.orm.impl.SMSMessage;

import java.util.List;

/**
 * Created by Danuran on 3/11/2015.
 */
public interface SmsDAO extends CommonDAO  {
    int insertSms(SMSMessage smsMessage);
    List<SMSMessage> getSmsFromDB();

    int updateSMSRecord(int smsId, int status, int retryCount);
}
