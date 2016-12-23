package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chathurangad
 * Date: 4/19/13
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndividualProfilesDTO implements Serializable {
    private List<Map<String, String>> individuals = null;
    private int maxCount = 0;

    public List<Map<String, String>> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Map<String, String>> individuals) {
        this.individuals = individuals;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
