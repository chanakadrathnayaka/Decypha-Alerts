package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by channas on 6/3/2015.
 */

@Entity
@Table(name = "AUTHENTICATION_API_RESPONSE")
public class AuthenticationAPIResponse implements Serializable {

    private String IP;
    private String response;
    private Timestamp timeStamp;
    private String userID;
    private String status;
    private Integer responseID;

    @Column(name = "REQUEST_TIME",nullable = false)
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Column(name = "USER_ID",nullable = false)
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Column(name = "STATUS",nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @Column(name = "RESPONSE_ID")
    @SequenceGenerator(name = "seq_authentication_api", sequenceName = "seq_authentication_api")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_authentication_api")
    public Integer getResponseID() {
        return responseID;
    }

    public void setResponseID(Integer responseID) {
        this.responseID = responseID;
    }


    @Column(name = "IP", nullable = false)
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    @Column(name = "RESPONSE", nullable = false)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
