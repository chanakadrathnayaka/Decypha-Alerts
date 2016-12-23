package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.util.Date;

/**
 * Represent dao object for USER_ACCOUNT_ACTIVATION table
 *
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 9/23/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USR_ACCOUNT_ACTIVATION", uniqueConstraints = {@UniqueConstraint(columnNames = {"ACT_ID"})})
public class UserActivationData {


    private Integer actID;
    private Long userID;
    private String actCode;
    private Date actCodeSentTime;
    private Date actCodeExpiryTime;
    private Integer status;

    @Id
    @Column( name = "ACT_ID")
    @SequenceGenerator(name="hibernate_sequence", sequenceName="hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="hibernate_sequence")
    public Integer getActID() {
        return actID;
    }

    public void setActID(Integer actID) {
        this.actID = actID;
    }

    @Column(name = "USER_ID",  nullable = false)
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "ACT_CODE",  nullable = false)
    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    @Column(name = "ACT_CODE_SENT_TIME",  nullable = false)
    public Date getActCodeSentTime() {
        return actCodeSentTime;
    }

    public void setActCodeSentTime(Date actCodeSentTime) {
        this.actCodeSentTime = actCodeSentTime;
    }

    @Column(name = "ACT_CODE_EXPIRE_TIME",  nullable = false)
    public Date getActCodeExpiryTime() {
        return actCodeExpiryTime;
    }

    public void setActCodeExpiryTime(Date actCodeExpiryTime) {
        this.actCodeExpiryTime = actCodeExpiryTime;
    }

    @Column(name = "STATUS",  nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
