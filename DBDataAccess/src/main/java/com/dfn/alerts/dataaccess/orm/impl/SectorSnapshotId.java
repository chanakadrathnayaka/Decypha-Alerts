package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 9/2/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SectorSnapshotId implements Serializable {
    @Column(name = "ID")
    private int id;

    @Column(name = "PERIOD")
    private int period;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
