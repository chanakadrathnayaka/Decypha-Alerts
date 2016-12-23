package com.dfn.alerts.dataaccess.orm.impl.notification;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hasarindat on 4/4/2015.
 */
@Entity
@Table(name = "EMAIL_META_DATA")
public class EmailMetaData {

    @Id
    @Column(name = "EMAIL_ID")
    @SequenceGenerator(name = "seq_email_meta", sequenceName = "seq_email_meta")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_email_meta")
    private int id;

    @Column(name = "EMAIL_FROM", nullable = false)
    private String from;

    @Column(name = "EMAIL_TO")
    private String to;

    @Column(name = "EMAIL_BCC")
    private String bcc;

    @Column(name = "EMAIL_CC")
    private String cc;

    @Column(name = "NOTIFICATION_TIME", nullable = false)
    private Timestamp notificationTime;

    @Column(name = "STATUS", nullable = false)
    private int status;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp updatedTime;

    @Column(name = "RETRY_COUNT", nullable = false)
    private int retryCount;

    @Column(name = "EMAIL_TYPE", nullable = false)
    private int type;

    @Column(name = "SOURCE_ID", nullable = false)
    private int source;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "EMAIL_ID")
    private EmailContent emailContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public Timestamp getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Timestamp notificationTime) {
        this.notificationTime = notificationTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public EmailContent getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(EmailContent emailContent) {
        this.emailContent = emailContent;
    }

    @Override
    public String toString() {
        return "EmailMetaData{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", source=" + source +
                ", content=" + emailContent +
                '}';
    }
}
