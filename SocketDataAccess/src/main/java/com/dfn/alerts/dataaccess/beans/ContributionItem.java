package com.dfn.alerts.dataaccess.beans;

import java.util.Arrays;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 10:53
 */

/**
 * Created as an attribute of {@link DataNotification}
 */
public class ContributionItem {
    private Object[] K;

    public Object[] getK() {
        return K;
    }

    public void setK(Object[] k) {
        K = k;
    }

    @Override
    public String toString() {
        return Arrays.toString(K);
    }
}
