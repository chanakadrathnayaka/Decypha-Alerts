package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Danuran on 4/27/2015.
 */
@Entity
@Table(name = "BUSINESS_ACT_DESC")
public class BusinessActivityDesc implements Serializable {
    @Id
    @SequenceGenerator(name = "ACT_DESC", sequenceName = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACT_DESC")
    @Column(name = "ACTIVITY_ID")
    private Integer activityId;

    @Column(name = "DESCRIPTION_EN")
    private String descriptionEN;

    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp lastUpdatedTime;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
