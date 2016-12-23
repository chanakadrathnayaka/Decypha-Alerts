package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by aravindal on 19/11/2014.
 */
public class IndividualProfileListDTO implements Serializable {
    private List<Map<String, String>> individualList = null;
    private Integer totalIndividualCount = 0;

    public List<Map<String, String>> getIndividualList() {
        return individualList;
    }

    public void setIndividualList(List<Map<String, String>> individualList) {
        this.individualList = individualList;
    }

    public Integer getTotalIndividualCount() {
        return totalIndividualCount;
    }

    public void setTotalIndividualCount(Integer totalIndividualCount) {
        this.totalIndividualCount = totalIndividualCount;
    }
}
