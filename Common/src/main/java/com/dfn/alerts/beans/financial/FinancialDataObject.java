package com.dfn.alerts.beans.financial;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Danuran on 4/1/2015.
 */
public class FinancialDataObject {

    private Integer companyId;
    private String financialType;
    private String viewType;
    private Map<String, List<String>> groupHeaders;//todo : sort by order in group
    private List<Integer> groups = new ArrayList<Integer>();
    private Map<String, String> groupDescription;
    private Map<String, FinancialHeader> headerMap;
    private Map<String, Map<String, String>> dataMap;

    private Map<String, Integer> fieldIndexMap;

    private int latestFinancialYear = -1;
    private int latestFinancialQuarter = -1;
    private List<String> financialPeriods = new ArrayList<String>();

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getFinancialType() {
        return financialType;
    }

    public void setFinancialType(String finacialType) {
        this.financialType = finacialType;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Map<String, List<String>> getGroupHeaders() {
        return groupHeaders;
    }

    public void setGroupHeaders(Map<String, List<String>> groupHeaders) {
        this.groupHeaders = groupHeaders;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void addGroup(Integer group) {
        groups.add(group);
        Collections.sort(groups);
    }

    public Map<String, String> getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(Map<String, String> groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Map<String, FinancialHeader> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, FinancialHeader> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, Map<String, String>> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Map<String, String>> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, Integer> getFieldIndexMap() {
        return fieldIndexMap;
    }

    public void setFieldIndexMap(Map<String, Integer> fieldIndexMap) {
        this.fieldIndexMap = fieldIndexMap;
    }

    public int getLatestFinancialYear() {
        return latestFinancialYear;
    }

    public void setLatestFinancialYear(int latestFinancialYear) {
        this.latestFinancialYear = latestFinancialYear;
    }

    public int getLatestFinancialQuarter() {
        return latestFinancialQuarter;
    }

    public void setLatestFinancialQuarter(int latestFinancialQuarter) {
        this.latestFinancialQuarter = latestFinancialQuarter;
    }

    public List<String> getFinancialPeriods() {
        return financialPeriods;
    }

    public void addFinancialPeriod(String fYear, String fQuarter) {
        if(StringUtils.isNumeric(fYear) && StringUtils.isNumeric(fQuarter)) {
            int financialYear = Integer.parseInt(fYear);
            int financialQuarter = Integer.parseInt(fQuarter);
            if (latestFinancialYear == -1) {
                latestFinancialYear = financialYear;
                latestFinancialQuarter = financialQuarter;
            } else {
                if (financialYear > latestFinancialYear) {
                    latestFinancialYear = financialYear;
                    latestFinancialQuarter = financialQuarter;
                } else if (financialYear == latestFinancialYear && financialQuarter > latestFinancialQuarter) {
                    latestFinancialQuarter = financialQuarter;
                }
            }
            financialPeriods.add(fYear + fQuarter);
            Collections.sort(financialPeriods);
        }
    }
}
