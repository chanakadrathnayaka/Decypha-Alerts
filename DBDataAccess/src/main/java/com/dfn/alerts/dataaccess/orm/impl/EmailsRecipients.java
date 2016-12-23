package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 4/17/14
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "emails_recipients")
public class EmailsRecipients implements Serializable {

    private Long id;
    private String emailAddress;
    private String emailType;
    private int recipientType;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "CREATE_ID", sequenceName = "HIBERNATE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREATE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EMAIL_ADDRESS",nullable = false)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    @Column(name = "EMAIL_TYPE",nullable = false)
    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    @Column(name = "RECIPIENT_TYPE",nullable = false)
    public int getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(int recipientType) {
        this.recipientType = recipientType;
    }
}
