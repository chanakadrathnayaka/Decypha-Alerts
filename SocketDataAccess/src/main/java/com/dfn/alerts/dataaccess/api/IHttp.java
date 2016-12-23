package com.dfn.alerts.dataaccess.api;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 6/26/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttp {
    Object doGet(String getData, boolean isJsonResponse);
    Object doPost(String postData, boolean isJsonResponse);

}
