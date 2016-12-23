package com.dfn.alerts.dataaccess.orm.impl.earnings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by chathurag
 * On 12/6/2016
 */
@Entity
@Table(name = "ALERT_EARNINGS_ANN")
public class EarningNotification implements Serializable{

    @Id
    @Column(name = "ALERT_ID")
    private Integer alertId;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "ALERT_TEXT")
    private String alertText;

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlertText() {
        return alertText;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }
}
