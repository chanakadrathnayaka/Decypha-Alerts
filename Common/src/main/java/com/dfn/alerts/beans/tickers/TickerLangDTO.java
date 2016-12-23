package com.dfn.alerts.beans.tickers;

import java.io.Serializable;

/**
 * Created by nimilaa on 1/30/14.
 */
public class TickerLangDTO implements Serializable{
    private String tickerShortDesc;
    private String tickerLongDesc;
    private String sectorName;
    private String tickerCountryDesc;

    public String getTickerShortDesc() {
        return tickerShortDesc;
    }

    public void setTickerShortDesc(String tickerShortDesc) {
        this.tickerShortDesc = tickerShortDesc;
    }

    public String getTickerLongDesc() {
        return tickerLongDesc;
    }

    public void setTickerLongDesc(String tickerLongDesc) {
        this.tickerLongDesc = tickerLongDesc;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getTickerCountryDesc() {
        return tickerCountryDesc;
    }

    public void setTickerCountryDesc(String tickerCountryDesc) {
        this.tickerCountryDesc = tickerCountryDesc;
    }
}
