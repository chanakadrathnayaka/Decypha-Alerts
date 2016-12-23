package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.dataaccess.orm.impl.MessageResourceBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/17/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageResourcesDAO extends CommonDAO{

     int insertMessage(MessageResourceBean messageResourceBean);
     boolean deleteMessage(String resourceKey);
     List<MessageResourceBean> findMessage(String resourceKey);
     boolean updateMessage(MessageResourceBean messageResourceBean);

     List<MessageResourceBean> getAllMessages();
     List<MessageResourceBean> getAllStyles();
     Collection getAllResources(String[] basenames);
     List<MessageResourceBean> getMessages(String locale);
}
