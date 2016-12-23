package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/13/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportSubCategory implements Serializable {
    private int categoryId;
    private int subCategoryId;
    private Map<String, String> description;

    private boolean disclosure;
    private boolean report;
    private boolean research;

    public ReportSubCategory(int categoryId, int subCategoryId, Map<String, String> description, boolean disclosure, boolean report, boolean research) {
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.description = description;
        this.disclosure = disclosure;
        this.report = report;
        this.research = research;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public boolean isDisclosure() {
        return disclosure;
    }

    public void setDisclosure(boolean disclosure) {
        this.disclosure = disclosure;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public boolean isResearch() {
        return research;
    }

    public void setResearch(boolean research) {
        this.research = research;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReportSubCategory) {
            ReportSubCategory toCompare = (ReportSubCategory) o;
            return (this.categoryId == toCompare.categoryId && this.subCategoryId == toCompare.subCategoryId );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return categoryId;
    }
}
