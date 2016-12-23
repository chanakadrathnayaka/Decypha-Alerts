package com.dfn.alerts.beans;

import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.CharUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dushani
 * Date: 8/6/13
 * Time: 10:30 AM
 */
public class IpoData implements Serializable {
    private List<IpoDTO> ipoDTOList = new ArrayList<IpoDTO>();
    private Map<String, CompanyDTO> managersList;
    private String managerIds = "";
    private String newsIds = "";

    public List<IpoDTO> getIpoDTOList() {
        return ipoDTOList;
    }

    public void setIpoDTOList(List<IpoDTO> ipoDTOList) {
        this.ipoDTOList = ipoDTOList;
    }

    @SuppressWarnings("unused")
    public Map<String, CompanyDTO> getManagersList() {
        return managersList;
    }

    public void setManagersList(Map<String, CompanyDTO> managersList) {
        this.managersList = managersList;
    }

    public String getManagerIds() {
        return managerIds;
    }

    public String getNewsIds() {
        return newsIds;
    }

    public void addManagerIds(String managerIds) {
        if (managerIds != null && !managerIds.isEmpty()) {
            if (!this.managerIds.isEmpty()) {
                this.managerIds = this.managerIds +  CharUtils.toString(IConstants.Delimiter.COMMA)  + managerIds;
            } else {
                this.managerIds = managerIds;
            }
        }
    }

    public void addNewsIds(int newsId) {
        if (newsId != 0) {
            if (!this.newsIds.isEmpty()) {
                this.newsIds = this.newsIds + CharUtils.toString(IConstants.Delimiter.COMMA) + newsId;
            } else {
                this.newsIds = Integer.toString(newsId);
            }
        }
    }

    public void addIPO(IpoDTO ipoDTO) {
        this.ipoDTOList.add(ipoDTO);
        this.addManagerIds(ipoDTO.getManagerIds());
        this.addNewsIds(ipoDTO.getNewsId());
        this.addNewsIds(ipoDTO.getActiveNewsId());
        this.addNewsIds(ipoDTO.getTradedNewsId());
        this.addNewsIds(ipoDTO.getUpcomingNewsId());
        this.addNewsIds(ipoDTO.getWithdrawnNewsId());
        this.addNewsIds(ipoDTO.getDetailsReleasedNewsId());
        this.addNewsIds(ipoDTO.getToBeTradedNewsId());

    }
}
