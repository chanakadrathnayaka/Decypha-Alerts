package com.dfn.alerts.beans;

import com.dfn.alerts.constants.IConstants;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 8/27/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SectorSnapshotDTO implements Serializable {
    private double indexValue            = -1;
    private float high52Week             = -1;
    private float low52Week              = -1;
    private double turnover              = -1;
    private double volume                = -1;
    private double change                = -1;
    private double close                 = -1;
    private double percentageChange      = -1;
    private IConstants.SectorSnapshotTypes period;

    public SectorSnapshotDTO(double turnover, double volume, double close, int period){
        this.turnover = turnover;
        this.volume = volume;
        this.close = close;
        this.period = IConstants.SectorSnapshotTypes.valueOf(period);
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public IConstants.SectorSnapshotTypes getPeriod() {
        return period;
    }

    public void setPeriod(IConstants.SectorSnapshotTypes period) {
        this.period = period;
    }

    public double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(double indexValue) {
        this.indexValue = indexValue;
    }

    public float getHigh52Week() {
        return high52Week;
    }

    public void setHigh52Week(float high52Week) {
        this.high52Week = high52Week;
    }

    public float getLow52Week() {
        return low52Week;
    }

    public void setLow52Week(float low52Week) {
        this.low52Week = low52Week;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }
}
