package com.dfn.alerts.beans.ownershipLimits;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/9/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class OwnershipLimitSeriesDTO implements Serializable{

    private int seriesId;
    private String seriesName;
    private String sourceId;
    private String direction;
    private int updateFrequencyId;
    private String updateFrequencyDescription;

    private Map<String, String> seriesNameMap = new HashMap<String, String>(2);
    private Map<String, String> updateFrequencyDescriptionMap = new HashMap<String, String>(2);

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public Map<String, String> getSeriesNameMap() {
        return seriesNameMap;
    }

    public void setSeriesName(String seriesName) {
        this.seriesNameMap = CommonUtils.getLanguageDescriptionMap(seriesName);
        this.seriesName = seriesName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getUpdateFrequencyId() {
        return updateFrequencyId;
    }

    public void setUpdateFrequencyId(int updateFrequencyId) {
        this.updateFrequencyId = updateFrequencyId;
    }

    public String getUpdateFrequencyDescription() {
        return updateFrequencyDescription;
    }

    public Map<String, String> getUpdateFrequencyDescriptionMap() {
        return updateFrequencyDescriptionMap;
    }

    public void setUpdateFrequencyDescription(String updateFrequencyDescription) {
        this.updateFrequencyDescriptionMap = CommonUtils.getLanguageDescriptionMap(updateFrequencyDescription);
        this.updateFrequencyDescription = updateFrequencyDescription;
    }

}
