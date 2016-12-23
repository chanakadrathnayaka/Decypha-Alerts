package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.constants.IConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/13/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "REPORTS_CATEGORIES")
public class ReportCategories implements Serializable {

    private String pageIds;
    private Integer category;
    private Integer subCategory;

    private Map<String,String> categoryDescription = null;
    private Map<String,String> subCategoryDescription = null;

    private String categoryDesc;
    private String subCategoryDesc;

    private Boolean disclosure;
    private Boolean report;
    private Boolean research;

    @Id
    @Column(name = "CATEGORY", nullable = false)
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Id
    @Column(name = "SUB_CATEGORY", nullable = false)
    public int getSubCategory() {
        return subCategory;
    }

    @Column(name = "PAGE_ID", length = 200, nullable = false)
    public String getPageIds() {
        return pageIds;
    }

    public void setPageIds(String pageIds) {
        this.pageIds = pageIds;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    @Column(name = "CATEGORY_DESCRIPTION", length = 250, nullable = false)
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDescription) {
        this.categoryDesc = categoryDescription;

        if(categoryDescription != null && categoryDescription.contains(Character.toString(IConstants.Delimiter.VL))){
            this.categoryDescription = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(categoryDescription, Character.toString(IConstants.Delimiter.VL));

            while (strTkn.hasMoreTokens()){

                String [] keyVal = strTkn.nextToken().split(":");

                if(keyVal != null && keyVal.length == 2)
                {
                    this.categoryDescription.put(keyVal[0],keyVal[1]);
                }

            }
        }
    }

    @Column(name = "SUB_CATEGORY_DESCRIPTION", length = 250, nullable = false)
    public String getSubCategoryDesc() {
        return subCategoryDesc;
    }

    public void setSubCategoryDesc(String subCategoryDesc) {
        this.subCategoryDesc = subCategoryDesc;
        if(subCategoryDesc != null && subCategoryDesc.contains(Character.toString(IConstants.Delimiter.VL))){
            this.subCategoryDescription = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(subCategoryDesc, Character.toString(IConstants.Delimiter.VL));

            while (strTkn.hasMoreTokens()){

                String [] keyVal = strTkn.nextToken().split(":");

                if(keyVal != null && keyVal.length == 2)
                {
                    this.subCategoryDescription.put(keyVal[0],keyVal[1]);
                }

            }
        }
    }

    @Column(name = "IS_RESEARCH")
    public Boolean isResearch() {
        return research;
    }

    public void setResearch(Boolean research) {
        this.research = research;
    }

    @Column(name = "IS_REPORT")
    public Boolean isReport() {
        return report;
    }

    public void setReport(Boolean report) {
        this.report = report;
    }

    @Column(name = "IS_DISCLOSURE")
    public Boolean isDisclosure() {
        return disclosure;
    }

    public void setDisclosure(Boolean disclosure) {
        this.disclosure = disclosure;
    }

    @Transient
    public Map<String, String> getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(Map<String, String> categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Transient
    public Map<String, String> getSubCategoryDescription() {
        return subCategoryDescription;
    }

    public void setSubCategoryDescription(Map<String, String> subCategoryDescription) {
        this.subCategoryDescription = subCategoryDescription;
    }
}
