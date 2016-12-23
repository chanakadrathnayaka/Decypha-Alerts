package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 12/19/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class EarningsAnnouncement implements Serializable {
    private List<Map<String,String>> earningsAnnouncements = null;
    private int maxCount = 0;

    public List<Map<String, String>> getEarningsAnnouncements() {
        return earningsAnnouncements;
    }

    public void setEarningsAnnouncements(List<Map<String, String>> earningsAnnouncements) {
        this.earningsAnnouncements = earningsAnnouncements;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
