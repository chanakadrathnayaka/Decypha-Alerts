package com.dfn.alerts.dataaccess.orm.impl.notifications;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 13:33
 */

/**
 * Communication purposes with FUNDAMENTAL_DATA_NOTIFICATIONS table
 */
@Entity
@Table(name = "FUNDAMENTAL_DATA_NOTIFICATIONS")
public class FundamentalDataNotification implements Serializable {

    private int notificationId;
    private long transactionId;
    private String informationType;
    private int operationType;
    private String contributionItem;

    @Id
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "hibernate_fdn_seq")})
    @GeneratedValue(generator = "sequence")
    @Column(name = "NOTIFICATION_ID")
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }


    @Column(name = "TRANSACTION_ID")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = "INFORMATION_TYPE_ID")
    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    @Column(name = "OPERATION_TYPE")
    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    @Column(name = "CONTRIBUTION_ITEM")
    public String getContributionItem() {
        return contributionItem;
    }

    public void setContributionItem(String contributionItem) {
        this.contributionItem = contributionItem;
    }
}
