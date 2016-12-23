package com.dfn.alerts.beans.ownershipLimits;

import java.io.Serializable;

/**
 * Created by hasarindat on 3/3/2015.
 */
public class StockOwnershipValueDTO implements Serializable{

    private long tickerSerial;
    private int seriesId;

    private String ownershipDate;

    private double ownershipPercentage;
    private double vwap;
    private double vwapUSD;
    private long noOfShares;
    private boolean isCA;

    private StockOwnershipValueDTO() {
    }

    public StockOwnershipValueDTO(long tickerSerial, int seriesId, String ownershipDate) {
        this.tickerSerial = tickerSerial;
        this.seriesId = seriesId;
        this.ownershipDate = ownershipDate;
    }

    public double getOwnershipPercentage() {
        return ownershipPercentage;
    }

    public void setOwnershipPercentage(double ownershipPercentage) {
        this.ownershipPercentage = ownershipPercentage;
    }

    public double getVwap() {
        return vwap;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public double getVwapUSD() {
        return vwapUSD;
    }

    public void setVwapUSD(double vwapUSD) {
        this.vwapUSD = vwapUSD;
    }

    public long getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(long noOfShares) {
        this.noOfShares = noOfShares;
    }

    public boolean isCA() {
        return isCA;
    }

    public void setCA(boolean isCA) {
        this.isCA = isCA;
    }

    public long getTickerSerial() {
        return tickerSerial;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public String getOwnershipDate() {
        return ownershipDate;
    }
}
