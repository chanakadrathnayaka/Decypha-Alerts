package com.dfn.alerts.dataaccess.orm.impl;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Danuran on 4/10/2015.
 */
@Entity
@Table(name = "CUSTOMER_PUBLIC_INFO", uniqueConstraints = {@UniqueConstraint(columnNames = {"TYPE", "ID"})})
public class CustomerPublicInfo {
    @Id
    @Column(name = "PUBLIC_ID")
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    private String publicId;

    @Column(name = "TYPE", nullable = false)
    private int type;

    @Column(name = "ID", nullable = false)
    private long id;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

