package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chathurangad
 * Date: 10/9/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class KpiData implements Serializable {
    private List<KpiDTO> kpiDTOList;
    private List<Integer> kpiIdsList;
    private Map<String,List<KpiDTO>> kpiCompanyMap;
    private Map<String, CompanyDTO> relatedCompanyDTOMap;

    public List<KpiDTO> getKpiDTOList() {
        return kpiDTOList;
    }

    public void setKpiDTOList(List<KpiDTO> kpiDTOList) {
        this.kpiDTOList = kpiDTOList;
    }

    public List<Integer> getKpiIdsList() {
        return kpiIdsList;
    }

    public void setKpiIdsList(List<Integer> kpiIdsList) {
        this.kpiIdsList = kpiIdsList;
    }

    public Map<String, List<KpiDTO>> getKpiCompanyMap() {
        return kpiCompanyMap;
    }

    public void setKpiCompanyMap(Map<String, List<KpiDTO>> kpiCompanyMap) {
        this.kpiCompanyMap = kpiCompanyMap;
    }

    public Map<String, CompanyDTO> getRelatedCompanyDTOMap() {
        return relatedCompanyDTOMap;
    }

    public void setRelatedCompanyDTOMap(Map<String, CompanyDTO> companyDTOMap) {
        this.relatedCompanyDTOMap = companyDTOMap;
    }
}
