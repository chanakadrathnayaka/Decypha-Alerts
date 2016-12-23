package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chanakar
 * Date: 9/23/13
 * Time: 7:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class FundActionDTO implements Serializable {

    private List<Map<String, String>> fundActions = null;
    private int maxCount;


    public List<Map<String, String>> getFundActions() {
        return fundActions;
    }

    public void setFundActions(List<Map<String, String>> fundActions) {
        this.fundActions = fundActions;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
