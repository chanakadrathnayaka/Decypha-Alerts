package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 8/8/13
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SCREENER_CATEGORIES")
public class ScreenerCategory implements Serializable {
    private String category;
    private String description;
    private String language;

    @Id
    @Column(name = "CATEGORY", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Id
    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @Column(name = "LANGUAGE", nullable = false)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
