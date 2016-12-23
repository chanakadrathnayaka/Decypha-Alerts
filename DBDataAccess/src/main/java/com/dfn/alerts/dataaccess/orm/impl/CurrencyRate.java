package com.dfn.alerts.dataaccess.orm.impl;

/**
 * Business object for CURRENCY_RATES
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/16/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "CURRENCY_RATES", uniqueConstraints = @UniqueConstraint(columnNames ={ "currency_pair" }))
public class CurrencyRate implements Serializable{

   private String currencyPair ;
   private Float rate ;
   private Date lastUpdatedDate ;

    @Id
    @Column(name = "currency_pair", length = 7, nullable = false)
    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    @Column(name = "rate",  nullable = true)
    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Column(name = "last_updated_date",  nullable = true)
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
          