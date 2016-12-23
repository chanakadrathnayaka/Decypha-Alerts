package com.dfn.alerts.dataaccess.orm.impl;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Business object for messages
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/15/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "INFO_MESSAGES")
public class MessageResourceBean implements Serializable{

    public static final String TYPE = "MESSAGE";

    private Long id;
    private String resourceKey;
    private String locale;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RESOURCE_KEY", length = 200, nullable = false)
    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    @Column(name = "LOCALE", length = 5, nullable = false)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Column(name = "VALUE", length = 3000)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
