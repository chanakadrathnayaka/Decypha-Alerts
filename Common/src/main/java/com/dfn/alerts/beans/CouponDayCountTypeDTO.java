package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by aravindal on 08/07/14.
 */
public class CouponDayCountTypeDTO implements Serializable {
    int dayCountId;
    double dayCountValue;
    String dayCountDesc;

    public int getDayCountId() {
        return dayCountId;
    }

    public void setDayCountId(int dayCountId) {
        this.dayCountId = dayCountId;
    }

    public double getDayCountValue() {
        return dayCountValue;
    }

    public void setDayCountValue(double dayCountValue) {
        this.dayCountValue = dayCountValue;
    }

    public String getDayCountDesc() {
        return dayCountDesc;
    }

    public void setDayCountDesc(String dayCountDesc) {
        this.dayCountDesc = dayCountDesc;
    }
}
