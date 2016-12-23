package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/13/13
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportCategory implements Serializable {

    private static final String REG_EXP_LEADING_TRAILING_COMMA = "(^,)|(,$)";
    private static final String EMPTY_STRING = "";

    private int categoryId;
    private Map<String, String> description;
    private List<ReportSubCategory> subCategoryList;
    private String subCategoryIds = "";
    private int disclosure;
    private int report;
    private int research;

    public ReportCategory(int categoryId, Map<String, String> description) {
        this.categoryId = categoryId;
        this.description = description;
        this.subCategoryList = new ArrayList<ReportSubCategory>();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public List<ReportSubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<ReportSubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public String getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(String subCategoryIds) {
        this.subCategoryIds = subCategoryIds.replaceAll(REG_EXP_LEADING_TRAILING_COMMA, EMPTY_STRING);
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        if (this.report == 0) {
            this.report = report;
        }
    }

    public int getResearch() {
        return research;
    }

    public void setResearch(int research) {
        if (this.research == 0) {
            this.research = research;
        }
    }

    public int getDisclosure() {
        return disclosure;
    }

    public void setDisclosure(int disclosure) {
        if (this.disclosure == 0) {
            this.disclosure = disclosure;
        }
    }
}
