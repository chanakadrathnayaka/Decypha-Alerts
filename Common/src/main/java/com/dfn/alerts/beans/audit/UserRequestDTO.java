package com.dfn.alerts.beans.audit;

/**
 * Object to hold user details and request params
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/31/14
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRequestDTO {

    private final String url;

    private final String requestType;

    private final long userId;
    
    private String params;
    
    public UserRequestDTO(long userId, String url , String requestType){
      this.userId = userId;
      this.url = url;
      this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getUrl() {
        return url;
    }

    public long getUserId() {
        return userId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
