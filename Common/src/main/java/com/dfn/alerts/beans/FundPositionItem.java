package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by nimilaa on 2/24/14.
 */
public class FundPositionItem implements Serializable {
    private final String transactionDate;
    private final long investedTickerSerial;
    private final long noOfShares;
    private final long estimatedShares;
    private final double investmentPercentage;
    private final boolean singleRecord;

    public FundPositionItem(String transactionDate, long investedTickerSerial, double investmentPercentage, long noOfShares,
                            long estimatedShares, boolean singleRecord){
        this.transactionDate = transactionDate;
        this.investedTickerSerial = investedTickerSerial;
        this.investmentPercentage = investmentPercentage;
        this.noOfShares = noOfShares;
        this.estimatedShares = estimatedShares;
        this.singleRecord = singleRecord;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public long getInvestedTickerSerial() {
        return investedTickerSerial;
    }

    public double getInvestmentPercentage() {
        return investmentPercentage;
    }

    public long getNoOfShares() {
        return noOfShares;
    }

    public long getEstimatedShares() {
        return estimatedShares;
    }

    public boolean isSingleRecord() {
        return singleRecord;
    }
}
