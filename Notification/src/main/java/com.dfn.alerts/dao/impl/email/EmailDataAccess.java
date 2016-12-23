package com.dfn.alerts.dao.impl.email;

import com.dfn.alerts.beans.EmailsRecipientsDTO;
import com.dfn.alerts.beans.notification.EmailDTO;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.dao.api.IAppConfigData;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasarindat on 4/6/2015.
 * email crud operations
 */
public class EmailDataAccess {

    private DAOFactory hibernateDAOFactory;

    /**
     * save email
     *
     * @param email EmailDTO
     * @return email id
     */
    public int save(EmailDTO email) {
        return this.hibernateDAOFactory.getEmailDAO().save(email);
    }

    /**
     * save emails
     *
     * @param emails key vs email to insert
     * @return key vs email id of inserted
     */
    public Map<Object, Integer> save(Map<Object, EmailDTO> emails) {
        return this.hibernateDAOFactory.getEmailDAO().save(emails);
    }

    /**
     * update email notification status
     *
     * @param emailId id
     * @param status  success(1) or fail(-1)
     * @return email id if success
     */
    public int update(int emailId, int status) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int updateStatus = this.hibernateDAOFactory.getEmailDAO().update(emailId, now, status);
        if (updateStatus == IConstants.DBOperationStatus.UPDATE_SUCCESS.getDefaultValue()) {
            return emailId;
        } else {
            return -1;
        }
    }

    /**
     * get emails by filter eg: email_to = 'hasarinda86@gmail.com'
     *
     * @param toAddress get emails sent to given address
     * @return email list
     */
    public List<EmailDTO> getEmailsByTo(String toAddress) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("to", toAddress);
        return filter(params);
    }

    /**
     * get emails by filter - email_type = 1
     *
     * @param emailType get emails sent to given address
     * @return email list
     */
    public List<EmailDTO> getEmailsByEmailType(int emailType) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("type", emailType);
        return filter(params);
    }

    /**
     * call this method to apply any filter
     *
     * @param params filter map to create criteria
     * @return filtered data
     */
    private List<EmailDTO> filter(Map<String, Object> params) {
        return this.hibernateDAOFactory.getEmailDAO().get(params);
    }

    public List<EmailDTO> getPendingEmails(int status,int retryCount){
        return this.hibernateDAOFactory.getEmailDAO().getPendingEmails(status,retryCount);
    }

    public int archiveEmails(int noOfDays){
        this.hibernateDAOFactory.getEmailDAO().deleteHistoryData();
        return this.hibernateDAOFactory.getEmailDAO().archive(noOfDays);
    }

    public void setHibernateDAOFactory(DAOFactory hibernateDAOFactory) {
        this.hibernateDAOFactory = hibernateDAOFactory;
    }

}
