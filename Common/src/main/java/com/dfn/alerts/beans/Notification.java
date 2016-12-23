package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 9/5/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notification implements Serializable {

    private String informationType;
    List<NotificationItemDTO> notificationItemDTOList;

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    public List<NotificationItemDTO> getNotificationItemDTOList() {
        return notificationItemDTOList;
    }

    public void setNotificationItemDTOList(List<NotificationItemDTO> notificationItemDTOList) {
        this.notificationItemDTOList = notificationItemDTOList;
    }
}
