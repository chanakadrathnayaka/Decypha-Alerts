package com.dfn.alerts.beans.tickers;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 9/26/13
 * Time: 7:14 PM
 */
public class FundBenchmarkDTO extends TickerDTO {

    private long fundTickerSerial;
    private double benchmarkRate;
    private Timestamp benchmarkLastUpdatedTime;

    public long getFundTickerSerial() {
        return fundTickerSerial;
    }

    public void setFundTickerSerial(long fundTickerSerial) {
        this.fundTickerSerial = fundTickerSerial;
    }

    public double getBenchmarkRate() {
        return benchmarkRate;
    }

    public void setBenchmarkRate(double benchmarkRate) {
        this.benchmarkRate = benchmarkRate;
    }

    public Timestamp getBenchmarkLastUpdatedTime() {
        return benchmarkLastUpdatedTime;
    }

    public void setBenchmarkLastUpdatedTime(Timestamp benchmarkLastUpdatedTime) {
        this.benchmarkLastUpdatedTime = benchmarkLastUpdatedTime;
    }
}
