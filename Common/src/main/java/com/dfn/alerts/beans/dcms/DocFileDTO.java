package com.dfn.alerts.beans.dcms;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 11/28/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocFileDTO {

    private Integer fileId;
    private Integer categoryId;
    private Integer subCategoryId;
    private Date reportDate;
    private String provider;
    private Integer publisher;
    private String symbols;
    private String exchanges;
    private String countryCodes;
    private String defaultFileLang;
    private Integer status;
    private Timestamp lastUpdatedTime;
    private Timestamp lastSyncTime;
    private String industryCodes;

    private Map<String,DocFileLangDTO> docFileLangDTOMap;

    public DocFileDTO(){
        setDocFileLangDTOMap(new HashMap<String, DocFileLangDTO>());
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getDefaultFileLang() {
        return defaultFileLang;
    }

    public void setDefaultFileLang(String defaultFileLang) {
        this.defaultFileLang = defaultFileLang;
    }

    public Timestamp getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Timestamp lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Map<String, DocFileLangDTO> getDocFileLangDTOMap() {
        return docFileLangDTOMap;
    }

    public void setDocFileLangDTOMap(Map<String, DocFileLangDTO> docFileLangDTOMap) {
        this.docFileLangDTOMap = docFileLangDTOMap;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public String getExchanges() {
        return exchanges;
    }

    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }

    public String getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(String countryCodes) {
        this.countryCodes = countryCodes;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getIndustryCodes() {
        return industryCodes;
    }

    public void setIndustryCodes(String industryCodes) {
        this.industryCodes = industryCodes;
    }
}