package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 8/28/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationDataDTO implements Serializable {

    private int lastTransactionId;
    private double pageSize;
    private double row;
    private Map<String,List<NotificationItemDTO>> notificationList;

    public NotificationDataDTO(){
        notificationList = new HashMap<String, List<NotificationItemDTO>>();
    }

    public int getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(int lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public Map<String,List<NotificationItemDTO>> getNotificationList() {
        return Collections.unmodifiableMap(notificationList);
    }

    public void addNotificationList(Map<String,List<NotificationItemDTO>> notificationList) {
        for(String notificationItem : notificationList.keySet()){
            if(this.notificationList.containsKey(notificationItem)){
                List<NotificationItemDTO> notificationItemDTOList = this.notificationList.get(notificationItem);
                notificationItemDTOList.addAll( notificationList.get(notificationItem));
            } else {
                this.notificationList.put(notificationItem, notificationList.get(notificationItem));
            }
        }
    }

    public double getPageSize() {
        return pageSize;
    }

    public void setPageSize(double pageSize) {
        this.pageSize = pageSize;
    }

    public double getRow() {
        return row;
    }

    public void setRow(double row) {
        this.row = row;
    }
}
