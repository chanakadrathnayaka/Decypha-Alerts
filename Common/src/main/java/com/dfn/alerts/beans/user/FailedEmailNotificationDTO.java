package com.dfn.alerts.beans.user;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 3/12/14
 * Time: 5:23 PM
 */
public class FailedEmailNotificationDTO {

    private final int emailId;

    private final String fromEmail;

    private final String fromName;

    private final String to;

    private final String bcc;

    private final String cc;

    private final String title;

    private final String content;

    private final Timestamp lastUpdatedTime;

    private final Timestamp notificationTime;

    private final Integer retryCount;

    private final Long userId;

    private final Integer emailType;

    public FailedEmailNotificationDTO(int emailId, String fromEmail, String fromName, String to, String bcc, String cc,
                                      String title, String content, Timestamp lastUpdatedTime, Timestamp notificationTime
                                      , Integer retryCount, Long userId, Integer emailType) {
        this.emailId = emailId;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.to = to;
        this.bcc = bcc;
        this.cc = cc;
        this.title = title;
        this.content = content;
        this.lastUpdatedTime = lastUpdatedTime;
        this.notificationTime = notificationTime;
        this.retryCount = retryCount;
        this.userId = userId;
        this.emailType = emailType;
    }

    public int getEmailId() {
        return emailId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public String getTo() {
        return to;
    }

    public String getBcc() {
        return bcc;
    }

    public String getCc() {
        return cc;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getEmailType() {
        return emailType;
    }

    public Timestamp getNotificationTime() {
        return notificationTime;
    }
}
