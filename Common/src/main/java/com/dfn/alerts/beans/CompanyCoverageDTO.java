package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Danura
 * Date: 2015-12-14
 * Time: 10:14
 */
public class CompanyCoverageDTO implements Serializable {
    String code;
    String description;
    int publicCount;
    int privateCount;
    int totalCount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublicCount() {
        return publicCount;
    }

    public void setPublicCount(int publicCount) {
        this.publicCount = publicCount;
    }

    public int getPrivateCount() {
        return privateCount;
    }

    public void setPrivateCount(int privateCount) {
        this.privateCount = privateCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
