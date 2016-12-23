package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 4/1/14
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "USER_REQUEST_DETAILS", uniqueConstraints = @UniqueConstraint(columnNames = {"ID"}))
public class UserRequest implements Serializable{

    private Long id;

    private String url;

    private Long userId;

    private String params;

    private String requestType;

    private Timestamp requestTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

    @Id
    @Column( name = "ID")
    @SequenceGenerator(name="hibernate_sequence", sequenceName="hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="hibernate_sequence")
    public Long getId() {
        return id;
    }

 
    public void setId(Long id) {
        this.id = id;
    }


    @Id
    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Column(name = "PARAMS")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Column(name = "REQUEST_TIME" , nullable = false, columnDefinition ="date default sysdate")
    public Timestamp  getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp  requestTime) {
        this.requestTime = requestTime;
    }

    @Column(name = "REQUEST_TYPE")
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
