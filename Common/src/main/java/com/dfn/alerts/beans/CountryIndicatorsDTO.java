package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aravindal on 30/05/14.
 */
public class  CountryIndicatorsDTO implements Serializable {
    Map<String,Map<String,List<Object[]>>> indicatorValues;

    Map<Integer, Set<Date>> indicatorDatesMap;

    public Map<String, Map<String, List<Object[]>>> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(Map<String, Map<String, List<Object[]>>> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }

    public Map<Integer, Set<Date>> getIndicatorDatesMap() {
        return indicatorDatesMap;
    }

    public void setIndicatorDatesMap(Map<Integer, Set<Date>> indicatorDatesMap) {
        this.indicatorDatesMap = indicatorDatesMap;
    }
}
