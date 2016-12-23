package com.dfn.alerts.beans;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 11/15/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchObject {

    private int totalRecordCount;

    private List<? extends Object> values;

    public SearchObject(int totalRecordCount, List<? extends Object> values) {
        this.totalRecordCount = totalRecordCount;
        this.values = values;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public List<? extends Object> getValues() {
        return values;
    }
}
