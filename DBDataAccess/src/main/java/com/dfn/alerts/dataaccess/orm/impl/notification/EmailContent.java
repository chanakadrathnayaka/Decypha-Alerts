package com.dfn.alerts.dataaccess.orm.impl.notification;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Clob;

/**
 * Created by hasarindat on 4/6/2015.
 */
@Entity
@Table(name = "EMAIL_CONTENT")
public class EmailContent {

    @Id
    @Column(name="EMAIL_ID", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters = @Parameter(name="property", value="emailMetaData"))
    private int id;

    @OneToOne(mappedBy = "emailContent")
    @PrimaryKeyJoinColumn
    private EmailMetaData emailMetaData;

    @Column(name = "LANGUAGE_ID", nullable = false)
    private String language;

    @Column(name = "EMAIL_FROM_NAME", nullable = false)
    private String fromName;

    @Column(name = "EMAIL_TITLE", nullable = false)
    private String subject;

    @Column(name = "EMAIL_BODY")
    private Clob body;

    @Column(name = "IS_LOAD_ATTACHMENT", nullable = false)
    private boolean attachment;

    @Column(name = "ATTACHED_LOCATION")
    private String attachmentLocation;

    @Column(name = "ATTACHMENT_NAME")
    private String attachmentName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmailMetaData getEmailMetaData() {
        return emailMetaData;
    }

    public void setEmailMetaData(EmailMetaData emailMetaData) {
        this.emailMetaData = emailMetaData;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Clob getBody() {
        return body;
    }

    public void setBody(Clob body) {
        this.body = body;
    }

    public boolean isAttachment() {
        return attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String toString() {
        return "EmailContent{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
