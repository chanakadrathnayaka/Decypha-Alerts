package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 2/21/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class CorporateActionTypes implements Serializable {
    private List<CorporateActionTypeItem> capitalActionTypes = new ArrayList<CorporateActionTypeItem>();
    private List<CorporateActionTypeItem> nonCapitalActionTypes = new ArrayList<CorporateActionTypeItem>();

    public List<CorporateActionTypeItem> getCapitalActionTypes() {
        return capitalActionTypes;
    }

    public void addCapitalActionTypes(CorporateActionTypeItem item) {
        this.capitalActionTypes.add(item);
    }

    public List<CorporateActionTypeItem> getNonCapitalActionTypes() {
        return nonCapitalActionTypes;
    }

    public void addNonCapitalActionTypes(CorporateActionTypeItem item) {
        this.nonCapitalActionTypes.add(item);
    }
}
