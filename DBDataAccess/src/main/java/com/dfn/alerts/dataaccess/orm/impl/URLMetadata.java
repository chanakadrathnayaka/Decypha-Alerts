package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: nimilaa
 * Date: 10/25/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "URL_METADATA", uniqueConstraints = @UniqueConstraint(columnNames = {"ID"}))
public class URLMetadata {
    private Long id;
    private String expression;
    private Date createdDate;
    private Date lastUpdatedDate;
    private String lastUpdatedUserId;
    private String url;
    private String urlId;
    private String serviceType;
    private String lang;
    private Boolean isActive;
    private String resourceKey;
    private String menuGroup;
    private int displayOrder;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "URL_DATA", sequenceName = "URL_DATA_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "URL_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EXPRESSION", length = 1024)
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "LAST_UPDATED_DATE")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Column(name = "LAST_UPDATED_USER_ID", length = 128)
    public String getLastUpdatedUserId() {
        return lastUpdatedUserId;
    }

    public void setLastUpdatedUserId(String lastUpdatedUserId) {
        this.lastUpdatedUserId = lastUpdatedUserId;
    }

    @Column(name = "URL", length = 1024)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "SERVICE_TYPE", length = 64)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "LANG", length = 32)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column(name = "IS_ACTIVE")
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "RESOURCE_KEY", length = 64)
    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    @Column(name = "URL_ID", length = 64)
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Column(name = "MENU_GROUP", length = 64)
    public String getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(String menuGroup) {
        this.menuGroup = menuGroup;
    }

    @Column(name = "DISPLAY_ORDER")
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
