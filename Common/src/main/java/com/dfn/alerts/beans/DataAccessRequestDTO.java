package com.dfn.alerts.beans;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Aravinda
 * Date: 11/09/14
 * Time: 11:35 PM
 * Request object for data access level
 */
public class DataAccessRequestDTO {
    List<Map<String,String>> requestMapList;
    String updateType;
    Object updateData;
    RequestDBDTO requestDBDTO;

    public RequestDBDTO getRequestDBDTO() {
        return requestDBDTO;
    }

    public void setRequestDBDTO(RequestDBDTO requestDBDTO) {
        this.requestDBDTO = requestDBDTO;
    }

    public List<Map<String, String>> getRequestMapList() {
        return requestMapList;
    }

    public void setRequestMapList(List<Map<String, String>> requestMapList) {
        this.requestMapList = requestMapList;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public Object getUpdateData() {
        return updateData;
    }

    public void setUpdateData(Object updateData) {
        this.updateData = updateData;
    }
}
