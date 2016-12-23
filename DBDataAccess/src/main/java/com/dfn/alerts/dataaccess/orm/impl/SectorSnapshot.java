package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 8/27/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SECTOR_SNAPSHOT")
public class SectorSnapshot {
    private SectorSnapshotId sectorSnapshotId;
    private double turnover;
    private double volume;
    private double close;
    private Sector sector;

    @EmbeddedId
    public SectorSnapshotId getSectorSnapshotId() {
        return sectorSnapshotId;
    }

    public void setSectorSnapshotId(SectorSnapshotId sectorSnapshotId) {
        this.sectorSnapshotId = sectorSnapshotId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", nullable = false)
    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Column(name = "TURNOVER", length = 21)
    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    @Column(name = "VOLUME", length = 21)
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Column(name = "CLOSE", length = 21)
    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }
}
