package com.dfn.alerts.beans.tickers;

import com.dfn.alerts.utils.CommonUtils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: duminda
 * Date: 4/9/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class EquityTickerDTO extends TickerDTO {

    private String  sectorNameLn;
    private String  sectorCode;
    private Integer lotSize;
    private String  unit;
    private Integer isMainStock;
    private long noOfShares;
    private String listedIndexes;
    private Integer useSubunit;
    private Map<String,String> sectorNames             = null;
    private long prevNoOfShares;
    private double stockMarketCap= -1;
    private double stockMarketCapUSD= -1;

    public String getSectorNameLn() {
        return sectorNameLn;
    }

    public void setSectorNameLn(String sectorNameLn) {
        this.sectorNames = CommonUtils.getLanguageDescriptionMap(sectorNameLn);
        this.sectorNameLn = sectorNameLn;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMainStock() {
        return isMainStock;
    }

    public void setMainStock(Integer mainStock) {
        isMainStock = mainStock;
    }

    public long getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(long noOfShares) {
        this.noOfShares = noOfShares;
    }

    public void setSectorNames(Map<String, String> sectorNames) {
        this.sectorNames = sectorNames;
    }

    public Map<String, String> getSectorNames() {
        return sectorNames;
    }

    public String getListedIndexes() {
        return listedIndexes;
    }

    public void setListedIndexes(String listedIndexes) {
        this.listedIndexes = listedIndexes;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public Integer getUseSubunit() {
        return useSubunit;
    }

    public void setUseSubunit(Integer useSubunit) {
        this.useSubunit = useSubunit;
    }

    public long getPrevNoOfShares() {
        return prevNoOfShares;
    }

    public void setPrevNoOfShares(long prevNoOfShares) {
        this.prevNoOfShares = prevNoOfShares;
    }

    public double getStockMarketCap() {
        return stockMarketCap;
    }

    public void setStockMarketCap(double stockMarketCap) {
        this.stockMarketCap = stockMarketCap;
    }

    public double getStockMarketCapUSD() {
        return stockMarketCapUSD;
    }

    public void setStockMarketCapUSD(double stockMarketCapUSD) {
        this.stockMarketCapUSD = stockMarketCapUSD;
    }
}
