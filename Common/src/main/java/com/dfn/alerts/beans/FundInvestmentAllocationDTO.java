package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/23/13
 * Time: 5:00 PM
 */
public class FundInvestmentAllocationDTO implements Serializable{

    private int transactionSequence;        //TXN_SEQ
    private long fundTickerSerial;          //FUND_TICKER_SERIAL
    private String transactionDate;         //TXN_DATE
    private int factSheetId;                //FILE_ID
    private int allocationId;               //ALLOCATION_ID
    private int investmentType;             //INV_TYPE
    private String investmentTypeDesc;      //INV_TYPE_DESCRIPTION
    private int fundAssetId;                //INVESTED_ASSET_ID
    private String fundAssetDesc;           //INVESTED_ASSET_DESCRIPTION
    private long investedTickerSerial;      //INVESTED_TICKER_SERIAL
    private String investedTickerDesc;      //INVESTED_TICKER_DESCRIPTION
    private String investedCountryCode;     //INVESTED_COUNTRY_CODE
    private String investedCountryDesc;     //INVESTED_COUNTRY_DESCRIPTION
    private int investedSector;             //INVESTED_SECTOR
    private String investedSectorDesc;      //INVESTED_SECTOR_DESCRIPTION
    private double investmentPercentage;    //RATIO
                                            //LAST_UPDATED_TIME
    private String fileGUIDs;               //FILE_IDS


    public long getFundTickerSerial() {
        return fundTickerSerial;
    }

    public void setFundTickerSerial(long fundTickerSerial) {
        this.fundTickerSerial = fundTickerSerial;
    }

    public int getTransactionSequence() {
        return transactionSequence;
    }

    public void setTransactionSequence(int transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getFactSheetId() {
        return factSheetId;
    }

    public void setFactSheetId(int factSheetId) {
        this.factSheetId = factSheetId;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(int allocationId) {
        this.allocationId = allocationId;
    }

    public int getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(int investmentType) {
        this.investmentType = investmentType;
    }

    public String getInvestmentTypeDesc() {
        return investmentTypeDesc;
    }

    public void setInvestmentTypeDesc(String investmentTypeDesc) {
        this.investmentTypeDesc = investmentTypeDesc;
    }

    public int getFundAssetId() {
        return fundAssetId;
    }

    public void setFundAssetId(int fundAssetId) {
        this.fundAssetId = fundAssetId;
    }

    public String getFundAssetDesc() {
        return fundAssetDesc;
    }

    public void setFundAssetDesc(String fundAssetDesc) {
        this.fundAssetDesc = fundAssetDesc;
    }

    public long getInvestedTickerSerial() {
        return investedTickerSerial;
    }

    public void setInvestedTickerSerial(long investedTickerSerial) {
        this.investedTickerSerial = investedTickerSerial;
    }

    public String getInvestedTickerDesc() {
        return investedTickerDesc;
    }

    public void setInvestedTickerDesc(String investedTickerDesc) {
        this.investedTickerDesc = investedTickerDesc;
    }

    public String getInvestedCountryCode() {
        return investedCountryCode;
    }

    public void setInvestedCountryCode(String investedCountryCode) {
        this.investedCountryCode = investedCountryCode;
    }

    public String getInvestedCountryDesc() {
        return investedCountryDesc;
    }

    public void setInvestedCountryDesc(String investedCountryDesc) {
        this.investedCountryDesc = investedCountryDesc;
    }

    public int getInvestedSector() {
        return investedSector;
    }

    public void setInvestedSector(int investedSector) {
        this.investedSector = investedSector;
    }

    public String getInvestedSectorDesc() {
        return investedSectorDesc;
    }

    public void setInvestedSectorDesc(String investedSectorDesc) {
        this.investedSectorDesc = investedSectorDesc;
    }

    public double getInvestmentPercentage() {
        return investmentPercentage;
    }

    public void setInvestmentPercentage(double investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public String getFileGUIDs() {
        return fileGUIDs;
    }

    public void setFileGUIDs(String fileGUIDs) {
        this.fileGUIDs = fileGUIDs;
    }
}
