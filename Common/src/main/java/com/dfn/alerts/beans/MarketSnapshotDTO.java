package com.dfn.alerts.beans;

import com.dfn.alerts.beans.tickers.snapshot.IndexSnapshotDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 4/11/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarketSnapshotDTO extends  MarketDTO{

    //CM
    private String marketID         = null;
    private int decimalCorrFactor   = 1;
    private int marketStatus        = -1;
    private String lastEODDate      = null;
    private String marketDate       = null;
    private String marketTime       = null;
    private long volume             = -1;
    private double turnover         = -1;
    private int noOfTrades          = -1;
    private int symbolsTraded       = -1;
    private int noOfUps             = -1;
    private int noOfDown            = -1;
    private int noOfNoChange        = -1;
    private boolean isSubMarket     = false;

    //New
    private String   lastTradedDate  = null;
    private String   lastTradedTime  = null;
    private String cmSubscriptionStatus;

    private IndexSnapshotDTO indexDTO;

    public String getMarketID() {
        return marketID;
    }

    public void setMarketID(String marketID) {
        this.marketID = marketID;
    }

    public int getDecimalCorrFactor() {
        return decimalCorrFactor;
    }

    public void setDecimalCorrFactor(int decimalCorrFactor) {
        this.decimalCorrFactor = decimalCorrFactor;
    }

    public int getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(int marketStatus) {
        this.marketStatus = marketStatus;
    }

    public String getLastEODDate() {
        return lastEODDate;
    }

    public void setLastEODDate(String lastEODDate) {
        this.lastEODDate = lastEODDate;
    }

    public String getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(String marketDate) {
        this.marketDate = marketDate;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public int getNoOfTrades() {
        return noOfTrades;
    }

    public void setNoOfTrades(int noOfTrades) {
        this.noOfTrades = noOfTrades;
    }

    public int getSymbolsTraded() {
        return symbolsTraded;
    }

    public void setSymbolsTraded(int symbolsTraded) {
        this.symbolsTraded = symbolsTraded;
    }

    public int getNoOfUps() {
        return noOfUps;
    }

    public void setNoOfUps(int noOfUps) {
        this.noOfUps = noOfUps;
    }

    public int getNoOfDown() {
        return noOfDown;
    }

    public void setNoOfDown(int noOfDown) {
        this.noOfDown = noOfDown;
    }

    public int getNoOfNoChange() {
        return noOfNoChange;
    }

    public void setNoOfNoChange(int noOfNoChange) {
        this.noOfNoChange = noOfNoChange;
    }

    public boolean isSubMarket() {
        return isSubMarket;
    }

    public void setSubMarket(boolean subMarket) {
        isSubMarket = subMarket;
    }

    public String getLastTradedDate() {
        return lastTradedDate;
    }

    public void setLastTradedDate(String lastTradedDate) {
        this.lastTradedDate = lastTradedDate;
    }

    public String getLastTradedTime() {
        return lastTradedTime;
    }

    public void setLastTradedTime(String lastTradedTime) {
        this.lastTradedTime = lastTradedTime;
    }

    public IndexSnapshotDTO getIndexDTO() {
        return indexDTO;
    }

    public void setIndexDTO(IndexSnapshotDTO indexDTO) {
        this.indexDTO = indexDTO;
    }

    public String getCmSubscriptionStatus() {
        return cmSubscriptionStatus;
    }

    public void setCmSubscriptionStatus(String cmSubscriptionStatus) {
        this.cmSubscriptionStatus = cmSubscriptionStatus;
    }
}
