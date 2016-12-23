package com.dfn.alerts.beans.ownershipLimits;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/9/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockOwnershipLimitSeriesDTO extends OwnershipLimitSeriesDTO{

    private double currentValue = 0;
    private String currentDate;
    private double currentVwap;
    private double currentVwapUSD;
    private long currentNoOfShares;
    private double comparativeValue = 0;
    private String comparativeDate;
    private double comparativeVwap;
    private double comparativeVwapUSD;
    private long comparativeNoOfShares;
    private boolean eligibility;
    private double limit;
    private double comparativeChange;

    private Map<String, Double> values;
    private List<String> dates;
    private Map<String, StockOwnershipValueDTO> dataValues;

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
        setComparativeChange();
    }

    public double getComparativeValue() {
        return comparativeValue;
    }

    public void setComparativeValue(double comparativeValue) {
        this.comparativeValue = comparativeValue;
        setComparativeChange();
    }

    public double getComparativeChange(){
        return this.comparativeChange;
    }

    private void setComparativeChange(){
        this.comparativeChange = currentValue - comparativeValue;
    }

    public boolean isEligibility() {
        return eligibility;
    }

    public void setEligibility(boolean eligibility) {
        this.eligibility = eligibility;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public Map<String, Double> getValues() {
        if(values == null){
            values = new LinkedHashMap<String, Double>();
        }
        return values;
    }

    private void addValue(String date, Double ownershipPct){
        getValues().put(date, ownershipPct);
    }

    public void setValues(Map<String, Double> values) {
        this.values = values;
    }

    public List<String> getDates() {
        if(dates == null){
            dates = new ArrayList<String>();
        }
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getComparativeDate() {
        return comparativeDate;
    }

    public void setComparativeDate(String comparativeDate) {
        this.comparativeDate = comparativeDate;
    }

    public Map<String, StockOwnershipValueDTO> getDataValues() {
        if(dataValues == null){
            dataValues = new LinkedHashMap<String, StockOwnershipValueDTO>();
        }
        return dataValues;
    }

    public void addDataValue(StockOwnershipValueDTO stockOwnershipValueDTO){
        addValue(stockOwnershipValueDTO.getOwnershipDate(), stockOwnershipValueDTO.getOwnershipPercentage());
        getDataValues().put(stockOwnershipValueDTO.getOwnershipDate(), stockOwnershipValueDTO);
        getDates().add(stockOwnershipValueDTO.getOwnershipDate());
    }

    public void setDataValues(Map<String, StockOwnershipValueDTO> dataValues) {
        this.dataValues = dataValues;
    }

    public double getCurrentVwapUSD() {
        return currentVwapUSD;
    }

    public void setCurrentVwapUSD(double currentVwapUSD) {
        this.currentVwapUSD = currentVwapUSD;
    }

    public double getCurrentVwap() {
        return currentVwap;
    }

    public void setCurrentVwap(double currentVwap) {
        this.currentVwap = currentVwap;
    }

    public long getCurrentNoOfShares() {
        return currentNoOfShares;
    }

    public void setCurrentNoOfShares(long currentNoOfShares) {
        this.currentNoOfShares = currentNoOfShares;
    }

    public double getComparativeVwap() {
        return comparativeVwap;
    }

    public void setComparativeVwap(double comparativeVwap) {
        this.comparativeVwap = comparativeVwap;
    }

    public double getComparativeVwapUSD() {
        return comparativeVwapUSD;
    }

    public void setComparativeVwapUSD(double comparativeVwapUSD) {
        this.comparativeVwapUSD = comparativeVwapUSD;
    }

    public long getComparativeNoOfShares() {
        return comparativeNoOfShares;
    }

    public void setComparativeNoOfShares(long comparativeNoOfShares) {
        this.comparativeNoOfShares = comparativeNoOfShares;
    }
}
