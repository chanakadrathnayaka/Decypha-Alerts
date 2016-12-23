package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/13/13
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageReportCategories implements Serializable {

    private static final String REG_EXP_LEADING_TRAILING_COMMA = "(^,)|(,$)";
    private static final String EMPTY_STRING = "";

    private String pageId;
    private List<ReportCategory> categoryList;
    private String categoryIds = "";
    private String subCategoryIds = "";

    public PageReportCategories(String pageId){
        this.pageId = pageId;
        this.categoryList = new ArrayList<ReportCategory>();
    }

    public PageReportCategories(){
        this.categoryList = new ArrayList<ReportCategory>();
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public List<ReportCategory> getCategoryList() {
        return categoryList;
    }

    public ReportCategory getCategory(int categoryId) {
        ReportCategory returnCategory = null;
        if(this.categoryList != null)  {
            for (ReportCategory category : this.categoryList) {
                if(category.getCategoryId()== categoryId){
                    returnCategory = category;
                    break;
                }
            }
        }
        return returnCategory;
    }

    public void setCategoryList(List<ReportCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds.replaceAll(REG_EXP_LEADING_TRAILING_COMMA, EMPTY_STRING);
    }

    public String getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(String subCategoryIds) {
        this.subCategoryIds = subCategoryIds.replaceAll(REG_EXP_LEADING_TRAILING_COMMA, EMPTY_STRING);
    }
}
