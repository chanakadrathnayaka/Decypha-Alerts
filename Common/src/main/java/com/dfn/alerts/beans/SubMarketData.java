package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 1/23/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubMarketData implements Serializable {
    /*"E|MC|DES|IDSM"*/
    private String exchangeCode;
    private String marketCode;
    private String description;
    private String isDefaultSubMarket;

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultSubMarket() {
        return isDefaultSubMarket;
    }

    public void setDefaultSubMarket(String defaultSubMarket) {
        isDefaultSubMarket = defaultSubMarket;
    }
}
