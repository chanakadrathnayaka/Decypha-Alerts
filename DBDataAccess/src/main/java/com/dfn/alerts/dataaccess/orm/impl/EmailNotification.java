package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 3/12/14
 * Time: 2:55 PM
 */
@Entity
@Table(name = "EMAIL_AUDIT")
@Deprecated
public class EmailNotification implements Serializable {

    @Id
    @Column(name = "EMAIL_ID")
    @SequenceGenerator(name = "EMAIL_AUDIT", sequenceName = "EMAIL_AUDIT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_AUDIT")
    private Integer emailId;

    @Column(name = "EMAIL_FROM")
    private String fromEmail;

    @Column(name= "EMAIL_FROM_NAME")
    private String fromName;

    @Column(name = "EMAIL_TO")
    private String to;

    @Column(name = "EMAIL_BCC")
    private String bcc;

    @Column(name = "EMAIL_CC")
    private String cc;

    @Column(name = "EMAIL_TITLE")
    private String title;

    @Column(name = "EMAIL_BODY")
    private Clob body;

    @Transient
    private String content;

    @Column(name = "NOTIFICATION_TIME")
    private Timestamp notificationTime;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp lastUpdatedTime;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount = 0;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL_TYPE")
    private Integer emailType;

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Clob getBody() {
        return body;
    }

    public void setBody(Clob body) {
        this.body = body;
    }

    public Timestamp getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Timestamp notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getEmailType() {
        return emailType;
    }

    public void setEmailType(Integer emailType) {
        this.emailType = emailType;
    }
}
