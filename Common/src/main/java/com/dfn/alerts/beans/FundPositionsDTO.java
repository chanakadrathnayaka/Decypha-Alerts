package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.*;

/**
 * Created by nimilaa on 2/24/14.
 */
public class FundPositionsDTO implements Serializable {
    private Map<String, List<FundPositionItem>> currentFundPositions;
    private Map<String, List<FundPositionItem>> currentFundPositionsD;
    private Map<String, FundPositionItem> fundPositionsHistory;
    private Set<String> fundTickerSerials;

    private String startDate;
    private String endDate;

    public FundPositionsDTO() {
        currentFundPositions = new HashMap<String, List<FundPositionItem>>();
        fundPositionsHistory = new HashMap<String, FundPositionItem>();
        fundTickerSerials = new HashSet<String>();
    }

    public Map<String, List<FundPositionItem>> getCurrentFundPositions() {
        return currentFundPositions;
    }

    /**
     * this is sorted desc
     *
     * @param fundTickerSerial fund
     * @param fundPositionItem item
     */
    public void addCurrentFundPositions(String fundTickerSerial, FundPositionItem fundPositionItem) {
        List<FundPositionItem> currentList = this.currentFundPositions.get(fundTickerSerial);

        if (currentList == null) {
            currentList = new ArrayList<FundPositionItem>();
        }

        currentList.add(fundPositionItem);
        this.currentFundPositions.put(fundTickerSerial, currentList);
    }

    /**
     * this is sorted asc
     *
     * @param fundTickerSerial fund
     * @param fundPositionItem item
     */
    public void addCurrentFundPositionsForMax(String fundTickerSerial, FundPositionItem fundPositionItem) {
        if (currentFundPositionsD == null) {
            currentFundPositionsD = new HashMap<String, List<FundPositionItem>>();
        }
        List<FundPositionItem> currentList = this.currentFundPositionsD.get(fundTickerSerial);

        if (currentList == null) {
            currentList = new ArrayList<FundPositionItem>();
        }

        currentList.add(fundPositionItem);
        this.currentFundPositionsD.put(fundTickerSerial, currentList);
    }

    public void reverseOrderCurrentFundPositions() {
        if (currentFundPositionsD != null) {
            for (String k : this.currentFundPositionsD.keySet()) {
                List<FundPositionItem> list = this.currentFundPositionsD.get(k);
                Collections.reverse(list);
                currentFundPositions.put(k, list);
            }
            currentFundPositionsD = null;
        }
    }

    public Map<String, FundPositionItem> getFundPositionsHistory() {
        return fundPositionsHistory;
    }

    public void setFundPositionsHistory(Map<String, FundPositionItem> fundPositionsHistory) {
        this.fundPositionsHistory = fundPositionsHistory;
    }

    public void addFundPositionHistory(String fundTickerSerial, FundPositionItem fundPositionItem) {
        this.fundPositionsHistory.put(fundTickerSerial, fundPositionItem);
    }

    public Set<String> getFundTickerSerials() {
        return fundTickerSerials;
    }

    public void addFundTickerSerials(String fundTickerSerial) {
        this.fundTickerSerials.add(fundTickerSerial);
    }

    public boolean isFundTickerAdded(String fundTickerSerial) {
        return this.fundTickerSerials.contains(fundTickerSerial);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
