package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Danuran on 4/27/2015.
 */
@Entity
@Table(name = "PRODUCT_DESC")
public class ProductDescription implements Serializable {
    @Id
    @SequenceGenerator(name = "PROD_DESC", sequenceName = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROD_DESC")
    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "DESCRIPTION_EN")
    private String descriptionEN;

    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp lastUpdatedTime;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
